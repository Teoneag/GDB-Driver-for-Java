package com.teoneag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class JvmGdbWrapperTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private static String findExe(String exe) {
        String pathEnv = System.getenv("PATH");
        String[] pathDirs = pathEnv.split(File.pathSeparator);
        for (String dir : pathDirs) {
            File gdb = new File(dir, exe);
            if (gdb.exists()) {
                return gdb.getAbsolutePath();
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
        JvmGdbWrapper wrapper = new JvmGdbWrapper();
        assertNotNull(wrapper);
    }

    @Test
    void testResetAndToString() {
        JvmGdbWrapper wrapper = new JvmGdbWrapper();
        assertEquals("""
                JvmGdbWrapper{
                 breakpoints=[]
                 gdbPath='C:\\msys64\\ucrt64\\bin\\gdb.exe'
                 gccPath='C:\\msys64\\ucrt64\\bin\\gcc.exe'
                 filePath='D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.exe'
                 showOutput=false
                }""", wrapper.toString());
    }

    @Test
    void testShowOutput() {
        JvmGdbWrapper wrapper = new JvmGdbWrapper();
        wrapper.setShowOutput(true);
        assertEquals("""
                JvmGdbWrapper{
                 breakpoints=[]
                 gdbPath='C:\\msys64\\ucrt64\\bin\\gdb.exe'
                 gccPath='C:\\msys64\\ucrt64\\bin\\gcc.exe'
                 filePath='D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.exe'
                 showOutput=true
                }""", wrapper.toString());
        wrapper.setShowOutput(false);
        assertEquals("""
                JvmGdbWrapper{
                 breakpoints=[]
                 gdbPath='C:\\msys64\\ucrt64\\bin\\gdb.exe'
                 gccPath='C:\\msys64\\ucrt64\\bin\\gcc.exe'
                 filePath='D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.exe'
                 showOutput=false
                }""", wrapper.toString());
    }

    @Test
    void testLoadFile() {
        JvmGdbWrapper wrapper = new JvmGdbWrapper();
        wrapper.loadFile("path");
        assertEquals("""
                JvmGdbWrapper{
                 breakpoints=[]
                 gdbPath='C:\\msys64\\ucrt64\\bin\\gdb.exe'
                 gccPath='C:\\msys64\\ucrt64\\bin\\gcc.exe'
                 filePath='path'
                 showOutput=false
                }""", wrapper.toString());
    }

    @Test
    void testSetBreakpoint() {
        JvmGdbWrapper wrapper = new JvmGdbWrapper();
        wrapper.setBreakpoint("file", 1);
        assertEquals("""
                JvmGdbWrapper{
                 breakpoints=[file:1]
                 gdbPath='C:\\msys64\\ucrt64\\bin\\gdb.exe'
                 gccPath='C:\\msys64\\ucrt64\\bin\\gcc.exe'
                 filePath='D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.exe'
                 showOutput=false
                }""", wrapper.toString());
    }

    @Test
    void testRun() {
        String gdbPath = findExe("gdb.exe");
        String gccPath = findExe("gcc.exe");
        JvmGdbWrapper wrapper = new JvmGdbWrapper();
        wrapper.setGdbGccDir(gdbPath, gccPath);

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

        assertEquals("""
                JvmGdbWrapper{
                 breakpoints=[file_1.c:13]
                 gdbPath='""" + gdbPath + """
                '
                 gccPath='""" + gccPath + """
                '
                 filePath='""" + exeFile + """
                '
                 showOutput=true
                }""", wrapper.toString());

        wrapper.run();
        String result = outContent.toString();
        assertTrue(result.contains("GNU gdb (GDB)"));
        assertTrue(result.contains("Reading symbols from"));
        assertTrue(result.contains("Breakpoint 1 at"));
        assertTrue(result.contains("Breakpoint go brrrrr"));
        assertTrue(result.contains("main ()"));
        assertTrue(result.contains("file_1.c:13"));
        assertTrue(result.contains("int sum = 0"));
        assertTrue(result.contains("Sum of first 10 natural numbers: 55"));
        assertTrue(result.contains("Thread"));
        assertTrue(result.contains("exited with code 0"));
    }


}

