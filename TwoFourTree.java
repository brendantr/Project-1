public class TwoFourTree {
    
    private class TwoFourTreeItem {
        
        // The number of values in this node.
        // A 2-node has 1 value, a 3-node has 2 values, and a 4-node has 3 values.
        // The values are stored in sorted order.
        // value1 is always the smallest, value2 is the second smallest, and value3 is the largest.
        // value2 and value3 only exist if the node is a 3-node or 4-node, respectively.
        int values = 1;
        int value1 = 0;                             // always exists.
        int value2 = 0;                             // exists iff the node is a 3-node or 4-node.
        int value3 = 0;                             // exists iff the node is a 4-node.
        boolean isLeaf = true;                      // true if this node is a leaf / has no children.
        
        TwoFourTreeItem parent = null;              // parent exists iff the node is not root.
        TwoFourTreeItem leftChild = null;           // left and right child exist iff the note is a non-leaf.
        TwoFourTreeItem rightChild = null;          
        TwoFourTreeItem centerChild = null;         // center child exists iff the node is a non-leaf 3-node.
        TwoFourTreeItem centerLeftChild = null;     // center-left and center-right children exist iff the node is a non-leaf 4-node.
        TwoFourTreeItem centerRightChild = null;

        // Returns true if this node is a 2-node (contains 1 value).
        public boolean isTwoNode() {
            return values == 1;
        }

        // Returns true if this node is a 3-node (contains 2 values).
        public boolean isThreeNode() {
            return values == 2;
        }

        // Returns true if this node is a 4-node (contains 3 values).
        public boolean isFourNode() {
            return values == 3;
        }

        // Returns true if this node is the root of the tree.
        // (Currently always returns false; should be updated if root logic is needed.)
        public boolean isRoot() {
            if(parent == null) {
                return true; // This node has no parent, so it is the root.
            }
            // If the parent is not null, this node is not the root.
            return false;
        }

        // Constructor for a 2-node (node with a single value).
        // Initializes the node with the given value, sets the value count to 1,
        // and marks the node as a leaf (no children yet).
        public TwoFourTreeItem(int value1) {
            this.value1 = value1;   // Store the value in the node.

        }

        public TwoFourTreeItem(int value1, int value2) {

            // Make sure values are not identical.
            if(value1 == value2) {
                this.values = 1; // If both values are the same, treat it as a 2-node.
            }

            // Initialize the node with two values.
            this.values = 2; // This is a 3-node.

            // Make sure that the values are stored in sorted order.
            if(value1 < value2) {
                this.value1 = value1; // Store smaller value
                this.value2 = value2; // Store larger value
            } else {
                this.value1 = value2; // Store smaller value
                this.value2 = value1; // Store larger value
            }

            // New nodes are leafs by default.
            this.isLeaf = true; // it has no children yet.

        }

        public TwoFourTreeItem(int value1, int value2, int value3) {

            // Case 1: If all values are the same, treat it as a 2-node.
            if(value1 == value2 && value1 == value3) {
                this.values = 1; // This is a 2-node.
                this.value1 = value1; // Store the single value.
            }

            // Case 2: value1 is equal to value2, but not value3
            else if(value1 == value2 && value1 != value3) {
                this.values = 2; // This is a 3-node.
                if(value1 < value3) {
                    this.value1 = value1; // Store smaller value
                    this.value2 = value3; // Store larger value
                } else {
                    this.value1 = value3; // Store smaller value
                    this.value2 = value1; // Store larger value
                }

            // Case 3: value1 is equal to value3, but not value2
            } else if(value1 == value3 && value1 != value2) {
                this.values = 2; // This is a 3-node.
                if(value1 < value2) {
                    this.value1 = value1; // Store smaller value
                    this.value2 = value2; // Store larger value
                } else {
                    this.value1 = value2; // Store smaller value
                    this.value2 = value1; // Store larger value
                }

            // Case 4: value2 is equal to value3, but not value1
            } else if(value2 == value3 && value1 != value2) {
                this.values = 2; // This is a 3-node.
                if(value1 < value2) {
                    this.value1 = value1; // Store smaller value
                    this.value2 = value2; // Store larger value
                } else {
                    this.value1 = value2; // Store smaller value
                    this.value2 = value1; // Store larger value    
                }
            }

            // If none of the values are equal, store them in sorted order.
            if(value1 < value2 && value1 < value3) {
                // value1 is the smallest
                this.values = 3; // This is a 4-node.
                this.value1 = value1; // Store smallest value
                // Determine the second smallest and largest values.
                if(value2 < value3) {
                    this.value2 = value2; // second smallest value
                    this.value3 = value3; // largest value
                } else {
                    this.value2 = value3; // second smallest value
                    this.value3 = value2; // largest value   
                }
            }
            else if(value2 < value1 && value2 < value3) {
                // value2 is the smallest value.
                this.values = 3; // This is a 4-node.
                this.value1 = value2; // Store smallest value
                // Determine the second smallest and largest values.
                if(value1 < value3) {
                    this.value2 = value1; // second smallest value
                    this.value3 = value3; // largest value
                } else {
                    this.value2 = value3; // second smallest value
                    this.value3 = value1; // largest value   
                }
            } else { 
                // value3 is the smallest value.
                this.values = 3; // This is a 4-node.
                this.value1 = value3; // Store smallest value
                // Determine the second smallest and largest values.
                if(value1 < value2) {
                    this.value2 = value1; // second smallest value
                    this.value3 = value2; // largest value
                } else {
                    this.value2 = value2; // second smallest value
                    this.value3 = value1; // largest value   
                }
            }
            
        }

        private void printIndents(int indent) {
            for(int i = 0; i < indent; i++) System.out.printf("  ");
        }

        public void printInOrder(int indent) {
            if(!isLeaf) leftChild.printInOrder(indent + 1);
            printIndents(indent);
            System.out.printf("%d\n", value1);
            if(isThreeNode()) {
                if(!isLeaf) centerChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
            } else if(isFourNode()) {
                if(!isLeaf) centerLeftChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
                if(!isLeaf) centerRightChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value3);
            }
            if(!isLeaf) rightChild.printInOrder(indent + 1);
        }
    }

    TwoFourTreeItem root = null;

    public boolean addValue(int value) {
        // If the tree is empty, create a root node with the value.
        if (root == null) {
            root = new TwoFourTreeItem(value);
            return true;
        }
        // If the root is a 2-node and a leaf, add the value to it.
        if (root.isTwoNode() && root.isLeaf) {
            // Insert in sorted order
            if (value == root.value1) return false; // no duplicates
            if (value < root.value1) {
                root.value2 = root.value1;
                root.value1 = value;
            } else {
                root.value2 = value;
            }
            root.values = 2;
            return true;
        }
        // If the root is a 3-node and a leaf, add the value to it.
        if (root.isThreeNode() && root.isLeaf) {
            // Insert in sorted order
            if (value == root.value1 || value == root.value2) return false; // no duplicates
            if (value < root.value1) {
                root.value3 = root.value2;
                root.value2 = root.value1;
                root.value1 = value;
            } else if (value < root.value2) {
                root.value3 = root.value2;
                root.value2 = value;
            } else {
                root.value3 = value;
            }
            root.values = 3;
            return true;
        }
        // TODO: Handle more complex cases (splitting, multi-level insert)
        return false;
    }

    public boolean hasValue(int value) {
        return false;
    }

    public boolean deleteValue(int value) {
        return false;
    }
  
    public void printInOrder() {
        if(root != null) root.printInOrder(0);
    }

    public TwoFourTree() {

    }
}
