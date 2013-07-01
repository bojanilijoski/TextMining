import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import struct.BiMap;

public class TestSimilarityFunctions
{
	public static void main(String[] args)
	{
		String files[] =
		// { "TestTextFile2.txt" };
		{ "Text_Zoran.txt", "Text_Sport.txt", "Text_Ekonomija.txt" };
		NGram.SimilarityType[] types =
		{ NGram.SimilarityType.DICE, NGram.SimilarityType.JACCARD, NGram.SimilarityType.OVERLAP, NGram.SimilarityType.LINEAR_MEASURE1,
				NGram.SimilarityType.LINEAR_MEASURE2, NGram.SimilarityType.LINEAR_MEASURE3, NGram.SimilarityType.LINEAR_MEASURE4,
				NGram.SimilarityType.QUADRATIC_MEASURE1, NGram.SimilarityType.QUADRATIC_MEASURE2, NGram.SimilarityType.QUADRATIC_MEASURE3,
				NGram.SimilarityType.QUADRATIC_MEASURE4 };

		// System.out.println("Difference");
		// for (int i = 2; i < 4; i++)
		// {
		// for (String file : files)
		// {
		// System.out.println("File name: " + file);
		// System.out.println("nGram: " + i);
		// System.out.print(" \t");
		// for (NGram.SimilarityType type1 : types)
		// System.out.print(type1 + "\t");
		// System.out.println();
		// for (NGram.SimilarityType type1 : types)
		// {
		//
		// System.out.print(type1 + "\t");
		// for (NGram.SimilarityType type2 : types)
		// {
		// getDifference2(type1, type2, file, i);
		// }
		// System.out.println();
		// }
		// }
		// }

		// System.out.println("Average");
		// for (int i = 2; i < 4; i++)
		// {
		// for (String file : files)
		// {
		// HashMap<String, ArrayList<String>> wordMap = Tokenizator.getWordsTokens(new File(file),
		// 4, i, 1, 1);
		// HashMap<NGram.SimilarityType, double[][]> matrices = new HashMap<>();
		// NGram nGram = new NGram();
		// for (NGram.SimilarityType type1 : types)
		// matrices.put(type1, nGram.getSiminaliryMatrix(wordMap, type1));
		// BiMap<Integer, String> biMap = nGram.getWordMap();// reden broj <-> zbor
		//
		// // printanje matrici
		// /*
		// * for (NGram.SimilarityType type1 : types) { System.out.println(type1); double[][]
		// * mat = matrices.get(type1); for (int ii = 0; ii < mat.length; ii++) { for (int jj
		// * = 0; jj < mat[0].length; jj++) System.out.print(mat[ii][jj] + "\t");
		// * System.out.println(); } }
		// */
		//
		// System.out.println("File name: " + file);
		// System.out.println("nGram: " + i);
		// System.out.print(" \t");
		// for (NGram.SimilarityType type1 : types)
		// System.out.print(type1 + "\t");
		// System.out.println();
		// for (NGram.SimilarityType type1 : types)
		// {
		//
		// System.out.print(type1 + "\t");
		// for (NGram.SimilarityType type2 : types)
		// {
		// getDifference(matrices.get(type1), matrices.get(type2), biMap);
		// }
		// System.out.println();
		// }
		// }
		// }
		//
		// System.out.println("Min Max");
		// for (int i = 2; i < 4; i++)
		// {
		// for (String file : files)
		// {
		// HashMap<String, ArrayList<String>> wordMap = Tokenizator.getWordsTokens(new File(file),
		// 4, i, 1, 1);
		// HashMap<NGram.SimilarityType, double[][]> matrices = new HashMap<>();
		// NGram nGram = new NGram();
		// for (NGram.SimilarityType type1 : types)
		// matrices.put(type1, nGram.getSiminaliryMatrix(wordMap, type1));
		// BiMap<Integer, String> biMap = nGram.getWordMap();// reden broj <-> zbor
		//
		// System.out.println("File name: " + file);
		// System.out.println("nGram: " + i);
		// System.out.print(" \t");
		// for (NGram.SimilarityType type1 : types)
		// System.out.print(type1 + "\t");
		// System.out.println();
		// for (NGram.SimilarityType type1 : types)
		// {
		//
		// System.out.print(type1 + "\t");
		// for (NGram.SimilarityType type2 : types)
		// {
		// getDifference2(matrices.get(type1), matrices.get(type2), biMap);
		// }
		// System.out.println();
		// }
		// }
		// }

		// System.out.println("Diff");
		// for (int i = 2; i < 4; i++)
		// {
		// for (String file : files)
		// {
		// HashMap<String, ArrayList<String>> wordMap = Tokenizator.getWordsTokens(new File(file),
		// 4, i, 1, 1);
		// HashMap<NGram.SimilarityType, double[][]> matrices = new HashMap<>();
		// NGram nGram = new NGram();
		// for (NGram.SimilarityType type1 : types)
		// matrices.put(type1, nGram.getSiminaliryMatrix(wordMap, type1));
		// BiMap<Integer, String> biMap = nGram.getWordMap();// reden broj <-> zbor
		//
		// System.out.println("File name: " + file);
		// System.out.println("nGram: " + i);
		// System.out.print(" \t");
		// for (NGram.SimilarityType type1 : types)
		// System.out.print(type1 + "\t");
		// System.out.println();
		// for (NGram.SimilarityType type1 : types)
		// {
		//
		// System.out.print(type1 + "\t");
		// for (NGram.SimilarityType type2 : types)
		// {
		// getDifference3(matrices.get(type1), matrices.get(type2), biMap);
		// }
		// System.out.println();
		// }
		// }
		// }

		for (int i = 2; i < 4; i++)
		{
			for (String file : files)
			{
				HashMap<String, ArrayList<String>> wordMap = Tokenizator.getWordsTokens(new File(file), 4, i, 1, 1);
				HashMap<NGram.SimilarityType, double[][]> matrices = new HashMap<>();
				NGram nGram = new NGram();
				for (NGram.SimilarityType type1 : types)
					matrices.put(type1, nGram.getSiminaliryMatrix(wordMap, type1));
				BiMap<Integer, String> biMap = nGram.getWordMap();// reden broj <-> zbor

				System.out.println("(*File name: " + file + "*)");
				System.out.println("(*nGram: " + i + "*)");
				for (NGram.SimilarityType type1 : types)
				{
					System.out.print("(*" + type1 + "*)");

					getListsN(matrices.get(type1), biMap, "lists" + i + "_" + file + "_" + type1 + "_");

					System.out.println();
				}
			}
		}

	}

	public static double getDifference(double[][] similarityMatrix1, double[][] similarityMatrix2, BiMap<Integer, String> biMap)
	{
		HashMap<String, ArrayList<String>> wordStem = new HashMap<String, ArrayList<String>>();
		// koreni
		for (String word : biMap.getList())
		{
			ArrayList<String> stems = Database.getStem(word);
			wordStem.put(word, stems);
		}

		ArrayList<Double> diffT1List = new ArrayList<>();
		ArrayList<Double> diffT2List = new ArrayList<>();
		ArrayList<Double> diffF1List = new ArrayList<>();
		ArrayList<Double> diffF2List = new ArrayList<>();

		for (int i = 1; i < similarityMatrix1.length; i++)
		{
			ArrayList<String> stemsI = wordStem.get(biMap.get(i));
			for (int j = 0; j < i; j++)
			{
				ArrayList<String> stemsJ = wordStem.get(biMap.get(j));

				boolean same = false;
				boolean found = false;
				for (String stemI : stemsI)
				{
					for (String stemJ : stemsJ)
					{
						// ako nema koren vo bazata, nemozhe da se proveri i togash ne gi stavame ni
						// vo isti ni vo razlichni
						if (stemI == null || stemJ == null)
							continue;

						found = true;

						if (stemJ.compareTo(stemI) == 0)
						{
							diffT1List.add(similarityMatrix1[i][j]);
							diffT2List.add(similarityMatrix2[i][j]);

							same = true;
						}

						if (same)
							break;
					}
					if (same)
						break;
				}
				if (!same && found)
				{
					diffF1List.add(similarityMatrix1[i][j]);
					diffF2List.add(similarityMatrix2[i][j]);
				}
			}
		}

		Double avgdiffT1 = 0d;
		Double avgdiffT2 = 0d;
		Double avgdiffF1 = 0d;
		Double avgdiffF2 = 0d;
		for (Double d : diffT1List)
			avgdiffT1 += d;
		avgdiffT1 /= diffT1List.size();
		for (Double d : diffT2List)
			avgdiffT2 += d;
		avgdiffT2 /= diffT2List.size();
		for (Double d : diffF1List)
			avgdiffF1 += d;
		avgdiffF1 /= diffF1List.size();
		for (Double d : diffF2List)
			avgdiffF2 += d;
		avgdiffF2 /= diffF2List.size();

		for (int i = 0; i < diffT1List.size(); i++)
			diffT1List
					.set(i, (diffT1List.get(i) - avgdiffT1) / Math.pow(Math.pow((diffT1List.get(i) - avgdiffT1), 2) / (diffT1List.size() - 1), 0.5));

		for (int i = 0; i < diffT2List.size(); i++)
			diffT2List
					.set(i, (diffT2List.get(i) - avgdiffT2) / Math.pow(Math.pow((diffT2List.get(i) - avgdiffT2), 2) / (diffT2List.size() - 1), 0.5));

		for (int i = 0; i < diffF1List.size(); i++)
			diffF1List
					.set(i, (diffF1List.get(i) - avgdiffF1) / Math.pow(Math.pow((diffF1List.get(i) - avgdiffF1), 2) / (diffF1List.size() - 1), 0.5));

		for (int i = 0; i < diffF2List.size(); i++)
			diffF2List
					.set(i, (diffF2List.get(i) - avgdiffF2) / Math.pow(Math.pow((diffF2List.get(i) - avgdiffF2), 2) / (diffF2List.size() - 1), 0.5));

		double diffT1 = 0d;
		double diffT2 = 0d;
		double diffF1 = 0d;
		double diffF2 = 0d;

		for (int i = 0; i < diffT1List.size(); i++)
			if (diffT1List.get(i) > diffT2List.get(i))
				diffT1 += diffT1List.get(i) - diffT2List.get(i);
			else
				diffT2 += diffT2List.get(i) - diffT1List.get(i);

		for (int i = 0; i < diffF1List.size(); i++)
			if (diffF1List.get(i) > diffF2List.get(i))
				diffF2 += diffF1List.get(i) - diffF2List.get(i);
			else
				diffF1 += diffF2List.get(i) - diffF1List.get(i);

		System.out.print("T(" + Math.round((diffT1) * 1000) / 1000d + " / " + Math.round((diffT2) * 1000) / 1000d + ") " + " F("
				+ Math.round((diffF1) * 1000) / 1000d + " / " + Math.round((diffF2) * 1000) / 1000d + ") " + " ("
				+ Math.round(((diffF1) + (diffT1)) * 1000) / 1000d + " / " + Math.round(((diffF2) + (diffT2)) * 1000) / 1000d + ")" + "\t");

		return (diffT1 + diffF1) - (diffT2 + diffF2);
	}

	public static double getDifference2(double[][] similarityMatrix1, double[][] similarityMatrix2, BiMap<Integer, String> biMap)
	{
		HashMap<String, ArrayList<String>> wordStem = new HashMap<String, ArrayList<String>>();
		// koreni
		for (String word : biMap.getList())
		{
			ArrayList<String> stems = Database.getStem(word);
			wordStem.put(word, stems);
		}

		ArrayList<Double> diffT1List = new ArrayList<>();
		ArrayList<Double> diffT2List = new ArrayList<>();
		ArrayList<Double> diffF1List = new ArrayList<>();
		ArrayList<Double> diffF2List = new ArrayList<>();

		for (int i = 1; i < similarityMatrix1.length; i++)
		{
			ArrayList<String> stemsI = wordStem.get(biMap.get(i));
			for (int j = 0; j < i; j++)
			{
				ArrayList<String> stemsJ = wordStem.get(biMap.get(j));

				boolean same = false;
				boolean found = false;
				for (String stemI : stemsI)
				{
					for (String stemJ : stemsJ)
					{
						// ako nema koren vo bazata, nemozhe da se proveri i togash ne gi stavame ni
						// vo isti ni vo razlichni
						if (stemI == null || stemJ == null)
							continue;

						found = true;

						if (stemJ.compareTo(stemI) == 0)
						{
							diffT1List.add(similarityMatrix1[i][j]);
							diffT2List.add(similarityMatrix2[i][j]);

							same = true;
						}

						if (same)
							break;
					}
					if (same)
						break;
				}
				if (!same && found)
				{
					diffF1List.add(similarityMatrix1[i][j]);
					diffF2List.add(similarityMatrix2[i][j]);
				}
			}
		}

		Double avgdiffT1 = 0d;
		Double avgdiffT2 = 0d;
		Double avgdiffF1 = 0d;
		Double avgdiffF2 = 0d;
		for (Double d : diffT1List)
			avgdiffT1 += d;
		avgdiffT1 /= diffT1List.size();
		for (Double d : diffT2List)
			avgdiffT2 += d;
		avgdiffT2 /= diffT2List.size();
		for (Double d : diffF1List)
			avgdiffF1 += d;
		avgdiffF1 /= diffF1List.size();
		for (Double d : diffF2List)
			avgdiffF2 += d;
		avgdiffF2 /= diffF2List.size();

		for (int i = 0; i < diffT1List.size(); i++)
			diffT1List.set(i, (diffT1List.get(i) - Collections.min(diffT1List)) / (Collections.max(diffT1List) - Collections.min(diffT1List)));

		for (int i = 0; i < diffT2List.size(); i++)
			diffT2List.set(i, (diffT2List.get(i) - Collections.min(diffT2List)) / (Collections.max(diffT2List) - Collections.min(diffT2List)));

		double min = Collections.min(diffF1List);
		double max = Collections.max(diffF1List);
		for (int i = 0; i < diffF1List.size(); i++)
			diffF1List.set(i, (diffF1List.get(i) - min) / (max - min));

		min = Collections.min(diffF2List);
		max = Collections.max(diffF2List);
		for (int i = 0; i < diffF2List.size(); i++)
			diffF2List.set(i, (diffF2List.get(i) - min) / (max - min));

		double diffT1 = 0d;
		double diffT2 = 0d;
		double diffF1 = 0d;
		double diffF2 = 0d;

		for (int i = 0; i < diffT1List.size(); i++)
			if (diffT1List.get(i) > diffT2List.get(i))
				diffT1 += diffT1List.get(i) - diffT2List.get(i);
			else
				diffT2 += diffT2List.get(i) - diffT1List.get(i);

		for (int i = 0; i < diffF1List.size(); i++)
			if (diffF1List.get(i) > diffF2List.get(i))
				diffF2 += diffF1List.get(i) - diffF2List.get(i);
			else
				diffF1 += diffF2List.get(i) - diffF1List.get(i);

		System.out.print("T(" + Math.round((diffT1) * 1000) / 1000d + " / " + Math.round((diffT2) * 1000) / 1000d + ") " + " F("
				+ Math.round((diffF1) * 1000) / 1000d + " / " + Math.round((diffF2) * 1000) / 1000d + ") " + " ("
				+ Math.round(((diffF1) + (diffT1)) * 1000) / 1000d + " / " + Math.round(((diffF2) + (diffT2)) * 1000) / 1000d + ")" + "\t");

		return (diffT1 + diffF1) - (diffT2 + diffF2);
	}

	public static double getDifference3(double[][] similarityMatrix1, double[][] similarityMatrix2, BiMap<Integer, String> biMap)
	{
		HashMap<String, ArrayList<String>> wordStem = new HashMap<String, ArrayList<String>>();
		// koreni
		for (String word : biMap.getList())
		{
			ArrayList<String> stems = Database.getStem(word);
			wordStem.put(word, stems);
		}

		ArrayList<Double> diffT1List = new ArrayList<>();
		ArrayList<Double> diffT2List = new ArrayList<>();
		ArrayList<Double> diffT12DiffList = new ArrayList<>();
		ArrayList<Double> diffF1List = new ArrayList<>();
		ArrayList<Double> diffF2List = new ArrayList<>();
		ArrayList<Double> diffF12DiffList = new ArrayList<>();

		for (int i = 1; i < similarityMatrix1.length; i++)
		{
			ArrayList<String> stemsI = wordStem.get(biMap.get(i));
			for (int j = 0; j < i; j++)
			{
				ArrayList<String> stemsJ = wordStem.get(biMap.get(j));

				boolean same = false;
				boolean found = false;
				for (String stemI : stemsI)
				{
					for (String stemJ : stemsJ)
					{
						// ako nema koren vo bazata, nemozhe da se proveri i togash ne gi stavame ni
						// vo isti ni vo razlichni
						if (stemI == null || stemJ == null)
							continue;

						found = true;

						if (stemJ.compareTo(stemI) == 0)
						{
							diffT1List.add(similarityMatrix1[i][j]);
							diffT2List.add(similarityMatrix2[i][j]);
							diffT12DiffList.add(Math.abs(similarityMatrix1[i][j] - similarityMatrix2[i][j]));

							same = true;
						}

						if (same)
							break;
					}
					if (same)
						break;
				}
				if (!same && found)
				{
					diffF1List.add(similarityMatrix1[i][j]);
					diffF2List.add(similarityMatrix2[i][j]);
					diffF12DiffList.add(Math.abs(similarityMatrix1[i][j] - similarityMatrix2[i][j]));
				}
			}
		}

		Double avgdiffT12Dif = 0d;
		Double avgdiffF12Dif = 0d;
		for (Double d : diffT12DiffList)
			avgdiffT12Dif += d;
		avgdiffT12Dif /= diffT12DiffList.size();
		for (Double d : diffF12DiffList)
			avgdiffF12Dif += d;
		avgdiffF12Dif /= diffF12DiffList.size();

		for (int i = 0; i < diffT12DiffList.size(); i++)
			diffT12DiffList.set(
					i,
					(diffT12DiffList.get(i) - avgdiffT12Dif)
							/ Math.pow(Math.pow((diffT12DiffList.get(i) - avgdiffT12Dif), 2) / (diffT12DiffList.size() - 1), 0.5));

		for (int i = 0; i < diffF12DiffList.size(); i++)
			diffF12DiffList.set(
					i,
					(diffF12DiffList.get(i) - avgdiffF12Dif)
							/ Math.pow(Math.pow((diffF12DiffList.get(i) - avgdiffF12Dif), 2) / (diffF12DiffList.size() - 1), 0.5));

		double diffT1 = 0d;
		double diffT2 = 0d;
		double diffF1 = 0d;
		double diffF2 = 0d;

		for (int i = 0; i < diffT1List.size(); i++)
			if (diffT1List.get(i) > diffT2List.get(i))
				diffT1 += diffT12DiffList.get(i);
			else
				diffT2 += diffT12DiffList.get(i);

		for (int i = 0; i < diffF1List.size(); i++)
			if (diffF1List.get(i) > diffF2List.get(i))
				diffF2 += diffF12DiffList.get(i);
			else
				diffF1 += diffF12DiffList.get(i);

		System.out.print("T(" + Math.round((diffT1) * 1000) / 1000d + " / " + Math.round((diffT2) * 1000) / 1000d + ") " + " F("
				+ Math.round((diffF1) * 1000) / 1000d + " / " + Math.round((diffF2) * 1000) / 1000d + ") " + " ("
				+ Math.round(((diffF1) + (diffT1)) * 1000) / 1000d + " / " + Math.round(((diffF2) + (diffT2)) * 1000) / 1000d + ")" + "\t");

		return (diffT1 + diffF1) - (diffT2 + diffF2);
	}

	public static void getLists(double[][] similarityMatrix, BiMap<Integer, String> biMap, String fileName)
	{
		HashMap<String, ArrayList<String>> wordStem = new HashMap<String, ArrayList<String>>();
		// koreni
		for (String word : biMap.getList())
		{
			ArrayList<String> stems = Database.getStem(word);
			wordStem.put(word, stems);
		}

		ArrayList<Double> trueList = new ArrayList<>();
		ArrayList<Double> falseList = new ArrayList<>();

		for (int i = 1; i < similarityMatrix.length; i++)
		{
			ArrayList<String> stemsI = wordStem.get(biMap.get(i));
			for (int j = 0; j < i; j++)
			{
				ArrayList<String> stemsJ = wordStem.get(biMap.get(j));

				boolean same = false;
				boolean found = false;
				for (String stemI : stemsI)
				{
					for (String stemJ : stemsJ)
					{
						// ako nema koren vo bazata, nemozhe da se proveri i togash ne gi stavame ni
						// vo isti ni vo razlichni
						if (stemI == null || stemJ == null)
							continue;

						found = true;

						if (stemJ.compareTo(stemI) == 0)
						{
							trueList.add(Math.round((similarityMatrix[i][j]) * 1000) / 1000d);

							same = true;
						}

						if (same)
							break;
					}
					if (same)
						break;
				}
				if (!same && found)
				{
					falseList.add(Math.round((similarityMatrix[i][j]) * 1000) / 1000d);
				}
			}
		}

		try
		{
			PrintWriter outT = new PrintWriter(new BufferedWriter(new FileWriter(fileName + "T.txt", true)));
			PrintWriter outF = new PrintWriter(new BufferedWriter(new FileWriter(fileName + "F.txt", true)));
			for (Double d : trueList)
				outT.print(d + " ");
			for (Double d : falseList)
				outF.print(d + " ");
			outT.close();
			outF.close();
		}
		catch (IOException e)
		{
			// oh noes!
		}
	}

	// get lists normalized
	public static void getListsN(double[][] similarityMatrix, BiMap<Integer, String> biMap, String fileName)
	{
		HashMap<String, ArrayList<String>> wordStem = new HashMap<String, ArrayList<String>>();
		// koreni
		for (String word : biMap.getList())
		{
			ArrayList<String> stems = Database.getStem(word);
			wordStem.put(word, stems);
		}

		ArrayList<Double> trueList = new ArrayList<>();
		ArrayList<Double> falseList = new ArrayList<>();

		for (int i = 1; i < similarityMatrix.length; i++)
		{
			ArrayList<String> stemsI = wordStem.get(biMap.get(i));
			for (int j = 0; j < i; j++)
			{
				ArrayList<String> stemsJ = wordStem.get(biMap.get(j));

				boolean same = false;
				boolean found = false;
				for (String stemI : stemsI)
				{
					for (String stemJ : stemsJ)
					{
						// ako nema koren vo bazata, nemozhe da se proveri i togash ne gi stavame ni
						// vo isti ni vo razlichni
						if (stemI == null || stemJ == null)
							continue;

						found = true;

						if (stemJ.compareTo(stemI) == 0)
						{
							trueList.add(Math.round((similarityMatrix[i][j]) * 1000) / 1000d);

							same = true;
						}

						if (same)
							break;
					}
					if (same)
						break;
				}
				if (!same && found)
					falseList.add(Math.round((similarityMatrix[i][j]) * 1000) / 1000d);
			}
		}

		Double minTrueList = Collections.min(trueList);
		Double maxTrueList = Collections.max(trueList);
		Double minFalseList = Collections.min(falseList);
		Double maxFalseList = Collections.max(falseList);


		for (int i = 0; i < trueList.size(); i++)
			trueList.set(i, (trueList.get(i) - minTrueList) / (maxTrueList-minTrueList));

		for (int i = 0; i < falseList.size(); i++)
			falseList.set(i, (falseList.get(i) - minFalseList) / (maxFalseList - minFalseList));

		try
		{
			PrintWriter outT = new PrintWriter(new BufferedWriter(new FileWriter(fileName + "T_N.txt", true)));
			PrintWriter outF = new PrintWriter(new BufferedWriter(new FileWriter(fileName + "F_N.txt", true)));
			for (Double d : trueList)
				outT.print(d + " ");
			for (Double d : falseList)
				outF.print(d + " ");
			outT.close();
			outF.close();
		}
		catch (IOException e)
		{
			// oh noes!
		}
	}
}
