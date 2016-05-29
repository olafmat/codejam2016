import java.io.*;
import java.util.*; 
import java.math.*; 

/** 
 * @author Olaf Matyja olafmat@gmail.com 
 */
public class Preparation2015_0B {
	//START boilerplate code
	
	@SuppressWarnings("unused")
	private static class ScannerCopier {
		private Scanner scanner;
		private boolean print;
		
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

	void calcFile(String fin, String fout) throws IOException {
		ScannerCopier in = new ScannerCopier(new File(fin), true);
		PrintStreamCopier out = new PrintStreamCopier(new PrintStream(fout), true);		
		process(in, out);		
	}

	long startTime = System.nanoTime();
	
	void timeMeasure(int curCase, int cases) {
		double time = ((double)(System.nanoTime() - startTime)) / 1e9;
		System.out.println("TIME: " + time + " " + time * cases / (curCase + 1));					
	}
	
	
	
	
	static class Dijkstra {
		//BEGIN
		static int MAX = 10000;
		//END
		
		static class State implements Comparable<State> {
			long cost;
			List<Integer> state;
			String history;
			
			@Override
			public int compareTo(State s) {
				if (cost > s.cost)
					return 1;
				if (cost < s.cost)
					return -1;
				int diff = state.size() - s.state.size();
				if (diff != 0)
					return diff;
				for (int n = 0; n < state.size(); n++) {
					if (state.get(n) > s.state.get(n))
						return 1;
					if (state.get(n) < s.state.get(n))
						return -1;
				}
				return 0;
			}
			
			@Override
			public boolean equals(Object o) {
				return o instanceof State && compareTo((State)o) == 0; 
			}
			
			public void propagate(Dijkstra base) {
				//BEGIN
				List<Integer> nlist = new ArrayList<Integer>();
				for (int i : state) {
					if (i > 1)
						nlist.add(i - 1);					
				}				
				base.add(cost + 1, nlist, history + ".");
				
				int k = state.size() - 1;
				int i = state.get(k);
				if (i > 2) {
					for (int i2 = 1; i2 + i2 <= i; i2++) {
						nlist = new ArrayList<Integer>();
						for (int l = 0; l < state.size(); l++) {
							if (l != k)
								nlist.add(state.get(l));							
						}
						nlist.add(i2);
						nlist.add(i - i2);
						Collections.sort(nlist);
						base.add(cost + 1, nlist, history + " " + state.get(k) + ":" + i2);
					}
				}
				//END
			}
		};
		
		Map<List<Integer>, State> map = new HashMap<List<Integer>, State>(); 
		PriorityQueue<State> queue = new PriorityQueue<State>();  	
		static long max;
		
		public State get(int n) {
			return map.get(n);
		}
		
		public void add(long cost, List<Integer> s, String history) {
			if (cost > max)
				return;
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
		
		public long run() {
			while (!queue.isEmpty()) {
				State state = queue.poll();
				if (state.state.isEmpty()) {
					System.out.println(state.cost);
					System.out.println(state.history);
					return state.cost;
				}
				state.propagate(this);
			}
			return -1;
		}
		
		public static long analyse(List<Integer> P) {
			max = sum(P);
			Dijkstra d = new Dijkstra();
			
			//BEGIN 
			d.add(0, P, "");
			//END
			
			return d.run();
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
		if (n > 1)
			factors.add(n);
		return factors;
	}

	public static int nwd(int a, int b) {
		int c;		
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
		for (int n = 0; n < arr.length; n++) {
			total += arr[n];
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
	
	Random random = new Random(1423444);
	
	//END boilerplate code
	

	
	
	
	void process(ScannerCopier scanner, PrintStreamCopier out) throws IOException {
		int cases = scanner.nextInt();
		scanner.nextLine();
		scanner.println();
		
		for (int curCase = 0; curCase < cases; curCase++) {
			int D = scanner.nextInt();
			scanner.println();
			List<Integer> P = new ArrayList<Integer>(D);
			for (int i = 0; i < D; i++) {
				P.add(scanner.nextInt());
			}
			scanner.println();
			
			Collections.sort(P);
			out.println("Case #" + (curCase + 1) + ": " + Dijkstra.analyse(P));
			
			timeMeasure(curCase, cases);
		}
		
		scanner.close();
		out.close();		
	}
	
	Preparation2015_0B() throws IOException {
		/*calcSample("3\r\n" + 
				"1\r\n" + 
				"3\r\n" + 
				"4\r\n" + 
				"1 2 1 2\r\n" + 
				"1\r\n" + 
				"4");*/
		//calcFile("C:\\Users\\Olaf\\Downloads\\B-small-practice(4).in", "practice-out-B-small.txt");
		calcFile("C:\\Users\\Olaf\\Downloads\\B-large-practice(2).in", "practice-out-B-large.txt");
	}	
	
	public static void main(String[] args) throws IOException {
		new Preparation2015_0B();
	}	
	
}
