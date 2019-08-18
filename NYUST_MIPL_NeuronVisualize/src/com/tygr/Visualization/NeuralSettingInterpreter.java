package com.tygr.Visualization;

import java.awt.Color;

public class NeuralSettingInterpreter {

	private Color node_color;
	private ENeurlType node_type;
	private String node_type_str;
	private int radius = 20;
	
	private int layer;
	
	
	public NeuralSettingInterpreter(ENeurlType type) {
		this.setNode_type(type);
		
		node_type_seeting();
	}
	
	public String getNodeTypeString() {
		return node_type_str;
	}
	private void node_type_seeting() {
		switch (getNode_type()) {
		case INPUT_NODE:
			input_node_setting();
			break;
		case HIDDEN_NODE:
			hidden_node_setting();
			break;
		case OUTPUT_NODE:
			output_node_setting();
			break;
		default:
			none_node_type_setting();
			break;
		}
	}
	
	private void input_node_setting() {
		// 	#A6FFA6
		node_color = new Color(166, 250, 166);
		node_type_str = "I.";
		layer = 0;
		setRadius(20);
	}
	
	private void hidden_node_setting() {
		//#9D9D9D
		node_color = new Color(157, 157, 157);
		node_type_str = "H.";
		layer = 1;
		setRadius(20);
	}
	
	private void output_node_setting() {
		//#8F4586
		node_color = new Color(143, 69, 134);
		node_type_str = "O.";
		layer = -1;
		setRadius(20);
	}
	
	private void none_node_type_setting() {
		
	}
	
	public Color getNode_color() {
		return node_color;
	}
	public void setNode_color(Color node_color) {
		this.node_color = node_color;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public ENeurlType getNode_type() {
		return node_type;
	}

	private void setNode_type(ENeurlType node_type) {
		this.node_type = node_type;
	}	
	
	public int getLayer() {
		return layer;
	}
	
	public void setLayer(int l) {
		this.layer = l;
	}
}
