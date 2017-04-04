package cwx.view.panels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;

import cwx.controller.Controller;
import cwx.util.ImageUtil;
import cwx.view.ViewPanel;

public class MapPanel extends ViewPanel {

	private static ArrayList<Alien> aliens;
	private static final BufferedImage alienImage = ImageUtil.loadBufferedImageFromFile("assets/images/alien.png"), mapImage = ImageUtil.loadBufferedImageFromFile("assets/images/us_map.png");

	/**
	 * A class to store the centres of the states as well as the scale of the states
	 */
	private class Alien {
		private int x, y;
		private float scale;
		private String state;

		/**
		 * Initialises the Alien object
		 * @param state The state the Alien is a part of
		 * @param size The size of the Alien
		 * @param x The x position of the Alien
		 * @param y The y position of the Alien
		 */
		public Alien(String state, float size, int x, int y) {
			this.x = x;
			this.y = y;
			this.state = state;
			scale = size / 2f + 0.2f;
		}
	}

	/**
	 * Updates the Panel
	 */
	@Override
	public void update(Observable observable, Object argument) {

	}

	/**
	 * Initialises the Panel
	 */
	@Override
	public void init(Controller controller) {
		addMouseListener(controller);
		
		aliens = new ArrayList<Alien>();

		float max = (float) controller.getMaxStateCount();
		
		aliens.add(new Alien("AL", (float) controller.getStateCount("AL") / max, 1225, 560));
		aliens.add(new Alien("AK", (float) controller.getStateCount("AK") / max, 265, 690));
		aliens.add(new Alien("AZ", (float) controller.getStateCount("AZ") / max, 555, 490));
		aliens.add(new Alien("AR", (float) controller.getStateCount("AR") / max, 1070, 570));
		aliens.add(new Alien("CA", (float) controller.getStateCount("CA") / max, 420, 510));
		aliens.add(new Alien("CO", (float) controller.getStateCount("CO") / max, 730, 360));
		aliens.add(new Alien("CT", (float) controller.getStateCount("CT") / max, 1535, 245));
		aliens.add(new Alien("DE", (float) controller.getStateCount("DE") / max, 1495, 350));
		aliens.add(new Alien("FL", (float) controller.getStateCount("FL") / max, 1400, 770));
		aliens.add(new Alien("GA", (float) controller.getStateCount("GA") / max, 1305, 560));
		aliens.add(new Alien("HI", (float) controller.getStateCount("HI") / max, 655, 895));
		aliens.add(new Alien("ID", (float) controller.getStateCount("ID") / max, 570, 250));
		aliens.add(new Alien("IL", (float) controller.getStateCount("IL") / max, 1145, 320));
		aliens.add(new Alien("IN", (float) controller.getStateCount("IN") / max, 1220, 390));
		aliens.add(new Alien("IA", (float) controller.getStateCount("IA") / max, 1030, 330));
		aliens.add(new Alien("KS", (float) controller.getStateCount("KS") / max, 970, 425));
		aliens.add(new Alien("KY", (float) controller.getStateCount("KY") / max, 1275, 420));
		aliens.add(new Alien("LA", (float) controller.getStateCount("LA") / max, 1070, 640));
		aliens.add(new Alien("ME", (float) controller.getStateCount("ME") / max, 1580, 75));
		aliens.add(new Alien("MD", (float) controller.getStateCount("MD") / max, 1490, 380));
		aliens.add(new Alien("MA", (float) controller.getStateCount("MA") / max, 1560, 215));
		aliens.add(new Alien("MI", (float) controller.getStateCount("MI") / max, 1230, 210));
		aliens.add(new Alien("MN", (float) controller.getStateCount("MN") / max, 1000, 190));
		aliens.add(new Alien("MS", (float) controller.getStateCount("MS") / max, 1140, 600));
		aliens.add(new Alien("MO", (float) controller.getStateCount("MO") / max, 1045, 390));
		aliens.add(new Alien("MT", (float) controller.getStateCount("MT") / max, 740, 150));
		aliens.add(new Alien("NE", (float) controller.getStateCount("NE") / max, 810, 310));
		aliens.add(new Alien("NV", (float) controller.getStateCount("NV") / max, 465, 400));
		aliens.add(new Alien("NH", (float) controller.getStateCount("NH") / max, 1545, 180));
		aliens.add(new Alien("NJ", (float) controller.getStateCount("NJ") / max, 1505, 310));
		aliens.add(new Alien("NM", (float) controller.getStateCount("NM") / max, 700, 590));
		aliens.add(new Alien("NY", (float) controller.getStateCount("NY") / max, 1450, 190));
		aliens.add(new Alien("NC", (float) controller.getStateCount("NC") / max, 1500, 475));
		aliens.add(new Alien("ND", (float) controller.getStateCount("ND") / max, 920, 120));
		aliens.add(new Alien("OH", (float) controller.getStateCount("OH") / max, 1295, 370));
		aliens.add(new Alien("OK", (float) controller.getStateCount("OK") / max, 950, 555));
		aliens.add(new Alien("OR", (float) controller.getStateCount("OR") / max, 425, 215));
		aliens.add(new Alien("PA", (float) controller.getStateCount("PA") / max, 1385, 325));
		aliens.add(new Alien("RI", (float) controller.getStateCount("RI") / max, 1560, 240));
		aliens.add(new Alien("SC", (float) controller.getStateCount("SC") / max, 1410, 590));
		aliens.add(new Alien("SD", (float) controller.getStateCount("SD") / max, 930, 230));
		aliens.add(new Alien("TN", (float) controller.getStateCount("TN") / max, 1285, 485));
		aliens.add(new Alien("TX", (float) controller.getStateCount("TX") / max, 905, 740));
		aliens.add(new Alien("UT", (float) controller.getStateCount("UT") / max, 600, 415));
		aliens.add(new Alien("VT", (float) controller.getStateCount("VT") / max, 1515, 165));
		aliens.add(new Alien("VA", (float) controller.getStateCount("VA") / max, 1430, 390));
		aliens.add(new Alien("WA", (float) controller.getStateCount("WA") / max, 415, 30));
		aliens.add(new Alien("WV", (float) controller.getStateCount("WV") / max, 1350, 415));
		aliens.add(new Alien("WI", (float) controller.getStateCount("WI") / max, 1125, 250));
		aliens.add(new Alien("WY", (float) controller.getStateCount("WY") / max, 705, 300));
	}

	/**
	 * Closes the panel
	 */
	@Override
	public void close() {

	}

	/**
	 * Paints the panel
	 * @param g The graphics object
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(mapImage, 0, 0, getWidth(), getHeight(), null);

		for (Alien alien : aliens) {
			if (alienImage != null)
				g.drawImage(alienImage, (int) ((alien.x * ((float) getWidth() / (float) mapImage.getWidth())) - (alienImage.getWidth(null) * alien.scale / 2f)), (int) ((alien.y * ((float) getHeight() / (float) mapImage.getHeight())) - (alienImage.getHeight(null) * alien.scale / 2f)), (int) ((float) alienImage.getWidth(null) * alien.scale), (int) ((float) alienImage.getHeight(null) * alien.scale), null);
		}
	}

	/**
	 * Returns the state at the specified location
	 * @param x The x location
	 * @param y The y location
	 * @return The state at the specified locations
	 */
	public String getStateAt(int x, int y) {
		for (Alien alien : aliens) {
			int alienX = (int) (alien.x * ((float) getWidth() / (float) mapImage.getWidth())), alienY = (int) (alien.y * ((float) getHeight() / (float) mapImage.getHeight()));
			int alienSize = (int) ((float) alienImage.getWidth(null) * alien.scale);
			if (x > alienX - alienSize / 2 && x < alienX + alienSize / 2) {
				if (y > alienY - alienSize / 2 && y < alienY + alienSize / 2) {
					return alien.state;
				}
			}
		}
		return null;
	}
}
