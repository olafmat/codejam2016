import java.io.*;
import java.util.*; 
import java.math.*; 

/** 
 * @author Olaf Matyja olafmat@gmail.com 
 */
public class Preparation2015_1CC {
	private static class ScannerCopier {
		Scanner scanner;
		boolean print;
		
		public ScannerCopier(String s, boolean print) {
			this.scanner = new Scanner(s);
			this.print = print;
		}
		
		public ScannerCopier(File f, boolean print) throws FileNotFoundException {
			scanner = new Scanner(f);
			this.print = print;
		}
		
		int nextInt() {
			int res = scanner.nextInt();
			if (print) {
				System.out.print(res);	
				System.out.print(' ');
			}
			return res;
		}
		
		long nextLong() {
			long res = scanner.nextLong();
			if (print) {
				System.out.print(res);
				System.out.print(' ');
			}
			return res;
		}
		
		double nextDouble() {
			double res = scanner.nextDouble();
			if (print) {
				System.out.print(res);
				System.out.print(' ');
			}
			return res;
		}
		
		String next() {
			String res = scanner.next();
			if (print) {
				System.out.print(res);
				System.out.print(' ');
			}
			return res;
		}
		
		String nextLine() {
			String res = scanner.nextLine();
			if (print) {
				System.out.print(res);
				System.out.print(' ');
			}
			return res;
		}
		
		void println() {
			if (print) {
				System.out.println();
			}
		}
		
		void close() {
			scanner.close();
		}
	}
	
	private static class PrintStreamCopier {
		PrintStream out;
		boolean print;
		
		PrintStreamCopier(PrintStream out, boolean print) {
			this.out = out;
			this.print = print;
		}
		
		void print(String s) {
			out.print(s);
			if (print)
				System.out.print(s);
		}

		void print(long x) {
			out.print(x);
			if (print)
				System.out.print(x);
		}

		void print(double x) {
			out.print(x);
			if (print)
				System.out.print(x);
		}

		void print(boolean x) {
			out.print(x);
			if (print)
				System.out.print(x);
		}

		void println(String s) {
			out.println(s);
			if (print)
				System.out.println(s);
		}
		
		void println() {
			out.println();
			if (print)
				System.out.println();
		}
		
		void close() {
			out.close();
		}
	}
	
	void calcFile(String fin, String fout) throws IOException {
		ScannerCopier in = new ScannerCopier(new File(fin), true);
		PrintStreamCopier out = new PrintStreamCopier(new PrintStream(fout), true);		
		process(in, out);		
	}

	
	
	
	
	int cases;
	long maxSet;
	int minClear;
	
	int lacking(BitSet cur) {
		return cur.nextClearBit(minClear);
	}

	void addCoin(BitSet cur, long V, int C, int n) {
		for (int i = (int)maxSet; i >= 1; i--) {
			if (cur.get(i)) {
				long d = i;
				for (int k = 0; k < C; k++) {
					d += n;
					if (d <= V) {
						cur.set((int)d);
					}
				}
				if (d > maxSet) {
					maxSet = d;
				}
			}
		}
		long d = 0;
		for (int k = 0; k < C; k++) {
			d += n;
			if (d <= V) {
				cur.set((int)d);
			}
		}		
		if (d > maxSet) {
			maxSet = d;
		}
	}
	
	int calc(int C, long V, int[] coins) {
		minClear = 1;
		maxSet = 0;
		BitSet cur = new BitSet((int)V + 2);
		for (int c: coins) {
			addCoin(cur, V, C, c);
		}
		
		int nCoins = 0;
		while (true) {
			int n = lacking(cur);
			if (n == 0 || n > V)
				return nCoins;
			minClear = n;
			addCoin(cur, V, C, n);
			nCoins++;
		}
	}
	
	void process(ScannerCopier scanner, PrintStreamCopier out) throws IOException {
		long startTime = System.nanoTime();
		cases = scanner.nextInt();
		scanner.nextLine();
		scanner.println();
		
		for (int curCase = 0; curCase < cases; curCase++) {
			int C = scanner.nextInt();
			int D = scanner.nextInt();
			int V = scanner.nextInt();
			scanner.println();
			int[] coins = new int[D]; 
			for (int i = 0; i < D; i++) {
				coins[i] = scanner.nextInt();
			}
			scanner.println();
			
			int res = calc(C, V, coins);
			
			out.println("Case #" + (curCase + 1) + ": " + res);
			
			double time = ((double)(System.nanoTime() - startTime)) / 1e9;
			System.out.println("TIME: " + time + " " + time * cases / (curCase + 1));			
		}
		
		scanner.close();
		out.close();				
	}

	void calcSample() throws IOException {
		String sampleText = "4\r\n" + 
				"1 2 3\r\n" + 
				"1 2\r\n" + 
				"1 3 6\r\n" + 
				"1 2 5\r\n" + 
				"2 1 3\r\n" + 
				"3\r\n" + 
				"1 6 100\r\n" + 
				"1 5 10 25 50 100";
		ScannerCopier in = new ScannerCopier(sampleText, true);
		PrintStreamCopier out = new PrintStreamCopier(System.out, false);		
		process(in, out);		
	}
	
	Preparation2015_1CC() throws IOException {
		//calcSample();
		calcFile("C:\\Users\\Olaf\\Downloads\\C-small-practice.in", "practice-out-C-small.txt");
		//calcFile("C:\\Users\\Olaf\\Downloads\\C-large-practice.in", "practice-out-C-large.txt");	
	}	
	
	public static void main(String[] args) throws IOException {
		new Preparation2015_1CC();
	}	
	
}
