import java.lang.System;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class Theoretical {
    public static void main(String[] args) {
        final int[] iValues = {5, 10, 15, 20};
        long[][] results = new long[4][4];
        int j = 0;
        for (int i : iValues) {
            results[j++] = Q1(i);
        }
        print2DArray(results);
    }

    public static FibonacciHeap.HeapNode[] Q1inserts(FibonacciHeap heap, int i) {
        int m = (int)Math.pow(2, i);
        FibonacciHeap.HeapNode[] array = new FibonacciHeap.HeapNode[m + 1];
        for (int k = m - 1, j = 0; k >= -1; k--, j++) {
            array[j] = heap.insert(k);
        }
        return array;
    }

    public static void Q1decreaseKeys(FibonacciHeap heap, List<FibonacciHeap.HeapNode> list, int m) {
        for(FibonacciHeap.HeapNode node : list) {
            heap.decreaseKey(node, m + 1);
        }
    }

    public static long[] Q1(int i) {
        FibonacciHeap heap = new FibonacciHeap();

        long initTime = System.currentTimeMillis();
        FibonacciHeap.HeapNode[] nodesArray = Q1inserts(heap, i);

        long t1 = System.currentTimeMillis();

        ///
        int differenceCounter = 2;
        List<FibonacciHeap.HeapNode> decreaseKeyNodesList = new ArrayList<>();
        decreaseKeyNodesList.add(nodesArray[0]);
        int index = 0;
        while (nodesArray[index].getKey() != 1) {
            index += differenceCounter;
            differenceCounter *= 2;
            decreaseKeyNodesList.add(nodesArray[index]);
        }
        List<FibonacciHeap.HeapNode> decreaseKeyNodesListReversed = new ArrayList<>();
        for (int j = decreaseKeyNodesList.size() - 1; j >= 0; j--) {
            decreaseKeyNodesListReversed.add(decreaseKeyNodesList.get(j));
        }
        ///
        long t2 = System.currentTimeMillis();

        heap.deleteMin();

        int m = nodesArray.length - 1;
        Q1decreaseKeys(heap, decreaseKeyNodesListReversed, m);

        long tFinal = System.currentTimeMillis();

        long measTime = (tFinal - t2) + (t1 - initTime);
        return new long[]{measTime, (long)FibonacciHeap.totalLinks(), (long)FibonacciHeap.totalCuts(), (long)heap.potential()};
    }

    public static void print2DArray(long[][] array) {
        for (long[] row : array) {
            System.out.println(Arrays.toString(row));
        }
    }




}
