package avl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
public class AVLTreeTest {
    static final int FIRST = 0;
    static final int RANDOM = 1;
    static final int LAST = Integer.MAX_VALUE;

    static final Logger logger = Logger.getLogger(AVLTreeTest.class.getName());

    public class Pair<T, S> {
        public T a;
        public S b;

        public Pair(T a, S b) {
            this.a = a;
            this.b = b;
        }
    }

    public void assertValidAVLTrees(AVLTree... trees) {
        for (AVLTree t : trees)
            assertValidAVLTree(t);
    }

    public int getHeight(AVLTree tree) {
        return tree.getRoot() == null ? -1 : tree.getRoot().getHeight();
    }

    public AVLTree.IAVLNode createNode(int key, String value) {
        AVLTree t = new AVLTree();
        t.insert(key, value);
        return t.getRoot();
    }

    protected int assertValidAVLTreeTraverse(Queue<AVLTree.IAVLNode> bfs, int height,
            Map<Integer, Pair<String, Boolean>> data) {
        int count = 0;

        AVLTree.IAVLNode node = null;
        // Traverse the entire tree using BFS, verify the validity of each node
        // encountered
        while (!bfs.isEmpty()) {
            node = bfs.remove();
            assertNotSame(null, node);

            if (!node.isRealNode()) {
                // Verify properties of virtual node
                assertSame(-1, node.getKey());
                assertNull(node.getValue());
                assertSame(0, node.getSize());
                assertSame(-1, node.getHeight());
                continue;
            }

            count++;
            bfs.add(node.getLeft());
            bfs.add(node.getRight());

            /* Verify current node */

            // Heights
            int cHeight = node.getHeight();
            assertTrue(cHeight >= 0);
            assertTrue(cHeight <= height);

            // Connectivity of left/right child
            if (node.getLeft().isRealNode()) {
                assertSame(node, node.getLeft().getParent());
            }
            if (node.getRight().isRealNode()) {
                assertSame(node, node.getRight().getParent());
            }

            // Heights diff
            int lDiff = cHeight - node.getLeft().getHeight();
            int rDiff = cHeight - node.getRight().getHeight();
            assertTrue((lDiff == 1 && rDiff == 1)
                    || (lDiff == 2 && rDiff == 1)
                    || (lDiff == 1 && rDiff == 2),
                    String.format("Found a %d,%d node", lDiff, rDiff));

            // Key and value
            int key = node.getKey();
            String value = node.getValue();
            assertTrue(data.containsKey(key));
            assertEquals(data.get(key).a, value);
            assertFalse(data.get(key).b); // key is unique
            data.get(key).b = true;
        }

        return count;
    }

    public void assertValidAVLTree(AVLTree tree) {
        int size = tree.size();
        int height = getHeight(tree);
        String min = tree.min();
        String max = tree.max();
        boolean empty = tree.empty();

        int[] keys = tree.keysToArray();
        String[] values = tree.infoToArray();

        assertTrue(keys.length == values.length);
        assertFalse(size >= 2 && keys[size - 1] == 0);

        if (!empty) {
            assertEquals(min, values[0]);
            assertEquals(max, values[values.length - 1]);
        }

        /* Assert sorted */
        for (int i = 1; i < keys.length; ++i) {
            assertTrue(keys[i - 1] < keys[i]);
        }

        /* <Key : Pair(Value, isChecked)> dictionary */
        Map<Integer, Pair<String, Boolean>> data = IntStream.range(0, keys.length).boxed()
                .collect(Collectors.toMap(i -> keys[i], i -> new Pair<String, Boolean>(values[i], false)));
        AVLTree.IAVLNode node = tree.getRoot();

        assertSame(node == null, empty);
        if (node == null) {
            // Empty tree
            assertSame(-1, height);
            assertSame(0, size);
            assertSame(0, keys.length);
            assertSame(0, values.length);
            assertNull(max);
            assertNull(min);
            return;
        }

        assertSame(height, node.getHeight());
        assertNotNull(min);
        assertNotNull(max);

        Queue<AVLTree.IAVLNode> bfs = new LinkedList<>();
        bfs.add(node);

        int count = assertValidAVLTreeTraverse(bfs, height, data);

        // Assert size() returns the actual amount of nodes in the tree
        assertEquals(size, count);

        // Assert every key actually exists in the tree
        data.forEach((key, pair) -> assertTrue(pair.b,
                String.format("Node with the following key is missing: %d", key)));
    }

    public AVLTree testInsertion(int[] keys) {
        AVLTree tree = createTree(keys);
        for (int key : keys) {
            assertEquals(String.valueOf(key), tree.search(key));
        }
        assertValidAVLTree(tree);
        return tree;
    }

    public AVLTree testDeletion(AVLTree tree, int[] keys) {
        int size = tree.size();
        for (int key : keys) {
            assertNotNull(tree.search(key));
            tree.delete(key);
            assertSame(size - 1, tree.size());
            assertNull(tree.search(key));
            assertValidAVLTree(tree);

            size--;
        }

        return tree;
    }

    public AVLTree testJoin(AVLTree tree1, int key, AVLTree tree2) {
        int expectedValue = Math.abs(getHeight(tree1) - getHeight(tree2)) + 1;
        int expectedSize = tree1.size() + tree2.size() + 1;
        int returnValue = tree1.join(createNode(key, String.valueOf(key)), tree2);
        int actualSize = tree1.size();

        assertSame(expectedValue, returnValue);
        assertSame(expectedSize, actualSize);

        assertValidAVLTree(tree1);

        return tree1;
    }

    /* Sanity test cases */

    @Test
    @Order(FIRST)
    public void testEmptyTreeSanity() {
        AVLTree tree = new AVLTree();
        assertTrue(tree.empty());
        assertValidAVLTree(tree);

        assertSame(-1, tree.delete(-1));
        assertSame(-1, tree.delete(0));
        assertSame(-1, tree.delete(1));
        assertNull(tree.search(-1));
        assertNull(tree.search(0));
        assertNull(tree.search(1));
        assertTrue(tree.empty());
        assertValidAVLTree(tree);
    }

    @Test
    @Order(FIRST)
    public void testInsertSanity() {
        int[][] keysSets = {
                { 1, 2, 3, 4, 5, 6, 7 },
                { 7, 6, 5, 4, 3, 2, 1 },
                { 3, 7, 2, 4, 5, 1, 6 }
        };
        for (int[] keys : keysSets) {
            testInsertion(keys);
        }
    }

    @Test
    @Order(FIRST)
    public void testInsertDeleteSanity() {
        AVLTree tree = new AVLTree();
        // Insert empty trees
        tree.insert(0, "only value");
        assertSame(1, tree.size());
        assertValidAVLTree(tree);

        // Delete an item and become an empty tree
        assertNotSame(-1, tree.delete(0));
        assertTrue(tree.empty());
        assertValidAVLTree(tree);
    }

    @Test
    @Order(FIRST)
    public void testJoinSanity() {
        AVLTree tree = testInsertion(new int[] { 0 });
        AVLTree tree2 = testInsertion(new int[] { 100 });
        testJoin(tree, 5, tree2);
        assertValidAVLTrees(tree);
        assertSame(3, tree.size());
        assertEquals("0", tree.search(0));
        assertEquals("5", tree.search(5));
        assertEquals("100", tree.search(100));
    }

    /* Functional test cases */
    @Test
    @Order(RANDOM)
    public void testInsertDeleteSmallTree() {
        int[] inOrd = { 0, 1, 2, 3, 4, 5, 6, 7 };
        AVLTree tree = new AVLTree();
        insertDeleteValues(tree, inOrd);
        assertValidAVLTree(tree);
        int[] notOrd = { 7, 6, 5, 4, 3, 2, 1, 0 };
        tree = new AVLTree();
        insertDeleteValues(tree, notOrd);
        assertValidAVLTree(tree);

        for (int i = 0; i < 1000; i++) {
            tree = new AVLTree();
            int[] rands = randomRange(8);
            insertDeleteValues(tree, rands);
            assertValidAVLTree(tree);
        }
    }

    public void insertDeleteValues(AVLTree tree, int[] vals) {
        for (int val : vals) {
            for (int j = 0; j < 3; j++) {
                tree.insert(val, String.valueOf(val));
                assertValidAVLTree(tree);
                tree.delete(val);
                assertValidAVLTree(tree);
            }
            tree.insert(val, String.valueOf(val));
            assertValidAVLTree(tree);
        }
    }

    public int[] randomRange(int k) {
        List<Integer> integers = IntStream.range(0, k).boxed().collect(Collectors.toList());
        Collections.shuffle(integers);
        return integers.stream().mapToInt(Integer::intValue).toArray();
    }

    /* insert test cases */

    @Test
    @Order(RANDOM)
    public void testInsertCase1() {
        // Causes case 1 to occur when 1 is inserted
        testInsertion(new int[] { 2, 1 });
    }

    @Test
    @Order(RANDOM)
    public void testInsertCase2() {
        // Causes case 2 with RIGHT rotation to occur when 0 is inserted
        // Case 1 occurs multiple times
        testInsertion(new int[] { 3, 2, 4, 1, 0 });

        // Causes case 2 with LEFT rotation to occur when 6 is inserted
        // Case 1 occurs multiple times
        testInsertion(new int[] { 3, 2, 4, 5, 6 });
    }

    @Test
    @Order(RANDOM)
    public void testInsertCase3() {
        // Causes case 3 with LEFT-RIGHT double rotation to occur when 7 is inserted
        // Case 1 occurs multiple times
        testInsertion(new int[] { 13, 10, 15, 16, 5, 11, 4, 6, 7 });

        // Causes case 3 with RIGHT-LEFT double rotation to occur when 15 is inserted
        // Case 1 occurs multiple times
        testInsertion(new int[] { 5, 2, 7, 1, 4, 6, 9, 3, 16, 15 });
    }

    /* delete test cases */
    @Test
    @Order(RANDOM)
    public void testDeleteNoRebalance() {
        AVLTree tree = testInsertion(new int[] { 11, 4, 13, 2, 6, 12, 16, 5, 7 });
        testDeletion(tree, new int[] { 4 });
    }

    @Test
    @Order(RANDOM)
    public void testDeleteMinMaxLeaves() {
        AVLTree tree = testInsertion(new int[] { 1, 0, 4, 3, 5, 2 });
        testDeletion(tree, new int[] { 0, 5 });
    }

    @Test
    @Order(RANDOM)
    public void testDeleteRoot() {
        AVLTree tree = testInsertion(new int[] { 0, 1, 2 });
        testDeletion(tree, new int[] { 1, 2, 0 });
    }

    @Test
    @Order(RANDOM)
    public void testDeleteMultipleValues() {
        AVLTree tree = testInsertion(new int[] { 1, 0, 4, 3, 5, 2 });
        testDeletion(tree, new int[] { 4, 2, 3, 0, 5, 1 });
    }

    @Test
    @Order(RANDOM)
    public void testDeleteMultipleCase1() {
        /* Multiple demotions */
        AVLTree tree = testInsertion(new int[] { 11, 4, 14, 2, 6, 12, 16, 5, 7, 13 });
        testDeletion(tree, new int[] { 11 });
        tree = testInsertion(new int[] { 7, 4, 9, 2, 6, 8, 10, 5 });
        testDeletion(tree, new int[] { 4 });
        tree = testInsertion(new int[] { 9, 5, 12, 4, 10, 14, 13 });
        testDeletion(tree, new int[] { 13 }); // 3 demotions in a row
    }

    @Test
    @Order(RANDOM)
    public void testDeleteCase2() {
        /* Single rotation (a) */

        /* LEFT rotation */
        AVLTree tree = testInsertion(new int[] { 1, 0, 3, 2, 4 });
        testDeletion(tree, new int[] { 0 });

        /* RIGHT rotation */
        tree = testInsertion(new int[] { 3, 2, 4, 0, 1 });
        testDeletion(tree, new int[] { 4 });
    }

    @Test
    @Order(RANDOM)
    public void testDeleteCase3() {
        /* Single rotation (b) */

        /* LEFT rotation */
        AVLTree tree = testInsertion(new int[] { 1, 0, 3, 4 });
        testDeletion(tree, new int[] { 0 });

        /* RIGHT rotation */
        tree = testInsertion(new int[] { 3, 1, 4, 0 });
        testDeletion(tree, new int[] { 4 });
    }

    @Test
    @Order(RANDOM)
    public void testDeleteCase4() {
        /* Double rotation */

        /* Test RIGHT-LEFT double rotation */
        AVLTree tree = testInsertion(new int[] { 5, 3, 7, 6 });
        testDeletion(tree, new int[] { 3 });

        /* Test LEFT-RIGHT double rotation */
        tree = testInsertion(new int[] { 5, 3, 7, 4 });
        testDeletion(tree, new int[] { 7 });
    }

    @Test
    @Order(RANDOM)
    public void testDeleteMediumSizeTrees() {
        AVLTree tree = testInsertion(new int[] { 50, 30, 70, 20, 35, 60, 80, 15, 25, 40, 55, 10 });
        testDeletion(tree, new int[] { 80 });

        tree = testInsertion(new int[] { 15, 12, 54, 8, 13, 18, 60, 5, 9, 14, 16, 56, 70 });
        testDeletion(tree, new int[] { 8, 12, 14 });
    }

    /* join test cases */
    @Test
    @Order(RANDOM)
    public void testJoinEmptyTree() {
        // Join two empty trees
        AVLTree tree = testJoin(new AVLTree(), 1, new AVLTree());
        // Join an empty tree with a non empty tree
        tree = testJoin(new AVLTree(), 2, tree);
        // Join a non empty tree with an empty tree
        testJoin(tree, 3, new AVLTree());
    }

    @Test
    @Order(RANDOM)
    public void testJoinNewRoot() {
        AVLTree tree = testInsertion(new int[] { 1, 2, 3, 4 });
        AVLTree tree2 = testInsertion(new int[] { 6, 8, 10, 12 });
        testJoin(tree, 5, tree2);

        tree = testInsertion(new int[] { 1, 2, 3, 4 });
        tree2 = testInsertion(new int[] { 6, 8, 10, 12 });
        testJoin(tree2, 5, tree);
    }

    @Test
    @Order(RANDOM)
    public void testJoinCase1() {
        /* 1 promotion */
        AVLTree tree = createTree(new int[] { 6, 5, 8, 4, 7, 9 });
        AVLTree tree2 = createTree(new int[] { 2, 1 });
        testJoin(tree, 3, tree2);

        tree = createTree(new int[] { 6, 5, 8, 4, 7, 9 });
        tree2 = createTree(new int[] { 2, 1 });
        testJoin(tree2, 3, tree);

        /* 3 promotions */
        tree = createTree(new int[] { 6, 5, 8, 7, 9 });
        tree2 = createTree(new int[] { 3 });
        testJoin(tree, 4, tree2);

        tree = createTree(new int[] { 6, 5, 8, 7, 9 });
        tree2 = createTree(new int[] { 3 });
        testJoin(tree2, 4, tree);
    }

    @Test
    @Order(RANDOM)
    public void testJoinCase2() {
        /* Test LEFT rotation */
        AVLTree tree = createTree(new int[] { 4985 });
        AVLTree tree2 = createTree(new int[] { 360, 543, 1043, 1894, 2123, 2623, 3474, 3515, 3637 }); // 5 case-2
                                                                                                      // insertions
        testJoin(tree, 4137, tree2); // 1 case-2 join

        tree = createTree(new int[] { 4985 });
        tree2 = createTree(new int[] { 360, 543, 1043, 1894, 2123, 2623, 3474, 3515, 3637 });
        testJoin(tree2, 4137, tree);

        /* Test RIGHT rotation */
        tree = createTree(new int[] { 357 });
        tree2 = createTree(new int[] { 360, 543, 1043, 1894, 2123, 2623, 3515, 359, 361 }); // 4 case-2 insertions
        testJoin(tree, 358, tree2); // 1 case-2 join

        tree = createTree(new int[] { 357 });
        tree2 = createTree(new int[] { 360, 543, 1043, 1894, 2123, 2623, 3515, 359, 361 });
        testJoin(tree2, 358, tree);
    }

    @Test
    @Order(RANDOM)
    public void testJoinCase3() {
        /* Test LEFT-RIGHT double rotation */
        AVLTree tree = createTree(new int[] { 10000, 8000, 15000, 7000, 9000, 20000, 6000 });
        AVLTree tree2 = createTree(new int[] { 2000 });
        testJoin(tree, 5000, tree2);

        tree = createTree(new int[] { 10000, 8000, 15000, 7000, 9000, 20000, 6000 });
        tree2 = createTree(new int[] { 2000 });
        testJoin(tree2, 5000, tree);

        /* Test RIGHT-LEFT double rotation */
        tree = createTree(new int[] { 10000, 8000, 15000, 7000, 13000, 20000, 25000 });
        tree2 = createTree(new int[] { 100000 });
        testJoin(tree, 50000, tree2);

        tree = createTree(new int[] { 10000, 8000, 15000, 7000, 13000, 20000, 25000 });
        tree2 = createTree(new int[] { 100000 });
        testJoin(tree2, 50000, tree);
    }

    @Test
    @Order(RANDOM)
    public void testJoinSpecialCase() {
        AVLTree tree = createTree(new int[] { 4, 2, 6, 1, 3, 5, 7, 8 });
        AVLTree tree2 = createTree(new int[] { 11, 10, 12 });
        testJoin(tree, 9, tree2);

        tree = createTree(new int[] { 4, 2, 6, 1, 3, 5, 7, 8 });
        tree2 = createTree(new int[] { 11, 10, 12 });
        testJoin(tree2, 9, tree);
    }

    /* split test cases */
    @Test
    @Order(RANDOM)
    public void testSplitEmptyTree() {
        AVLTree tree = new AVLTree();
        // Two empty trees
        tree.insert(1, "unique");
        assertSame(1, tree.size());
        assertEquals("unique", tree.search(1));
        assertValidAVLTree(tree);
        AVLTree[] trees = tree.split(1);
        assertTrue(trees[0].empty());
        assertTrue(trees[1].empty());
        assertValidAVLTrees(trees);
        tree = trees[0];
        // Smaller is an empty tree
        tree.insert(1, "1");
        tree.insert(2, "2");
        assertValidAVLTree(tree);
        trees = tree.split(1);
        assertTrue(trees[0].empty());
        assertEquals("2", trees[1].search(2));
        assertSame(1, trees[1].size());
        assertValidAVLTrees(trees);
        tree = trees[0];
        // Bigger is an empty tree
        tree.insert(3, "3");
        tree.insert(4, "4");
        assertValidAVLTree(tree);
        trees = tree.split(4);
        assertTrue(trees[1].empty());
        assertEquals("3", trees[0].search(3));
        assertSame(1, trees[0].size());
        assertValidAVLTrees(trees);
    }

    @Test
    @Order(RANDOM)
    public void testSplitExtrema() {
        AVLTree tree = testInsertion(new int[] { 1, 2, 3, 4, 5, 6, 7 });

        // Maximum
        AVLTree[] trees = tree.split(7);
        assertValidAVLTrees(trees);
        assertEquals(6, trees[0].size());
        assertEquals(0, trees[1].size());
        for (int i : new int[] { 1, 2, 3, 4, 5, 6 }) {
            assertEquals(String.valueOf(i), trees[0].search(i));
        }

        // Minimum
        tree = trees[0];
        trees = tree.split(1);
        assertValidAVLTrees(trees);
        assertEquals(0, trees[0].size());
        assertEquals(5, trees[1].size());
        for (int i : new int[] { 2, 3, 4, 5, 6 }) {
            assertEquals(String.valueOf(i), trees[1].search(i));
        }
    }

    @Test
    @Order(RANDOM)
    public void testSplitZigZag() {
        AVLTree tree = testInsertion(IntStream.range(1, 1024).toArray());
        AVLTree[] trees = tree.split(341);
        assertValidAVLTrees(trees);
        assertEquals(340, trees[0].size());
        assertEquals(1023 - 341, trees[1].size());
        for (int i : IntStream.range(1, 341).toArray()) {
            assertEquals(String.valueOf(i), trees[0].search(i));
        }
        for (int i : IntStream.range(342, 1024).toArray()) {
            assertEquals(String.valueOf(i), trees[1].search(i));
        }
    }

    @Test
    @Order(RANDOM)
    public void testSplitRootSuccessorPredecessor() {
        AVLTree tree = testInsertion(IntStream.range(1, 1024).toArray());
        // Precessor
        AVLTree[] trees = tree.split(511);
        assertValidAVLTrees(trees);
        assertEquals(510, trees[0].size());
        assertEquals(1023 - 511, trees[1].size());
        for (int i : IntStream.range(1, 510).toArray()) {
            assertEquals(String.valueOf(i), trees[0].search(i));
        }
        for (int i : IntStream.range(512, 1024).toArray()) {
            assertEquals(String.valueOf(i), trees[1].search(i));
        }

        // Successor
        trees = trees[0].split(257);
        assertValidAVLTrees(trees);
        assertEquals(256, trees[0].size());
        assertEquals(510 - 257, trees[1].size());
        for (int i : IntStream.range(1, 256).toArray()) {
            assertEquals(String.valueOf(i), trees[0].search(i));
        }
        for (int i : IntStream.range(258, 511).toArray()) {
            assertEquals(String.valueOf(i), trees[1].search(i));
        }
    }

    @Test
    @Order(RANDOM)
    public void testSplitNonLeafNode() {
        AVLTree tree = testInsertion(IntStream.range(1, 256).toArray());
        AVLTree.IAVLNode node = tree.getRoot().getLeft().getLeft().getRight();
        assertEquals(48, node.getKey());
        assertTrue(node.getLeft().isRealNode());
        assertTrue(node.getRight().isRealNode());

        AVLTree[] trees = tree.split(48);
        assertValidAVLTrees(trees);
        for (int i : IntStream.range(1, 48).toArray()) {
            assertEquals(String.valueOf(i), trees[0].search(i));
        }
        for (int i : IntStream.range(49, 256).toArray()) {
            assertEquals(String.valueOf(i), trees[1].search(i));
        }
    }

    /* Stress test case */

    static final Random rand = new Random();

    void stressTestInsertionDeletion(AVLTree tree, int lowerbound, int upperbound) {
        for (int i = 0; i < 100; i++) {
            int val = lowerbound + rand.nextInt(upperbound - lowerbound + 1);
            boolean insertOrDel = rand.nextBoolean();

            if (!insertOrDel) {
                tree.insert(val, String.valueOf(val));
            } else {
                tree.delete(val);
            }
            assertValidAVLTree(tree);
        }
    }

    AVLTree stressTestJoin(AVLTree tree) {
        boolean larger = rand.nextBoolean();
        AVLTree smallerTree = tree;
        AVLTree biggerTree = new AVLTree();

        int[] keys = smallerTree.keysToArray();
        int lowerbound = keys.length == 0 ? 500 : keys[keys.length - 1] + 1000;
        int upperbound = lowerbound + 1000;
        stressTestInsertionDeletion(biggerTree, lowerbound, upperbound);

        AVLTree.IAVLNode connect = createNode(lowerbound - 500, String.valueOf(lowerbound - 500));

        AVLTree mainTree = larger ? biggerTree : smallerTree;
        AVLTree subTree = larger ? smallerTree : biggerTree;

        mainTree.join(connect, subTree);

        assertValidAVLTree(mainTree);
        return mainTree;
    }

    AVLTree stressTestSplit(AVLTree tree) {
        int[] keys = tree.keysToArray();
        int splitKey = rand.nextInt(keys.length);
        int theKey = keys[splitKey];
        AVLTree[] trees = tree.split(theKey);
        assertValidAVLTree(trees[1]);
        assertValidAVLTree(trees[0]);
        boolean larger = rand.nextBoolean();
        return trees[larger ? 1 : 0];
    }

    @Test
    @Order(LAST)
    public void stressTest() {
        AVLTree tree = new AVLTree();
        final int iterations = 300000;
        int counter = 1;
        do {
            int printFrequency = counter < 100 ? 1 : 10;
            if (counter % printFrequency == 0) {
                String message = String.format("Iteration #%d%n", counter);
                logger.log(Level.INFO, message);
            }
            tree = (counter % 100 != 0) ? tree : new AVLTree();
            stressTestInsertionDeletion(tree, 0, 1000);
            int howMany = rand.nextInt(15);
            for (int i = 0; i < howMany; i++) {
                boolean join = tree.size() == 0 || rand.nextBoolean();
                if (join) {
                    tree = stressTestJoin(tree);
                } else {
                    tree = stressTestSplit(tree);
                }
            }
        } while (++counter <= iterations);
    }

    public static AVLTree createTree(int[] values) {
        AVLTree tree = new AVLTree();
        for (int value : values) {
            String s = String.valueOf(value);
            tree.insert(value, s);
        }
        return tree;
    }
}
