package me.dylan.NNL.Visualizer;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class DisplayCanvas extends Canvas {
    private static final long serialVersionUID = -2139314540043885618L;

    @Override
    public void update(Graphics g) {
	Image next = createImage(getWidth(), getHeight());
	Graphics nextGraphics = next.getGraphics();
	nextGraphics.setColor(getBackground());
	nextGraphics.fillRect(0, 0, getWidth(), getHeight());
	nextGraphics.setColor(getForeground());
	paint(nextGraphics);
	g.drawImage(next, 0, 0, this);
    }
}
