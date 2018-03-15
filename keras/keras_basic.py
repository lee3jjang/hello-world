from keras.datasets import mnist
from keras.utils import np_utils
from keras.models import Sequential
from keras.layers import Dense, Activation
import matplotlib.pyplot as plt

#1. 전처리
(x_train, y_train), (x_test, y_test) = mnist.load_data()
x_train = x_train.reshape(60000,784).astype('float32') / 255.0
x_test = x_test.reshape(10000,784).astype('float32') / 255.0
y_train = np_utils.to_categorical(y_train)
y_test = np_utils.to_categorical(y_test)


#2. 모델 생성
model = Sequential()
model.add(Dense(units=64, input_dim=28*28, activation='relu'))
model.add(Dense(units=10, activation='softmax'))

#3. 손실함수, 최적화알고리즘, 메트릭 설정
model.compile(loss='categorical_crossentropy', optimizer='sgd', metrics=['accuracy'])

#4. 훈련 시키기
hist = model.fit(x_train, y_train, epochs=5, batch_size=32)
print(hist.history)

#5. 손실값, 메트릭값
loss_and_metrics = model.evaluate(x_test, y_test, batch_size=32)
print(loss_and_metrics)

#6. 테스트
xhat = x_test[0:1]
yhat = model.predict(xhat)
f, ax = plt.subplots(1,2,figsize=(12,6))
ax[0].imshow(xhat[0].reshape(28,28))
ax[1].scatter(range(10), yhat[0])
plt.tight_layout()
plt.show()
