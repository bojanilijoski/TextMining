import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * This class is used for reading all words from file and tokenize them into N-grams
 * 
 * @author Bojan
 * 
 */
public class Tokenizator
{
	// set of characters which should be removed from text
	private static final char[] unnecessaryCharacterSet =
	{ '.', ',', '"', '\'', '?', '!', '/', '\\', '*', '@', '$', '#', '%', '^', '^', '&', '(', ')',
			'{', '}', '[', ']', ';', ':', '<', '>', '_', '-', '+', '=', '|', '„', '“', '’', '‘' };

	/**
	 * 
	 * @param file
	 *            a file which should be splitted in words
	 * @param minimumLength
	 *            minimum length of word (all smaller words are ignored)
	 * @param NGramLength
	 * 
	 * @return HashMap<String, ArrayList<String>> hashMap where word is a key and value is a list of
	 *         n-grams of that word
	 */
	public static HashMap<String, ArrayList<String>> getWords(File file, int minimumLength,
			int nGramLength)
	{
		// HashMap with keyValue=word and ArrayList of N-grams of that word
		LinkedHashMap<String, ArrayList<String>> wordMap = new LinkedHashMap<>();

		try
		{
			FileInputStream fis = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			String line;
			line = br.readLine();

			int wordsCount = 0;

			while (line != null)
			{
				String[] words = cleanUpLine(line.toLowerCase()).split(" ");
				wordsCount += words.length;

				for (String word : words)
				{
					word = word.trim();
					if (word.length() >= minimumLength)
						if (!wordMap.containsKey(word))
							wordMap.put(word, nGramWord(word, nGramLength));
				}

				line = br.readLine();
			}

			System.out.println("no of words: " + wordsCount);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return wordMap;
	}

	/**
	 * remove unnecessary characters from string
	 * 
	 * @param line
	 * @return same string with removed characters
	 */
	private static String cleanUpLine(String line)
	{
		for (char c : unnecessaryCharacterSet)
		{
			line = line.replace(c, ' ');
		}

		line = line.replaceAll("\\s+", " ");// replace double space

		return line;
	}

	/**
	 * 
	 * @param word
	 * @param nGramLength
	 * @return list of nGrams with length nGramLength from word word
	 */
	private static ArrayList<String> nGramWord(String word, int nGramLength)
	{
		ArrayList<String> nGramList = new ArrayList<>();

		// get leading nGrams
		for (int i = nGramLength - 1; i > 0; i--)
		{
			StringBuilder nGram = new StringBuilder();
			// add leading blanks
			for (int j = 0; j < i; j++)
				nGram.append(" ");

			// add characters
			for (int j = 0; j < nGramLength - i; j++)
				// if nGramLength is bigger than word.length than add ending blanks
				if (j < word.length())
					nGram.append(word.charAt(j));
				else
					nGram.append(" ");

			nGramList.add(nGram.toString());
		}

		// get middle nGrams
		for (int i = 0; i < word.length() - nGramLength + 1; i++)
		{
			nGramList.add((new StringBuilder(word.subSequence(i, i + nGramLength))).toString());
		}

		// get ending nGrams
		// condition because of duplicate
		// leading and ending nGram produce same nGram
		for (int i = nGramLength < word.length() ? nGramLength - 1
				: nGramLength % 2 == 0 ? nGramLength - (nGramLength - word.length()) : nGramLength
						- (nGramLength - word.length()) - 1; i > 0; i--)
		{
			StringBuilder nGram = new StringBuilder();
			// add characters
			for (int j = i; j > 0; j--)
			{
				if (j <= word.length())
					nGram.append(word.charAt(word.length() - j));
				else
					nGram.append(" ");
			}

			// add ending blanks
			for (int j = 0; j < nGramLength - i; j++)
				nGram.append(" ");

			nGramList.add(nGram.toString());
		}

		return nGramList;
	}
}
