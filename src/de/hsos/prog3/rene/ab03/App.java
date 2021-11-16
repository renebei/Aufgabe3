package de.hsos.prog3.rene.ab03;

public class App {

    public static void main(String[] args) {
        int count = 0;
        Ringpuffer<Integer> ring = new Ringpuffer<>(3, true, true, 5);
        Ringpuffer<Integer> ring2 = new Ringpuffer<>(3, true, true, 5);
        for (int i = 0; i < 19; i++) {
            try {
                ring.add(count++);
            } catch (IllegalStateException e) {
                System.out.println(e);
            }
        }

        ring2.addAll(ring);
        ring2.forEach(System.out::println);
    }
}
