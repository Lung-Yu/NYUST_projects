package com.tygr.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.tygr.Visualization.Connection;
import com.tygr.Visualization.ENeurlType;
import com.tygr.Visualization.Neural;

public class ImportConnections {
	private List<Connection> connect_list = new LinkedList<Connection>();
	private List<Neural> nodes;

	public ImportConnections(String filepath, List<Neural> nodes) throws IOException {
		this.nodes = nodes;

		readFile(filepath);
	}

	private void readFile(String filepath) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(filepath);
		BufferedReader br = new BufferedReader(fr);

		while (br.ready()) {
			String line = br.readLine();
			String[] data = line.split(" ");

			int in = Integer.parseInt(data[0]);
			int out = Integer.parseInt(data[1]);
			double weight = Double.parseDouble(data[2]);

			Neural in_node = findNeural(in);
			Neural out_node = findNeural(out);

			Connection connection = new Connection(in_node, out_node, weight);

			System.out.println("No." + in_node.getNameId() + "--(" + weight + ")-->" + "No." + out_node.getNameId());
			this.connect_list.add(connection);
		}
		fr.close();
	}

	public List<Connection> getConnections() {
		return this.connect_list;
	}

	private Neural findNeural(int id) {
		for (Neural node : this.nodes) {
			if (node.getNameId() == id)
				return node;
		}

		return null;
	}

}
