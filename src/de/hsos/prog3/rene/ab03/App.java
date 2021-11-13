package de.hsos.prog3.rene.ab03;

import java.util.Random;

public class App {


    public static void main(String[] args) {
        Random rand = new Random();
        int count = 0;
        Ringpuffer<Integer> ring = new Ringpuffer<>(5, true, true);
        for (int i = 0; i < 10; i++) {
            ring.add(count++);
        }
        System.out.println("jo");


    }
}
