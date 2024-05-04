## Run
### Os: Windows
### Debugger: GDB
### Language: C

## Task
Implement a class that allows debugging native applications from a JVM program.
  - specify debugger
  - set breakpoints
  - print the backtrace when execution is stopped
  - resume execution
- sample CLI application
- submit in a private GitHub repository - grant access to https://github.com/fastasturtle
- build with Gradle
- choices:
  - naming conventions
  - threading
  - error handling
  - tests
  - extend functionality: stepping, watches, interactive mode
- example 
```java
var driver = new DebuggerDriver("/usr/bin/gdb");
driver.load("/path/to/debuggee");
driver.setBreakpoint("file.c", 6);
driver.setBreakHandler(() -> {
System.out.println(driver.getBackTrace());
driver.resume();
});
driver.run();
```


## ToDo
- checkstyle: 10m
- install + play with GDB: 15m
- class
  - design api: 10m
  - implement: 2h
  - test: 30m
- CLI app
  - implement: 30m
  - test: 10m
- format README.md: 15m

## Done
- understand task + read resources, similar projects + make plan: 1:30h

## ToDo Later
- fix
  - Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.
- things to read about:
  - Java version: how to choose
  - project name: driver vs wrapper
  - readme.md: good practices + write ToDo here or use issues