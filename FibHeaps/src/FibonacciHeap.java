import java.util.Iterator;

/**
 * FibonacciHeap
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap {
    private final NodeFactory nodeFactory = new NodeFactory(this); // For initialization of new nodes
    LinkedList rootList = new LinkedList(); // The representation for the top of the heap

    private static int links = 0; // Counter for total links
    private static int cuts = 0; // Counter for total cuts
    private int marked = 0; // Counter for number of current marked nodes in heap


    public HeapNode getFirst() {
        return rootList.root;
    }


    /**
     * public boolean isEmpty()
     * Returns true if and only if the heap is empty.
     * time complexity: O(1)
     */
    public boolean isEmpty() {
        // Uses the linked list method for the representation of the top of the heap (the forest)
        return rootList.isEmpty();
    }

    /**
     * public HeapNode insert(int key)
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     * The added key is assumed not to already belong to the heap.
     * Returns the newly created node.
     * time complexity: O(1)
     */
    public FibonacciHeap.HeapNode insert(int key) {
        // Initialize a new node object
        HeapNode node = nodeFactory.createNode(key);
        // Insert the new node to the forest, by using a linked list method on the top of the heap
        rootList.insertFirst(node);
        // Returns the newly created node.
        return node;
    }

    /**
     * public void deleteMin()
     * Deletes the node containing the minimum key.
     * Returns null
     * time complexity: O(n)
     */
    public void deleteMin() {
        // If the heap is empty, no minNode to delete
        if (isEmpty()) {
            return;
        }

        // Call the rootList method as a linked list to delete the minNode
        rootList.deleteMin();

        // Call the linking process of the heap
        consolidate();

        // Update min node pointer
        rootList.updateMin();
    }

    /**
     * private static double log(double base, double arg)
     * Computes log function according to the given base and number, using Math.log10()
     * Returns the result of the calculation
     * time complexity: O(1)
     */
    private static double log(double base, double arg) {
        return Math.log10(arg) / Math.log10(base);
    }


    /**
     * private int maxRankUpperBound()
     * This function is used to calculate the upper bound of the maximum rank in the heap.
     * Returns the upper bound
     * time complexity: O(1)
     */
    private int maxRankUpperBound() {
        // PHI is the golden ratio, used in the Fibonacci number formula
        final double PHI = (1 + Math.sqrt(5)) / 2;
        // Using log to calculate the upper bound of max rank
        return (int) Math.floor(log(PHI, size()));
    }


    /**
     * recursive method that performs 'successive' linking
     * until no two nodes of same rank are in query.
     * worst case: O(log(n))
     */
    private HeapNode link(HeapNode[] query, HeapNode node) {
        HeapNode newRoot;
        // In case there is already a node with the same rank in query.
        if (query[node.rank()] != null) {
            // increase links counter
            links++;
            // This is the other node with the same rank.
            HeapNode other = query[node.rank()];
            // Remove all nodes from current query's rank position (to move into the next rank's place).
            query[node.rank()] = null;

            // TMP
            //
            //


            // Decide which node to be the parent of the other, perform the insertion.
            if (node.getKey() < other.getKey()) {
                // MIGHT BE PROBLEMATIC
                rootList.ripNode(other);
                // This is OK.
                node.insertChild(other);
                newRoot = node;
            } else {
                // PROBLEMO?
                rootList.ripNode(node);
                // OK.
                other.insertChild(node);
                newRoot = other;
            }
            return link(query, newRoot);
        }
        // In case there is no node with rank in query (simple case).
        else {
            query[node.rank()] = node;
            return node;
        }
    }

    /**
     * private void consolidate()
     * Runs the linking process in the heap after deleteMin()
     * Returns null
     * time complexity: O(n)
     */
    private void consolidate() {
        if (size() < 2) {
            return;
        }
        HeapNode[] query = new HeapNode[maxRankUpperBound() + 1];
        for (HeapNode treeRoot : rootList) {
            link(query, treeRoot);
        }
    }

    /**
     * public void increaseCuts()
     * Increases the cuts counter by one
     * Returns null
     * time complexity: O(1)
     */
    public void increaseCuts() {
        // Update counter of total cuts
        cuts++;
    }

    /**
     * public void increaseMarked()
     * Increases the marks counter by one
     * Returns null
     * time complexity: O(1)
     */
    public void increaseMarked() {
        // Update counter of total marks
        marked++;
    }

    /**
     * public void decreaseMarked()
     * Decreases the marks counter by one
     * Returns null
     * time complexity: O(1)
     */
    public void decreaseMarked() {
        // Update counter of total marks
        marked--;
    }

    /**
     * public HeapNode findMin()
     * Returns the node of the heap whose key is minimal, or null if the heap is empty.
     * time complexity: O(1)
     */
    public FibonacciHeap.HeapNode findMin() {
        // Return the node object that holds the minimal key using the saved pointer in the linked list representation
        return rootList.getMin();
    }

    /**
     * public void meld (FibonacciHeap heap2)
     * Melds heap2 with the current heap.
     * Returns null
     * time complexity: O(1)
     */
    public void meld(FibonacciHeap heap2) {
        // Connect the root list of heap2 to the current heap's root list
        rootList.annex(heap2.rootList);
        // update the number of marked nodes in the current heap after the meld
        marked += heap2.marked;
    }

    /**
     * public int size()
     * Returns the number of elements in the heap.
     * time complexity: O(1)
     */
    public int size() {
        // Uses the linked list saved data
        return rootList.size;
    }

    /**
     * public int[] countersRep()
     * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
     * (Note: The size of the array depends on the maximum order of a tree.)
     * time complexity: O(n)
     */
    public int[] countersRep() {
        if (size() == 0) {
            return new int[0];
        }

        // Find the maximum rank in the heap and initialize a new array of that size
        int[] arr = new int[getMaxRank() + 1];
        // For each tree root in the forest, increase the counter by the rank index in the counter array
        for (FibonacciHeap.HeapNode n : this.rootList) {
            arr[n.rank()]++;
        }
        // Returns the counter array after the counting is done
        return arr;
    }

    /**
     * private int maxRankUpperBound()
     * Finds the maximum existing tree rank in the heap forest
     * Returns the maximum rank
     * time complexity: O(n)
     */
    private int getMaxRank() {
        int max = 0;
        // For each tree root in the forest
        for (FibonacciHeap.HeapNode n : this.rootList) {
            // If the current node rank is higher than current maximum, update the maximum
            if (n.rank() > max) {
                max = n.rank();
            }
        }
        // Return the maximum rank found
        return max;
    }

    /**
     * public void delete(HeapNode x)
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     * time complexity: O(n)
     */
    public void delete(FibonacciHeap.HeapNode x) {
        // check if the node is null, if it is then return
        if (null == x) {
            return;
        }
        // get the key of the node to be deleted
        int k = x.getKey();
        // set a delta to the maximum value of an integer
        int maxDelta = Integer.MAX_VALUE;
        // decrease the key of the node to be deleted which will move it to the root list
        rootList.listDecreaseKey(k, maxDelta, this);
        // delete the minimum node from the heap
        deleteMin();
    }


    /**
     * public void decreaseKey(HeapNode x, int delta)
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     * time complexity: O(n)
     */
    public void decreaseKey(FibonacciHeap.HeapNode x, int delta) {
        // get the key of the node to be decreased
        int k = x.getKey();
        // Calls the linked list method for finding the specified node in the heap and decreasing the key by the given delta
        rootList.listDecreaseKey(k, delta, this);
    }

    /**
     * public int nonMarked()
     * This function returns the current number of non-marked items in the heap
     * time complexity: O(1)
     */
    public int nonMarked() {
        // The non marked will be every node in the heap that is not counted in marked
        return size() - marked;
    }

    /**
     * public int potential()
     * This function returns the current potential of the heap, which is:
     * Potential = #trees + 2*#marked
     * In words: The potential equals to the number of trees in the heap
     * plus twice the number of marked nodes in the heap.
     * time complexity: O(1)
     */
    public int potential() {
        // Calculate the potential function using the saved heap data
        return rootList.length + 2 * marked;
    }

    /**
     * public static int totalLinks()
     * This static function returns the total number of link operations made during the
     * run-time of the program. A link operation is the operation which gets as input two
     * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
     * tree which has larger value in its root under the other tree.
     * time complexity: O(1)
     */
    public static int totalLinks() {
        // Returns the value saved in the links counter
        return links;
    }

    /**
     * public static int totalCuts()
     * This static function returns the total number of cut operations made during the
     * run-time of the program. A cut operation is the operation which disconnects a subtree
     * from its parent (during decreaseKey/delete methods).
     * time complexity: O(1)
     */
    public static int totalCuts() {
        // Returns the value saved in the cuts counter
        return cuts;
    }


    /**
     * private HeapNode find(int k)
     * Find a node with key 'k' in the heap.
     * returns the node with the corresponding key
     * time complexity: O(n)
     */
    private FibonacciHeap.HeapNode find(int k) {
        // Calls the linked list recursive find method
        return rootList.findRecursive(k);
    }


    /**
     * public static int[] kMin(FibonacciHeap H, int k)
     * This static function returns the smallest k elements in a Fibonacci heap that contains a single tree.
     * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
     * ###CRITICAL### : you are NOT allowed to change H.
     * time complexity: O(k * deg(H))
     */
    public static int[] kMin(FibonacciHeap H, int k) {
        // Initialize a result array with size k.
        int[] result = new int[k];
        if (k == 0) {
            return result;
        }

        // Insert the minimal value from H to the result array.
        HeapNode minNode = H.findMin();
        result[0] = minNode.getKey();

        // Initialize variables for the procedure.
        int nextMinKey;
        FibonacciHeap query = new FibonacciHeap();

        // Repeat k - 1 times:
        for (int i = 1; i < k; i++) {
            // Insert the recently added node's children from H to the query.
            for (FibonacciHeap.HeapNode child : minNode.children) {
                query.insert(child.getKey());
            }

            // Find the next minimal key from query
            nextMinKey = query.findMin().getKey();

            // Add the minimal key from query to the results array
            result[i] = nextMinKey;

            // Find the recently added node in H.
            minNode = H.find(nextMinKey);
        }

        // Return the resulting array after k inserts.
        return result;
    }


        /**
         * public class HeapNode
         * If you wish to implement classes other than FibonacciHeap
         * (for example HeapNode), do it in this file, not in another file.
         */
        public static class HeapNode {
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
             *
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
                int childrenLengthBefore = children.length;
                // Recursive call on the children linked list in order to find the node with the key to decrease
                HeapNode returnedNode = children.listDecreaseKey(key, d, heap);
    
                // The returned value from the recursion is null if the searched node is not found in the subtree
                if (returnedNode != null) {
                    // Checks if the parent node is a root node of the heap
                    // only cuts and marks parent if parent node is not a root node in the heap forest
                    if (hasParent()) {
                        // If a child node was cut for this node, the length of children linked list was decreased
                        if (childrenLengthBefore > children.length) {
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
             *
             * @param key the key of this node
             */
            public HeapNode(int key) {
                this.key = key;
                children = listFactory.createList(this);
            }
    
            /**
             * String representation of HeapNode
             * @return String with the key of the node
             * Time Complexity: O(1)
             */
            public String toString() {
                return Integer.toString(key);
            }
    
    
            ///////////////////
            // 'HAS' METHODS //
            ///////////////////
    
            /**
             * Check if this node has a previous node.
             * @return true if this node has a previous node, false otherwise
             * Time Complexity: O(1)
             */
            public boolean hasPrev() {
                return prev != null;
            }
    
            /**
             * Check if this node has a next node
             * @return true if this node has a next node, false otherwise
             * Time Complexity: O(1)
             */
            public boolean hasNext() {
                return next != null;
            }
    
            /**
             * Check if this node has a parent.
             * iff not, then the node must be in rootList.
             * @return true iff this node has a parent
             * Time Complexity: O(1)
             */
            public boolean hasParent() {
                return getParent() != null;
            }
    
    
            ///////////////////
            // 'GET' METHODS //
            ///////////////////
    
            /**
             * @return the key of this node
             * Time Complexity: O(1)
             */
            public int getKey() {
                return key;
            }
    
            /**
             * @return the parent of the siblings LinkedList
             * Time Complexity: O(1)
             */
            public HeapNode getParent() {
                if (siblings == null) {
                    return null;
                }
                return siblings.parent;
            }
    
            /**
             * @return the number of children of this node
             * Time Complexity: O(1)
             */
            public int rank() {
                return children.length;
            }
    
            /**
             * Get the number of nodes in the subtree rooted at this node (including this node).
             * @return the number of nodes in the subtree rooted at this node
             * Time Complexity: O(1)
             */
            public int getSize() {
                return 1 + children.size;
            }
    
            /**
             * Check if this node is marked
             * @return true iff this node is marked
             * Time Complexity: O(1)
             */
            public boolean getMark() {
                return mark;
            }
    
    
            /**
             * recursively searches for the node with the given key in the tree rooted at this node,
             * including this node.
             *
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
             * Time Complexity: O(1)
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
             * Time Complexity: O(1)
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
             * Time Complexity: O(1)
             */
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
             * Time Complexity: O(1)
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
             * Insert a node to the header of the children list of this node
             * @param node the new child of this node
             * Time Complexity: O(1)
             */
            public void insertChild(HeapNode node) {
                children.insertFirst(node);
            }
    
    
            /**
             * Plant a linked list previous to this node.
             * Updates length, size, and relevant node pointers.
             * @param list the linked list to be planted before this node
             * Time Complexity: O(1)
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
             * Time Complexity: O(1)
             */
            public void eject() {
                //NEED TO SAVE POINTERS AHEAD
                HeapNode originalNext = next;
                HeapNode originalPrev = prev;
    
                // Connect prev node to the next node
                if (originalPrev != null) {
                    originalPrev.setNext(originalNext);
                    setPrev(null);
                }
    
                // Connect next node to the prev node
                if (originalNext != null) {
                    originalNext.setPrev(originalPrev);
                    setNext(null);
                }
    
                // Nullify siblings pointer
                siblings = null;
            }
    
    
            /**
             * cut this node from its siblings list.
             * Time Complexity: O(1)
             */
            public void cut() {
                // Refer to LinkedList's cut function.
                siblings.cutNode(this);
            }
    
    
        }
    
        /**
         * Node factory which takes the key as an argument and also
         * sets a reference to the heap of the node.
         */
        static class NodeFactory {
            FibonacciHeap heap;
    
            public NodeFactory(FibonacciHeap heap) {
                this.heap = heap;
            }
    
            public HeapNode createNode(int key) {
                FibonacciHeap.HeapNode node = new HeapNode(key);
                node.heap = heap;
                return node;
            }
        }
}

/**
 * LinkedList class that stores HeapNodes, also has a pointer to the minNode
 * and a length and size attributes that update according to the stored node's
 * size. a size is the total number of nodes stored in the current list, and all their offsprings.
 */
class LinkedList implements Iterable<FibonacciHeap.HeapNode> {
    FibonacciHeap.HeapNode root;
    FibonacciHeap.HeapNode tail;
    int length;
    int size;
    FibonacciHeap.HeapNode minNode;
    FibonacciHeap.HeapNode parent;
    LinkedList observer;


    public LinkedList() {
        observer = this;
    }

    /**
     * Helper method for heap.deleteMin()
     * Deletes the given node from the list, and plants the children in his place by order
     *
     * @return HeapNode
     * time complexity: O(1)
     */
    public void deleteMin() {
        // Saves pointer to the node that is about to be deleted to use the location pointers later
        FibonacciHeap.HeapNode nodeToDelete = minNode;
        // Saves children of the node that is about to be deleted to insert them later
        LinkedList children = nodeToDelete.children;
        // Save nodeToDelete.next pointer to use planeBefore
        FibonacciHeap.HeapNode nodeAfter = nodeToDelete.next;
        // Deletes the node from the list (Linked list cut is equivalent to Node delete with children)
        ripNode(nodeToDelete);
        // Plants the deleted node children nodes according to his old location in the list
        plantBefore(children, nodeAfter);
    }


    /**
     * Helper method for heap.DecreaseKey() - works with node.nodeDecreaseKey()
     *
     * @return HeapNode if found, else null
     * time complexity: O(n)
     */
    public FibonacciHeap.HeapNode listDecreaseKey(int key, int d, FibonacciHeap heap) {
        // Loop runs through every node in the list
        for (FibonacciHeap.HeapNode node : this) {
            // Start of recursive call for each tree in the forest, to find the node and decrease the key
            FibonacciHeap.HeapNode retrievedNode = node.nodeDecreaseKey(key, d, heap);
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
     *
     * @return [key_root, key_2, ..., key_tail]
     * Time Complexity: O(n)
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
     * Time Complexity: O(1)
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return true iff parent is not null
     * Time Complexity: O(1)
     */
    public boolean hasParent() {
        return parent != null;
    }


    ///////////////////
    // 'GET' METHODS //
    ///////////////////

    /**
     * @return the minimal node from a designated pointer 'minNode'
     * Time Complexity: O(1)
     */
    public FibonacciHeap.HeapNode getMin() {
        return minNode;
    }


    /**
     * recursively search for the node with the provided key within the children list of each node,
     * only if the requested key is not directly inside the list.
     *
     * @param key the requested key to search
     * @return the node that corresponds to the key
     * Time Complexity: O(n)
     */
    public FibonacciHeap.HeapNode findRecursive(int key) {
        // Added this to prioritize upper-most nodes. CAN BE REMOVED
        for (FibonacciHeap.HeapNode node : this) {
            if (node.getKey() == key) {
                return node;
            }
        }

        // Original procedure
        for (FibonacciHeap.HeapNode node : this) {
            FibonacciHeap.HeapNode retrievedNode = node.findRecursive(key);
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
     *
     * @param size the new size of the list
     *             Time Complexity: O(1)
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
     *
     * @param delta the increment in this list size
     *              Time Complexity: O(logn)
     */
    public void increaseSize(int delta) {
        // Trivial case.
        if (delta == 0) {
            return;
        }

        // Subscribed case.
        if (observer != this) {
            observer.increaseSize(delta);
            return;
        }

        // Calculate new size and call size setter
        int newSize = size + delta;
        setSize(newSize);

        // Recursively update all parent lists' sizes.
        if (hasParent() && parent.children == this && parent.siblings != null) {
            parent.siblings.increaseSize(delta);
        }
    }

    /**
     * Call increaseSize with -delta,
     * recursively call the parent's siblings' increaseSize with delta
     *
     * @param delta a positive decrement of this list size
     *              Time Complexity: O(logn)
     */
    public void decreaseSize(int delta) {
        // Call increaseSize with a negative argument
        increaseSize(-delta);
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
     *
     * @param node the node to be inserted at the header of this list
     *             Time Complexity: O(logn)
     */
    public void insertFirst(FibonacciHeap.HeapNode node) {

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
     *
     * @param list2 the list to be annexed to this list
     *              Time Complexity: O(logn)
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
        } else {
            // Connect the root of list2 to the
            this.tail.setNext(list2.root);
            this.minNode = (this.minNode.key < list2.minNode.key) ? this.minNode : list2.minNode;
        }
        this.tail = list2.tail;
        this.length += list2.length;
        list2.observer = this;
        this.increaseSize(list2.size);
    }

    /**
     * @param list2     the list to be planted to this list
     * @param nodeAfter the node to be after the planted list. null iff annex
     *                  Time Complexity: O(logn)
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
        if (nodeAfter == this.root && !list2.isEmpty()) {
            this.root = list2.root;
        }
        list2.observer = this;
    }


    ////////////////////
    // DELETE METHODS //
    ////////////////////

    /**
     * Removes the node with its children from the linked list.
     * updates length and size of list, also sends an update command upwards in the tree.
     * updates the minNode reference of this linked list, if necessary.
     * Time Complexity: O(logn)
     */
    public void cutNode(FibonacciHeap.HeapNode node) {
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
    }

    /**
     * Removes the node with its children from the linked list.
     * updates length and size of list, also sends an update command upwards in the tree.
     * Time Complexity: O(logn)
     */
    public void ripNode(FibonacciHeap.HeapNode node) {
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


    ///////////////////
    // OTHER METHODS //
    ///////////////////

    /**
     * Goes through all the nodes in the list to find the minimal keyed node,
     * and stores a reference to it in the list.
     * Time Complexity: O(m), where m is the length of the list. (n is the size of the heap).
     */
    public void updateMin() {
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
}


/**
 * A factory to create a linked list with a reference to its parent.
 */
class LinkedListFactory {
    public LinkedList createList(FibonacciHeap.HeapNode parent) {
        LinkedList result = new LinkedList();
        result.parent = parent;
        return result;
    }
}

/**
 * A node iterator that goes through the linked list.
 */
class LinkedListIterator implements Iterator<FibonacciHeap.HeapNode> {
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
