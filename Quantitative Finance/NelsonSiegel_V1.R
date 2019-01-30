rm(list=ls())

# 데이터 불러오기
data <- read.csv('KIS_spot_continuous_montly_201612.csv')

# 환경설정(만기)
tau <- c(0.25, 0.5, 0.75, 1, 1.5, 2, 2.5, 3, 5, 7, 10, 15, 20)

# 모수설정(베타)
lambda <- 0.7071
L <- 0.02
S <- -0.01
C <- 0.01
beta <- matrix(c(L, S, C), ncol=1)

# H 계산
h1 <- replicate(13,1)
h2 <- (1-exp(-lambda*tau))/(lambda*tau)
h3 <- (1-exp(-lambda*tau))/(lambda*tau)-exp(-lambda*tau)
H <- cbind(h1, h2, h3)

# 그래프
plot(tau, data[132,])
for(i in 1:10){
  lambda <- 1-0.0500*i
  h1 <- replicate(13,1)
  h2 <- (1-exp(-lambda*tau))/(lambda*tau)
  h3 <- (1-exp(-lambda*tau))/(lambda*tau)-exp(-lambda*tau)
  H <- cbind(h1, h2, h3)
  lines(tau, H%*%beta)
}

# 베타 추정
r <- as.vector(t(data[132, ])); colnames(r) <- NULL; rownames(r) <- NULL
df <- data.frame(r, H)
model <- lm(r~h2+h3, data=df)
beta.fitted <- matrix(model$coefficients, ncol=1)

# λ 추정
obj.func <- function(lambda){
  h1 <- replicate(13,1)
  h2 <- (1-exp(-lambda*tau))/(lambda*tau)
  h3 <- (1-exp(-lambda*tau))/(lambda*tau)-exp(-lambda*tau)
  H <- cbind(h1, h2, h3)
  df <- data.frame(r, H)
  model <- lm(r~h2+h3, data=df)
  beta.fitted <- matrix(model$coefficients, ncol=1)
  mse <- sqrt(mean(model$residuals^2))
  return(mse)
}
lambda.fitted <- optim(0.5, obj.func, method='Brent', lower=0, upper=1)$par
r <- as.vector(t(data[132, ])); colnames(r) <- NULL; rownames(r) <- NULL
df <- data.frame(r, H)
model <- lm(r~h2+h3, data=df)
beta.fitted <- matrix(model$coefficients, ncol=1)

# 그래프2
h1 <- replicate(13,1)
h2 <- (1-exp(-lambda.fitted*tau))/(lambda.fitted*tau)
h3 <- (1-exp(-lambda.fitted*tau))/(lambda.fitted*tau)-exp(-lambda.fitted*tau)
H <- cbind(h1, h2, h3)
plot(tau, data[132,])
lines(tau, H%*%beta.fitted)
