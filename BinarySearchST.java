import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Instant;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Queue;

import java.util.*;

public class BinarySearchST<Key extends Comparable<Key>, Value>
{
	 private Key[] keys;
	 private Value[] vals;
	 private int N = 0;
	 private static final int INIT_CAPACITY = 2;
	 
	 public BinarySearchST()
	 {
		 this (INIT_CAPACITY);
	 }
	 
	 public BinarySearchST(int capacity)
	 { // See Algorithm 1.1 for standard array-resizing code.
		 keys = (Key[]) new Comparable[100];
		 vals = (Value[]) new Object[100];
	 }
	 
	 	 
	 public int size()
	 { return N; }
	 
	 public boolean isEmpty(){
		 return size() == 0;
	 }
	 
	 public Value get(Key key)
	 {
		 if (isEmpty()) return null;
		 int i = rank(key);
		 if (i < N && keys[i].compareTo(key) == 0) return vals[i];
		 else return null;
	 }
	 
	 public int rank(Key key)
	 {
	  int lo = 0, hi = N-1;
	  while (lo <= hi)
	  {
		  int mid = lo + (hi - lo) / 2;
		  int cmp = key.compareTo(keys[mid]);
		  if (cmp < 0) hi = mid - 1;
		  else if (cmp > 0) lo = mid + 1;
		  else return mid;
	  }
	  return lo;
	 }
	 
	 public void put(Key key, Value val)
	 { // Search for key. Update value if found; grow table if new.
		 int i = rank(key);
		 if (i < N && keys[i].compareTo(key) == 0)
		 { vals[i] = val; return; }
		 for (int j = N; j > i; j--)
			 
		 { keys[j] = keys[j-1]; vals[j] = vals[j-1]; }
		 keys[i] = key; vals[i] = val;
		 N++;
	 }
	 
	 public boolean contains (Key key)
	 {
		 return get(key) != null;
	 }
	 
	 public Key min()
	 {
		 return keys[0];
	 }
	 
	 public Key max()
	 {
		 return keys[N-1];
	 }
	 
	 public Iterable<Key> keys()
	 {
		 return keys(min(), max());
	 }
	 
	 public Iterable<Key> keys(Key lo, Key hi)
	 {
		 Queue<Key> queue = new Queue<Key>();
		 if (lo.compareTo(hi) > 0) return queue;
		 for (int i = rank(lo); i < rank(hi); i++)
			 queue.enqueue(keys[i]);
		 if (contains(hi)) queue.enqueue(keys[rank(hi)]);
		 return queue;
	 }
	 //public void delete(Key key)
	 // See Exercise 3.1.16 for this method.
	 
	 public static void main(String[] args) throws FileNotFoundException
	 {
		 long startTimeInput = System.currentTimeMillis();
		 //long startTime = Instant.now().toEpochMilli();
		 int minlen = 0; //Integer.parseInt(args[0]); // key-length cutoff
		 BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
		 System.setIn(new FileInputStream("taleOfCities2.txt"));
		 int i = 0;
		 while (i < 100)
		 //while (!StdIn.isEmpty())
		 { // Build symbol table and count frequencies.
			 String word = StdIn.readString();
			 if (word.length() < minlen) continue; // Ignore short keys.
			 if (!st.contains(word)) st.put(word, 1);
			 else st.put(word, st.get(word) + 1);
			 i++;
		 }
		 long endTimeInput = System.currentTimeMillis();
		 long startTimeMax = Instant.now().toEpochMilli();
		 // Find a key with the highest frequency count.
		 String max = "";
		 st.put(max, 0);
		 for (String word : st.keys())
			 if (st.get(word) > st.get(max))
				 	max = word;
		 StdOut.println(max + " " + st.get(max));
		 //long endTime2 = System.currentTimeMillis();
		 long endTimeMax = Instant.now().toEpochMilli();
		 long timeMax = endTimeMax - startTimeMax;
		 long timeInput = endTimeInput - startTimeInput;
		 System.out.println("Elapsed time finding max in milliseconds: " + timeMax);
		 System.out.println("Elapsed time inputing in milliseconds: " + timeInput);
		 
		 /*
		  * 
		  * of 6
		  * Elapsed time finding max in milliseconds: 2
		  * Elapsed time inputing in milliseconds: 35
		  * 
		  * 
		  * 
		  */
	 }
	 
}

