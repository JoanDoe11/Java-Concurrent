# Java-Concurrent

Mutual Exclusion - only one thread or process can execute a block of code (critical section) at a time.

Visibility - changes made by one thread to shared data are visible to other threads.

Cases when you don't need any synchronization mechanism:
* fields that are immutable (declared final)
* variables that are accessed by only one thread

In Java, each thread has a separate memory space known as working memory; this holds the values of different variables used for performing operations. After performing an operation, thread copies the updated value of the variable to the main memory, and from there other threads can read the latest value.
In the situations where the next value of the variable is dependent on the previous value, there is a chance that multiple threads reading and writing the variable may go out of sync, due to a time gap between the reading and writing back to the main memory.

# Deamon
  A deamon thread is a thread that does not prevent the JVM from exiting when the program finishes but the thread is still running.
  When a new thread is created it inherits the deaom status of its parent.
  When all non-deamon threads finish, the JVM halts, and any remaining deamon threads are abandoned. Finally blocks are not executed. Stacks are not unwound - the JVM just exites. Due to this reason deamon threads should be used sparingly, and it is dangerous to use them for tasks that might perform any sort of I/O.
  
  Deamon threads are like service providers for other threads or objects running in the same process as the deamon thread. Deamon threads are used for background supporting tasks and are only needed while normal threads are executing. If the normal threads are not running and remaining threads are deamon threads, then the interpreter exits.

# Volatile
  Using the volatile keyword is a way of making class thread safe - that a method or class instance can be used by multiple threads at the same time without any problem. Simply put, the volatile keyword makes a variable to always go to main memory, for both reads and writes.
  If two threads run on different processors each thread may have its own local copy of a shared variable. If one thread modifies its value the change might not reflect in the original one in the main memory instantly. This depends on the write policy of cache. Now the other thread is not aware of the modified value which leads to data inconsistency.
  Volatile variables have the visibility features of synchronized but not the atomicity features.
  The values of volatile variable will never be cached and all writes and reads will be done to and from the main memory.
  
  The most typical case of use is where you write a variable, such as a flag, in one thread, and you check that variable in another thread. Crucially, the value to write doesn't depend on the current value or you don't care about missing an update.
  
  In general, where you need atomic access to a "on-off" variable or one created a fairly small number of times, then the Java atomic classes are your answer. But if you're creating a large number of instances of an object containing a field that needs atomic access, using a volatile field and accessing it via an AtomicReferenceFieldUpdater will generally be more efficient.
  
  Volatile is not suitable for complex operations where you need to prevent access to a variable for the duration of the operation. In such cases, you should use object synchronization or lock classes.
  
  * If you feel that all reader threads always get latest value of a variable, you have to mark variable as volatile.
  * If you have one writer thread to modify the value of variable and multiple reader threads to read the value of variable, volatile modifier guarantees memory consistency. 
  * If you have multiple threads to write and read variables, volatile modifier alone does not guaranty memory consistency. You have to synchroniye the code or use high level concurrency constructs.

  
# Synchronized
  Synchronized blocks in Java are synchronized on some object. All synchronize blocks synchronized on the same object can only have one thread executing inside them at a time. All other threads attempting to enter the synchronized block are blocked until the thread inside the synchronized block exits the block.
  
    synchronized (sync_object) {
        // Access shared variables and other shared resources
    }
  sync_object is a reference to an object whose lock associates with the monitor. The code is said to be synchronized on the monitor object. Java will use the hash code of the sync_object as the ID for the critical section. For that reason, sync_object should be immutable so we can know for sure that its hash code wont change.
  
  We can also use the keyword synchronized on methods, and then the key is "this", but we don't need to reference it.
  
  Only one thread can own a monitor at a given time. When a thread acquires a lock, it is said to have entered the monitor.

  Java's synchronized keyword guarantees both mutual exclusion and visibility.
  If we make the blocks of threads that modifies the value of shared variable synchronized only one thread can enter the block and changes made by it will be reflected in the main memory. All other threads trying to enter the block at the same time will be blocked and putto sleep.
  In some cases, we may only desire the visibility and not atomicity. Use of synchronized in such situation is an overkill and may cause scalability problems.
  
  
  One tool we can use to coordinate actions of multiple threads in Java is guarded blocks. Such blocks keep a check for a particular condition before resuming the execution.
  * wait()
      Waits for a condition to occur. Susspending.
  * notify()
      Notifies a thread that is waiting for a condition that the condition has occured. Waking up.
  When we call wait() it forces the current thread to wait until some other thread invokes notify() or notifyAll() on the same object. For this, the current thread must own the object's monitor. This can happen when:
      - we've executed synchronized instance method for the given object
      - we've executed the body of a synchronized block on the given object
      - by executing synchronized static methods for objects of type Class
  
Difference between notify() and notifyAll():
    For all threads waiting on this object's monitor the method notify() notifies any one of them to wake up arbitrarily. The choice of exactly which thread to wake is non-deterministic and depends upon the implementation. Since notify() wakes up a single random thread it can be used to implement mutually exclusive locking where threads are doing similar tasks, but in most cases, it would be more viable to implement notifyAll().
    NotifyAll() simply wakes all threads that are waiting on this object's monitor. The awekened threads will complete in the usual manner - like any other thread. But before we allow their execurion to continue, always define a quick check for the condition required to proceed with the thread - because there may be some situations where the thread got woken up without receiving a notification.
  
# Locks
  Instead of using implicit locking via the synchronized keyword, the Concurrency API supports various explicit locks specified by the Lock interface. Locks support various methods for finer grained lock control thus are more expressive than implicit monitors.
  * ReentrantLock - a mutual exclusion lock with the same basic behavior as the implicit monitors accessed via the synchronized keyword, but with extended capabilities.
  * ReadWriteLock - an advanced thread lock mechanism. It allows multiple threads to read a certain resource, but only one to write it, at a time. The rules by which a thread is allowed to lock the ReadWriteLock either for reading or writing the guarded resource, are as follows:
      - read lock: if no threads have locked for writing, and no thread have requested a write lock (but not yet obtained it). Thus, multiple threads can lock the lock for reading.
      - write lock: if no threads are reading or writing. Thus, only one thread at a time can lock the lock for writing.
  * CountDownLatch - has a counter field, which you can decrement as you require. You can then use it to block a calling thread until it's been counted down to zero. If we were doing some parallel processing, we could instantiate the latch with the same value for the counter as a number of threads we want to work across. Then, we could just call countdown() after each thread finishes, guaranteeing that a dependent thread calling await() will block until the worker threads are finished.
  * CyclicBarrier - a synchronizer that allows a set of threads to wait for each other to reach a common execution point, also called a barrier. CyclicBarriers are used in programs in which we have a fixed number of threads that must wait for each other to reach a common point before continuing execution.
  
# Condition
  A java.util.concurrent.locks.Condition interface provides a thread ability to suspend its execution, until the given condition is true. 
  A Condition object is bound to a Lock and is obtained using the newCondition() method.
  Any calls to change the condition variab
  les do need to be within a synchronized region.
  
# Exchanger
  A synchronization point at which threads can pair and swap elements within pairs.
  Each thread presents some object on entry to the exchange method, matches with a partner thread, and receives its partner's object in return.
  
  Exchanger waits until two separate threads call its exchange() method and then it swaps the objects represented by the threads.
  
# Future
  The Future interface is a generic interface that represents the value returned from an asynchronous computation. 
  It contains methods to check if the computation has been completed, to wait for it and to retreive the result.
  
  The result can only be retreived using method get() when the computation has completed, blocking if necessary until it is ready.
  
  This interface also contains methods to cancel Callable's execution. However, once the computation has been completed, it cannot be canceled.
  
# Atomic
  A small toolkit of classes that support lock free thread safe programming on single variables.
  In essence, the classes in this package extend the notion of volatile values, fields and array elements to those that also provide an atomic conditional update operation of the form:
        boolean compareAndSet(expectedValue, updateValue);
  This method atomically sets a variable to the updateValue if it currently holds the expectedValue, reporting true on success.
  
  A very commonly occurring pattern in programs and concurrent algorithms is the "check then act" pattern. This pattern occurs when the code first checks the value of a variable and then acts based on that value. To work properly in a multithreaded application, "check then act" operations must be atomic. Any thread that start executing this atomic lock of code will finish executing it without interference from other threads. No other threads can execute the atomic block at the same time.
  
# Exponential backoff
  An algorithm that uses feedback to multiplicatively decrease the rate of some process, in order to gradually find an acceptable rate.
  We start at some relatively small pause, and then double the amount at every awakening. It's also good practice to have some upper limit.

# Mutex
  The simplest solution for the race condition.
  
  Critical section is the code part that modifies some memory value that is shared between threads.
  
  * lock() - start of the critical section. If there is more than one thread, the one that succeeded the lock, should block all the others.
  * unlock() - allows one new thread to go through "lock()" / reinitializes critical section state
  
# Semaphore
  A semaphore controls access to a shared resource through the use of a counter. If the counter is greater than zero, then access is allowed. If it is zero, then access is denied.
  What the counter is counting are permits that allow access to the shared resource. Thus, to access the resource, a thread must be granted a permit from the semaphore.
  
  In general, to use a semaphore, the thread that wants access to the shared resource tries to acquire a permit.
  * if the semaphore's count is greater than zero, then the thread acquires a permit, which causes the semaphore's count to be decremented.
  * otherwise, the thread will be blocked until a permit can be acquired
  When the thread no longer needs access to the shared resource, it releases the permit, which causes the semaphore's count to be incremented. If there is another thread waiting for a permit, then that thread will acquire a permit at that time.
  
# Thread pool
  In Java, threads are mapped to system-level threads which are operating system's resources. If you create threads uncontrollably, you may run out of these resources quickly.
  The context switching between threads is done by the OS as well - in order to emulate parallelism. A simplistic view is that - the more threads you spawn, the less time each thread spends doing actual work.
  
  The Thread pool pattern helps to save resources in a multithreaded application, and also to contain the parallelsim in certain predefined limits. When you use a thread pool, you write your concurrent code in the form of parallel tasks and submit them for exectution to an instance of a thread pool. This instance controls several re-used threads for executing these tasks.
  The pattern allows you to control the number of threads the application is creating, their lifecycle, as well as to schedule tasks' execution and keep incoming tasks in a queue.
  
# Executor
  ExecutorService is a framework provided by the JDK which simplifies the execution of tasks in asynchronous mode. Generally speaking, ExecutorService automatically provides a pool of threads and API for assigning tasks to it.
  ```
  ExecutorService exevutor = Executors.newFixedThreadPool(n);
  ```
  
  ExecutorService can execute Runnable and Callable tasks. 
  Methods:
  * execute() - doesn't give any possibility to get the result of task's execution or to check the task's status
  * submit() - submits a Callable or a Runnable task to an ExecutorService and returns a result of type Future
  * invokeAny() - assigns a collection of tasks to an ExecutorService, causing each to be executed, and returns the result of a successful execution of one task.
  * invokeAll() - assigns a collection of tasks to an ExecutorService, causing each to be executed, and returns the result of all task executions in the form of a list of objects of type Future.
  
  In general, the ExecutorService will not be automatically destroyed when there is no tasks to process. It will stay alive and wait for new work to do. To properly shut down an ExecutorService, we have the shutdown() and shutdownNow() APIs.
  * shutdown() - doesn't cause an immediate destruction of the ExecutorService. It will make the ExecutorService stop accepting new tasks and shut down after all running threads finish their current work.
  * shutdownNow() - tries to destroy the ExecutorService immediately, but doesn't guarantee that all running threads will be stoopped at the same time. This method returns a list of tasks which are waiting to be processed. It is up to the developer to decide what to do with these tasks.
  One good way to shut down the ExecutorService is to use both of these methods combined with the awaitTermination() method. With this approach, the ExecutorService will first stop taking new tasks, the wait up to a specified period of time for all tasks to be completed. If that time expires, the execution is stopped immediately.
  
  
# Fork/Join
  The fork/join framework is an implementation of the ExecutorService interface that helps you take advantage of multiple processors. It is designed for work that can be broken into smaller pieces recursively. The goal is to use all the available processing power to enhance the performance of your application.
  
  This framework distributes tasks to worker threads in a thread pool. It is distinct because it uses a work-stealing algorithm - worker threads that run out of things to do can steal tasks from other threads that are still busy.
  
# Cancellation
  An activity is cancellable if external code can move it to completion before its normal completion. 
  
  When using the Executor framework, you can interrupt a specific task without shutting down the ExecutorService. On submitting a task to the service an instance of Future<> is returned by the service. You may call the cancel() method on that instance to interrupt the task. 
  
  
