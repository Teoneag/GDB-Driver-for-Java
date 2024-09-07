package com.teoneag;

public class Main {
    private static final GdbDriver gdbDriver = new GdbDriver();

    /**
     * Main method - CLI application entry point for the GdbDriver class
     * Usage example
     * help
     * init C:\MinGW\bin
     * test
     * comp res\file_1.c
     * load D:\working\GDB-Driver\src\main\resources\file_1.exe
     * break file_1.c 13
     * start
     * handle manual
     * start
     * help
     * backtrace
     * resume
     * output on
     * start
     * help
     * backtrace
     * exit
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Shell shell = new Shell(Main.class, "GDB Driver");

        shell.start();
    }

    @Command(name = "load", description = "Load a file to debug")
    public void load(String filePath) {
        gdbDriver.loadFile(filePath);
    }

    @Command(name = "init", description = "Set the folder path where the gdb.exe, gcc.exe are located")
    public void init(String folderPath) {
        gdbDriver.setGdbGccDir(folderPath);
    }

    @Command(name = "gdb", description = "Set the GDB path (include the name of the executable)")
    public void gdb(String gdbPath) {
        System.out.println("Setting GDB path to " + gdbPath);
        gdbDriver.setGdbPath(gdbPath);
        System.out.println("GDB path set to " + gdbPath);
    }

    @Command(name = "gcc", description = "Set the GCC path (include the name of the executable)")
    public void gcc(String gccPath) {
        gdbDriver.setGccPath(gccPath);
        System.out.println("GCC path set to " + gccPath);
    }

    @Command(name = "test", description = "Check for the GDB and GCC versions")
    public void test() {
        System.out.println("Testing GDB and GCC versions...");
        gdbDriver.test();
        System.out.println("Test complete.");
    }

    @Command(name = "comp", description = "Compile a C file, the executable will be filePath.exe")
    public void comp(String filePath) {
        System.out.println("Compiling " + filePath + "...");
        gdbDriver.compile(filePath);
    }

    @Command(name = "compile", description = "Compile a C file")
    public void compile(String source, String destination) {
        System.out.println("Compiling " + source + " to " + destination + "...");
        gdbDriver.compile(source, destination);
    }

    @Command(name = "reset", description = "Reset all settings")
    public void reset() {
        gdbDriver.reset();
        System.out.println("Debugger reset.");
    }

    @Command(name = "start", description = "Start the debugger")
    public void start() {
        System.out.println("Debugging started...");
        gdbDriver.run();
        System.out.println("Debugging finished.");
    }

    @Command(name = "backtrace", description = "Show the backtrace")
    public void backtrace() {
        System.out.println(gdbDriver.getBacktrace());
    }

    @Command(name = "output", description = "Enable or disable the debugger output")
    public void output(String outputMode) {
        if (outputMode.equals("on")) {
            gdbDriver.setShowOutput(true);
            System.out.println("Output enabled.");
            return;
        }
        if (outputMode.equals("off")) {
            gdbDriver.setShowOutput(false);
            System.out.println("Output disabled.");
            return;
        }
        System.out.println("Invalid option. Please use 'on' or 'off'.");
    }

    @Command(name = "break", description = "Set a breakpoint")
    // ToDo add instructions on what parameters to use???
    public void setBreakpoint(String fileName, int lineNumber) {
        gdbDriver.setBreakpoint(fileName, lineNumber);
    }

    @Command(name = "handle", description = "Chose from auto | manual breakpoint handeling")
    public void handle(String handleMode) {
        if (handleMode.equals("auto")) {
            gdbDriver.setBreakHandler(() -> {
                System.out.println("Breakpoint hit with backtrace: " + gdbDriver.getBacktrace());
                gdbDriver.resume();

            });
            return;
        }

        if (!handleMode.equals("manual")) {
            System.out.println("Invalid handler. Please use 'auto' or 'manual'.");
            return;
        }

        gdbDriver.setBreakHandler(() -> {
            System.out.println("Breakpoint hit. Press bt to print the backtrace or resume to continue.");
            // ToDo fix this output message
            Shell breakPointShell = new Shell(BreakPointCommands.class);

            breakPointShell.start();
        });
    }

    static class BreakPointCommands {
        @Command(name = "resume", description = "Resume the debugger", stop = true)
        public void resume() {
            System.out.println("Resuming debugger...");
            gdbDriver.resume();
        }

        @Command(name = "backtrace", description = "Show the backtrace")
        public void backtrace() {
            System.out.println(gdbDriver.getBacktrace());
        }
    }
}

