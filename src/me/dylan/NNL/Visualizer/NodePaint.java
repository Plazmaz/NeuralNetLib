package me.dylan.NNL.Visualizer;

import java.awt.Color;
import java.awt.Point;

import me.dylan.NNL.HiddenNode;
import me.dylan.NNL.Node;

public class NodePaint {
    Point nodeLocation = new Point(0, 0);
    int size;
    Color color;

    /**
     * This class is for painting all nodes(Hiddens, inputs, outputs)
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

    public void paintNode(int x, int y, Node nodeToPaint) {
	nodeLocation = new Point(x, y);
	Color original = Display.getDisplayBackgroundColor();
	Display.setDisplayBackgroundColor(color);
	if (nodeToPaint instanceof HiddenNode) {
	    if (((HiddenNode) nodeToPaint).isActive())
		Display.setDisplayBackgroundColor(Color.YELLOW);
	}
	Display.fillOval(nodeLocation.x, nodeLocation.y, size, size);
	Display.setDisplayBackgroundColor(original);
	// Display.drawString(x, y, nodeToPaint.getNodeInfo().getValue());
    }

    public void setColor(Color c) {
	this.color = c;
    }
}
