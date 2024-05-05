package com.teoneag;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JvmGdbWrapper {

    private final List<String> breakpoints;
    private String gdbPath;
    private String gccPath;
    private String filePath;
    private Runnable breakHandler;
    private Process debuggerProcess;
    private BufferedWriter debuggerInput;
    private BufferedReader debuggerOutput;

    /**
     * Constructor for the JvmGdbWrapper class
     */
    public JvmGdbWrapper() {
        setPath();
        filePath = "D:\\working\\JVM-GDB-Wrapper\\src\\main\\resources\\file_1.exe";
        breakpoints = new ArrayList<>();
        breakHandler = () -> {
            System.out.println("Breakpoint hit with backtrace: " + getBacktrace());
            resume();
        };
    }

    /**
     * Set the path to the debugger folder
     */
    public void setPath() {
        //noinspection SpellCheckingInspection
        setPath("C:\\msys64\\ucrt64\\bin");
    }

    /**
     * Set the path to the debugger folder
     *
     * @param folderPath the path to the debugger folder
     */
    public void setPath(String folderPath) {
        setPath(folderPath + "\\gdb.exe", folderPath + "\\gcc.exe");
    }

    /**
     * Set the path to the gdb and gcc executables
     *
     * @param gdbPath the path to the gdb executable
     * @param gccPath the path to the gcc executable
     */
    public void setPath(String gdbPath, String gccPath) {
        setGdbPath(gdbPath);
        setGccPath(gccPath);
    }

    /**
     * Set the path to the gdb executable
     *
     * @param gdbPath the path to the gdb executable
     */
    public void setGdbPath(String gdbPath) {
        this.gdbPath = gdbPath;
    }

    /***
     * Set the path to the gcc executable
     * @param gccPath the path to the gcc executable
     */
    public void setGccPath(String gccPath) {
        this.gccPath = gccPath;
    }


    /**
     * Load a file
     *
     * @param filePath the path to the file
     */
    public void load(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Set a breakpoint
     *
     * @param className  the class name
     * @param lineNumber the line number
     */
    public void setBreakpoint(String className, int lineNumber) {
        breakpoints.add(className + ":" + lineNumber);
    }

    /**
     * Set the breakpoint handler
     *
     * @param handler a runnable executed when a breakpoint is hit
     */
    public void setBreakHandler(Runnable handler) {
        this.breakHandler = handler;
    }

    /**
     * Run the debugger
     */
    public void run() {
        if (filePath == null) {
            System.out.println("No file loaded! Please load a file before running the debugger!");
            return;
        }
        if (!breakpoints.isEmpty() && breakHandler == null) {
            System.out.println("There are breakpoints, but no breakpoint handler set! " + "Please set a breakpoint handler or remove the breakpoints before running the debugger!");
            return;
        }
        ProcessBuilder pb = new ProcessBuilder(gdbPath, filePath);
        pb.redirectErrorStream(true);
        try {
            debuggerProcess = pb.start();
            debuggerInput = new BufferedWriter(new OutputStreamWriter(debuggerProcess.getOutputStream()));
            debuggerOutput = new BufferedReader(new InputStreamReader(debuggerProcess.getInputStream()));

            for (var breakpoint : breakpoints) sendCommand("break " + breakpoint);

            sendCommand("run");

            try {
                String line;
                while (true) {
                    line = debuggerOutput.readLine();
                    if (line == null || line.contains("[Thread")) break;
                    if (line.contains("hit Breakpoint")) {
                        debuggerOutput.readLine();
                        debuggerOutput.readLine();
                        breakHandler.run();
                    }
                }
                quit();
            } catch (IOException e) {
                System.out.println("Error reading debugger output: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error running debugger: " + e.getMessage());
        }
    }

    /**
     * Resume execution after a breakpoint
     */
    public void resume() {
        sendCommand("continue");
    }

    /**
     * Quit the debugger
     */
    public void quit() {
        sendCommand("quit");
    }

    /**
     * Get the backtrace
     *
     * @return the backtrace
     */
    public String getBacktrace() {
        sendCommand("bt");
        try {
            return debuggerOutput.readLine();
        } catch (IOException e) {
            System.out.println("Error getting backtrace: " + e.getMessage());
            return null;
        }
    }

    /**
     * Test the gdb and gcc versions
     */
    public void test() {
        testGdb();
        testGcc();
    }

    /**
     * Prints the gdb version
     */
    public void testGdb() {
        runCommand("testing GDB", gdbPath, "--version");
    }

    /**
     * Prints the gcc version
     */
    public void testGcc() {
        runCommand("testing GCC", gccPath, "--version");
    }

    /**
     * Compile the file
     *
     * @param filePath the path to the file
     * @return the binary file path
     */
    public String compile(String filePath) {
        String outputFilePath = filePath.replace(".c", "");
        compile(filePath, outputFilePath);
        return outputFilePath;
    }

    /**
     * Compile the file
     *
     * @param filePath       the path to the file
     * @param outputFilePath the path to the output file
     */
    public void compile(String filePath, String outputFilePath) {
        runCommand("compiling", gccPath, "-g", "-o", outputFilePath, filePath);
    }

    private void sendCommand(String command) {
        if (debuggerInput != null) {
            try {
                debuggerInput.write(command);
                debuggerInput.newLine();
                debuggerInput.flush();
            } catch (IOException e) {
                System.out.println("Error sending command: " + e.getMessage());
            }
        }
    }

    /**
     * Run a command
     *
     * @param name    the name of the command used for logging
     * @param command the command to run as an array of strings
     */
    private void runCommand(String name, String... command) {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        try {
            debuggerProcess = pb.start();
            int exitCode = debuggerProcess.waitFor();
            System.out.println(getOutput(debuggerProcess));
            if (exitCode == 0) {
                System.out.println("Successfully finished " + name + "!");
            } else {
                System.out.println(name + " exited with non-zero status: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error " + name + ": " + e.getMessage());
        }
    }

    private String getOutput(Process process) {
        Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
