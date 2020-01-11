import threading
import time
'''
多线程
'''
class Mythread(threading.Thread):
    def __init__(self,n):
        super(Mythread,self).__init__()
        self.n=n
    def run(self):
        print("running task:",self.n)
        time.sleep(2)

t1 = Mythread("t1")
t2 = Mythread("t2")
t1.start()
t2.start()