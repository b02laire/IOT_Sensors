package network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/** 
 *Class Server
 *Implementation of a UDP server that receives a packet each time the run() method is called.
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
		socket = new DatagramSocket(17600);
		response = new DatagramPacket(buffer, buffer.length);
		
	}

	/**
	Constructor
	Allows for more fine tuning of the server when instantiating it.
	@param buffer_size : size of the buffer to be used.
	@param port : port on which the server will be listening
	 */
	public Server(int buffer_size, int port) throws SocketException {
		buffer = new byte[buffer_size];
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
		String quote = new String(response.getData());
		System.out.println("Current Temp is: " + quote +"°C");
	}
	/**
	 *Returns the DataPacket as 
	 *@return contents of the packet as an Integer
	 */
	public int PackettoInt() {
	     return ByteBuffer.wrap(response.getData()).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt(); 
	}
	
	public static void main(String args[]) throws IOException
	{
		Server server = new Server();
		server.start();
		//socket.close();
	}
}