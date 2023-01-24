import java.util.Random;

public class DocMeasurement
{
	private int sort_d2m1000, sort_d2m10000, sort_d2m100000;
	private int sort_d3m1000, sort_d3m10000, sort_d3m100000;
	private int sort_d4m1000, sort_d4m10000, sort_d4m100000;
	private int decrease_d2x1, decrease_d2x100, decrease_d2x1000;
	private int decrease_d3x1, decrease_d3x100, decrease_d3x1000;
	private int decrease_d4x1, decrease_d4x100, decrease_d4x1000;
	public DocMeasurement()
	{
		for (int i = 0; i < 10; i++)
		{ 
			sort_d2m1000 += getSortComparisons(2, 1000);
			sort_d2m10000 += getSortComparisons(2, 10000);
			sort_d2m100000 += getSortComparisons(2, 100000);
			sort_d3m1000 += getSortComparisons(3, 1000);
			sort_d3m10000 += getSortComparisons(3, 10000);
			sort_d3m100000 += getSortComparisons(3, 100000);
			sort_d4m1000 += getSortComparisons(4, 1000);
			sort_d4m10000 += getSortComparisons(4, 10000);
			sort_d4m100000 += getSortComparisons(4, 100000);
			decrease_d2x1 += getDecreaseComparisons(2, 1);
			decrease_d2x100 += getDecreaseComparisons(2, 100);
			decrease_d2x1000 += getDecreaseComparisons(2, 1000);
			decrease_d3x1 += getDecreaseComparisons(3, 1);
			decrease_d3x100 += getDecreaseComparisons(3, 100);
			decrease_d3x1000 += getDecreaseComparisons(3, 1000);
			decrease_d4x1 += getDecreaseComparisons(4, 1);
			decrease_d4x100 += getDecreaseComparisons(4, 100);
			decrease_d4x1000 += getDecreaseComparisons(4, 1000);
		}
		print();
	}
	
	private int getSortComparisons(int d, int m)
	{
		int[] keys = new int[m];
		Random r = new Random();
		for (int i = 0; i < m; i++)
		{
			keys[i] = r.nextInt(1001);
		}
		return DHeap.DHeapSort(keys, d);
	}
	
	private int getDecreaseComparisons(int d, int x)
	{
		int count = 0;
		
		DHeap_Item[] items = new DHeap_Item[100000];
		Random r = new Random();
		DHeap heap = new DHeap(d, 100000);
		for (int i = 0; i < 100000; i++)
		{
			int key = r.nextInt(1001);
			items[i] = new DHeap_Item(key + "", key);
			heap.Insert(items[i]);
		}
		for (int i = 0; i < 100000; i++)
		{
			count += heap.Decrease_Key(items[i], x);
		}
		return count;
	}
	
	public void print()
	{
		System.out.println("Avarage comparisons amount over 10 itirations:");
		System.out.println("sort_d2m1000: " + (double)sort_d2m1000/10.0);
		System.out.println("sort_d2m10000: " + (double)sort_d2m10000/10.0);
		System.out.println("sort_d2m100000: " + (double)sort_d2m100000/10.0);
		System.out.println("sort_d3m1000: " + (double)sort_d3m1000/10.0);
		System.out.println("sort_d3m10000: " + (double)sort_d3m10000/10.0);
		System.out.println("sort_d3m100000: " + (double)sort_d3m100000/10.0);
		System.out.println("sort_d4m1000: " + (double)sort_d4m1000/10.0);
		System.out.println("sort_d4m10000: " + (double)sort_d4m10000/10.0);
		System.out.println("sort_d4m100000: " + (double)sort_d4m100000/10.0);
		System.out.println("decrease_d2x1: " + (double)decrease_d2x1/10.0);
		System.out.println("decrease_d2x100: " + (double)decrease_d2x100/10.0);
		System.out.println("decrease_d2x1000: " + (double)decrease_d2x1000/10.0);
		System.out.println("decrease_d3x1: " + (double)decrease_d3x1/10.0);
		System.out.println("decrease_d3x100: " + (double)decrease_d3x100/10.0);
		System.out.println("decrease_d3x1000: " + (double)decrease_d3x1000/10.0);
		System.out.println("decrease_d4x1: " + (double)decrease_d4x1/10.0);
		System.out.println("decrease_d4x100: " + (double)decrease_d4x100/10.0);
		System.out.println("decrease_d4x1000: " + (double)decrease_d4x1000/10.0);
	}
}

