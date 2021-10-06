package network;
import java.io.IOException;
import java.lang.Thread;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

public class Sensor extends Thread
{	
	DatagramSocket socket;
	byte[] buffer;
	private InetAddress address;
	static int port;
			
	public Sensor() throws SocketException, UnknownHostException{
		
		socket = new DatagramSocket();
		System.out.println("Creating Server...");
		address = InetAddress.getByName("localhost");
		buffer = new byte[512];
		port = 17600;
	}
	public void run()
	{
	while(true) {
		int temp = ThreadLocalRandom.current().nextInt(40);
	    String TempString = "Current Sensor Temperature: " + temp + "°C";
		System.out.println(TempString);	
		// I shouldn't do this but the other StackOverflow answers were too complicated
		buffer = Integer.toString(temp).getBytes();
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
	public static void main(String args[]) throws InterruptedException, SocketException, UnknownHostException
	{  
		Sensor mySensor = new Sensor();
		
		mySensor.start();
		}	
	}