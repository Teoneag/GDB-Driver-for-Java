package com.teoneag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GdbDriverTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private static String findExe(String exe) {
        String pathEnv = System.getenv("PATH");
        String[] pathDirs = pathEnv.split(File.pathSeparator);
        for (String dir : pathDirs) {
            File file = new File(dir, exe);
            if (file.exists()) {
                return file.getAbsolutePath();
            }
        }
        throw new IllegalStateException("GDB not found in PATH");
    }

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testConstructor() {
        GdbDriver wrapper = new GdbDriver();
        assertNotNull(wrapper);
    }

    @Test
    void testResetAndToString() {
        GdbDriver wrapper = new GdbDriver();
        assertTrue(wrapper.toString().contains("GdbDriver"));
        assertTrue(wrapper.toString().contains("gdbPath="));
        assertTrue(wrapper.toString().contains("gccPath="));
        assertTrue(wrapper.toString().contains("filePath="));
        assertTrue(wrapper.toString().contains("breakHandler"));
        assertTrue(wrapper.toString().contains("debuggerProcess=<null>"));
        assertTrue(wrapper.toString().contains("debuggerInput=<null>"));
        assertTrue(wrapper.toString().contains("showOutput=false"));
    }

    @Test
    void testShowOutput() {
        GdbDriver wrapper = new GdbDriver();
        wrapper.setShowOutput(true);
        assertTrue(wrapper.toString().contains("showOutput=true"));
        wrapper.setShowOutput(false);
        assertTrue(wrapper.toString().contains("showOutput=false"));
    }

    @Test
    void testLoadFile() {
        GdbDriver wrapper = new GdbDriver();
        wrapper.loadFile("path");
        assertTrue(wrapper.toString().contains("filePath=path"));
    }

    @Test
    void testSetBreakpoint() {
        GdbDriver wrapper = new GdbDriver();
        wrapper.setBreakpoint("file", 1);
        // ToDo
    }

    // ToDo fix test
    @Test
    void testRun() {
        String gdbPath = findExe("gdb.exe");
        String gccPath = findExe("gcc.exe");
        GdbDriver wrapper = new GdbDriver();
//        wrapper.setGdbGccDir(gdbPath, gccPath);

        String testFile = "src/test/resources/file_1.c";
        String projectPath = System.getProperty("user.dir");
        String testFilePath = projectPath + File.separator + testFile;

        wrapper.test();

        assertTrue(outContent.toString().contains("Successfully finished testing GDB!"));
        assertTrue(outContent.toString().contains("Successfully finished testing GCC!"));

        String exeFile = wrapper.compile(testFilePath);

        wrapper.loadFile(exeFile);

        wrapper.setBreakpoint("file_1.c", 13);
        wrapper.setShowOutput(true);
        wrapper.setShowOutput(true);

        wrapper.setBreakHandler(() -> {
            System.out.println("Breakpoint go brrrrr: " + wrapper.getBacktrace());
            wrapper.resume();
        });

        assertTrue(outContent.toString().contains("Successfully finished compiling!"));
        wrapper.run();
        String result = outContent.toString();
        assertTrue(result.contains("GNU gdb (GDB)"));
        assertTrue(result.contains("Reading symbols from"));
//        assertTrue(result.contains("Breakpoint 1 at"));
//        assertTrue(result.contains("Breakpoint go brrrrr"));
//        assertTrue(result.contains("main ()"));
//        assertTrue(result.contains("file_1.c:13"));
//        assertTrue(result.contains("int sum = 0"));
//        assertTrue(result.contains("Sum of first 10 natural numbers: 55"));
//        assertTrue(result.contains("Thread"));
//        assertTrue(result.contains("exited with code 0"));
    }


}

