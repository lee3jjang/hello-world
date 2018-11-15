#0. 데이터 초기화
rm(list=ls())
gc()

######################################### Ⅰ. Smith-Wilson #########################################

#1. 클래스(붕어빵틀) 정의하기
setClass("Smith.Wilson", representation(alpha="numeric", UFR="numeric", maturity="vector", observation="vector", zeta="vector"))


#2. 윌슨 함수(Wilson Function) 정의
setGeneric("Wilson", function(object, t, u) standardGeneric("Wilson"))
setMethod("Wilson", "Smith.Wilson", function(object, t, u){
  result <- exp(-object@UFR*(t+u))*(object@alpha*min(t,u) - exp(-object@alpha*max(t,u))*sinh(object@alpha*min(t,u)))
  return(result)
})

#3. ζ 구하기
setGeneric("calcZeta", function(object) standardGeneric("calcZeta"))
setMethod("calcZeta", "Smith.Wilson", function(object){
  
  n <- length(object@observation)

  #3-1. W 계산
  W <- matrix(0,n,n)
  for (i in 1:n){
    for (j in 1:n){
      W[i,j] <- Wilson(object, object@maturity[i],object@maturity[j])  
    }
  }
  
  #3-2. m, μ 계산
  m <- 1/(1+object@observation)^object@maturity
  mu <- exp(-object@UFR*object@maturity)
  
  #3-3. ζ 계산
  object@zeta <- as.vector(solve(W)%*%(m-mu))
  
  return(object)
})


#4. 만기(t)별 무이표채 가격(P(t)) 메소드 정의
setGeneric("P", function(object, t, ...) standardGeneric("P"))
setMethod("P", "Smith.Wilson", function(object, t){
  
  n <- length(object@observation)
  term1 <- exp(-object@UFR*t)
  term2 <- 0
  
  for(i in 1:n){
    term2 <- term2 + object@zeta[i]*Wilson(object, t, object@maturity[i])
  }
  return(term1+term2)
})


#5. 만기(t)별 현물 이자율(spot(t)) 메소드 정의
setGeneric("spot", function(object, t) standardGeneric("spot"))
setMethod("spot", "Smith.Wilson", function(object, t){
  if(t < 1e-15) t <- 1e-15
  return((1/P(object, t))^(1/t)-1)
})



#6. 윌슨함수의 도함수 정의
setGeneric("derivWilson", function(object, t, u) standardGeneric("derivWilson"))
setMethod("derivWilson", "Smith.Wilson", function(object, t, u){
  alpha <- object@alpha
  UFR <- object@UFR
  
  if(t < u){
    deriv <- exp(-UFR*t-(alpha+UFR)*u)*(UFR*sinh(alpha*t)-alpha*cosh(alpha*t)-alpha*(UFR*t-1)*exp(alpha*u)) 
  } else {
    deriv <- exp(-UFR*u-(alpha+UFR)*t)*((alpha+UFR)*sinh(alpha*u)-alpha*UFR*u*exp(alpha*t))
  }
  
  return(deriv)
})

#6-2. 윌슨함수의 2계 도함수 정의
setGeneric("deriv2ndWilson", function(object, t, u) standardGeneric("deriv2ndWilson"))
setMethod("deriv2ndWilson", "Smith.Wilson", function(object, t, u){
  alpha <- object@alpha
  UFR <- object@UFR
  
  if(t < u){
    deriv2nd <- exp(-UFR*t-(alpha+UFR)*u)*(-(alpha^2+UFR^2)*sinh(alpha*t)+2*alpha*UFR*cosh(alpha*t)+alpha*UFR*(UFR*t-2)*exp(alpha*u))
  } else {
    deriv2nd <- exp(-UFR*u-(alpha+UFR)*t)*(alpha*UFR^2*u*exp(alpha*t)-(alpha+UFR)^2*sinh(alpha*u))
  }
  
  return(deriv2nd)
})

#7. 만기(t)별 무이표채 가격의 도함수(derivP(t)) 메소드 정의
setGeneric("derivP", function(object, t) standardGeneric("derivP"))
setMethod("derivP", "Smith.Wilson", function(object, t){
  
  n <- length(object@observation)
  term1 <- -object@UFR*exp(-object@UFR*t)
  term2 <- 0
  
  for(i in 1:n){
    term2 <- term2 + object@zeta[i]*derivWilson(object, t, object@maturity[i])
  }
  return(term1+term2)
})

#7-2. 만기(t)별 무이표채 가격의 이계 도함수(deriv2ndP(t)) 메소드 정의
setGeneric("deriv2ndP", function(object, t) standardGeneric("deriv2ndP"))
setMethod("deriv2ndP", "Smith.Wilson", function(object, t){
  
  n <- length(object@observation)
  term1 <- (-object@UFR)^2*exp(-object@UFR*t)
  term2 <- 0
  
  for(i in 1:n){
    term2 <- term2 + object@zeta[i]*deriv2ndWilson(object, t, object@maturity[i])
  }
  return(term1+term2)
})


#8. 만기(t)별 선도(spot) 이자율(forward(t)) 메소드 정의
setGeneric("forward", function(object, t) standardGeneric("forward"))
setMethod("forward", "Smith.Wilson", function(object, t){
  return(-derivP(object, t)/P(object, t))
})

#8-2. 만기(t)별 선도(spot) 이자율의 도함수(derivForward(t)) 메소드 정의
setGeneric("derivForward", function(object, t) standardGeneric("derivForward"))
setMethod("derivForward", "Smith.Wilson", function(object, t){
  return(1/P(object, t)*(derivP(object, t)^2/P(object, t)-deriv2ndP(object, t)))
})


#9. α 대입하기
setGeneric("setAlpha<-", function(this, value) standardGeneric("setAlpha<-"))
setReplaceMethod("setAlpha", signature("Smith.Wilson", "numeric"), function(this, value){
  this@alpha <- value
  this <- calcZeta(this)
  return(this)
})


#9-2. UFR 대입하기
setGeneric("setUFR<-", function(this, value) standardGeneric("setUFR<-"))
setReplaceMethod("setUFR", signature("Smith.Wilson", "numeric"), function(this, value){
  this@UFR <- value
  this <- calcZeta(this)
  return(this)
})


#10. α 찾기
setGeneric("calcAlpha", function(object) standardGeneric("calcAlpha"))
setMethod("calcAlpha", "Smith.Wilson", function(object){
  M <- 60
  tol <- 1e-4
  error <- function(alpha){
    setAlpha(object) <- alpha
    return(abs(abs(forward(object, M)-object@UFR)-tol))
  }
  setAlpha(object) <- optim(0.05, fn=error, method="Brent", lower=0.05, upper=1)$par
  return(object)
})


#11. 초기화 구현하기(★)
setMethod("initialize", "Smith.Wilson", function(.Object, UFR, maturity, observation){
  .Object@UFR <- UFR
  .Object@maturity <- maturity
  .Object@observation <- observation
  .Object <- calcAlpha(.Object)
  return(.Object)
})


#12. 선도스왑이자율(Forward Swap Rate) (교환주기 : 분기)
setGeneric("fswap", function(object, start, tenor) standardGeneric("fswap"))
setMethod("fswap", "Smith.Wilson", function(object, start, tenor){
  tau <- 0.25
  end <- start+tenor
  x <- seq(start+tau, end, by=tau)
  y <- sum(sapply(x, FUN=function(t) P(object, t)))
  result <- (P(object, start) - P(object, end))/(y*tau)
  return(result)
})


#13. 선도스왑 현금흐름 (교환주기 : 분기)
setGeneric("fswapCashFlow", function(object, start, tenor) standardGeneric("fswapCashFlow"))
setMethod("fswapCashFlow", "Smith.Wilson", function(object, start, tenor){
  tau <- 0.25
  n <- as.integer(tenor/tau)
  c <- rep(fswap(object, start, tenor)*tau, n)
  c[n] <- 1+c[n]
  return(c)
})


#14. Black Receiver Swaption (ATM, 교환주기 : 분기)
setGeneric("ReceiverSwaption", function(object, maturity, tenor, ...) standardGeneric("ReceiverSwaption"))
setMethod("ReceiverSwaption", "Smith.Wilson", function(object, maturity, tenor, blackVol){
  tau <- 0.25
  alpha <- P(object, maturity)
  beta <- P(object, maturity+tenor)
  d1 <- 0.5*blackVol*sqrt(maturity)
  result <- (alpha-beta)*(2*pnorm(d1, mean=0, sd=1)-1)
  return(result)
})




###############################################################################################

#1. Smith.Wilson 객체(sw) 생성
sw <- new("Smith.Wilson",
          UFR=0.056,
          maturity=c(1,2,3,4,5,7,10,12,15,20),
          observation=c(0.0334,0.0326,0.0327,0.0328,0.033,0.0335,0.0342,0.0345,0.0347,0.0352)
      )

######################################### Ⅱ. Hull-White(미완성) #########################################

#1. 클래스 정의하기
setClass("Hull.White", representation(a="numeric", sigma="numeric", dt="numeric", tenor="vector", Blackvol="matrix", TermStr="Smith.Wilson"))

#2. B(t,T)
setGeneric("B", function(object, t, T) standardGeneric("B"))
setMethod("B", "Hull.White", function(object, t, T){
  result <- (1-exp(-object@a*(T-t)))/object@a
  return(result)
})


#3. A(t,T)
setGeneric("A", function(object, t, T) standardGeneric("A"))
setMethod("A", "Hull.White", function(object, t, T){
  result <- P(object@TermStr,T)/P(object@TermStr,t)*exp(B(object,t,T)*forward(object@TermStr, t)-object@sigma^2/(4*object@a)*B(object,t,T)^2*(1-exp(-2*object@a*t)))
  return(result)
})


#4. 만기(t)별 무이표채 가격(P(t)) 메소드 정의
setMethod("P", "Hull.White", function(object, t, T, r){
  result <- A(object, t, T)*exp(-B(object,t, T)*r)
  return(result)
})


#5. σ_p
setGeneric("sigma_p", function(object, T, S) standardGeneric("sigma_p"))
setMethod("sigma_p", "Hull.White", function(object, T, S){
  result <- object@sigma/object@a*(1-exp(-object@a*(S-T)))*sqrt((1-exp(-2*object@a*T))/(2*object@a))
  return(result)
})


#6. Jamshidian's Trick (선도스왑 교환주기 : 분기)
setGeneric("Jamshidian", function(object, start, tenor) standardGeneric("Jamshidian"))
setMethod("Jamshidian", "Hull.White", function(object, start, tenor){
  tau <- 0.25
  end <- start + tenor
  T <- seq(start+tau, end, by=tau)
  c <- fswapCashFlow(object@TermStr, start, tenor)
  error <- function(r){
    X <- sapply(T, function(x) P(object, t=start, T=x, r=r))
    return(abs(sum(c*X)-1))
  }
  
  result <- optim(0, error, method="Brent", lower=0, upper=1)$par
  return(result)
})


#7. Hull-White Receiver Swaption (ATM, 교환주기 : 분기)
setMethod("ReceiverSwaption", "Hull.White", function(object, maturity, tenor){
  r <- Jamshidian(object, maturity, tenor)
  r0 <- spot(object@TermStr, 0)
  tau <- 0.25
  end <- maturity + tenor
  T <- seq(maturity+tau, end, by=tau)
  X <- sapply(T, function(x) P(object, t=maturity, T=x, r=r))
  c <- fswapCashFlow(object@TermStr, maturity, tenor)
  n <- length(T)
  
  d.pos <- sapply(1:n, FUN=function(i) 1/sigma_p(object, maturity, T[i])*log(P(object@TermStr, T[i])/P(object@TermStr, maturity)/X[i])+sigma_p(object, maturity, T[i])/2)
  d.neg <- sapply(1:n, FUN=function(i) d.pos[i] - sigma_p(object, maturity, T[i]))
  
  terms <- sapply(1:n, FUN=function(i) c[i]*(P(object@TermStr, T[i])*pnorm(d.pos[i]) - X[i]*P(object@TermStr, maturity)*pnorm(d.neg[i])))
  result <- sum(terms)
  
  return(result)
})

#8. Parameter Setting
setGeneric("setParams<-", function(this, value) standardGeneric("setParams<-"))
setReplaceMethod("setParams", signature("Hull.White", "vector"), function(this, value){
  this@a <- value[1]
  this@sigma <- value[2]
  return(this)
})


#9. Calibration
setGeneric("Calibration", function(object) standardGeneric("Calibration"))
setMethod("Calibration", "Hull.White", function(object){
  n <- length(object@tenor)
  BlackRS <- matrix(0,n,n)
  for(i in 1:n){
    for(j in 1:n){
      BlackRS[i,j] <- ReceiverSwaption(object@TermStr, object@tenor[i], object@tenor[j], object@Blackvol[i,j])
    }
  }
  
  error <- function(params){
    setParams(object) <- params
    HullWhiteRS <- matrix(0,n,n)
    for(i in 1:n){
      for(j in 1:n){
        HullWhiteRS[i,j] <- ReceiverSwaption(object, object@tenor[i], object@tenor[j])
      }
    }
    
    res <- sqrt(sum((BlackRS-HullWhiteRS)^2))
    return(res)
  }
  
  p <- optim(c(0.01,0.005), fn=error, method="Nelder-Mead", control=list(reltol=100))$par
  return(p)
})

#10. Theta
setGeneric("Theta", function(object, t) standardGeneric("Theta"))
setMethod("Theta", "Hull.White", function(object, t){
  y <- derivForward(object@TermStr, t)+object@a*forward(object@TermStr, t)+object@sigma^2/(2*object@a)*(1-exp(-2*object@a*t))
  return(y)
})

#11. Simulation
setGeneric("Simulation", function(object, m, n=1) standardGeneric("Simulation"))
setMethod("Simulation", "Hull.White", function(object, m, n){
  dW <- sqrt(object@dt)*matrix(rnorm(m*n), m, n)
  r <- matrix(0, m+1, n)
  r[1,] <- forward(object@TermStr,0)
  
  for (i in 1:m){
    t <- (i-1)/12
    r[i+1,] <- r[i,]+(Theta(object,t)-object@a*r[i,])*object@dt+object@sigma*dW[i,]
  }
  
  if(n==1) r <- as.vector(r)
  
  return(r)
})

###############################################################################################

#1. Black 변동성 입력
vol <- matrix (c(0.1815,0.172,0.17,0.1605,0.158,0.155,
                 0.177,0.1715,0.17,0.158,0.1545,0.15,
                 0.174,0.168,0.1655,0.1555,0.1495,0.1475,
                 0.165,0.1575,0.157,0.1495,0.1455,0.144,
                 0.165,0.1575,0.157,0.1535,0.1455,0.144,
                 0.1665,0.159,0.1575,0.15,0.1455,0.14),
                 ncol=6, byrow=TRUE)
tenor <- c(1,2,3,5,7,10)

#2. Hull.White 클래스의 인스턴스(hw) 생성
hw <- new("Hull.White",
          a=0.01,
          sigma=0.005,
          tenor=tenor,
          Blackvol=vol,
          dt=1/12,
          TermStr=sw)

#3. 캘리브레이션
setParams(hw) <- Calibration(hw)


#4. 시뮬레이션
n <- 200
r <- Simulation(hw, m=120, n=n)

#4-2. 시각화
plot(r[,1], type='l', xlab="경과월", ylab="이자율", ylim=c(-0.03,0.1))
for (i in 2:n){
  par(new=TRUE)
  plot(r[,i], type='l', axes=FALSE, xlab="", ylab="", ylim=c(-0.03,0.1))
}
