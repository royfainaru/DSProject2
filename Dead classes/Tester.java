public class Tester {

    public static void main(String[] args) {
        HeapNodeTester.findRecursive();
    }


}

class LinkedListTester {
    public static void main(String[] args) {
        // Create a node factory instance
        FibonacciHeap.NodeFactory factory = new NodeFactory(null);

        // Create an instance of the linked list
        LinkedList linkedList = new LinkedList();

        // Create some nodes with keys
        HeapNode node1 = factory.createNode(5);
        HeapNode node2 = factory.createNode(2);
        HeapNode node3 = factory.createNode(7);
        HeapNode node4 = factory.createNode(3);

        // Test the isEmpty() method
        System.out.println("Is the linked list empty? " + linkedList.isEmpty()); // should return true
        System.out.println("Length of the linked list: " + linkedList.length); // should return 0

        // Test the insertFirst() method
        linkedList.insertFirst(node1);
        linkedList.insertFirst(node2);
        linkedList.insertFirst(node3);
        linkedList.insertFirst(node4);
        System.out.println("Length of the linked list: " + linkedList.length); // should return 4

        // Test the getMin() method
        System.out.println("Node with minimal key: " + linkedList.getMin().getKey()); // should return 2

        // Test the deleteMin() method
        System.out.println("Length of the linked list: " + linkedList.length); // should return 3

        // Test the deleteAny() method
        System.out.println("Deleted node with key 7: " + linkedList.cutKey(7).getKey()); // should return 7
        System.out.println("Length of the linked list: " + linkedList.length); // should return 2

        // Test the isEmpty() method again
        System.out.println("Is the linked list empty? " + linkedList.isEmpty()); // should return false

// State tests
        LinkedList list = new LinkedList();

        // Initial state
        if (!list.isEmpty()) {
            throw new AssertionError("1");
        }

        if (list.length != 0) {
            throw new AssertionError("2");
        }

        if (!list.toString().equals("[]")) {
            throw new AssertionError("3");
        }

        if (list.getMin() != null) {
            throw new AssertionError("4");
        }

        // First insertion
        list.insertFirst(factory.createNode(5));

        if (list.isEmpty()) {
            throw new AssertionError("5");
        }

        if (list.length != 1) {
            throw new RuntimeException("6");
        }

        if (!list.toString().equals("[5]")) {
            throw new RuntimeException("7");
        }

        if (list.getMin().key != 5) {
            throw new RuntimeException("8");
        }

        // Second insertion
        list.insertFirst(factory.createNode(3));

        if (list.length != 2) {
            throw new RuntimeException("9");
        }

        if (!list.toString().equals("[3, 5]")) {
            throw new RuntimeException("10");
        }

        if (list.getMin().key != 3) {
            throw new RuntimeException("11");
        }

        // Third insertion
        list.insertFirst(factory.createNode(7));

        if (list.length != 3) {
            throw new RuntimeException("12");
        }

        if (!list.toString().equals("[7, 3, 5]")) {
            throw new RuntimeException("13");
        }

        if (list.getMin().key != 3) {
            throw new RuntimeException("14");
        }

        // Deletion of the node with minimal key
        list.cutMin();

        if (list.length != 2) {
            throw new RuntimeException("15");
        }

        if (!list.toString().equals("[7, 5]")) {
            throw new RuntimeException("16");
        }

        if (list.getMin().key != 5) {
            throw new RuntimeException("17");
        }

        // Deletion of any node
        list.cutKey(7);

        if (list.length != 1) {
            throw new RuntimeException("18");
        }

        if (!list.toString().equals("[5]")) {
            throw new RuntimeException("19");
        }

        if (list.getMin().key != 5) {
            throw new RuntimeException("20");
        }

        // Insertion of a node with key 2
        list.insertFirst(factory.createNode(2));

        if (list.length != 2) {
            throw new RuntimeException("21");
        }

        if (!list.toString().equals("[2, 5]")) {
            throw new RuntimeException("22");
        }

        if (list.getMin().key != 2) {
            throw new RuntimeException("23");
        }

        // Deletion of the node with minimal key again
        list.cutMin();

        if (list.length != 1) {
            throw new RuntimeException("24");
        }

        if (!list.toString().equals("[5]")) {
            throw new RuntimeException("25");
        }

        if (list.getMin().key != 5) {
            throw new RuntimeException("26");
        }

        // Deletion of the remaining node
        list.cutKey(5);

        if (!list.isEmpty()) {
            throw new RuntimeException("26");
        }

        if (list.length != 0) {
            throw new RuntimeException("27");
        }

        if (!list.toString().equals("[]")) {
            throw new RuntimeException("28");
        }

        if (list.getMin() != null) {
            throw new RuntimeException("29");
        }
    }
}


class HeapNodeTester {
    public static void inserts() {
        // Create an instance of a heap
        FibonacciHeap heap = new FibonacciHeap();

        // Create a node factory
        NodeFactory nodeFactory = new NodeFactory(heap);

        // Create a few node instances
        HeapNode node1 = nodeFactory.createNode(5);
        HeapNode node2 = nodeFactory.createNode(7);
        HeapNode node3 = nodeFactory.createNode(3);
        HeapNode node4 = nodeFactory.createNode(6);
        HeapNode node5 = nodeFactory.createNode(10);
        HeapNode node6 = nodeFactory.createNode(8);
        HeapNode node7 = nodeFactory.createNode(9);
        HeapNode node8 = nodeFactory.createNode(15);

        // Create a node chain
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        node4.setNext(node5);

        // Insert a node in the middle of the chain using insertNext
        node2.insertNext(node6);

        // Insert a node in the middle of the chain using insertPrev
        node2.insertPrev(node7);

        // Insert a node to the header of the chain using insertPre
        node1.insertPrev(node8);
    }


    public static void children() {
        // Create an instance of a heap
        FibonacciHeap heap = new FibonacciHeap();

        // Create a node factory
        NodeFactory nodeFactory = new NodeFactory(heap);

        // Create node instances
        HeapNode node1 = nodeFactory.createNode(50);
        HeapNode node2 = nodeFactory.createNode(10);
        HeapNode node3 = nodeFactory.createNode(20);
        HeapNode node4 = nodeFactory.createNode(30);

        // Insert children to node1
        node1.insertChild(node2);
        node1.insertChild(node3);
        node1.insertChild(node4);

        // Reject children
        LinkedList children = node1.rejectChildren();
    }


    public static void findRecursive() {
        // Create an instance of a heap
        FibonacciHeap heap = new FibonacciHeap();

        // Create a node factory
        NodeFactory nodeFactory = new NodeFactory(heap);

        // Create node instances
        HeapNode node1 = nodeFactory.createNode(1);
        HeapNode node2 = nodeFactory.createNode(2);
        HeapNode node3 = nodeFactory.createNode(3);
        HeapNode node4 = nodeFactory.createNode(4);
        HeapNode node5 = nodeFactory.createNode(5);
        HeapNode node6 = nodeFactory.createNode(6);
        HeapNode node7 = nodeFactory.createNode(7);
        HeapNode node8 = nodeFactory.createNode(8);
        HeapNode node9 = nodeFactory.createNode(9);
        HeapNode node10 = nodeFactory.createNode(10);
        HeapNode node11 = nodeFactory.createNode(11);
        HeapNode node12 = nodeFactory.createNode(12);
        HeapNode node13 = nodeFactory.createNode(13);
        HeapNode node14 = nodeFactory.createNode(14);
        HeapNode node15 = nodeFactory.createNode(15);
        HeapNode node16 = nodeFactory.createNode(16);

        LinkedList rootList = new LinkedList();

        rootList.insertFirst(node1);
        rootList.insertFirst(node2);

        node1.insertChild(node3);
        node1.insertChild(node4);
        node1.insertChild(node5);

        node2.insertChild(node6);
        node2.insertChild(node7);
        node2.insertChild(node8);

        node3.insertChild(node9);
        node3.insertChild(node10);

        node4.insertChild(node11);

        node5.insertChild(node12);

        node7.insertChild(node13);
        node7.insertChild(node14);

        node14.insertChild(node15);
        node14.insertChild(node16);

        HeapNode someNode = rootList.findRecursive(3);


    }
}


//
//public class FibonacciHeapTester {
//    public static void main(String[] args) {
//        // Create an instance of the Fibonacci Heap
//        FibonacciHeap heap = new FibonacciHeap();
//
//        // Test the isEmpty() method
//        if (!heap.isEmpty()) {
//            throw new AssertionError("1");
//        }
//
//        // Test the insert() method
//        heap.insert(5);
//        heap.insert(3);
//        heap.insert(8);
//
//        if (heap.isEmpty()) {
//            throw new AssertionError("2");
//        }
//
//        // Test the findMin() method
//        if (heap.findMin().getKey() != 3) {
//            throw new AssertionError("3");
//        }
//
//        // Test the deleteMin() method
//        heap.deleteMin();
//        if (heap.findMin().getKey() != 5) {
//            throw new AssertionError("4");
//        }
//
//        // Test the decreaseKey() method
//        heap.decreaseKey(8, 2); // problem with access
//        if (heap.findMin().getKey() != 2) {
//            throw new AssertionError("5");
//        }
//
//        // Test the delete() method
//        heap.delete(5); // problem with access
//        if (heap.findMin().getKey() != 2) {
//            throw new AssertionError("6");
//        }
//
//        // Test the merge() method
//        FibonacciHeap heap2 = new FibonacciHeap();
//        heap2.insert(7);
//        heap2.insert(6);
//        heap.meld(heap2);
//        if (heap.findMin().getKey() != 2) {
//            throw new AssertionError("7");
//        }
//    }
//}
//
//
