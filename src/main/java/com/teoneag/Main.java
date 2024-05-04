package com.teoneag;

public class Main {
    /**
     * Main method - CLI application entry point for the JVM-GDB-Wrapper class
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        //noinspection SpellCheckingInspection
        String debuggerFolder = "C:\\msys64\\ucrt64\\bin";
        JvmGdbWrapper jvmGdbWrapper = new JvmGdbWrapper(debuggerFolder);

        // test
        jvmGdbWrapper.testGdb();

        // compile
        String cFilePath = "D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.c";
        String resPath = jvmGdbWrapper.compile(cFilePath);

        // debug
        String exeFilePath = "D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.exe";
        jvmGdbWrapper.load(exeFilePath);
        jvmGdbWrapper.setBreakpoint("file_1.c", 13);
        jvmGdbWrapper.setBreakHandler(() -> {
            System.out.println("Breakpoint hit with backtrace: " + jvmGdbWrapper.getBacktrace());
            jvmGdbWrapper.resume();
        });
        jvmGdbWrapper.run();

    }
}