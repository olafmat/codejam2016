import java.io.*;
import java.util.*; 
import java.math.*; 

/** 
 * @author Olaf Matyja olafmat@gmail.com 
 */
public class Preparation2015_1BB {

	int cases;
	
	int solve(int R, int C, int N) {
		boolean[][] rows = new boolean[R][]; 
		for (int i = 0; i < R; i++) {
			rows[i] = new boolean[C];
		}
		long max = 1L << (R * C);
		int bestP = Integer.MAX_VALUE;
		long bestS = 0;
		for (long p = 0; p < max; p++) {
			long k = p;
			int n = N;
			for (int i = 0; i < R && n >= 0; i++) {
				for (int j = 0; j < C && n >= 0; j++) {
					rows[i][j] = k % 2 == 1;
					if (rows [i][j])
						n--;
					k >>= 1;
				}
			}
			if (n == 0) {
				int penalty = 0;
				for (int i = 0; i < R; i++) {
					for (int j = 1; j < C; j++) {
						if (rows[i][j-1] && rows[i][j]) {
							penalty++;
						}
					}
				}
				for (int j = 0; j < C; j++) {
					for (int i = 1; i < R; i++) {
						if (rows[i-1][j] && rows[i][j]) {
							penalty++;
						}
					}
				}
				if (penalty < bestP) {
					bestP = penalty;
					bestS = p;
				}
			}
		}

		long k = bestS;
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (k % 2 == 1) 
					System.out.print('X');
				else
					System.out.print('-');
				k >>= 1;
			}
			System.out.println();
		}
		System.out.println(bestP);

		return bestP;
	}
	
	int get(boolean[][] rows, int R, int C, int i, int j) {
		if (i < 0 || i >= R || j < 0 || j >= C) {
			return 0;
		}
		return rows[i][j] ? 1 : 0;
	}
	
	int getPenalty(int R, int C, int N) {
		System.err.println(R + "\t" + C + "\t" + N);
		int penalty = 0;
		boolean[][] rows = new boolean[R][]; 
		for (int i = 0; i < R; i++) {
			rows[i] = new boolean[C];
		}
		
		for (int n = 0; n < N; n++) {
			int bestI = 0;
			int bestJ = 0;
			int bestN = Integer.MAX_VALUE;
			for (int i = 0; i < R; i++) {
				for (int j = 0; j < C; j++) {
					if (rows[i][j])
						continue;
					int neigh = 
						get(rows, R, C, i - 1, j) +
						get(rows, R, C, i + 1, j) +
						get(rows, R, C, i, j - 1) + 
						get(rows, R, C, i, j + 1);
					if (neigh < bestN) {
						bestI = i;
						bestJ = j;
						bestN = neigh;
					}
				}
			}
			rows[bestI][bestJ] = true;
		}
		
		for (int i = 0; i < R; i++) {
			for (int j = 1; j < C; j++) {
				if (rows[i][j-1] && rows[i][j]) {
					penalty++;
				}
			}
		}
		for (int j = 0; j < C; j++) {
			for (int i = 1; i < R; i++) {
				if (rows[i-1][j] && rows[i][j]) {
					penalty++;
				}
			}
		}
		if ((R == 3 || C == 3) && ((R + C) % 2 == 0)) {
			int width = Math.max(R, C);			
			int min = (width*3 + 1) / 2 + 3;
			int max = (width*5 + 1) / 2;
			if (N >= min && N <= max)
				penalty--;
		}
		return penalty;
	}
	
	void process(Scanner scanner, PrintStream out) throws IOException {
		cases = scanner.nextInt();
		scanner.nextLine();
		
		for (int curCase = 0; curCase < cases; curCase++) {
			int R = scanner.nextInt();
			int C = scanner.nextInt();
			int N = scanner.nextInt();
			
			out.println("Case #" + (curCase + 1) + ": " + getPenalty(R, C, N));
		}
	}
	
	Preparation2015_1BB() throws IOException {
		/*String sampleText = "4\r\n" + 
				"2 3 6\r\n" + 
				"4 1 2\r\n" + 
				"3 3 8\r\n" + 
				"5 2 0"; 
		Scanner in = new Scanner(sampleText);
		PrintStream out = System.out;*/
		
		
		/*Scanner in = new Scanner(new File("C:\\Users\\Olaf\\Downloads\\B-small-practice(1).in"));
		PrintStream out = new PrintStream("practice-out-B-small.txt");*/
		
		
		Scanner in = new Scanner(new File("C:\\Users\\Olaf\\Downloads\\B-large-practice.in"));
		PrintStream out = new PrintStream("practice-out-B-large.txt");
		

		
		process(in, out);
		
		in.close();
		out.close();
		
		/*for (int n = 0; n <= 27; n++) {
			solve(3, 9, n);
			System.out.println(getPenalty(3, 9, n));
		}*/
	}	
	
	public static void main(String[] args) throws IOException {
		new Preparation2015_1BB();
	}	
	
}
