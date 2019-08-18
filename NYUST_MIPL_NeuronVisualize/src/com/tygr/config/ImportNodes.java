package com.tygr.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.tygr.Visualization.ENeurlType;
import com.tygr.Visualization.Neural;

public class ImportNodes {
	private List<Neural> node_list = new LinkedList<Neural>();

	public ImportNodes(String filepath) throws IOException {
		FileReader fr = new FileReader(filepath);
		BufferedReader br = new BufferedReader(fr);

		while (br.ready()) {
			ENeurlType type;
			int neural_name;

			String line = br.readLine();
			String[] data = line.split(" ");		

			neural_name = Integer.parseInt(data[0]);
			type = convert(Integer.parseInt(data[1]));
			Neural neural = new Neural(neural_name, type);
			node_list.add(neural);

		}
		fr.close();
	}
	
	public List<Neural> getNodes(){
		return this.node_list;
	}

	private ENeurlType convert(int no) {
		switch (no) {
		case 0:
			return ENeurlType.INPUT_NODE;
		case 1:
			return ENeurlType.HIDDEN_NODE;
		case 2:
			return ENeurlType.OUTPUT_NODE;
		default:
			return ENeurlType.ERROR;
		}
	}
}
