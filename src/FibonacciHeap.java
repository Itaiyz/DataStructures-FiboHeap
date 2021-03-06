/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci Heap over integers.
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
	protected HeapNode first = null; // Beginning of doubly-linked list
	protected HeapNode last = null; // End of doubly-linked list
	protected int numTrees = 0;
	protected int numMarked = 0;
	protected static int totalLinks = 0;
	protected static int totalCuts = 0;
	// For deleting an arbitrary key
	protected static final int NEG_INFTY = Integer.MIN_VALUE;
	// For brute finding minimum
	protected static final int POS_INFTY = Integer.MAX_VALUE;

	/**
	 * protected int maxPossibleRank()
	 * 
	 * Complexity: O(1)
	 */
	protected int maxPossibleRank() {
		// Adding 1 to size to avoid problems when size==0
		return (int) (Math.ceil(Math.log(size + 1) / Math.log(2)) + 1);
	}

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
	 * protected void addRoot(HeapNode newNode)
	 * 
	 * Adds a given node at the start of the heap
	 * 
	 * Complexity: O(1)
	 * 
	 */
	protected void addRoot(HeapNode newNode) {

		this.numTrees += 1;

		if (this.first == null) {
			this.min = this.first = this.last = newNode;
			return;
		}

		HeapNode firstNode = this.first;
		HeapNode lastNode = this.last;
		newNode.setNext(firstNode);
		firstNode.setPrev(newNode);
		lastNode.setNext(newNode);
		newNode.setPrev(lastNode);
		this.first = newNode;

		// Update minimum
		if (this.min.getKey() > newNode.getKey()) {
			this.min = newNode;
		}

		return;
	}

	/**
	 * protected void removeNode(HeapNode node)
	 * 
	 * Removes given node from list its siblings
	 * 
	 * @pre: We are not removing the last node
	 * 
	 *       Complexity: O(1)
	 * 
	 */
	protected void removeNode(HeapNode node) {
		if (this.first == node) {
			this.first = node.getNext();
		}
		if (this.last == node) {
			this.last = node.getPrev();
		}
		node.getPrev().setNext(node.getNext());
		node.getNext().setPrev(node.getPrev());
	}

	/**
	 * public HeapNode insert(int key)
	 *
	 * Creates a node (of type HeapNode) which contains the given key, and
	 * inserts it into the heap.
	 * 
	 * Lazy insertion - inserting the node as a new tree of rank 0.
	 * 
	 * Calls addRoot which is O(1)
	 * 
	 * Complexity: O(1)
	 * 
	 */
	public HeapNode insert(int key) {

		// Not to be implemented using meld, according to forum
		this.size += 1;
		HeapNode newNode = new HeapNode(key);
		this.addRoot(newNode);

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
		int currentMinKey = POS_INFTY;
		HeapNode startNode = this.first;
		HeapNode node = startNode;
		do {
			if (node.getKey() < currentMinKey) {
				currentMinKey = node.getKey();
				this.min = node;
			}
			node = node.getNext();
		} while (node != startNode);

		return this.min;
	}

	/**
	 * public void deleteMin()
	 *
	 * Delete the node containing the minimum key.
	 * 
	 * Calls consolidate which is O(n)
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

			// Removing parent pointers, unmarking nodes
			HeapNode startNode = minNode.getChild();
			HeapNode node = startNode;
			do {
				node.setParent(null);
				// If node was marked, it became a root and so is now unmarked
				if (node.isMark()) {
					node.setMark(false);

				}
				node = node.getNext();
			} while (node != startNode);

			// Adding minNode's children but removing minNode
			this.numTrees += minNode.getRank() - 1;
			this.size -= 1;

			// Removing minNode from root list and replacing with children
			HeapNode minNodeChildPrev = minNode.getChild().getPrev();
			minNode.getPrev().setNext(minNode.getChild());
			minNode.getChild().setPrev(minNode.getPrev());
			if (this.first == minNode) {
				this.first = minNode.getChild();
			}
			minNode.getNext().setPrev(minNodeChildPrev);
			minNodeChildPrev.setNext(minNode.getNext());
			if (this.last == minNode) {
				this.last = minNodeChildPrev;
			}

			this.consolidate();

		} else {
			this.size -= 1;
			this.numTrees -= 1;
			if (size > 0) {
				// Removing minNode from root list
				removeNode(minNode);

				this.consolidate();

			} else { // Minimum was only node
				this.min = null;
				this.first = null;
				this.last = null;
			}

		}

	}

	/**
	 * protected void consolidate()
	 * 
	 * Performs consolidation as described on slides 38 - 48 of
	 * https://www.cs.tau.ac.il/~schechik/Data-Structures-2020/Binomial-Fibonacci-heaps.pptx
	 * 
	 * Also finds new minimum.
	 * 
	 * Complexity: O(n)
	 */
	protected void consolidate() {
		HeapNode[] arr = new HeapNode[maxPossibleRank() + 1];

		HeapNode currNode = this.first;
		HeapNode nextNode = currNode.getNext();
		int currRank = 0;

		while (numTrees > 0) {
			nextNode = currNode.getNext();
			this.removeNode(currNode);
			numTrees -= 1;
			currRank = currNode.getRank();
			while (arr[currNode.getRank()] != null
					&& currNode != arr[currNode.getRank()]) {
				currRank = currNode.getRank();
				this.link(currNode, arr[currNode.getRank()]);
				arr[currRank] = null;
				currRank += 1;
				if (currNode.getParent() != null) {
					currNode = currNode.getParent();
				}
			}
			arr[currRank] = currNode;

			currNode = nextNode;
		}

		// Retrieve heap from buckets
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null) {
				this.first = arr[i];
				break;
			}
		}

		for (int i = arr.length - 1; i > -1; i--) {
			if (arr[i] != null) {
				this.last = arr[i];
				break;
			}
		}

		this.first.setPrev(this.last);
		this.last.setNext(this.first);

		HeapNode recent = null;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null) {
				if (recent != null) {
					recent.setNext(arr[i]);
					arr[i].setPrev(recent);
				}
				recent = arr[i];
				numTrees += 1;
			}
		}

		// Update min
		this.bruteFindMin();

		return;
	}

	/**
	 * protected void link(HeapNode node1, HeapNode node2)
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

		totalLinks += 1;

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

		// Inserting large into small's children
		if (small.getChild() != null) {
			large.setNext(small.getChild());
			large.setPrev(small.getChild().getPrev());
			small.getChild().getPrev().setNext(large);
			small.getChild().setPrev(large);

		} else {
			large.setNext(large);
			large.setPrev(large);
		}
		small.setChild(large);
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

		this.last.setNext(heap2.first);
		heap2.first.setPrev(this.last);
		this.last = heap2.last;
		this.last.setNext(this.first);
		this.first.setPrev(this.last);

		// Update minimum
		if (this.min.getKey() > heap2.min.getKey()) {
			this.min = heap2.min;
		}

		// Update other fields
		this.size += heap2.size();
		this.numTrees += heap2.numTrees;
		this.numMarked += heap2.numMarked;

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
	 * Complexity: O(n)
	 * 
	 */
	public int[] countersRep() {
		int[] arr = new int[maxPossibleRank() + 1];

		HeapNode node = this.first;

		do {
			arr[node.getRank()] += 1;
			node = node.getNext();
		} while (node != this.first);

		return arr;
	}

	/**
	 * public void delete(HeapNode x)
	 *
	 * Deletes the node x from the heap.
	 *
	 * Decreases key to negative infinity and then deletes minimum
	 *
	 * Complexity: O(n)
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
	 * 
	 * Calls cascadingCut which is O(n)
	 * 
	 * Complexity: O(n)
	 * 
	 */
	public void decreaseKey(HeapNode x, int delta) {

		x.setKey(x.getKey() - delta);

		// Update minimum
		if (this.min.getKey() > x.getKey()) {
			this.min = x;
		}

		if ((x.getParent() != null) && (x.getParent().getKey() >= x.getKey())) {
			cascadingCut(x, x.getParent());
		}

		return;
	}

	/**
	 * protected void cascadingCut(HeapNode x, HeapNode parent)
	 * 
	 * Performs a cascading cut process starting at x with parent y
	 * 
	 * Calls cut which is O(1)
	 * 
	 * Complexity: O(n)
	 * 
	 */
	protected void cascadingCut(HeapNode x, HeapNode y) {
		cut(x, y);
		if (y.getParent() != null) {
			if (y.isMark()) {
				cascadingCut(y, y.getParent());
			} else {
				y.setMark(true);
			}
		}
	}

	/**
	 * protected void cut(HeapNode x, HeapNode parent)
	 * 
	 * Cut x from its parent y
	 * 
	 * Complexity: O(1)
	 * 
	 */
	protected void cut(HeapNode x, HeapNode y) {

		totalCuts += 1;

		x.setParent(null);
		x.setMark(false);
		y.setRank(y.getRank() - 1);

		// Update y.child
		if (y.getChild() == x) {
			if (x.getNext() == x) {
				y.setChild(null);
			} else {
				y.setChild(x.getNext());
			}
		}

		// Remove x from its list
		removeNode(x);

		// Add x to root list
		addRoot(x);

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
		return totalCuts;
	}

	/**
	 * public static int[] kMin(FibonacciHeap H, int k)
	 *
	 * This static function returns the k minimal elements in a binomial tree H.
	 * The function should run in O(k(logk + deg(H))).
	 */
	public static int[] kMin(FibonacciHeap H, int k) {
		if (H.isEmpty()) {
			return new int[0];
		}
		int degH= H.first.rank;
		int [] res= new int[k]; 
		res[0]= H.first.key; 
		HeapNode child= H.first.child;
		FibonacciHeap minHeap= new FibonacciHeap();
		HeapNode p0= child;
		int ind=1;
		for(int i=0; i<k && ind<k; i++, ind++)
		{
			HeapNode p= p0;
			do
			{
				minHeap.insert(p.key);
				p=p.getNext();
				
			}while(p!=child);
			HeapNode min= minHeap.findMin();
			res[ind]= min.getKey();	
			p= child; 
			//changing min, in order to make it point to the original node in the tree
			do
			{
				if(p.key==min.key)
				{
					min=p;
					break;
				}
			} while(p!=child);
			minHeap.deleteMin();
			child= min.child;
			p=child;
			if(p==null)
				child= p0.next;
			
		} 
		
		return res;
		
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
			boolean init = this.mark;
			this.mark = mark;
			if (init == false && mark == true) {
				FibonacciHeap.this.numMarked += 1;
			}
			if (init == true && mark == false) {
				FibonacciHeap.this.numMarked -= 1;
			}
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

	protected class HeapNodeWithInfo extends HeapNode {

		HeapNode info;

		protected HeapNodeWithInfo(int key, HeapNode info) {
			super(key);
			this.info = info;
		}

		protected HeapNode getInfo() {
			return info;
		}

		protected void setInfo(HeapNode info) {
			this.info = info;
		}

	}
}