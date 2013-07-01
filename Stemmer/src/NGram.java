import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import struct.BiMap;
import struct.Matrix;

public class NGram
{
	private BiMap<Integer, String> wordMap = new BiMap<>();

	public enum SimilarityType
	{
		DICE, JACCARD, SIMPLE, COSINE, OVERLAP, LINEAR_MEASURE1, LINEAR_MEASURE2, LINEAR_MEASURE3, LINEAR_MEASURE4, QUADRATIC_MEASURE1, QUADRATIC_MEASURE2, QUADRATIC_MEASURE3, QUADRATIC_MEASURE4
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
	public double[][] getSiminaliryMatrix(HashMap<String, ArrayList<String>> words, NGram.SimilarityType similarityType)
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
					matrix[i][j] = getSimilarity(words.get(wordMap.get(i)), words.get(wordMap.get(j)), similarityType);

		return matrix;
	}

	private static double getSimilarity(ArrayList<String> word1, ArrayList<String> word2, NGram.SimilarityType similarityType)
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

		case LINEAR_MEASURE1:
			return getSimilarityLinearMeasure(word1, word2);
		case LINEAR_MEASURE2:
			return getSimilarityLinearMeasure2(word1, word2);
		case LINEAR_MEASURE3:
			return getSimilarityLinearMeasure3(word1, word2);
		case LINEAR_MEASURE4:
			return getSimilarityLinearMeasure4(word1, word2);

		case QUADRATIC_MEASURE1:
			return getSimilarityQuadraticMeasure(word1, word2);
		case QUADRATIC_MEASURE2:
			return getSimilarityQuadraticMeasure2(word1, word2);
		case QUADRATIC_MEASURE3:
			return getSimilarityQuadraticMeasure3(word1, word2);
		case QUADRATIC_MEASURE4:
			return getSimilarityQuadraticMeasure4(word1, word2);

		default:
			return getSimilarityDice(word1, word2);
		}
	}

	// Dice's coefficient
	private static double getSimilarityDice(List<String> word1, List<String> word2)
	{
		Set<String> word1Set = new HashSet<>(word1);
		Set<String> word2Set = new HashSet<>(word2);

		return 2d * getIntersectionSize(word1Set, word2Set) / (word1Set.size() + word2Set.size());
	}

	// Simple matching coefficient
	private static double getSimilaritySimple(List<String> word1, List<String> word2)
	{
		Set<String> word1Set = new HashSet<>(word1);
		Set<String> word2Set = new HashSet<>(word2);

		return getIntersectionSize(word1Set, word2Set);
	}

	// Jaccard's coefficient
	public static double getSimilarityJaccard(List<String> word1, List<String> word2)
	{
		Set<String> word1Set = new HashSet<>(word1);
		Set<String> word2Set = new HashSet<>(word2);

		double intersectionSize = getIntersectionSize(word1Set, word2Set);

		return intersectionSize * 1d / (word1Set.size() + word2Set.size() - intersectionSize);
	}

	// Cosine coefficient
	// TODO neshto ne e vo red, mozhno e da ne e taka formulata
	private static double getSimilarityCosine(List<String> word1, List<String> word2)
	{
		Set<String> word1Set = new HashSet<>(word1);
		Set<String> word2Set = new HashSet<>(word2);

		return getIntersectionSize(word1Set, word2Set) * 1.0d / (word1Set.size() * word2Set.size());
	}

	// Overlap coefficient
	private static double getSimilarityOverlap(List<String> word1, List<String> word2)
	{
		Set<String> word1Set = new HashSet<>(word1);
		Set<String> word2Set = new HashSet<>(word2);

		return getIntersectionSize(word1Set, word2Set) * 1.0d / Math.min(word1Set.size(), word2Set.size());
	}

	public static double getSimilarityLinearMeasure(List<String> word1, List<String> word2)
	{
		double distance = 0d;

		HashMap<String, Integer> tokenCount = new HashMap<>();
		HashMap<String, ArrayList<Double>> tokenIndexes = new HashMap<>();

		for (int i = 0; i < word1.size(); i++)
		{
			int gap = Integer.MAX_VALUE;// distance between i and j
			String w1 = word1.get(i);
			for (int j = 0; j < word2.size(); j++)
			{
				String w2 = word2.get(j);
				if (w1.compareTo(w2) == 0)
				{
					gap = Math.abs(i - j);

					if (tokenIndexes.containsKey(w1))
						tokenIndexes.get(w1).add(gap * 1.0d);
					else
					{
						ArrayList<Double> array = new ArrayList<>();
						array.add(gap * 1.0d);
						tokenIndexes.put(w1, array);
					}
				}
			}
			if (gap < Integer.MAX_VALUE)
				if (tokenCount.containsKey(w1))
					tokenCount.put(w1, tokenCount.get(w1) + 1);
				else
					tokenCount.put(w1, 1);
		}
		for (String token : tokenIndexes.keySet())
		{
			List<Double> minList = Matrix.getListMinMember(new Matrix(makeMatrix(tokenIndexes.get(token), tokenCount.get(token))));
			Collections.sort(minList, Collections.reverseOrder());
			int minTokens = tokenCount.get(token) > tokenIndexes.get(token).size() / tokenCount.get(token) ? tokenIndexes.get(token).size()
					/ tokenCount.get(token) : tokenCount.get(token);
			int i = 0;
			for (Double gap : minList)
				if (i < minTokens)
				{
					distance += 1 - (gap * 1d / Math.max(word1.size(), word2.size()));
					i++;
				}
				else
					break;

		}

		return distance / Math.max(word1.size(), word2.size());

	}

	public static double getSimilarityLinearMeasure2(List<String> word1, List<String> word2)
	{
		double distance = 0d;

		HashMap<String, Integer> tokenCount = new HashMap<>();
		HashMap<String, ArrayList<Double>> tokenIndexes = new HashMap<>();

		for (int i = 0; i < word1.size(); i++)
		{
			int gap = Integer.MAX_VALUE;// distance between i and j
			String w1 = word1.get(i);
			for (int j = 0; j < word2.size(); j++)
			{
				String w2 = word2.get(j);
				if (w1.compareTo(w2) == 0)
				{
					gap = Math.abs(i - j);

					if (tokenIndexes.containsKey(w1))
						tokenIndexes.get(w1).add(gap * 1.0d);
					else
					{
						ArrayList<Double> array = new ArrayList<>();
						array.add(gap * 1.0d);
						tokenIndexes.put(w1, array);
					}
				}
			}
			if (gap < Integer.MAX_VALUE)
				if (tokenCount.containsKey(w1))
					tokenCount.put(w1, tokenCount.get(w1) + 1);
				else
					tokenCount.put(w1, 1);
		}
		for (String token : tokenIndexes.keySet())
		{
			List<Double> minList = Matrix.getListMinMember(new Matrix(makeMatrix(tokenIndexes.get(token), tokenCount.get(token))));
			Collections.sort(minList, Collections.reverseOrder());
			int minTokens = tokenCount.get(token) > tokenIndexes.get(token).size() / tokenCount.get(token) ? tokenIndexes.get(token).size()
					/ tokenCount.get(token) : tokenCount.get(token);
			int i = 0;
			for (Double gap : minList)
				if (i < minTokens)
				{
					distance += 1 - (gap * 1d / Math.max(word1.size(), word2.size()));
					i++;
				}
				else
					break;

		}
		int diffSize = word1.size() + word2.size() - 2 * tokenCount.keySet().size();

		return distance / (Math.min(word1.size(), word2.size()) + diffSize);

	}

	public static double getSimilarityLinearMeasure3(List<String> word1, List<String> word2)
	{
		double distance = 0d;

		HashMap<String, Integer> tokenCount = new HashMap<>();
		HashMap<String, ArrayList<Double>> tokenIndexes = new HashMap<>();

		for (int i = 0; i < word1.size(); i++)
		{
			int gap = Integer.MAX_VALUE;// distance between i and j
			String w1 = word1.get(i);
			for (int j = 0; j < word2.size(); j++)
			{
				String w2 = word2.get(j);
				if (w1.compareTo(w2) == 0)
				{
					gap = Math.abs(i - j);

					if (tokenIndexes.containsKey(w1))
						tokenIndexes.get(w1).add(gap * 1.0d);
					else
					{
						ArrayList<Double> array = new ArrayList<>();
						array.add(gap * 1.0d);
						tokenIndexes.put(w1, array);
					}
				}
			}
			if (gap < Integer.MAX_VALUE)
				if (tokenCount.containsKey(w1))
					tokenCount.put(w1, tokenCount.get(w1) + 1);
				else
					tokenCount.put(w1, 1);
		}
		for (String token : tokenIndexes.keySet())
		{
			List<Double> minList = Matrix.getListMinMember(new Matrix(makeMatrix(tokenIndexes.get(token), tokenCount.get(token))));
			Collections.sort(minList, Collections.reverseOrder());
			int minTokens = tokenCount.get(token) > tokenIndexes.get(token).size() / tokenCount.get(token) ? tokenIndexes.get(token).size()
					/ tokenCount.get(token) : tokenCount.get(token);
			int i = 0;
			for (Double gap : minList)
				if (i < minTokens)
				{
					distance += 1 - (gap * 1d / Math.max(word1.size(), word2.size()));
					i++;
				}
				else
					break;

		}

		int diffSize = word1.size() + word2.size() - 2 * tokenCount.keySet().size();

		return distance / (Math.max(word1.size(), word2.size()) + diffSize); // A+B
	}

	public static double getSimilarityLinearMeasure4(List<String> word1, List<String> word2)
	{
		double distance = 0d;

		HashMap<String, Integer> tokenCount = new HashMap<>();
		HashMap<String, ArrayList<Double>> tokenIndexes = new HashMap<>();

		for (int i = 0; i < word1.size(); i++)
		{
			int gap = Integer.MAX_VALUE;// distance between i and j
			String w1 = word1.get(i);
			for (int j = 0; j < word2.size(); j++)
			{
				String w2 = word2.get(j);
				if (w1.compareTo(w2) == 0)
				{
					gap = Math.abs(i - j);

					if (tokenIndexes.containsKey(w1))
						tokenIndexes.get(w1).add(gap * 1.0d);
					else
					{
						ArrayList<Double> array = new ArrayList<>();
						array.add(gap * 1.0d);
						tokenIndexes.put(w1, array);
					}
				}
			}
			if (gap < Integer.MAX_VALUE)
				if (tokenCount.containsKey(w1))
					tokenCount.put(w1, tokenCount.get(w1) + 1);
				else
					tokenCount.put(w1, 1);
		}
		for (String token : tokenIndexes.keySet())
		{
			List<Double> minList = Matrix.getListMinMember(new Matrix(makeMatrix(tokenIndexes.get(token), tokenCount.get(token))));
			Collections.sort(minList, Collections.reverseOrder());
			int minTokens = tokenCount.get(token) > tokenIndexes.get(token).size() / tokenCount.get(token) ? tokenIndexes.get(token).size()
					/ tokenCount.get(token) : tokenCount.get(token);
			int i = 0;
			for (Double gap : minList)
				if (i < minTokens)
				{
					distance += 1 - (gap * 1d / Math.max(word1.size(), word2.size()));
					i++;
				}
				else
					break;

		}

		int diffSize = word1.size() + word2.size() - tokenCount.keySet().size();
		return distance / diffSize; // A u B
	}

	private static double getSimilarityQuadraticMeasure(List<String> word1, List<String> word2)
	{
		double distance = 0d;

		HashMap<String, Integer> tokenCount = new HashMap<>();
		HashMap<String, ArrayList<Double>> tokenIndexes = new HashMap<>();

		for (int i = 0; i < word1.size(); i++)
		{
			int gap = Integer.MAX_VALUE;// distance between i and j
			String w1 = word1.get(i);
			for (int j = 0; j < word2.size(); j++)
			{
				String w2 = word2.get(j);
				if (w1.compareTo(w2) == 0)
				{
					gap = Math.abs(i - j);

					if (tokenIndexes.containsKey(w1))
						tokenIndexes.get(w1).add(gap * 1.0d);
					else
					{
						ArrayList<Double> array = new ArrayList<>();
						array.add(gap * 1.0d);
						tokenIndexes.put(w1, array);
					}
				}
			}
			if (gap < Integer.MAX_VALUE)
				if (tokenCount.containsKey(w1))
					tokenCount.put(w1, tokenCount.get(w1) + 1);
				else
					tokenCount.put(w1, 1);
		}
		for (String token : tokenIndexes.keySet())
		{
			List<Double> minList = Matrix.getListMinMember(new Matrix(makeMatrix(tokenIndexes.get(token), tokenCount.get(token))));
			Collections.sort(minList, Collections.reverseOrder());
			int minTokens = tokenCount.get(token) > tokenIndexes.get(token).size() / tokenCount.get(token) ? tokenIndexes.get(token).size()
					/ tokenCount.get(token) : tokenCount.get(token);
			int i = 0;
			for (Double gap : minList)
				if (i < minTokens)
				{
					distance += 1 - (Math.pow(gap * 1d / Math.max(word1.size(), word2.size()), 2));
					i++;
				}
				else
					break;

		}

		return distance / Math.max(word1.size(), word2.size());
	}

	private static double getSimilarityQuadraticMeasure2(List<String> word1, List<String> word2)
	{
		double distance = 0d;

		HashMap<String, Integer> tokenCount = new HashMap<>();
		HashMap<String, ArrayList<Double>> tokenIndexes = new HashMap<>();

		for (int i = 0; i < word1.size(); i++)
		{
			int gap = Integer.MAX_VALUE;// distance between i and j
			String w1 = word1.get(i);
			for (int j = 0; j < word2.size(); j++)
			{
				String w2 = word2.get(j);
				if (w1.compareTo(w2) == 0)
				{
					gap = Math.abs(i - j);

					if (tokenIndexes.containsKey(w1))
						tokenIndexes.get(w1).add(gap * 1.0d);
					else
					{
						ArrayList<Double> array = new ArrayList<>();
						array.add(gap * 1.0d);
						tokenIndexes.put(w1, array);
					}
				}
			}
			if (gap < Integer.MAX_VALUE)
				if (tokenCount.containsKey(w1))
					tokenCount.put(w1, tokenCount.get(w1) + 1);
				else
					tokenCount.put(w1, 1);
		}
		for (String token : tokenIndexes.keySet())
		{
			List<Double> minList = Matrix.getListMinMember(new Matrix(makeMatrix(tokenIndexes.get(token), tokenCount.get(token))));
			Collections.sort(minList, Collections.reverseOrder());
			int minTokens = tokenCount.get(token) > tokenIndexes.get(token).size() / tokenCount.get(token) ? tokenIndexes.get(token).size()
					/ tokenCount.get(token) : tokenCount.get(token);
			int i = 0;
			for (Double gap : minList)
				if (i < minTokens)
				{
					distance += 1 - (Math.pow(gap * 1d / Math.max(word1.size(), word2.size()), 2));
					i++;
				}
				else
					break;

		}

		int diffSize = word1.size() + word2.size() - 2 * tokenCount.keySet().size();

		return distance / (Math.min(word1.size(), word2.size()) + diffSize);
	}

	private static double getSimilarityQuadraticMeasure3(List<String> word1, List<String> word2)
	{
		double distance = 0d;

		HashMap<String, Integer> tokenCount = new HashMap<>();
		HashMap<String, ArrayList<Double>> tokenIndexes = new HashMap<>();

		for (int i = 0; i < word1.size(); i++)
		{
			int gap = Integer.MAX_VALUE;// distance between i and j
			String w1 = word1.get(i);
			for (int j = 0; j < word2.size(); j++)
			{
				String w2 = word2.get(j);
				if (w1.compareTo(w2) == 0)
				{
					gap = Math.abs(i - j);

					if (tokenIndexes.containsKey(w1))
						tokenIndexes.get(w1).add(gap * 1.0d);
					else
					{
						ArrayList<Double> array = new ArrayList<>();
						array.add(gap * 1.0d);
						tokenIndexes.put(w1, array);
					}
				}
			}
			if (gap < Integer.MAX_VALUE)
				if (tokenCount.containsKey(w1))
					tokenCount.put(w1, tokenCount.get(w1) + 1);
				else
					tokenCount.put(w1, 1);
		}
		for (String token : tokenIndexes.keySet())
		{
			List<Double> minList = Matrix.getListMinMember(new Matrix(makeMatrix(tokenIndexes.get(token), tokenCount.get(token))));
			Collections.sort(minList, Collections.reverseOrder());
			int minTokens = tokenCount.get(token) > tokenIndexes.get(token).size() / tokenCount.get(token) ? tokenIndexes.get(token).size()
					/ tokenCount.get(token) : tokenCount.get(token);
			int i = 0;
			for (Double gap : minList)
				if (i < minTokens)
				{
					distance += 1 - (Math.pow(gap * 1d / Math.max(word1.size(), word2.size()), 2));
					i++;
				}
				else
					break;

		}

		int diffSize = word1.size() + word2.size() - 2 * tokenCount.keySet().size();

		return distance / (Math.max(word1.size(), word2.size()) + diffSize); // A+B
	}

	private static double getSimilarityQuadraticMeasure4(List<String> word1, List<String> word2)
	{
		double distance = 0d;

		HashMap<String, Integer> tokenCount = new HashMap<>();
		HashMap<String, ArrayList<Double>> tokenIndexes = new HashMap<>();

		for (int i = 0; i < word1.size(); i++)
		{
			int gap = Integer.MAX_VALUE;// distance between i and j
			String w1 = word1.get(i);
			for (int j = 0; j < word2.size(); j++)
			{
				String w2 = word2.get(j);
				if (w1.compareTo(w2) == 0)
				{
					gap = Math.abs(i - j);

					if (tokenIndexes.containsKey(w1))
						tokenIndexes.get(w1).add(gap * 1.0d);
					else
					{
						ArrayList<Double> array = new ArrayList<>();
						array.add(gap * 1.0d);
						tokenIndexes.put(w1, array);
					}
				}
			}
			if (gap < Integer.MAX_VALUE)
				if (tokenCount.containsKey(w1))
					tokenCount.put(w1, tokenCount.get(w1) + 1);
				else
					tokenCount.put(w1, 1);
		}
		for (String token : tokenIndexes.keySet())
		{
			List<Double> minList = Matrix.getListMinMember(new Matrix(makeMatrix(tokenIndexes.get(token), tokenCount.get(token))));
			Collections.sort(minList, Collections.reverseOrder());
			int minTokens = tokenCount.get(token) > tokenIndexes.get(token).size() / tokenCount.get(token) ? tokenIndexes.get(token).size()
					/ tokenCount.get(token) : tokenCount.get(token);
			int i = 0;
			for (Double gap : minList)
				if (i < minTokens)
				{
					distance += 1 - (Math.pow(gap * 1d / Math.max(word1.size(), word2.size()), 2));
					i++;
				}
				else
					break;

		}

		int diffSize = word1.size() + word2.size() - tokenCount.keySet().size();
		return distance / diffSize; // A u B
	}

	private static int getIntersectionSize(Set<String> set1, Set<String> set2)
	{
		boolean set1IsLarger = set1.size() > set2.size();
		Set<String> cloneSet = new HashSet<String>(set1IsLarger ? set2 : set1);
		cloneSet.retainAll(set1IsLarger ? set1 : set2);
		return cloneSet.size();
	}

	private static double[][] makeMatrix(List<Double> array, int noOfRows)
	{
		int noOfColumns = array.size() / noOfRows;
		int n = noOfColumns > noOfRows ? noOfColumns : noOfRows;
		double[][] matrix = new double[n][n];
		for (int i = 0; i < noOfRows; i++)
			for (int j = 0; j < noOfColumns; j++)
				matrix[i][j] = array.get(i * noOfColumns + j);

		return matrix;
	}
}
