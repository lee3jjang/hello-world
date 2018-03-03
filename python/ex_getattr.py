class sangjin():
    def __init__(self):
        self.name = "sangjin"
        self.age = 29
        self.height = 173
        self.weight = 80
    def crawl(self):
        print("wal wal!")
        


x = sangjin()
if hasattr(x,"crawl"):
    getattr(x,"crawl")()
