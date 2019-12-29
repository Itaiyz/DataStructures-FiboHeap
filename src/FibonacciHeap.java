/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 * 
 * Submitted by:
 *
 * Itai Zemah itaizemah 209637453
 *
 * Oded Carmon odedcarmon 208116517
 * 
 */
public class FibonacciHeap {

	protected int size = 0;
	protected HeapNode min = null;
	protected int numTrees = 0;
	protected int numMarked = 0;
	protected static int totalLinks = 0;
	protected static int totalCuts = 0;
	protected int maxRank = 0;

	// For deleting an arbitrary key
	protected static final int NEG_INFTY = Integer.MIN_VALUE;

	/**
	 * public boolean isEmpty()
	 *
	 * precondition: none
	 * 
	 * The method returns true if and only if the heap is empty.
	 * 
	 * Calls size() which is O(1)
	 * 
	 * Complexity: O(1)
	 * 
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * public HeapNode insert(int key)
	 *
	 * Creates a node (of type HeapNode) which contains the given key, and
	 * inserts it into the heap.
	 * 
	 * Calls meld which is O(1)
	 * 
	 * Complexity: O(1)
	 * 
	 */
	public HeapNode insert(int key) {

		FibonacciHeap heap2 = new FibonacciHeap();
		HeapNode newNode = new HeapNode(key);
		heap2.min = newNode;
		heap2.size = 1;
		heap2.numTrees = 1;

		this.meld(heap2);

		return newNode;
	}

	/**
	 * protected HeapNode bruteFindMin()
	 * 
	 * Sequentially scans all roots in heap and looks for minimum among them
	 * 
	 * Complexity: O(n)
	 * 
	 */
	protected HeapNode bruteFindMin() {
		int currentMinKey = NEG_INFTY;
		HeapNode currentMin = this.findMin();
		HeapNode startNode = this.findMin();
		HeapNode node = startNode;
		do {
			if (node.getKey() < currentMinKey) {
				currentMinKey = node.getKey();
				currentMin = node;
			}
			node = node.getNext();
		} while (node != startNode);

		return currentMin;
	}

	/**
	 * public void deleteMin()
	 *
	 * Delete the node containing the minimum key.
	 * 
	 * Calls successiveLinking which is O(n)
	 * Calls bruteFindMin which is O(n)
	 * 
	 * Complexity: O(n)
	 *
	 */
	public void deleteMin() {
		if (isEmpty()) {
			return;
		}

		HeapNode minNode = this.findMin();
		if (minNode.getChild() != null) {

			FibonacciHeap heap2 = new FibonacciHeap();

			// Find minimum child
			heap2.min = minNode.getChild();
			heap2.bruteFindMin();
			// Removing parent pointers, unmarking nodes
			HeapNode startNode = heap2.findMin();
			HeapNode node = startNode;
			do {
				node.setParent(null);
				// If node was marked, it became a root and so is now unmarked
				if (node.isMark()) {
					heap2.numMarked -= 1;
					node.setMark(false);
				}
				node = node.getNext();
			} while (node != startNode);

			// Adding minNode's children but removing minNode
			heap2.numTrees = minNode.getRank() - 1;
			heap2.size = -1;

			this.meld(heap2);

			// Removing minNode from root list
			minNode.getPrev().setNext(minNode.getNext());
			minNode.getNext().setPrev(minNode.getPrev());

		} else {
			this.size -= 1;
			this.numTrees -= 1;
			if (size > 0) {
				// Removing minNode from root list
				minNode.getPrev().setNext(minNode.getNext());
				minNode.getNext().setPrev(minNode.getPrev());
				this.successiveLinking();
			} else { // Minimum was only node
				this.min = null;
			}

		}

	}

	/**
	 * protected void successiveLinking()
	 * 
	 * Performs one-pass successive linking as described on slides 53 - 56 of
	 * https://www.cs.tau.ac.il/~schechik/Data-Structures-2020/Binomial-Fibonacci-heaps.pptx
	 * 
	 * Also finds new minimum.
	 * 
	 * Complexity: O(n)
	 */
	protected void successiveLinking() {
		HeapNode[] arr = new HeapNode[maxRank + 1];

		// Current min shouldnt be part of root list so we first go to its next
		// sibling
		this.min = this.findMin().getNext();
		// We already pay for iterating through all trees, no problem
		// (asymptotically) to brute find min right now
		this.bruteFindMin();
		HeapNode startNode = this.findMin();
		HeapNode node = startNode;

		do {
			// Only if node hasn't been linked to someone already
			if (node.getParent() == null) {
				if (arr[node.getRank()] == null) {
					arr[node.getRank()] = node;
				} else {
					this.link(node, arr[node.getRank()]);
					if (node.getParent() == null) {
						arr[node.getRank() - 1] = null;
					} else {
						node = node.getParent();
						arr[node.getRank() - 1] = null;
					}
				}

				// Update maxRank
				if (node.getRank() > maxRank) {
					this.maxRank = node.getRank();
				}
			} else {
				throw new RuntimeException(
						"We shouldn't get here, successive link reached a non-root");
			}
			node = node.getNext();
		}
		// min won't be linked under anyone because it is minimal so we
		// can check if we finished iterating with
		while (node != startNode);

		// Update min
		this.bruteFindMin();

		return;
	}

	/**
	 * protected void link(HeapNode node, HeapNode heapNode)
	 * 
	 * A link operation is the operation which gets as input two trees of the
	 * same rank, and generates a tree of rank bigger by one, by hanging the
	 * tree which has larger value in its root on the tree which has smaller
	 * value in its root.
	 * 
	 * Complexity: O(1)
	 * 
	 */
	protected void link(HeapNode node1, HeapNode node2) {

		this.numTrees -= 1;

		if (node1.getRank() == this.maxRank) {
			this.maxRank += 1;
		}

		HeapNode small;
		HeapNode large;

		if (node1.getKey() < node2.getKey()) {
			small = node1;
			large = node2;
		} else {
			small = node2;
			large = node1;
		}

		large.setParent(small);
		// Removing large from root list
		large.getPrev().setNext(large.getNext());
		large.getNext().setPrev(large.getPrev());

		large.setNext(large);
		large.setPrev(large);

		// Inserting large into small's children
		if (small.getChild() == null) {
			small.setChild(large);
		} else {
			large.setPrev(small.getChild());
			large.setNext(small.getChild().getNext());
			small.getChild().getNext().setPrev(large);
			small.getChild().setNext(large);
		}
		small.setRank(small.getRank() + 1);

		return;
	}

	/**
	 * public HeapNode findMin()
	 *
	 * Return the node of the heap whose key is minimal.
	 * 
	 * Complexity: O(1)
	 *
	 */
	public HeapNode findMin() {
		return this.min;
	}

	/**
	 * public void meld (FibonacciHeap heap2)
	 *
	 * Meld the heap with heap2
	 * 
	 * Complexity: O(1)
	 *
	 */
	public void meld(FibonacciHeap heap2) {

		if (heap2.isEmpty()) {
			return;
		}

		if (this.isEmpty()) {
			this.min = heap2.findMin();
			this.size = heap2.size();
			this.numTrees = heap2.numTrees;
			this.numMarked = heap2.numMarked;
			this.maxRank = heap2.maxRank;
		} else {
			HeapNode newNode = heap2.findMin();
			HeapNode newNodePrev = newNode.getPrev();

			newNodePrev.setNext(this.findMin());
			newNode.setPrev(this.findMin().getPrev());

			this.findMin().getPrev().setNext(newNode);
			this.findMin().setPrev(newNodePrev);
			// Test this last part when inserting into a heap with one element

			// Replace min if needed
			if (this.findMin().getKey() > newNode.getKey()) {
				this.min = newNode;
			}
			this.size += heap2.size();
			this.numTrees += heap2.numTrees;
			this.numMarked += heap2.numMarked;
			this.maxRank = Math.max(this.maxRank, heap2.maxRank);
		}

		return;
	}

	/**
	 * public int size()
	 *
	 * Return the number of elements in the heap
	 * 
	 * Complexity: O(1)
	 * 
	 */
	public int size() {
		return this.size;
	}

	/**
	 * public int[] countersRep()
	 *
	 * Return a counters array, where the value of the i-th entry is the number
	 * of trees of order i in the heap.
	 * 
	 * Complexity: O(Max(#Trees,log n))
	 * 
	 */
	public int[] countersRep() {
		int[] arr = new int[maxRank + 1];

		HeapNode node = this.findMin();

		do {
			arr[node.getRank()] += 1;
			node = node.getNext();
		} while (node != this.findMin());

		return arr;
	}

	/**
	 * public void delete(HeapNode x)
	 *
	 * Deletes the node x from the heap.
	 *
	 * Decreases key to negative infinity and then deletes minimum
	 *
	 * Complexity: O(log n)
	 */
	public void delete(HeapNode x) {
		decreaseKey(x, x.getKey() - NEG_INFTY);
		deleteMin();
	}

	/**
	 * public void decreaseKey(HeapNode x, int delta)
	 *
	 * The function decreases the key of the node x by delta. The structure of
	 * the heap should be updated to reflect this chage (for example, the
	 * cascading cuts procedure should be applied if needed).
	 */
	public void decreaseKey(HeapNode x, int delta) {
		return; // should be replaced by student code
	}

	/**
	 * public int potential()
	 *
	 * This function returns the current potential of the heap, which is:
	 * Potential = #trees + 2*#marked The potential equals to the number of
	 * trees in the heap plus twice the number of marked nodes in the heap.
	 * 
	 * Complexity: O(1)
	 * 
	 */
	public int potential() {
		return numTrees + 2 * numMarked;
	}

	/**
	 * public static int totalLinks()
	 *
	 * This static function returns the total number of link operations made
	 * during the run-time of the program. A link operation is the operation
	 * which gets as input two trees of the same rank, and generates a tree of
	 * rank bigger by one, by hanging the tree which has larger value in its
	 * root on the tree which has smaller value in its root.
	 * 
	 * Complexity: O(1)
	 * 
	 */
	public static int totalLinks() {
		return totalLinks;
	}

	/**
	 * public static int totalCuts()
	 *
	 * This static function returns the total number of cut operations made
	 * during the run-time of the program. A cut operation is the operation
	 * which diconnects a subtree from its parent (during decreaseKey/delete
	 * methods).
	 * 
	 * Complexity: O(1)
	 * 
	 */
	public static int totalCuts() {
		return totalCuts();
	}

	/**
	 * public static int[] kMin(FibonacciHeap H, int k)
	 *
	 * This static function returns the k minimal elements in a binomial tree H.
	 * The function should run in O(k(logk + deg(H)).
	 */
	public static int[] kMin(FibonacciHeap H, int k) {
		int[] arr = new int[42];
		return arr; // should be replaced by student code
	}

	/**
	 * public class HeapNode
	 * 
	 * If you wish to implement classes other than FibonacciHeap (for example
	 * HeapNode), do it in this file, not in another file
	 * 
	 */
	public class HeapNode {

		public int key;
		protected int rank = 0;
		protected boolean mark = false;
		protected HeapNode child = null;
		protected HeapNode next = this;
		protected HeapNode prev = this;
		protected HeapNode parent = null;

		public HeapNode(int key) {
			this.key = key;
		}

		public int getKey() {
			return this.key;
		}

		protected void setKey(int key) {
			this.key = key;
		}

		protected int getRank() {
			return rank;
		}

		protected void setRank(int rank) {
			this.rank = rank;
		}

		protected boolean isMark() {
			return mark;
		}

		protected void setMark(boolean mark) {
			this.mark = mark;
		}

		protected HeapNode getChild() {
			return child;
		}

		protected void setChild(HeapNode child) {
			this.child = child;
		}

		protected HeapNode getNext() {
			return next;
		}

		protected void setNext(HeapNode next) {
			this.next = next;
		}

		protected HeapNode getPrev() {
			return prev;
		}

		protected void setPrev(HeapNode prev) {
			this.prev = prev;
		}

		protected HeapNode getParent() {
			return parent;
		}

		protected void setParent(HeapNode parent) {
			this.parent = parent;
		}

	}
}
