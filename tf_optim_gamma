import numpy as np
from scipy.stats import gamma
import tensorflow as tf
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
import tensorflow.contrib.distributions as dist


# setting
alpha = 2
theta = 100
x = np.linspace(0,1500,100)
y = gamma.pdf(x, a=alpha, scale=theta)
#plt.plot(x,y)
data = gamma.rvs(a=alpha, scale=theta, size=1000).astype(np.float32)
#print(np.mean(data), np.var(data))


# optimization
a = tf.Variable(2.0)
b = tf.Variable(25.0)
dist = dist.Gamma(concentration=a, rate=1/b)
loglikelihood = tf.reduce_mean(dist.log_prob(data))
optimizer = tf.train.GradientDescentOptimizer(0.1)
train = optimizer.minimize(-loglikelihood)
init = tf.global_variables_initializer()
train_data = []

with tf.Session() as sess:
    sess.run(init)
    for step in range(25000):
        evals = sess.run([train, a, b])[1:]
        if step % 5 == 0:
            train_data.append(evals)
            print(step, evals, sess.run(loglikelihood))
       
    
# plotting    
#converter = plt.colors
cr, cg, cb = (1.0, 1.0, 0.0)
for f in train_data:
    cb += 1.0 / len(train_data)
    cg -= 1.0 / len(train_data)
    if cb > 1.0: cb = 1.0
    if cg < 0.0: cg = 0.0
    [a, b] = f
    line = plt.plot(a, b, 'ro', markersize=5)
    plt.setp(line, color=(cr,cg,cb))
    plt.xlabel('alpha')
    plt.ylabel('theta')
    
plt.plot(alpha, theta,'ro', markersize=8, color='black')
green_line = mpatches.Patch(color='black', label='Data Points')
plt.legend(handles=[green_line])
plt.show()
