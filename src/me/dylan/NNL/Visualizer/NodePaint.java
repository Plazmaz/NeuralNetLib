package me.dylan.NNL.Visualizer;

import java.awt.Color;
import java.awt.Point;

public class NodePaint {
	Point nodeLocation = new Point(0, 0);
	int size;
	Color color;

	/**
	 * This class is for painting all nodes(neurons, inputs, outputs)
	 * 
	 * @param x
	 * @param nodeColor
	 *            The color of the node
	 */
	public NodePaint(int size, Color nodeColor) {
		color = nodeColor;
		this.size = size;
	}

	public Point getPaintCoords() {
		return nodeLocation;
	}

	public void paintNode(int x, int y, Color color) {
		this.color = color;
		nodeLocation = new Point(x, y);
		Color original = Display.getDisplayBackgroundColor();
		Display.setDisplayBackgroundColor(color);
		Display.fillOval(nodeLocation.x, nodeLocation.y, size, size);
		Display.setDisplayBackgroundColor(original);
	}

	public void setColor(Color c) {
		this.color = c;
	}
}
