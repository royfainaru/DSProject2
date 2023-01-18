import java.util.Iterator;

/**
 * FibonacciHeap
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
    private final NodeFactory nodeFactory = new NodeFactory(this); // For initialization of new nodes
    LinkedList rootList = new LinkedList(); // The representation for the top of the heap

    private static int links = 0; // Counter for total links
    private static int cuts = 0; // Counter for total cuts
    private int marked = 0; // Counter for number of current marked nodes in heap


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
    public HeapNode insert(int key) {
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
     * time complexity: O(logn)
     */
    public void deleteMin() {
        // If the heap is empty, no minNode to delete
        if (isEmpty()) {
            return;
        }

        // Call the rootList method as a linked list to delete the minNode
        rootList.deleteMin();

        // Call the linking process of the heap
        reOrganize();
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
     * private void reOrganize()
     * Runs the linking process in the heap after deleteMin()
     * Returns null
     * time complexity: O(n)
     */
    private void reOrganize() {
        // Initialize an array to keep track of the number of nodes at each rank
        int[] rankCounter = new int[maxRankUpperBound() + 1];
        // Copy the current counters representation to rankCounter
        int[] currentCountersRep = countersRep();
        System.arraycopy(currentCountersRep, 0, rankCounter, 0, currentCountersRep.length);

        // Iterate through all ranks from 0 to maxRankUpperBound
        for (int i = 0; i < rankCounter.length - 1; i++) {
            // While there are more than 1 node with rank i, continue the linking
            while (rankCounter[i] > 1) {
                // Find the first 2 nodes with rank i
                HeapNode node1 = null;
                HeapNode node2 = null;

                for (HeapNode n : this.rootList) {
                    if (n.rank() == i) {
                        // Makes sure to only take 2
                        if (null != node1) {
                            node2 = n;
                            break;
                        } else {
                            node1 = n;
                        }
                    }
                }

                // Compare the keys of the two nodes, and set tmpParent and tmpChild accordingly to the smaller key
                HeapNode tmpParent;
                HeapNode tmpChild;
                if (node1.key < node2.key) {
                    tmpParent = node1;
                    tmpChild = node2;
                } else {
                    tmpParent = node2;
                    tmpChild = node1;
                }

                // Cut the child from the root list and insert the child into the parent children list
                tmpParent.insertChild(rootList.cutNode(tmpChild));

                // Update the rank counter
                updateCounter(i, rankCounter);
            }
        }
    }

    /**
     * public void updateCounter()
     * Updates countersRep array (given as argument) after link of two trees in rank i
     * Increases the links counter by one
     * Returns null
     * time complexity: O(1)
     */
    private void updateCounter(int i, int[] rankCounter) {
        // A link between two trees of rank i, creates a new tree of rank i+1 in the forest, updates the rank counter accordingly
        rankCounter[i] -= 2;
        rankCounter[i+1]++;
        // Update counter of total links
        links++;
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
    public HeapNode findMin() {
        // Return the node object that holds the minimal key using the saved pointer in the linked list representation
        return rootList.getMin();
    }

    /**
     * public void meld (FibonacciHeap heap2)
     * Melds heap2 with the current heap.
     * Returns null
     * time complexity: O(1)
     */
    public void meld (FibonacciHeap heap2) {
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
     * time complexity: O(logn)
     */
    public int[] countersRep() {
        // Find the maximum rank in the heap and initialize a new array of that size
        int[] arr = new int[getMaxRank() + 1];
        // For each tree root in the forest, increase the counter by the rank index in the counter array
        for (HeapNode n : this.rootList) {
            arr[n.rank()]++;
        }
        // Returns the counter array after the counting is done
        return arr;
    }

    /**
     * private int maxRankUpperBound()
     * Finds the maximum existing tree rank in the heap forest
     * Returns the maximum rank
     * time complexity: O(logn)
     */
    private int getMaxRank() {
        int max = 0;
        // For each tree root in the forest
        for (HeapNode n : this.rootList) {
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
    public void delete(HeapNode x) {
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
    public void decreaseKey(HeapNode x, int delta) {
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
    private HeapNode find(int k) {
        // Calls the linked list recursive find method
        return rootList.findRecursive(k);
    }


    /**
     * public static int[] kMin(FibonacciHeap H, int k)
     * This static function returns the smallest k elements in a Fibonacci heap that contains a single tree.
     * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
     * ###CRITICAL### : you are NOT allowed to change H.
     * time complexity: O(klogn) ??????????????????????????????????????????????????????????????????????????????
     */
    public static int[] kMin(FibonacciHeap H, int k)
    {
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
            for (HeapNode child : minNode.children) {
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
}


