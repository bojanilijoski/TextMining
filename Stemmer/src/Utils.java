import java.math.BigDecimal;
import java.text.Bidi;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Utils
{
	/**
	 * create a histogram for matrix, beginning from minValue to maxValue with step
	 * 
	 * @param matrix
	 * @param minValue
	 * @param maxValue
	 * @param step
	 * @return array with values for histogram
	 */
	public static LinkedHashMap<BigDecimal, Integer> getHistogram(double[][] matrix,
			double minValue, double maxValue, double step)
	{
		LinkedHashMap<BigDecimal, Integer> values = new LinkedHashMap<>();
		BigDecimal inc = BigDecimal.valueOf(minValue);

		while (inc.compareTo(BigDecimal.valueOf(maxValue)) <= 0)
		{
			values.put(inc, 0);
			inc = inc.add(BigDecimal.valueOf(step));
		}

		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++)
			{
				int pos = (int) (matrix[i][j] / step);

				BigDecimal key = BigDecimal.valueOf(step).multiply(BigDecimal.valueOf(pos));

				//if (key.compareTo(BigDecimal.valueOf(minValue)) == 0)
				//	key = BigDecimal.valueOf(minValue).add(BigDecimal.valueOf(step));

				int value = (Integer) values.get(key);
				value++;
				values.put(key, value);
			}

		return values;
	}
}
