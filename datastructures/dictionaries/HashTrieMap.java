/* An implementation of the Trie, which is a tree-based structure of string-keyed data where each 
branch stores part of a key and leads to a value, using TreeMaps. */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        if (key.isEmpty()) {
            V val = this.root.value;
            this.root.value = value;
            return val;
        }

        if (this.root.pointers == null) {
            return null;
        }

        HashTrieNode node = (HashTrieNode) this.root;

        for (A s: key) {
            if (node.pointers.containsKey(s)) {
                node = node.pointers.get(s);
            } else {
                HashTrieNode newNode = new HashTrieNode();
                node.pointers.put(s, newNode);
                node = newNode;
            }
        }

        V val = node.value;
        node.value = value;
        return val;

    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        if (key.isEmpty()) {
            return this.root.value;
        }

        if (this.root.pointers == null) {
            return null;
        }


        HashTrieNode node = (HashTrieNode) this.root;

        for (A s: key) {
            if (node.pointers.containsKey(s)) {
                node = node.pointers.get(s);
            } else {
                return null;
            }
        }

        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public boolean findPrefix(K key) {

        if (key.isEmpty()) {
            return true;
        }

        HashTrieNode node = (HashTrieNode) this.root;

        for (A s: key) {
            if (node.pointers.containsKey(s)) {
                node = node.pointers.get(s);
            } else {
                return false;
            }
        }

        return node != null;
    }

    @Override
    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        ArrayStack<HashTrieNode> nodesToDelete = new ArrayStack<HashTrieNode>();
        ArrayStack<A> lettersToDelete = new ArrayStack<A>();

        HashTrieNode node = (HashTrieNode) this.root;
        for (A s: key) {
            nodesToDelete.add(node);
            lettersToDelete.add(s);

            if (node.pointers!= null && node.pointers.containsKey(s)) {
                node = node.pointers.get(s);
            } else {
                return;
            }
        }

        node.value = null;

        while(nodesToDelete.hasWork()) {
            HashTrieNode n = nodesToDelete.next();
            A l = lettersToDelete.next();

            if (n.pointers.get(l).value == null && n.pointers.get(l).pointers.isEmpty() && lettersToDelete.hasWork()) {
                n.pointers.remove(l);
            }
        }
    }

    @Override
    public void clear() {
        this.root = null;
    }
}