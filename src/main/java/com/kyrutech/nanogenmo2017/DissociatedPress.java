package com.kyrutech.nanogenmo2017;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class DissociatedPress {

    private String words[];
    private String fullText;


    public DissociatedPress() {

    }

    /**
     * Pull the full text of the file into the fullText string;
     *
     * @param file The file object containing text
     * @throws IOException If there is a problem with the file
     */
    public void addTextFull(File file) throws IOException {
        FileReader fr = new FileReader(file);
        StringBuilder text = new StringBuilder();
        char lastLetter = 'z';
        while (fr.ready()) {
            char letter = (char) fr.read();
            if (letter == '\n' || letter == '\r') {
                text.append(" ");
            } else if (letter == ' ' && letter == lastLetter) {
                //Do nothing
            } else {
                text.append(letter);
            }
            lastLetter = letter;
        }
        fullText = text.toString();
    }

    /**
     * Dissociate letter to letter
     *
     * @param match Number of letters to match on
     * @param totallength Total length of text to generate
     * @return The text that was generated
     */
    public String dissociateLetter(int match, int totallength) {
        StringBuilder sb = new StringBuilder();

        int startingIndex = (int) (Math.random() * fullText.length());

        String lastLetters = fullText.substring(startingIndex, startingIndex + match);

        sb.append(lastLetters);

        while (sb.length() < totallength) {
            String nextLetters = findNextLetters(lastLetters);

            if (nextLetters.length() == 0) {
                break;
            }

            int letterIndex = (int) (Math.random() * nextLetters.length());


            sb.append(nextLetters.charAt(letterIndex));

            lastLetters = sb.substring(sb.length() - match);

        }


        return sb.toString();
    }

    /**
     * Search the fullText for occurrences of lastLetters and record the next letter
     *
     * @param lastLetters The letters to look for matches of
     * @return The new set of letters
     */
    private String findNextLetters(String lastLetters) {
        StringBuilder foundLetters = new StringBuilder();

        int lastLettersIndex = 0;
        for (int i = 0; i < fullText.length(); i++) {
            //If the index is the length of lastLetters we have found an appropriate next letter
            if (lastLettersIndex == lastLetters.length()) {
                foundLetters.append(fullText.charAt(i));
                lastLettersIndex = 0;

            }
            //If the character at the current loop index matches the letter at the last letter index
            //  we will check the next letter on the next loop
            if (fullText.charAt(i) == lastLetters.charAt(lastLettersIndex)) {
                lastLettersIndex++;
            } else {
                lastLettersIndex = 0;
            }
        }

//		System.out.println(foundLetters.toString());
        return foundLetters.toString();
    }

    /**
     * Add to fullText for letter dissociate
     *
     * @param text Text to add to fullText
     */
    public void addToFullText(String text) {
        StringBuilder sb = new StringBuilder();
        char lastLetter = 0x00;
        char[] textArray = text.toCharArray();
        for (char letter : textArray) {
            if (letter == '\n' || letter == '\r') {
                sb.append(" ");
            } else if (letter == ' ' && letter == lastLetter) {
                //Do nothing
            } else {
                sb.append(letter);
            }
            lastLetter = letter;
        }
        if (fullText == null) {
            fullText = text;
        } else {
            fullText = fullText + text;
        }
    }

    /**
     * Adds the passed text to the words array
     *
     * @param text Text to add to words array
     */
    public void addToWords(String text) {
        if (words == null) {
            words = text.split(" ");
        } else {
            String[] newWords = text.split(" ");
            List<String> allWords = new ArrayList<String>();
            Collections.addAll(allWords, words);
            for (String word : newWords) {
                if ("".equals(word)) {
                    continue;
                }
                allWords.add(word);
            }
            words = new String[allWords.size()];
            for (int i = 0; i < allWords.size(); i++) {
                words[i] = allWords.get(i);
            }
        }
    }

    /**
     * Take in a text file and split it up based on spaces
     *
     * @param file File with text to add to the words array
     * @throws IOException There is an issue accessing the file
     */
    public void addTextWords(File file, boolean punctuation) throws IOException {

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            sb.append(br.readLine());
            sb.append(" ");
        }
        words = sb.toString().split(" ");
        Vector<String> checkWords = new Vector<String>();

        //Clean up word list and reapply it to the array
        for (String word : words) {
            if ("".equals(word)) {
                continue;
            }
            if (!punctuation) {
                StringBuilder newWord = new StringBuilder();
                for (char ch : word.toCharArray()) {
                    if (Character.isLetterOrDigit(ch)) {
                        newWord.append(ch);
                    }
                }
                word = newWord.toString();
            }
            checkWords.add(word);
        }
        words = new String[checkWords.size()];
        for (int i = 0; i < checkWords.size(); i++) {
            words[i] = checkWords.elementAt(i);
        }

    }

    /**
     * Create a new line of text with match number of words matched and of a length of totalLength
     *
     * @param match Number of words to match on
     * @param totalLength Total number of words to generate
     * @return Text generated
     */
    public String dissociateWords(int match, int totalLength) {
        StringBuffer sb = new StringBuffer();

        //Find a random starting position in the words array
        int startingPosition = (int) (Math.random() * (words.length - match));

        String currentWords[] = new String[match];

        for (int i = 0; i < match; i++) {
            sb.append(words[startingPosition + i]);
            sb.append(" ");
            currentWords[i] = words[startingPosition + i];
        }

        while (countWords(sb) <= totalLength) {
            String nextWords[] = findNextWords(currentWords);
            String nextWord;

            //If we have more then 2 matches select one randomly otherwise find a random word and continue
            //  (This avoids just repeating what already exists)
            if (nextWords.length > 1) {
                int nextWordIndex = (int) (Math.random() * nextWords.length);
                nextWord = nextWords[nextWordIndex];
                sb.append(nextWord);
                sb.append(" ");

                //Adjust currentWords array to add the new word
                System.arraycopy(currentWords, 1, currentWords, 0, currentWords.length - 1);
                currentWords[currentWords.length - 1] = nextWord;

            } else {
                int randomWordIndex = (int) (Math.random() * (words.length - match));

                for (int i = 0; i < match; i++) {
                    sb.append(words[randomWordIndex + i]);
                    sb.append(" ");
                    currentWords[i] = words[randomWordIndex + i];
                }

//				nextWord = words[randomWordIndex];
//				sb.append(nextWord);
//				sb.append(" ");
            }


//			System.out.println(sb.toString());
        }

        return sb.toString();
    }

    /**
     * Takes the array of words and finds the list of possible next words
     *
     * @param currentWords current words we are checking
     * @return possible new words after currentWords
     */
    private String[] findNextWords(String[] currentWords) {
        int currentWordsIndex = 0;
        Vector<String> possibleWords = new Vector<String>();

        for (String word : words) {
            //If the index equals the length we've found a pattern of the current words
            //  and can assume this is right after them
            if (currentWordsIndex == currentWords.length) {
                possibleWords.add(word);
                currentWordsIndex = 0;
            }
            //If the word we are looking at matches the curren word at the index we increase
            //  the index and continue forward to check the next word
            if (word.equals(currentWords[currentWordsIndex])) {
                currentWordsIndex++;
            } else {
                currentWordsIndex = 0;
            }

        }
        String returnWords[] = new String[possibleWords.size()];
        for (int i = 0; i < possibleWords.size(); i++) {
            returnWords[i] = possibleWords.elementAt(i);
        }

        return returnWords;
    }

    private int countWords(StringBuffer sb) {
        return sb.toString().split(" ").length;
    }

    /**
     * Debug method to view the contents of the words array
     */
    public void printWords() {
        for (String word : words) {
            System.out.println(word);
        }
    }

    /**
     * Method to return the size of the words array
     *
     * @return length of the words array
     */
    public int wordsSize() {
        return words.length;
    }

}
