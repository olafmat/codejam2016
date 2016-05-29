import java.io.*;
import java.util.*; 
import java.math.*; 

/** 
 * @author Olaf Matyja olafmat@gmail.com 
 */
public class Preparation2014_2D {
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

	public static int find(List<String> list, String s) {
		for (int n = 0; n < list.size(); n++) {
			if (list.get(n).equals(s))
				return n;
		}
		return -1;
	}
	
	public static int findOrAdd(List<String> list, String s) {
		int ind = find(list, s);
		if (ind < 0) {
			ind = list.size();
			list.add(s);
		}
		return ind;
	}	
	
	static interface Iteration {
		void run(int[] state);
	}
	
	static class Loop implements Iterable<int[]>, Iterator<int[]> {
		int numLevels;
		int maxVal;
		boolean last;
		int[] state;
		
		public Loop(int numLevels, int maxVal) {
			this.numLevels = numLevels;
			this.maxVal = maxVal;
			this.state = new int[numLevels];
		}
		
		@Override
		public boolean hasNext() {
			return !last;
		}

		@Override
		public int[] next() {
			int i;
			for (i = 0; i < numLevels; i++) {
				state[i]++;
				if (state[i] >= maxVal) {
					state[i] = 0;
				} else {
					break;
				}
			}
			last = i >= numLevels;
			return state;
		}

		public void remove() {
		}

		@Override
		public Iterator<int[]> iterator() {
			return this;
		}
	}
		
	public static void assert_(boolean expr) {
		assert_("", expr);
	}
	
	public static void assert_(String txt, boolean expr) {
		if (!expr)
			throw new RuntimeException(txt);
			//System.out.println("ERR " + txt);
	}

	public static void log(String s) {
		System.out.println(s);
	}
	
	public static Random random = new Random(1423444);
	
	//END boilerplate code
	//10^11 30.2s
	//4 min small, 8 min large
	//7e9 small, 14e9 large
	

	Map<String, BitSet> map = new HashMap<String, BitSet>();
	int bits = 0;

	BitSet find(String s) {
		BitSet bs = map.get(s);
		if (bs != null)
			return bs;
		
		bs = new BitSet();
		bs.set(bits);
		bits++;
		if (!s.isEmpty()) {
			bs.or(find(s.substring(0, s.length() - 1)));
		}
		map.put(s, bs);
		return bs;
	}

	int best;
	long num;
	void process(ScannerCopier scanner, PrintStreamCopier out) throws IOException {
		int cases = scanner.nextInt();
		scanner.nextLine();
		scanner.println();
		
		for (int curCase = 0; curCase < cases; curCase++) {
			final int M = scanner.nextInt();
			final int N = scanner.nextInt();
			scanner.println();
			
			final BitSet[] words = new BitSet[M];
			for (int i = 0; i < M; i++) {
				String s = scanner.next().trim();
				BitSet bs = find(s);
				words[i] = bs;
				scanner.println();
			}

			final BitSet[] b = new BitSet[N];
			final BitSet[] b1 = new BitSet[N];
			for (int i = 0; i < N; i++) {
				b[i] = new BitSet();
				b1[i] = new BitSet();
			}

			long num2 = 1;
			for (int i = 0; i < M; i++) {
				int bestj = 0;
				int best1 = 0;
				long num1 = 0;
				for (int j = 0; j < N; j++) {
					BitSet w = new BitSet();
					w.or(words[i]);
					w.or(b1[j]);
					int sum = w.cardinality() - b1[j].cardinality();
					if (sum > best1) {
						best1 = sum;
						num1 = 1;
						bestj = j;
					} else if (sum == best1) {
						num1 = ((num1 + 1) % 1000000007L);
					}
				}
				b1[bestj].or(words[i]);				
				num2 = (num2 * num1) % 1000000007L;
			}

			int best2 = 0;
			for (int j = 0; j < N; j++) {
				best2 += b1[j].cardinality();
			}
			
			best = 0;
			num = 0;
			for (int[] state: new Loop(M, N)) {
				for (int j = 0; j < N; j++) {
					b[j].clear();
				}
				
				for (int i = 0; i < M; i++) {
					b[state[i]].or(words[i]);
				}
				
				int sum = 0;
				for (int j = 0; j < N; j++) {
					sum += b[j].cardinality();
				}
				if (sum > best) {
					best = sum;
					num = 1;
				} else if (sum == best) {
					num = ((num + 1) % 1000000007L);
				}
			};
			
			out.println("Case #" + (curCase + 1) + ": " + best + " " + (num % 1000000007L) + " " + best2 + " " + (num2 % 1000000007L));
			
			timeMeasure(curCase, cases);
		}
		
		scanner.close();
		out.close();		
	}


	

	void calcSample() throws IOException {
		calcSample("2\r\n" + 
				"4 2\r\n" + 
				"AAA\r\n" + 
				"AAB\r\n" + 
				"AB\r\n" + 
				"B\r\n" + 
				"5 2\r\n" + 
				"A\r\n" + 
				"B\r\n" + 
				"C\r\n" + 
				"D\r\n" + 
				"E\r\n");		
	}
	
	Preparation2014_2D() throws IOException {
		//calcSample();
		calcFile("C:\\Users\\Olaf\\Downloads\\D-small-practice.in", "out-D-small.txt", true);
		//calcFile("C:\\Users\\Olaf\\Downloads\\A-large.in", "out-A-large.txt", false);
	}	
	
	public static void main(String[] args) throws IOException {
		new Preparation2014_2D();
	}	
}

