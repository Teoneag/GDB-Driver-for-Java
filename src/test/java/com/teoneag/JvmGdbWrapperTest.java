package com.teoneag;

import org.junit.jupiter.api.Test;

class JvmGdbWrapperTest {

    @Test
    void bigTest() {
        JvmGdbWrapper jvmGdbWrapper = new JvmGdbWrapper();

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