import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.Scanner;

import edu.princeton.cs.algs4.*;
import java.io.InputStream;

//import java.io.*;

public class BST2<Key extends Comparable<Key>, Value>
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
	 
	 public BST2() {
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
	 
	 public void deleteMin()
	 {
	  root = deleteMin(root);
	 }
	 
	 private Node deleteMin(Node x)
	 {
	  if (x.left == null) return x.right;
	  x.left = deleteMin(x.left);
	  x.N = size(x.left) + size(x.right) + 1;
	  return x;
	 }
	 
	 public void delete(Key key)
	 { root = delete(root, key); }
	 
	 private Node delete(Node x, Key key)
	 {
	  if (x == null) return null;
	  int cmp = key.compareTo(x.key);
	  if (cmp < 0) x.left = delete(x.left, key);
	  else if (cmp > 0) x.right = delete(x.right, key);
	  else
	  {
		  if (x.right == null) return x.left;
		  if (x.left == null) return x.right;
		  Node t = x;
		  x = min(t.right); // See page 407.
		  x.right = deleteMin(t.right);
		  x.left = t.left;
	  }
	  x.N = size(x.left) + size(x.right) + 1;
	  return x;
	 }
	 

	 public static int interval(BST2<String, Integer> st, int i, int upper, int lower)
	 {

		 String max = "";
		 st.put(max, 0);
		 for (String word : st.keys())
			 if (st.get(word) > st.get(max))
				 	max = word;
		 int value = st.get(max);
		 while(i <= upper)
		 {
			if(i >= lower && i <= upper)
			 {
				 System.out.println("the " + i + "th most common word is: " + max + " " + st.get(max) + "| ");
			 }
			st.delete(max);
			i++;
			i = interval(st, i, upper, lower);
			
		 }
		 st.put(max, value);
		 return i;
	 }
	 
	 
	 public static void main(String[] args) throws FileNotFoundException
	 {
		 long startTimeInput = System.currentTimeMillis();
		 int minlen = 0; //Integer.parseInt(args[0]); // key-length cutoff
		 BST2<String, Integer> st = new BST2<String, Integer>();
		 InputStream in = System.in;

		 
		 System.setIn(new FileInputStream("taleOfCities2.txt"));

		 while (!StdIn.isEmpty())
		 { // Build symbol table and count frequencies.
			 String word = StdIn.readString();
			 if (word.length() < minlen) continue; // Ignore short keys.
			 if (!st.contains(word)) st.put(word, 1);
			 else st.put(word, st.get(word) + 1);
		 }
		 long endTimeInput = System.currentTimeMillis();
		 

		 System.setIn(in);
		 StdOut.println("Add interval: ");
		 StdIn.readLine();
		 
		 // Input the interval
		 Scanner input = new Scanner(System.in);
		 int lower = input.nextInt();
		 int upper = input.nextInt();
		 
		 long startTimeInterval = System.currentTimeMillis();
		 interval(st, 1, upper, lower);
		 long endTimeInterval = System.currentTimeMillis();
		 
		 long startTimeMax = System.currentTimeMillis();
		// Find a key with the highest frequency count.
		 String max = "";
		 st.put(max, 0);
		 for (String word : st.keys())
			 if (st.get(word) > st.get(max))
				 	max = word;
		 StdOut.println(max + " " + st.get(max));

		 long endTimeMax = System.currentTimeMillis();
		 long timeMax = endTimeMax - startTimeMax;
		 long timeInput = endTimeInput - startTimeInput;
		 long interval = endTimeInterval - startTimeInterval;
		 System.out.println("Elapsed time in milliseconds getting max: " + timeMax);
		 System.out.println("Elapsed time in milliseconds input: " + timeInput);
		 System.out.println("Elapsed time in milliseconds interval: " + interval);
		 
		 
		 /*Add interval: 
		  * 4 8
		  * the 4th most common word is: to 3602|
		  * the 5th most common word is: a 2864| 
		  * the 6th most common word is: in 2541|
		  * the 7th most common word is: I 1989| 
		  * the 8th most common word is: his 1942|
		  * the 7577
		  * Elapsed time in milliseconds getting max: 4
		  * Elapsed time in milliseconds input: 274
		  * Elapsed time in milliseconds interval: 51
		  * 
		  * 
		  */
	 }
	 
}