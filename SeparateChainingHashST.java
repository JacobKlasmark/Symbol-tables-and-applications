import java.io.FileInputStream;
import java.io.FileNotFoundException;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SequentialSearchST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SeparateChainingHashST<Key, Value>
{
	 private int N; // number of key-value pairs
	 private int M; // hash table size
	 private SequentialSearchST<Key, Value>[] st; // array of ST objects
	 
	 public SeparateChainingHashST()
	 { this(997); }
	 
	 public SeparateChainingHashST(int M)
	 { // Create M linked lists.
		 this.M = M;
		 st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
		 for (int i = 0; i < M; i++)
		 st[i] = new SequentialSearchST();
	 }
	 
	 private int hash(Key key)
	 { return (key.hashCode() & 0x7fffffff) % M; }
	 
	 public Value get(Key key)
	 { return (Value) st[hash(key)].get(key); }
	 
	 public void put(Key key, Value val)
	 { st[hash(key)].put(key, val); }
	 
	 public boolean contains(Key key) {
	        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
	        return get(key) != null;
	    } 
	 
	 public Iterable<Key> keys()
	 {
		 Queue<Key> queue = new Queue<Key>();
	     for (int i = 0; i < M; i++) {
	         for (Key key : st[i].keys())
	             queue.enqueue(key);
	     }
	     return queue;
	 }
	 
	 public static void main(String[] args) throws FileNotFoundException
	 {
		 System.setIn(new FileInputStream("taleOfCities2.txt"));
		 
		 int minlen = 0; // key-length cutoff
		 SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
		 
		 long startTimeInput = System.currentTimeMillis();
		 while (!StdIn.isEmpty())
		 { // Build symbol table and count frequencies.
			 String word = StdIn.readString();
			 if (word.length() < minlen) continue; // Ignore short keys.
			 if (!st.contains(word)) st.put(word, 1);
			 else st.put(word, st.get(word) + 1);
		 }
		 long endTimeInput = System.currentTimeMillis();
		 
		 long startTimeMax = System.currentTimeMillis();
		 // Find a key with the highest frequency count.
		 String max = "";
		 st.put(max, 0);
		 for (String word : st.keys())
			 if (st.get(word) > st.get(max))
				 max = word;
		 
		 long endTimeMax = System.currentTimeMillis();
		 StdOut.println(max + " " + st.get(max));
		 
		 long maxTime = endTimeMax - startTimeMax;
		 long inputTime = endTimeInput - startTimeInput;
			
		 System.out.println("Elapsed time in milliseconds getting max: " + maxTime);
		 System.out.println("Elapsed time in milliseconds getting input: " + inputTime);
		 
		 /*the 7577
		  * Elapsed time in milliseconds getting max: 8
		  * Elapsed time in milliseconds getting input: 251
		  * 
		  */
		 
		 
	 }
}