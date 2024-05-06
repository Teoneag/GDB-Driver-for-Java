package com.teoneag;

public class UsageExample {
    /**
     * Usage example for the GdbDriver class
     *
     * @param args none
     */
    public static void main(String[] args) {
        String gdbGccDir = "C:\\msys64\\ucrt64\\bin";
        String sourcePath = "D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.c";
        String executablePath;

        GdbDriver gdbDriver = new GdbDriver();

        gdbDriver.setGdbGccDir(gdbGccDir);

        executablePath = gdbDriver.compile(sourcePath);

        gdbDriver.loadFile(executablePath);

        gdbDriver.setBreakpoint("file_1.c", 13);

        gdbDriver.setBreakHandler(() -> {
            System.out.println("Breakpoint hit with backtrace: " + gdbDriver.getBacktrace());
            gdbDriver.resume();
        });

        gdbDriver.run();
    }

}

