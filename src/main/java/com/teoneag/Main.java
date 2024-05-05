package com.teoneag;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final JvmGdbWrapper jvmGdbWrapper = new JvmGdbWrapper();
    static final Scanner userInput = new Scanner(System.in);
    static String lastLine;

    /**
     * Main method - CLI application entry point for the JVM-GDB-Wrapper class
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the JVM-GDB-Wrapper!");
        help();

        String option = "";
        while (!option.equals("quit") && !option.equals("10")) {
            lastLine = userInput.nextLine();
            option = lastLine.split(" ")[0];
            lastLine = lastLine.replace(option, "").trim();
            switch (option) {
                case "0", "help" -> help();
                case "1", "init" -> init();
                case "11", "gdb" -> gdb();
                case "12", "gcc" -> gcc();
                case "2", "test" -> test();
                case "3", "comp" -> comp();
                case "31", "compile" -> compile();
                case "4", "load" -> load();
                case "5", "start" -> start();
                case "6", "break" -> setBreakpoint();
                case "7", "resume" -> resume();
                case "8", "handle" -> handle();
                case "9", "backtrace" -> backtrace();
                case "10", "quit" -> {
                }
                default -> System.out.println("Invalid option. Please try again or type 'help'.");
            }
        }

    }

    private static void help() {
        System.out.println("""
                To chose an option, type the number or the name of the option, optionally followed by the arguments
                    0 - help = show this message
                    1 - init <folderPath> = set the folder path where the gdb.exe, gcc.exe are located
                        11 - gdb <gdbPath> = set the GDB path (include the name of the executable)
                        12 - gcc <gccPath> = set the GCC path (include the name of the executable)
                    2 - test = checks for the GDB, GCC versions
                    3 - comp <filePath.c> = compile a C file, the executable will be filePath.exe
                        31 - compile <source.c> <destination> = compile a C file
                    4 - load <filePath> = load a file to debug
                    5 - start = start the debugger
                    6 - break <fileName> <lineNumber> = set a breakpoint
                    7 - resume = resume the execution
                    8 - handle auto|manual = set the breakpoint handler
                            auto = print backtrace and resume
                            manual = wait for user input
                    9 - backtrace = print the backtrace
                    10 - quit = exit the application""");
    }

    private static void init() {
        if (lastLine.isEmpty()) {
            System.out.println("The path cannot be empty. Please try again.");
            return;
        }
        jvmGdbWrapper.setPath(lastLine);
        System.out.println("Folder path set to " + lastLine);
    }

    private static void gdb() {
        String gdbPath = userInput.next();
        jvmGdbWrapper.setGdbPath(gdbPath);
    }

    private static void gcc() {
        String gccPath = userInput.next();
        jvmGdbWrapper.setGccPath(gccPath);
    }

    private static void test() {
        System.out.println("Testing GDB and GCC versions...");
        jvmGdbWrapper.test();
        System.out.println("Test complete.");
    }

    private static void comp() {
        if (lastLine.isEmpty()) {
            System.out.println("The path cannot be empty. Please try again.");
            return;
        }
        jvmGdbWrapper.compile(lastLine);
    }

    private static void compile() {
        List<String> args = Arrays.asList(lastLine.split(" "));
        if (args.size() != 2) {
            System.out.println("Please provide the source and destination file paths.");
            return;
        }
        jvmGdbWrapper.compile(args.get(0), args.get(1));
    }

    private static void load() {
        if (lastLine.isEmpty()) {
            System.out.println("The path cannot be empty. Please try again.");
            return;
        }
        jvmGdbWrapper.load(lastLine);
    }

    private static void start() {
        jvmGdbWrapper.run();
    }

    private static void setBreakpoint() {
        try {
            List<String> args = Arrays.asList(lastLine.split(" "));
            if (args.size() != 2) {
                System.out.println("Please provide the file name and line number.");
                return;
            }
            String fileName = args.get(0);
            int lineNumber = Integer.parseInt(args.get(1));
            jvmGdbWrapper.setBreakpoint(fileName, lineNumber);
            System.out.println("Breakpoint set at " + fileName + ":" + lineNumber);
        } catch (NumberFormatException e) {
            System.out.println("The line number must be an integer.");
        }
    }

    private static void resume() {
        jvmGdbWrapper.resume();
    }

    private static void handle() {
        if (lastLine.equals("auto")) {
            jvmGdbWrapper.setBreakHandler(() -> {
                System.out.println("Breakpoint hit with backtrace: " + jvmGdbWrapper.getBacktrace());
                jvmGdbWrapper.resume();
            });
            return;
        }
        if (lastLine.equals("manual")) {
            // ToDo
//            jvmGdbWrapper.setBreakHandler(() -> {
//                System.out.println("Breakpoint hit with backtrace: " + jvmGdbWrapper.getBacktrace());
//                System.out.println("Press enter to resume...");
//                userInput.nextLine();
//                jvmGdbWrapper.resume();
//            });
            return;
        }
        System.out.println("Invalid handler. Please use 'auto' or 'manual'.");
    }

    private static void backtrace() {
        System.out.println(jvmGdbWrapper.getBacktrace());
    }
}