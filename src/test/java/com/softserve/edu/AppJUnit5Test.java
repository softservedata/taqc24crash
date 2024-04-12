package com.softserve.edu;


import org.junit.jupiter.api.*;

public class AppJUnit5Test {

    @BeforeAll
    public static void setup() {
        System.out.println("@BeforeAll setup()");
    }

    @AfterAll
    public static void tear() {
        System.out.println("@AfterAll tear()");
    }

    @BeforeEach
    public void setupThis() {
        System.out.println("\t@BeforeEach setupThis()");
    }

    @AfterEach
    public void tearThis() {
        System.out.println("\t@AfterEach tearThis()");
    }

    @Test
    void testExpectedException() {
        System.out.println("\t\t@Test testExpectedException()");
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
                () -> {
            //Code under test
            int i = 0;
            i = 10 / (i + 0);
        });
        System.out.println("\t\tMessage = " + thrown.getMessage());
        Assertions.assertEquals("/ by zero", thrown.getMessage());
    }


    @Test
    public void testDemo() {
        System.out.println("\t\t@Test testDemo()");
        //
        Assertions.assertEquals(5, 3 + 2);
    }
}
