package com.kais.app;

public class Test {

    public static void main(String[] args) {

        System.out.println("start");
        assert true;
        System.out.println("go on");
        assert false:"stop";
        System.out.println("end");
    }
}
