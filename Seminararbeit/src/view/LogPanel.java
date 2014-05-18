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

import model.LogLine;
import model.Team;

import viewControl.SaveLogListener;
import viewControl.TeamBoxListener;
import viewControl.UpdateLogListener;

public class LogPanel extends JPanel {

	private JPanel logSettings;
	private JTextArea logConsole;
	private JLabel infoLog;
	private JComboBox<String> teamBox1;
	private JComboBox<String> teamBox2;
	public JCheckBox[] type;
	private JButton saveLog;
	
	public LogPanel(ServerWindow serverWindow){
		super(new BorderLayout());
		logConsole = new JTextArea();
		logConsole.setEditable(false);
		JScrollPane spLog = new JScrollPane();
		spLog.setViewportView(logConsole);
		logSettings = new JPanel(new GridLayout(0, 1));
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
		logSettings.add(infoLog);
		logSettings.add(teamBox1);
		logSettings.add(teamBox2);
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
			logSettings.add(jCB);
		}
		logSettings.remove(type[0]);
		logSettings.remove(type[6]);
		saveLog = new JButton("Save Log");
		saveLog.addActionListener(new SaveLogListener(serverWindow));
		saveLog.setVisible(true);
		add(spLog, BorderLayout.CENTER);
		add(logSettings, BorderLayout.EAST);
		add(saveLog, BorderLayout.SOUTH);
	}
	
	public void registerTeam(Team t){
		teamBox1.addItem(t.getName() + t.getID());
		teamBox2.addItem(t.getName() + t.getID());
	}
	
	public void appendLogLine(final LogLine ll, JTabbedPane tabPane)
	{
		boolean validInstance = (ll.getInstanceName() == null || teamBox1.getSelectedItem().toString().equalsIgnoreCase("All Teams") ||
					teamBox1.getSelectedItem().toString().equals(ll.getInstanceName() + ll.getInstanceID()) || 
					teamBox2.getSelectedItem().toString().equals(ll.getInstanceName() + ll.getInstanceID()));
		
		//check whether Log is selected, type applies, and message is about a blocked instance a la (LogIsShowing && typeIsSelected && (name==null || allTeams || nameIsInBox1 || nameIsInBox2) )
		if(tabPane.getComponentAt(2).isShowing() && type[ll.getType()].isSelected() && validInstance)
		{
			SwingUtilities.invokeLater(new Runnable(){
				public void run()
				{
					logConsole.append("\n" + ll.getMessage());
				}
			});
		}
	}
	
	public JComboBox<String> getTeamBox1(){
		return teamBox1;
	}
	
	public JComboBox<String> getTeamBox2() {
		return teamBox2;
	}
	
	public JTextArea getLogString(){
		return logConsole;
	}
	
	public JButton getSaveLog() {
		return saveLog;
	}
	
	public JTextArea getLogConsole() {
		return logConsole;
	}
	
	public JCheckBox[] getType(){
		return type;
	}
}
