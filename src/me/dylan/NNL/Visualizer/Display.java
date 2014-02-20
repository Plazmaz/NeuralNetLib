package me.dylan.NNL.Visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display {
	private static JFrame main;
	private static JPanel displayPanel;
	private static Color color = Color.BLACK;
	public static void show(String title, Dimension size, Color backgroundFill) {
		main = new JFrame(title);
		displayPanel = new JPanel();
		displayPanel.setBackground(backgroundFill);
		main.setSize(size);
		main.add(displayPanel);
		main.setVisible(true);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static Graphics getGraphics() {
		return displayPanel.getGraphics();
	}
	
	public static void setColor(Color c) {
		getGraphics().setColor(c);
	}
	
	public static void fillOval(int x, int y, int width, int height) {
		getGraphics().fillOval(x, y, width, height);
	}
	
	public static void drawLine(int x, int y, int x2, int y2) {
		getGraphics().drawLine(x, y, x2, y2);
	}
	
	public static void repaint() {
		displayPanel.repaint();
	}
}
