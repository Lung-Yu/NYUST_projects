package com.tygr.Visualization;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.ldap.SortControl;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.PortableInterceptor.IORInterceptor;

public class ElementLayoutHandler {

	private List<Neural> node_list;
	private List<Connection> connect_list;
	private VisualizationPanel panel;

	List<Neural> in_nodes;
	List<Neural> hidden_nodes;
	List<Neural> out_nodes;

	private final int UNIT = 10;
	private int weight;
	private int height;

	private int hidden_max_layer_size = 0;

	public ElementLayoutHandler(int w, int h) {
		this.weight = w;
		this.height = h;
		in_nodes = new ArrayList();
		hidden_nodes = new LinkedList<>();
		out_nodes = new ArrayList();
	}

	private static Comparator<Neural> comparator = new Comparator<Neural>() {
		@Override
		public int compare(Neural o1, Neural o2) {
			// TODO Auto-generated method stub
			return o1.getNameId() - o2.getNameId();
		}
	};

	public void splitNodes() {
		for (Neural neural : node_list) {

			switch (neural.getType()) {
			case INPUT_NODE:
				in_nodes.add(neural);
				break;
			case HIDDEN_NODE:
				hidden_nodes.add(neural);
				break;
			case OUTPUT_NODE:
				out_nodes.add(neural);
				break;
			default:
				break;
			}
		}
	}

	public void inputs() {
		// int half = in_nodes.size() / 2;
		int y = height - height / UNIT;
		int x_dif = (int) (weight / (in_nodes.size() + 1));
		int start_x = x_dif;

		in_nodes.sort(comparator);

		for (Neural neural : in_nodes) {
			neural.setX(start_x);
			neural.setY(y);

			start_x += x_dif;
		}

	}

	public void outputs() {
		// int half = out_nodes.size() / 2;
		int y = height / UNIT;

		int x_dif = (int) (weight / (out_nodes.size() + 1));
		int start_x = x_dif;

		in_nodes.sort(comparator);

		for (Neural neural : out_nodes) {
			neural.setX(start_x);
			neural.setY(y);

			start_x += x_dif;
		}
	}

	public void hiddens() {
		calc_nodes_layer();
		hidden_nodes.sort(comparator);

		int temp_layer = 0;
		int y_range = height - 2 * UNIT;
		int y_unit = y_range / (hidden_max_layer_size + 1);
		int start_y = (height -(height / UNIT));

		int x_dif = calc_current_hidden_layer_x_dif(temp_layer);
		int start_x = x_dif;
		
		for (Neural neural : hidden_nodes) {

			if (temp_layer != neural.getCurrentLayer()) {
				temp_layer = neural.getCurrentLayer();
				start_y -= y_unit;
				
				x_dif = calc_current_hidden_layer_x_dif(temp_layer);
				start_x = x_dif;
			}else {
				start_x += x_dif;	
			}

			neural.setX(start_x);
			neural.setY(start_y);	
		}
	}

	private int calc_current_hidden_layer_x_dif(int l) {
		int x_dif = (int) (weight / (get_current_hidden_layer_size(l) + 1));
		return x_dif;
	}

	private int get_current_hidden_layer_size(int l) {
		int count = 0;
		for (Neural e : hidden_nodes) {
			if (e.getCurrentLayer() == l)
				count++;
		}
		return count;
	}

	public void display_all() {
		inputs();
		outputs();
		hiddens();

		System.err.println("max_hidden_layer_size::" + hidden_max_layer_size);

		for (IElement element : connect_list) {
			panel.addElement(element);
			System.out.println(((Connection) element).getOutputNodeNameId() + "****"
					+ ((Connection) element).getOutputNodeLayer() + "***" + ((Connection) element).getOutputNodeType());
		}

		for (IElement element : node_list) {
			panel.addElement(element);
		}
	}

	private void calc_nodes_layer() {

		for (Connection item : connect_list) {
			int ilayer = item.getInputNodeLayer();
			int olayer = item.getOutputNodeLayer();

			if (ENeurlType.IsOutputNode(item.getOutputNodeType()))
				continue;

			if (ilayer >= olayer) {
				olayer = ilayer + 1;
				item.updateOutputNodeLayer(olayer);
				update_hidden_max_layer_size(olayer);
			}
			
		}

	}

	private void update_hidden_max_layer_size(int current_outlayer) {
		if (current_outlayer > hidden_max_layer_size)
			hidden_max_layer_size = current_outlayer;
	}

	public void setNodes(List<Neural> nodes) {
		this.node_list = nodes;
		splitNodes();
	}

	public void setConnects(List<Connection> connections) {
		this.connect_list = connections;
	}

	public void setVisualizationPanel(VisualizationPanel panel) {
		this.panel = panel;
	}

}
