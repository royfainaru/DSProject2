import java.util.Arrays;
import java.lang.Math;




public class Ex2Tester
{
	final private static int SCENARIO_NUM = 10;
	final private static int TEST_NUM = 24;
	final private static int MAX_SIZE = 100000;
	private static int testCount = 0;
	private static int successCount = 0;
	private static int lastTestInPrevScen = 0;
	private static int prevScen = 0;
	
	private static void performTest(boolean success, int scenario) {

		successCount += (success ? 1 : 0);

		if (scenario != prevScen) {
			lastTestInPrevScen = testCount;
			prevScen = scenario;
		}
		
		testCount++;
		
		if (success)
		{
			//System.out.println("Success on test "+(testCount - lastTestInPrevScen)+" in scenario "+scenario); 
		}
		else
		{
			//System.out.println("Failure on test "+(testCount - lastTestInPrevScen)+" in scenario "+scenario); 
		}
		
	}
	
	public static double log2(double a)
	{
		return Math.log(a)/Math.log(2);
	}
	
	
	
	private static int compareHeapItems(int a, int b) {
		if ( a == b )
		{
			return 0;
		}

		return (a <= b ? -1 : 1);
	}
		
	
	public static void main(String[] args)
	{		

		BinomialHeap heap = null;
		// create array of values between 700-1700
		// like this - 700, 702, 702, 704, 704, 706, 706 ...
		int[] values = new int[1000];
		for (int j=0; j<values.length; j++)
		{			
			//700+j+j%2
			values[j]=700+j+j%2;
		}
		
		// hide some minimum value in it
		int hiddenMin = 113;
		values[values.length / 2] = hiddenMin;
		
		// create array of values 20,19,18,....,1
		int[] values2 = new int[20];
		for (int j=0; j<values2.length; j++)
		{
			values2[j] = values2.length - j;
		}
		
		// create custom array of values
		int[] values_3 = new int[] {6,1,19,17,9,2,10,12,18,
				5,15,11,4,16,13,20,7,3,8,14};
		
		int[] values3 = new int[values_3.length]; 
		
		for (int i = 0; i < values_3.length; i++) {
			values3[i] = values_3[i];
		}
		
		Arrays.sort(values_3);

		//int testCount = 0;
		int prevLen = 0;
		for (int i=0; i<SCENARIO_NUM; i++)
		{
			try
			{
				if (i==0)
				{
					// First test - Create a new heap and check if it's valid
					heap = new BinomialHeap();
					performTest(heap.isValid(),i);
					performTest(heap.size() == 0,i);
					performTest(heap.empty(),i);
				}
				else if (i==1)
				{
					// Second test - Insert values and check if the heap is still valid and the minimum makes sense
					for (int value : values)
					{
						heap.insert(value);
					}

					performTest(heap.isValid(),i);
					performTest(heap.size() == values.length,i);
					performTest(compareHeapItems(heap.findMin(), hiddenMin)== 0,i);

				}
				else if (i==2)
				{				
					// Third test - Delete the minimum multiple times and see if the heap is still valid and the minimum is updated
					int n = 0;
					
					heap.deleteMin();

					if (heap.size() != values.length-1 || heap.findMin()!= 700) {
						n++;
					}
					
					heap.deleteMin();

					if (heap.size() != values.length-2 || heap.findMin() != 702) {
						n++;
					}

					heap.deleteMin();

					if (heap.size() != values.length-3 || heap.findMin() != 702) {
						n++;
					}
					
					heap.deleteMin();

					performTest(heap.isValid(),i);
					performTest(heap.size() == values.length-4,i);
					performTest((n==0) && heap.findMin()== 704,i);//No one Has Fallen
					
					prevLen = heap.size();
				}
				else if (i==3)
				{
					// 4th test - Insert values and check the heap's consistency
					int n = 0;
					for (int value : values3)
					{
						heap.insert(value);
						if (!heap.isValid() || heap.size() == 0)
						{
							n++;
						}
					}
					
					performTest(heap.isValid(),i);
					performTest(n == 0 ,i);
					performTest(heap.size() == (prevLen + values3.length),i);					
				}
				else if (i==4)
				{
					// 5th test - multiple deletion of minimum values
					int n = 0;
					for (int j=(values2.length-1); j>=0; j--)
					{
						prevLen = heap.size();
						if (!heap.isValid() || compareHeapItems(heap.findMin(),values2[j]) != 0)
							//every node in values 2 must be lower 
						{
							n++;
						}
						heap.deleteMin();
						if (!heap.isValid() || heap.size()!=(prevLen-1))
						{
							n++;
						}
					}
					
					// Check if the heap is intact
					performTest(n == 0,i);
					// Check if the minimum makes sense
					performTest(heap.findMin() == 704,i);
					
					prevLen = heap.size();
				}
				else if (i==5)
				{
					// 6th test - array to heap and check minimum

					heap.arrayToHeap(values_3);

					performTest(heap.isValid(),i);
					performTest(heap.size() == values3.length,i);
					
					performTest(heap.findMin() == values_3[0],i);
				}
				else if (i==6)
				{
					// 7th test - Consistency - deleteMin, size, isValid
					int n = 0;
					for (int j = 1; j < values_3.length; j++) {
						heap.deleteMin();
						if (heap.findMin() != values_3[j]) {
							n++;
						}
					}
					heap.deleteMin();
					performTest(heap.isValid(),i);
					performTest(heap.size() == 0,i);
					performTest(n == 0,i);
				}
				else if (i == 7)
				{
					//8th test - Tree Size
					int n=0;
					for (int j=0;j<heap.treesSize().length;j++)
					{
						if (log2(heap.treesSize()[j])==Math.floor(log2(heap.treesSize()[j])))
						{
							n++;
						}
					}
					performTest(n==0,i);
				}
				else if (i==8)
				{ 	
					// 9th test - empty heap
					while (heap.size() != 0)
					{
						System.out.println(heap.size());
						heap.deleteMin();
					}
					
					performTest(heap.isValid(),i);					
				}
				
				else if (i==9) {
					// 10th test - meld
					
					int n = 0;
					for (int value : values)
					{
						heap.insert(value);
					}
					BinomialHeap heap2=new BinomialHeap();
					BinomialHeap heap2_test=new BinomialHeap();
					for (int value : values2)
					{
						heap2.insert(value);
						heap2_test.insert(value);
					}
					prevLen=heap.size();
					heap.meld(heap2);
					performTest(heap.isValid(),i);
					performTest(heap.size()==prevLen+heap2.size(),i);
					performTest(heap.findMin()==heap2.findMin(),i);
					//Test of values
					for (int j = 1; j < heap2_test.size(); j++) {
						if (heap.findMin()!=heap2_test.findMin())
						{
							n++;
						}
						heap.deleteMin();
						heap2_test.deleteMin();
						}
					performTest(n==0, i);
					}
					
					
				}
			
			catch (Exception e)
			{
			//	System.out.println("Exception Caught On Test" + i + " : "+e);
			}
		}

	//	System.out.println("=========================================");
		System.out.println("Total : " + successCount + "/" + testCount);
		System.out.println(/*"Grade : " +*/ (float) 100 * successCount / testCount);
		System.out.println(successCount);
	}
	

}
