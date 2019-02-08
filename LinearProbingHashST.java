import java.io.FileInputStream;
import java.io.FileNotFoundException;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class LinearProbingHashST<Key, Value>
{
	private static final int INIT_CAPACITY = 4;
	 private int N; // number of key-value pairs in the table
	 private int M = 16; // size of linear-probing table
	 private Key[] keys; // the keys
	 private Value[] vals; // the values
	 
	 public LinearProbingHashST()
	 {
		 this(INIT_CAPACITY);
	 }
	 
	 public LinearProbingHashST(int capacity)
	 {
		 M = capacity;
		 N = 0;
		 keys = (Key[]) new Object[M];
		 vals = (Value[]) new Object[M];
	 }
	 
	 private int hash(Key key)
	 { return (key.hashCode() & 0x7fffffff) % M; }
	 
	 private void resize(int cap) // See page 474.
	 {
		 LinearProbingHashST<Key, Value> t;
		 t = new LinearProbingHashST<Key, Value>(cap);
		 for (int i = 0; i < M; i++)
		 if (keys[i] != null)
		 t.put(keys[i], vals[i]);
		 keys = t.keys;
		 vals = t.vals;
		 M = t.M;
	}
	 
	 public void put(Key key, Value val)
	 {
		 if (N >= M/2) resize(2*M); // double M (see text)
		 int i;
		 for (i = hash(key); keys[i] != null; i = (i + 1) % M)
		 if (keys[i].equals(key)) { vals[i] = val; return; }
		 keys[i] = key;
		 vals[i] = val;
		 N++;
	 }
	 
	 public Value get(Key key)
	 {
		 for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
		 if (keys[i].equals(key))
		 return vals[i];
		 return null;
	 }
	 
	 public boolean contains(Key key) {
	        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
	        return get(key) != null;
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
		  * Elapsed time in milliseconds getting max: 9
		  * Elapsed time in milliseconds getting input: 267
		  * 
		  */
		 
	 }
	 
}