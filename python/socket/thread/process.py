import multiprocessing,time,os
from multiprocessing import Process
from multiprocessing import Queue  #进程queues队列，用来进程间的通信
from multiprocessing import Pipe  #通过sokect实现了进程间的通信
from multiprocessing import managers #进程间的数据共享
from multiprocessing import Lock #进程锁，
from multiprocessing import RLock
from multiprocessing import Pool #进程池
import greenlet #协程 封装了yield操作 手动切换
import gevent #自动切换
'''
多进程
'''
def run(name):
    time.sleep(2)
    print("running:",name)

if __name__ =="__main__":
    for i in range(10):
        p = multiprocessing.Process(target=run,args=("bob %s" %i,))
        p.start()