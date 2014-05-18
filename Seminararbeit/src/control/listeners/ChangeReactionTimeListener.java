package control.listeners;

import javax.swing.*;
import javax.swing.event.*;

import testClient.TestClient;

public class ChangeReactionTimeListener implements ChangeListener {

	private JSlider reactionTime;
	private JLabel reactionTimeLabel;
	
	public ChangeReactionTimeListener (JSlider reactionTime, JLabel reactionTimeLabel)
	{
		this.reactionTime = reactionTime;
		this.reactionTimeLabel = reactionTimeLabel;
	}
	
	
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		TestClient.setWaitForIt(reactionTime.getValue());
		reactionTimeLabel.setText("Reaction Time (ms): " + reactionTime.getValue());
	}

}
