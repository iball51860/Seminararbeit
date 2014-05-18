package control.listeners;

import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;


import view.ServerWindow;

/**
 * Listener triggered by the SaveLog Button in the LogPane.
 * Formats and saves an existing log as .txt file.
 * @author Jan Fritze & Manuel Kaiser *
 */
public class SaveLogListener implements ActionListener
{
	/**ServerWindow from which Listener is triggered*/
	ServerWindow serverWindow;
	
	/**
	 * Constructor setting the ServerWindow
	 * @param serverWindow
	 */
	public SaveLogListener(ServerWindow serverWindow)
	{
		this.serverWindow = serverWindow;
	}
	
	/**
	 * Retrieves the options for the current log that is shown in the LogPanel of the ServerWindow,
	 * creates a header for the log and saves it to the Users Desktop in a Folder WMServer_Logs.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		String directory = System.getProperty("user.home") + "/Desktop/WMServer_Logs";
		File logs = new File(directory);
		logs.mkdir();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy.MM.dd'_at_'HH.mm.ss.SSSS'.txt'");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd.MMMM yyyy' at 'HH.mm");
		File file = new File(directory, "WMServerLog_" + dateFormat1.format(new Date()));
		
		String teams = "Teams: " + serverWindow.getTeamBox1().getSelectedItem() + " and " +
				"" + serverWindow.getTeamBox2().getSelectedItem();
		String settings = "Settings: Server " + serverWindow.getLogPanel().getType()[1].isSelected() + " | Communication " + serverWindow.getLogPanel().getType()[2].isSelected() +
				" | Game " + serverWindow.getLogPanel().getType()[3].isSelected() + " | Round " + serverWindow.getLogPanel().getType()[4].isSelected() +
				" | Match " + serverWindow.getLogPanel().getType()[5].isSelected();
		
		FileWriter writer;
		try {
			writer = new FileWriter(file, true);
			writer.write(dateFormat2.format(new Date()) + "\n\n");
			writer.write(teams + "\n");
			writer.write(settings + "\n");
			writer.write("____________________________________________________________________________________\n\n");
			writer.write(serverWindow.getLogConsole().getText());
			writer.write("\n\n>> RESULTS: --------------------------------------------------------------------\n\n");
			writer.write(serverWindow.getResultList().getText());
			writer.flush();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		
	}

}
