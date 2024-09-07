package com.teoneag;

public class UsageExample {
    /**
     * Usage example for the GdbDriver class
     * The command send to the terminal
     * gdb file_1.exe
     * break file_1.c:13
     * run
     * bt
     * continue
     *
     * @param args none
     */
    public static void main(String[] args) {
        GdbDriver gdbDriver = new GdbDriver();

        gdbDriver.test();

        String exePath = gdbDriver.compile("D:\\working\\GDB-Driver\\src\\main\\resources\\file_1.c");

        gdbDriver.loadFile(exePath);

        gdbDriver.setBreakpoint("file_1.c", 13);

        gdbDriver.setBreakHandler(() -> {
            System.out.println("Breakpoint hit with backtrace: " + gdbDriver.getBacktrace());
            gdbDriver.resume();
        });

//        ToDo use in case smth goes wrong
//        gdbDriver.setShowOutput(true);

        gdbDriver.run();
    }
}

