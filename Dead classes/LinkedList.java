import java.util.Iterator;

public class LinkedList implements Iterable<HeapNode> {
    HeapNode root;
    HeapNode tail;
    int length;
    int size;
    HeapNode minNode;
    HeapNode parent;


    /**
     * Helper method for heap.deleteMin()
     * Deletes the given node from the list, and plants the children in his place by order
     * @return HeapNode
     * time complexity: O(1)
     */
    public void deleteMin() {
        // Saves pointer to the node that is about to be deleted to use the location pointers later
        HeapNode nodeToDelete = minNode;
        // Saves children of the node that is about to be deleted to insert them later
        LinkedList children = nodeToDelete.children;
        // Deletes the node from the list (Linked list cut is equivalent to Node delete with children)
        ripNode(nodeToDelete);
        // Plants the deleted node children nodes according to his old location in the list
        plantBefore(children, nodeToDelete.next);
    }


    /**
     * Helper method for heap.DecreaseKey() - works with node.nodeDecreaseKey()
     * @return HeapNode if found, else null
     * time complexity: O(n)
     */
    public HeapNode listDecreaseKey(int key, int d, FibonacciHeap heap) {
        // Loop runs through every node in the list
        for (HeapNode node : this) {
            // Start of recursive call for each tree in the forest, to find the node and decrease the key
            HeapNode retrievedNode = node.nodeDecreaseKey(key, d, heap);
            // If the node is not found in the subtree, the return value is null
            if (retrievedNode != null) {
                // Returns the node after decrease of key by delta
                return retrievedNode;
            }
        }
        // If node not found in any tree in the forest
        return null;
    }


    /**
     * String representation of LinkedList
     * @return [key_root, key_2, ..., key_tail]
     */
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
    public HeapNode getMin() {
        return minNode;
    }


    /**
     * recursively search for the node with the provided key within the children list of each node,
     * only if the requested key is not directly inside the list.
     * @param key the requested key to search
     * @return the node that corresponds to the key
     */
    public HeapNode findRecursive(int key) {
        // Added this to prioritize upper-most nodes. CAN BE REMOVED
        for (HeapNode node: this) {
            if (node.getKey() == key) {
                return node;
            }
        }

        // Original procedure
        for (HeapNode node : this) {
            HeapNode retrievedNode = node.findRecursive(key);
            if (retrievedNode != null) {
                return retrievedNode;
            }
        }
        return null;
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
        // Calculate new size and call size setter
        int newSize = size + delta;
        setSize(newSize);

        // Recursively update all parent lists' sizes.
        if (hasParent() && parent.siblings != null) {
            parent.siblings.increaseSize(delta);
        }
    }

    /**
     * Call increaseSize with -delta,
     * recursively call the parent's siblings' increaseSize with delta
     * @param delta a positive decrement of this list size
     */
    public void decreaseSize(int delta) {
        // In case of a negative decrease, call increaseSize.
        if (delta < 0) {
            increaseSize(delta);
        }

        // Call increaseSize with a negative argument
        increaseSize(-delta);

        // Recursively update all parent lists' sizes.
        if (hasParent() && parent.siblings != null) {
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
    public void insertFirst(HeapNode node) {

        if (!isEmpty()) {
            // When possible, rely on node's insertion methods
            root.insertPrev(node);
        } else {
            // Iff the list is currently empty, the node will be the new tail.
            tail = node;
        }

        // Set the siblings pointer of the added node
        node.siblings = this;

        // Set the new root of this list.
        root = node;

        // Update length and size attributes of this list.
        length++;
        increaseSize(node.getSize());

        // Update minNode pointer of this list.
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
        // Update annexed list's parent pointer
        list2.parent = this.parent;

        // If list2 is empty, nothing more is to be done.
        if (list2.isEmpty()) {
            return;
        }

        if (this.isEmpty()) {
            // Iff this list is empty, then the root of the annexed list will be the root of this list.
            this.root = list2.root;
            // If this list is empty, then minNode is initialized, and minNode will be updated.
            this.minNode = list2.minNode;
        }
        else {
            // Connect the root of list2 to the
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
    public void plantBefore(LinkedList list2, HeapNode nodeAfter) {
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

    public HeapNode cutNode(HeapNode node) {
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

    public void ripNode(HeapNode node) {
        if (root == node) {
            root = node.next;
        }
        if (tail == node) {
            tail = node.prev;
        }
        node.eject();
        decreaseSize(node.getSize());
        length--;
    }


    public HeapNode cutKey(int key) {
        for (HeapNode node : this) {
            if (node.key != key) {
                continue;
            }
            return cutNode(node);
        }
        return null;
    }

    public void cutMin() {
        cutNode(minNode);
    }

    ///////////////////
    // OTHER METHODS //
    ///////////////////

    public void updateMin() {
        HeapNode result = root;
        for (HeapNode node : this) {
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
    public Iterator<HeapNode> iterator() {
        return new LinkedListIterator(this);
    }
    //////////////




}


class LinkedListFactory {
    public LinkedList createList(HeapNode parent) {
        LinkedList result = new LinkedList();
        result.parent = parent;
        return result;
    }
}


class LinkedListIterator implements Iterator<HeapNode> {
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
