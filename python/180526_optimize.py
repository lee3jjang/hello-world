def f(x, *args):
    return x[0]**2 +x[1]**2 + args[0] - args[1]
minimum = fmin(f, x0=[-10,10], args=(2,3), maxiter=100, disp=True, retall=True)
print(minimum[0])
