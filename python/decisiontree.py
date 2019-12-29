import csv
import numpy as np
from collections import Counter
from statistics import mode

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


if __name__ == '__main__':

    np.random.seed(10)

    # Sample Model
    model = Stump()
    model.criterion = 0
    model.criterion_value = 5.5
    model.left = 'Setosa'
    model.right = 'Virginica'
 
    test = np.array([[0.5, 0.2, 4.2], [7, 2, 6.2]])
    # print(model.predict(test))

    # Load Data
    with open('./data/iris.csv', 'r') as f:
        data = [line for line in csv.reader(f)]
        data = data[1:]
    X = [x[:3] for x in data]
    X_sample = X = np.asarray(X, dtype=float)
    y_sample = y = np.asarray([x[4] for x in data])

    models = list()
    for _ in range(10):

        # Train
        model = Stump()
        model.train(X_sample, y_sample)
        

        # Predict
        y_pred = model.predict(X_sample)

        # Sample Weight
        total_error = sum(y_pred!=y_sample)/len(y_sample)
        amt_to_say = 0.5*np.log((1-total_error)/total_error)
        weight = np.where(y_pred!=y_sample, np.exp(amt_to_say), np.exp(-amt_to_say))
        weight /= weight.sum()
        models.append((model, amt_to_say))

        

        # Resampling
        sample = np.random.choice(len(y_sample), len(y_sample), p=weight)
        X_sample = X_sample[sample]
        y_sample = y_sample[sample]

    for i in range(len(models)):
        print('Amount to Say: {}'.format(models[i][1]))
        print(models[i][0])

    # Predict
    y_pred_ensemble = list()
    for i in range(len(models)):
        y_pred_ensemble.append(models[i][0].predict(X))
    y_pred_ensemble = np.r_[y_pred_ensemble].T

    keys = dict()
    indices = dict()
    for idx, key in enumerate(np.unique(y_pred_ensemble)):
        keys[key] = idx
        indices[idx] = key

    y_pred = np.zeros(shape=(len(X), len(keys)))
    for i in range(len(X)):
        for j in range(X.shape[1]):
            y_pred[i, keys[y_pred_ensemble[i][j]]] += models[j][1]
    y_pred = np.array([indices[i] for i in np.argmax(y_pred, axis=1)])

    y_pred_0 = models[0][0].predict(X)
    print(sum(y_pred_0==y)/len(y))
    print(sum(y_pred==y)/len(y))

    


    
