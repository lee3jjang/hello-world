import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
sns.set(rc={"figure.figsize":(6,6)})
np.random.seed(sum(map(ord,"palettes")))
current_palette = sns.color_palette()
sns.palplot(current_palette)
sns.palplot(sns.color_palette("hls",8))
sns.palplot(sns.hls_palette(8, l=0.3, s=0.8))
sns.palplot(sns.color_palette("husl",20))
sns.palplot(sns.color_palette("Paired",20))
sns.palplot(sns.color_palette("Set2",20))

flatui = ["#9b59b6", "#3498db", "#95a5a6", "#e74c3c", "#34495e", "#2ecc71"]
sns.palplot(sns.color_palette(flatui))

plt.plot([0,1],[0,1], color=sns.xkcd_rgb["pale red"], lw=3)
plt.plot([0,1],[0,2], color=sns.xkcd_rgb["medium green"], lw=3)
plt.plot([0,1],[0,3], color=sns.xkcd_rgb["denim blue"], lw=3)

colors = ["windows blue", "amber", "greyish", "faded green", "dusty purple"]
sns.palplot(sns.xkcd_palette(colors))
sns.palplot(sns.color_palette("Blues",20))
sns.palplot(sns.color_palette("GnBu_d",10))
sns.palplot(sns.color_palette("cubehelix",10))
sns.palplot(sns.cubehelix_palette(20))
sns.palplot(sns.cubehelix_palette(8,start=2,rot=0,dark=0,light=0.95, reverse=True))

x, y = np.random.multivariate_normal([0,0], [[1,-0.5],[-0.5,1]], size=300).T
cmap = sns.cubehelix_palette(light=1, as_cmap=True)
sns.kdeplot(x,y, cmap=cmap, shade=True)

pal = sns.dark_palette("palegreen", as_cmap=True)
sns.kdeplot(x,y,cmap=pal)
