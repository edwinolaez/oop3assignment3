package appDomain;

import implementations.BSTree;
import implementations.Word;
import utilities.Iterator;

import java.io.*;
import java.util.*;

/**
 * WordTracker Application
 * Tracks words from text files and generates reports
 * 
 * Usage: java -jar WordTracker.jar <input.txt> -pf/-pl/-po [-f<output.txt>]
 */

public class WordTracker
{
	private static final String REPOSITORY_FILE = "repository.ser";
	private static BSTree<Word> wordTree;
	
	public static void main (String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java -jar WordTracker.jar <input.txt> -pf/-pl/-po [-f<output.txt>]");
			System.out.println(" <input.txt> : Text file to process");
			System.out.println(" -pf         : Print words with files");
			System.out.println(" -pl         : Print words with files and line numbers");
			System.out.println(" -po         : Print words with files, lines, and frequency");
			System.out.println(" -f<output>  : Optional - redirect output to file");
			return;
		}
		
		String inputFile = args[0];
		String reportType = args[1];
		String outputFile = null;
		
		if (args.length >= 3 && args[2].startsWith("-f")) {
			outputFile = args[2].substring(2); 
		}
		
		wordTree = loadRepository();
		
		System.out.println("Processing file: "+ inputFile);
		
		saveRepository();
		
		generateReport(reportType, outputFile);
		
		System.out.println("\nWordTracker completed successfully!");
	}
	
	/**
	 * Load existing word tree from repository.ser
	 * 
	 */
	private static BSTree<Word> loadRepository() {
		File repoFile = new File(REPOSITORY_FILE);
		
		if (!repoFile.exists()) {
			System.out.println("No exisiting repository found. Creating new word tree, ");
			return new BSTree<>(); 
		}
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(repoFile))) {
			System.out.println("Loading existing repository...");
			BSTree<Word> tree = (BSTree<Word>) ois.readObject();
			System.out.println("Repository loaded successfully. Words in tree: " + tree.size());
			return tree;
		} catch (Exception e) {
			System.err.println("Error loading repository: " + e.getMessage());
			System.out.println("Creating new word tree.");
			return new BSTree<>();
		}
	}
	/**
	 * Save word tree to repository.ser
	 * 
	 */
	private static void saveRepository() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REPOSITORY_FILE))) {
			oos.writeObject(wordTree);
			System.out.println("Repository saved successfully.");
		} catch (IOException e) {
			System.out.println("Error saving repository: " +e.getMessage());
		}
	}
	
	private static void processFile(String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			int lineNumber = 1;
			
			while ((line = reader.readLine()) !=null) {
				processLine(line, filename, lineNumber);
				lineNumber++;
			}
			
			System.out.println("File processed. Total unique word in tree: " + wordTree.size());
		} catch (FileNotFoundException e) {
			System.err.println("Error: File not found -" + filename);
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
	}
	private static void processLine(String line, String filename, int lineNumber) {
		String cleanLine = line.replaceAll("[^a-aZ-Z\\s]", " ");
		String[] words = cleanLine.trim().split("\\s+");
		
		for (String wordText : words) {
			if (wordText.isEmpty()) {
				continue;
			}
			
			wordText =  wordText.toLowerCase();
			
			Word searchWord = new Word(wordText);
			
			Word existingWord = null;
			Iterator<Word> iterator = wordTree.inorderIterator();
			
			while (iterator.hasNext()) {
				Word w = iterator.next();
				if (w.getText().equals(wordText)) {
					existingWord = w;
					break;
				}
			}
			
			if (existingWord != null) {
				existingWord.addOccurrence(filename, lineNumber);
			} else {
				Word newWord = new Word(wordText);
				newWord.addOccurrence(filename, lineNumber);
				wordTree.add(newWord);
			}
		}
	}
	private static void generateReport(String reportType, String outputFile) {
		PrintWriter writer = null;
		
		try {
			if (outputFile !=null) {
				writer = new PrintWriter(new FileWriter(outputFile));
				System.out.println("Report will be written to: " + outputFile);
			} else {
				writer = new PrintWriter(System.out);
			}
			
			switch (reportType) {
				case "-pf":
					generateFileReport(writer);
					break;
				case "-pl":
					generateLineReport(writer);
					break;
				case "-po":
					generateOccurrenceReport(writer);
					break;
				default:
					System.err.println("Inavlid report type:" + reportType);
					System.err.println("Use -pf, -pl, -po");
					return;
			}
			
			writer.flush();
			
			if (outputFile !=null) {
				System.out.println("Report generated successfully in " + outputFile);
			}
		} catch (IOException e) {
			System.err.println("Error generating report: " + e.getMessage());
		} finally {
			if (writer != null && outputFile != null) {
				writer.close();
			}
		}
	}
	
	private static void generateFileReport(PrintWriter writer) {
		writer.println("===WORD REPORT: Files===\n");
		
		Iterator<Word> iterator = wordTree.inorderIterator();
		
		while (iterator.hasNext()) {
			Word word = iterator.next();
			writer.println("Word: " + word.getText());
			
			Set<String> files = word.getFileOccurrences().keySet();
			for (String file: files) {
				writer.println(" - " + file);
			}
			writer.println();
		}
	}
	/**
	 * Report: Words with file name only (-pf)
	 */
	
	private static void generateLineReport(PrintWriter writer) {
		writer.println("===WORD REPORT: Files, Lines, Occurrences ===\n");
		
		Iterator<Word> iterator = wordTree.inorderIterator();
		
		while (iterator.hasNext()) {
			Word word = iterator.next();
			writer.println("Word: " + word.getText());
			writer.println(" Total Occurrences: " + word.getTotalFrequency());
			
			HashMap<String, ArrayList<Integer>> occurrences = word.getFileOccurrences();
			
			for (Map.Entry<String, ArrayList<Integer>> entry : occurrences.entrySet()) {
				String filename = entry.getKey();
				ArrayList<Integer> lines = entry.getValue();
				
				writer.print("  -" + filename + " (");
				writer.print(lines.size() + "occurrence");
				if (lines.size() != 1) writer.print("s");
				writer.print(", lines: ");
				
				for (int i = 0; i < lines.size(); i++) {
					writer.print(lines.get(i));
					if (i < lines.size() -1) {
						writer.print(", ");
					}
				}
				writer.println(")");
			}
			writer.println();
		}
	}
	
	/**
	 * Report: Words with files, lines and frequency (-po)
	 */
	
	private static void generateOccurrenceReport(PrintWriter writer) {
		writer.println("===WORD REPORT: Files, Lines, and Occurrences ===\n");
		
		Iterator<Word> iterator = wordTree.inorderIterator();
		
		while (iterator.hasNext()) {
			Word word = iterator.next();
			writer.println("Word: " +word.getText());
			writer.println("  Total Occurrences: " + word.getTotalFrequency());
			
			HashMap<String, ArrayList<Integer>> occurrences = word.getFileOccurrences();
			
			for (Map.Entry<String, ArrayList<Integer>> entry : occurrences.entrySet()) {
				String filename = entry.getKey();
				ArrayList<Integer> lines = entry.getValue();
				
				writer.print("  - " + filename + " (");
				writer.print(lines.size() + " occurrence ");
				if (lines.size() !=1) writer.print("s");
				writer.print(", lines: ");
				
				for (int i = 0; i < lines.size(); i++) {
					writer.print(lines.get(i));
					if (i <lines.size() -1) {
						writer.print(", ");
					}
				}
				writer.println(")");
			}
			writer.println();
		}
	}
}
