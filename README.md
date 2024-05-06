<div align="center">
<pre>
     ██╗██╗   ██╗███╗   ███╗       ██████╗ ██████╗ ██████╗       ██╗    ██╗██████╗  █████╗ ██████╗ ██████╗ ███████╗██████╗ 
     ██║██║   ██║████╗ ████║      ██╔════╝ ██╔══██╗██╔══██╗      ██║    ██║██╔══██╗██╔══██╗██╔══██╗██╔══██╗██╔════╝██╔══██╗
     ██║██║   ██║██╔████╔██║█████╗██║  ███╗██║  ██║██████╔╝█████╗██║ █╗ ██║██████╔╝███████║██████╔╝██████╔╝█████╗  ██████╔╝
██   ██║╚██╗ ██╔╝██║╚██╔╝██║╚════╝██║   ██║██║  ██║██╔══██╗╚════╝██║███╗██║██╔══██╗██╔══██║██╔═══╝ ██╔═══╝ ██╔══╝  ██╔══██╗
╚█████╔╝ ╚████╔╝ ██║ ╚═╝ ██║      ╚██████╔╝██████╔╝██████╔╝      ╚███╔███╔╝██║  ██║██║  ██║██║     ██║     ███████╗██║  ██║
╚════╝   ╚═══╝  ╚═╝     ╚═╝       ╚═════╝ ╚═════╝ ╚═════╝        ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝     ╚══════╝╚═╝  ╚═╝
</pre>
<div align="right">

Java tool designed to facilitate debugging of native applications using GDB from within a JVM environment.

By [Teodor Neagoe](https://github.com/Teoneag)

</div>

<img src="gifs/JVM-GDB-Wrapper Preview.gif" alt="JVM-GDB-Wrapper Preview"/>
</div>

## Getting Started

### Prerequisites

- Os: Windows
- Debugger + Compiler: GDB + GCC (MinGW)
- Language: C
- Java version: 21

## Download

### 1. Clone the repository

```bash
git clone https://github.com/Teoneag/JVM-GDB-Wrapper
```

### 2. Build & run

To build it run
```bash
./gradlew build
```

And then to run it run
```bash
java -cp build/libs/JVM-GDB-Wrapper-1.0-SNAPSHOT.jar com.teoneag.Main
```

Or you can run it directly from gradle (but IO is a bit slower)

```bash
./gradlew run -q --console=plain
```

Or you can use IntelliJ IDEA to run it. (open the project and run the Main class)

## Usage

If you want to integrate the JVM-GDB-Wrapper in your project, you can use the following code to get started:

```java
String gdbGccDir = "C:\\msys64\\ucrt64\\bin";
String sourcePath = "D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.c";
String executablePath = "D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.exe";

JvmGdbWrapper jvmGdbWrapper = new JvmGdbWrapper();

jvmGdbWrapper.setGdbGccDir(gdbGccDir);

executablePath =jvmGdbWrapper.compile(sourcePath);

jvmGdbWrapper.loadFile(executablePath);

jvmGdbWrapper.setBreakpoint("file_1.c",13);

jvmGdbWrapper.setBreakHandler(() -> {
        System.out.println("Breakpoint hit with backtrace: " + jvmGdbWrapper.getBacktrace());
        jvmGdbWrapper.resume();
});

jvmGdbWrapper.run();
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

## Plan: 6h
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

## ToDo

### things to read about:

- gradle run
- simplify the cli: List<String, Function, String>, all string parsing
- git pipeline
- copyright

### refactor

- reformat project: check best practices for project structure
  - better error handling
- improve tests

### fix

- show multiple line backtrace: use some non-blocking input?
- add timeout for output
- make quit work anytime
- Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.

### features

- compile in the load step
- relative path
- info command: print all data
- remember last file used
- make help better, explain default

## ToDo after I'm accepted in the internship

- change the name of the project to GDB-Driver
- make it public