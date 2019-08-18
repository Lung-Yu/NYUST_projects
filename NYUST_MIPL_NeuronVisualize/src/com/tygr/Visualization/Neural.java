package com.tygr.Visualization;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Neural implements IElement{

	// display setting	
	private int center_x = 300;
	private int center_y = 300;
	private final NeuralSettingInterpreter visualizationSetting;
	private List<Connection> views;

	//cell setting
	private int node_name;
	public Neural(int name,ENeurlType type) {
		visualizationSetting = new NeuralSettingInterpreter(type);
		views = new LinkedList<Connection>();
		this.node_name = name;
	}
	
	public Neural(int x,int y,int name ,ENeurlType type) {
		this(name,type);
		this.center_x = x;
		this.center_y = y;
	}
	

	public void attach(Connection connection) {
		views.add(connection);
	}
	
	public void notify_position_update() {
		for(Connection connect : views) {
			connect.node_move();
		}
	}
	
	@Override
	public void point_self(Graphics graphics) {
		// TODO Auto-generated method stub
		int x = center_x -  visualizationSetting.getRadius() / 2;
		int y = center_y -  visualizationSetting.getRadius() / 2;
		graphics.setColor(visualizationSetting.getNode_color());// 設置畫筆顏色
		graphics.fillOval(x, y, visualizationSetting.getRadius(), visualizationSetting.getRadius());// 畫直徑為20的圓
		
		graphics.setColor(Color.WHITE);// 設置畫筆顏色
		//graphics.drawString(visualizationSetting.getNodeTypeString() + this.node_name, x, x);
		graphics.drawString(this.node_name+"", x, y);
	}
	
	public int getNameId() {
		return this.node_name;
	}
	
	public ENeurlType getType() {
		return visualizationSetting.getNode_type();
	}
	
	public void setX(int x) {
		this.center_x = x;
	}
	
	public int getX() {
		return center_x;
	}
	
	public int getRadius() {
		return visualizationSetting.getRadius();
	}
	public void setY(int y) {
		this.center_y = y;
	}
	public int getY() {
		return center_y;
	}

	public int getCurrentLayer() {
		return visualizationSetting.getLayer();
	}
	
	public void setCurrentLayer(int layer) {
		this.visualizationSetting.setLayer(layer);
	}
	
}