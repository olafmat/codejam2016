import java.io.*;
import java.util.*; 
import java.math.*; 

/** 
 * @author Olaf Matyja olafmat@gmail.com 
 */
public class Preparation2015_1CB {
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

	long startTime = System.nanoTime();
	
	void timeMeasure(int curCase, int cases) {
		double time = ((double)(System.nanoTime() - startTime)) / 1e9;
		System.out.println("TIME: " + time + " " + time * cases / (curCase + 1));					
	}

	public static String revStr(String s) {
		StringBuffer buf = new StringBuffer();
		for (int n = s.length() - 1; n >= 0; n--) {
			buf.append(s.charAt(n));
		}
		return buf.toString();
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
				state.history = state.history + " " + history;
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
			for (int n = 2; n <= MAX; n++) {
				long cur = d.get(n).cost;
				long prev = d.get(n - 1).cost;
				{
					System.out.println(n + "\t" + cur + "\t" + d.get(n).history);
				}
			}
			//END
		}
	};
		
	
	
	class State {
		int done;
		String hist;
		double p;
	};
	
	
	int cases;
	
	void process(ScannerCopier scanner, PrintStreamCopier out) throws IOException {
		cases = scanner.nextInt();
		scanner.nextLine();
		scanner.println();
		
		for (int curCase = 0; curCase < cases; curCase++) {
			int K = scanner.nextInt();
			int L = scanner.nextInt();
			int S = scanner.nextInt();
			scanner.println();
			String keyboard = scanner.next().trim();
			scanner.println();
			String target = scanner.next().trim();
			scanner.println();
			
			int klen = keyboard.length();
			int tlen = target.length();
			double pr = 1.0 / klen;			
			
			List<State> states = new ArrayList<State>();
			State state = new State();
			state.done = 0;
			state.p = 1;
			state.hist = "";
			states.add(state);
			
			for (int s = 0; s < S; s++) {
				List<State> nstates = new ArrayList<State>();
				for (int j = 0; j < klen; j++) {
					char c = keyboard.charAt(j);
					for (State st: states) {
						State nstate = new State();
						nstate.p = st.p * pr;
						nstate.done = st.done;
						nstate.hist = st.hist + c;
						while (nstate.hist.length() > tlen || !target.substring(0, nstate.hist.length()).equals(nstate.hist))
							nstate.hist = nstate.hist.substring(1);
						for (int k = tlen; k > 0; k--) {
							if (k <= nstate.hist.length() && target.substring(0, k).equals(nstate.hist.substring(nstate.hist.length() - k))) {
								if (k == tlen) {
									nstate.done++;
								}
								break;
							}
						}
						boolean found = false;
						for (State stat: nstates) {
							if (stat.done == nstate.done && stat.hist.equals(nstate.hist)) {
								stat.p += nstate.p;
								found = true;
								break;
							}
						}					
						if (!found) {
							nstates.add(nstate);
						}
					}					
				}
				states = nstates;
			}			
			
			int max = 0;
			double p = 0;
			for (State st: states) {
				if (st.done > max) {
					max = st.done;
				}
				p += st.p * st.done;
			}
			
			out.println("Case #" + (curCase + 1) + ": " + (max - p));
			timeMeasure(curCase, cases);
		}
		
		scanner.close();
		out.close();		
	}
	
	void calcSample() throws IOException {
		String sampleText = "5\r\n" + 
				"7 6 6\r\n" + 
				"BANANAS\r\n" + 
				"MONKEY\r\n" + 
				"2 3 4\r\n" + 
				"AA\r\n" + 
				"AAA\r\n" + 
				"2 1 2\r\n" + 
				"AB\r\n" + 
				"B\r\n" + 
				"6 2 2\r\n" + 
				"GOOGLE\r\n" + 
				"GO\r\n" + 
				"26 11 100\r\n" + 
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ\r\n" + 
				"ROSENCRANTZ"; 
		ScannerCopier in = new ScannerCopier(sampleText, true);
		PrintStreamCopier out = new PrintStreamCopier(System.out, false);		
		process(in, out);		
	}

	Preparation2015_1CB() throws IOException {
		//calcSample();
		//calcFile("C:\\Users\\Olaf\\Downloads\\B-small-practice(3).in", "practice-out-B-small.txt");
		calcFile("C:\\Users\\Olaf\\Downloads\\B-large-practice(1).in", "practice-out-B-large.txt");
	}	
	
	public static void main(String[] args) throws IOException {
		new Preparation2015_1CB();
	}	
	
}
