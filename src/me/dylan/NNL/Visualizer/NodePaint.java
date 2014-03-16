package me.dylan.NNL.Visualizer;

import java.awt.Color;
import java.awt.Point;

import me.dylan.NNL.Node;

public class NodePaint {
	Point nodeLocation = new Point(0, 0);
	int size;
	Color color;

	/**
	 * This class is for painting all nodes(HiddenNodes, inputs, outputs)
	 * 
	 * @param x
	 * @param nodeColor
	 *            The color of the node
	 */
	// TODO: No javadocs for this file since pretty self explanatory. Will need
	// to
	// have javadocs added in future for release and/or when its determined
	// necessary
	public NodePaint(int size, Color nodeColor) {
		color = nodeColor;
		this.size = size;
	}

	public Point getPaintCoords() {
		return nodeLocation;
	}

	public void paintNode(int x, int y, Node nodeToPaint) {
		nodeLocation = new Point(x, y);
		Color original = Display.getDisplayBackgroundColor();
		Display.setDisplayBackgroundColor(color);
		if (nodeToPaint.isActive())
			Display.setDisplayBackgroundColor(Color.RED);
		Display.fillOval(nodeLocation.x, nodeLocation.y, size, size);
		Display.drawString(x, y, nodeToPaint.getNodeInfo().getData());
		Display.setDisplayBackgroundColor(original);
	}

	public void setColor(Color c) {
		this.color = c;
	}
}
