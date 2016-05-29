import java.io.*;
import java.util.*; 
import java.math.*; 

/** 
 * @author Olaf Matyja olafmat@gmail.com 
 */
public class Preparation2015_1BA {

	int cases;
	
	static String revStr(String s) {
		StringBuffer buf = new StringBuffer();
		for (int n = s.length() - 1; n >= 0; n--) {
			buf.append(s.charAt(n));
		}
		return buf.toString();
	}
	
	private static long jumps[] = {
		0,
		19,
		199,
		1099,
		10999L,
		100999L,
		1009999L,
		10009999L,
		100099999L,
		1000099999L,
		10000999999L,
		100000999999L,
		1000009999999L,
		10000009999999L,
		100000099999999L,
		1000000099999999L,
		10000000999999999L,
		100000000999999999L
	};
	
	public static long getJumps(long n) {		
		long v = 0;		
		
		while (n > 20) {
			String s = "" + (n - 1);
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < s.length(); i++) {
				if (i == 0)
					buf.append('1');
				else if (i < s.length() / 2)
					buf.append('0');
				else
					buf.append(s.charAt(s.length() - 1 - i));
			}
			
			long from = Long.parseLong(buf.toString());
			long to = Long.parseLong(revStr(buf.toString()));		
			if (buf.charAt(0) != '0' && from < to && to <= n) {
				v += n - to + 1;
				n = from;
			} else {
				boolean found = false;
				for (int i = jumps.length - 1; i >= 0; i--) {
					from = jumps[i];
					to = Long.parseLong(revStr("" + from));
					if (from < to && to <= n) {
						v += n - to + 1;
						n = from;
						found = true;
						break;
					}
				}
				if (!found) {
					v++;
					n--;
				}
			}			
		};
		v += n;
		return v;
	}
	
	
	void process(Scanner scanner, PrintStream out) throws IOException {
		cases = scanner.nextInt();
		scanner.nextLine();
		
		for (int curCase = 0; curCase < cases; curCase++) {
			long n = scanner.nextLong();
			out.println("Case #" + (curCase + 1) + ": " + getJumps(n));
		}
	}
	
	Preparation2015_1BA() throws IOException {
		/*String sampleText = "3\r\n" + 
				"1\r\n" + 
				"19\r\n" + 
				"23"; 
		Scanner in = new Scanner(sampleText);
		PrintStream out = System.out;*/
		
		
		/*Scanner in = new Scanner(new File("C:\\Users\\Olaf\\Downloads\\A-small-practice(2).in"));
		PrintStream out = new PrintStream("practice-out-A-small.txt");*/
		
		
		Scanner in = new Scanner(new File("C:\\Users\\Olaf\\Downloads\\A-large-practice.in"));
		PrintStream out = new PrintStream("out-A-large.txt");
		

		
		process(in, out);
		
		in.close();
		out.close();
	}	
	
	public static void main(String[] args) throws IOException {
		new Preparation2015_1BA();
	}	
	
}
