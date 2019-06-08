#!/usr/bin/env python
# coding: utf-8

# In[1]:


import torch
from itertools import product

class Ising:
    
    # 초기화
    def __init__(self, num_row, num_col):
        self.num_row, self.num_col = num_row, num_col
        self.gen_system()
        
    # (r x s) System configuration을 랜덤으로 생성    
    def gen_system(self, p=0.5):
        self.spins = torch.where(torch.rand(self.num_row, self.num_col) < p, torch.ones(1), -torch.ones(1))
    
    # System의 Hamiltonian를 계산
    def hamiltonian(self):
        H, J = 0., 1.
        for i, j in product(range(self.num_row), range(self.num_col)):
            for i_nhb, j_nhb in self.get_neighbors(i, j):
                H -= J*self.spins[i_nhb, j_nhb]*self.spins[i, j]
        return H/2
            
    # 격자 (i, j)의 neighbors를 리턴
    def get_neighbors(self, i, j):
        assert (i >= 0) and (i <= self.num_row-1) and (j >= 0) and (j <= self.num_col-1)
        nhb = []
        if j != self.num_col - 1: nhb.append([i, j+1])
        if j != 0: nhb.append([i, j-1])
        if i != self.num_row - 1: nhb.append([i+1, j])
        if i != 0: nhb.append([i-1, j])
        return nhb
    
    # n개의 데이터 생성 (Hamiltonian이 낮을수록 생성 확률 높음)
    def gen_train_data(self, num_data):
        self.gen_system()
        H = self.hamiltonian()
        data = ((1-self.spins.view(-1))/2).unsqueeze(0)
        for _ in range(num_data-1):
            self.gen_system()
            H_new = self.hamiltonian()
            p = torch.clamp(torch.exp(-(H_new-H)), max=1)
            if torch.rand(1) < p:
                v = ((1-self.spins.view(-1))/2).unsqueeze(0)
                H = H_new
            else:
                v = data[-1].unsqueeze(0)  
            data = torch.cat([data, v])
        return data

