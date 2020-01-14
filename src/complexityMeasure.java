import java.util.ArrayList;

public class complexityMeasure {

	public static void main(String[] args) {
		ArrayList<Integer> x = new ArrayList<>();
		ArrayList<Integer> y = new ArrayList<>();
		for (int mul = 1; mul < 999; mul++) {

			FibonacciHeap h = new FibonacciHeap();

			FibonacciHeap.totalCuts = 0;
			FibonacciHeap.totalLinks = 0;

			int max = 1000 * mul;
			System.out.println(max);
			FibonacciHeap.HeapNode[] arr = new FibonacciHeap.HeapNode[max + 1];

			// long start = System.nanoTime();

			for (int i = max; i >= 1; i--) {
				arr[i] = h.insert(i);
			}
			/*
			 * for (int i = 0; i < mul * 500; i++) { h.deleteMin(); }
			 */
			for (int i = 0; i < mul * 1000; i++) {
				h.deleteMin();
			}
			// long elapsedTimeNanos = System.nanoTime() - start;

			/*
			 * System.out.println(" m: " + max); System.out.println(" time: " +
			 * elapsedTimeNanos); System.out.println(" link: " +
			 * FibonacciHeap.totalLinks()); System.out.println(" cuts: " +
			 * FibonacciHeap.totalCuts()); System.out.println(" pot: " +
			 * h.potential());
			 */

			x.add(max);
			y.add(h.totalLinks);
		}
		System.out.print("[");
		for (int num : x) {
			System.out.print(num);
			System.out.print(",");
		}
		System.out.println("]");

		System.out.print("[");
		for (int num : y) {
			System.out.print(num);
			System.out.print(",");
		}
		System.out.println("]");
	}

}
