package implementations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a word with all its occurrences across multiple files 
 */

public class Word implements Comparable<Word>, Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String text;

	private HashMap<String, ArrayList<Integer>> fileOccurrences;
	
	/**
	 * Constructor - creating a new word entry
	 * @para	m text the word itself
	 */
	
	public Word(String text) {
		this.text = text.toLowerCase();
		this.fileOccurrences = new HashMap<>();
	}
	
	/**
	 * Add an occurrence to this word 
	 * @param filename which file the word was found in 
	 * @param lineNumber which line number 
	 */
	
	public void addOccurrence(String filename, int lineNumber) {
		if (!fileOccurrences.containsKey(filename)) {
			fileOccurrences.put(filename, new ArrayList<>());
		}
		
		ArrayList<Integer> lines = fileOccurrences.get(filename);
		if (!lines.contains(lineNumber)) {
			lines.add(lineNumber);
		}
	}
	
	public String getText() {
		return text;
	}
	
	public HashMap<String, ArrayList<Integer>> getFileOccurrences() {
		return fileOccurrences;
	}
	
	public int getTotalFrequency() {
		int total = 0;
		for (ArrayList<Integer> lines : fileOccurrences.values()) {
			total += lines.size();
		}
		return total;
	}
	
	@Override
	public int compareTo(Word other) {
		return this.text.compareTo(other.text);
	}
	
	
	@Override 
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Word other = (Word) obj;
		return text.equals(other.text);
	}
	
	@Override
	public String toString() {
		return text + " (appears in " + fileOccurrences.size() + "files)";
	}
}
