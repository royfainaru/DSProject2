import java.util.Iterator;

/**
 * FibonacciHeap
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
    private final static NodeFactory nodeFactory = new NodeFactory();
    LinkedList rootList = new LinkedList();

    /**
     * public boolean isEmpty()
     * Returns true if and only if the heap is empty.
     *
     */
    public boolean isEmpty()
    {
        return rootList.isEmpty();
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
        rootList.insertFirst(node);
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
        return rootList.getMin();
    }

    /**
     * public void meld (FibonacciHeap heap2)
     * Melds heap2 with the current heap.
     *
     */
    public void meld (FibonacciHeap heap2)
    {
        rootList.annex(heap2.rootList);
    }

    /**
     * public int size()
     * Returns the number of elements in the heap.
     *
     */
    public int size()
    {
        return rootList.size;
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
        rootList.annex(x.rejectChildren());
        x.eject();
    }


    private void cut(HeapNode x) {
        rootList.insertFirst(x.eject());
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
        // add logic to check if we need to Cascade Cut
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

        /**
         * String representation of HeapNode
         * @return the key of the node
         */
        public String toString() {
            return Integer.toString(key);
        }


        ///////////////////
        // 'HAS' METHODS //
        ///////////////////

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

        /**
         * Check if this node has a parent.
         * iff not, then the node must be in rootList.
         * @return true iff this node has a parent
         */
        public boolean hasParent() {
            return getParent() != null;
        }


        ///////////////////
        // 'GET' METHODS //
        ///////////////////

        /**
         * @return the key of this node
         */
        public int getKey() {
            return key;
        }

        /**
         * @return the parent of the sibling LinkedList
         */
        public HeapNode getParent() {
            return siblings.parent;
        }

        /**
         * Get the number of children of this node
         * @return the number of children of this node
         */
        public int rank() {
            return children.length;
        }

        /**
         * Get the number of nodes in the subtree rooted at this node (including this node)
         * @return the number of nodes in the subtree rooted at this node
         */
        public int getSize() {
            return 1 + children.size;
        }

        /**
         * Check if this node is marked
         * @return true if this node is marked, false otherwise
         */
        private boolean getMark() {
            return mark;
        }


        ///////////////////
        // 'SET' METHODS //
        ///////////////////

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
         * sets the mark of the node according to the parameter given.
         * @param mark is the boolean value to set
         */
        // NEED TO COMPLETE WITH POINTER TO HEAP
        public void setMark(boolean mark) {
            this.mark = mark;
            // heap.increaseMarks
        }

        ///////////////////////
        // INSERTION METHODS //
        ///////////////////////

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
        /**
         * Insert a node as the next node of this node
         * @param node the node to insert as the next node
         */
        public void insertNext(HeapNode node) {
            if (hasNext()) {
                next.setPrev(node);
            }

            setNext(node);
        }

        /**
         * Insert a node to the header of the children list of this node
         * @param node the new child of this node
         */
        public void insertChild(HeapNode node) {
            children.insertFirst(node);
        }

        /**
         * Plant a linked list next to this node.
         * Updates length, size, and relevant node pointers.
         * @param list the linked list to be planted next to this node
         */
        public void plantNext(LinkedList list) {
            if (list == null || list.isEmpty()) {
                return;
            }

            if (hasNext()) {
                next.setPrev(list.tail);
            }

            setNext(list.root);
        }

        /**
         * Plant a linked list previous to this node.
         * Updates length, size, and relevant node pointers.
         * @param list the linked list to be planted before this node
         */
        public void plantPrev(LinkedList list) {
            if (list == null || list.isEmpty()) {
                return;
            }

            if (hasPrev()) {
                prev.setNext(list.root);
            }

            setPrev(list.tail);
        }


        //////////////////////
        // EJECTION METHODS //
        //////////////////////

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

        /**
         * disconnects the children list from this node,
         * creates and assigns a new children list to this node,
         * decreases the sibling list's size,
         * and returns the list of original children of this node.
         * @return the original children linked list of this node
         */
        public LinkedList rejectChildren() {
            LinkedList oldChildren = this.children;
            oldChildren.parent = null;
            children = listFactory.createList(this);
            siblings.decreaseSize(oldChildren.size);
            return oldChildren;
        }

        /**
         * Rejects the children of this node,
         * deletes this node from the siblings list,
         * plants the children list in place of this node's original position,
         * decreases the siblings list's size by 1
         */
        public void plantUp() {
            HeapNode next = this.next;
            LinkedList children = rejectChildren();
            siblings.deleteNode(this);
            siblings.plantBefore(children, next);
            siblings.decreaseSize(1);
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

    /**
     * String representation of LinkedList
     * @return [key_root, key_2, ..., key_tail]
     */
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


    ///////////////////
    // 'HAS' METHODS //
    ///////////////////

    /**
     * @return true iff root is null
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return true iff parent is not null
     */
    public boolean hasParent() {
        return parent != null;
    }


    ///////////////////
    // 'GET' METHODS //
    ///////////////////

    /**
     * @return the minimal node from a designated pointer 'minNode'
     */
    public FibonacciHeap.HeapNode getMin() {
        return minNode;
    }


    ///////////////////
    // 'SET' METHODS //
    ///////////////////

    /**
     * Set the size of this list
     * @param size the new size of the list
     */
    private void setSize(int size) {
        if (this.size == size) {
            return;
        }

        this.size = size;
    }

    /**
     * Calculate newSize = currentSize + delta and call setSize(newSize),
     * recursively call the parent's siblings' increaseSize with delta
     * @param delta the increment in this list size
     */
    public void increaseSize(int delta) {
        int newSize = size + delta;
        setSize(newSize);

        if (hasParent()) {
            parent.siblings.increaseSize(delta);
        }
    }

    /**
     * Call increaseSize with -delta,
     * recursively call the parent's siblings' increaseSize with delta
     * @param delta a positive decrement of this list size
     */
    public void decreaseSize(int delta) {
        if (delta < 0) {
            increaseSize(delta);
        }

        increaseSize(-delta);

        if (hasParent()) {
            parent.siblings.decreaseSize(delta);
        }
    }


    ///////////////////////
    // INSERTION METHODS //
    ///////////////////////

    /**
     * Insert the parameter node as the header of this list,
     * set the parameter node's siblings list to this list,
     * update root attribute to the parameter node, and also the tail node if necessary.
     * updates length and size of this list
     * updates minNode pointer of this list, if necessary.
     * @param node the node to be inserted at the header of this list
     */
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

    /**
     * Concatenate the param 'list2' to the tail of this list.
     * updates list pointers as relevant, including tail, root, and minNode
     * updates length and size of this list
     * @param list2 the list to be annexed to this list
     */
    public void annex(LinkedList list2) {
        list2.parent = this.parent;
        if (list2.isEmpty()) {
            return;
        }
        if (this.isEmpty()) {
            this.root = list2.root;
            this.minNode = list2.minNode;
        }
        else {
            this.tail.setNext(list2.root);
            this.minNode = (this.minNode.key < list2.minNode.key) ? this.minNode : list2.minNode;
        }
        this.tail = list2.tail;
        this.length += list2.length;
        this.increaseSize(list2.size);
    }

    /**
     * @param list2 the list to be planted to this list
     * @param nodeAfter the node to be after the planted list. null iff annex
     */
    public void plantBefore(LinkedList list2, FibonacciHeap.HeapNode nodeAfter) {
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


    ////////////////////
    // DELETE METHODS //
    ////////////////////

    public FibonacciHeap.HeapNode deleteNode(FibonacciHeap.HeapNode node) {
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

    public void deleteMin() {
        deleteNode(minNode);
    }


    ///////////////////
    // OTHER METHODS //
    ///////////////////

    private void updateMin() {
        FibonacciHeap.HeapNode result = root;
        for (FibonacciHeap.HeapNode node : this) {
            if (node.key < result.key) {
                result = node;
            }
        }
        minNode = result;
    }


    ///////////////
    // ITERATORS //
    ///////////////

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



    //////////////

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
