package view;

import java.awt.*;

import javax.swing.*;

import control.listeners.*;

import model.*;

/**
 * Popup that provides information about a single team. Opens when the teams Button in the teamMatrix is clicked. Provided Information:<br>
 * Team Name<br>IP-Address<br>Strengths<br>Goals<br>Victories<br>Success Rate<br><br>Close-Button to close the frame.
 * @author Jan Fritze & Manuel Kaiser
 *
 */
@SuppressWarnings("serial")
public class TeamPopup extends JDialog
{
	/**Base Container holds all components*/
	private Container c;
	
	/**Label with the name on it*/
	private JLabel name;
	/**Label with the IP on it*/
	private JLabel ip;
	/**Label with the strengths "Left: xx | Middle: xx | Right: xx"*/
	private JLabel strength;
	/**Label with the number of scored goals.*/
	private JLabel goals;
	/**Label with the number of victories*/
	private JLabel victories;
	/**Label with the success Rate of the teams shots*/
	private JLabel success;
	/**Button to close frame*/
	private JButton close;
	
	/**
	 * Constructs a frame that shows information about a team. For more detailed Information see {@link TeamPopup}
	 * @param serverWindow
	 * @param t
	 */
	public TeamPopup(final ServerWindow serverWindow, final Team t)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setSize(200, 180);
				setLocation(MouseInfo.getPointerInfo().getLocation());
				c = getContentPane();
				c.setLayout(new GridLayout(0, 1));
				name = new JLabel(t.getName());
				ip = new JLabel(t.getClientSocket().getInetAddress().toString());
				strength = new JLabel("Left: " + t.getStrength()[0]
						+ " | Middle: " + t.getStrength()[1] + " | Right: "
						+ t.getStrength()[2]);
				goals = new JLabel("Goals: " + t.getGoals());
				victories = new JLabel("Victories: " + t.getWonMatches());
				int rate = (int) ((double) t.getGoals() * 100 / (double) t
						.getFinishedShots());
				success = new JLabel("Success Rate: " + rate + " %");
				close = new JButton("close");
				close.addActionListener(new ClosePopupListener(serverWindow,
						TeamPopup.this));
				close.addKeyListener(new ClosePopupListener(serverWindow,
						TeamPopup.this));
				c.add(name);
				c.add(ip);
				c.add(strength);
				c.add(goals);
				c.add(victories);
				c.add(success);
				c.add(close);
				
				dispose();
				setUndecorated(true);
				setVisible(true);
			}
		});
		
	}
	
	
}
