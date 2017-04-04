package cwx.view.panels.game.main;

public abstract class PartAction {

	protected PuzzleCube cube;
	protected Part part;
	
	/**
	 * Initialises the PartAction object
	 * @param cube The cube the PartAction is a part of
	 */
	public PartAction(PuzzleCube cube) {
		this.cube = cube;
	}
	
	/**
	 * Sets the Part the PartAction is for
	 * @param part The Part the PartAction is for
	 */
	protected void setPart(Part part) {
		this.part = part;
	}
	
	/**
	 * The abstract method of whether the PartAction should trigger or not
	 * @return Whether the PartAction should trigger or not
	 */
	public abstract boolean shouldTrigger();
	
	/**
	 * The abstract method of what the PartAction should do on triggering
	 */
	public abstract void triggered();
}
