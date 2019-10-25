# ctrl + shift + m
# ctrl + shift + c
# ctrl + alt + b
require(ggplot2)
require(dplyr)


#1. 데이터 전처리(Preprocessing)
rm(list=ls())
benz <- read.table(file='C:/Users/11700205/Documents/데이터/benz.csv', sep=',', header=TRUE) 
benz <- benz %>% filter(PY>=2000)
benz <- benz %>% mutate(CERT=ifelse(CERT=='Certificated','Certificated','Normal'))
benz$MODEL <- as.character(benz$MODEL)
model.n <- benz %>% group_by(MODEL) %>% summarise(N=n()) %>% arrange(desc(N))
benz <- left_join(benz,model.n,by='MODEL')
benz <- benz %>% mutate(MODEL=ifelse(N<50,'Others',MODEL)) %>% select(-N)
#distinct(benz,MODEL)
benz$MODEL <- as.factor(benz$MODEL)
benz$CERT <- as.factor(benz$CERT)


#2. 차량가격예측모형(Log-linear model)
Pricing.model <- lm(log(PRICE)~.,data=benz)
Beta <- Pricing.model$coefficients
write.csv(Beta,'coefficients.csv')


#3. 시뮬레이션
Trials <- 1000000
CPO <- benz %>% filter(CERT=='Certificated')
Insured <- CPO[sample(nrow(CPO), size=Trials, replace=TRUE),]
row.names(Insured) <- NULL
mean.km <- benz$KM/(2017.5 - benz$PY)
mean.km <- mean.km[mean.km>0]
Insured.3yr <- Insured

# Method 1 : 직관적으로 더 합리적이나 Loss가 작게 잡힘
#Insured.3yr$PRICE <- Insured.3yr$PRICE*exp(Beta['CERTNormal'])
#Insured.3yr$PRICE <- Insured.3yr$PRICE*exp(-3*Beta['PY'])
#Insured.3yr$PRICE <- Insured.3yr$PRICE*exp(3*Beta['KM']*mean.km[sample(NROW(mean.km), size=Trials, replace=TRUE)])
#Insured.3yr$PRICE <- Insured.3yr$PRICE*exp(rnorm(Trials,mean=0,sd=summary(Pricing.model)$sigma))
#Res.value <- Insured.3yr$PRICE/Insured$PRICE
# Method 2 : 직관적으로 덜 합리적이나 Loss가 크게 잡히고, 외부데이터와 일관성이 있음
Insured.3yr$CERT <- 'Normal'
Insured.3yr$PY <- Insured.3yr$PY - 3
Insured.3yr$KM <- Insured.3yr$KM + 3*mean.km[sample(NROW(mean.km), size=Trials, replace=TRUE)]
y <- predict(Pricing.model,Insured.3yr) + rnorm(Trials,mean=0,sd=summary(Pricing.model)$sigma)
names(y) <- NULL
Res.value <- exp(y)/Insured$PRICE

rm(Insured,Insured.3yr,CPO,y)
Bound <- 0.3
Freq <- NROW(Res.value[Res.value < Bound])/NROW(Res.value)
X <- Res.value[Res.value < Bound]
Discount <- 1.0
Loss <- pmax(Bound-Discount*X,0)


#4. 통계량 산출
p <- 0.995
VaR <- quantile(Loss, 1-(1-p)/Freq)
TVaR <- mean(Loss[Loss>VaR])
Statistics <- c(Freq,sqrt(Freq*(1-Freq)),mean(Loss),sd(Loss),Freq*mean(Loss),sqrt(mean(Freq)*var(Loss)+mean(Loss)^2*Freq*(1-Freq)),VaR,TVaR)
names(Statistics) <- c('mu.N','sigma.N','mu.X','sigma.X','mu.L','sigma.L','VaR','TVaR')
write.csv(Statistics,'statistics.csv')
