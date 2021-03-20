import java.lang.Math;

public class BinarySearchTree<E extends Comparable<E>> {
	class Node {
		E value;
		Node leftChild = null;
		Node rightChild = null;
		Node(E value) {
			this.value = value;
		}
		@Override
		public boolean equals(Object obj) {
			if ((obj instanceof BinarySearchTree.Node) == false)
				return false;
			@SuppressWarnings("unchecked")
			Node other = (BinarySearchTree<E>.Node)obj;
			return other.value.compareTo(value) == 0 &&
					other.leftChild == leftChild && other.rightChild == rightChild;
		}
	}
	
	protected Node root = null;
	
	//prints value of node, n
	protected void visit(Node n) {
		System.out.println(n.value);
	}
	
	// returns true if BST contains value, val
	public boolean contains(E val) {
		return contains(root, val);
	}
	
	
	protected boolean contains(Node n, E val) {
		if (n == null) return false;
		
		if (n.value.equals(val)) {
			return true;
		} else if (n.value.compareTo(val) > 0) {
			return contains(n.leftChild, val);
		} else {
			return contains(n.rightChild, val);
		}
	}
	
	public boolean add(E val) {
		if (root == null) {
			root = new Node(val);
			return true;
		}
		return add(root, val);
	}
	
	protected boolean add(Node n, E val) {
		if (n == null) {
			return false;
		}
		int cmp = val.compareTo(n.value);
		if (cmp == 0) {
			return false; // this ensures that the same value does not appear more than once
		} else if (cmp < 0) {
			if (n.leftChild == null) {
				n.leftChild = new Node(val);
				return true;
			} else {
				return add(n.leftChild, val);
			} 	
		} else {
			if (n.rightChild == null) {
				n.rightChild = new Node(val);
				return true;
			} else {
				return add(n.rightChild, val);
			} 	
		}
	}	
	
	public boolean remove(E val) {
		return remove(root, null, val);
	}
	
	protected boolean remove(Node n, Node parent, E val) {
		if (n == null) return false;

		if (val.compareTo(n.value) == -1) {
				return remove(n.leftChild, n, val);
		} else if (val.compareTo(n.value) == 1) {
				return remove(n.rightChild, n, val);
		} else {
			if (n.leftChild != null && n.rightChild != null){
				n.value = maxValue(n.leftChild);
				remove(n.leftChild, n, n.value);
			} else if (parent == null) {
				root = n.leftChild != null ? n.leftChild : n.rightChild;
			} else if (parent.leftChild == n){
				parent.leftChild = n.leftChild != null ? n.leftChild : n.rightChild;
			} else {
				parent.rightChild = n.leftChild != null ? n.leftChild : n.rightChild;
			}
			return true;
		}
	}

	protected E maxValue(Node n) {
		if (n.rightChild == null) {
			return n.value;
	    } else {
	       return maxValue(n.rightChild);
	    }
	}

	
	/*********************************************
	 * 
	 * IMPLEMENT THE METHODS BELOW!
	 *
	 *********************************************/
	
	
	// Method #1.
	public Node findNode(E val) {
		if (val == null) return null;
		
		return findNode(root, val);
	}
	
	protected Node findNode(Node n, E val) {
		if (n == null) return null;
		
		if (n.value.equals(val)) {
			return n;
		} else if (n.value.compareTo(val)>0) {
			return findNode(n.leftChild, val);
		} else {
			return findNode(n.rightChild, val);
		}
	}
	
	
	// Method #2.
	protected int depth(E val) {
		int currDepth = 0;
		Node currNode = root;
		if (currNode == null || !this.contains(val)) {
			return -1;
		} else if (root.value == val) {
			return 0;
		}
		
		while (true) {
			if (currNode.value.equals(val)) {
				return currDepth;
			} else if (currNode.value.compareTo(val) > 0) {
				currNode = currNode.leftChild;
			} else {
				currNode = currNode.rightChild;
			}
			currDepth++;
		}
	}
	
	// Method #3.
	public int height(E val) {
		if (val == null) return -1;
		
		Node n = findNode(val);
		if (n == null) return -1;
		
		return height(n);
	}
		
	protected int height(Node n) {
		if (n == null) return -1;
		
		if (n.leftChild == null && n.rightChild == null) {
			return 0;
		}
		return 1 + Math.max(height(n.leftChild), height(n.rightChild));
		
	}


	// Method #4.
	protected boolean isBalanced(Node n) {
		if (n == null) return false;
		int leftHeight;
		int rightHeight;
		
		if (n.leftChild == null) {
			leftHeight = -1;
		} else {
			leftHeight = height(n.leftChild.value);
		}
	
		if (n.rightChild == null) {
			rightHeight = -1;
		} else {
			rightHeight = height(n.rightChild.value);
		}
		
		return (Math.abs(leftHeight-rightHeight) <= 1);
	}
	
	// Method #5. .
	public boolean isBalanced() {
		return allBalanced(root);
	}
	
	private boolean allBalanced(Node n) {
		if (n == null || (n.leftChild == null && n.rightChild == null)) return true;
		
		boolean left = n.leftChild == null || isBalanced(n.leftChild);
		boolean right = n.rightChild == null || isBalanced(n.rightChild);
		boolean balanced = left && right;
		
		return balanced && (allBalanced(n.rightChild) && allBalanced(n.leftChild));
		
	}

}
