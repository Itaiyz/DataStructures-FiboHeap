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
	 * protected HeapNode insertNode(HeapNode newNode)
	 *
	 * Recieves a node (of type HeapNode) and inserts it into the heap.
	 * 
	 * Calls findMin which is O(1)
	 * 
	 * Complexity: O(1)
	 * 
	 */
	protected HeapNode insertNode(HeapNode newNode) {

		if (this.isEmpty()) {
			this.min = newNode;
		} else {
			HeapNode newNodePrev = newNode.getPrev();
			
			newNodePrev.setNext(this.findMin());
			newNode.setPrev(this.findMin().getPrev());

			this.findMin().getPrev().setNext(newNode);
			this.findMin().setPrev(newNodePrev);
			// Test this last part when inserting into a heap with one element
		}

		// Replace min if needed
		if (this.findMin().getKey() > newNode.getKey()) {
			this.min = newNode;
		}

		this.size += 1;
		return newNode;
	}

	/**
	 * public HeapNode insert(int key)
	 *
	 * Creates a node (of type HeapNode) which contains the given key, and
	 * inserts it into the heap.
	 * 
	 * Calls insertNode which is O(1)
	 * 
	 * Complexity: O(1)
	 * 
	 */
	public HeapNode insert(int key) {

		HeapNode newNode = new HeapNode(key);

		return insertNode(newNode);
	}

	/**
	 * public void deleteMin()
	 *
	 * Delete the node containing the minimum key.
	 *
	 */
	public void deleteMin() {
		return; // should be replaced by student code

	}

	/**
	 * public HeapNode findMin()
	 *
	 * Return the node of the heap whose key is minimal.
	 *
	 */
	public HeapNode findMin() {
		return new HeapNode(0);// should be replaced by student code
	}

	/**
	 * public void meld (FibonacciHeap heap2)
	 *
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2) {
		return; // should be replaced by student code
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
	 */
	public int[] countersRep() {
		int[] arr = new int[42];
		return arr; // to be replaced by student code
	}

	/**
	 * public void delete(HeapNode x)
	 *
	 * Deletes the node x from the heap.
	 *
	 */
	public void delete(HeapNode x) {
		return; // should be replaced by student code
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
	 */
	public int potential() {
		return 0; // should be replaced by student code
	}

	/**
	 * public static int totalLinks()
	 *
	 * This static function returns the total number of link operations made
	 * during the run-time of the program. A link operation is the operation
	 * which gets as input two trees of the same rank, and generates a tree of
	 * rank bigger by one, by hanging the tree which has larger value in its
	 * root on the tree which has smaller value in its root.
	 */
	public static int totalLinks() {
		return 0; // should be replaced by student code
	}

	/**
	 * public static int totalCuts()
	 *
	 * This static function returns the total number of cut operations made
	 * during the run-time of the program. A cut operation is the operation
	 * which diconnects a subtree from its parent (during decreaseKey/delete
	 * methods).
	 */
	public static int totalCuts() {
		return 0; // should be replaced by student code
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
