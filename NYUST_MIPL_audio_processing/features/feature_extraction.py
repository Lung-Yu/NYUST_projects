import numpy as np

def pre_emphasis(x,a=0.95):
    x2= np.append(x[0],x[1:] - a*x[:-1])
    return x2