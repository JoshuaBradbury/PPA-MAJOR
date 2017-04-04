package cwx.view.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cwx.controller.Controller;
import cwx.view.ViewPanel;

public class StatisticsPanel extends ViewPanel {
	
	private JLabel[] statisticNames, statisticText;
	
	/**
	 * Updates the Panel
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			String str = (String) arg;
			if (str.toLowerCase().contains("swap statistic")) {
				String[] parts = str.split(" ");
				int index = Integer.valueOf(parts[2]), to = Integer.valueOf(parts[3]);
				statisticNames[index].setText(controller.getStatisticTitle(to) + ":");
				statisticText[index].setText(controller.getStatistic(to));
			}
		}
	}
	
	private Controller controller;

	/**
	 * Initialises the Panel
	 */
	@Override
	public void init(Controller controller) {
		this.controller = controller;
		
		setLayout(new GridLayout(2, 2));
		
		int[] ids = controller.getDisplayedStatistics();
		
		statisticNames = new JLabel[4];
		statisticText = new JLabel[4];
		
		for (int id : ids)
			addStatisticSlot(id);
	}
	
	private int counter = 0;
	
	/**
	 * Adds the statistic panel for the specified statistics
	 * @param id The statistic to add
	 */
	public void addStatisticSlot(int id) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		statisticNames[counter] = new JLabel(controller.getStatisticTitle(id) + ":");
		
		panel.add(statisticNames[counter], BorderLayout.NORTH);
		
		JButton leftButton = new JButton("<");
		leftButton.addActionListener(controller);
		leftButton.setActionCommand("left statistic " + counter);
		
		panel.add(leftButton, BorderLayout.WEST);
		
		JButton rightButton = new JButton(">");
		rightButton.addActionListener(controller);
		rightButton.setActionCommand("right statistic " + counter);
		
		panel.add(rightButton, BorderLayout.EAST);
		
		statisticText[counter] = new JLabel(controller.getStatistic(id));
		statisticText[counter].setHorizontalAlignment(SwingConstants.CENTER);
		
		panel.add(statisticText[counter], BorderLayout.CENTER);
		
		add(panel);
		
		counter++;
	}

	/**
	 * Closes the screen
	 */
	@Override
	public void close() {
		controller.saveStatistics();
	}
}
