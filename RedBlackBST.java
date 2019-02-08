import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;


import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class RedBlackBST<Key extends Comparable<Key>, Value>
{
	 private static final boolean RED = true;
	 private static final boolean BLACK = false;
	 private Node root;
	 
	 private class Node // BST node with color bit (see page 433)
	 {
		 Key key; // key
		 Value val; // associated data
		 Node left, right; // subtrees
		 int N; // # nodes in this subtree
		 boolean color; // color of link from
		 // parent to this node
		 Node(Key key, Value val, int N, boolean color)
		 {
		 this.key = key;
		 this.val = val;
		 this.N = N;
		 this.color = color;
		 }
		}
	 
	 private boolean isRed(Node h) // See page 433.
	 {
		 if (h == null) return false;
		 return h.color == RED;
	}
	 
	 private Node rotateLeft(Node h) // See page 434.
	 {
		 Node x = h.right;
		 h.right = x.left;
		 x.left = h;
		 x.color = h.color;
		 h.color = RED;
		 x.N = h.N;
		 h.N = 1 + size(h.left) + size(h.right);
		 return x;
	}
	 
	 private Node rotateRight(Node h) // See page 434.
	 {
		 Node x = h.left;
		 h.left = x.right;
		 x.right = h;
		 x.color = h.color;
		 h.color = RED;
		 x.N = h.N;
		 h.N = 1 + size(h.left)
		 + size(h.right);
		 return x;
	}
	 
	 private void flipColors(Node h) // See page 436.
	 {
		 h.color = RED;
		 h.left.color = BLACK;
		 h.right.color = BLACK;
	}
	 
	 public int size()
	 { return size(root); }
	 
	 private int size(Node x)
	 {
		 if (x == null) return 0;
		 else return x.N;
	 }
	 
	 public void put(Key key, Value val)
	 { // Search for key. Update value if found; grow table if new.
	 root = put(root, key, val);
	 root.color = BLACK;
	 }
	 
	 private Node put(Node h, Key key, Value val)
	 {
		 if (h == null) // Do standard insert, with red link to parent.
		 return new Node(key, val, 1, RED);
		 int cmp = key.compareTo(h.key);
		 if (cmp < 0) h.left = put(h.left, key, val);
		 else if (cmp > 0) h.right = put(h.right, key, val);
		 else h.val = val;
		 if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
		 if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
		 if (isRed(h.left) && isRed(h.right)) flipColors(h);
		 h.N = size(h.left) + size(h.right) + 1;
		 return h;
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
	 
	 private void keys(Node x, Queue<Key> queue, Key lo, Key hi)
	 {
	  if (x == null) return;
	  int cmplo = lo.compareTo(x.key);
	  int cmphi = hi.compareTo(x.key);
	  if (cmplo < 0) keys(x.left, queue, lo, hi);
	  if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
	  if (cmphi > 0) keys(x.right, queue, lo, hi);
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
	 
	 public boolean contains(Key key)
	 {
		 return get(key) != null;
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
	 

	 public static int interval(RedBlackBST<String, Integer> st, int i, int upper, int lower)
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
		 RedBlackBST<String, Integer> st = new RedBlackBST<String, Integer>();
		 
		 InputStream in = System.in;
		 
		 
		 System.setIn(new FileInputStream("taleOfCities2.txt"));
		 int minlen = 0;
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
		String max = "";
		st.put(max, 0);
		for (String word : st.keys())
			if (st.get(word) > st.get(max))
				max = word;
		long endTimeMax = System.currentTimeMillis();
		StdOut.println(max + " " + st.get(max));
		
		long maxTime = endTimeMax - startTimeMax;
		long intervalTime = endTimeInterval - startTimeInterval;
		long timeInput = endTimeInput - startTimeInput;
		
		System.out.println("Elapsed time in milliseconds getting max: " + maxTime);
		System.out.println("Elapsed time in milliseconds input: " + timeInput);
		System.out.println("Elapsed time in milliseconds getting interval: " + intervalTime);

		/*Add interval: 
		 * 4 8
		 *  the 4th most common word is: to 3602|
		 *  the 5th most common word is: a 2864| 
		 *  the 6th most common word is: in 2541|
		 *  the 7th most common word is: I 1989|
		 *  the 8th most common word is: his 1942|
		 *  the 7577
		 *  Elapsed time in milliseconds getting max: 4
		 *  Elapsed time in milliseconds input: 294
		 *  Elapsed time in milliseconds getting interval: 44
		 *  
		 */
		
	 }
}





