# Java-Concurrent


# Mutex
  The simplest solution for the race condition.
  
  Critical section is the code part that modifies some memory value that is shared between threads.
  
  * lock() - start of the critical section. If there is more than one thread, the one that succeeded the lock, should block all the others.
  * unlock() - allows one new thread to go through "lock()" / reinitializes critical section state
