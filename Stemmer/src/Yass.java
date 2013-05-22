import java.util.ArrayList;

import struct.BiMap;

public class Yass
{
	private BiMap<Integer, String> wordMap = new BiMap<>();

	public BiMap<Integer, String> getWordMap()
	{
		return wordMap;
	}

	public double[][] getSiminaliryMatrix(ArrayList<String> words)
	{
		// create similarity measure matrix
		double[][] matrix = new double[words.size()][words.size()];

		// create hash map associated with word=index in matrix
		wordMap = new BiMap<>();
		int index = 0;
		for (String word : words)
			wordMap.add(index++, word);

		// fill similarity matrix
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j <= i; j++)
				// same word
				if (i == j)
					matrix[i][j] = 1;
				else if (j < i)// only half matrix
					matrix[i][j] = 2 - getSimilarity23(words.get(i), words.get(j));// get D1 values

		return matrix;
	}

	/**
	 * 
	 * @param word1
	 * @param word2
	 * @return the values {D1, D2, D3, D4}
	 */
	private static double[] getSimilarity(String word1, String word2)
	{
		int n = -1;// length of a bigger word
		int ns = -1;// length of a smaller word

		if (word1.length() >= word2.length())
		{
			n = word1.length();
			ns = word2.length();
		}
		else
		{
			ns = word1.length();
			n = word2.length();
		}

		// fill the array p, similarity of characters of the words
		int[] p = new int[n];
		int m = -1;
		for (int i = 0; i < ns; i++)
		{
			if (word1.charAt(i) == word2.charAt(i))
			{
				p[i] = 0;
			}
			else
			{
				p[i] = 1;
				// First missmach
				if (m == -1)
					m = i;
			}
		}

		for (int i = ns; i < n; i++)
			p[i] = 1;

		double D1 = 0;
		for (int i = 0; i < n; i++)
		{
			D1 += (1 / Math.pow(2, i)) * p[i];
		}

		double DSum = 0;
		for (int i = m; i < n; i++)
		{
			DSum += (1 / Math.pow(2, i - m));
		}

		double D2 = -1;
		D2 = m != -1 ? DSum * 1 / m : -1;

		double D3 = -1;
		D3 = m != -1 ? DSum * (n - m + 1) / m : -1;

		double D4 = -1;
		D4 = m != -1 ? DSum * (n - m + 1) / (n + 1) : -1;

		return new double[]
		{ D1, D2, D3, D4 };
	}

	private static double getSimilarity23(String word1, String word2)
	{
		int n = -1;// length of a bigger word
		int ns = -1;// length of a smaller word

		if (word1.length() >= word2.length())
		{
			n = word1.length();
			ns = word2.length();
		}
		else
		{
			ns = word1.length();
			n = word2.length();
		}

		// fill the array p, similarity of characters of the words
		int[] p = new int[n];
		int m = -1;
		for (int i = 0; i < ns; i++)
		{
			if (word1.charAt(i) == word2.charAt(i))
			{
				p[i] = 0;
			}
			else
			{
				p[i] = 1;
				// First missmach
				if (m == -1)
					m = i;
			}
		}

		for (int i = ns; i < n; i++)
			p[i] = 1;

		double D1 = 0;
		int k = (int) (n * 0.33);

		int iArray[] = new int[n];
		int iValue = 0;

		iArray[k] = iValue;

		iValue = 1;
		for (int i = k + 1; i < n; i++)
		{
			iArray[i] = iValue;
			iValue += 2;
		}

		iValue = 2;
		for (int i = k - 1; i >= 0; i--)
		{
			iArray[i] = iValue;
			iValue += 2;
		}

		for (int i = 0; i < n; i++)
		{
			D1 += (1 / Math.pow(2, iArray[i])) * p[i];
		}

		return D1;
	}
}
