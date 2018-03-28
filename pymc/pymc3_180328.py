
# coding: utf-8

# In[17]:


import pymc3 as pm
import numpy as np

n = np.ones(4)*5
y = np.array([0,1,3,5])
dose = np.array([-.86,-.3,-.05,.73])

with pm.Model() as bioassay_model:
    
    # Prior
    alpha = pm.Normal('alpha', 0, sd=100)
    beta = pm.Normal('beta', 0, sd=100)
    
    # Linear combiations of parameters
    theta = pm.invlogit(alpha + beta*dose)
    
    # Model likelihood
    deaths = pm.Binomial('deaths', n=n, p=theta, observed=y)

