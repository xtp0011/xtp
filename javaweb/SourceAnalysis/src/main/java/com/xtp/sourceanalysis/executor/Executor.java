package com.xtp.sourceanalysis.executor;

/**
 * 线程池
 *
 1，newSingleThreadExecutor
 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，
 保证所有任务按照指定顺序（FIFO，LIFO，优先级）执行。

 2，newFixedThreadPool
 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。


 3，newCachedThreadPool
 创建一个可缓存线程池，如果线程池长度超过处理需要，
 可灵活回收空闲线程，若无可回收，则新建线程。

 4，newScheduledThreadPool
 创建一个定长线程池，支持定时及周期性任务执行。


 1）newFixedThreadPool和newSingleThreadExecutor：
   主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM.
 2）newCachedThreadPool和newScheduledThreadPool：
   主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。



 */
public class Executor {


}
