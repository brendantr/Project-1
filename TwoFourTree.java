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

            // Case 5: All values are different.
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
            // value2 is the smallest value.
            else if(value2 < value1 && value2 < value3) {
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
            } 
            // value3 is the smallest value.
            else {
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

        // Check if tree is empty
        if (root == null) { 
            root = new TwoFourTreeItem(value);  // create root node with value.
            return true;
        }

        // Check If the root is a 2-node and a leaf, add the value to it.
        if (root.isTwoNode() && root.isLeaf) {

            // Check for identical values
            if (value == root.value1)
                return false; // we do not want identical values

            // Check the new value against the old value
            if (value < root.value1) {
                // Insert in sorted order
                root.value2 = root.value1;
                root.value1 = value;
            } else {
                root.value2 = value;
            }
            // Update the number of values in the node
            root.values = 2;
            return true;
        }
        
        // Check If the root is a 3-node and a leaf, add the value to it.
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
        // If root is a 4-node leaf, it needs to split
        if (root.isFourNode() && root.isLeaf) {
            // Check for duplicates first
            if (value == root.value1 || value == root.value2 || value == root.value3) {
                return false; // no duplicates
            }
            
            splitRootFourNode(value);
            return true;
        }

        // Handle case where root has children (multi-level tree)
        if (!root.isLeaf) {
            return insertIntoTree(value);
        }

        // TODO: Handle more complex cases (splitting, multi-level insert)
        return false;
    }

    public boolean hasValue(int value) {

        // Check if the tree is empty
        if (root == null) {
            return false; // Tree is empty, no values present.
        }
        
        // Start searching from the root
        TwoFourTreeItem currentNode = root;

        while (currentNode != null) {
            // Check if the value is in the current node    
            if (currentNode.value1 == value) {
                return true; // Value found in the current node.
            } 
            if (currentNode.values >= 2 && currentNode.value2 == value) {
                return true; // Value found in the current node.
            }
            if (currentNode.values >= 3 && currentNode.value3 == value) {
                return true; // Value found in the current node.
            }
        
            // If not found in current node, navigate to appropriate child
            if (currentNode.isLeaf) {
                return false; // Reached a leaf, value not found
            }
            
            // Navigate to the correct child based on value
            if (value < currentNode.value1) {
                currentNode = currentNode.leftChild;
            } else if (currentNode.values == 1) {
                // 2-node: only leftChild and rightChild exist
                currentNode = currentNode.rightChild;
            } else if (value < currentNode.value2) {
                // 3-node or 4-node: go to center child
                currentNode = currentNode.centerChild;
            } else if (currentNode.values == 2) {
                // 3-node: go to rightChild
                currentNode = currentNode.rightChild;
            } else if (value < currentNode.value3) {
                // 4-node: go to centerRightChild
                currentNode = currentNode.centerRightChild;
            } else {
                // 4-node: go to rightChild
                currentNode = currentNode.rightChild;
            }
        }
        
        return false;
    }

    public boolean deleteValue(int value) {
        return false;
    }

    private void splitRootFourNode(int newValue) {
        // Current root is 4-node: [value1, value2, value3]
        // We need to add newValue and split into 2 nodes with middle promoted
        
        // Step 1: Collect all 4 values and sort them
        int[] allValues = {root.value1, root.value2, root.value3, newValue};
        java.util.Arrays.sort(allValues);
        
        // Step 2: Create new tree structure
        // allValues[0], allValues[1] | allValues[2] | allValues[3]
        //     left 2-node         middle    right 2-node
    
        TwoFourTreeItem leftChild = new TwoFourTreeItem(allValues[0], allValues[1]);
        TwoFourTreeItem rightChild = new TwoFourTreeItem(allValues[3]);
        
        // Step 3: Create new root with middle value
        root = new TwoFourTreeItem(allValues[2]);
        root.isLeaf = false;  // Root now has children
        root.leftChild = leftChild;
        root.rightChild = rightChild;
        
        // Step 4: Set parent pointers
        leftChild.parent = root;
        rightChild.parent = root;
        
        System.out.println("DEBUG: Split complete! New root: " + root.value1 + 
                          " Left: [" + leftChild.value1 + "," + leftChild.value2 + "]" +
                          " Right: [" + rightChild.value1 + "]");
    }
  
    public void printInOrder() {
        if(root != null) root.printInOrder(0);
    }

    public TwoFourTree() {

    }

    private boolean insertIntoTree(int value) {
        // Find the correct leaf node for insertion
        TwoFourTreeItem current = root;
        
        // Navigate to leaf
        while (!current.isLeaf) {
            // Use same navigation logic as hasValue()
            if (value < current.value1) {
                current = current.leftChild;
            } else if (current.values == 1) {
                current = current.rightChild;
            } else if (value < current.value2) {
                current = current.centerChild;
            } else if (current.values == 2) {
                current = current.rightChild;
            } else if (value < current.value3) {
                current = current.centerRightChild;
            } else {
                current = current.rightChild;
            }
        }
        
        // Now insert into the leaf (reuse existing leaf logic)
        // Check for duplicates first
        if (current.values >= 1 && value == current.value1) return false;
        if (current.values >= 2 && value == current.value2) return false;
        if (current.values >= 3 && value == current.value3) return false;

        // Insert based on current node type
        if (current.isTwoNode()) {
            // Convert 2-node to 3-node
            if (value < current.value1) {
                current.value2 = current.value1;
                current.value1 = value;
            } else {
                current.value2 = value;
            }
            current.values = 2;
            return true;
            
        } else if (current.isThreeNode()) {
            // Convert 3-node to 4-node
            if (value < current.value1) {
                current.value3 = current.value2;
                current.value2 = current.value1;
                current.value1 = value;
            } else if (value < current.value2) {
                current.value3 = current.value2;
                current.value2 = value;
            } else {
                current.value3 = value;
            }
            current.values = 3;
            return true;
            
        } else if (current.isFourNode()) {
            // 4-node needs to split - this is complex, for now return false
            System.out.println("DEBUG: Found 4-node leaf - splitting not implemented yet");
            return false;
        }

        return false;
    }
}
