import numpy as np
import matplotlib.pyplot as plt

def action(x,y,z):
    if (not (x in [0,1])&(y in [0,1])&(z in [0,1])):
        print("입력을 올바르게 해주세요!")
        return
    
    p = np.array([[[0.5, 0, 0.5],[0,0,1]],
                  [[0.7,0.1,0.2],[0,0.95,0.05]],
                  [[0.4,0,0.6],[0.3,0.3,0.4]]])
    reward_book = np.array([[[0,0,0],[0,0,0]],
                  [[5,0,0],[0,0,0]],
                  [[0,0,0],[-1,0,0]]])
    
    next_state = 0
    time = 10000
    result = []
    
    action_book = {0:x,1:y,2:z}
    for i in range(time):
        state = next_state
        action = action_book[state]
        next_state = np.random.choice(a=3, size=1, p=p[state][action])[0]
        reward = reward_book[state][action][next_state]
        result.append([state, action, next_state, reward])
        #if i % 100 == 0:
        #    print('i : {0:4}  State : {1}  Action : {2}  Next state : {3}  Reward : {4}'.format(i, state, action, next_state, reward))

    result = np.array(result)
    return np.mean(result[:,3])
    
    
if __name__ == __main__:
  index = np.array([[0,0,0],[0,0,1],[0,1,0],[0,1,1],[1,0,0],[1,0,1],[1,1,0],[1,1,1]])
  for i in range(8):
      print('{}{}{} : {}'.format(index[i][0],index[i][1],index[i][2],action(index[i][0],index[i][1],index[i][2])))
