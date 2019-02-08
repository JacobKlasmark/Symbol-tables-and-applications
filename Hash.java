import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.*;

import edu.princeton.cs.algs4.StdIn;


public class Hash 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		Hashtable <String, Integer> wordlist = new Hashtable <String, Integer>();
		Hashtable <Integer, Integer> hashlist = new Hashtable <Integer, Integer>();
		 
		 System.setIn(new FileInputStream("taleOfCities2.txt"));
		
		 while (!StdIn.isEmpty())
		 {
			 String word = StdIn.readString();
			 if (!wordlist.containsKey(word)) wordlist.put(word, 1);
			 else wordlist.put(word, wordlist.get(word) + 1);

			 
		 }
		 
		 Enumeration wordEn = wordlist.keys();
		 while (wordEn.hasMoreElements())
		 {
			 int hashNr = wordEn.nextElement().hashCode();
			 if (!hashlist.containsKey(hashNr)) hashlist.put(hashNr, 1);
			 else hashlist.put(hashNr, hashlist.get(hashNr) + 1);
		 }
		 
		 Enumeration hashEn = hashlist.elements();
		 int twos = 0;
		 while(hashEn.hasMoreElements())
		 {
			 int value = (int) hashEn.nextElement();
			 if (value == 2) twos++;
		 }
		 
		 System.out.println("the " + wordlist.get("the"));
		 

		 int i = wordlist.size();
		 System.out.println("size: " + i);
		 boolean b = hashlist.contains(2);
		 System.out.println("Does any 2 words have the same hashcode? " + b);
		 System.out.println("Number of 2 words with the same hashcode: " + twos);
		 b = hashlist.contains(3);
		 System.out.println("Does any 3 words have the same hashcode? " + b);
		
		 /*the 7577
		  * size: 10975
		  * Does any 2 words have the same hashcode? true
		  * Number of 2 words with the same hashcode: 3
		  * Does any 3 words have the same hashcode? false
		  */
		 
	}
}
