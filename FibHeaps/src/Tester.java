public class Tester {

}

class LinkedListTester {
    public static void main(String[] args) {
        // Create a node factory instance
        NodeFactory factory = new NodeFactory();

        // Create an instance of the linked list
        LinkedList linkedList = new LinkedList();

        // Create some nodes with keys
        FibonacciHeap.HeapNode node1 = factory.createNode(5);
        FibonacciHeap.HeapNode node2 = factory.createNode(2);
        FibonacciHeap.HeapNode node3 = factory.createNode(7);
        FibonacciHeap.HeapNode node4 = factory.createNode(3);

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
        linkedList.deleteMin();
        System.out.println("Length of the linked list: " + linkedList.length); // should return 3

        // Test the deleteAny() method
        System.out.println("Deleted node with key 7: " + linkedList.deleteKey(7).getKey()); // should return 7
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
        list.deleteMin();

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
        list.deleteKey(7);

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
        list.deleteMin();

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
        list.deleteKey(5);

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
