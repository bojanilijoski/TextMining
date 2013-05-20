import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import struct.BiMap;

public class SingleLink
{

	/**
	 * 
	 * @param similarityMatrix
	 * @param threshold
	 * @return sets of similar items
	 */
	public static ArrayList<ArrayList<Integer>> getSinleLinkClusters(double[][] similarityMatrix,
			double threshold)
	{
		// all words are mapped with integer, number of row in matrix

		// find all single egdes
		LinkedHashMap<Integer, Integer> edges = new LinkedHashMap<>();
		// put first word
		edges.put(0, 0);

		// find link for all words (max similarity to other word)
		for (int i = 1; i < similarityMatrix.length; i++)
		{
			double maxSimilarity = 0.0d;
			int nodeId = i;// the same node
			for (int j = 0; j < i; j++)
			{
				if (maxSimilarity < similarityMatrix[i][j])
				{
					maxSimilarity = similarityMatrix[i][j];
					nodeId = j;
				}
			}

			// if maximum similarity is greater than threshold
			if (maxSimilarity > threshold)
				edges.put(i, nodeId);
			else
				edges.put(i, i);
		}

		ArrayList<ArrayList<Integer>> clusters = new ArrayList<>();

		Integer[] keySet = new Integer[edges.keySet().size()];
		Arrays.sort(edges.keySet().toArray(keySet));

		// iterate all words
		for (Object object : keySet)
		{
			Integer word = (Integer) object;
			boolean clusterFound = false;
			// iterate clusters
			for (ArrayList<Integer> array : clusters)
			{
				// if cluster contains word link then put the word in same cluster
				if (array.contains(edges.get(word)))
				{
					array.add(word);
					clusterFound = true;
				}
			}

			// if cluster is not found create another
			if (!clusterFound)
			{
				ArrayList<Integer> cluster = new ArrayList<>();
				cluster.add(word);
				clusters.add(cluster);
			}
		}

		return clusters;
	}

	public static ArrayList<ArrayList<String>> getMappedSinleLinkClusters(
			BiMap<Integer, String> biMap, double[][] similarityMatrix, double threshold)
	{
		ArrayList<ArrayList<Integer>> arrayLists = SingleLink.getSinleLinkClusters(
				similarityMatrix, threshold);

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

		return lists;
	}
}
