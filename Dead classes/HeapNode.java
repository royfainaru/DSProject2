/**
 * public class HeapNode
 * If you wish to implement classes other than FibonacciHeap
 * (for example HeapNode), do it in this file, not in another file.
 *
 */
public class HeapNode{
    public int key;
    public boolean mark;
    public HeapNode next;
    public HeapNode prev;
    public LinkedList children;
    public LinkedList siblings;
    public FibonacciHeap heap;
    private static final LinkedListFactory listFactory = new LinkedListFactory();


    /**
     * Helper method for heap.DecreaseKey() - works with list.listDecreaseKey()
     * @return HeapNode if found, else null
     * time complexity: O(n)
     */
    public HeapNode nodeDecreaseKey(int key, int d, FibonacciHeap heap) {
        // Checks for searched key to decrease
        if (key == this.getKey()) {
            // Case of heap.delete(node) - implemented by decrease to minimum and deleteMin
            if (d == Integer.MAX_VALUE) {
                this.key = Integer.MIN_VALUE;
            } else {
                // Decreases key by given delta
                this.key -= d;
            }
            // Checks if the parent node is a root node of the heap
            HeapNode parent = this.getParent();
            // only cuts and marks parent if parent node is not a root node in the heap forest
            if (hasParent()) {
                // If the heap rule is violated, the node and his children will be cut from his parent
                if (parent.getKey() >= this.getKey()) {
                    // Cutting the node with his children from parent node and sibling linked list
                    cut();
                    // Update the Cuts heap counter
                    heap.increaseCuts();
                    // The setMark method return value indicates if changes in the node mark were made by the operation
                    boolean markFlag = this.setMark(false);
                    // Only if changes were made, the heap counter will be updated accordingly
                    if (markFlag) {
                        heap.decreaseMarked();
                    }
                    // Insert the cut node with his children back to the heap forest as independent trees
                    heap.rootList.insertFirst(this);
                }
            } else {
                // If the node is a root node
                heap.rootList.updateMin();
            }
            // Return the searched node to the recursion
            return this;
        }

        // If the node has no children the recursive search ends for this tree, returns null as indicator to the recursion
        if (children.isEmpty()) {
            return null;
        }

        // Saves the size of children linked list to compare as indicator for changes in the subtrees
        int childrenSizeBefore = children.size;
        // Recursive call on the children linked list in order to find the node with the key to decrease
        HeapNode returnedNode = children.listDecreaseKey(key, d, heap);

        // The returned value from the recursion is null if the searched node is not found in the subtree
        if (returnedNode != null) {
            // Checks if the parent node is a root node of the heap
            // only cuts and marks parent if parent node is not a root node in the heap forest
            if (hasParent()) {
                // If a child node was cut for this node, the length of children linked list was decreased
                if (childrenSizeBefore > children.size) {
                    // If current node was not marked before the operation
                    if (!getMark()) {
                        // The setMark method return value indicates if changes in the node mark were made by the operation
                        boolean markFlag = this.setMark(true);
                        // Only if changes were made, the heap counter will be updated accordingly
                        if (markFlag) {
                            heap.increaseMarked();
                        }
                    } else { // The current node was already marked (start of cascading cuts)
                        // Cutting the node with his children from parent node and sibling linked list
                        cut();
                        // Update the Cuts heap counter
                        heap.increaseCuts();
                        // The setMark method return value indicates if changes in the node mark were made by the operation
                        boolean markFlag = this.setMark(false);
                        // Only if changes were made, the heap counter will be updated accordingly
                        if (markFlag) {
                            heap.decreaseMarked();
                        }
                        // Insert the cut node with his children back to the heap forest as independent trees
                        heap.rootList.insertFirst(this);
                    }
                }
            }
        }
        // Return the searched node to the recursion
        return returnedNode;
    }


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
     * @return String with the key of the node
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
     * @return the parent of the siblings LinkedList
     */
    public HeapNode getParent() {
        if (siblings == null) {
            return null;
        }
        return siblings.parent;
    }

    /**
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
     * @return true iff this node is marked
     */
    private boolean getMark() {
        return mark;
    }


    /**
     * recursively searches for the node with the given key in the tree rooted at this node,
     * including this node.
     * @param key the key value to be searched
     * @return the node with the requested key, null if not found
     * Time Complexity: O(n), since the tree is not a search tree.
     */
    public HeapNode findRecursive(int key) {
        // Successful edge case.
        if (key == this.getKey()) {
            return this;
        }

        // Unsuccessful edge case.
        if (children.isEmpty()) {
            return null;
        }

        // Recursive call.
        return children.findRecursive(key);
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

        // Also take care of the other node's relevant pointer.
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

        // Also take care of the other node's relevant pointer.
        if (node != null) {
            node.prev = this;
        }
    }

    /**
     * sets the mark of the node according to the parameter given.
     * @param mark is the boolean value to set
     */
    // NEED TO COMPLETE WITH POINTER TO HEAP
    public boolean setMark(boolean mark) {
        boolean oldMark = this.getMark();
        this.mark = mark;
        return oldMark != mark;
    }

    ///////////////////////
    // INSERTION METHODS //
    ///////////////////////

    /**
     * Insert a node as the previous node of this node
     * @param node the node to insert as the previous node
     */
    public void insertPrev(HeapNode node) {
        // Take care of prev.next and node.prev pointers.
        if (hasPrev()) {
            prev.setNext(node);
        }

        // Take care of this.prev and node.next pointers.
        setPrev(node);
    }
    /**
     * Insert a node as the next node of this node
     * @param node the node to insert as the next node
     */
    public void insertNext(HeapNode node) {
        // Take care of next.prev and node.next pointers
        if (hasNext()) {
            next.setPrev(node);
        }

        // Take care of this.next and node.prev pointers
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
     * Plant a linked list previous to this node.
     * Updates length, size, and relevant node pointers.
     * @param list the linked list to be planted before this node
     */
    public void plantPrev(LinkedList list) {
        // Trivial case.
        if (list == null || list.isEmpty()) {
            return;
        }

        // Take care of prev.next and list.root.prev pointers.
        if (hasPrev()) {
            prev.setNext(list.root);
        }

        // Take care of this.prev and list.tail.next pointers.
        setPrev(list.tail);
    }


    //////////////////////
    // EJECTION METHODS //
    //////////////////////

    /**
     * Remove this node from the doubly linked list:
     * set pointers to next, prev and 'siblings' to 'null'
     * @return the removed node
     */
    public void eject() {
        // Connect prev node to the next node
        if (hasPrev()) {
            prev.setNext(next);
            setPrev(null);
        }

        // Connect next node to the prev node
        if (hasNext()) {
            next.setPrev(prev);
            setNext(null);
        }

        // Nullify siblings pointer
        siblings = null;
    }

    /**
     * disconnects the children list from this node,
     * creates and assigns a new children list to this node,
     * decreases the sibling list's size (if siblings != null),
     * and returns the list of original children of this node.
     * @return the original children linked list of this node
     */
    public LinkedList rejectChildren() {
        LinkedList oldChildren = this.children;

        // Nullify children's parent pointer.
        oldChildren.parent = null;

        // Get a new children list from the factory.
        children = listFactory.createList(this);

        // Updates the size of the siblings list (and bubbles up an update call).
        if (siblings != null) {
            siblings.decreaseSize(oldChildren.size);
        }

        // Returns the rejected children.
        return oldChildren;
    }


    public void cut() {
        // Refer to LinkedList's cut function.
        siblings.cutNode(this);
    }


}

class NodeFactory {
    FibonacciHeap heap;

    public NodeFactory(FibonacciHeap heap) {
        this.heap = heap;
    }

    public HeapNode createNode(int key) {
        HeapNode node = new HeapNode(key);
        node.heap = heap;
        return node;
    }
}