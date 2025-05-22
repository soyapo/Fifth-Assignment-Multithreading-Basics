## 1. `start()` vs `run()`

#### Explanation:

###### t1.run():
This does not start a new thread, 
but simply calls the run() method 
like a regular method, so the output 
is printed from the main thread.
That's why you see ```Running in: main```.

###### t2.start():
This does start a new thread, 
but internally calls the run() 
method but on a new thread, named 
"Thread-2" in this case. So you
see ```Running in: Thread-2```.

##### Difference in behavior:
```run()```	executes the code in the current 
thread. It’s a normal method call whilst 
```start()``` Creates a new thread, which 
then executes the ```run()``` method in parallel.

___
## Daemon Threads
### 1. What output do you get from the program? Why?

The output will typically be:
```
Main thread ends.
Daemon thread running...
Daemon thread running...
... (a few more "Daemon thread running..." lines)
```

The daemon thread might print "Daemon thread running..." a few times (usually less than 20) before the program terminates. This happens because:
- Daemon threads are automatically terminated when all non-daemon threads (in this case, just the main thread) finish execution
- The main thread prints "Main thread ends." and then terminates
- The JVM then terminates the daemon thread, even if it hasn't completed its 20 iterations

### 2. What happens if you remove thread.setDaemon(true)?

If you remove `thread.setDaemon(true)`, the thread becomes a user (non-daemon) thread, and the output will be:
```
Main thread ends.
Daemon thread running...
Daemon thread running...
... (all 20 "Daemon thread running..." lines)
```
The program will wait for the thread to complete all 20 iterations before terminating, because the JVM won't exit until all non-daemon threads have finished.

### 3. What are some real-life use cases of daemon threads?

Common real-life use cases for daemon threads include:
1. **Garbage Collection**: The JVM's garbage collector runs as a daemon thread
2. **Background saving/autosave**: Like in text editors that periodically save your work
6. **Logging services**: Background threads that write logs to disk
7. **Event listeners**: That wait for system events in the background

Daemon threads are ideal for supporting tasks that should not prevent the JVM from exiting when the main program ends. They're often used for maintenance or housekeeping tasks that run in the background but aren't critical to the application's core functionality.

___

## A shorter way to create threads

### Answers:

#### 1. **What output do you get from the program?**
The output will be:  
`Thread is running using a ...!`

When the thread starts, it executes the lambda expression’s code block, which prints the message. The main thread starts the new thread and terminates almost immediately, but the new thread typically has enough time to execute the `println` statement before the program exits.

---

#### 2. **What is the `() -> { ... }` syntax called?**
This is called a **lambda expression**.
- Introduced in Java 8, it provides a concise way to implement functional interfaces (interfaces with a single abstract method, like `Runnable`).
- The `()` represents the parameters (none in this case, since `Runnable`’s `run()` method takes no arguments).
- The `-> { ... }` defines the implementation of the `run()` method.

---

#### 3. **How is this code different from creating a class that extends `Thread` or implements `Runnable`?**
This approach differs in three key ways:

| **Approach**                | **Lambda (Example Code)**                          | **Extending `Thread`**                      | **Implementing `Runnable`**                |
|------------------------------|----------------------------------------------------|---------------------------------------------|---------------------------------------------|
| **Code Length**              | Short and concise (avoids boilerplate).            | Requires a subclass with `run()` override.  | Requires a class implementing `Runnable`.   |
| **Flexibility**              | Directly defines behavior inline.                 | Ties the thread logic to a specific class.  | Separates thread logic from the `Thread` class. |
| **Best Practice**            | Preferred for simple tasks (avoids class clutter). | Not recommended (inheritance is less flexible). | Traditional approach (pre-lambda).          |
| **Functional Programming**   | Uses lambda to implement `Runnable`.               | Imperative class-based approach.            | Can also use anonymous inner classes.       |

##### Key Advantages of Lambda:
- **Brevity**: No need to create a separate class or anonymous inner class.
- **Readability**: Logic is defined inline where it’s used.
- **Modern**: Aligns with Java’s functional programming features.

##### Example Comparison:
**Lambda (from the code):**
```java
Thread thread = new Thread(() -> { 
    System.out.println("Thread is running using a lambda!"); 
});
```

**Anonymous Inner Class (Equivalent):**
```java
Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("Thread is running using an anonymous class!");
    }
});
```

**Extending `Thread` (Not Recommended):**
```java
class CustomThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread is running by extending Thread!");
    }
}

// Usage:
Thread thread = new CustomThread();
```

**Implementing `Runnable` (Traditional):**
```java
class CustomRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread is running by implementing Runnable!");
    }
}

// Usage:
Thread thread = new Thread(new CustomRunnable());
```

---

#### Summary:
The lambda-based approach is the most concise and modern way to define thread behavior in Java for simple tasks, avoiding unnecessary boilerplate code. It leverages functional programming principles and is preferred over older approaches like extending `Thread` or using anonymous inner classes.