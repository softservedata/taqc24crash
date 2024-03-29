package com.softserve.edu01inher;

public class ClassB extends ClassA {
    public double i = 1.1;

    public ClassB() {
        System.out.println("ClassB() done");
    }

    @Override
    public void m1() {
        System.out.println("ClassB, metod m1, i=" + i);
        // super.m1();
    }

    @Override
    public void m4() {
        System.out.println("ClassB, metod m4, i = " + i);
    }

    // @Override
    public void m5() {
        System.out.println("ClassB, metod m5");
    }

    // @Override
    public static void m6() {
        System.out.println("ClassB, static metod m6");
        ClassA.m6();
    }

    //private void m8() {
    public void m8() {
        System.out.println("ClassB, metod m8");
        super.m8();
    }
}