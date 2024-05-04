package com.teoneag;

public class JvmGdbWrapper {
    /**
     * Constructor for the JvmGdbWrapper class
     *
     * @param gdbPath the path to the GDB executable
     */
    public JvmGdbWrapper(String gdbPath) {

    }

    /**
     * Load a file
     *
     * @param filePath the path to the file
     */
    public void load(String filePath) {
    }

    /**
     * Set a breakpoint
     *
     * @param className  the class name
     * @param lineNumber the line number
     */
    public void setBreakpoint(String className, int lineNumber) {
    }

    /**
     * Set the breakpoint handler
     *
     * @param handler a runnable executed when a breakpoint is hit
     */
    public void setBreakHandler(Runnable handler) {
    }

    /**
     * Resume execution after a breakpoint
     */
    public void resume() {

    }

    /**
     * Run the debugger
     */
    public void run() {

    }
}
