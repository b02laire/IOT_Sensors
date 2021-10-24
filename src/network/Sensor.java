package network;
import java.io.IOException;
import java.lang.Thread;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

/**
 *Class Sensor
 *Implementation of a Sensor sending UDP packet every second
 *@author: Pol BODET
 */

public class Sensor extends Thread
{
	// Class-wide declaration to allow for cleaner code
	DatagramSocket socket;
	byte[] buffer;
	private InetAddress address;
	static int port;
	/**
	*Constructor
	*Initializes the attributes of the Class
	*/
	public Sensor() throws SocketException, UnknownHostException{

		socket = new DatagramSocket();
		System.out.println("Creating Sensor...");
		address = InetAddress.getByName("localhost");
		buffer = new byte[512];
		port = 17600;
	}
	public void run()
	{
	while(true) {
		// Creating a random interger on the range [0;40]
		int temp = ThreadLocalRandom.current().nextInt(40);

	    String TempString = "Current Sensor Temperature: " + temp + "°C";
		System.out.println(TempString);

		buffer = Sensor.intToBytes(temp);
		// Creating a ready-to-send packet containing the random integer
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
		try {
			socket.send(packet);
			System.out.println("Sent packet");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			Sensor.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			}
		}
	}
/**
*intToBytes
*Method used to convert an int into a 4-byte array using shifting and masking
*@param: data The int to be converted
*/
	private static byte[] intToBytes(final int data) {
	    return new byte[] {
	        (byte)((data >> 24) & 0xff),
	        (byte)((data >> 16) & 0xff),
	        (byte)((data >> 8) & 0xff),
	        (byte)((data >> 0) & 0xff),
	    };
	}

	public static void main(String args[]) throws InterruptedException, SocketException, UnknownHostException
	{
		Sensor mySensor = new Sensor();

		mySensor.start();
		}
	}
