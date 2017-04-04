package cwx.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import api.ripley.Ripley;
import cwx.model.Model;
import cwx.view.View;
import cwx.view.ViewPanel;
import cwx.view.panels.MapPanel;

public class Controller implements ActionListener, ItemListener, MouseListener, ListSelectionListener {

	private Model model;
	private View view;

	/**
	 * Initialises the Controller
	 * @param model The Model
	 */
	public Controller(Model model) {
		this.model = model;
	}

	/**
	 * Sets the View
	 * @param view The View
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * Returns the Start Year
	 * @return The start year
	 */
	public int getStartYear() {
		return model.getStartYear();
	}

	/**
	 * Returns the End Year
	 * @return The end year
	 */
	public int getEndYear() {
		return model.getEndYear();
	}
	
	/**
	 * Returns the Ripley object
	 * @return The Ripley object
	 */
	public Ripley getRipley() {
		return model.getRipley();
	}
	
	/**
	 * Returns the count of incidents for the specified state
	 * @param state The state to check
	 * @return The number of incidents for the state
	 */
	public int getStateCount(String state) {
		return model.getStateCount(state);
	}

	/**
	 * Returns the title of the specified statistic
	 * @param id The id of the statistic
	 * @return The title of the statistic
	 */
	public String getStatisticTitle(int id) {
		return model.getStatisticTitle(id);
	}

	/**
	 * Returns the value of the specified statistic
	 * @param id The id of the statistic
	 * @return The value of the statistic
	 */
	public String getStatistic(int id) {
		return model.getStatistic(id);
	}

	/**
	 * Returns the array of displayed statistics
	 * @return The statistics currently being displayed
	 */
	public int[] getDisplayedStatistics() {
		return model.getStatistics();
	}

	/**
	 * Saves the statistics to a file
	 */
	public void saveStatistics() {
		model.saveStatisticsToFile();
	}
	
	/**
	 * Enables or disables the Panel buttons depending on the flag
	 * @param state Whether the buttons should be enabled or disabled
	 */
	public void enableButtons(boolean state) {
		view.enableButtons(state);
	}
	
	/**
	 * The Buttons action listener
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("left button")) {
			view.shouldIncrementPanelIndex(false);
		} else if (e.getActionCommand().equalsIgnoreCase("right button")) {
			view.shouldIncrementPanelIndex(true);
		} else if (e.getActionCommand().toLowerCase().contains("right statistic")) {
			model.swapStatistic(Integer.valueOf("" + e.getActionCommand().charAt(e.getActionCommand().length() - 1)), false);
		} else if (e.getActionCommand().toLowerCase().contains("left statistic")) {
			model.swapStatistic(Integer.valueOf("" + e.getActionCommand().charAt(e.getActionCommand().length() - 1)), true);
		} else if (e.getActionCommand().equalsIgnoreCase("select dates")) {
			model.updateDates();
		}
	}
	
	/**
	 * Returns the from year
	 * @return The from year
	 */
	public int getFromYear() {
		return model.getFromYear();
	}
	
	/**
	 * Returns the to year
	 * @return The to year
	 */
	public int getToYear() {
		return model.getToYear();
	}

	/**
	 * ComboBox Item Listener
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void itemStateChanged(ItemEvent e) {
		JComboBox<Integer> comboBox = (JComboBox<Integer>) e.getSource();

		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (comboBox.getName().equalsIgnoreCase("from")) {
				model.setStartOfRange((int) comboBox.getSelectedItem());
			} else if (comboBox.getName().equalsIgnoreCase("to")) {
				model.setEndOfRange((int) comboBox.getSelectedItem());
			} else if (comboBox.getName().toLowerCase().contains("sort")) {
				model.displaySortedStateInfo(comboBox.getName().substring(5), (String) comboBox.getSelectedItem());
			}
		}
	}

	/**
	 * When the mouse is clicked
	 */
	@Override
	public void mouseClicked(MouseEvent e) {}

	/**
	 * When the mouse is pressed
	 */
	@Override
	public void mousePressed(MouseEvent e) {}

	/**
	 * When the mouse is released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		ViewPanel panel = view.getCurrentPanel();
		if (panel instanceof MapPanel) {
			MapPanel map = (MapPanel) panel;
			String state = map.getStateAt(e.getX(), e.getY());
			if (state != null) {
				model.displayStateInfo(state);
			}
		}
	}

	/**
	 * When the mouse enters the screen
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}

	/**
	 * When the mouse exits the screen
	 */
	@Override
	public void mouseExited(MouseEvent e) {}

	/**
	 * Returns the max number of incidents for a state
	 * @return The max number of incidents
	 */
	public int getMaxStateCount() {
		return model.getMaxStateCount();
	}
	
	/**
	 * Returns the information about the specified incident
	 * @param incidentID The incident to check
	 * @return The information about the incident
	 */
	public String getIncidentInfo(String incidentID) {
		return model.getIncidentInfo(incidentID);
	}

	/**
	 * JList Item Listener
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		model.displayIncidentDetailsFromText(((JList<String>) e.getSource()).getSelectedValue());
	}
}
