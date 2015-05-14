package assignment1;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * Class for approximating them magic number.
 * @author tzwickl
 */
public class FastInverseMagicNumber {

	private static int anzBitsExponent = 4;
	private static int anzBitsMantisse = 8;

	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {

		Gleitpunktzahl.setSizeExponent(anzBitsExponent);
		Gleitpunktzahl.setSizeMantisse(anzBitsMantisse);

		int numOfSamplingPts = 1001;
		int endSize = 10000;
		int stepSize = 1;
		int startSize = 0;
		int offset = 1;
		float[] xData = new float[numOfSamplingPts];
		float[] yData = new float[numOfSamplingPts];
		float x = 0.10f;
		
		List<List<Double>> yValues = new ArrayList<List<Double>>();
		yValues.add(new ArrayList<Double>());
		
		List<List<Double>> xValues = new ArrayList<List<Double>>();
		xValues.add(new ArrayList<Double>());
		
		List<String> names = new ArrayList<String>();
		names.add("Sampling Number");

		for (int i = startSize; i <= endSize; i += stepSize) {
			xValues.get(0).add((double) i / offset);
			validateMagicNumber(xData, yData, i, numOfSamplingPts, x);
			double val = calculateMetric(xData, yData);
			yValues.get(0).add(val);
		}

		/* initialize plotter */
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Chart chart = new Chart();
		frame.add(chart.getChartPanel(xValues, yValues, names, "Magic Number", "Fast Inverse Math"));
		frame.setPreferredSize(new Dimension(10000, 1000));
		frame.setLocation(0, 0);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Calculates actual value and absolute failure for current magic number.
	 * 
	 * @param actualValue
	 * 			The actual value.
	 * @param absoluteFailure
	 * 			The absolute failure.
	 * @param magicNumber
	 * 			The magic number.
	 * @param numOfSamplingPts
	 * 			The number of sampling points
	 * @param x
	 * 			The starting value.
	 */
	public static void validateMagicNumber(float[] actualValue, float[] absoluteFailure, int magicNumber, int numOfSamplingPts, float x) {
		FastMath.setMagic(magicNumber);
		/* calculate data to plot */
		for (int i = 0; i < numOfSamplingPts; i++) {
			actualValue[i] = (float) (1.0d / Math.sqrt(x));
			Gleitpunktzahl y = new Gleitpunktzahl(x);
			absoluteFailure[i] = (float) FastMath.absInvSqrtErr(y);

			x *= Math.pow(100.0d, 1.0d / numOfSamplingPts);
		}
	}
	
	/**
	 * Calculates the metrics.
	 * 
	 * @param xData
	 * 		The x data of the diagram.
	 * @param yData
	 * 		The y data of the diagram.
	 * @return
	 */
	public static float calculateMetric(float[] xData, float[] yData) {
		float sum = 0.0f;
		
		for (int i = 0; i < xData.length; i++) {
			sum += yData[i];
		}
		
		return sum;
	}
}