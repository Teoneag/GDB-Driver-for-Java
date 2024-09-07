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

## ToDo

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

- compile in the load step
- relative path
- cli
  - add custom input to the gdb
  - init: when open the app ask for the path to the gdb, then make a separate settings page
  - info command: print all data
  - remember last file used
  - make help better, explain default