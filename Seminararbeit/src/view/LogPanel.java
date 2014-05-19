package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import control.listeners.SaveLogListener;
import control.listeners.TeamBoxListener;
import control.listeners.UpdateLogListener;

import model.LogLine;
import model.Team;

/**
 * Panel holding the Log that can be found in the Tabbed Pane. Holds a TextArea with the log itself
 * that can be changed to show information about a certain type of events and certain instances/teams.
 * 
 * @author Jan Fritze & Manuel Kaiser
 *
 */
@SuppressWarnings("serial")
public class LogPanel extends JPanel {
	
	/**Panel on the right holding the controls*/
	private JPanel logSettingsPanel;
	/**TextArea for displaying the log*/
	private JTextArea logConsole;
	/**Title Label of the logSettingsPanel*/
	private JLabel infoLog;
	/**First Combobox for selecting a team or any other instance being logged*/
	private JComboBox<String> teamBox1;
	/**Combobox for another team/instance*/
	private JComboBox<String> teamBox2;
	/**CheckBoxArray with Checkboxes for each eventtype*/
	public JCheckBox[] type;
	/**Button for Saving the displayed log*/
	private JButton saveLog;
	
	/**
	 * Constructs the whole logPanel with the TextArea, Settings to build the log and a save Button at the bottom.
	 * @param serverWindow
	 */
	public LogPanel(ServerWindow serverWindow){
		super(new BorderLayout());
		logConsole = new JTextArea();
		logConsole.setEditable(false);
		JScrollPane spLog = new JScrollPane();
		spLog.setViewportView(logConsole);
		logSettingsPanel = new JPanel(new GridLayout(0, 1));
		infoLog = new JLabel("Show:");
		UpdateLogListener updateLogListener = new UpdateLogListener(serverWindow);
		teamBox1 = new JComboBox<String>();
		teamBox1.addItem("No Teams");
		teamBox1.addItem("All Teams");
		teamBox1.addActionListener(updateLogListener);
		teamBox1.addActionListener(new TeamBoxListener(serverWindow));
		teamBox2 = new JComboBox<String>();
		teamBox2.addItem("No Team");
		teamBox2.setEnabled(false);
		teamBox2.addActionListener(updateLogListener);
		logSettingsPanel.add(infoLog);
		logSettingsPanel.add(teamBox1);
		logSettingsPanel.add(teamBox2);
		type = new JCheckBox[7];
		type[0] = new JCheckBox("Default");
		type[1] = new JCheckBox("Server");
		type[2] = new JCheckBox("Communication");
		type[3] = new JCheckBox("Game");
		type[4] = new JCheckBox("Round");
		type[5] = new JCheckBox("Match");
		type[6] = new JCheckBox("Shot");
		for(JCheckBox jCB : type)
		{
			jCB.setSelected(true);
			jCB.addActionListener(updateLogListener);
			logSettingsPanel.add(jCB);
		}
		logSettingsPanel.remove(type[0]);
		logSettingsPanel.remove(type[6]);
		saveLog = new JButton("Save Log");
		saveLog.addActionListener(new SaveLogListener(serverWindow));
		saveLog.setVisible(true);
		add(spLog, BorderLayout.CENTER);
		add(logSettingsPanel, BorderLayout.EAST);
		add(saveLog, BorderLayout.SOUTH);
	}
	
	/**
	 * Registers a new team at the Comboboxes so it can be selected for the log.
	 * @param team
	 */
	public void registerTeam(Team team){
		teamBox1.addItem(team.getName() + team.getID());
		teamBox2.addItem(team.getName() + team.getID());
	}
	
	/**
	 * Appends a new logline to the existing Log. Preferably use this method instead of rebuilding the Log everytime a new line is added.
	 * @param logLine
	 * @param tabPane
	 */
	public void appendLogLine(final LogLine logLine, JTabbedPane tabPane)
	{
		boolean validInstance = (logLine.getInstanceName() == null || teamBox1.getSelectedItem().toString().equalsIgnoreCase("All Teams") ||
					teamBox1.getSelectedItem().toString().equals(logLine.getInstanceName() + logLine.getInstanceID()) || 
					teamBox2.getSelectedItem().toString().equals(logLine.getInstanceName() + logLine.getInstanceID()));
		
		//check whether Log is selected, type applies, and message is about a blocked instance a la (LogIsShowing && typeIsSelected && (name==null || allTeams || nameIsInBox1 || nameIsInBox2) )
		if(tabPane.getComponentAt(2).isShowing() && type[logLine.getType()].isSelected() && validInstance)
		{
			SwingUtilities.invokeLater(new Runnable(){
				public void run()
				{
					logConsole.append("\n" + logLine.getMessage());
				}
			});
		}
	}
	
	/**
	 * 
	 * @return TeamBox1
	 */
	public JComboBox<String> getTeamBox1(){
		return teamBox1;
	}
	
	/**
	 * 
	 * @returnTeamBox2
	 */
	public JComboBox<String> getTeamBox2() {
		return teamBox2;
	}
	
	/**
	 * 
	 * @return Save-Button
	 */
	public JButton getSaveLog() {
		return saveLog;
	}
	
	/**
	 * 
	 * @return logConsole
	 */
	public JTextArea getLogConsole() {
		return logConsole;
	}
	
	/**
	 * 
	 * @return CheckBox to select Eventtypes
	 */
	public JCheckBox[] getType(){
		return type;
	}
}
