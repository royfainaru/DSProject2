import java.util.Arrays;
import java.lang.Math;
public class ExTester
{
	public static int[] sortInts(int[] arr)
	{
		int[] sortedArr = new int[arr.length];
		for (int j=0; j<arr.length; j++)
		{
			sortedArr[j] = arr[j];
		}
		Arrays.sort(sortedArr);
		return sortedArr;
	}
	
	public static boolean arraysIdentical(int[] arr1, int[] arr2)
	{
		if (arr1.length != arr2.length)
		{
			return false;
		}
		for (int j=0; j<arr1.length; j++)
		{
			if (arr1[j] != arr2[j])
			{
				return false;
			}
		}
		return true;
	}
	
	// Log base 2
	public static double log2(double a)
	{
		return Math.log(a)/Math.log(2);
	}
	
	// In a balanced tree, log_2(n)<=height<=2log_2(n+1) where n is the number of elements in the tree. 
	public static boolean isHeightOk(int n, int userHeight)
	{
		double upper = 2*log2(n+1);
		double lower = Math.floor(log2(n));
		
		return (userHeight >= lower) && (userHeight <= upper);
	}
	// The average depth should be shallower than the max height.
	public static boolean isDepthOk(int n, int userAvgDepth)
	{
		return userAvgDepth <= 2*log2(n+1);
	}
	
	public static void main(String[] args)
	{
		// initialize tests success array to false
		boolean[] success = new boolean[11];
		for (int i=0; i<success.length; i++)
		{
			success[i] = false;
		}
		
		// create array of values between 800-1800
		// like this - 800, 801, 802, 803, 804
		int[] valuesTemp = new int[1000];
		for (int j=0; j<valuesTemp.length; j++)
		{
			valuesTemp[j] = 800 + j;
		}
		
		// mix the values - create a new list of values taken
		// one from the start one from the end, alternately
		// i.e. values[0], values[-1], values[1], values[-2] ...
		int[] values = new int[1000];
		{
			int k = 0;
			for (int j=0; j< (values.length / 2); j++)
			{
				values[k] = valuesTemp[j];
				k++;
				values[k] = valuesTemp[valuesTemp.length-1-j];
				k++;
			}
		}
		
		// create custom array of values
		int[] values3 = new int[] {17,6,1,19,18,3,2,10,13,12,
				20,15,4,11,7,16,9,5,8,14,21};
		
		
		RBTree rbTree = null;
		MyTree myTree = null;
		int last_value_removed = -1;
		
		for (int i=0; i<(success.length); i++)
		{
			try
			{
				// Initialization
				if (i==0)
				{
					rbTree = new RBTree();
					success[i] = rbTree.empty() && rbTree.toIntArray().length == 0;
				}
				// Insert Sanity
				else if (i==1)
				{
					int n = 0;
					myTree = new MyTree();
					for (int j=0; j<values.length; j++)
					{
						rbTree.insert(values[j]);
						myTree.insert(values[j]);
					}
					
					for (int j=0; j<values.length; j++)
					{
						if (!rbTree.contains(values[j]))
						{
							n++;
						}
					}
					success[i] = (n==0) && rbTree.toIntArray().length == values.length; 

				}
				
				// ToDoubleArray
				else if (i==2)
				{
					success[i] = arraysIdentical(sortInts(myTree.array()), 
									rbTree.toIntArray());
				}
				// Delete
				else if (i==3 || i==5)
				{
					int n=0;
					
					// delete values going from the middle outwards
					// i.e. 1000, 1002, 998, ..
					// do it in 2 chunks
					int chunk_size = values.length/4;
					for (int j=0; j<2; j++)
					{
						// delete a chunk of values
						int start = j*chunk_size;
						for (int k=start; k < (start+chunk_size); k++)
						{
							rbTree.delete(values[values.length-1-k]);
							myTree.delete(values[values.length-1-k]);
							last_value_removed = values[values.length-1-k];
						}
						// check correctness
						for (int k=0; k<values.length; k++)
						{
							if (rbTree.contains(values[k]) != myTree.contains(values[k]))
							{
								n++;
							}
						}
						if (!arraysIdentical(rbTree.toIntArray(),
								sortInts(myTree.array())))
						{
							n++;
						}
					}
					success[i] = (n==0);
				}
				// Re-Insert
				else if (i==4)
				{
					int n=0;
					// add values going from the middle outwards
					// i.e. 1000, 1002, 998, ..
					// do it in 2 chunks
					int chunk_size = values.length/4;
					for (int j=0; j<2; j++)
					{
						// insert a chunk of values
						int start = j*chunk_size;
						for (int k=start; k < (start+chunk_size); k++)
						{
							rbTree.insert(values[values.length-1-k]);
							myTree.insert(values[values.length-1-k]);
						}
						// check correctness
						for (int k=0; k<values.length; k++)
						{
							if (rbTree.contains(values[k]) != myTree.contains(values[k]))
							{
								n++;
							}
						}
						if (!arraysIdentical(rbTree.toIntArray(),
								sortInts(myTree.array())))
						{
							n++;
						}
					}
					success[i] = (n==0);
				}
				// Min Max
				else if (i==6)
				{
					int n = 0;
					for (int j=0; values[j]!=last_value_removed; j++)
					{
						rbTree.delete(values[j]);
						myTree.delete(values[j]);
						int[] arr = rbTree.toIntArray();
						if (values[j+1]==last_value_removed)
						{
							if (arr.length > 0)
							{
								n++;
							}
						}
						else
						{
							if (arr.length != myTree.size() ||
									arr[0] != rbTree.min() ||
									arr[0] != myTree.min() ||
									arr[arr.length-1] != rbTree.max() ||
									arr[arr.length-1] != myTree.max())
							{
								n++;
							}
						}
					}
					success[i] = (n == 0) && rbTree.empty();
				}
				// Insert random ordered ints
				else if (i==7)
				{
					int n=0;
					rbTree = new RBTree();
					myTree = new MyTree();
					for (int j=0; j<values3.length; j++)
					{
						rbTree.insert(values3[j]);
						myTree.insert(values3[j]);
						if (rbTree.max() != myTree.max() ||
								rbTree.min() != myTree.min())
						{
							n++;
						}
						for (int k=0; k<values3.length; k++)
						{
							if (rbTree.contains(values3[k]) != myTree.contains(values3[k]))
							{
								n++;
							}
						}
						if (!arraysIdentical(rbTree.toIntArray(),
								sortInts(myTree.array())))
						{
							n++;
						}
					}
					success[i] = (n == 0);
				}
				// Min/Max
				else if (i==8)
				{
					int n=0;
					for (int j=0; j<values3.length; j++)
					{
						rbTree.delete(values3[values3[j]-1]);
						myTree.delete(values3[values3[j]-1]);
						if (myTree.size() > 0)
						{
							if (rbTree.max() != myTree.max() ||	rbTree.min() != myTree.min())
							{
								n++;
							}
						}
						else
						{
							if (!rbTree.empty())
							{
								n++;
							}
						}
						for (int k=0; k<values3.length; k++)
						{
							if (rbTree.contains(values3[k]) != myTree.contains(values3[k]))
							{
								n++;
							}
						}
						if (!arraysIdentical(rbTree.toIntArray(),
								sortInts(myTree.array())))
						{
							n++;
						}
					}
					success[i] = (n == 0);
				}
				// Checking tree height
				else if (i==9)
				{
					int n = 0;
				
					// Creating a new RBTree and MyTree
					rbTree = new RBTree();
					myTree = new MyTree();
					

					// Not checking empty tree - wasn't well defined
					if (rbTree.maxDepth() != -1 || rbTree.minLeafDepth()!= -1)
					{
						//n++;
					}
					
					// Inserting items
					for (int j = 0; j < values.length; j++)
					{
						rbTree.insert(values[j]);
						
						if ((j % 10) == 0)
						{
							
							if (!isHeightOk(j+1,rbTree.maxDepth()))
							{
								n++;
							}							
						}
					}
					if (!isHeightOk(values.length,rbTree.maxDepth()))
					{
						n++;
					}
					// Deleting items
					for (int j = 0; j < values.length; j++)
					{
						rbTree.delete(values[j]);
						if (((j % 10) == 0) && (j+1 <  values.length))
						{
							if (!isHeightOk(values.length-(j+1), rbTree.maxDepth()))
							{
								n++;
							}
						}
					}

					// Not checking empty tree - wasn't well defined
					if (rbTree.maxDepth() != 0)
					{
						//n++;
					}
					
					success[i] = (n == 0);

				}
				// Checking tree depth
				else if (i==10)
				{
					
					int n = 0;
				
					// Creating a new RBTree and MyTree
					rbTree = new RBTree();
					myTree = new MyTree();
					
					// Not checking empty tree - wasn't well defined
					if (rbTree.maxDepth() != -1 || rbTree.minLeafDepth()!= -1)
					{
						//n++;
					}
					
					// Inserting items
					for (int j = 0; j < values.length; j++)
					{
						rbTree.insert(values[j]);
						
						if ((j % 10) == 0)
						{
							if (!((isDepthOk(j+1,rbTree.maxDepth()))  && (isDepthOk(j+1,rbTree.minLeafDepth()))))
							{
								n++;
							}							
						}
					}
					
					if (!((isDepthOk(values.length,rbTree.maxDepth()))  && (isDepthOk(values.length,rbTree.minLeafDepth()))))
					{
						n++;
					}
					
					// Deleting items
					for (int j = 0; j < values.length; j++)
					{
						rbTree.delete(values[j]);
						
						if (((j % 10) == 0) && (j+1 <  values.length))
						{
							
							if (!((isDepthOk(values.length-(j+1),rbTree.maxDepth()))  && (isDepthOk(values.length-(j+1),rbTree.minLeafDepth()))))
							{
								n++;
							}
						}
					}

					// Not checking empty tree - wasn't well defined
					if (rbTree.maxDepth() != -1 || rbTree.minLeafDepth()!= -1)
					{
					//	n++;
					}
					
					success[i] = (n == 0);

				}
			}
			catch (Exception e)
			{ 
				System.out.println("Exception Caught On Test" + i + " : "+e);
			}
			//System.out.println("Success On Test " + i + " = " + success[i]);
		}
			
		int n = 0;
		int count=0;
		for (boolean value : success)
		{
			System.out.println(value+" "+count);
			count++;
			if (value)
			{
				n++;
			}
		}
		//System.out.println("=========================================");
		//System.out.println("Total : " + n + "/" + (success.length));		
		System.out.println(n);		
		
		
	}
	

}
