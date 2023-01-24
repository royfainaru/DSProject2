import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DHeapTester_Tamir
{
	public DHeapTester_Tamir(int AmountOfTests, int OperAmountPerTest)
	{
		int passed = 0, failed = 0;
		for (int i = 0; i < AmountOfTests; i++)
		{
			Test t = new Test(OperAmountPerTest);
			t.RunTest();
			System.out.println("Test " + i + " d=" + t.get_d() + ": " + t.getResult());
			passed += t.Passed() ? 1 : 0;
			failed += t.Passed() ? 0 : 1;
		}
		System.out.println("\nPassed: " + passed + " Failed: " + failed + ". Rate: " + (int)((double)passed/(double)(passed+failed)*100) + "%");
	}
	public DHeapTester_Tamir(int d, String preOperations)
	{
		try
		{
			Test t = new Test(d, preOperations);
			t.RunTest();
			System.out.println(t.getResult());
		}
		catch (Exception ex){System.out.println("Run Error:" + ex.getMessage());}
	}
	
	public class Test
	{
		final boolean TEST_EVERY_OPER = true;
		int _maxOperations;
		List<Operation> _operations;
		List<Integer> _keys;
		DHeap _heap;
		ErrorCode _code;
		
		private boolean preMakeMode;
		private int lastExecutedOper = -1;
		
		public Test(int totalNumOfOperations)
		{
			preMakeMode = false;
			Random r = new Random();
			
			_maxOperations = totalNumOfOperations;
			_operations = new ArrayList<Operation>();
			_keys = new ArrayList<Integer>();
			
			int d = r.nextInt(19)+2; // 2-20
			_heap = new DHeap(d, totalNumOfOperations*2);
		}
		public Test(int d, String preOperations) throws Exception
		{
			preMakeMode = true;
			String[] parts = preOperations.split("-");
			if (parts.length != 2)
				throw new Exception();
			
			parts = parts[1].split(";");
			_operations = new ArrayList<Operation>();
			for (String oper : parts)
			{
				Operation o = null;
				if (oper.trim().equals("DeleteMin"))
					o = new DeleteMin();
				else
				{
					String[] type_value = oper.split(":");
					String type = type_value[0].trim();
					if (type.equals("Insert"))
					{
						int key = Integer.parseInt(type_value[1].trim());
						o = new Insert(key);
					}
					else if (type.equals("Delete at"))
					{
						int pos = Integer.parseInt(type_value[1].trim());
						o = new Delete(pos);
					}
					else if (type.equals("arrayToHeap"))
					{
						String arrStr = type_value[1].split("[")[1].split("]")[0];
						int[] items;
						if (arrStr == "")
							items = new int[0];
						else
						{
							String[] arrParts = arrStr.split(",");
							items = new int[arrParts.length];
							for (int i = 0; i < arrParts.length; i++)
							{
								items[i] = Integer.parseInt(arrParts[i].trim());
							}
						}
						o = new ArrayToHeap(items);
					}
					else if (type.equals("DecreaseKey"))
					{
						String[] pos_delta = type_value[1].split(",");
						o = new DecreaseKey(Integer.parseInt(pos_delta[0].trim()), Integer.parseInt(pos_delta[1].trim()));
					}
					else if (type.equals("Sort_Array"))
					{
						String arrStr = type_value[1].split("[")[1].split("]")[0];
						int[] items;
						if (arrStr == "")
							items = new int[0];
						else
						{
							String[] arrParts = arrStr.split(",");
							items = new int[arrParts.length];
							for (int i = 0; i < arrParts.length; i++)
							{
								items[i] = Integer.parseInt(arrParts[i].trim());
							}
						}
						o = new Sort_Array(items);
					}
				}
				
				_operations.add(o);
			}
			
			_maxOperations = _operations.size();
			_keys = new ArrayList<Integer>();
			
			_heap = new DHeap(d, _operations.size()*2);
		}
		public ErrorCode RunTest()
		{
			if (!preMakeMode)
			{
				for (int i = 0; i < _maxOperations; i++)
				{
					OperType t = chooseOperation();
					Operation o = createOperation(t);
					_operations.add(o);
					o.Execute(_heap, _keys);
					lastExecutedOper = i;
					if (TEST_EVERY_OPER)
					{
						_code = TestAll();
						if (_code != ErrorCode.OK)
							return _code;
					}
				}
				if (!TEST_EVERY_OPER)
				{
					_code =  TestAll();
					return _code;
				}
				
				_code =  ErrorCode.OK;
				return _code;
			}
			else
			{
				for (int i = 0; i < _operations.size(); i++)
				{
					_operations.get(i).Execute(_heap, _keys);
					lastExecutedOper = i;
					
					if (TEST_EVERY_OPER)
					{
						_code = TestAll();
						if (_code != ErrorCode.OK)
							return _code;
					}
				}
				if (!TEST_EVERY_OPER)
				{
					_code = TestAll();
					return _code;
				}
				
				_code = ErrorCode.OK;
				return _code;
			}
		}
		
		public OperType chooseOperation()
		{
			Random r = new Random();
			if (_keys.isEmpty())
				return (r.nextInt(100) < 75 ? OperType.Insert : OperType.ArrayToHeap);
			
			int i = _heap.size >= _heap.max_size ? r.nextInt(60)+40 : r.nextInt(100);
			
			if (i < 40)
				return OperType.Insert;
			else if (i < 60)
				return OperType.DeleteMin;
			else if (i < 75)
				return OperType.Delete;
			else if (i < 85)
				return OperType.Decrease_Key;
			else if (i < 92)
				return OperType.ArrayToHeap;
			else
				return OperType.Sort_Array;
		}
		public Operation createOperation(OperType type)
		{
			switch (type)
			{
				case Insert:
					Random r = new Random();
					return new Insert(r.nextInt(_maxOperations * 100));
				case DeleteMin:
					return new DeleteMin();
				case Delete:
					return new Delete();
				case Decrease_Key:
					return new DecreaseKey();
				case ArrayToHeap:
					return new ArrayToHeap();
				case Sort_Array:
					return new Sort_Array();
			}
			return null;
		}
		
		public ErrorCode TestAll()
		{
			if (_operations.get(lastExecutedOper) instanceof Sort_Array)
			{
				if (!((Sort_Array)_operations.get(lastExecutedOper)).isSorted())
					return ErrorCode.Bad_Sort_Array;
				else
					return ErrorCode.OK;
			}
			
			if (!checkChildFormula())
				return ErrorCode.Bad_Child_Formula;
			if (!checkParentFormula())
				return ErrorCode.Bad_Parent_Formula;
			if (!checkSize())
				return ErrorCode.Bad_Size;
			if (!checkMin())
				return ErrorCode.Bad_Get_Min;
			if (!checkPositions())
				return ErrorCode.Bad_pos_Properties;
			if (!checkisHeap())
				return ErrorCode.isHeap_is_false;
			ErrorCode c = checkElems();
			if (c != ErrorCode.OK)
				return c;
			
			return ErrorCode.OK;
		}
		
		
		
		public boolean checkSize()
		{
			return _heap.getSize() == _keys.size();
		}
		public boolean checkMin()
		{
			if (_keys.size() == 0)
				return true;
			int min = Collections.min(_keys);
			return _heap.Get_Min().getKey() == min;
		}
		public boolean checkPositions()
		{
			for (int i = 0; i < _heap.getSize(); i++)
			{
				if (_heap.array[i] != null && _heap.array[i].getPos() != i)
					return false;
			}
			
			return true;
		}
		public boolean checkisHeap()
		{
			boolean res = _heap.isHeap();
			return res;
		}
		public ErrorCode checkElems()
		{
			List<Integer> list = new ArrayList<Integer>(_keys);
			for (int i = 0; i < _heap.getSize(); i++)
			{
				if (!list.contains(_heap.array[i].getKey()))
					return ErrorCode.Missing_Nodes;
				list.remove((Object)_heap.array[i].getKey());
			}
			return list.isEmpty() ? ErrorCode.OK : ErrorCode.Has_Deleted_Nodes;
		}
		public boolean checkChildFormula()
		{
			Random r = new Random();
			int i = r.nextInt(500);
			int d = r.nextInt(19)+2; //d=2->20
			int k = r.nextInt(d)+1; // k=1->d
			int c = DHeap.child(i, k, d);
			
			return c == d*i+k;
		}
		public boolean checkParentFormula()
		{
			Random r = new Random();
			int i = r.nextInt(500); // i=0->499
			int d = r.nextInt(19)+2; //d=2->20
			int p = DHeap.parent(i, d);
			
			return p == (i-1)/d;
		}

		
		public String getResult()
		{
			String res = _code + "";
			if (_code != ErrorCode.OK)
			{
				res += " - ";
				for (int i = 0; i < _operations.size(); i++)
				{
					res += _operations.get(i).toString() + ";";
				}
			}
			return res;
		}

		
		public int get_d()
		{
			return _heap.d;
		}
		
		public boolean Passed()
		{
			return _code == ErrorCode.OK;
		}
		
	}
	
	public interface Operation
	{
		public void Execute(DHeap heap, List<Integer> existingKey);
	}
	
	public class Insert implements Operation
	{
		int _key;
		public Insert(int key)
		{
			_key = key;
		}
		
		public void Execute(DHeap heap, List<Integer> existingKey)
		{
			heap.Insert(new DHeap_Item(_key + "", _key));
			existingKey.add(_key);
		}
		
		@Override
		public String toString()
		{
			return "Insert: " + _key;
		}
	}
	public class DeleteMin implements Operation
	{
		public void Execute(DHeap heap, List<Integer> existingKey)
		{
			int min = Collections.min(existingKey);
			heap.Delete_Min();
			existingKey.remove((Object)min);
		}
		
		@Override
		public String toString()
		{
			return "DeleteMin";
		}
	}
	public class Delete implements Operation
	{
		int _pos = -1;
		public Delete()
		{
		}
		public Delete(int pos)
		{
			_pos = pos; 
		}
		public void Execute(DHeap heap, List<Integer> existingKey)
		{
			if (_pos == -1)
			{
				_pos = (new Random()).nextInt(heap.getSize());
			}
			DHeap_Item item = heap.array[_pos];
			int key = item.getKey();
			
			heap.Delete(item);
			existingKey.remove((Object)key);
		}
		
		@Override
		public String toString()
		{
			return "Delete at: " + _pos;
		}
	}
	public class ArrayToHeap implements Operation
	{
		DHeap_Item[] _array;
		
		public ArrayToHeap()
		{
		}
		public ArrayToHeap(int[] array)
		{
			_array = new DHeap_Item[array.length];
			for (int i = 0; i < _array.length; i++)
			{
				_array[i] = new DHeap_Item(array[i] + "", array[i]);
			}
		}
		
		public void Execute(DHeap heap, List<Integer> existingKey)
		{
			existingKey.clear();
			
			if (_array == null)
			{
				Random r = new Random();
				int n = r.nextInt(100) + 10;
				if (n > heap.max_size)
					n = heap.max_size;
				
				_array = new DHeap_Item[n];
				for (int i = 0; i < n; i++)
				{
					int key = r.nextInt(n*100)+1;
					_array[i] = new DHeap_Item(key + "", key);
					existingKey.add(key);
				}
			}
			else
			{
				for (int i = 0; i < _array.length; i++)
				{
					existingKey.add(_array[i].getKey());
				}
			}
			
			heap.arrayToHeap(_array);
		}

		@Override
		public String toString()
		{
			String res = "arrayToHeap: ["; 
			for (int i = 0; i < _array.length; i++)
			{
				if (i == _array.length - 1)
					res += _array[i].getKey();
				else
					res += _array[i].getKey() + ",";
			}
			res += "]";
			return res;
		}
	}
	public class DecreaseKey implements Operation
	{
		int _pos = -1, _delta = 0;
		public DecreaseKey()
		{
		}
		public DecreaseKey(int pos, int delta)
		{
			_pos = pos;
			_delta = delta;
		}
		public void Execute(DHeap heap, List<Integer> existingKey)
		{
			if (_pos == -1)
			{
				Random r = new Random();
				_pos = r.nextInt(heap.getSize());
				_delta = r.nextInt(5000);
			}
			int key = heap.array[_pos].getKey();
			
			heap.Decrease_Key(heap.array[_pos], _delta);
			existingKey.remove((Object)key);
			existingKey.add(key-_delta);
		}
		
		@Override
		public String toString()
		{
			return "DecreaseKey: " + _pos + ","  + _delta;
		}
	}
	
	public class Sort_Array implements Operation
	{
		List<Integer> _originals;
		int[] _array;
		int _d;
		public Sort_Array()
		{
			_originals = new ArrayList<>();
		}
		public Sort_Array(int[] array)
		{
			_array = array;
			_originals = new ArrayList<>();
			for (int i : _array)
			{
					_originals.add(i);
			}
		}
		
		@Override
		public void Execute(DHeap heap, List<Integer> existingKey)
		{
			Random r = new Random();
			_d = r.nextInt(19)+2; // d=2-20
			if (_array == null)
			{
				int n = r.nextInt(2000); // items=0-1999
				
				_array = new int[n];
				for (int i = 0; i < n; i++)
				{
					_array[i] = r.nextInt(n*100)+1;
					_originals.add(_array[i]);
				}
			}
			
			DHeap.DHeapSort(_array, _d);
		}
	
		public boolean isSorted()
		{
			if (_array == null)
				return false;
			
			List<Integer> temp = new ArrayList<>(_originals);
			if (_array.length != temp.size())
				return false;
			
			if (_array.length == 0)
				return true;
			
			int prev = _array[0];
			temp.remove((Object)_array[0]);
			for (int i = 1; i < _array.length; i++)
			{
				if (prev > _array[i] || !temp.contains(_array[i]))
					return false;
				temp.remove((Object)_array[i]);
			}
			
			return temp.isEmpty();
			
		}

		@Override
		public String toString()
		{
			String res = "Sort_Array: ["; 
			for (int i = 0; i < _originals.size(); i++)
			{
				if (i == _originals.size() - 1)
					res += _originals.get(i);
				else
					res += _originals.get(i) + ",";
			}
			res += "]";
			return res;
		}
	}
	
	public enum OperType
	{
		Insert,
		Delete,
		DeleteMin,
		ArrayToHeap,
		Decrease_Key,
		Sort_Array
	}

	public enum ErrorCode
	{
		OK,
		Bad_Size,
		Bad_Get_Min,
		Bad_pos_Properties,
		isHeap_is_false,
		Missing_Nodes,
		Has_Deleted_Nodes,
		Bad_Sort_Array,
		Bad_Child_Formula,
		Bad_Parent_Formula
	}
}
