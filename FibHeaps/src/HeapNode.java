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

class NodeFactory {
    public HeapNode createNode(int key) {
        return new HeapNode(key);
    }
}
