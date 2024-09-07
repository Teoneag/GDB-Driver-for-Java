//package com.teoneag;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintStream;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class MainTest {
//
//    private final InputStream originalSystemIn = System.in;
//    private final PrintStream originalSystemOut = System.out;
//    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
//
//    @BeforeEach
//    void setUp() {
//        System.setOut(new PrintStream(outputStreamCaptor));
//    }
//
//    @Test
//    void testAllCommands() {
//        provideInput("""
//                help
//                init brrrr
//                gdb brrrr
//                gcc brrrr
//                test
//                comp brrrr
//                compile brrrr brrrr
//                load brrrr
//                start
//                break brrrr brrrr
//                handle
//                output
//                reset
//                quit
//                """);
//        Main.main(new String[]{});
//        assertTrue(outputStreamCaptor.toString().contains(
//                "To chose an option, type the number or the name of the option, optionally followed by the arguments"));
//    }
//
//    private void provideInput(String data) {
//        System.setIn(new ByteArrayInputStream(data.getBytes()));
//    }
//}
