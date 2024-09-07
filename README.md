<div align="center">
<pre>
 ██████╗ ██████╗ ██████╗     ██████╗ ██████╗ ██╗██╗   ██╗███████╗██████╗ 
██╔════╝ ██╔══██╗██╔══██╗    ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔══██╗
██║  ███╗██║  ██║██████╔╝    ██║  ██║██████╔╝██║██║   ██║█████╗  ██████╔╝
██║   ██║██║  ██║██╔══██╗    ██║  ██║██╔══██╗██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
╚██████╔╝██████╔╝██████╔╝    ██████╔╝██║  ██║██║ ╚████╔╝ ███████╗██║  ██║
 ╚═════╝ ╚═════╝ ╚═════╝     ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝
</pre>
<div align="right">

Java tool designed to facilitate debugging of native applications using GDB from within a JVM environment.

By [Teodor Neagoe](https://github.com/Teoneag)

</div>

<img src="gifs/GDB-Driver Preview.gif" alt="GDB-Driver Preview"/>
</div>

## Getting Started

### 0. Prerequisites

- Os: Windows
- Debugger + Compiler: GDB + GCC (MinGW) for the C language
- Java version: 22
- Simple CLI library - for now, it is not published, but you can
    1. clone the repository from [here](https://github.com/Teoneag/Simple-CLI_Java)
    2. publish it to your local maven repository

### 1. Clone the repository

```bash
git clone https://github.com/Teoneag/GDB-Driver
```

### 2. Build & run

```bash
./gradlew run -q --console=plain
```

Or you can use IntelliJ IDEA to run it. (open the project and run the Main class)

### 3. Run commands

By default, it has everything set up, and you can run it. But here is a normal flow:

0. See all the available commands
```bash
help
```

1. Set the path to the GDB compiler
```bash
init C:\MinGW\bin
```

2. Test the debugger
```bash
test
```

3. Compile a file
```bash
comp res\file_1.c
```

4. Load the compiled file
```bash
load res\file_1.exe
```

5. Set a breakpoint
```bash
break file_1.c 13
```

6. Run the debugger
```bash
start
```
As you can see, the debugger stopped at the breakpoint. By default, it showed the backtrace, then it resumed the execution.
But you may want to manually handle breakpoints. You can do that by 

7. Set the breakpoint handler to manual
```bash
handle manual
```
Now you can test it again
```bash
start
```
Now you are inside the breakpoint. You can

8. See the commands available in the breakpoint
```bash
help
```

9. Print the backtrace
```bash
backtrace
```

10. Resume the execution
```bash
resume
```

11. Quit the debugger
```bash
exit
```

12. You have many other commands available. Use help to learn more about them. Have fun!


## Usage

If you want to integrate the GDB-Driver in your project, you can use the following code to get started:

```java
String gdbGccDir = "C:\\MinGW\\bin";
String sourcePath = "D:\\working\\GDB-Driver\\src\\main\\resources\\file_1.c";
String executablePath = "D:\\working\\GDB-Driver\\src\\main\\resources\\file_1.exe";

GdbDriver gdbDriver = new GdbDriver();

gdbDriver.setGdbGccDir(gdbGccDir);

executablePath =gdbDriver.compile(sourcePath);

gdbDriver.loadFile(executablePath);

gdbDriver.setBreakpoint("file_1.c",13);

gdbDriver.setBreakHandler(() -> {
        System.out.println("Breakpoint hit with backtrace: " + gdbDriver.getBacktrace());
        gdbDriver.resume();
});

gdbDriver.run();
```

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

## Plan: 6h + done
Chronological order. Planned time -> actual time
- understand task + read resources, similar projects: 1:30h
plan project
- project setup
  - checkstyle: 10m
  - install + play with GDB: 15m -> 30m
  - set up running with gradle: 15m -> 30m
- class
  - design api: 10m
  - implement: 2h -> 3h
  - test + bugfix: 30m
- CLI app
  - implement: 30m -> 1h
  - test + bugfix: 10m
- README.md
- Extra
  - show output from gdb: 30m
- fix stopping (test usageExample)
- fix start
- why cd sometimes doesn't work on cmd? + shortcut intelij focus terminal
- fix hitting manual breakpoint not working
- in the manual debugger, it shows exit: exits the program
- make quit work anytime
- Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.
- change the name of the project to GDB-Driver
- make it public
- fix readme gif
- relative path: 15m -> 38m

## ToDo

- fix compiling (not working on gali's machine)

- check all outputs for all commands
- custom goodbye message
- make documentation

### Refactor

- better error handling
- improve tests - make them work outside my dir (make the testing somehow download the gdb or mock it)

### Fix

- showing all the commands on main (modify the cli or the code here?)
- show multiple line backtrace: use some non-blocking input?
- add timeout for output

### Features

- download all requirements automatically (gdb, gcc)
- compile in the load step
- cli
  - add custom input to the gdb
  - init: when open the app ask for the path to the gdb, then make a separate settings page
  - info command: print all data
  - remember last file used
  - make help better, explain default