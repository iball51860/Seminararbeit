package view;

import java.awt.*;
import javax.swing.*;

import model.*;

public class TeamPopupDialog extends JDialog
{
	
	Container c;
	JButton close;
	JLabel info;
	
	
	public TeamPopupDialog(Team t)
	{
		setUndecorated(true);
		
		c = getContentPane();
		c.setLayout(new GridLayout(2, 1));
		
		info = new JLabel(t.getName() + "/n" + "Goals: " + t.getGoals());
		info.setHorizontalAlignment(SwingConstants.CENTER);
		close = new JButton("close");
		
		c.add(info);
		c.add(close);
		
	}
	
	
}
