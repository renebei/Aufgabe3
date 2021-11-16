package de.hsos.prog3.rene.ab03;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class Ringpuffer<T> implements Queue<T>, Serializable, Cloneable {

    private ArrayList<T> elements;
    private int writePos = 0;
    private int readPos = 0;
    private int size;
    private int capacity;
    private boolean fixedCapacity;
    private boolean discarding;
    private final int CAPACITY_EXPANSION;


    public Ringpuffer(int capacity, int parameter) {
        this.elements = new ArrayList<>(capacity);
        this.capacity = capacity;
        this.fixedCapacity = true;
        this.discarding = true;
        this.CAPACITY_EXPANSION = parameter;
    }

    public Ringpuffer(int capacity, boolean fixed, boolean discard, int parameter) {
        this.elements = new ArrayList<>();
        this.capacity = capacity;
        this.fixedCapacity = fixed;
        this.discarding = discard;
        this.CAPACITY_EXPANSION = parameter;
        fill();
    }


    private void fill() {
        for (int i = 0; i < capacity; i++) {
            elements.add(null);
        }
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object t) {
        for (T tt : this) {
            if (tt.equals(t)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {
            private int count = 0;

            @Override
            public boolean hasNext() {
                if (size == capacity) return count < capacity;
                else return count < size;
            }

            @Override
            public T next() {
                if (readPos <= size) {
                    T temp = elements.get(readPos);
                    readPos = incrementPos(readPos);
                    count++;
                    return temp;
                }
                return null;
            }
        };
        return it;
    }

    @Override
    public Object[] toArray() {
        T[] arr = (T[]) new Object[size];
        int count = 0;
        for (T t : this) {
            arr[count++] = t;
        }
        return arr;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        if (size() == capacity) {
            return testInsertionRules(t);
        }
        elements.add(writePos, t);
        writePos = incrementPos(writePos);
        this.size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (this.contains(o)) {
            readPos = incrementPos(readPos);
            size--;
            return true;
        }
        return false;
    }

    private int incrementPos(int pos) {
        return (pos + 1) % this.capacity;
    }


    @Override //
    public boolean containsAll(Collection<?> c) {
        if (c.size() > this.size) return false;
        else {
            for (T t : this) {
                if (!c.contains(t)) return false;
            }
            return true;
        }

    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c != null) {
            for (T t : c) {
                add(t);
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        try {
            T[] t = (T[]) c.toArray();
            for (int i = 0; i < capacity; i++) {
                this.remove(t[i]);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for (T t : this) {
            if (!c.contains(t)) {
                this.remove(t);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        elements = new ArrayList<>();
        size = 0;
        readPos = 0;
        writePos = 0;
    }

    @Override
    public boolean offer(T t) {
        return testInsertionRules(t);
    }

    private boolean testInsertionRules(T t) {
        if (discarding) {
            elements.set(writePos, t);
            writePos = incrementPos(writePos);
            readPos = incrementPos(readPos);
            return true;
        } else {
            if (!fixedCapacity) {
                this.capacity += CAPACITY_EXPANSION;
                elements.add(writePos, t);
                writePos = incrementPos(writePos);
                readPos = incrementPos(readPos);
                this.size++;
                return true;
            }
        }
        return false;
    }

    @Override
    public T remove() {
        T t = elements.get(readPos);
        readPos = incrementPos(readPos);
        this.size--;
        return t;
    }

    @Override
    public T poll() {
        if (elements.isEmpty()) return null;
        T t = elements.get(readPos);
        readPos = incrementPos(readPos);
        this.size--;
        return t;
    }

    @Override
    public T element() {
        return elements.get(writePos - 1);
    }

    @Override
    public T peek() {
        try {
            return elements.get(writePos - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
