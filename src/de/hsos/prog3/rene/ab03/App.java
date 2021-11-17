package de.hsos.prog3.rene.ab03;

public class App {

    public static void main(String[] args) {
        truetrue();
        falsefalse();
        truefalse();
    }

    public static void truetrue() {
        System.out.println("Capacity 4, fixed: true, discard: true --- for (int i = 0; i < 20; i++) ");
        int count = 0;
        Ringpuffer<Integer> ring = new Ringpuffer<>(4, true, true, 5);
        Ringpuffer<Integer> ring2 = new Ringpuffer<>(4, true, true, 5);
        for (int i = 0; i < 20; i++) {
            try {
                ring.add(count++);
            } catch (IllegalStateException e) {
                System.out.println(e);
            }
        }
        System.out.println("Ring in foreach (iterator):");
        ring.forEach(System.out::println);

        System.out.println("Added all to ring2");
        ring2.addAll(ring);
        System.out.println("Ring2 contains all Elements out of ring: " + ring2.containsAll(ring));
        ring2.forEach(System.out::println);
        ring2.remove();
        System.out.println("Remove by FIFO");
        ring2.forEach(System.out::println);
        System.out.println("Does ring2 contain 17?: " + ring2.contains(17));
        System.out.println("Does ring2 contain 42?: " + ring2.contains(42));
        System.out.println("Offer 2");
        ring2.offer(2);
        ring2.forEach(System.out::println);
        System.out.println("peek: " + ring2.peek());
    }

    public static void falsefalse() {
        System.out.println("Capacity 4, fixed: false, discard: false  --- for (int i = 0; i < 10; i++) ");
        int count = 0;
        Ringpuffer<Integer> ring = new Ringpuffer<>(4, false, false, 5);
        Ringpuffer<Integer> ring2 = new Ringpuffer<>(4, false, false, 5);
        for (int i = 0; i < 10; i++) {
            try {
                ring.add(count++);
            } catch (IllegalStateException e) {
                System.out.println(e);
            }
        }
        System.out.println("Ring in foreach (iterator):");
        ring.forEach(System.out::println);

        System.out.println("Added all to ring2");
        ring2.addAll(ring);
        System.out.println("Ring2 contains all Elements out of ring: " + ring2.containsAll(ring));
        ring2.forEach(System.out::println);
        ring2.remove();
        System.out.println("Remove by FIFO");
        ring2.forEach(System.out::println);
        System.out.println("Does ring2 contain 3?: " + ring2.contains(5));
        System.out.println("Does ring2 contain 0?: " + ring2.contains(0));
        System.out.println("Offer 2");
        ring2.offer(2);
        ring2.forEach(System.out::println);

        System.out.println("peek: " + ring2.peek());
    }

    public static void truefalse() {
        System.out.println("Capacity 4, fixed: true, discard: false  --- for (int i = 0; i < 10; i++) ");
        int count = 0;
        Ringpuffer<Integer> ring = new Ringpuffer<>(4, true, false, 5);
        Ringpuffer<Integer> ring2 = new Ringpuffer<>(4, true, false, 5);
        for (int i = 0; i < 10; i++) {
            try {
                ring.add(count++);
            } catch (IllegalStateException e) {
                System.out.println(e);
            }
        }
        System.out.println("Ring in foreach (iterator):");
        ring.forEach(System.out::println);

        System.out.println("Added all to ring2");
        ring2.addAll(ring);
        System.out.println("Ring2 contains all Elements out of ring: " + ring2.containsAll(ring));
        ring2.forEach(System.out::println);
        ring2.remove();
        System.out.println("Remove by FIFO");
        ring2.forEach(System.out::println);
        System.out.println("Does ring2 contain 5?: " + ring2.contains(5));
        System.out.println("Does ring2 contain 0?: " + ring2.contains(0));
        System.out.println("Offer 2");
        ring2.offer(2);
        ring2.forEach(System.out::println);

        System.out.println("peek: " + ring2.peek());
    }
}
