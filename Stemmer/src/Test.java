import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import struct.BiMap;

public class Test
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		long startTime = System.currentTimeMillis();
		// // Test.tokenizator();
		// Test.getWords();
		// Test.getSimilarityMatrix();
		// Test.getSinleLinkClusters();
		getSimilarityMatrix();
		// testLineareMeasure();

		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("Time elased: " + estimatedTime / 1000d + "s");

		// testSyllableWord();
	}

	private static void tokenizator()
	{
		// Tokenizator tokenizator = new Tokenizator();
		// ArrayList<String> arrayList = tokenizator.getWordsTokens("TestTextFile2.txt", 2);
		// for (String s : arrayList)
		// {
		// System.out.println(s + ": " + s.length());
		// }
		// for (int i = 1; i < 11; i++)
		// {
		// System.out.println("N-Gram size: " + i);
		// ArrayList<String> arrayList = tokenizator.nGramWord("abcdefghijklmnopqrstuvwxyz", i);
		// System.out.println("Array List size: " + arrayList.size());
		// for (String s : arrayList)
		// {
		// System.out.println(s + ": " + s.length());
		// }
		// }
		// System.out.println(tokenizator.nGramWord("abcdefghijklmnopqrstuvwxyz", 4)
		// .get(tokenizator.nGramWord("abcdefghijklmnopqrstuvwxyz", 4).size()-1).length());

	}

	private static void getWords()
	{
		HashMap<String, ArrayList<String>> wordMap = Tokenizator.getWordsTokens(new File("TestTextFile2.txt"), 3, 2, 1, 1);
		System.out.println(wordMap);
	}

	private static void getSimilarityMatrix()
	{
		NGram nGram = new NGram();
		HashMap<String, ArrayList<String>> wordMap = Tokenizator.getWordsTokens(new File("TestTextFile2.txt"), 3, 2, 1, 1);
		System.out.println(wordMap);
		double[][] matrix = nGram.getSiminaliryMatrix(wordMap, NGram.SimilarityType.QUADRATIC_MEASURE1);
		DecimalFormat twoDForm = new DecimalFormat("#.###");

		for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix.length; j++)
			{
				System.out.print(Double.valueOf(twoDForm.format(matrix[i][j])) + "\t");
			}
			System.out.println();
		}

	}

	private static void getSimilarityMatrixYASS()
	{
		Yass yass = new Yass();
		ArrayList<String> wordMap = Tokenizator.getWords(new File("TestTextFile2.txt"), 3);
		System.out.println(wordMap);
		double[][] matrix = yass.getSiminaliryMatrix(wordMap);
		DecimalFormat twoDForm = new DecimalFormat("#.##");

		for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix.length; j++)
			{
				System.out.print(Double.valueOf(twoDForm.format(matrix[i][j])) + " ");
			}
			System.out.println();
		}

	}

	private static void getSinleLinkClusters()
	{
		NGram nGram = new NGram();
		HashMap<String, ArrayList<String>> wordMap = Tokenizator.getWordsTokens(new File("TestTextFile2.txt"), 3, 0);
		System.out.println("no of distinct words: " + wordMap.size());
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		double[][] matrix = nGram.getSiminaliryMatrix(wordMap, NGram.SimilarityType.DICE);
		BiMap<Integer, String> biMap = nGram.getWordMap();
		System.out.println(biMap);
		double minValue = Double.MAX_VALUE;
		double maxValue = Double.MIN_VALUE;

		for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix.length; j++)
			{
				System.out.print(Double.valueOf(twoDForm.format(matrix[i][j])) + " ");
				if (matrix[i][j] > maxValue)
					maxValue = matrix[i][j];
				if (matrix[i][j] < minValue)
					minValue = matrix[i][j];
			}
			System.out.println();
		}

		System.out.println("minValue: " + minValue);
		System.out.println("maxValue: " + maxValue);

		System.out.println("histogram: " + Utils.getHistogram(matrix, minValue, maxValue, 0.1d));

		ArrayList<ArrayList<Integer>> arrayLists = SingleLink.getSinleLinkClusters(matrix, 0.30d);

		ArrayList<ArrayList<String>> lists = new ArrayList<>();

		for (ArrayList<Integer> array : arrayLists)
		{
			ArrayList<String> list = new ArrayList<>();
			for (Integer i : array)
			{
				list.add(biMap.get(i));
			}
			lists.add(list);
		}

		System.out.println("clusters: " + lists);
		int clustersSize = lists.size();
		double averageWordsPerCluster = 0.0d;
		int max = 0;
		int min = clustersSize;
		for (ArrayList<String> words : lists)
		{
			averageWordsPerCluster += words.size();
			if (max < words.size())
				max = words.size();
			if (min > words.size())
				min = words.size();
		}
		averageWordsPerCluster = (averageWordsPerCluster / clustersSize * 1.0d);
		System.out.println("clusters size: " + lists.size());
		System.out.println("average size per cluster: " + averageWordsPerCluster);
		System.out.println("max size per cluster: " + max);
		System.out.println("min size per cluster: " + min);

		System.out.println(lists.size() + " & " + averageWordsPerCluster + " & " + max + " & " + min);
	}

	public static void testSyllableWord()
	{
		String words[] =
		{ "��������", "���������", "���������", "�����", "�����", "�����", "�����", "�����", "������", "������", "������", "������", "������",
				"�����", "�������", "���", "����", "����", "����", "�����", "������", "�������", "������", "������", "�����", "����", "����",
				"�����", "�������", "������", "�����", "������", "�����", "�����", "�������", "�����", "�����", "�����", "������", "�������",
				"�����", "�����", "������", "�����", "��������", "����������", "���������", "���������", "�������", "�����������", "�������",
				"��������", "����������", "���������", "�������", "������������", "������������", "������������", "����������", "���������",
				"�����������", "������������", "������������", "��������������", "�����", "���������", "��������" };
		// { "������������", "���������", "������������" };
		// { "������������" };
		for (String s : words)
			System.out.println(Tokenizator.syllableWord(s));
	}

	public static void tastMakeMatrix()
	{
		// double matrix[][] = NGram.makeMatrix(Arrays.asList(1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d,
		// 10d, 11d, 12d), 6);
		// for (int i = 0; i < matrix.length; i++)
		// {
		// for (int j = 0; j < matrix[0].length; j++)
		// System.out.print(matrix[i][j] + "\t");
		// System.out.println();
		// }

	}

	public static void testLineareMeasure()
	{
		// System.out.println(NGram.getSimilarityLinearMeasure(Arrays.asList("a", "c", "b", "c",
		// "b"), Arrays.asList("a", "b", "a", "b", "c", "a")));
		// System.out.println(NGram.getSimilarityLinearMeasure(Arrays.asList("a", "b", "a", "b",
		// "c", "a"), Arrays.asList("a", "c", "b", "c", "b")));
		System.out.println(NGram.getSimilarityLinearMeasure(Arrays.asList("a", "a", "b", "b", "b"), Arrays.asList("b", "b", "a", "a")));
		System.out.println(NGram.getSimilarityJaccard(Arrays.asList("a", "a", "b", "b", "b"), Arrays.asList("b", "b", "a", "a")));
	}

}
