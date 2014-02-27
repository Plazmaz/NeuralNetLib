package me.dylan.NNL.Visualizer;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Basic painting and frame class
 * 
 * @author Dylan
 * 
 */
public class Display {
    private static JFrame main;
    private static JPanel displayPanel;
    private static Graphics graphics;
    private static Robot bot;
    public static void showDisplay(String title, Dimension size,
	    Color backgroundFill) {
	try {
	    bot = new Robot();
	} catch (AWTException e) {
	    e.printStackTrace();
	}
	main = new JFrame(title);
	displayPanel = new JPanel();
	displayPanel.setBackground(backgroundFill);
	main.setSize(size);
	main.add(displayPanel);
	main.setVisible(true);
	main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	graphics = displayPanel.getGraphics();
    }

    public static int getWidth() {
	return main.getWidth();
    }

    public static int getHeight() {
	return main.getHeight();
    }

    public static void clearRect(int x, int y, int width, int height) {
	getGraphics().clearRect(x, y, width, height);
    }

    public static void addMouseMotionListener(MouseMotionListener listener) {
	displayPanel.addMouseMotionListener(listener);
    }

    public static void addMouseListener(MouseListener listener) {
	displayPanel.addMouseListener(listener);
    }

    public static void setStrokeWidth(int width) {
	((Graphics2D) getGraphics()).setStroke(new BasicStroke(width));
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

    public static void drawString(int x, int y, String value) {
	getGraphics().drawString(value, x, y);
    }

    public static void drawLine(int x, int y, int x2, int y2) {
	getGraphics().drawLine(x, y, x2, y2);
    }

    public static void repaint() {
	displayPanel.revalidate();
    }

    public static void fillRect(int x, int y, int width, int height) {
	getGraphics().fillRect(x, y, width, height);
    }

    public static Color getDisplayBackgroundColor() {
	return getGraphics().getColor();
    }

    public static void setOffset(Point offset) {
	getGraphics().translate(offset.x, offset.y);
    }
    
    public static Point getWindowLocOnScreen() {
	return main.getLocationOnScreen();
    }
    
    public static void setMouseLocation(Point location) {
	bot.mouseMove((int)location.getX(), (int)location.getY());
    }
    // public static void updateDoubleBuffer() {
    // displayPanel.update(getGraphics());
    // }
}
