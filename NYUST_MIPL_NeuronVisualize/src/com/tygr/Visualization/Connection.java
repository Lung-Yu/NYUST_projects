package com.tygr.Visualization;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Connection implements IElement{

	private Neural input_node;
	private Neural output_node;
	private double weight = 0;
	
	public Connection(Neural input,Neural output) {
		this.input_node = input;
		this.output_node = output;
	}
	
	public Connection(Neural input,Neural output,double weight) {
		this.input_node = input;
		this.output_node = output;
		this.weight = weight;
	}
	
	public void node_move() {
		
	}
	
	public int getInputNodeLayer() {
		return this.input_node.getCurrentLayer();
	}
	
	public int getOutputNodeLayer() {
		return this.output_node.getCurrentLayer();
	}
	
	public void updateOutputNodeLayer(int l) {
		output_node.setCurrentLayer(l);
	}
	
	public ENeurlType getInputNodeType() {
		return this.input_node.getType();
	}
	
	public ENeurlType getOutputNodeType() {
		return this.output_node.getType();
	}
	
	public int getOutputNodeNameId() {
		return this.output_node.getNameId();
	}
	
	@Override
	public void point_self(Graphics graphics) {
		// TODO Auto-generated method stub
		graphics.setColor(Color.WHITE);
		graphics.drawLine(input_node.getX(),input_node.getY(),
				output_node.getX(),output_node.getY());
		int x_center = (input_node.getX() + output_node.getX()) / 2;
		int y_center = (input_node.getY() + output_node.getY()) / 2;
		
		graphics.setColor(Color.WHITE);// 設置畫筆顏色
		graphics.drawString(weight+"", x_center, y_center);
	}
}
