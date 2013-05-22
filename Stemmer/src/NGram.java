import java.util.ArrayList;
import java.util.HashMap;

import struct.BiMap;

public class NGram
{
	private BiMap<Integer, String> wordMap = new BiMap<>();

	public enum SimilarityType
	{
		DICE, JACCARD, SIMPLE, COSINE, OVERLAP
	}

	public BiMap<Integer, String> getWordMap()
	{
		return wordMap;
	}

	/**
	 * 
	 * @param words
	 *            hashMap with words nGrams
	 * @return similarity matrix of words
	 */
	public double[][] getSiminaliryMatrix(HashMap<String, ArrayList<String>> words,
			NGram.SimilarityType similarityType)
	{
		// create similarity measure matrix
		double[][] matrix = new double[words.size()][words.size()];

		// create hash map associated with word=index in matrix
		wordMap = new BiMap<>();
		int index = 0;
		for (String word : words.keySet())
			wordMap.add(index++, word);

		// fill similarity matrix
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j <= i; j++)
				// same word
				if (i == j)
					matrix[i][j] = 1;
				else if (j < i)// only half matrix
					matrix[i][j] = getSimilarity(words.get(wordMap.get(i)),
							words.get(wordMap.get(j)), similarityType);

		return matrix;
	}

	private static double getSimilarity(ArrayList<String> word1, ArrayList<String> word2,
			NGram.SimilarityType similarityType)
	{
		switch (similarityType)
		{
		case DICE:
			return getSimilarityDice(word1, word2);

		case JACCARD:
			return getSimilarityJaccard(word1, word2);

		case SIMPLE:
			return getSimilaritySimple(word1, word2);

		case COSINE:
			return getSimilarityCosine(word1, word2);

		case OVERLAP:
			return getSimilarityOverlap(word1, word2);

		default:
			return getSimilarityDice(word1, word2);
		}
	}

	// Dice's coefficient
	private static double getSimilarityDice(ArrayList<String> word1, ArrayList<String> word2)
	{
		// count similarNGrams
		int similarNGrams = 0;
		for (String nGram : word1)
			if (word2.contains(nGram))
				similarNGrams++;

		double distance = 2d * similarNGrams / (word1.size() + word2.size());

		return distance;
	}

	// Simple matching coefficient
	private static double getSimilaritySimple(ArrayList<String> word1, ArrayList<String> word2)
	{
		// count similarNGrams
		int similarNGrams = 0;
		for (String nGram : word1)
			if (word2.contains(nGram))
				similarNGrams++;

		double distance = similarNGrams;

		return distance;
	}

	// Jaccard's coefficient
	private static double getSimilarityJaccard(ArrayList<String> word1, ArrayList<String> word2)
	{
		// count similarNGrams
		int similarNGrams = 0;
		for (String nGram : word1)
			if (word2.contains(nGram))
				similarNGrams++;

		double distance = (double)similarNGrams / (double)(word1.size() + word2.size() - similarNGrams);

		return distance;
	}

	// Cosine coefficient
	//TODO neshto ne e vo red, mozhno e da ne e taka formulata
	private static double getSimilarityCosine(ArrayList<String> word1, ArrayList<String> word2)
	{
		// count similarNGrams
		int similarNGrams = 0;
		for (String nGram : word1)
			if (word2.contains(nGram))
				similarNGrams++;

		double distance = similarNGrams / (0.5d * word1.size() * 0.5d * word2.size());

		return distance;
	}

	// Overlap coefficient
	private static double getSimilarityOverlap(ArrayList<String> word1, ArrayList<String> word2)
	{
		// count similarNGrams
		int similarNGrams = 0;
		for (String nGram : word1)
			if (word2.contains(nGram))
				similarNGrams++;

		double distance = (double)similarNGrams / (double)Math.min(word1.size(), word2.size());

		return distance;
	}

}
