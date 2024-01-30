
/* An implementation of the Stack (LIFO) ADT using a Java Array */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private E[] array;
    private int size;

    public ArrayStack() {
        this.array = (E[]) new Object[10];
        this.size = 0;
    }

    @Override
    public void add(E work) {
        if (this.size == this.array.length) {
            E[] newArray = (E[]) new Object[this.array.length * 2];

            for (int i = 0; i < this.array.length; i++) {
                newArray[i] = this.array[i];
            }

            this.array = newArray;
        }
        this.array[this.size] = work;
        this.size++;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return this.array[this.size - 1];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }

        this.size--;
        return this.array[this.size];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size = 0;
    }
}
