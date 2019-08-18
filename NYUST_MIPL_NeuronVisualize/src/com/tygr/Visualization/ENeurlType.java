package com.tygr.Visualization;

public enum ENeurlType {
	INPUT_NODE,HIDDEN_NODE,OUTPUT_NODE,ERROR;
	
	public static boolean IsInputNode(ENeurlType type) {
		if (type == INPUT_NODE)
			return true;
		else 
			return false;
	}
	
	public static boolean IsOutputNode(ENeurlType type) {
		if(type == OUTPUT_NODE) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public static boolean IsHiddenNode(ENeurlType type) {
		if(type == ENeurlType.HIDDEN_NODE) {
			return true;
		}else {
			return false;
		}
	}
	
}
