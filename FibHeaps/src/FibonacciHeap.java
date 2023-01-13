import java.util.Iterator;

/**
 * FibonacciHeap
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap extends LinkedList
{
    private final static NodeFactory nodeFactory = new NodeFactory();

    /**
     * public boolean isEmpty()
     * Returns true if and only if the heap is empty.
     *
     */
    public boolean isEmpty()
    {
        return super.isEmpty();
    }

    /**
     * public HeapNode insert(int key)
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     * The added key is assumed not to already belong to the heap.
     * Returns the newly created node.
     */
    public HeapNode insert(int key)
    {
        HeapNode node = nodeFactory.createNode(key);
        insertFirst(node);
        return node;
    }

    /**
     * public void deleteMin()
     * Deletes the node containing the minimum key.
     *
     */

    public void deleteMin()
    {
        delete(findMin());
    }

    /**
     * public HeapNode findMin()
     * Returns the node of the heap whose key is minimal, or null if the heap is empty.
     *
     */
    public HeapNode findMin()
    {
        return getMin();
    }

    /**
     * public void meld (FibonacciHeap heap2)
     * Melds heap2 with the current heap.
     *
     */
    public void meld (FibonacciHeap heap2)
    {
        annex(heap2);
    }

    /**
     * public int size()
     * Returns the number of elements in the heap.
     *
     */
    public int size()
    {
        return size;
    }

    /**
     * public int[] countersRep()
     * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
     * (Note: The size of of the array depends on the maximum order of a tree.)
     *
     */
    public int[] countersRep()
    {
        int[] arr = new int[100];
        return arr; //	 to be replaced by student code
    }

    /**
     * public void delete(HeapNode x)
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     *
     */
    public void delete(HeapNode x)
    {
        annex(x.rejectChildren());
        x.eject();
    }


    private void cut(HeapNode x) {
        insertFirst(x.eject());
    }


    /**
     * public void decreaseKey(HeapNode x, int delta)
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta)
    {
        x.key -= delta;
        if (x.hasParent() && x.getParent().getKey() > x.key) {
            cut(x);
        }
    }

    /**
     * public int nonMarked()
     * This function returns the current number of non-marked items in the heap
     */
    public int nonMarked()
    {
        return -232; // should be replaced by student code
    }

    /**
     * public int potential()
     * This function returns the current potential of the heap, which is:
     * Potential = #trees + 2*#marked
     * In words: The potential equals to the number of trees in the heap
     * plus twice the number of marked nodes in the heap.
     */
    public int potential()
    {
        return -234; // should be replaced by student code
    }

    /**
     * public static int totalLinks()
     * This static function returns the total number of link operations made during the
     * run-time of the program. A link operation is the operation which gets as input two
     * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
     * tree which has larger value in its root under the other tree.
     */
    public static int totalLinks()
    {
        return -345; // should be replaced by student code
    }

    /**
     * public static int totalCuts()
     * This static function returns the total number of cut operations made during the
     * run-time of the program. A cut operation is the operation which disconnects a subtree
     * from its parent (during decreaseKey/delete methods).
     */
    public static int totalCuts()
    {
        return -456; // should be replaced by student code
    }

    /**
     * public static int[] kMin(FibonacciHeap H, int k)
     * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
     * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
     * ###CRITICAL### : you are NOT allowed to change H.
     */
    public static int[] kMin(FibonacciHeap H, int k)
    {
        int[] arr = new int[100];
        return arr; // should be replaced by student code
    }

    /**
     * public class HeapNode
     * If you wish to implement classes other than FibonacciHeap
     * (for example HeapNode), do it in this file, not in another file.
     *
     */
    public static class HeapNode{
        public int key;
        public boolean mark;
        public HeapNode next;
        public HeapNode prev;
        public LinkedList children;
        public LinkedList siblings;
        private static final LinkedListFactory listFactory = new LinkedListFactory();

        /**
         * Constructor of HeapNode
         * @param key the key of this node
         */
        public HeapNode(int key) {
            this.key = key;
            children = listFactory.createList(this);
        }


        public String toString() {
            return Integer.toString(key);
        }

        /**
         * Check if this node has a previous node
         * @return true if this node has a previous node, false otherwise
         */
        public boolean hasPrev() {
            return prev != null;
        }

        /**
         * Check if this node has a next node
         * @return true if this node has a next node, false otherwise
         */
        public boolean hasNext() {
            return next != null;
        }


        private boolean hasParent() {
            return getParent() != null;
        }


        public int getKey() {
            return key;
        }


        public HeapNode getParent() {
            return siblings.parent;
        }


        /**
         * Set the previous node of this node
         * @param node the previous node to set
         */
        public void setPrev(HeapNode node) {
            prev = node;

            if (node != null) {
                node.next = this;
            }
        }

        /**
         * Set the next node of this node
         * @param node the next node to set
         */
        public void setNext(HeapNode node) {
            next = node;

            if (node != null) {
                node.prev = this;
            }
        }


        /**
         * Get the number of children of this node
         * @return the number of children of this node
         */
        public int rank() {
            return children.length;
        }

        /**
         * Check if this node is marked
         * @return true if this node is marked, false otherwise
         */
        private boolean isMarked() {
            return mark;
        }

        /**
         * Get the number of nodes in the subtree rooted at this node (including this node)
         * @return the number of nodes in the subtree rooted at this node
         */
        public int getSize() {
            return 1 + children.size;
        }


        /**
         * Calculate the number of nodes in the subtree rooted at this node (including this node)
         * @return the number of nodes in the subtree rooted at this node
         */
        public int calculateSize() {
            int result = 1;
            for (HeapNode child : children) {
                result += child.getSize();
            }
            return result;
        }


        /**
         * Insert a node as the previous node of this node
         * @param node the node to insert as the previous node
         */
        public void insertPrev(HeapNode node) {
            if (hasPrev()) {
                prev.setNext(node);
            }

            setPrev(node);
        }


        public void insertNext(HeapNode node) {
            if (hasNext()) {
                next.setPrev(node);
            }

            setNext(node);
        }

        /**
         * Remove this node from the doubly linked list
         * @return the removed node
         */
        public HeapNode eject() {
            if (hasPrev()) {
                prev.setNext(next);
                setPrev(null);
            }
            if (hasNext()) {
                next.setPrev(prev);
                setNext(null);
            }
            siblings = null;
            return this;
        }


        public void insertChild(HeapNode node) {
            children.insertFirst(node);
        }


        public LinkedList rejectChildren() {
            LinkedList oldChildren = this.children;
            oldChildren.parent = null;
            children = listFactory.createList(this);
            return oldChildren;
        }


        public void plantNext(LinkedList list) {
            if (list == null || list.isEmpty()) {
                return;
            }

            if (hasNext()) {
                next.setPrev(list.tail);
            }

            setNext(list.root);
        }


        public void plantPrev(LinkedList list) {
            if (list == null || list.isEmpty()) {
                return;
            }

            if (hasPrev()) {
                prev.setNext(list.root);
            }

            setPrev(list.tail);
        }
    }
}

class LinkedList implements Iterable<FibonacciHeap.HeapNode> {
    FibonacciHeap.HeapNode root;
    FibonacciHeap.HeapNode tail;
    int length;
    int size;
    FibonacciHeap.HeapNode minNode;
    FibonacciHeap.HeapNode parent;


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (FibonacciHeap.HeapNode node : this) {
            sb.append(node.toString());
            sb.append(node.hasNext() ? ", " : "");
        }
        sb.append(']');
        return sb.toString();
    }


    public boolean isEmpty() {
        return root == null;
    }

    public boolean hasParent() {
        return parent != null;
    }

    private void setSize(int size) {
        if (this.size == size) {
            return;
        }

        this.size = size;
    }

    private void increaseSize(int delta) {
        int newSize = size + delta;
        setSize(newSize);

        if (hasParent()) {
            parent.siblings.increaseSize(delta);
        }
    }

    private void decreaseSize(int delta) {
        if (delta < 0) {
            increaseSize(delta);
        }

        increaseSize(-delta);

        if (hasParent()) {
            parent.siblings.decreaseSize(delta);
        }
    }

    /*@pre: NOT NULL*/
    public void insertFirst(FibonacciHeap.HeapNode node) {
        if (!isEmpty()) {
            root.insertPrev(node);
        } else {
            tail = node;
        }
        node.siblings = this;
        root = node;
        length++;
        increaseSize(node.getSize());

        if (minNode == null || node.key < minNode.key) {
            minNode = node;
        }
    }


    private FibonacciHeap.HeapNode deleteNode(FibonacciHeap.HeapNode node) {
        if (root == node) {
            root = node.next;
        }
        if (tail == node) {
            tail = node.prev;
        }
        node.eject();
        if (node == minNode) {
            updateMin();
        }
        decreaseSize(node.getSize());
        length--;
        return node;
    }


    public FibonacciHeap.HeapNode deleteKey(int key) {
        for (FibonacciHeap.HeapNode node : this) {
            if (node.key != key) {
                continue;
            }
            return deleteNode(node);
        }
        return null;
    }


    private void updateMin() {
        FibonacciHeap.HeapNode result = root;
        for (FibonacciHeap.HeapNode node : this) {
            if (node.key < result.key) {
                result = node;
            }
        }
        minNode = result;
    }


    public void deleteMin() {
        deleteNode(minNode);
    }

    public FibonacciHeap.HeapNode getMin() {
        return minNode;
    }

    public void annex(LinkedList list2) {
        list2.parent = this.parent;
        if (list2.isEmpty()) {
            return;
        }
        this.tail.setNext(list2.root);
        this.tail = list2.tail;
        this.length += list2.length;
        this.increaseSize(list2.size);
    }

    /**
     *
     * @param list2 the list to be planted to this list
     * @param nodeAfter the node to be after the planted list. null iff annex
     * @post this.size is updated
     * @post this.length is updated
     */
    public void plant(LinkedList list2, FibonacciHeap.HeapNode nodeAfter) {
        list2.parent = this.parent;
        if (nodeAfter == null) {
            annex(list2);
            return;
        }
        nodeAfter.plantPrev(list2);
        this.length += list2.length;
        increaseSize(list2.size);
        if (nodeAfter == this.root) {
            this.root = list2.root;
        }
    }



    // Iterators

    @Override
    public Iterator<FibonacciHeap.HeapNode> iterator() {
        return new LinkedListIterator(this);
    }

    public Iterator<Integer> keyIterator() {
        return new LinkedListKeyIterator(this);
    }

    static class LinkedListIterator implements Iterator<FibonacciHeap.HeapNode> {
        FibonacciHeap.HeapNode current;

        public LinkedListIterator(LinkedList list) {
            current = list.root;
        }


        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public FibonacciHeap.HeapNode next() {
            FibonacciHeap.HeapNode result = current;
            current = current.next;
            return result;
        }
    }

    static class LinkedListKeyIterator implements Iterator<Integer> {
        FibonacciHeap.HeapNode current;

        public LinkedListKeyIterator(LinkedList list) {
            current = list.root;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Integer next() {
            int result = current.key;
            current = current.next;
            return result;
        }
    }

}

class NodeFactory {
    public FibonacciHeap.HeapNode createNode(int key) {
        return new FibonacciHeap.HeapNode(key);
    }
}

class LinkedListFactory {
    public LinkedList createList(FibonacciHeap.HeapNode parent) {
        LinkedList result = new LinkedList();
        result.parent = parent;
        return result;
    }
}
