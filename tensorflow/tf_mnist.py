import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data
mnist = input_data.read_data_sets("./mnist/data/", one_hot=True)

X = tf.placeholder(tf.float32, [None,784])
Y = tf.placeholder(tf.float32, [None,10])

W1 = tf.Variable(tf.random_normal([784,256],stddev=0.01))
L1 = tf.nn.relu(tf.matmul(X,W1))
W2 = tf.Variable(tf.random_normal([256,256],stddev=0.01))
L2 = tf.nn.relu(tf.matmul(L1,W2))
W3 = tf.Variable(tf.random_normal([256,10],stddev=0.01))
model = tf.nn.softmax(tf.nn.relu(tf.matmul(L2,W3)))
cost = tf.reduce_mean(-tf.reduce_sum(Y*tf.log(model),axis=1))
optimizer = tf.train.AdamOptimizer(0.001).minimize(cost)

init = tf.global_variables_initializer()
sess = tf.Session()
sess.run(init)

batch_size = 100
total_batch = int(mnist.train.num_examples / batch_size)

for epoch in range(15):
    total_cost = 0
    
    for i in range(total_batch):
        batch_xs, batch_ys = mnist.train.next_batch(batch_size)
        
        _, cost_val = sess.run([optimizer, cost], feed_dict={X: batch_xs, Y: batch_ys})
        total_cost += cost_val

    print('Epoch:', '%04d' % (epoch + 1),
         'Avg. cost =', '{:.3f}'.format(total_cost / total_batch))
    
print('최적화 완료!')

is_correct = tf.equal(tf.argmax(model, 1), tf.argmax(Y, 1))
accuracy = tf.reduce_mean(tf.cast(is_correct, tf.float32))
print('정확도:', sess.run(accuracy, feed_dict={X: mnist.test.images, Y:mnist.test.labels}))
