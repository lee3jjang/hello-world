rm(list=ls())
data <- read.csv('KIS_spot_continuous_montly_201612.csv')

# 환경설정
dt <- 1/12
tau <- c(0.25, 0.5, 0.75, 1, 1.5, 2, 2.5, 3, 5, 7, 10, 15, 20)

# 모수 입력
lambda <- 0.0702
eps <- 1e-3
kappa1 <- 0.5
kappa2 <- 0.5
kappa3 <- 0.5
mu1 <- 0.01
mu2 <- 0.01
mu3 <- 0.01
sigma11 <- 0.01
sigma22 <- 0.01
sigma33 <- 0.01

# A 계산
A <- matrix(c(kappa1,0,0,
              0,kappa2,0,
              0,0,kappa3), nrow=3, byrow=TRUE)

# B 계산
B <- matrix(c((1-kappa1)*mu1, (1-kappa2)*mu2, (1-kappa3)*mu3), ncol=1)

# Q 계산
Q <- matrix(c(sigma11^2,0,0,
              0,sigma22^2,0,
              0,0,sigma33^2), nrow=3, byrow=TRUE)
# H 계산
h1 <- replicate(13,1)
h2 <- (1-exp(-lambda*tau))/(lambda*tau)
h3 <- (1-exp(-lambda*tau))/(lambda*tau)-exp(-lambda*tau)
H <- cbind(h1, h2, h3)

# R 계산
R <- diag(13)

# 초기값 입력
x <- x0 <- matrix(c(0.02, -0.01, 0.01), ncol=1)
P <- P0 <- diag(3)*0.01

# 예측 단계
x.pred <- A%*%x + B
P.pred <- A%*%P%*%t(A) + Q

# 보정 단계
z.meas <- t(data[1,]);rownames(z.meas) <- NULL
z.pred <- H%*%x.pred
F <- H%*%P.pred%*%t(H)+R
K <- P.pred%*%t(H)%*%solve(F)
x.update <- x.pred + K%*%(z.meas-z.pred)
P.update <- P.pred - K%*%H%*%P.pred

# 루프
x <- x0 <- matrix(c(0.02, -0.01, 0.01), ncol=1)
P <- P0 <- diag(3)*0.01
x.stack <- list(x)
P.stack <- list(P)
n <- dim(data)[1]
for(i in 1:n){
  # 예측 단계
  x.pred <- A%*%x + B
  P.pred <- A%*%P%*%t(A) + Q
  
  # 보정 단계
  z.meas <- t(data[1,]);rownames(z.meas) <- NULL
  z.pred <- H%*%x.pred
  F <- H%*%P.pred%*%t(H)+R
  K <- P.pred%*%t(H)%*%solve(F)
  x <- x.update <- x.pred + K%*%(z.meas-z.pred)
  P <- P.update <- P.pred - K%*%H%*%P.pred
  x.stack[[i+1]] <- x.update
  P.stack[[i+1]] <- P.update
}