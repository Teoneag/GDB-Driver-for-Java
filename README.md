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
- set up running with gradle + upload: 15m
- extra
  - show output from gdb: 30m
  - relative path
  - copyright
  - format README.md (explain planned vs actual time)
  - info command: print all data

## Done: 6h
- understand task + read resources, similar projects + make plan: 1:30h
- checkstyle: 10m
- install + play with GDB: 15m -> 30m
- class
  - design api: 10m
  - implement: 2h -> 3h
  - test: 30m
- CLI app
  - implement: 30m -> 1h
  - test: 10m

## ToDo Later
- fix
  - Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.
- things to read about:
  - Java version: how to choose
  - project name: driver vs wrapper
  - readme.md: good practices + write ToDo here or use issues
- git pipeline
- simplify the cli: List<String, Function, String>, all string parsing
- make help better, explain default
- improve tests