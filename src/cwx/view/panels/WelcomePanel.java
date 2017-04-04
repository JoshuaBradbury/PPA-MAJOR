package cwx.view.panels;

import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cwx.controller.Controller;
import cwx.view.ViewPanel;

public class WelcomePanel extends ViewPanel {
	private static JLabel dateLabel, grabLabel, dataGrabbed, pleaseLabel;

	private Controller controller;

	static {
		dateLabel = new JLabel();
		dateLabel.setHorizontalAlignment(JLabel.CENTER);
		grabLabel = new JLabel();
		grabLabel.setHorizontalAlignment(JLabel.CENTER);
		dataGrabbed = new JLabel();
		dataGrabbed.setHorizontalAlignment(JLabel.CENTER);
		pleaseLabel = new JLabel();
		pleaseLabel.setHorizontalAlignment(JLabel.CENTER);
	}
	
	@Override
	public void update(Observable observable, Object argument) {
		String string = (String) argument;
		if (string.equalsIgnoreCase("reset")) {
			dateLabel.setText("Date range selected: " + controller.getFromYear() + " - " + controller.getToYear());
			grabLabel.setText("Grabbing data...");
			dataGrabbed.setText("");
			pleaseLabel.setText("");
		} else if (string.toLowerCase().contains("update")) {
			String[] parts = string.split(" ");

			long millis = Long.valueOf(parts[parts.length - 1]);

			int minutes = (int) (millis / 60000), seconds = (int) ((millis / 1000) % 60);

			dataGrabbed.setText("Data grabbed in " + minutes + " minute, " + seconds + " seconds");
			pleaseLabel.setText("Please now interact with this data using the buttons to the left and the right");

			controller.enableButtons(true);
		}
	}

	@Override
	public void init(Controller controller) {
		this.controller = controller;
		JPanel panel = new JPanel();
		add(panel);

		panel.setLayout(new GridLayout(5, 1));
		
		JLabel welcomeInfo = new JLabel("<html><div style: 'text-align: center;'>Welcome to the Ripley API v " + controller.getRipley().getVersion() + "<br> Please select from the dates above,<br> in order to begin analysing UFO sighting data.</div></html>");
		welcomeInfo.setHorizontalAlignment(JLabel.CENTER);
		welcomeInfo.setVerticalAlignment(JLabel.CENTER);
		
		panel.add(welcomeInfo);
		panel.add(dateLabel);
		panel.add(grabLabel);
		panel.add(dataGrabbed);
		panel.add(pleaseLabel);

	}

	@Override
	public void close() {

	}
}
