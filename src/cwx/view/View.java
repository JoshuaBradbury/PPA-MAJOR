package cwx.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cwx.controller.Controller;
import cwx.view.panels.GamePanel;
import cwx.view.panels.MapPanel;
import cwx.view.panels.StatisticsPanel;
import cwx.view.panels.WelcomePanel;

public class View extends JFrame implements Observer {

	private ArrayList<Class<? extends ViewPanel>> panels;
	private Controller controller;
	private int panelIndex;
	private ViewPanel currPanel;
	private JPanel mainPanel;
	private JLabel label;
	private JComboBox<Integer> fromDates, toDates;
	private JButton selectDates, leftButton, rightButton;
	private StateWindow stateFrame;

	public View(Controller controller) {
		panels = new ArrayList<Class<? extends ViewPanel>>();

		panels.add(WelcomePanel.class);
		panels.add(MapPanel.class);
		panels.add(StatisticsPanel.class);
		panels.add(GamePanel.class);

		this.controller = controller;
		controller.setView(this);

		initGUI();
	}

	public void init() {
		panelIndex = -1;
		shouldIncrementPanelIndex(true);
	}

	public void resetPanels() {
		panelIndex = 0;
		if (currPanel != null) {
			currPanel.close();
			mainPanel.remove(currPanel);
			currPanel = null;
		}

		mainPanel.add(getCurrentPanel(), BorderLayout.CENTER);
		currPanel.revalidate();
		currPanel.repaint();

		enableButtons(false);
	}

	public void enableButtons(boolean state) {
		selectDates.setEnabled(state);
		leftButton.setEnabled(state);
		rightButton.setEnabled(state);
	}

	public void shouldIncrementPanelIndex(boolean increment) {
		if (currPanel != null) {
			currPanel.close();
			mainPanel.remove(currPanel);
			currPanel = null;
		}

		panelIndex = (panelIndex + (increment ? 1 : -1)) % panels.size();
		if (panelIndex < 0)
			panelIndex += panels.size();

		mainPanel.add(getCurrentPanel(), BorderLayout.CENTER);
		currPanel.revalidate();
		currPanel.repaint();
	}
	
	public ViewPanel getCurrentPanel() {
		if (currPanel == null) {
			try {
				currPanel = panels.get(panelIndex).newInstance();
				currPanel.setView(this);
				currPanel.init(controller);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return currPanel;
	}

	public void initGUI() {
		setPreferredSize(new Dimension(800, 600));
		setResizable(true);
		setTitle("PPA MAJOR - WATCH OUT FOR ALIENS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		add(mainPanel);

		JPanel dateRanges = new JPanel();
		dateRanges.setLayout(new BorderLayout());

		JPanel dates = new JPanel();

		fromDates = new JComboBox<Integer>();
		fromDates.setEditable(false);
		fromDates.addItemListener(controller);
		fromDates.setName("from");

		dates.add(new JLabel("From: "));
		dates.add(fromDates);

		toDates = new JComboBox<Integer>();
		toDates.setEditable(false);
		toDates.addItemListener(controller);
		toDates.setName("to");

		dates.add(new JLabel("To: "));
		dates.add(toDates);

		selectDates = new JButton("Select dates");
		selectDates.addActionListener(controller);
		selectDates.setActionCommand("select dates");

		dates.add(selectDates);

		dateRanges.add(dates, BorderLayout.EAST);

		mainPanel.add(dateRanges, BorderLayout.NORTH);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BorderLayout());

		mainPanel.add(buttons, BorderLayout.SOUTH);

		leftButton = new JButton("<");
		leftButton.addActionListener(controller);
		leftButton.setActionCommand("left button");
		buttons.add(leftButton, BorderLayout.WEST);

		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);

		buttons.add(label, BorderLayout.CENTER);

		rightButton = new JButton(">");
		rightButton.addActionListener(controller);
		rightButton.setActionCommand("right button");
		buttons.add(rightButton, BorderLayout.EAST);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				getCurrentPanel().close();
			}
		});

		enableButtons(false);
		selectDates.setEnabled(true);
	}

	public void setLabelText(String text) {
		label.setText(text);
	}

	@Override
	public void update(Observable observable, Object argument) {
		if (argument instanceof int[]) {
			int[] range = (int[]) argument;
			int start = range[0], end = range[1];

			fromDates.removeItemListener(controller);
			toDates.removeItemListener(controller);

			fromDates.removeAllItems();
			toDates.removeAllItems();

			for (int i = controller.getStartYear(); i <= end; i++)
				fromDates.addItem(i);

			for (int i = start; i <= controller.getEndYear(); i++)
				toDates.addItem(i);

			fromDates.setSelectedItem(start);
			toDates.setSelectedItem(end);

			fromDates.addItemListener(controller);
			toDates.addItemListener(controller);
			return;
		} else if (argument instanceof ArrayList) {
			ArrayList<String> text = (ArrayList<String>) argument;
			
			if (stateFrame == null) {
				String name = text.remove(0);
				stateFrame = new StateWindow(name, text, controller);
				stateFrame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						stateFrame = null;
					}
				});
			} else {
				stateFrame.updateSortedLines(text);
			}
		}

		if (argument instanceof String) {
			String str = (String) argument;

			if (str.toLowerCase().contains("texting")) {
				label.setText(str.replace("texting", "").trim());
				return;
			} else if (str.equalsIgnoreCase("reset")) {
				resetPanels();
			} else if (str.toLowerCase().contains("eve details")) {
				JOptionPane.showMessageDialog(this, str.replace("eve details", ""));
			}
		}

		getCurrentPanel().update(observable, argument);
	}

}
