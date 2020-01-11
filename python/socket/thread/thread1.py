import threading
import time
'''
多线程
'''
def run(n1):
    print("%s 执行了..." %n1)
    time.sleep(2)

start_time = time.time()

thread_list=[]
for i in range(10):
    t = threading.Thread(target=run,args=("t1-%s" %i,))
    #t.setDaemon(True)
    t.start()
    thread_list.append(t)

for t in thread_list:
    t.join()
    print("task done ",threading.current_thread(),threading.active_count())

print("all thread has finished!")
print("const:",time.time()-start_time)