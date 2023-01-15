import java.util.Arrays;

public class MyTest {
    public static void main(String[] args){

        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(1);
        heap.insert(2);
        heap.insert(6);
        heap.insert(10);
        heap.insert(0);

        System.out.println("********** HEAP 1 **********");
        System.out.println("MIN: " + heap.findMin());
        heap.deleteMin();

        System.out.println("top of the heap: " + heap.rootList.toString());
        System.out.println("cuts: " + FibonacciHeap.totalCuts());
        System.out.println("links: " + FibonacciHeap.totalLinks());

        HeapNode x = heap.rootList.findRecursive(10);
        heap.decreaseKey(x,5);
        System.out.println("top of the heap: " + heap.rootList.toString());
        System.out.println("MIN: " + heap.findMin());

        System.out.println("cuts: " + FibonacciHeap.totalCuts());
        System.out.println("links: " + FibonacciHeap.totalLinks());

        System.out.println("top of the heap: " + heap.rootList.toString());
        System.out.println(Arrays.toString(heap.countersRep()));
        System.out.println(heap.rootList.findRecursive(10));
        System.out.println(heap.rootList.findRecursive(5));

        System.out.println("MIN: " + heap.findMin());

        HeapNode x2 = heap.rootList.findRecursive(2);
        heap.decreaseKey(x2,5);
        System.out.println("top of the heap: " + heap.rootList.toString());

        System.out.println("MIN: " + heap.findMin());

        System.out.println("cuts: " + FibonacciHeap.totalCuts());
        System.out.println("links: " + FibonacciHeap.totalLinks());


        FibonacciHeap heap2 = new FibonacciHeap();
        heap2.insert(9);
        heap2.insert(8);
        heap2.insert(11);
        heap2.insert(7);

        System.out.println("********** HEAP 2 **********");
        System.out.println("MIN: " + heap2.findMin());
        System.out.println("top of the heap2: " + heap2.rootList.toString());
        heap2.deleteMin();
        System.out.println("top of the heap2: " + heap2.rootList.toString());
        System.out.println(Arrays.toString(heap2.countersRep()));

        System.out.println(Arrays.toString(heap.countersRep()));
        heap.meld(heap2);
        System.out.println("top of the heap: " + heap.rootList.toString());
        System.out.println(Arrays.toString(heap.countersRep()));


        System.out.println("********** HEAP 1 **********");
        System.out.println("top of the heap: " + heap.rootList.toString());
        System.out.println("counter rep" + Arrays.toString(heap.countersRep()));

        System.out.println("MIN: " + heap.findMin());
        heap.deleteMin();
        System.out.println("top of the heap: " + heap.rootList.toString());
        System.out.println("counter rep" + Arrays.toString(heap.countersRep()));





//
//        ///////////////////
//        // 'LIOR' METHODS // HEAPNODE
//        ///////////////////
//
//        /*************************************************************************************************
//         * Helper method for heap.deleteMin()
//         * Deletes the given node from the list, and plants the children in his place by order
//         * @return HeapNode
//         */
//        public HeapNode deleteMinLIOR() {
//            HeapNode nodeToDelete = minNode;
//            LinkedList children = nodeToDelete.children;
//            cutNode(nodeToDelete);
//            plantBefore(children, nodeToDelete.next);
//            return nodeToDelete;
//        }
//
//
//        /*************************************************************************************************
//         * Helper method for heap.DecreaseKey()
//         * @return HeapNode if found, else null
//         */
//        public HeapNode listDecreaseKey(int key, int d, FibonacciHeap heap) {
//            for (HeapNode node : this) {
//                HeapNode retrievedNode = node.nodeDecreaseKey(key, d, heap);
//                if (retrievedNode != null) {
//                    return retrievedNode;
//                }
//            }
//            return null;
//        }
//
//        /*************************************************************************************************
//         */
//
//
//
//        /*************************************************************************************************
//         * Helper method for heap.DecreaseKey()
//         * @return HeapNode if found, else null
//         */
//        public HeapNode nodeDecreaseKey(int key, int d, FibonacciHeap heap) {
//            if (key == this.getKey()) {
//                if (d == Integer.MAX_VALUE) {
//                    this.key = Integer.MIN_VALUE;
//                } else {
//                    this.key -= d;
//                }
//                HeapNode p = this.getParent();
//                if (p != null) {
//                    if (p.getKey() >= this.getKey()) {
//                        this.siblings.cutNode(this);
//                        heap.increaseCuts();
//                        this.setMark(false);
//                        heap.rootList.insertFirst(this);
//                    }
//                } else {
//                    heap.rootList.updateMin();
//                }
//                return this;
//            }
//
//            if (children.isEmpty()) {
//                return null;
//            }
//            int childrenSizeBefore = children.size;
//            HeapNode returnedNode = children.listDecreaseKey(key, d, heap);
//
//            if (returnedNode != null) {
//                HeapNode p = this.getParent();
//                if (p != null) {
//                    if (childrenSizeBefore > children.size) {
//                        if (!getMark()) {
//                            mark = true;
//                        } else {
//                            this.siblings.cutNode(this);
//                            heap.increaseCuts();
//                            this.setMark(false);
//                            heap.rootList.insertFirst(this);
//                        }
//                    }
//                }
//            }
//            return returnedNode;
//        }
//
//
//        /*************************************************************************************************
//         */


    }

}


