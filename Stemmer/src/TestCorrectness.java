import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import struct.BiMap;

public class TestCorrectness
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		testYass();
	}

	public static void testNGram()
	{
		NGram nGram = new NGram();
		HashMap<String, ArrayList<String>> wordMap = Tokenizator.getWordsTokens(new File(
				"TestTextFile2.txt"), 3, 0);
		double[][] similarityMatrix = nGram.getSiminaliryMatrix(wordMap, NGram.SimilarityType.DICE);
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		for (int i = 0; i < similarityMatrix.length; i++)
		{
			System.out.println();
			for (int j = 0; j < similarityMatrix.length; j++)
				System.out.print(Double.valueOf(twoDForm.format(similarityMatrix[i][j])) + " ");
		}
		System.out.println();
		BiMap<Integer, String> biMap = nGram.getWordMap();// reden broj <-> zbor
		// ArrayList<ArrayList<String>> arrayLists = SingleLink.getMappedSinleLinkClusters(biMap,
		// similarityMatrix, 0.3d);

		HashMap<String, String> wordStem = new HashMap<String, String>();// zbor <-> koren

		for (String word : biMap.getList())
		{
			String stem = Database.getFirstStem(word);
			wordStem.put(word, stem);
			System.out.print("(" + word + ": " + stem + ") ");
		}
		System.out.println();

		ArrayList<Double> similarityList = new ArrayList<Double>();
		ArrayList<Double> nonSimilarityList = new ArrayList<Double>();
		for (int i = 1; i < similarityMatrix.length; i++)
		{
			String stemI = wordStem.get(biMap.get(i));
			for (int j = 0; j < i; j++)
			{
				String stemJ = wordStem.get(biMap.get(j));
				if (stemJ.compareTo(stemI) == 0)
					similarityList.add(similarityMatrix[i][j]);
				else
					nonSimilarityList.add(similarityMatrix[i][j]);
			}
		}

		Collections.sort(similarityList);
		Collections.sort(nonSimilarityList);
		System.out.println(similarityList);
		System.out.println(nonSimilarityList);
	}

	public static void testYass()
	{
		Yass yass = new Yass();
		ArrayList<String> wordMap = Tokenizator.getWords(new File("TestTextFile2.txt"), 3);
		double[][] similarityMatrix = yass.getSiminaliryMatrix(wordMap);
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		for (int i = 0; i < similarityMatrix.length; i++)
		{
			System.out.println();
			for (int j = 0; j < similarityMatrix.length; j++)
				System.out.print(Double.valueOf(twoDForm.format(similarityMatrix[i][j])) + " ");
		}
		System.out.println();
		BiMap<Integer, String> biMap = yass.getWordMap();// reden broj <-> zbor
		// ArrayList<ArrayList<String>> arrayLists = SingleLink.getMappedSinleLinkClusters(biMap,
		// similarityMatrix, 0.3d);

		HashMap<String, String> wordStem = new HashMap<String, String>();// zbor <-> koren

		for (String word : biMap.getList())
		{
			String stem = Database.getFirstStem(word);
			wordStem.put(word, stem);
			System.out.print("(" + word + ": " + stem + ") ");
		}
		System.out.println();

		ArrayList<Double> similarityList = new ArrayList<Double>();
		ArrayList<Double> nonSimilarityList = new ArrayList<Double>();
		for (int i = 1; i < similarityMatrix.length; i++)
		{
			String stemI = wordStem.get(biMap.get(i));
			for (int j = 0; j < i; j++)
			{
				String stemJ = wordStem.get(biMap.get(j));
				if (stemJ.compareTo(stemI) == 0)
					similarityList.add(similarityMatrix[i][j]);
				else
					nonSimilarityList.add(similarityMatrix[i][j]);
			}
		}

		Collections.sort(similarityList);
		Collections.sort(nonSimilarityList);
		System.out.println(similarityList);
		System.out.println(nonSimilarityList);
	}
}
