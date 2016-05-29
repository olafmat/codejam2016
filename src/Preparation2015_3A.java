import java.io.*;
import java.util.*; 
import java.math.*; 

/** 
 * @author Olaf Matyja olafmat@gmail.com 
 */
public class Preparation2015_3A {
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
		
	Random random = new Random(1423444);
	
	//END boilerplate code
	

	
	
	static class Emp implements Comparable<Emp> {
		Emp manager;
		List<Emp> workers = new ArrayList<Emp>();
		int salary;
		int min;
		int max;
		boolean employed;
		
		void subTree() {
			for (Emp sub : workers) {
				if (min < sub.min)
					sub.min = min; 
				if (max > sub.max)
					sub.max = max;
				sub.subTree();
			}
		}

		@Override
		public int compareTo(Emp o) {
			if (salary < o.salary)
				return -1;
			if (salary > o.salary)
				return 1;
			return 0;
		}
		
		
	}
	
	Emp[] emps;

	int curSize;
	Set<Emp> leaves;
	
	void check(boolean exp, String str) {
		if (!exp)
			new Exception(str).printStackTrace();
	}
	
	void updateTree(int minSal, int maxSal) {
		List<Emp> toRemove = new ArrayList<Emp>();
		List<Emp> toAdd = new ArrayList<Emp>();
		Set<Emp> toCheck = new HashSet<Emp>();
		toCheck.addAll(leaves);
		while (!toCheck.isEmpty()) {
			toRemove.clear();
			toAdd.clear();
			for (Emp emp : toCheck) {
				if ((emp.min < minSal || emp.max > maxSal) && emp.employed) {
					emp.employed = false;
					toRemove.add(emp);
					curSize--;
					toAdd.add(emp.manager);
				}
			}
			toCheck.clear();
			toCheck.addAll(toAdd);
			leaves.removeAll(toRemove);
			leaves.addAll(toCheck);
		}
		toCheck.clear();
		toCheck.addAll(leaves);
		while (!toCheck.isEmpty()) {
			toRemove.clear();
			toAdd.clear();
			for (Emp emp : toCheck) {
				int subs = 0;
				for (Emp sub: emp.workers) {
					if (sub.min >= minSal && sub.max <= maxSal) {
						subs++;
						if (!sub.employed) {
							sub.employed = true;
							curSize++;
							toAdd.add(sub);
						}
					}
					if (subs == emp.workers.size()) {
						toRemove.add(emp);
					}
				}
			}
			toCheck.clear();
			toCheck.addAll(toAdd);
			leaves.removeAll(toRemove);
			leaves.addAll(toCheck);
		}
		
		/*int s = 0;
		for (Emp emp: emps) 
			if (emp.employed) {
				check(emp.salary >= minSal && emp.salary <= maxSal, "");
				s++;
				for (Emp sub: emp.workers) {
					check(sub.salary < minSal || sub.salary > maxSal || sub.employed, "");							
				}
			}
		check(s == curSize, "" + s + "\t" + curSize);*/
	}
	
	int calc(int D) {
		curSize = 1;
		leaves = new HashSet<Emp>();
		int best = 0;
		int chairSal = emps[0].salary;
		emps[0].employed = true;
		leaves.add(emps[0]);
		
		for (int minSal = chairSal - D; minSal <= chairSal; minSal++) {
			//System.out.println(minSal + "\t" + curSize + "\t" + chairSal);
			updateTree(minSal, minSal + D);
			if (curSize > best)
				best = curSize;
		}
		
		return best;
	}
	
	void process(ScannerCopier scanner, PrintStreamCopier out) throws IOException {
		int cases = scanner.nextInt();
		scanner.nextLine();
		scanner.println();
		
		for (int curCase = 0; curCase < cases; curCase++) {
			int N = scanner.nextInt();
			emps = new Emp[N];
			int D = scanner.nextInt();
			scanner.println();
			int S0 = scanner.nextInt();
			int As = scanner.nextInt();
			int Cs = scanner.nextInt();
			int Rs = scanner.nextInt();
			scanner.println();
			int M0 = scanner.nextInt();
			int Am = scanner.nextInt();
			int Cm = scanner.nextInt();
			int Rm = scanner.nextInt();
			scanner.println();
			
			for (int i = 0; i < N; i++) {
				emps[i] = new Emp();
			}
			emps[0].salary = emps[0].min = emps[0].max = S0;

			for (int i = 1; i < N; i++) {
				emps[i].salary = emps[i].min = emps[i].max = (emps[i - 1].salary * As + Cs) % Rs;
				M0 = (M0 * Am + Cm) % Rm;
				emps[i].manager = emps[M0 % i];
				emps[M0 % i].workers.add(emps[i]);
			}
			
			emps[0].subTree();
			
			int saved = calc(D);
			
			out.println("Case #" + (curCase + 1) + ": " + saved);			
			timeMeasure(curCase, cases);
		}
		
		scanner.close();
		out.close();		
	}


	

	void calcSample() throws IOException {
		calcSample("3\r\n" + 
				"1 395\r\n" + 
				"18 246 615815 60\r\n" + 
				"73 228 14618 195\r\n" + 
				"6 5\r\n" + 
				"10 1 3 17\r\n" + 
				"5 2 7 19\r\n" + 
				"10 13\r\n" + 
				"28 931 601463 36\r\n" + 
				"231 539 556432 258");		
	}
	
	Preparation2015_3A() throws IOException {
		//calcSample();
		//calcFile("C:\\Users\\Olaf\\Downloads\\A-small-practice.in", "practice-out-A-small.txt", true);
		calcFile("C:\\Users\\Olaf\\Downloads\\A-large-practice.in", "practice-out-A-large.txt", false);
	}	
	
	public static void main(String[] args) throws IOException {
		new Preparation2015_3A();
	}	
}

