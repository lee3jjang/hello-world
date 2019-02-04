import pickle
data = [('sangjin', '90', '80', '70'),
        ('jiwon', '95', '85', '75'),
        ('sangjin', '10', '50', '40'),
        ('jiwon', '15', '15', '35')
        ]

with open('out.txt', 'wb') as f:
    pickle.dump(data, f)

with open('out.txt', 'rb') as f:
    data = pickle.load(f)
    print(data)

