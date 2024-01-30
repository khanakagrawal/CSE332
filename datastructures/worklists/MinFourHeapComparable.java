/* An implementation of a Heap where every node can have upto 4 children using an array */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int last;

    public MinFourHeapComparable() {
        this.last = 0;
        this.data = (E[]) new Comparable[15];
    }

    @Override
    public boolean hasWork() {
        return this.last != 0;
    }

    @Override
    public void add(E work) {
        if (!hasWork()) {
            this.data[0] = work;
            this.last++;
            return;
        }
        int insert = this.last;

        //if the array is full
        if (this.last == this.data.length) {
            E[] n = (E[]) new Comparable[this.data.length * 2];
            for (int i = 0; i < this.data.length; i++) {
                n[i] = this.data[i];
            }
            this.data = n;
        }

        data[insert] = work;
        int parent = (insert/4);
        if ((insert != 0) && (insert % 4 == 0)) {
            parent--;
        }

        //percolate up
        while ((data[insert].compareTo(data[parent])) < 0) {
            E temp = data[insert];
            data[insert] = data[parent];
            data[parent] = temp;
            insert = parent;
            parent = (insert/4);
            if ((insert != 0) && (insert % 4 == 0)) {
                parent--;
            }
        }
        this.last++;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }

        return this.data[0];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }

        E next = this.data[0];
        if (this.last == 1) {
            this.last--;
            return next;
        }

        this.last--;
        this.data[0] = this.data[this.last];
        this.data[this.last] = null;

        int node = 0;

        //finding the smallest child
        int child = 1;
        for (int i = 2; i < 5; i++) {
            if ((i < this.data.length) &&
                    (this.data[i] != null) &&
                    (this.data[i].compareTo(this.data[child]) < 0)) {
                child = i;
            }
        }

        //percolate down
        while((child < this.data.length) && (this.data[child] != null) &&
                this.data[node].compareTo(this.data[child]) > 0){
            E temp = this.data[node];
            this.data[node] = this.data[child];
            this.data[child] = temp;
            node = child;

            child = 4*node+1;
            for (int i = 4*node+2; i < 4*node+5; i++) {
                if ((i < this.data.length) &&
                        (this.data[i] != null) &&
                        (this.data[i].compareTo(this.data[child]) < 0)) {
                    child = i;
                }
            }
        }

        return next;
    }

    @Override
    public int size() {
        return this.last;
    }

    @Override
    public void clear() {
        this.last = 0;
    }
}