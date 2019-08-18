package com.tygr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.tygr.Visualization.Connection;
import com.tygr.Visualization.ElementLayoutHandler;
import com.tygr.Visualization.Neural;
import com.tygr.Visualization.VisualizationPanel;
import com.tygr.config.ImportConnections;
import com.tygr.config.ImportNodes;

public class MainUI extends JFrame {

	private final int DISPLAY_SIZE_WIDTH = 960;
	private final int DISPLAY_SIZE_HEIGHT = 720;

	private Container contentPane;
	private VisualizationPanel visualizationPanel;

	private JMenuBar menuBar;
	private JMenu menufile;
	private JMenuItem menuItemSave;

	public MainUI() {
		initComponents();
		initEventListeners();
		setVisible(true);
	}

	private void initComponents() {
		// TODO Auto-generated method stub
		init_basic_panel_info();
		init_menu_bar_all_item();

		init_visualization_nn_context();
	}

	private void init_basic_panel_info() {
		setSize(DISPLAY_SIZE_WIDTH, DISPLAY_SIZE_HEIGHT);
		setTitle("Lung-Yu,Tsai,Paper Network Architecture");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = getContentPane();
		contentPane.setBackground(Color.gray); // 将JFrame实例背景设置为蓝绿色
	}

	private void init_menu_bar_all_item() {
		init_menu_bar();

		init_menu();
		init_file_menu_item();

		add_all_menu_to_menu_bar();

		setJMenuBar(menuBar);
	}

	private void add_all_menu_to_menu_bar() {
		menuBar.add(menufile);
	}

	private void init_menu_bar() {
		menuBar = new JMenuBar(); // 建立選單列
	}

	private void init_menu() {
		// set選單
		menufile = new JMenu("File");
	}

	private void init_file_menu_item() {
		menuItemSave = new JMenuItem("Save Image");

		menufile.add(menuItemSave);
	}

	private void init_visualization_nn_context() {
		visualizationPanel = new VisualizationPanel(DISPLAY_SIZE_WIDTH, DISPLAY_SIZE_HEIGHT);
		contentPane.add(visualizationPanel);

		display_configs(visualizationPanel);
	}

	private void saveImage() {
		try {
			if (visualizationPanel == null) {
				System.err.println("vis panel is null");
			}
			visualizationPanel.saveImage();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void initEventListeners() {
		// TODO Auto-generated method stub
		menuItemSave.addActionListener(new ActionListener() { // 選單 - 儲存資料庫事件傾聽
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveImage();
			};
		});
	}

	private void display_configs(VisualizationPanel panel) {
		String nodes_config_path = "nodes.txt";
		String connect_config_path = "connections.txt";
		List<Neural> nodes;
		try {
			nodes = new ImportNodes(nodes_config_path).getNodes();
			List<Connection> connections = new ImportConnections(connect_config_path, nodes).getConnections();

			ElementLayoutHandler handler = new ElementLayoutHandler(DISPLAY_SIZE_WIDTH, DISPLAY_SIZE_HEIGHT);
			handler.setNodes(nodes);
			handler.setConnects(connections);
			handler.setVisualizationPanel(panel);

			handler.display_all();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new MainUI();
			}
		});

	}
}
