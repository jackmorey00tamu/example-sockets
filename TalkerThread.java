import java.io.*;
import java.net.*;
import java.util.*;


public class TalkerThread extends Thread {
	protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean running = true;
    protected ArrayList<String> messages = new ArrayList<String>();
    protected int n;
    
    public TalkerThread() throws IOException {
    	this("TalkerThread", 8008, "This is a default testing message character count!");
    }
    
    public TalkerThread(String name, int portnum, String msg) throws IOException {
    	super(name);
    	
    	socket = new DatagramSocket(portnum);
    	populateMessages(msg);
    	
//    	for(int i = 0; i < messages.size(); i++) {
//    		System.out.println(messages.get(i));
//    	}
    	int n = 0;
    	
    	
    	
    	
    	
    }
    
    public void run() {
    	while(running) {
    		try {
    		
	    		byte[] buf = new byte[256];
	    		//receive request
	    		DatagramPacket packet = new DatagramPacket(buf, buf.length);
	    		socket.receive(packet);
    			
    			
	    		
	    		socket.setSoTimeout(2000);
	    		
	    		//handle response
	    		//TODO: Make this not just a default message
	    		
	    		
	    		InetAddress address = packet.getAddress();
	    		int port = packet.getPort();
	    		
	    		
	    		while(n!=messages.size()) {
		    		try {
		    			String dString = messages.get(n++);
		    	    	
			    		
			    		buf = dString.getBytes();
		    			
		    			
		    			packet = new DatagramPacket(buf, buf.length, address, port);
			    		socket.send(packet);
		    			
		    			byte[] r = new byte[256];
		    			DatagramPacket received = new DatagramPacket(r, r.length);
		    			socket.receive(received);
		    			String rcv = new String(received.getData(),0,received.getLength());
		    			System.out.println(rcv);
		    		
		    			
		    		}
		    		catch(SocketTimeoutException e) {
		    			n--;
		    		}
	    		}
	    		n=0;
	    		
	    		
	    		socket.setSoTimeout(0);
    		}
    		catch( IOException e) {
    			e.printStackTrace();
    			running=false;
    		}
    	}
    }
    
    private void populateMessages(String msg) {
    	if(msg.length()%10 == 0) {
    		messages.add("0 " + (msg.length()/10));
    		for(int i = 0; i < (msg.length()/10); i++) {
        		messages.add((i+1) + " " + msg.substring(i*10, Math.min( ((i+1)*10) , msg.length()) ));
        	}
    	}
    	else {
    		messages.add("0 " + (msg.length()/10+1));
    		for(int i = 0; i < (msg.length()/10 + 1); i++) {
        		messages.add((i+1) + " " + msg.substring(i*10, Math.min( ((i+1)*10) , msg.length()) ));
        	}
    	}
    	
    	
    	
    }
}
