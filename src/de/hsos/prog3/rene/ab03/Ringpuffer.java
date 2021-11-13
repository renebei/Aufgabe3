package de.hsos.prog3.rene.ab03;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class Ringpuffer<T> implements Queue<T>, Serializable, Cloneable {

    private ArrayList<T> elements;
    private int writePos;
    private int readPos;
    private int size;
    private int capacity;
    private boolean fixedCapacity;
    private boolean discarding;


    public Ringpuffer(int capacity) {
        this.elements = new ArrayList<>();
        this.capacity = capacity;
        this.fixedCapacity = true;
        this.discarding = true;
    }

    public Ringpuffer(int capacity, boolean fixed, boolean discard) {
        this.elements = new ArrayList<>();
        this.capacity = capacity;
        this.fixedCapacity = fixed;
        this.discarding = discard;
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
        for (T element : elements) {
            if (element.equals(t)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return (elements.get(readPos + 1)) != null;
            }

            @Override
            public T next() {
                return ((elements.get(incrementPos(readPos)) != null) ? elements.get(incrementPos(readPos)) : null);
            }
        };
        return it;
    }

    @Override
    public Object[] toArray() {
        return elements.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        if (size() == capacity) {
            //if (!discarding) return false;
            readPos = incrementPos(readPos);
        }
        elements.add(writePos, t);
        writePos = incrementPos(writePos);
        if (size < capacity) this.size++;
        return true;
    }

    private int incrementPos(int pos) {
        return (pos + 1) % this.capacity;
    }

    @Override
    public boolean remove(Object o) {
        if (!elements.contains(o)) return false;
        readPos = incrementPos(readPos);
        this.size--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (T t : elements) if (!c.contains(t)) return false;

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean offer(T t) {
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
        return null;
    }

    @Override
    public T element() {
        return null;
    }

    @Override
    public T peek() {
        if (elements.get(0) != null) return elements.get(0);
        else return null;
    }
}
