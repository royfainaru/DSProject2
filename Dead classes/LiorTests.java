import java.util.Arrays;

public class LiorTests {
    public static void main(String[] args){
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(2);
        heap.insert(1);
        heap.insert(3);
        heap.deleteMin();
        heap.deleteMin();
        heap.deleteMin();
    }

}