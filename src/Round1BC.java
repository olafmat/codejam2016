import java.io.*;
import java.util.*; 
import java.math.*; 

/** 
 * @author Olaf Matyja olafmat@gmail.com 
 */
public class Round1BC {
	//START boilerplate code
	
	@SuppressWarnings("unused")
	private static class ScannerCopier {
		private Scanner scanner;
		private boolean print;
		
		public ScannerCopier(String s, boolean print) {
			this.scanner = new Scanner(s);
			this.scanner.useLocale(Locale.US);
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
			String s = scanner.next();
			double res = Double.parseDouble(s);
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
	
	@SuppressWarnings("unused")
	private static class PrintStreamCopier {
		private PrintStream out;
		private boolean print;
		
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
	
	void calcSample(String sampleText) throws IOException {
		ScannerCopier in = new ScannerCopier(sampleText, true);
		PrintStreamCopier out = new PrintStreamCopier(System.out, false);		
		process(in, out);		
	}

	void calcFile(String fin, String fout, boolean print) throws IOException {
		ScannerCopier in = new ScannerCopier(new File(fin), print);
		PrintStreamCopier out = new PrintStreamCopier(new PrintStream(fout), print);		
		process(in, out);		
	}

	long startTime = System.nanoTime();
	
	void timeMeasure(int curCase, int cases) {
		double time = ((double)(System.nanoTime() - startTime)) / 1e9;
		System.out.println("TIME " + (curCase + 1) + ": " + time + " " + time * cases / (curCase + 1));					
	}
	
	
	
	
	static class Dijkstra {
		//BEGIN
		static int MAX = 10000;
		//END
		
		static class State implements Comparable<State> {
			long cost;
			int state;
			String history;
			
			@Override
			public int compareTo(State s) {
				if (cost > s.cost)
					return 1;
				if (cost < s.cost)
					return -1;
				if (state > s.state)
					return 1;
				if (state < s.state)
					return -1;
				return 0;
			}
			
			@Override
			public boolean equals(Object o) {
				return o instanceof State && compareTo((State)o) == 0; 
			}
			
			public void propagate(Dijkstra base) {
				//BEGIN
				if (state < MAX) {
					base.add(cost + 1, state + 1, history);
				}
				int rev = Integer.parseInt(revStr("" + state));
				if (rev <= MAX) {
					base.add(cost + 1, rev, history + " " + state);
				}
				//END
			}
		};
		
		Map<Integer, State> map = new HashMap<Integer, State>(); 
		PriorityQueue<State> queue = new PriorityQueue<State>();  	
		
		public State get(int n) {
			return map.get(n);
		}
		
		public void add(long cost, int s, String history) {
			State state = map.get(s);
			if (state != null) {
				if (state.cost <= cost)
					return;
				map.remove(s);
				queue.remove(state);
				state.history = history;
				state.cost = cost;
			} else {
				state = new State();
				state.cost = cost;
				state.state = s;
				state.history = history;
			}
			map.put(s, state);
			queue.add(state);
		}
		
		public void run() {
			while (!queue.isEmpty()) {
				State state = queue.poll();
				state.propagate(this);
			}
		}
		
		public static void analyse() {
			Dijkstra d = new Dijkstra();
			
			//BEGIN 
			d.add(1, 1, "");
			//END
			
			d.run();
			
			//BEGIN
			for (int n = 1; n <= MAX; n++) {
				long cur = d.get(n).cost;
				{
					System.out.println(n + "\t" + cur + "\t" + d.get(n).history);
				}
			}
			//END
		}
	};

	public static String revStr(String s) {
		StringBuffer buf = new StringBuffer();
		for (int n = s.length() - 1; n >= 0; n--) {
			buf.append(s.charAt(n));
		}
		return buf.toString();
	}
	
	public static final int primes[] = {
		 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 
		 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 
		 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 
		 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 
		 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 
		 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 
		 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 
		 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 
		 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 
		 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 
		 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 
		 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 
		 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 
		 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 
		 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 
		 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 
		 947, 953, 967, 971, 977, 983, 991, 997			
	};
	
	public static List<Integer> factorization(int n) {
		List<Integer> factors = new ArrayList<Integer>();
		for (int p: primes) {
			if (p * p > n)
				break;
			while (n % p == 0) {
				factors.add(p);
				n /= p;
			}
		}
		if (n > 1000000)
			System.out.println("factorization " + n);
		if (n > 1)
			factors.add(n);
		return factors;
	}

	public static int gcd(int a, int b) {
		int c;		
		while (b != 0) {
			c = a % b;
			a = b;
			b = c;
		}
		return a;
	}	
	
	public static long gcd(long a, long b) {
		long c;		
		while (b != 0) {
			c = a % b;
			a = b;
			b = c;
		}
		return a;
	}	
	
	public static <T> void print(T[] arr) {
		for (int n = 0; n < arr.length; n++) {
			System.out.println(arr[n]);
		}
	}
	
	public static <T> void print(Iterable<T> iter) {
		for (T t: iter) {
			System.out.println(t);
		}
	}
	
	public static long sum(int[] arr) {
		long total = 0;
		for (int v : arr) {
			total += v;
		}
		return total;
	}
	
	public static long sum(long[] arr) {
		long total = 0;
		for (long v : arr) {
			total += v;
		}
		return total;
	}

	public static double sum(double[] arr) {
		double total = 0;
		for (double v : arr) {
			total += v;
		}
		return total;
	}
		
	public static <T extends Number> long sum(Iterable<T> iter) {
		long total = 0;
		for (T v: iter) {
			total += ((Number)v).longValue();
		}
		return total;
	}
		
	public static int max(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int v: arr) {
			if (v > max) {
				max = v;
			}
		}
		return max;
	}
	
	public static long max(long[] arr) {
		long max = Long.MIN_VALUE;
		for (long v: arr) {
			if (v > max) {
				max = v;
			}
		}
		return max;
	}
	
	public static double max(double[] arr) {
		double max = -Double.MAX_VALUE;
		for (double v: arr) {
			if (v > max) {
				max = v;
			}
		}
		return max;
	}
	
	public static <T extends Comparable<T>> T max(Iterable<T> iter) {
		T max = null;		
		for (T v: iter) {
			if (max == null || v.compareTo(max) > 0) {
				max =v;
			}
		}
		return max;
	}
		
	public static int min(int[] arr) {
		int min = Integer.MAX_VALUE;
		for (int v: arr) {
			if (v < min) {
				min = v;
			}
		}
		return min;
	}
	
	public static long min(long[] arr) {
		long min = Long.MAX_VALUE;
		for (long v: arr) {
			if (v < min) {
				min = v;
			}
		}
		return min;
	}
	
	public static double min(double[] arr) {
		double min = Double.MAX_VALUE;
		for (double v: arr) {
			if (v < min) {
				min = v;
			}
		}
		return min;
	}
	
	public static <T extends Comparable<T>> T min(Iterable<T> iter) {
		T min = null;		
		for (T v: iter) {
			if (min == null || v.compareTo(min) < 0) {
				min =v;
			}
		}
		return min;
	}

	public void assert_(boolean expr) {
		assert_("", expr);
	}
	
	public void assert_(String txt, boolean expr) {
		if (!expr)
			throw new RuntimeException(txt);
			//System.out.println("ERR " + txt);
	}
	
	Random random = new Random(1423444);
	
	//END boilerplate code
	

	
	class Topic {
		String a;
		String b;
	}
	
	void process(ScannerCopier scanner, PrintStreamCopier out) throws IOException {
		int cases = scanner.nextInt();
		scanner.nextLine();
		scanner.println();
		
		for (int curCase = 0; curCase < cases; curCase++) {
			int N = scanner.nextInt();
			scanner.println();

			Set<String> words1 = new HashSet<String>();
			Set<String> words2 = new HashSet<String>();

			Topic[] topic = new Topic[N];
			for (int i = 0; i < N; i++) {
				Topic t = new Topic();
				t.a = scanner.next().trim();
				t.b = scanner.next().trim();
				topic[i] = t;
				words1.add(t.a);
				words2.add(t.b);
				scanner.println();
			}
			
			int size1 = words1.size();
			int size2 = words2.size();
			
			long limit = 1L << N;
			int max = 0;
			for (long a = 0; a < limit; a++) {
				words1.clear();
				words2.clear();
				long b = a;
				int clearBits = 0;
				for (int i = 0; i < N; i++) {
					if ((b & 1) == 1) {
						words1.add(topic[i].a);
						words2.add(topic[i].b);
					} else 
						clearBits ++;
					b >>= 1;
				}
				//System.out.println(a +" " + clearBits + " " +words1.size() + " " + words2.size());
				if (words1.size() == size1 && words2.size() == size2 && clearBits > max) {
					max = clearBits;
				}
			}
			
			out.println("Case #" + (curCase + 1) + ": " + max);
			
			timeMeasure(curCase, cases);
		}
		
		scanner.close();
		out.close();		
	}


	

	void calcSample() throws IOException {
		calcSample("3\r\n" + 
				"3\r\n" + 
				"HYDROCARBON COMBUSTION\r\n" + 
				"QUAIL BEHAVIOR\r\n" + 
				"QUAIL COMBUSTION\r\n" + 
				"3\r\n" + 
				"CODE JAM\r\n" + 
				"SPACE JAM\r\n" + 
				"PEARL JAM\r\n" + 
				"2\r\n" + 
				"INTERGALACTIC PLANETARY\r\n" + 
				"PLANETARY INTERGALACTIC\r\n");		
	}
	
	Round1BC() throws IOException {
		//calcSample();
		calcFile("C:\\Users\\Olaf\\Downloads\\C-small-attempt0.in", "out-C-small.txt", true);
		//calcFile("C:\\Users\\Olaf\\Downloads\\C-large.in", "out-C-large.txt", false);
	}	
	
	public static void main(String[] args) throws IOException {
		new Round1BC();
	}	
}

