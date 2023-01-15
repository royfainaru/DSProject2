import java.util.Iterator;

/**
 * FibonacciHeap
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
    private final NodeFactory nodeFactory = new NodeFactory(this);
    LinkedList rootList = new LinkedList(); // The representation for the top of the heap

    private static int links = 0; // Counter for total links
    private static int cuts = 0; // Counter for total cuts


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
     * Returns null
     */
    public void deleteMin() {
        if (isEmpty()) {
            return;
        }
        rootList.deleteMin();
        reOrganize();
    }


    /**
     * private void reOrganize()
     * Runs the linking process in the heap after deleteMin()
     * Returns null
     */
    private void reOrganize() {
        int[] rankCounter = countersRep();
        for (int i = 0; i < rankCounter.length - 2; i++) {
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

                HeapNode tmpparent;
                HeapNode tmpchild;
                if (node1.key < node2.key) {
                    tmpparent = node1;
                    tmpchild = node2;
                } else {
                    tmpparent = node2;
                    tmpchild = node1;
                }

                tmpparent.insertChild(rootList.cutNode(tmpchild));
                updateCounter(i, rankCounter);
            }
        }
    }

    /**
     * public void increaseCuts()
     * Updates countersRep array (given as argument) after link of two trees in rank i
     * Increases the links counter by one
     * Returns null
     */
    private void updateCounter(int i, int[] rankCounter) {
        rankCounter[i] -= 2;
        rankCounter[i+1]++;
        // Update counter of total links
        links++;
    }

    /**
     * public void increaseCuts()
     * Increases the cuts counter by one
     * Returns null
     */
    public void increaseCuts() {
        // Update counter of total cuts
        cuts++;
    }

    /**
     * public HeapNode findMin()
     * Returns the node of the heap whose key is minimal, or null if the heap is empty.
     *
     */
    public HeapNode findMin() {
        return rootList.getMin();
    }

    /**
     * public void meld (FibonacciHeap heap2)
     * Melds heap2 with the current heap.
     * Returns null
     */
    public void meld (FibonacciHeap heap2) {
        rootList.annex(heap2.rootList);
    }

    /**
     * public int size()
     * Returns the number of elements in the heap.
     *
     */
    public int size() {
        return rootList.size;
    }

    /**
     * public int[] countersRep()
     * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
     * (Note: The size of of the array depends on the maximum order of a tree.)
     *
     */
    public int[] countersRep() {
        int[] arr = new int[getMaxRank()];
        for (HeapNode n : this.rootList) {
            arr[n.rank()]++;
        }
        return arr;
    }

    /**
     ****************************************** DO WE NEED IT?
     * Returns the maximum rank in the heap.
     *
     */
    private int getMaxRank() {
        int max = 0;
        for (HeapNode n : this.rootList) {
            if (n.rank() > max) {
                max = n.rank();
            }
        }
        return max;
    }

    /**
     * public void delete(HeapNode x)
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     *
     */
    public void delete(HeapNode x) {
        if (null == x) {
            return;
        }

        int k = x.getKey();
        int maxDelta = Integer.MAX_VALUE;
        rootList.listDecreaseKey(k, maxDelta, this);
        deleteMin();
    }


    /**
     * public void decreaseKey(HeapNode x, int delta)
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta) {
        int k = x.getKey();
        rootList.listDecreaseKey(k, delta, this);
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
        return links;
    }

    /**
     * public static int totalCuts()
     * This static function returns the total number of cut operations made during the
     * run-time of the program. A cut operation is the operation which disconnects a subtree
     * from its parent (during decreaseKey/delete methods).
     */
    public static int totalCuts()
    {
        return cuts;
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
}


