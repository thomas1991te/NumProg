package assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.approximatrix.charting.Legend;
import com.approximatrix.charting.coordsystem.BoxCoordSystem;
import com.approximatrix.charting.coordsystem.CoordSystem;
import com.approximatrix.charting.model.MultiScatterDataModel;
import com.approximatrix.charting.render.MultiScatterChartRenderer;
import com.approximatrix.charting.render.RowColorModel;
import com.approximatrix.charting.swing.ExtendedChartPanel;

public class Chart {

	/**
	 * Constructs the extended chart panel for the given input data.
	 * 
	 * @param xValues
	 *            The x values of all graphs to display.
	 * @param yValues
	 *            The y values of all graph to display.
	 * @param names
	 *            The names of all graphs.
	 * @param xAxisUnit
	 *            The unit of the x axis.
	 * @param title
	 *            The title of the chart.
	 * @return the newly created chart panel.
	 */
	public ExtendedChartPanel getChartPanel(List<List<Double>> xValues,
			List<List<Double>> yValues, List<String> names, String xAxisUnit,
			String title) {
		// Create a MultiScatterDataModel
		MultiScatterDataModel model = new MultiScatterDataModel();

		// add all graphs to the chart
		for (int i = 0; i < names.size(); i++) {
			double[] xVals = new double[xValues.get(i).size()];
			for (int j = 0; j < xValues.get(i).size(); j++) {
				double val = xValues.get(i).get(j);
				xVals[j] = val;
			}
			double[] yVals = new double[yValues.get(i).size()];
			for (int j = 0; j < yValues.get(i).size(); j++) {
				double val = yValues.get(i).get(j);
				yVals[j] = val;
			}
			model.addData(xVals, yVals, names.get(i));
		}

		// Show lines and points
		model.setSeriesLine("Result Chart", true);
		model.setSeriesMarker("Result Chart", true);

		// Create an associated coordinate system object
		CoordSystem coord = new BoxCoordSystem(model);

		coord.setYAxisUnit("Average failure");
		coord.setXAxisUnit(xAxisUnit);

		// Generate the chart panel
		ExtendedChartPanel chartPanel = new ExtendedChartPanel(model, title);

		// Add the coordinate system to the chart
		chartPanel.setCoordSystem(coord);

		// Add the chart renderer
		MultiScatterChartRenderer renderer = new MultiScatterChartRenderer(
				coord, model);

		// Disable the point buffering - there are only 100 points anyway
		renderer.setAllowBuffer(false);

		RowColorModel rowColorModel = new RowColorModel(model);
		Legend legend = new Legend(rowColorModel);
		chartPanel.setLegend(legend);

		// Add a renderer to the chart
		chartPanel.addChartRenderer(renderer, 0);
		chartPanel.enableZoom(true);
		return chartPanel;
	}
}
