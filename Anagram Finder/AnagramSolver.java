import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

/* CS 314 STUDENTS: FILL IN THIS HEADER AND THEN COPY AND PASTE IT TO YOUR
 * LetterInventory.java AND AnagramSolver.java CLASSES.
 *
 * Student information for assignment:
 *
 *  On my honor, Shishir Madari, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: sm54643
 *  email address: shishir.madari@utexas.edu
 *  Grader name:
 *  Number of slip days I am using:
 */

public class AnagramSolver {

	// instance variables
	HashMap<String, LetterInventory> mapOfWords;

	// takes in a dictionary as a list
	// creates a LetterInventory for each word in list
	// stores LetterInventory in a hashMap with word as key
	public AnagramSolver(List<String> list) {
		if (list == null) 
			throw new IllegalArgumentException("Failed precondition: equals method");

		mapOfWords = new HashMap();
		for (String word : list) {
			if (!mapOfWords.containsKey(word)) {
				LetterInventory inventoryForWord = new LetterInventory(word);
				mapOfWords.put(word, inventoryForWord);
			}
		}
	}

	// Comparator interface for the Anagram Class
	private static class AnagramComparator implements Comparator<List<String>> {
		// sorts two lists by length
        public int compare(List<String> a1, List<String> a2) {
        	Collections.sort(a1);
        	Collections.sort(a2);
        	int difference = a1.size() - a2.size();
        	if (difference > 0)
        		return 1;
        	else if (difference < 0)
        		return -1;
        	// length is equal, sort list based on contents of list
        	else if (difference == 0) {
            	for (int i = 0; i < a1.size(); i++) {
            		int compare = a1.get(i).compareTo(a2.get(i));
            		if (compare != 0)
       					// contents of lists differ
            			return compare;
            	}
            }
            // length is equal and content of two lists are equal
            // returns 0
            return difference;
        }
    }

	public List<List<String>> getAnagrams(String target, int maxNumWords) {
		if (target == null || !containsOneLetter(target))
			throw new IllegalArgumentException("Failed precondition: LetterInventory"); 

		// creates LetterInventory of the target string
		LetterInventory targetLetters = new LetterInventory(target);
		// creates an empty List that will contain all final list of anagrams
		List<List<String>> result = new ArrayList<List<String>>();
		// creates List that will store partially finished lists of anagrams
		List<String> anagramPieces = new ArrayList<String>();
		// creates a List that stores all words that could be anagrams of target string
		List<String> possibleAnagrams = determineAnagrams(targetLetters);
		// calls recursive method
		result = recursiveAnagrams(result, possibleAnagrams, maxNumWords,
			anagramPieces, targetLetters, 0);

		// sorts resulting list
		Collections.sort(result, new AnagramComparator());
		// checks resulting list, removes possible duplicate lists
		result = removeAnagrams(result, maxNumWords);
		return result;
	}

	public List<List<String>> recursiveAnagrams(List<List<String>> result, List<String> possibleAnagrams,
			int maxNumWords, List<String> anagramPieces, LetterInventory currentLetters, int index) {

		// base case, combination of words equal
		// same number of letters as target string
		// add list of words to final list of anagrams
		if (currentLetters.isEmpty()) {
			Collections.sort(anagramPieces);
			result.add(anagramPieces);
			return result; 
		}
		// base case for when there is a limit on how many words can be an anagram
		// means that there are left over letters from target string
		// cannot be a possible anagram given restrictions. List is not added.
		else if (maxNumWords == anagramPieces.size() && maxNumWords > 0)
			return result; 
		else {
			// forloop iterates through list of words that could make up an anagram
			// create LetterInventory for possible word
			// subtract it from target string's LetterInventory
			for (int i = index; i < possibleAnagrams.size(); i++) {
				LetterInventory possibleInventory = new LetterInventory(possibleAnagrams.get(i));
				LetterInventory leftOverInventory = currentLetters.subtract(possibleInventory);
				if (leftOverInventory != null) {
					// letters remain from target string's LetterInventory
					// creates a copy of list of partially completed List of anagram
					// adds word to list
					// recursive method called with the remaining LetterInventory
					ArrayList<String> anagramExtension = new ArrayList<String>(anagramPieces); 
					anagramExtension.add(possibleAnagrams.get(i));
					recursiveAnagrams(result, possibleAnagrams, maxNumWords,
						anagramExtension, leftOverInventory, i);
				}
			}
		}
		return result;
	}		

	// method to remove possible duplicates in List of List of Anagrams
	public List<List<String>> removeAnagrams(List<List<String>> anagrams, int maxNumWords) {
		// creates new empty resulting list of list of strings
		List<List<String>> removedDuplicates = new ArrayList<List<String>>();
		// if a duplicate list does not exists in the new resulting list,
		// add list to the new resulting list. 
		for (List<String> list : anagrams) 
			if (!removedDuplicates.contains(list))
				removedDuplicates.add(list);
		return removedDuplicates;
	}

	// method checks every word in the map, returns a list of words
	// that could be made from the target string
	public List<String> determineAnagrams(LetterInventory targetLetters) {
		List<String> listOfPossibleAnagrams = new ArrayList<String>();
		// get LetterInventory for each word in map
		// subtract LetterInventory from the LetterInventory of target string
		for (Map.Entry<String, LetterInventory> entry : mapOfWords.entrySet()) {
			String keyOfEntry = entry.getKey();
			LetterInventory potential = mapOfWords.get(keyOfEntry);
			LetterInventory poss = targetLetters.subtract(potential);
			if (poss != null) 
				// there are letters remaining, word is added to list of possible anagrams
				listOfPossibleAnagrams.add(keyOfEntry);
		}
		return listOfPossibleAnagrams;
	}

	// method to check if string contains at least one
	// letter in the english alphabet
	public boolean containsOneLetter(String target) {
		target = target.toLowerCase();
		for (int i = 0; i < target.length(); i++) {
			char ch = target.charAt(i);
			if ('a' <= ch && ch <= 'z') {
				return true;
			}
		}
		return false;
	}	
}