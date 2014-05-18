package control.listeners;

import javax.swing.*;
import javax.swing.event.*;

import testClient.TestClient;

/**
 * Listener Class that is triggered by the Slider setting the reaction time of the Test Clients.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
public class ChangeReactionTimeListener implements ChangeListener {

	/**Slider where the simulated delay is set */
	private JSlider reactionTimeSlider;
	/**Label showing the current reaction Time*/
	private JLabel reactionTimeLabel;
	
	/**
	 * 
	 * @param reactionTimeSlider Slider where delay is set
	 * @param reactionTimeLabel
	 */
	public ChangeReactionTimeListener (JSlider reactionTime, JLabel reactionTimeLabel)
	{
		this.reactionTimeSlider = reactionTime;
		this.reactionTimeLabel = reactionTimeLabel;
	}
	
	/**
	 * Updates the delay of the Test Clients.
	 */
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		TestClient.setWaitForIt(reactionTimeSlider.getValue());
		reactionTimeLabel.setText("Reaction Time (ms): " + reactionTimeSlider.getValue());
	}

}
