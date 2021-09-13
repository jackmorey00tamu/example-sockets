import java.io.IOException;

public class Talker {
	
	public static boolean isNumeric(String string) {
	    int intValue;
			
			
	    if(string == null || string.equals("")) {
	        return false;
	    }
	    
	    try {
	        intValue = Integer.parseInt(string);
	        return true;
	    } catch (NumberFormatException e) {
	    }
	    return false;
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 2 || (args[1].length() > 50) || (!isNumeric(args[0])) ) {
			System.out.println("Usage: java Talker <portnumber> <message up to 50 characters>");
			return;
		}
		new TalkerThread("TalkerThread", Integer.parseInt(args[0]), args[1]).start();
	}
	
}
