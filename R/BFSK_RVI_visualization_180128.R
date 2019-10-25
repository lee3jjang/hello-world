# ctrl + shift + m

require(ggplot2)
require(dplyr)

rm(list=ls())
benz <- read.table(file='D:/데이터/sk_encar/benz.csv', sep=",", na.strings=" ", header=TRUE)
benz$MODEL <- as.character(benz$MODEL)

# dplyr 연습
# summary(benz)                                                                        # 벤츠 데이터 요약
# benz %>% select(PRICE)                                                               # 가격 컬럼 선택
# benz %>% arrange(PRICE)                                                              # 정렬
# benz %>% mutate(KM.MEAN=KM/(2017.5-PY))                                      # 파생변수 생성
# benz %>% mutate(KM.MEAN=KM/(2017.5-PY)) %>% select(KM.MEAN)                          # 파생변수 생성 후 선택
# benz %>% arrange(desc(CERT,MODEL))                                                   # 내림차순 정렬
# benz %>% group_by(CERT) %>% summarise(MEAN = mean(PRICE))                            # 인증별 가격 평균
# benz %>% group_by(MODEL) %>% summarise(MEAN = mean(PRICE))                           # 모델별 가격 평균
# benz %>% group_by(MODEL) %>% summarise(N = n())                                      # 모델별 개수
# count(benz, MODEL) %>% arrange(desc(n))                                              # 모델별 개수 및 내림차순 정렬

benz <- benz %>% filter(PY>=2000)                                                       # 필터
benz <- benz %>% mutate(CERT=ifelse(CERT=='Certificated','Certificated','Normal'))       # 컬럼 수정
model.n <- benz %>% group_by(MODEL) %>% summarise(N=n())
benz <- left_join(benz,model.n,by='MODEL')
benz <- benz %>% mutate(MODEL=ifelse(N<50,'Others',MODEL)) %>% select(-N)
benz$MODEL <- as.factor(benz$MODEL)
benz$CERT <- as.factor(benz$CERT)
rm(model.n)




col <- benz %>% group_by(MODEL,CERT) %>% summarise(N=n())
ggplot(data=col,aes(x=reorder(MODEL,-N),y=N,fill=CERT)) +
  geom_col(position='dodge') +
  coord_flip() +
  ggtitle('모델별 중고차 대수') +
  theme(plot.title=element_text(size=10,face='bold')) +
  xlab('대수') +
  ylab('빈도')

d1 <- benz %>% group_by(MODEL,CERT) %>% summarise(PRICE=mean(PRICE))
ggplot(d1,aes(x=CERT,MODEL)) +
  geom_point(aes(size=PRICE),shape=21,colour='grey',fill='green',alpha=0.5)

ggplot(benz,aes(x=CERT,y=PRICE)) +
  geom_boxplot(outlier.color='red') +
  stat_summary(fun.y='mean',geom='point',shape=21,size=3,fill='blue')

d2 <- benz %>% select(PRICE)
ggplot(d2,aes(x=PRICE,y=..density..)) +
  geom_histogram(binwidth=500,fill='blue',colour='blue',alpha=0.4) +
  geom_density(fill='yellow',colour='red',alpha=0.5) +
  #geom_line(stat='density',size=0.8) +
  xlab('가격') +
  ylab('빈도')



ggplot(benz,aes(x=PY,y=PRICE)) +
  geom_point(size=0,stroke=2,colour='skyblue',alpha=1.0,shape=1) + ggtitle('연식별 차량가격') +
  theme(plot.title=element_text(size=10,face='bold')) +
  geom_smooth(color='red')

ggplot(benz,aes(x=KM,y=PRICE)) +
  geom_point(size=2,alpha=0.6) + ggtitle('주행거리별 차량가격') +
  theme(plot.title=element_text(size=10,face='bold')) +
  geom_smooth(color='red') +
  xlim(c(0,300000))

ggplot(benz,aes(x=PY,y=KM)) +
  geom_point(size=2.5,color='blue',alpha=0.7) + ggtitle('연식별 주행거리') +
  theme(plot.title=element_text(size=10,face='bold')) +
  ylim(c(0,350000))
  
ggplot(data=benz) +
  geom_histogram(mapping=aes(x=PRICE,y=..density..),binwidth=250) +
  geom_density(mapping=aes(x=PRICE),fill='yellow',colour='red',alpha=0.5) +
  facet_grid(FUEL~CERT) +
  labs(x='가격',
       y='물량',
       title='가격대별 물량')

ggplot(data=benz) +
  geom_histogram(position='identity',mapping=aes(x=PRICE,y=..density..,group=CERT,fill=CERT,alpha=0.2),binwidth=250) +
  labs(x='가격',
       y='물량',
       title='가격대별 물량')

ggplot(data=benz) +
  geom_density(mapping=aes(x=PRICE,group=FUEL,fill=FUEL),colour='red',alpha=0.5) +
  labs(x='가격',
       y='물량',
       title='가격대별 물량')
  




################################################################################################

attach(benz)
# 시장 내 중고차 물량
ggplot(benz, aes(x=MODEL)) + geom_bar()                         # 모델별 물량 분포
ggplot(benz, aes(x=FUEL)) + geom_bar()                          # 연료별 물량 분포
ggplot(benz, aes(x=CERT)) + geom_bar()                          # 인증 종류별 물량 분포
ggplot(benz, aes(x=PY)) + geom_bar()                            # 연식별 물량 분포
table(MODEL)
table(FUEL)
table(CERT)
table(PY)


# 가격, 주행거리, 연식
qplot(KM,PRICE)                                                 # 주행거리와 가격의 관계
qplot(PY,KM)                                                    # 연식과 주행거리의 관계
qplot(PY,PRICE)                                                 # 연식과 가격의 관계
mean.km <- KM/(2017.5 - PY)                                     # 연 주행거리 분포
qplot(mean.km, bins=100, main='Mean Kilometer per Year')        # 연 주행거리 분포 요약
summary(mean.km)                                                # 요약 통계량 (음수 나오는 거는 2018년산 중고차(5개인가..) 때문에 2017.5-2018 되서 그런거임. 데이터 기준일 1/23일인데 2018년식 중고차 실화냐...)
ggplot(benz[FUEL=='Diesel',], aes(x=PRICE)) + geom_histogram(bins=100)
ggplot(benz, aes(x=FUEL, y=PRICE)) + geom_boxplot()             # 연료별 가격 분포. ※ Boxplot에 있는 가로선은 Median을 의미하지 Mean을 의미하지 않음. 실제로 평균값을 계산해보면 가솔린이 디젤보다 높다.
ggplot(benz, aes(x=MODEL, y=PRICE)) + geom_boxplot()            # 모델별 가격 분포
ggplot(benz, aes(x=CERT, y=PRICE)) + geom_boxplot()             # 인증 종류별 가격 분포
ggplot(benz, aes(x=PRICE)) + geom_histogram(bins=100)           # 전체 차량 가격 분포
summary(PRICE)                                                  # 가격 분포 요약



#5. 토막상식
Beta <- Pricing.model2$coefficients         # 계수
exp(Beta[2])-1                  # 일반중고차가 인증중고차보다 5%씩 가격이 낮음
exp(Beta[3])-1                  # 연식이 1년 감소할 때마다 16%씩 가격이 떨어짐
exp(Beta[4])^10000-1            # 1만KM 달릴 때마다 2%씩 가격이 떨어짐
exp(Beta[5])-1                  # 가솔린이 디젤보다 11% 비쌈

df <- data.frame(Sev)
row.names(df) <- NULL
ggplot(df) +
  geom_histogram(position='identity',mapping=aes(x=Sev,y=..density..),bins=100,color='blue',fill='blue',alpha=0.5) +
  geom_density(mapping=aes(x=Sev),color='red',linetype=2,fill='red',alpha=0.6)





detach(benz)


# 5,100만원
benz %>% filter(PY>=2015 & MODEL=='E-Class') %>%  summarise(mean(PRICE))
