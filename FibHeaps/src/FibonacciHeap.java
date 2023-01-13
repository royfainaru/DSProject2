import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.List;

/**
 * FibonacciHeap
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{

    /**
     * public boolean isEmpty()
     * Returns true if and only if the heap is empty.
     *
     */
    public boolean isEmpty()
    {
        return false; // should be replaced by student code
    }

    /**
     * public HeapNode insert(int key)
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     * The added key is assumed not to already belong to the heap.
     * Returns the newly created node.
     */
    public HeapNode insert(int key)
    {
        return new HeapNode(key); // should be replaced by student code
    }

    /**
     * public void deleteMin()
     * Deletes the node containing the minimum key.
     *
     */
    public void deleteMin()
    {
        return; // should be replaced by student code

    }

    /**
     * public HeapNode findMin()
     * Returns the node of the heap whose key is minimal, or null if the heap is empty.
     *
     */
    public HeapNode findMin()
    {
        return new HeapNode(678);// should be replaced by student code
    }

    /**
     * public void meld (FibonacciHeap heap2)
     * Melds heap2 with the current heap.
     *
     */
    public void meld (FibonacciHeap heap2)
    {
        return; // should be replaced by student code
    }

    /**
     * public int size()
     * Returns the number of elements in the heap.
     *
     */
    public int size()
    {
        return -123; // should be replaced by student code
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
        return; // should be replaced by student code
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta)
    {
        return; // should be replaced by student code
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
        /**
         * parent: the node that is the parent
         * key: the key of this HeapNode
         * mark: flag to mark the node
         * next: next node in the doubly linked list
         * prev: previous node in the doubly linked list
         * children: linked list of children of this node
         * size: number of nodes in the subtree rooted at this node (including this node)
         */
        public int key;
        public boolean mark;
        public HeapNode next;
        public HeapNode prev;
        public HeapNode parent;
        public LinkedList children;
        LinkedList siblings;

        /**
         * Constructor of HeapNode
         * @param key the key of this node
         */
        public HeapNode(int key) {
            this.key = key;
            children = new LinkedList();
        }


        public String toString() {
            return Integer.toString(key);
        }


        /**
         * Check if this node has a previous node
         * @return true if this node has a previous node, false otherwise
         */
        private boolean hasPrev() {
            return prev != null;
        }

        /**
         * Check if this node has a next node
         * @return true if this node has a next node, false otherwise
         */
        private boolean hasNext() {
            return next != null;
        }


        private boolean hasParent() {
            return parent != null;
        }


        public int getKey() {
            return key;
        }


        /**
         * Set the previous node of this node
         * @param node the previous node to set
         */
        private void setPrev(HeapNode node) {
            prev = node;

            if (node != null) {
                node.next = this;
            }
        }

        /**
         * Set the next node of this node
         * @param node the next node to set
         */
        private void setNext(HeapNode node) {
            next = node;

            if (node != null) {
                node.prev = this;
            }
        }

        private void setParent(HeapNode node) {
            parent = node;
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

            if (hasParent()) {
                setParent(null);
            }

            return this;
        }


        public void insertChild(HeapNode node) {
            children.insertFirst(node);
        }



    }

    static class LinkedList implements Iterable<HeapNode> {
        HeapNode root;
        int length;
        int size;
        HeapNode minNode;
        HeapNode parent;


        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (HeapNode node : this) {
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
        public void insertFirst(HeapNode node) {
            if (!isEmpty()) {
                root.insertPrev(node);
            }

            root = node;
            node.siblings = this;
            length++;
            increaseSize(node.getSize());

            if (minNode == null || node.key < minNode.key) {
                minNode = node;
            }
        }


        private HeapNode deleteNode(HeapNode node) {
            if (root == node) {
                root = node.next;
            }
            if (node.eject() == minNode) {
                updateMin();
            }
            decreaseSize(node.getSize());
            length--;
            return node;
        }


        public HeapNode deleteKey(int key) {
            for (HeapNode node : this) {
                if (node.key != key) {
                    continue;
                }
                return deleteNode(node);
            }
            return null;
        }


        private void updateMin() {
            HeapNode minNode = root;
            for (HeapNode node: this) {
                if (node.key < minNode.key) {
                       minNode = node;
                }
            }
            this.minNode = minNode;
        }


        public HeapNode deleteMin() {
            return deleteNode(minNode);
        }

        public HeapNode getMin() {
            return minNode;
        }


        //NODE ITERATOR
        @Override
        public Iterator<HeapNode> iterator() {
            return new LinkedListIterator(this);
        }
        static class LinkedListIterator implements Iterator<HeapNode> {
            HeapNode current;

            public LinkedListIterator(LinkedList list) {
                current = list.root;
            }


            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public HeapNode next() {
                HeapNode result = current;
                current = current.next;
                return result;
            }
        }

        // KEY ITERATOR
        public Iterator<Integer> keyIterator() {
            return new LinkedListKeyIterator(this);
        }

        static class LinkedListKeyIterator implements Iterator<Integer> {
            HeapNode current;

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


    static class NodeFactory {
        public HeapNode createNode(int key) {
            return new HeapNode(key);
        }
    }

}
