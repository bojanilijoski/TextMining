import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import struct.BiMap;

public class TestCorrectness
{

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		String files[] =
		// { "TestTextFile2.txt" };
		{ "Text_HariPoter.txt", "Text_Sport.txt", "Text_twitter_DobroUtro.txt" };
		NGram.SimilarityType[] types =
		{ NGram.SimilarityType.COSINE, NGram.SimilarityType.DICE, NGram.SimilarityType.JACCARD, NGram.SimilarityType.NEW_SIMILARITY_LINEAR,
				NGram.SimilarityType.NEW_SIMILARITY_QUADRATIC, NGram.SimilarityType.OVERLAP, NGram.SimilarityType.SIMPLE };

		for (NGram.SimilarityType type : types)
			for (int i = 2; i < 6; i++)
				for (String file : files)
					testNGram(i, file, type);

	}

	public static void testNGram(int _nGram, String _fileName, NGram.SimilarityType _type) throws IOException
	{

		NGram nGram = new NGram();
		HashMap<String, ArrayList<String>> wordMap = Tokenizator.getWordsTokens(new File(_fileName), 3, _nGram, _nGram - 1, _nGram - 1);
		double[][] similarityMatrix = nGram.getSiminaliryMatrix(wordMap, _type);
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		// printanje na matricata na slichnost

		// for (int i = 0; i < similarityMatrix.length; i++)
		// {
		// System.out.println();
		// for (int j = 0; j < similarityMatrix.length; j++)
		// System.out.print(Double.valueOf(twoDForm.format(similarityMatrix[i][j])) + " ");
		// }
		//
		// System.out.println();
		BiMap<Integer, String> biMap = nGram.getWordMap();// reden broj <-> zbor
		// System.out.println("number of distinct words: " + biMap.size());
		// ArrayList<ArrayList<String>> arrayLists = SingleLink.getMappedSinleLinkClusters(biMap,
		// similarityMatrix, 0.3d);

		HashMap<String, ArrayList<String>> wordStem = new HashMap<String, ArrayList<String>>();// zbor
																								// <->
																								// lista
																								// na
																								// koreni

		for (String word : biMap.getList())
		{
			ArrayList<String> stems = Database.getStem(word);
			wordStem.put(word, stems);
			// System.out.print("(" + word + ": " + stem + ") ");
		}
		// System.out.println("expected values in matrix: " + (biMap.size() * (biMap.size() - 1)) /
		// 2);

		ArrayList<Double> similarityList = new ArrayList<Double>();
		ArrayList<Double> nonSimilarityList = new ArrayList<Double>();
		for (int i = 1; i < similarityMatrix.length; i++)
		{
			ArrayList<String> stemsI = wordStem.get(biMap.get(i));
			for (int j = 0; j < i; j++)
			{
				ArrayList<String> stemsJ = wordStem.get(biMap.get(j));

				boolean same = false;
				for (String stemI : stemsI)
				{
					for (String stemJ : stemsJ)
					{
						// ako nema koren vo bazata, nemozhe da se proveri i togash ne gi stavame ni
						// vo
						// isti
						// ni vo razlichni
						if (stemI == null || stemJ == null)
							continue;

						// if (similarityMatrix[i][j] > 1)
						// {
						// System.out.println(biMap.get(i) + " " + biMap.get(j));
						// System.out.println(stemI + " " + stemJ);
						// System.out.println(Database.getStem(biMap.get(i)));
						// System.out.println(Database.getStem(biMap.get(j)));
						// System.exit(0);
						// }

						if (stemJ.compareTo(stemI) == 0)
						{
							similarityList.add((double) Math.round(similarityMatrix[i][j] * 1000) / 1000);
							same = true;
						}

						if (same)
							break;
					}
					if (same)
						break;
				}
				if (!same)
					nonSimilarityList.add((double) Math.round(similarityMatrix[i][j] * 1000) / 1000);
			}
		}

		// System.out.println("velues in lists: " + (similarityList.size() +
		// nonSimilarityList.size()));

		Collections.sort(similarityList);
		Collections.sort(nonSimilarityList);
		// System.out.println("similarity list size: " + similarityList.size());
		// System.out.println("non similarity list size: " + nonSimilarityList.size());

		// System.out.println();

		double medianSim = similarityList.get(similarityList.size() / 2);
		double medianNonSim = nonSimilarityList.get(nonSimilarityList.size() / 2);

		double sumSim = 0;
		for (Double d : similarityList)
			sumSim += d;
		double averageSim = sumSim / similarityList.size();
		averageSim = (double) Math.round(averageSim * 1000) / 1000;

		double sumNonSim = 0;
		for (Double d : nonSimilarityList)
			sumNonSim += d;
		double averageNonSim = sumNonSim / nonSimilarityList.size();
		averageNonSim = (double) Math.round(averageNonSim * 1000) / 1000;

		// System.out.println("max similarity: " + similarityList.get(similarityList.size() - 1));
		// System.out.println("max non similarity: " +
		// nonSimilarityList.get(nonSimilarityList.size() - 1));
		//
		// System.out.println("min similarity: " + similarityList.get(0));
		// System.out.println("min non similarity: " + similarityList.get(0));
		//
		// System.out.println("median similarity: " + medianSim);
		// System.out.println("median non similarity: " + medianNonSim);
		//
		// System.out.println("average similarity: " + averageSim);
		// System.out.println("average non similarity: " + averageNonSim);
		// System.out.println(similarityList);
		// System.out.println(nonSimilarityList);

		System.out.println(_nGram + "\t" + similarityList.get(similarityList.size() - 1) + "\t" + nonSimilarityList.get(nonSimilarityList.size() - 1)
				+ "\t" + similarityList.get(0) + "\t" + similarityList.get(0) + "\t" + medianSim + "\t" + medianNonSim + "\t" + averageSim + "\t"
				+ averageNonSim + "\t" + _type + "\t" + _fileName);

		FileWriter fstream = new FileWriter("results.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(similarityList.toString());
		out.write("\n");
		out.write(nonSimilarityList.toString());
		out.close();
	}

	public static void testYass()
	{
		Yass yass = new Yass();
		ArrayList<String> wordMap = Tokenizator.getWords(new File("Text_Sport.txt"), 3);
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
				if (stemJ == null || stemI == null)
					continue;
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
