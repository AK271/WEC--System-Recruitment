import java.util.*;
import java.awt.Point;

public class main {

	static String str1;
	static String str2;
	static String str3;
	static String str4;
	static String str5;
	
	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		
		System.out.print("Enter line 1 of encoded message : ");
		String str1 = s.nextLine();
		System.out.print("Enter line 2 of encoded message : ");
		String str2 = s.nextLine();
		System.out.print("Enter line 3 of encoded message : ");
		String str3 = s.nextLine();
		System.out.print("Enter line 4 of encoded message : ");
		str4 = s.nextLine();
		System.out.print("Enter line 5 of encoded message : ");
		str5 = s.nextLine();
		
		System.out.println("Decoded line 5 : "+e5(str5));
		System.out.println("Decoded line 4 : "+e4(str4));
		System.out.println("Decoded line 3 : "+e3(str3));
		System.out.println("Decoded line 2 : "+e2(str2));
		System.out.println("Decoded line 1 : "+e1(str1));
	}
	
	public static String e5(String str)
	{
		Base64.Decoder decoder = Base64.getDecoder();  
      String dStr = new String(decoder.decode(str));  
//    System.out.println("Decoded string: "+dStr);  
     return dStr;
	}
	
	public static String e4(String str){
		String message, decryptedMessage = "";
		int key;
		char ch;
		message = e5(str);


		key = 13;
 
		for(int i = 0; i < message.length(); ++i){
			ch = message.charAt(i);
			
			if(ch >= 'a' && ch <= 'z'){
	            ch = (char)(ch - key);
	            
	            if(ch < 'a'){
	                ch = (char)(ch + 'z' - 'a' + 1);
	            }
	            
	            decryptedMessage += ch;
	        }
	        else if(ch >= 'A' && ch <= 'Z'){
	            ch = (char)(ch - key);
	            
	            if(ch < 'A'){
	                ch = (char)(ch + 'Z' - 'A' + 1);
	            }
	            
	            decryptedMessage += ch;
	        }
	        else {
	        	decryptedMessage += ch;
	        }
		}
		
		return decryptedMessage;
    }
	static int key_e3=0;
	public static String e3(String s){
		String message, decryptedMessage = "";
		char ch;
		Scanner sc = new Scanner(System.in);
		
		message = e4(s);
		
		if(key_e3==0)
		{
		System.out.println("Enter key for e3: ");
		key_e3 = sc.nextInt();
		}
 
		for(int i = 0; i < message.length(); ++i){
			ch = message.charAt(i);
			
			if(ch >= 'a' && ch <= 'z'){
	            ch = (char)(ch - key_e3);
	            
	            if(ch < 'a'){
	                ch = (char)(ch + 'z' - 'a' + 1);
	            }
	            
	            decryptedMessage += ch;
	        }
	        else if(ch >= 'A' && ch <= 'Z'){
	            ch = (char)(ch - key_e3);
	            
	            if(ch < 'A'){
	                ch = (char)(ch + 'Z' - 'A' + 1);
	            }
	            
	            decryptedMessage += ch;
	        }
	        else {
	        	decryptedMessage += ch;
	        }
		}
		
		return decryptedMessage;
    }
	
	static String key_e2="";
	public static String e2(String Message) {
		Scanner in = new Scanner(System.in);

		String str = e3(Message);
		System.out.print("Enter the key for e2: ");
		if(key_e2=="")
		{
			key_e2 = in.next();
			key_e2 = key_e2.toUpperCase();
		}
		String DMessage = "";
		str = str.toUpperCase();
		for (int i = 0, j = 0; i < str.length(); i++) {
			char letter = str.charAt(i);
			DMessage += (char)((letter - key_e2.charAt(j) + 26) % 26 + 65);
			j = ++j % key_e2.length();
		}
		return DMessage;
	}
	

	private static char[][] charTable;
    private static Point[] positions;
    static String key_e1 = "";
	public static String e1(String str) {
        Scanner sc = new Scanner(System.in);
 
        String txt = e2(str);
        if(key_e1=="")
        {
        System.out.print("Enter an encryption key of e1: ");
        key_e1 = sc.nextLine();
        }
        
        System.out.print("Replace J with I? y/n: ");
        String jti = sc.nextLine();
 
        boolean changeJtoI = jti.equalsIgnoreCase("y");
 
        createTable(key_e1, changeJtoI);
 
        return decode(txt);
    }
 
    private static String prepareText(String s, boolean changeJtoI) {
        s = s.toUpperCase().replaceAll("[^A-Z]", "");
        return changeJtoI ? s.replace("J", "I") : s.replace("Q", "");
    }
 
    private static void createTable(String key, boolean changeJtoI) {
        charTable = new char[5][5];
        positions = new Point[26];
 
        String s = prepareText(key + "ABCDEFGHIJKLMNOPQRSTUVWXYZ", changeJtoI);
 
        int len = s.length();
        for (int i = 0, k = 0; i < len; i++) {
            char c = s.charAt(i);
            if (positions[c - 'A'] == null) {
                charTable[k / 5][k % 5] = c;
                positions[c - 'A'] = new Point(k % 5, k / 5);
                k++;
            }
        }
    }
 
 
    private static String decode(String s) {
        return codec(new StringBuilder(s), 4);
    }
 
    private static String codec(StringBuilder text, int direction) {
        int len = text.length();
        for (int i = 0; i < len; i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
 
            int row1 = positions[a - 'A'].y;
            int row2 = positions[b - 'A'].y;
            int col1 = positions[a - 'A'].x;
            int col2 = positions[b - 'A'].x;
 
            if (row1 == row2) {
                col1 = (col1 + direction) % 5;
                col2 = (col2 + direction) % 5;
 
            } else if (col1 == col2) {
                row1 = (row1 + direction) % 5;
                row2 = (row2 + direction) % 5;
 
            } else {
                int tmp = col1;
                col1 = col2;
                col2 = tmp;
            }
 
            text.setCharAt(i, charTable[row1][col1]);
            text.setCharAt(i + 1, charTable[row2][col2]);
        }
        return text.toString();
    }

}
