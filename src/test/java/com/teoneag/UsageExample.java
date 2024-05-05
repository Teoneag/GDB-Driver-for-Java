package com.teoneag;

public class UsageExample {
    public static void main(String[] args) {
        String gdbGccDir = "C:\\msys64\\ucrt64\\bin";
        String sourcePath = "D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.c";
        String executablePath = "D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.exe";

        JvmGdbWrapper jvmGdbWrapper = new JvmGdbWrapper();

        jvmGdbWrapper.setGdbGccDir(gdbGccDir);

        executablePath = jvmGdbWrapper.compile(sourcePath);

        jvmGdbWrapper.loadFile(executablePath);

        jvmGdbWrapper.setBreakpoint("file_1.c", 13);

        jvmGdbWrapper.setBreakHandler(() -> {
            System.out.println("Breakpoint hit with backtrace: " + jvmGdbWrapper.getBacktrace());
            jvmGdbWrapper.resume();
        });

        jvmGdbWrapper.run();
    }

}

