import tensorflow as tf
import numpy as np

# setting
x_data = np.array([[0,0],[0,1],[1,0],[1,1]], dtype=np.float32)
y_data = np.array([[0],[1],[1],[0]], dtype=np.float32)

# optimization
X = tf.placeholder(tf.float32)
Y = tf.placeholder(tf.float32)
W1 = tf.Variable(tf.random_normal([2,10]), name='weight1')
b1 = tf.Variable(tf.random_normal([10]), name='bias1')
W2 = tf.Variable(tf.random_normal([10,1]), name='weight2')
b2 = tf.Variable(tf.random_normal([1]), name='bias2')

Z1 = tf.sigmoid(tf.matmul(X,W1) + b1)
Z2 = tf.sigmoid(tf.matmul(Z1,W2) + b2)
cost = -tf.reduce_mean(Y*tf.log(Z2) + (1-Y)*tf.log(1-Z2))
optimizer = tf.train.GradientDescentOptimizer(learning_rate=0.1)
train = optimizer.minimize(cost)

predicted = tf.cast(Z2 > 0.5, dtype=tf.float32)
accuracy = tf.reduce_mean(tf.cast(tf.equal(predicted,Y), dtype=tf.float32))

init = tf.global_variables_initializer()
dictionary = {X: x_data, Y: y_data}
with tf.Session() as sess:
    sess.run(init)
    
    for step in range(10001):
        sess.run(train, feed_dict=dictionary)
        if step % 100 == 0:
            print("step :", step, "cost :", sess.run(cost, feed_dict=dictionary))
            
    h, c, a = sess.run([Z2, predicted, accuracy], feed_dict=dictionary)
    print("\nHypothesis:",h,"\nPredicted:",c,"\nAccuracy:",a)
