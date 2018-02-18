import numpy as np
import tensorflow as tf

x_data = np.random.rand(100).astype(np.float32)
y_data = 3*x_data + 2 
y_data = np.vectorize(lambda y: y + np.random.normal(loc=0.0, scale=0.1))(y_data)

a = tf.Variable(1.0)
b = tf.Variable(0.2)
y = a*x_data + b
loss = tf.reduce_mean(tf.square(y-y_data))

optimizer = tf.train.GradientDescentOptimizer(0.01)
train = optimizer.minimize(loss)

init = tf.global_variables_initializer()
train_data = []
with tf.Session() as sess:
    sess.run(init)
    for step in range(10000):
        evals = sess.run([train,a,b])[1:]
        train_data.append([step, evals])
        if step % 5 == 0:
            print(step, evals)
