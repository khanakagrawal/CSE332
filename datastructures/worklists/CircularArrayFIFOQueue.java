/*An implementation of the Queue ADT using the concept of a fixed size circular array. 
A circular array is where the start index can be anywhere in the array and as data is added, 
the array would wrap around. */
public class CircularArrayFIFOQueue<E> extends FixedSizeFIFOWorkList<E> {
    private E[] array;
    private int front;
    private int size;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.array = (E[])new Comparable[capacity];
        this.front = 0;
        this.size = 0;
    }

    @Override
    public void add(E work) {
        if (isFull()) {
            throw new IllegalStateException();
        }

        this.array[(this.front + this.size) % this.array.length] = work;
        this.size++;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }

        return this.array[this.front];
    }

    @Override
    public E peek(int i) {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }

        if (i >= this.size) {
            throw new IndexOutOfBoundsException();
        }

        return this.array[(this.front + i) % this.array.length];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }

        E work = this.array[this.front];
        this.front = (this.front + 1) % this.array.length;
        this.size--;

        return work;
    }

    @Override
    public void update(int i, E value) {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }

        if (i >= size()) {
            throw new IndexOutOfBoundsException();
        }

        this.array[(this.front + i) % this.array.length] = value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.front = 0;
        this.size = 0;
    }
}