package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Presents the information necessary to sign on to the Server in large Font (IP and Port).
 * @author Jan Fritze & Manuel Kaiser
 *
 */
@SuppressWarnings("serial")
public class StartPanel extends JPanel {

	/**Label with the IP*/
	private JLabel showIp;
	/**Label with the Portnumber*/
	private JLabel showPort;
	
	/**
	 * Constructs a simple Panel with two large Labels showing IP-Adress and Port
	 */
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
	
	/**
	 * Returns the Label showing the Port.
	 * @return
	 */
	public JLabel getShowPort(){
		return showPort;
	}
}
