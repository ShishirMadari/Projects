import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

public class AnagramFinderTester {

    public static final int NUM_LETTERS_ALPHABET = 26;
    private static final String testCaseFileName = "testCaseAnagrams.txt";
    private static final String dictionaryFileName = "d3.txt";

    public static void main(String[] args) {
        LetterInventory lets1 = new LetterInventory("abcdefghijklmnopqrstuvwxyz");
        LetterInventory lets2 = new LetterInventory("abcdefghijklmnopqrstuvwxyz");
        Object expected = true;
        Object actual = lets1.equals(lets2);
        showTestResults(expected, actual, 1, " LetterInventory and equals method");

        lets1 = new LetterInventory("1a34b243cd34e5f3gh53ij6kl5m6no6pqr32st6u4v6wx7y3z");
        expected = 26;
        actual = lets1.size();
        showTestResults(expected, actual, 2, " LetterInventory and size method");

        int[] freq = new int[NUM_LETTERS_ALPHABET];
        for (int i = 0; i < NUM_LETTERS_ALPHABET; i++)
            freq[i]++;
        lets1 = new LetterInventory(freq);
        expected = "abcdefghijklmnopqrstuvwxyz";
        actual = lets1.toString();
        showTestResults(expected, actual, 3, " LetterInventory and toString method");

        freq = new int[NUM_LETTERS_ALPHABET];
        lets1 = new LetterInventory(freq);
        expected = "";
        actual = lets1.toString();
        showTestResults(expected, actual, 4, " LetterInventory and toString method");

        lets1 = new LetterInventory("aaa");
        expected = 3;
        actual = lets1.size();
        showTestResults(expected, actual, 5, " size of LetterInventory");

        lets1 = new LetterInventory("zzzz");
        expected = 4;
        actual  = lets1.size();
        showTestResults(expected, actual, 6, " size of LetterInventory");

        expected = 0;
        actual = lets1.get('y');
        showTestResults(expected, actual, 7, " get method");

        expected = 4;
        actual = lets1.get('z');
        showTestResults(expected, actual, 8, " get method");

        lets1 = new LetterInventory("");
        expected = 0;
        actual = lets1.size();
        showTestResults(expected, actual, 9, " size method");

        lets1 = new LetterInventory("abcdefghijklmnopqrstuvwxyz");
        expected = 26;
        actual = lets1.size();
        showTestResults(expected, actual, 10, " size method");

        lets1 = new LetterInventory("");
        expected = true;
        actual = lets1.isEmpty();
        showTestResults(expected, actual, 11, " empty method");

        lets1 = new LetterInventory("@!#$!@%%!@(#%)!)$!@#a$2341243095231$@");
        expected = false;
        actual = lets1.isEmpty();
        showTestResults(expected, actual, 12, " empty method");

        expected = "a";
        actual = lets1.toString();
        showTestResults(expected, actual, 13, " toString method");

        lets1 = new LetterInventory("");
        expected = "";
        actual = lets1.toString();
        showTestResults(expected, actual, 14, " toString method");

        lets2 = new LetterInventory("a");
        LetterInventory lets3 = lets2.add(lets1);
        expected = "a";
        actual = lets3.toString();
        showTestResults(expected, actual, 15, " add method and toString method");

        lets3 = lets2.add(new LetterInventory("a"));
        expected = 2;
        actual = lets3.size();
        showTestResults(expected, actual, 16, " add method and size method");

        lets3 = lets2.subtract(lets2);
        expected = true;
        actual = lets3.isEmpty();
        showTestResults(expected, actual, 17, " subtract method and isEmpty method");

        lets3 = lets3.subtract(new LetterInventory("z"));
        expected = null;
        actual = lets3;
        showTestResults(expected, actual, 18, " subtract method");

        lets1 = new LetterInventory("a!@#%!()#$)(@#)$(!#)$(!@)$(!@)#(");
        lets2 = new LetterInventory("!@#$*!@($)!@$()!@#$#*%(@!##@)(!)#@A");
        expected = true;
        actual = lets1.equals(lets2);
        showTestResults(expected, actual, 19, " equals method");

        lets1 = new LetterInventory("");
        lets2 = new LetterInventory("");
        expected = true;
        actual = lets1.equals(lets2);
        showTestResults(expected, actual, 20, " equals method");

        lets3 = lets1.add(lets2);
        LetterInventory lets4 = lets2.add(lets1);
        showTestResults(lets3, lets4, 21, "LetterInventory add and equals");
        
        // tests on the anagram solver itself
        boolean displayAnagrams = getChoiceToDisplayAnagrams();
        AnagramSolver solver = new AnagramSolver(AnagramMain.readWords(dictionaryFileName));
        runAnagramTests(solver, displayAnagrams);
    }

    private static boolean getChoiceToDisplayAnagrams() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter y or Y to display anagrams during tests: ");
        String response = console.nextLine();
        return response.length() > 0 && response.toLowerCase().charAt(0) == 'y';
    }

    public static boolean showTestResults(Object expected, Object actual, int testNum, String featureTested) {
        System.out.println("Test Number " + testNum + " testing " + featureTested);
        System.out.println("Expected result: " + expected);
        System.out.println("Actual result: " + actual);
        boolean passed = (actual == null && expected == null) || actual.equals(expected);
        if(passed)
            System.out.println("Passed test " + testNum);
        else
            System.out.println("!!! FAILED TEST !!! " + testNum);
        System.out.println();
        return passed;
    }

    /**
     * Method to run tests on Anagram solver itself.
     * pre: the files d5.txt and testCaseAnagrams.txt are in the local directory
     * 
     * assumed format for file is
     * <NUM_TESTS>
     * <TEST_NUM>
     * <MAX_WORDS>
     * <PHRASE>
     * <NUMBER OF ANAGRAMS>
     * <ANAGRAMS>
     */
    private static void runAnagramTests(AnagramSolver solver, boolean displayAnagrams) {
        int solverTestCases = 0;
        int solverTestCasesPassed = 0;
        Stopwatch st = new Stopwatch();
        try {
            Scanner sc = new Scanner(new File(testCaseFileName));
            final int NUM_TEST_CASES = Integer.parseInt(sc.nextLine().trim());
            System.out.println(NUM_TEST_CASES);
            for(int i = 0; i < NUM_TEST_CASES; i++) {
                // expected results
                TestCase currentTest = new TestCase(sc);
                solverTestCases++;
                st.start();
                // actual results
                List<List<String>> actualAnagrams = solver.getAnagrams(currentTest.phrase, currentTest.maxWords);
                st.stop();
                if(displayAnagrams) {
                    displayAnagrams("actual anagrams", actualAnagrams);
                    displayAnagrams("expected anagrams", currentTest.anagrams);
                }


                if(checkPassOrFailTest(currentTest, actualAnagrams))
                    solverTestCasesPassed++;
                System.out.println("Time to find anagrams: " + st.time());
            }
        }
        catch(Exception e) {
            System.out.println("\nProblem while running test cases on AnagramSolver. Check" +
                    " that file testCaseAnagrams.txt is in the correct location.");
            System.out.println(e);
            System.out.println("AnagramSolver test cases run: " + solverTestCases);
            System.out.println("AnagramSolver test cases failed: " + (solverTestCases - solverTestCasesPassed));
        }
        System.out.println("\nAnagramSolver test cases run: " + solverTestCases);
        System.out.println("AnagramSolver test cases failed: " + (solverTestCases - solverTestCasesPassed));
    }


    // print out all of the anagrams in a list of anagram
    private static void displayAnagrams(String type,
            List<List<String>> anagrams) {

        System.out.println("Results for " + type);
        System.out.println("num anagrams: " + anagrams.size());
        System.out.println("anagrams: ");
        for(List<String> singleAnagram : anagrams)
            System.out.println(singleAnagram);
    }


    // determine if the test passed or failed
    private static boolean checkPassOrFailTest(TestCase currentTest,
            List<List<String>> actualAnagrams) {
        System.out.println();
        if(actualAnagrams.equals(currentTest.anagrams)) {
            System.out.println("Passed test " + currentTest.testCaseNumber);
            return true;
        }
        else{
            System.out.println("\nFailed test case " + currentTest.testCaseNumber);
            System.out.println("Phrase: " + currentTest.phrase + ", max words: " + currentTest.maxWords + ". Recall 0 means no limit.");
            System.out.println("Expected number of anagrams: " + currentTest.anagrams.size());
            System.out.println("Actual number of anagrams: " + actualAnagrams.size());
            if(currentTest.anagrams.size() == actualAnagrams.size()) {
                System.out.println("Sizes the same, but either a difference in anagrams or anagrams not in correct order.");
            }
            System.out.println();
            return false;
        }  
    }

    // class to handle the parameters for an anagram test 
    // and the expected reuslt
    private static class TestCase {

        private int testCaseNumber;
        private String phrase;
        private int maxWords;
        private List<List<String>> anagrams;

        // pre: sc is positioned at the start of a test case
        private TestCase(Scanner sc) {
            testCaseNumber = Integer.parseInt(sc.nextLine().trim());
            maxWords = Integer.parseInt(sc.nextLine().trim());
            phrase = sc.nextLine().trim();
            anagrams = new ArrayList<List<String>>();
            readAndStoreAnagrams(sc);
        }

        // pre: sc is positioned at the start of the resulting angrams
        // read in the number of angrams and then for each anagram:
        //  - read in the line
        //  - break the line up into words
        //  - create a new list of Strings for the anagram
        //  - add each word to the anagram
        //  - add the anagram to the list of anagrams
        private void readAndStoreAnagrams(Scanner sc) {
            int numAnagrams = Integer.parseInt(sc.nextLine().trim());
            for(int j = 0; j < numAnagrams; j++){
                String[] words = sc.nextLine().split("\\s+");
                ArrayList<String> anagram = new ArrayList<String>();
                for(String st : words)
                    anagram.add(st);
                anagrams.add(anagram);
            }
            assert anagrams.size() == numAnagrams : "Wrong number of angrams read or expected";
        }
    }
}
