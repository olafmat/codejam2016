import java.io.*;
import java.util.*; 
import java.math.*; 

import com.joptimizer.functions.*;
import com.joptimizer.optimizers.*;

/** 
 * @author Olaf Matyja olafmat@gmail.com 
 */
public class Preparation2015_2B_LinearProgramming {
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

		private void write(String res) {
			System.out.print(res.length() > 1000 
				? res.substring(0, 100) + "..." + res.substring(res.length() - 100) 
					+ " (" + res.length()+ " len)" 
				: res);			
		}
		
		String next() {
			String res = scanner.next();
			if (print) {
				write(res);
				System.out.print(' ');
			}
			return res;
		}
		
		String nextLine() {
			String res = scanner.nextLine();
			if (print) {
				write(res);
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

        void print(char x) {
            out.print(x);
            if (print)
                System.out.print(x);
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

	static long startTime = System.nanoTime();
	
	static void timeMeasure(int curCase, int cases) {
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
	
	public static void print(int[] arr) {
		for (int n = 0; n < arr.length; n++) {
			System.out.println(arr[n]);
		}
	}

    public static void print(long[] arr) {
        for (int n = 0; n < arr.length; n++) {
            System.out.println(arr[n]);
        }
    }
    public static void print(double[] arr) {
        for (int n = 0; n < arr.length; n++) {
            System.out.println(arr[n]);
        }
    }

    public static <T> void print(Iterable<T> iter) {
		for (T t: iter) {
			System.out.println(t);
		}
	}
	
	public static long sum(int... arr) {
		long total = 0;
		for (int v : arr) {
			total += v;
		}
		return total;
	}
	
	public static long sum(long... arr) {
		long total = 0;
		for (long v : arr) {
			total += v;
		}
		return total;
	}

	public static double sum(double... arr) {
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
		
	public static int max(int... arr) {
		int max = Integer.MIN_VALUE;
		for (int v: arr) {
			if (v > max) {
				max = v;
			}
		}
		return max;
	}
	
	public static long max(long... arr) {
		long max = Long.MIN_VALUE;
		for (long v: arr) {
			if (v > max) {
				max = v;
			}
		}
		return max;
	}
	
	public static double max(double... arr) {
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
		
	public static int min(int... arr) {
		int min = Integer.MAX_VALUE;
		for (int v: arr) {
			if (v < min) {
				min = v;
			}
		}
		return min;
	}
	
	public static long min(long... arr) {
		long min = Long.MAX_VALUE;
		for (long v: arr) {
			if (v < min) {
				min = v;
			}
		}
		return min;
	}
	
	public static double min(double... arr) {
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

	static class Loop implements Iterable<int[]>, Iterator<int[]> {
		int numLevels;
		int maxVal;
		boolean last;
		int[] state;
	
		public void reset() {
			this.state = new int[numLevels];
			this.last = maxVal == 1;
		}
		
		public Loop(int numLevels, int maxVal) {
			this.numLevels = numLevels;
			this.maxVal = maxVal;
			reset();
		}
		
		public Loop(int[] state, int maxVal) {
			this.numLevels = state.length;
			this.maxVal = maxVal;
			this.state = state;
			this.last = true;
			for (int s: state) {
				if (s < maxVal - 1) {
					this.last = false;
					break;
				}
			}
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
            throw new UnsupportedOperationException();
        }

        @Override
		public Iterator<int[]> iterator() {
			return this;
		}
	}

	/*
		new Subsets(N).forEach(new Subsets.Transition() {
			@Override
			public void set(int bit, long curState) {
				//System.out.println(Long.toBinaryString(curState) + " set " + bit);			
			}

			@Override
			public void reset(int bit, long curState) {
				//System.out.println(Long.toBinaryString(curState) + " reset " + bit);			
			}			
		});
	 
	 */
	static class Subsets implements Iterable<Long>, Iterator<Long> {
		int size;
		
		long state;
		long lState;
		int nextFlipped;
		long levNextFlipped;
		
		public void reset() {
			state = 0;
			lState = 0;
			nextFlipped = -1;
			levNextFlipped = 0;
		}
		
		public Subsets(int size) {
			this.size = size;
			reset();
		}
		
		@Override
		public boolean hasNext() {
			return nextFlipped < size;
		}

		@Override
		public Long next() {
			if (nextFlipped >= 0)
				state ^= levNextFlipped;

			lState++;
			long lev = 1;
			for (int i = 0;; i++) {
				if ((lState & lev) != 0L) {
					levNextFlipped = lev;
					nextFlipped = i;
					break;
				}
				lev <<= 1;
			}
			
			return state;
		}

		@Override
		public Iterator<Long> iterator() {
			return this;
		}


        public void remove() {
            throw new UnsupportedOperationException();
        }

		static interface Transition {
			void set(int bit, long curState);
			void reset(int bit, long curState);
		}
		
		public void forEach(Transition tr) {
			for (@SuppressWarnings("unused") Long l: this) {
				if ((state & levNextFlipped) != 0L) {
					tr.reset(nextFlipped, state);
				} else {
					tr.set(nextFlipped, state);					
				}
			}
		}
	}

    static interface Swap {
        void swap(int[] stateBefore, int a, int b);
    }

    static void swap(int i, int j, int[] a) {
        int aux = a[i];
        a[i] = a[j];
        a[j] = aux;
    }


    /*
    for (int[] perm: new Permutations(3)) {
        for (int j = 0; j < perm.length; j++)
            System.out.print(perm[j] + " ");
        System.out.println();
    }

    for (int[] perm: new Permutations(3, new Swap() {
        @Override
        public void swap(int[] stateBefore, int a, int b) {
            for (int j = 0; j < stateBefore.length; j++)
                System.out.print(stateBefore[j] + " ");
            System.out.println("swap " + a + " " + b);
        }
    }));
    */

    //based on the code from http://stackoverflow.com/questions/2000048/stepping-through-all-permutations-one-swap-at-a-time
    public static class Permutations implements Iterator<int[]>, Iterable<int[]> {
        private int size;
        private int[] dir;
        private int[] state;
        private int[] next;
        private Swap swapListener;

        public void reset() {
            for (int i = 0; i < size; i++) {
                state[i] = i;
                dir[i] = i == 0 ? 0 : -1;
            }
            next = state;
        }

        public Permutations(int size, Swap swapListener) {
            this.size = size;
            this.state = new int[size];
            this.dir = new int[size];
            this.swapListener = swapListener;
            reset();
        }

        public Permutations(int size) {
            this(size, null);
        }

        private int[] getNext() {
            if (next != null)
                return next;

            int max = Integer.MIN_VALUE;
            int s1 = -1;
            for (int i = 0; i < size; i++) {
                if (dir[i] != 0 && state[i] > max) {
                    max = state[i];
                    s1 = i;
                }
            }

            if (s1 == -1) {
                if (swapListener != null)
                    swapListener.swap(state, s1, -1);
                return null;
            }

            int s2 = s1 + dir[s1];
            if (swapListener != null)
                swapListener.swap(state, s1, s2);
            swap(s1, s2, dir);
            swap(s1, s2, state);

            if (s2 <= 0 || s2 >= size - 1 || state[s2 + dir[s2]] > max)
                dir[s2] = 0;

            for (int i = 0; i < size; i++) {
                if (state[i] > max)
                    if (i < s2)
                        dir[i] = 1;
                    else
                        dir[i] = -1;
            }

            next = state;
            return next;
        }

        @Override
        public int[] next() {
            int[] r = getNext();
            next = null;
            return r;
        }

        @Override
        public boolean hasNext() {
            return getNext() != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
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
	
	
	void process(ScannerCopier scanner, PrintStreamCopier out) {
		int cases = scanner.nextInt();
		scanner.nextLine();
		scanner.println();
		
		for (int curCase = 0; curCase < cases; curCase++) {
			int N = scanner.nextInt();
			double V = scanner.nextDouble();
			double X = scanner.nextDouble();
			scanner.println();

			double[] R = new double[N];
			double[] C = new double[N];
			for (int i = 0; i < N; i++) {
				R[i] = scanner.nextDouble();
				C[i] = scanner.nextDouble();
				scanner.println();
			}

			// Objective function (variables y0, y1, z)
			double[] n = new double[N];
			n[N - 1] = V;
			LinearMultivariateRealFunction objectiveFunction = new LinearMultivariateRealFunction(n, 0);

			//inequalities (G.y-h.z<0, z>0)
			double[][] Gmh = new double[N + 1][];
			for (int i = 0; i < N; i++) {
				Gmh[i] = new double[N + 2];
				Gmh[i][i] = -1;
				Gmh[i][N + 1] = -1e-10;
			}
			Gmh[N] = new double[N + 2];
			Gmh[N][N] = -1;
			Gmh[N][N + 1] = 0;
			
			ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[N + 1];
			for (int i=0; i < N + 1; i++){
				inequalities[i] = new LinearMultivariateRealFunction(Gmh[i], 0);
			}

			double[][] Amb = new double[1][];			
			Amb[0] = new double[N + 1];
			for (int i = 0; i < N; i++) {
				Amb[0][i] = C[i] * R[i];				
			}			
			
			//equalities (e.y+f.z=1)
			double[] bm= new double[]{X};
			
			double[] init = new double[N + 1];
			init[N] = -1;
			
			//optimization problem
			OptimizationRequest or = new OptimizationRequest();
			or.setF0(objectiveFunction);
			or.setA(Amb);
			or.setB(bm);
			or.setFi(inequalities);
			or.setTolerance(1.E-9);
			or.setToleranceFeas(1.E-9);
			//or.setInitialPoint(init);
			
			//optimization
			JOptimizer opt = new JOptimizer();
			opt.setOptimizationRequest(or);
			try {
				int returnCode = opt.optimize();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double[] sol = opt.getOptimizationResponse().getSolution();
			
			double sum = 0;
			for (int i = 0; i < N; i++) {
				sum += R[i] * sol[i] / sol[N];				
			}
			double time = V / sum;
			
			out.println("Case #" + (curCase + 1) + ": " + time);
			
			timeMeasure(curCase, cases);
		}
		
		scanner.close();
		out.close();		
	}


	

	void calcSample() throws IOException {
		calcSample("6\r\n" + 
				"1 10.0000 50.0000\r\n" + 
				"0.2000 50.0000\r\n" + 
				"2 30.0000 65.4321\r\n" + 
				"0.0001 50.0000\r\n" + 
				"100.0000 99.9000\r\n" + 
				"2 5.0000 99.9000\r\n" + 
				"30.0000 99.8999\r\n" + 
				"20.0000 99.7000\r\n" + 
				"2 0.0001 77.2831\r\n" + 
				"0.0001 97.3911\r\n" + 
				"0.0001 57.1751\r\n" + 
				"2 100.0000 75.6127\r\n" + 
				"70.0263 75.6127\r\n" + 
				"27.0364 27.7990\r\n" + 
				"3 5000.0000 75.0000\r\n" + 
				"10.0000 30.0000\r\n" + 
				"20.0000 50.0000\r\n" + 
				"300.0000 95.0000\r\n" + 
				"40.0000 2.0000"
				);		
	}
	
	Preparation2015_2B_LinearProgramming() throws IOException {
		calcSample();
		//calcFile("C:\\Users\\Olaf\\Downloads\\A-small-attempt0.in", "out-A-small.txt", true);
		//calcFile("C:\\Users\\Olaf\\Downloads\\A-large.in", "out-A-large.txt", false);
	}	
	
	public static void main(String[] args) throws IOException {
		new Preparation2015_2B_LinearProgramming();
	}
}
