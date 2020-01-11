import threading
import time
'''
多线程 普通锁
'''
def run(n1):
    global num
    lock.acquire()
    num +=1
    time.sleep(0.1)
    lock.release()

lock = threading.Lock()

num =0
thread_list=[]
for i in range(100):
    t = threading.Thread(target=run,args=("t1-%s" %i,))
    t.start()
    thread_list.append(t)

for t in thread_list:
    t.join()

print("all thread has finished!")
print("num:",num)