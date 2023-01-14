import java.util.Iterator;

public class LinkedList implements Iterable<HeapNode> {
    HeapNode root;
    HeapNode tail;
    int length;
    int size;
    HeapNode minNode;
    HeapNode parent;

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
    public void insertFirst(HeapNode node) {
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

    public HeapNode deleteNode(HeapNode node) {
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

    public HeapNode deleteKey(int key) {
        for (HeapNode node : this) {
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

    public Iterator<Integer> keyIterator() {
        return new LinkedListKeyIterator(this);
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


class LinkedListKeyIterator implements Iterator<Integer> {
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