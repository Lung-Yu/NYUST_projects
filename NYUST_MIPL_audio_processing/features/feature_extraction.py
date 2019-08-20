import numpy as np

def pre_emphasis(x,a=0.95):
    x2= np.append(x[0],x[1:] - a*x[:-1])
    return x2


def sgn(val):
    if val > 0:
        return 1
    else:
        return 0

def zero_crossing_rate(s):
    zcr = 0 
    for i in range(1,len(s)):
        zcr += abs(sgn(s[i]) - sgn(s[i - 1]))

    return zcr