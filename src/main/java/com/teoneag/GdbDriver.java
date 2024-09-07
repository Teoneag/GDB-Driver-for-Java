package com.teoneag;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

/**
 * A class that wraps the GDB debugger
 */
public class GdbDriver {

    private final List<String> breakpoints = new ArrayList<>();
    private String gdbPath;
    private String gccPath;
    private String filePath;
    private Runnable breakHandler;
    private boolean showOutput;

    private Process debuggerProcess;
    private BufferedWriter debuggerInput;
    private BufferedReader debuggerOutput;

    /**
     * Constructor for the JvmGdbWrapper class
     */
    public GdbDriver() {
        reset();
    }

    /**
     * Reset the debugger to its default state
     */
    public void reset() {
        // ToDo make def variables from these
        setGdbGccDir("C:\\MinGW\\bin");
        filePath = pathToAbsolute("res\\file_1.exe", true);
        breakHandler = () -> {
            System.out.println("Breakpoint hit with backtrace: " + getBacktrace());
            resume();
        };
        showOutput = false;
    }

    /**
     * Set the path to the debugger folder
     *
     * @param folderPath the path to the debugger folder
     */
    public void setGdbGccDir(String folderPath) {
        setGdbPath(folderPath + "\\gdb.exe");
        setGccPath(folderPath + "\\gcc.exe");
        System.out.println("Successfully set GDB and GCC paths to " + folderPath);
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
     * Set the show output flag
     *
     * @param showOutput the show output flag
     */
    public void setShowOutput(boolean showOutput) {
        this.showOutput = showOutput;
        System.out.println("Show output set to " + showOutput);
    }


    /**
     * Load a file to debug
     *
     * @param filePath the path to the file
     */
    public void loadFile(String filePath) {
        if (!filePath.endsWith(".exe")) {
            System.out.println("Error: file must end in .exe");
            return;
        }
        String absolutePath = pathToAbsolute(filePath, true);
        if (absolutePath == null) return;

        this.filePath = absolutePath;
        System.out.println("Successfully loaded file " + this.filePath);
    }

    /**
     * Set a breakpoint
     *
     * @param className  the class name
     * @param lineNumber the line number
     */
    // ToDo be able to also set breakpoints while the debugger is running
    public void setBreakpoint(String className, int lineNumber) {
        breakpoints.add(className + ":" + lineNumber);
        System.out.println("Successfully set breakpoint at " + className + ":" + lineNumber);
    }

    /**
     * Set the breakpoint handler
     *
     * @param handler a runnable executed when a breakpoint is hit
     */
    public void setBreakHandler(Runnable handler) {
        this.breakHandler = handler;
        System.out.println("Successfully set breakpoint handler.");
        // ToDo show what's been set to
    }

    /**
     * Run the debugger
     */
    public void run() {
        ProcessBuilder pb = new ProcessBuilder(gdbPath, filePath);
        pb.redirectErrorStream(true);
        try {
            debuggerProcess = pb.start();
            debuggerInput = new BufferedWriter(new OutputStreamWriter(debuggerProcess.getOutputStream()));
            debuggerOutput = new BufferedReader(new InputStreamReader(debuggerProcess.getInputStream()));

            for (var breakpoint : breakpoints) sendCommand("break " + breakpoint);

            sendCommand("run");

            String line = "";
            // remove all the lines before starting the program, so break handler is not called by mistake
            while (line != null && !line.contains("Starting program")) line = nextLine();

            while (line != null && !line.contains(") exited normally]")) {
                if (line.contains("Breakpoint ")) {
                    nextLine(); // remove the int sum = 0 thing
                    // ToDo show the variables, while the line doesn't contain " at "
                    breakHandler.run();
                }
                line = nextLine();
            }
            quit();
        } catch (IOException e) {
            System.out.println("Error running debugger: " + e.getMessage());
        }
    }

    private String nextLine() {
        try {
            String line = debuggerOutput.readLine();
            if (showOutput) System.out.println(line);
            return line;
        } catch (IOException e) {
            System.out.println("Error reading debugger output: " + e.getMessage());
            return null;
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
        if (!filePath.endsWith(".c")) {
            System.out.println("Error: file must end in .c");
            return null;
        }
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
        filePath = pathToAbsolute(filePath, true);
        outputFilePath = pathToAbsolute(outputFilePath, false);
        if (filePath == null || outputFilePath == null) return;

        if (!filePath.endsWith(".c")) {
            System.out.println("Error: file must end in .c");
            return;
        }
        if (outputFilePath.contains(".")) {
            System.out.println("Error: output file path must not have extension");
            return;
        }
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
            if (showOutput) System.out.println(getOutput(debuggerProcess));
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

    // Returns the absolute path, or null if the file must exist but does not
    private String pathToAbsolute(String path, boolean hasToExist) {
        Path givenPath = Paths.get(path);

        // Convert to absolute if it's not already
        if (!givenPath.isAbsolute()) {
            String projectRoot = System.getProperty("user.dir");
            givenPath = Paths.get(projectRoot, path);
        }

        // Check existence only if required
        if (hasToExist && !givenPath.toFile().exists()) {
            System.out.println("Error: file does not exist: " + givenPath);
            return null;
        }

        return givenPath.toString();
    }
}
