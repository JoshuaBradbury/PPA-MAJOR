package cwx.view;

import java.util.Observer;

import javax.swing.JPanel;

import cwx.controller.Controller;

/**
 * This class acts as a super class to all the panels so there can be common methods while having access to the View
 */
public abstract class ViewPanel extends JPanel implements Observer {

	private View view;
	
	/**
	 * Sets the View of the Panel
	 * @param view The View
	 */
	void setView(View view) {
		this.view = view;
	}
	
	/**
	 * Returns the View of the Panel
	 * @return The View
	 */
	protected View getView() {
		return view;
	}
	
	/**
	 * Initialises the Panel
	 * @param controller The Controller
	 */
	public abstract void init(Controller controller);

	/**
	 * Closes the Panel
	 */
	public abstract void close();
}
