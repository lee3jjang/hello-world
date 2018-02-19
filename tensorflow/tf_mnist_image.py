import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data
import matplotlib.pyplot as plt
mnist = input_data.read_data_sets("./mnist/data/", one_hot=True)

batch_xs, batch_ys = mnist.train.next_batch(1)
batch_xs.shape = (28,28)
plt.imshow(batch_xs)
with tf.Session() as sess:
    print('Number:',sess.run(tf.argmax(batch_ys,1)[0]))
