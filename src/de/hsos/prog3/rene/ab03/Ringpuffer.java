package de.hsos.prog3.rene.ab03;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public class Ringpuffer<T> implements Queue<T>, Serializable, Cloneable {

    private ArrayList<T> elements;
    private int writePos = 0;
    private int readPos = 0;
    private int size;
    private int capacity;
    private boolean fixedCapacity;
    private boolean discarding;
    private final int CAPACITY_EXPANSION;

    public Ringpuffer(int capacity, boolean fixed, boolean discard, int parameter) {
        this.elements = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            elements.add(null);
        }
        this.capacity = capacity;
        this.fixedCapacity = fixed;
        this.discarding = discard;
        this.CAPACITY_EXPANSION = parameter;
    }


    @Override
    public int size() {
        return this.size;
    } //geht

    @Override
    public boolean isEmpty() {
        return size == 0;
    } //geht

    @Override
    public boolean contains(Object t) { //geht
        for (T tt : this) {
            if (tt.equals(t)) return true;
        }
        return false;
    }


    @Override
    public Ringpuffer<T> clone() {
        try {
            Ringpuffer clone = (Ringpuffer) super.clone();
            clone.elements = new ArrayList<>(elements);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone wasnt supported"); //https://stackoverflow.com/questions/24863185/what-is-an-assertionerror-in-which-case-should-i-throw-it-from-my-own-code
        }
    }

    @Override
    public Iterator<T> iterator() { //geht
        Iterator<T> it = new Iterator<T>() {
            private int count = 0;
            private int tempReader = readPos;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public T next() {
                T temp = elements.get(tempReader);
                tempReader = incrementPos(tempReader);
                count++;
                return temp;
            }
        };
        return it;
    }

    @Override
    public T[] toArray() {
        T[] array = (T[]) Array.newInstance(this.getClass().getComponentType(), size);
        int i = 0;
        for (T t : this) {
            array[i++] = t;
        }
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) { //geht
        if (!testInsertionRules(t)) throw new IllegalStateException();

        return true;
    }

    @Override
    public boolean offer(T t) {
        return testInsertionRules(t);
    }

    private boolean testInsertionRules(T t) {
        if (size() == capacity) {
            if (fixedCapacity) {
                if (discarding) {
                    removeFromRing();
                } else {
                    return false;
                }
            } else {
                readPos = 0;
                writePos = size;
                capacity *= CAPACITY_EXPANSION;
                for (int i = 0; i < capacity - size; i++) {
                    elements.add(null);
                }
            }
        }
        elements.set(writePos, t);
        size++;
        writePos = incrementPos(writePos);
        return true;
    }


    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T t : c) {
            this.add(t);
        }
        return true;
    }


    private int incrementPos(int pos) {
        return (pos + 1) % this.capacity;
    }

    @Override //
    public boolean containsAll(Collection<?> c) { //geht
        if (c.size() < this.size) return false;
        else {
            for (T t : this) {
                if (!c.contains(t)) return false;
            }
            return true;
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
//        for (T t : this) {
//            if (c.contains(t))
//                this.remove(c.remove(t));
//        }
        return true;
    }


    @Override
    public void clear() {
        elements = new ArrayList<>();
        size = 0;
        readPos = 0;
        writePos = 0;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public T remove() {
        return removeFromRing();
    }

    @Override
    public T poll() {
        if (this.isEmpty()) return null;
        return removeFromRing();
    }

    private T removeFromRing() {
        T t = elements.get(readPos);
        readPos = incrementPos(readPos);
        this.size--;
        return t;
    }

    @Override
    public T element() {
        return elements.get(readPos);
    }

    @Override
    public T peek() {
        if (this.isEmpty()) return null;
        return elements.get(readPos);
    }


    @Override
    public boolean retainAll(Collection<?> c) {
//        boolean changed = false;
//        for (Object o : c) {
//            if (!this.contains(o)) {
//                this.remove(o);
//                changed = true;
//            }
//        }
//        return changed;
        return false;
    }

    //retainAll, remove(o), toArray, removeAll
}
