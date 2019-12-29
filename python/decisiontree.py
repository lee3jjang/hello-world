import csv
import numpy as np
from collections import Counter
from statistics import mode
import matplotlib.pyplot as plt

class AdaBoost:

    def __init__(self, n):
        self.models = None
        self.n = n

    def predict(self, X):
        # Predict
        y_pred_ensemble = list()
        for i in range(len(self.models)):
            y_pred_ensemble.append(self.models[i][0].predict(X))
        y_pred_ensemble = np.r_[y_pred_ensemble].T

        keys = dict()
        indices = dict()
        for idx, key in enumerate(np.unique(y_pred_ensemble)):
            keys[key] = idx
            indices[idx] = key

        y_pred = np.zeros(shape=(len(X), len(keys)))
        for i in range(len(X)):
            for j in range(len(self.models)):
                
                y_pred[i, keys[y_pred_ensemble[i][j]]] += self.models[j][1]
        y_pred = np.array([indices[i] for i in np.argmax(y_pred, axis=1)])
        return y_pred

    def train(self, X, y):
        models = list()
        for _ in range(self.n):

            # Train
            model = Stump()
            model.train(X, y)

            # Predict
            y_pred = model.predict(X)

            # Sample Weight
            total_error = sum(y_pred!=y)/len(y)
            amt_to_say = 0.5*np.log((1-total_error)/total_error)
            weight = np.where(y_pred!=y, np.exp(amt_to_say), np.exp(-amt_to_say))
            weight /= weight.sum()
            models.append((model, amt_to_say))

            # Resampling
            sample = np.random.choice(len(y), len(y), p=weight)
            X = X[sample]
            y = y[sample]
        
        self.models = models

class Stump:

    def __init__(self):
        self.criterion = None
        self.criterion_value = None
        self.left = None
        self.right = None

    def predict(self, X):
        y_pred = np.where(X[:, self.criterion] <= self.criterion_value, self.left, self.right)
        return y_pred

    def __str__(self):
        result = ''
        result += '--- Stump ---\n'
        result += 'Criterion: {}\n'.format(self.criterion)
        result += 'Criterion Value: {}\n'.format(self.criterion_value)
        result += 'Left: {}\n'.format(self.left)
        result += 'Right: {}\n'.format(self.right)
        result += '------------'
        return result

    def train(self, X, y):
        result = tuple()

        for col in range(X.shape[1]):
            index = np.argsort(X[:, col])
            criteria = np.unique(X[index, col])
            for i in range(1, len(criteria)):
                criterion_value = (criteria[i-1]+criteria[i])/2
                
                y_left = y[X[:, col] <= criterion_value]
                p_left = len(y_left)/len(y)

                y_right = y[X[:, col] > criterion_value]
                p_right = len(y_right)/len(y)

                idx = self._gini_index(y_left)*p_left+self._gini_index(y_right)*p_right

                if (result==tuple()):
                    result = (col, criterion_value, Counter(y_left).most_common()[0][0], Counter(y_right).most_common()[0][0], idx)
                elif (idx < result[-1]):
                    result = (col, criterion_value, Counter(y_left).most_common()[0][0], Counter(y_right).most_common()[0][0], idx)

        self.criterion = result[0]
        self.criterion_value = result[1]
        self.left = result[2]
        self.right = result[3]


    def _gini_index(self, y):
        # Probability
        count = Counter(y)
        total = sum(count.values())
        probs = dict()
        for key, n in count.items():
            probs[key] = n/total

        # Gini Index
        idx = 0
        for key, p in probs.items():
            idx += p*(1-p)
        return idx


class NeuralNet:

    def __init__(self, n_hidden):
        self.n_hidden = n_hidden
        self.W1 = None
        self.b1 = None
        self.W2 = None
        self.b2 = None

    def predict(self, X):
        affine1 = Affine(self.W1, self.b1)
        sigmoid1 = Sigmoid()
        affine2 = Affine(self.W2, self.b2)
        softmax = Softmax()

        z1 = affine1.forward(X)
        a1 = sigmoid1.forward(z1)
        z2 = affine2.forward(a1)
        y_prob = softmax.forward(z2)
        return y_prob
    
    def train(self, X, y):
        n_in = X.shape[1]
        n_out = y.shape[1]
    
        # Initialize
        self.W1 = np.random.rand(n_in, self.n_hidden)
        self.b1 = np.random.rand(self.n_hidden)
        self.W2 = np.random.rand(self.n_hidden, n_out)
        self.b2 = np.random.rand(n_out)

        for _ in range(50000):
            affine1 = Affine(self.W1, self.b1)
            sigmoid1 = Sigmoid()
            affine2 = Affine(self.W2, self.b2)
            softmaxwithloss = SoftmaxWithLoss()
            
            z1 = affine1.forward(X)
            a1 = sigmoid1.forward(z1)
            z2 = affine2.forward(a1)
            loss = softmaxwithloss.forward(z2, y_onehot)

            dout = softmaxwithloss.backward()
            dout = affine2.backward(dout)
            dout = sigmoid1.backward(dout)
            dout = affine1.backward(dout)
            
            lr = 0.05
            self.W1 -= lr*affine1.dW
            self.b1 -= lr*affine1.db
            self.W2 -= lr*affine2.dW
            self.b2 -= lr*affine2.db



class Affine:
    def __init__(self, W, b):
        self.W = W
        self.b = b
        self.x = None
        self.dW = None
        self.db = None

    def forward(self, x):
        self.x = x
        out = x@self.W + self.b
        return out
    
    def backward(self, dout):
        dx = dout@self.W.T
        self.dW = self.x.T@dout
        self.db = np.sum(dout, axis=0)
        return dx

class Sigmoid:
    def __init__(self):
        self.out = None

    def forward(self, x):
        out = 1/(1+np.exp(-x))
        self.out = out
        return out

    def backward(self, dout):
        dx = dout*(1.0 - self.out)*self.out
        return dx

class Softmax:
    def __init__(self):
        pass

    def forward(self, x):
        out = np.exp(x)/np.exp(x).sum(axis=1).reshape(-1,1)
        return out

class SoftmaxWithLoss:
    def __init__(self):
        self.loss = None
        self.y = None
        self.t = None

    def forward(self, x, t):
        self.t = t
        self.y = self._softmax(x)
        self.loss = self._cross_entropy_error(self.y, self.t)
        return self.loss

    def backward(self, dout=1):
        batch_size = self.t.shape[0]
        dx = (self.y-self.t)/batch_size
        return dx

    def _softmax(self, x):
        return np.exp(x)/np.exp(x).sum(axis=1).reshape(-1,1)

    def _cross_entropy_error(self, y, t):
        return -np.sum((t/t.shape[1])*np.log(y), axis=1).mean()


if __name__ == '__main__':

    np.random.seed(10)

    # Load Data
    with open('./data/iris.csv', 'r') as f:
        data = [line for line in csv.reader(f)]
        data = data[1:]
    X = [x[:4] for x in data]
    X = np.asarray(X, dtype=float)
    y = np.asarray([x[4] for x in data])

    # (Stump) Sample Model
    model = Stump()
    model.criterion = 0
    model.criterion_value = 5.5
    model.left = 'Setosa'
    model.right = 'Virginica'
 
    test = np.array([[0.5, 0.2, 4.2], [7, 2, 6.2]])

    model = AdaBoost(30)
    model.train(X, y)

    y_pred = model.predict(X)

    #############################################################

    keys = dict()
    indices = dict()
    for idx, key in enumerate(np.unique(y)):
        keys[key] = idx
        indices[idx] = key

    y_onehot = np.zeros(shape=(len(y), len(keys)))
    y_onehot[np.arange(len(y)), np.array([keys[x] for x in y])] = 1

    model = NeuralNet(5)
    model.train(X, y_onehot)

    y_prob = model.predict(X)
    y_pred = np.argmax(y_prob, axis=1)
    y_pred = np.array([indices[x] for x in y_pred])
    print(y_pred)
    


    
    



    
    