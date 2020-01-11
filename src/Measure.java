import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Measure {

	public static void main(String[] args) {

		for (int exp = 10; exp < 13; exp++) {

			FibonacciHeap h = new FibonacciHeap();

			FibonacciHeap.totalCuts = 0;
			FibonacciHeap.totalLinks = 0;

			int max = (int) Math.pow(2, exp);
			FibonacciHeap.HeapNode[] arr = new FibonacciHeap.HeapNode[max + 1];

			long start = System.nanoTime();

			for (int i = max; i >= 0; i--) {
				arr[i] = h.insert(i);
			}
			h.deleteMin();

			// Print(h);

			for (int i = 0; i <= exp - 2; i++) {

				double sum = 0;
				for (int k = 1; k <= i; k++) {
					sum += Math.pow(0.5, k);
				}
				h.decreaseKey(arr[(int) (max * sum + 2)], 2 * max);

			}

			h.decreaseKey(arr[max - 1], 2 * max);

			// Print(h);

			long elapsedTimeNanos = System.nanoTime() - start;

			// System.out.println(h.getStart().getPrev().getChild());

			System.out.println(" m: " + max);
			System.out.println(" time: " + elapsedTimeNanos);
			System.out.println(" link: " + FibonacciHeap.totalLinks());
			System.out.println(" cuts: " + FibonacciHeap.totalCuts());
			System.out.println(" pot: " + h.potential());
			// System.out.println(h.numMarked);
		}
		for (int mul = 1; mul < 4; mul++) {

			FibonacciHeap h = new FibonacciHeap();

			FibonacciHeap.totalCuts = 0;
			FibonacciHeap.totalLinks = 0;

			int max = 1000 * mul;
			FibonacciHeap.HeapNode[] arr = new FibonacciHeap.HeapNode[max + 1];

			long start = System.nanoTime();

			for (int i = max; i >= 1; i--) {
				arr[i] = h.insert(i);
			}
			for (int i = 0; i < mul * 500; i++) {
				h.deleteMin();
			}

			long elapsedTimeNanos = System.nanoTime() - start;

			// System.out.println(h.getStart().getPrev().getChild());

			System.out.println(" m: " + max);
			System.out.println(" time: " + elapsedTimeNanos);
			System.out.println(" link: " + FibonacciHeap.totalLinks());
			System.out.println(" cuts: " + FibonacciHeap.totalCuts());
			System.out.println(" pot: " + h.potential());
			// System.out.println(h.numMarked);
		}
	}

	public static void Print(FibonacciHeap heap) {
		HashMap<Integer, Integer> spacesBeforeKey = new HashMap<>();
		if (!heap.isEmpty()) {
			assignSpaces(heap.first, spacesBeforeKey, 0);
			List<FibonacciHeap.HeapNode> nodesQueue = new ArrayList<>();
			FibonacciHeap.HeapNode currNode = heap.first;
			do {
				nodesQueue.add(currNode);
				currNode = currNode.getNext();
			} while (currNode != heap.first);

			int depth = 0;
			while (!nodesQueue.isEmpty()) {
				List<FibonacciHeap.HeapNode> children = new ArrayList<>();
				StringBuilder builderTop = new StringBuilder();
				StringBuilder builderConnectors = new StringBuilder();
				StringBuilder builderKeys = new StringBuilder();

				int spaces = 0;
				int spacesConnectorLine = 0;
				for (FibonacciHeap.HeapNode node : nodesQueue) {
					boolean firstChild = false;
					if (depth > 0) {
						firstChild = node == node.getParent().getChild();
						int spacesBefore = spacesBeforeKey.get(node.getKey());
						if (firstChild) {
							addSpaces(builderTop,
									spacesBefore - spacesConnectorLine, ' ');
							FibonacciHeap.HeapNode lastChild = node.getPrev();
							int lastChildSpacesBefore = spacesBeforeKey
									.get(lastChild.getKey());
							builderTop.append("|");

							if (lastChildSpacesBefore - spacesBefore - 1 > 0) {
								addSpaces(builderTop, lastChildSpacesBefore
										- spacesBefore - 1, '_');
								builderTop.append(" ");
							}
						}
						addSpaces(builderConnectors,
								spacesBefore - spacesConnectorLine, ' ');
						spacesConnectorLine = spacesBefore + 1;
						builderConnectors.append("|");
					}

					int spacesBefore = spacesBeforeKey.get(node.getKey());
					char spaceChar = firstChild ? ' ' : '-';
					addSpaces(builderKeys, spacesBefore - spaces, spaceChar);
					spaces = spacesBefore
							+ (int) (Math.log10(node.getKey()) + 1);
					builderKeys.append(node.getKey());

					FibonacciHeap.HeapNode child = node.getChild();
					if (child != null) {
						do {
							children.add(child);
							child = child.getNext();
						} while (child != node.getChild());
					}
				}
				System.out.println(builderTop);
				System.out.println(builderConnectors);
				System.out.println(builderKeys);
				depth++;
				nodesQueue = children;
			}
		}
	}

	private static void addSpaces(StringBuilder sb, int amount, char c) {
		for (int i = 0; i < amount; i++) {
			sb.append(c);
		}
	}

	private static int assignSpaces(FibonacciHeap.HeapNode node,
			HashMap<Integer, Integer> spacesBeforeKey, int spacesBefore) {
		int spaces = 0;
		if (node != null) {
			FibonacciHeap.HeapNode currNode = node;
			do {
				spacesBeforeKey.put(currNode.getKey(), spacesBefore + spaces);
				if (currNode.getChild() != null) {
					spaces += assignSpaces(currNode.getChild(), spacesBeforeKey,
							spacesBefore + spaces);
				} else {
					int digitsNum = (int) (Math.log10(currNode.getKey()) + 1);
					spaces += digitsNum + 1;
				}
				currNode = currNode.getNext();
			} while (currNode != node);
		}
		return spaces;
	}

}
