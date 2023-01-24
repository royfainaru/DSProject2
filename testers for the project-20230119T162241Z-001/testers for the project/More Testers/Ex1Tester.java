//package ex1;

import java.util.*;

class MyTree
{
	MyTree()
	{
		list = new LinkedList<Integer>();
	}
	
	public int size()
	{
		return list.size();
	}
	public void insert(int v)
	{
		list.add((Integer)v);
	}
	
	public void delete(int v)
	{
		for (Integer v2:list)
		{
			if (v == (int)v2)
			{
				list.remove(v2);
				return;
			}
		}
	}
	public int min()
	{
		int m = -1;
		for (Integer v:list)
		{
			if (m==-1 || v<=m)
			{
				m = v;
			}
		}
		return m;
	}
	public int max()
	{
		int m = -1;
		for (Integer v:list)
		{
			if (m==-1 || v>=m)
			{
				m = v;
			}
		}
		return m;
	}
	public boolean contains(int v)
	{
		for (Integer v2:list)
		{
			if (v == (int)v2)
			{
				return true;
			}
		}
		return false;
	}
	
	public int[] array()
	{
		Object[] arr1 = new Integer[list.size()]; 
		int[] arr2 = new int[list.size()];
		arr1 = list.toArray();
		for (int j=0; j<arr1.length; j++)
		{
			arr2[j] = (Integer)(arr1[j]);
		}
		return arr2;
	}
	
	private LinkedList<Integer> list;
}

