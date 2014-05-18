/**
 * 
 */
package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Jan
 *
 */
@SuppressWarnings("serial")
public class StartPanel extends JPanel {

	private JLabel showIp;
	private JLabel showPort;
	
	public StartPanel(){
		super(new GridLayout(2, 1));
		
		try {
			showIp = new JLabel("IP-Address: " + InetAddress.getLocalHost().getHostAddress());
			showIp.setFont(new Font("Arial", Font.BOLD, 50));
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		showPort = new JLabel("Port: XXXX");
		showPort.setFont(new Font("Arial", Font.BOLD, 50));
		add(showIp);
		add(showPort);
	}
	
	public JLabel getShowPort(){
		return showPort;
	}
}
