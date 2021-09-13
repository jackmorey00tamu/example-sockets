import java.io.*;
import java.net.*;
import java.util.*;







/* step 1: request 
 * step 2: receive message 0 outlining how many more messages it should expect
 * step 3: receive message n
 * step 4: send ack
 * step 5: step 3 until n is over
 * 
 */

public class FaultyListener {
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Usage: java FaultyListener <hostname> <portnumber>");
			return;
		}
		
		DatagramSocket socket = new DatagramSocket();
		
		//send request
		byte[] buf = new byte[256];
		InetAddress address = InetAddress.getByName(args[0]);
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, Integer.parseInt(args[1]));
		socket.send(packet);
		
		//get response
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);
		
		// message 0 ___
		
		
		
		//display reponse
		String received = new String(packet.getData(), 0, packet.getLength());
		System.out.println("Message received: " + received);
		
		int numberOfExpectedMessages = Integer.parseInt(received.substring(2));
		System.out.println(numberOfExpectedMessages);
		
		buf = ("ACK 1").getBytes();
		DatagramPacket ack = new DatagramPacket(buf, buf.length, address, Integer.parseInt(args[1]));
		socket.send(ack);
		
		//0 5
		//0   ?=   5
		//1 ?= 5
		//..
		//5 ?= 5
		
		while(   Integer.parseInt(received.substring(0,received.indexOf(' '))) != (numberOfExpectedMessages) ) {
		
			//receive message
			socket.receive(packet);
			received = new String(packet.getData(), 0, packet.getLength());
			System.out.println("Message received: " + received);
			//System.out.println(received.substring(0,received.indexOf(' ')));
			
			
			//ack
			if(Math.random() > .5) {
				buf = ("ACK "+ (Integer.parseInt(received.substring(0,received.indexOf(' ')))+1)).getBytes();
				ack = new DatagramPacket(buf, buf.length, address, Integer.parseInt(args[1]));
				socket.send(ack);
			}
		}
		
		System.out.println("Done.");
		
		socket.close();
		
	}
}
