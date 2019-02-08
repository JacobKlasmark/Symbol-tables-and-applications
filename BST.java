import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Instant;
import edu.princeton.cs.algs4.*;

public class BST<Key extends Comparable<Key>, Value>
{
	 private Node root; // root of BST
	 
	 private class Node
	 {
		 private Key key; // key
		 private Value val; // associated value
		 private Node left, right; // links to subtrees
		 private int N; // # nodes in subtree rooted here
		 
		 public Node(Key key, Value val, int N)
		 { this.key = key; this.val = val; this.N = N; }
	 }
	 
	 public BST() {
	 }
	 
	 public int size()
	 { return size(root); }
	 
	 private int size(Node x)
	 {
		 if (x == null) return 0;
		 else return x.N;
	 }
	 
	 public boolean contains(Key key)
	 {
		 return get(key) != null;
	 }
	 
	 public Iterable<Key> keys()
	 {
		 return keys(min(), max());
	 }
	 
	 public Iterable<Key> keys(Key lo, Key hi)
	 {
	  Queue<Key> queue = new Queue<Key>();
	  keys(root, queue, lo, hi);
	  return queue;
	 }
	 
	 public Key min()
	 {
	  return min(root).key;
	 }
	 
	 private Node min(Node x)
	 {
	  if (x.left == null) return x;
	  return min(x.left);
	 }
	 
	 public Key max()
	 {
		 return max(root).key;
	 }
	 
	 private Node max(Node x)
	 {
		 if (x.right == null) return x;
		 else	return max(x.right);
	 }
	 
	 private void keys(Node x, Queue<Key> queue, Key lo, Key hi)
	 {
	  if (x == null) return;
	  int cmplo = lo.compareTo(x.key);
	  int cmphi = hi.compareTo(x.key);
	  if (cmplo < 0) keys(x.left, queue, lo, hi);
	  if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
	  if (cmphi > 0) keys(x.right, queue, lo, hi);
	 }
	 
	 public Value get(Key key)
	 { return get(root, key); }
	 
	 private Value get(Node x, Key key)
	 { 	// Return value associated with key in the subtree rooted at x;
		// return null if key not present in subtree rooted at x.
		  if (x == null) return null;
		  int cmp = key.compareTo(x.key);
		  if (cmp < 0) return get(x.left, key);
		  else if (cmp > 0) return get(x.right, key);
		  else return x.val;
	 }
	 
	 public void put(Key key, Value val)
	 { // Search for key. Update value if found; grow table if new.
	  root = put(root, key, val);
	 }
	 private Node put(Node x, Key key, Value val)
	 {
		  // Change key’s value to val if key in subtree rooted at x.
		  // Otherwise, add new node to subtree associating key with val.
		  if (x == null) return new Node(key, val, 1);
		  
		  int cmp = key.compareTo(x.key);
		  if (cmp < 0) x.left = put(x.left, key, val);
		  else if (cmp > 0) x.right = put(x.right, key, val);
		  else x.val = val;
		  	x.N = size(x.left) + size(x.right) + 1;
		  return x;
	 }
	 
	 
	 public static void main(String[] args) throws FileNotFoundException
	 {
		 long startTimeInput = System.currentTimeMillis();
		 int minlen = 0;
		 BST<String, Integer> st = new BST<String, Integer>();
		 System.setIn(new FileInputStream("taleOfCities2.txt"));
		 int i = 0;
		 while (i < 100)
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
		 long endTimeMax = Instant.now().toEpochMilli();
		 long timeMax = endTimeMax - startTimeMax;
		 long timeInput = endTimeInput - startTimeInput;
		 System.out.println("Elapsed time finding max in milliseconds: " + timeMax);
		 System.out.println("Elapsed time inputting in milliseconds: " + timeInput);
		 
		 /*
		  * of 6
		  * Elapsed time finding max in milliseconds: 3
		  * Elapsed time inputting in milliseconds: 38
		  * 
		  */
	 }
	 
}