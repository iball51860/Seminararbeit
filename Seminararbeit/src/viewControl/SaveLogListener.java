package viewControl;

import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.JCheckBox;

import view.ServerWindow;

public class SaveLogListener implements ActionListener
{
	ServerWindow sW;
	
	
	public SaveLogListener(ServerWindow serverWindow)
	{
		this.sW = serverWindow;
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		String directory = System.getProperty("user.home") + System.getProperty("file.seperator") + "WMServer Logs";
		File logs = new File(directory);
		logs.mkdir();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy.MM.dd'_at_'HH.mm.ss.SSSS'.txt'");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd.MMMM yyyy' at 'HH.mm");
		File file = new File(directory, "WMServerLog_" + dateFormat1.format(new Date()));
		
		String teams = "Teams: " + sW.getTeamBox1().getSelectedItem() + " and " +
				"" + sW.getTeamBox2().getSelectedItem();
		String settings = "Settings: Server " + sW.type[1].isSelected() + " | Communication " + sW.type[2].isSelected() +
				" | Game " + sW.type[3].isSelected() + " | Round " + sW.type[4].isSelected() +
				" | Match " + sW.type[5].isSelected();
		
		FileWriter writer;
		try {
			writer = new FileWriter(file, true);
			writer.write(dateFormat2.format(new Date()) + "\n\n");
			writer.write(teams + "\n");
			writer.write(settings + "\n");
			writer.write("____________________________________________________________________________________\n\n");
			writer.write(sW.getLogConsole().getText());
			writer.flush();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		
	}

}
