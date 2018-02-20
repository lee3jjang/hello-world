require("forecast")
rm(list=ls())
set.seed(18)

x <- seq(0,100,0.1)
y <- -10*x + 17 + rnorm(length(x),sd=100)
y.ts <- ts(y)
plot(y.ts, xlab="time", main="White noice", ylab="value")
model <- rwf(y,h=100,drift=TRUE,level=c(80,95),fan=FALSE,lambda=NULL)
plot(model)
