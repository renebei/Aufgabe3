package de.hsos.prog3.rene.ab03;

import java.util.Random;

public class App {


    public static void main(String[] args) {
        Random rand = new Random();
        int count = 0;
        Ringpuffer<Integer> ring = new Ringpuffer<>(5, false, false, 5);
        for (int i = 0; i < 8; i++) {
            ring.add(count++);
        }

        //ring.forEach(System.out::println);
        Ringpuffer<Integer> ring2 = new Ringpuffer<>(5, false, false, 5);
        ring2.addAll(ring);
        //ring2.removeAll(ring); geht nicht
        //ring2.remove();  geht
        //System.out.println(ring2.containsAll(ring)); geht
        //ring2.retainAll(ring); geht
        //ring2.clear(); geht

        ring2.forEach(System.out::println);


    }
}
