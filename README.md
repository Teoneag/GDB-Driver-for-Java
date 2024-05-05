<div style="text-align: center;">
<pre>
     ██╗██╗   ██╗███╗   ███╗       ██████╗ ██████╗ ██████╗       ██╗    ██╗██████╗  █████╗ ██████╗ ██████╗ ███████╗██████╗ 
     ██║██║   ██║████╗ ████║      ██╔════╝ ██╔══██╗██╔══██╗      ██║    ██║██╔══██╗██╔══██╗██╔══██╗██╔══██╗██╔════╝██╔══██╗
     ██║██║   ██║██╔████╔██║█████╗██║  ███╗██║  ██║██████╔╝█████╗██║ █╗ ██║██████╔╝███████║██████╔╝██████╔╝█████╗  ██████╔╝
██   ██║╚██╗ ██╔╝██║╚██╔╝██║╚════╝██║   ██║██║  ██║██╔══██╗╚════╝██║███╗██║██╔══██╗██╔══██║██╔═══╝ ██╔═══╝ ██╔══╝  ██╔══██╗
╚█████╔╝ ╚████╔╝ ██║ ╚═╝ ██║      ╚██████╔╝██████╔╝██████╔╝      ╚███╔███╔╝██║  ██║██║  ██║██║     ██║     ███████╗██║  ██║
╚════╝   ╚═══╝  ╚═╝     ╚═╝       ╚═════╝ ╚═════╝ ╚═════╝        ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝     ╚══════╝╚═╝  ╚═╝
</pre>
<div style="text-align: right;">

Java tool designed to facilitate debugging of native applications using GDB from within a JVM environment.
By [Teodor Neagoe](https://github.com/Teoneag)

</div>
<img src="gifs/JVM-GDB-Wrapper Preview.gif" width="400" alt="JVM-GDB-Wrapper Preview"/>
</div>

## Getting Started

### Prerequisites

- Os: Windows
- Debugger + Compiler: GDB + GCC
- Language: C
- Java version: 21

## Download

1. ### Clone the repository

```bash
git clone https://github.com/Teoneag/JVM-GDB-Wrapper
```

2. ### Build & run

```bash
./gradlew run -q --console=plain
```

Or alternatively, you can first build it

```bash
./gradlew build
```

And then run it directly

```bash
java -cp build/libs/JVM-GDB-Wrapper-1.0-SNAPSHOT.jar com.teoneag.Main
```

## Usage

If you want to integrade the JVM-GDB-Wrapper in your project, you can use the following code to get started:

```java
String gdbGccDir = "C:\\msys64\\ucrt64\\bin";
String sourcePath = "D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.c";
String executablePath = "D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.exe";

JvmGdbWrapper jvmGdbWrapper = new JvmGdbWrapper();

jvmGdbWrapper.

setGdbGccDir(gdbGccDir);

executablePath =jvmGdbWrapper.

compile(sourcePath);

jvmGdbWrapper.

loadFile(executablePath);

jvmGdbWrapper.

setBreakpoint("file_1.c",13);

jvmGdbWrapper.

setBreakHandler(() ->{
        System.out.

println("Breakpoint hit with backtrace: "+jvmGdbWrapper.getBacktrace());
        jvmGdbWrapper.

resume();
});

        jvmGdbWrapper.

run();
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

## ToDo
- extra
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
- set up running with gradle + upload: 15m -> 30m
- extra
  - show output from gdb: 30m

## ToDo Later
- fix
  - Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.
- things to read about:
  - Java version: how to choose
  - project name: driver vs wrapper
  - readme.md: good practices + write ToDo here or use issues
    - license
    - acknowledgements
  - gradle run
- git pipeline
- simplify the cli: List<String, Function, String>, all string parsing
- make help better, explain default
- improve tests