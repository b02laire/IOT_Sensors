package network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *Class Server
 *Implementation of an UDP server that receives a packet each time the run() method is called.
 *@author: Pol BODET
 */
public class Server extends Thread{

	//declaring class-wide variables allowing us to access them outside the methods
	public byte[] buffer;
	public DatagramPacket response;
	public DatagramSocket socket;

	/**
	*Constructor
	*Allows for a quick & dirty instantiation of a Server
	 */
	public Server() throws SocketException {
		buffer = new byte[512];
		//we want to receive data from a sensor sending UDP packets on the 17600 port
		socket = new DatagramSocket(17600);
		response = new DatagramPacket(buffer, buffer.length);

	}

	/**
	*Constructor
	*Allows for more fine tuning of the server when instantiating it.
	*@param buffer_size : size of the buffer to be used.
	*@param port : port on which the server will be listening
	 */
	public Server(int buffer_size, int port) throws SocketException {
		buffer = new byte[buffer_size];
		//we want to receive data from a sensor sending UDP packets on a specific port
		socket = new DatagramSocket(port);
		response = new DatagramPacket(buffer, buffer_size);

	}
	/**
	*Every time this method is called, it receives the latest packet available.
	*/
	public void run()	{

		try {
			socket.receive(response);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Caught Exception");
		}
		int quote = PackettoInt();

		System.out.println("Current Temp is: " + quote +"°C");
	}
	/**
	 *Returns the DataPacket's buffer as an int
	 *@return contents of the packet as an Integer
	 */
	public int PackettoInt() {
	byte[] bytes = response.getData();
	 return ((bytes[0] & 0xFF) << 24) |
					((bytes[1] & 0xFF) << 16) |
					((bytes[2] & 0xFF) << 8 ) |
					((bytes[3] & 0xFF) << 0 );
}

	public static void main(String args[]) throws IOException
	{
		Server server = new Server();
		server.start();
	}
}
