package me.dylan.NNL.Visualizer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * Basic painting and frame class
 * @author Dylan
 *
 */
public class Display {
	private static JFrame main;
	private static JPanel displayPanel;
	static Graphics graphics;
	public static void showDisplay(String title, Dimension size, Color backgroundFill) {
		main = new JFrame(title);
		displayPanel = new JPanel();
		displayPanel.setBackground(backgroundFill);
		main.setSize(size);
		main.add(displayPanel);
		main.setVisible(true);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		graphics = displayPanel.getGraphics();
	}
	public static void setStrokeWidth(int width) {
		((Graphics2D)getGraphics()).setStroke(new BasicStroke(width));
	}
	public static Graphics getGraphics() {
		return graphics;
	}
	
	public static void setDisplayBackgroundColor(Color c) {
		getGraphics().setColor(c);
	}
	
	public static void fillOval(int x, int y, int width, int height) {
		getGraphics().fillOval(x, y, width, height);
	}
	
	public static void drawLine(int x, int y, int x2, int y2) {
		getGraphics().drawLine(x, y, x2, y2);
	}
	
	public static void repaint() {
		displayPanel.revalidate();
	}

	public static Color getDisplayBackgroundColor() {
		return getGraphics().getColor();
	}
	
	public static void setOffset(Point offset) {
		getGraphics().translate(offset.x, offset.y);
	}
	
//	public static void updateDoubleBuffer() {
//		displayPanel.update(getGraphics());
//	}
}
