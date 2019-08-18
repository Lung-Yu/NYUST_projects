package com.tygr.Visualization;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class VisualizationPanel extends JPanel {

	private int width = 960;
	private int height = 960;
	private BufferedImage paintImage;

	private List<IElement> list;

	public VisualizationPanel() {
		list = new LinkedList<IElement>();
		setBackground(Color.DARK_GRAY);

		paintImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_3BYTE_BGR);
	}

	public VisualizationPanel(int width, int height) {

		list = new LinkedList<IElement>();
		setBackground(Color.DARK_GRAY);

		this.width = width;
		this.height = height;

		paintImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_3BYTE_BGR);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(paintImage, 0, 0, null);
	}

	public void addElement(IElement element) {
		list.add(element);
	}

	@Override
	public void paint(Graphics graphics) {
		// TODO Auto-generated method stub
		super.paint(graphics);
		for (IElement element : list)
			element.point_self(graphics);
	}

	public void saveImage() throws IOException {
		BufferedImage imagebuf = null;
		try {
			imagebuf = new Robot().createScreenCapture(bounds());
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Graphics2D graphics2D = imagebuf.createGraphics();
		paint(graphics2D);
		try {
			ImageIO.write(imagebuf, "jpeg", new File("nn.jpeg"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("error");
		}
	}

}
