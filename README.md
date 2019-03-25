# Java-Concurrent

Mutual Exclusion - only one thread or process can execute a block of code (critical section) at a time.
Visibility - changes made by one thread to shared data are visible to other threads.

Cases when you don't need any synchronization mechanism:
* fields that are immutable (declared final)
* variables that are accessed by only one thread

In Java, each thread has a separate memory space known as working memory; this holds the values of different variables used for performing operations. After performing an operation, thread copies the updated value of the variable to the main memory, and from there other threads can read the latest value.

# Volatile
  Using the volatile keyword is a way of making class thread safe - that a method or class instance can be used by multiple threads at the same time without any problem. Simply put, the volatile keyword makes a variable to always go to main memory, for both reads and writes.
  If two threads run on different processors each thread may have its own local copy of a shared variable. If one thread modifies its value the change might not reflect in the original one in the main memory instantly. This depends on the write policy of cache. Now the other thread is not aware of the modified value which leads to data inconsistency.
  Volatile variables have the visibility features of synchronized but not the atomicity features.
  The values of volatile variable will never be cached and all writes and reads will be done to and from the main memory.
  
  The most typical case of use is where you write a variable, such as a flag, in one thread, and you check that variable in another thread. Crucially, the value to write doesn't depend on the current value or you don't care about missing an update.
  
  In general, where you need atomic access to a "on-off" variable or one created a fairly small number of times, then the Java atomic classes are your answer. But if you're creating a large number of instances of an object containing a field that needs atomic access, using a volatile field and accessing it via an AtomicReferenceFieldUpdater will generally be more efficient.
  
  Volatile is not suitable for complex operations where you need to prevent access to a variable for the duration of the operation. In such cases, you should use object synchronization or lock classes.

  
# Synchronized
  Java's synchronized keyword guarantees both mutual exclusion and visibility.
  If we make the blocks of threads that modifies the value of shared variable synchronized only one thread can enter the block and changes made by it will be reflected in the main memory. All other threads trying to enter the block at the same time will be blocked and putto sleep.
  In some cases, we may only desire the visibility and not atomicity. Use of synchronized in such situation is an overkill and may cause scalability problems.

# Mutex
  The simplest solution for the race condition.
  
  Critical section is the code part that modifies some memory value that is shared between threads.
  
  * lock() - start of the critical section. If there is more than one thread, the one that succeeded the lock, should block all the others.
  * unlock() - allows one new thread to go through "lock()" / reinitializes critical section state
