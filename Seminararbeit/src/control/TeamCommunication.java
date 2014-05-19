/**
 * 
 */
package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

/**
 * Holds the detailed communication logic of {@link Team}.
 * Provides a simple method call that handles the Readers and Writers.
 * @author Jan Fritze & Manuel Kaiser
 * @see Team
 */
public class TeamCommunication {

	/**
	 * Reads a line from the given reader. If problems occur or nothing is read (null), returns 'x'.
	 * @param reader
	 * @return
	 */
	public static String read(BufferedReader reader){
		try
		{
			String inputMessage = reader.readLine();
			if (inputMessage == null)
			{
				inputMessage = "x";
			}
			return inputMessage;
		}
		catch(SocketException se)
		{
			se.printStackTrace();
			return "x";
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return "x";
		}
	}
	
	/**
	 * Sends a String to the client.
	 * @param message
	 * @param writer
	 */
	public static void write(String message, PrintWriter writer){
		writer.println(message);
	}
	
	/**
	 * Flushes the Readers buffer so there are no (irrelevant) messages left to be read.
	 * @param reader
	 */
	public static void flushReader(BufferedReader reader){
		try {
			while(reader.readLine()!=null){
				reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
