import tensorflow as tf
import numpy as np

x_data = np.array([
    [0,0],   #[털, 날개]
    [1,0],
    [1,1],
    [0,0],
    [0,0],
    [0,1]
])

y_data = np.array([
    [1,0,0],  # 기타
    [0,1,0],  # 포유류
    [0,0,1],  # 조류
    [1,0,0],
    [1,0,0],
    [0,0,1]
])

X = tf.placeholder(tf.float32)
Y = tf.placeholder(tf.float32)

W1 = tf.Variable(tf.random_uniform([2,10],-1,1))
W2 = tf.Variable(tf.random_uniform([10,3],-1,1))
b1 = tf.Variable(tf.zeros([10]))
b2 = tf.Variable(tf.zeros([3]))

L1 = tf.nn.relu(tf.matmul(X,W1) + b1)
model = tf.nn.softmax(tf.nn.relu(tf.matmul(L1,W2) + b2))

cost = tf.reduce_mean(-tf.reduce_sum(Y*tf.log(model),axis=1))
optimizer = tf.train.AdamOptimizer(learning_rate=0.01)
train = optimizer.minimize(cost)

init = tf.global_variables_initializer()
sess = tf.Session()
sess.run(init)

dictionary = {X: x_data, Y: y_data}
for step in range(10000):
    sess.run(train, feed_dict=dictionary)
    if (step+1) % 10 == 0:
        print(step+1, sess.run(cost, feed_dict=dictionary))
        
prediction = tf.argmax(model,1)
target = tf.argmax(Y,1)
print('예측값:', sess.run(prediction, feed_dict={X: x_data}))
print('실제값:', sess.run(target, feed_dict={Y: y_data}))

is_correct = tf.equal(prediction, target)
accuracy = tf.reduce_mean(tf.cast(is_correct, tf.float32))
print('정확도: %2.f' % sess.run(accuracy * 100, feed_dict=dictionary))
