package cwx.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import cwx.controller.Controller;

public class StateWindow extends JFrame {

	private ArrayList<String> lines;
	private DefaultListModel<String> listModel;
	private String stateName;
	private Controller controller;
	
	/**
	 * Initialises the StateWindow object
	 * @param stateName
	 * @param lines
	 * @param controller
	 */
	public StateWindow(String stateName, ArrayList<String> lines, Controller controller) {
		this.lines = lines;
		this.stateName = stateName;
		this.controller = controller;
		
		setTitle(stateName);
		setPreferredSize(new Dimension(800, 400));
		
		initGUI(controller);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Initialises the GUI of the Frame
	 * @param controller The Controller
	 */
	private void initGUI(Controller controller) {
		setLayout(new BorderLayout());
		
		JComboBox<String> combo = new JComboBox<String>();
		
		combo.addItem("--");
		combo.addItem("Date");
		combo.addItem("City");
		combo.addItem("Shape");
		combo.addItem("Duration");
		combo.addItem("Posted");
		
		
		combo.addItemListener(controller);
		combo.setName("sort " + stateName);
		
		combo.setSelectedItem("--");
		
		add(combo, BorderLayout.NORTH);
		
		listModel = new DefaultListModel<String>();
		JList<String> list = new JList<String>(listModel);
		list.addListSelectionListener(controller);
		
		for (String line : lines) {
			listModel.addElement(controller.getIncidentInfo(line));
		}
		
		JScrollPane pane = new JScrollPane(list);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(pane, BorderLayout.CENTER);
	}
	
	/**
	 * Updates the lines with the sorted lines
	 * @param lines The sorted lines
	 */
	public void updateSortedLines(ArrayList<String> lines) {
		listModel.removeAllElements();
		for (String line : lines) {
			listModel.addElement(controller.getIncidentInfo(line));
		}
	}
}
