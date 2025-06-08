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
            if(!isLeaf && leftChild != null) leftChild.printInOrder(indent + 1);
            printIndents(indent);
            System.out.printf("%d\n", value1);
            if(isThreeNode()) {
                if(!isLeaf && centerChild != null) centerChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
            } else if(isFourNode()) {
                if(!isLeaf && centerLeftChild != null) centerLeftChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
                if(!isLeaf && centerRightChild != null) centerRightChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value3);
            }
            if(!isLeaf && rightChild != null) rightChild.printInOrder(indent + 1);
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
        // Check if tree is empty
        if (root == null) {
            return false;
        }
        
        // Find the node containing the value
        TwoFourTreeItem nodeToDelete = findNodeContaining(value);
        if (nodeToDelete == null) {
            return false; // Value not found
        }
        
        // Handle different deletion cases
        if (nodeToDelete.isLeaf) {
            return deleteFromLeaf(nodeToDelete, value);
        } else {
            return deleteFromInternalNode(nodeToDelete, value);
        }
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
        
        // System.out.println("DEBUG: Split complete! New root: " + root.value1 + 
                          // " Left: [" + leftChild.value1 + "," + leftChild.value2 + "]" +
                          // " Right: [" + rightChild.value1 + "]");
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
            // 4-node leaf needs to split
            return splitLeafFourNode(current, value);
        }

        return false;
    }

    private boolean splitLeafFourNode(TwoFourTreeItem leafNode, int newValue) {
        // Step 1: Collect all 4 values and sort them
        int[] allValues = {leafNode.value1, leafNode.value2, leafNode.value3, newValue};
        java.util.Arrays.sort(allValues);
        
        // Step 2: Split into: [allValues[0]] | allValues[1] | [allValues[2], allValues[3]]
        //                     left 2-node    promote   right 2-node
    
        TwoFourTreeItem leftNode = new TwoFourTreeItem(allValues[0]);
        TwoFourTreeItem rightNode = new TwoFourTreeItem(allValues[2], allValues[3]);
        int promoteValue = allValues[1];
    
        // Step 3: Set parent pointers
        leftNode.parent = leafNode.parent;
        rightNode.parent = leafNode.parent;
    
        // Step 4: Try to insert promote value into parent
        TwoFourTreeItem parent = leafNode.parent;
    
        if (parent.isTwoNode()) {
            // Parent can absorb the promoted value
            insertValueIntoParent(parent, promoteValue, leftNode, rightNode, leafNode);
            return true;
        } else if (parent.isThreeNode()) {
            // Parent can absorb the promoted value  
            insertValueIntoParent(parent, promoteValue, leftNode, rightNode, leafNode);
            return true;
        } else {
            // Parent is 4-node - would need recursive splitting
            // System.out.println("DEBUG: Parent is 4-node - recursive splitting not implemented yet");
            return false;
        }
    }

    private void insertValueIntoParent(TwoFourTreeItem parent, int promoteValue, 
                                 TwoFourTreeItem leftChild, TwoFourTreeItem rightChild, 
                                 TwoFourTreeItem oldChild) {
        // Find where oldChild was in parent's children and replace with leftChild/rightChild
    
        if (parent.leftChild == oldChild) {
            // Splitting leftmost child
            parent.leftChild = leftChild;
            if (parent.isTwoNode()) {
                // Insert promoteValue as value1, shift existing value
                parent.value2 = parent.value1;
                parent.value1 = promoteValue;
                parent.values = 2;
                parent.centerChild = rightChild;
            } else {
                // Parent is 3-node, becomes 4-node
                parent.value3 = parent.value2;
                parent.value2 = parent.value1;
                parent.value1 = promoteValue;
                parent.values = 3;
                parent.centerRightChild = parent.centerChild;
                parent.centerLeftChild = rightChild;
            }
        } else if (parent.rightChild == oldChild) {
            // Splitting rightmost child
            parent.rightChild = rightChild;
            if (parent.isTwoNode()) {
                parent.value2 = promoteValue;
                parent.values = 2;
                parent.centerChild = leftChild;
            } else {
                parent.value3 = promoteValue;
                parent.values = 3;
                parent.centerRightChild = leftChild;
            }
        } else if (parent.centerChild == oldChild) {
            // Splitting center child (3-node parent only)
            parent.value3 = parent.value2;
            parent.value2 = promoteValue;
            parent.values = 3;
            parent.centerLeftChild = leftChild;
            parent.centerRightChild = rightChild;
            parent.centerChild = null;
        }

        // System.out.println("DEBUG: Successfully split leaf and promoted value " + promoteValue + " to parent");
    }

    private TwoFourTreeItem findNodeContaining(int value) {
        TwoFourTreeItem current = root;
        
        while (current != null) {
            // Check if value is in current node
            if (current.value1 == value || 
                (current.values >= 2 && current.value2 == value) ||
                (current.values >= 3 && current.value3 == value)) {
                return current;
            }
            
            // Navigate to appropriate child
            if (current.isLeaf) {
                return null; // Value not found
            }
            
            // Use same navigation logic as hasValue
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
        
        return null;
    }

    private boolean deleteFromLeaf(TwoFourTreeItem node, int value) {
        // Simple case: delete from leaf node
        if (node.isTwoNode()) {
            // Special case: if this is the root, we can delete it
            if (node.isRoot()) {
                root = null; // Tree becomes empty
                return true;
            }
            
            // 2-node leaf underflow - try to borrow or merge
            return handleUnderflow(node, value);
            
        } else if (node.isThreeNode()) {
            // Delete from 3-node → becomes 2-node
            removeValueFromNode(node, value);
            node.values = 1;
            return true;
        } else { // 4-node
            // Delete from 4-node → becomes 3-node
            removeValueFromNode(node, value);
            node.values = 2;
            return true;
        }
    }

    private boolean deleteFromInternalNode(TwoFourTreeItem node, int value) {
        // Find which value to delete and replace with successor
        TwoFourTreeItem successor;
        int successorValue;
        
        if (node.value1 == value) {
            // Replace value1 with its inorder successor
            successor = findMinInSubtree(node.leftChild);
            successorValue = successor.value1;
            node.value1 = successorValue;
            return deleteFromLeaf(successor, successorValue);
        } else if (node.values >= 2 && node.value2 == value) {
            // Replace value2 with its inorder successor  
            successor = findMinInSubtree(node.centerChild);
            successorValue = successor.value1;
            node.value2 = successorValue;
            return deleteFromLeaf(successor, successorValue);
        } else if (node.values >= 3 && node.value3 == value) {
            // Replace value3 with its inorder successor
            successor = findMinInSubtree(node.centerRightChild);
            successorValue = successor.value1;
            node.value3 = successorValue;
            return deleteFromLeaf(successor, successorValue);
        }
        
        return false;
    }

    private TwoFourTreeItem findMinInSubtree(TwoFourTreeItem node) {
        // Find the leftmost (minimum) node in subtree
        while (!node.isLeaf) {
            node = node.leftChild;
        }
        return node;
    }

    private void removeValueFromNode(TwoFourTreeItem node, int value) {
        // Remove value and shift remaining values left
        if (node.value1 == value) {
            node.value1 = node.value2;
            node.value2 = node.value3;
            node.value3 = 0;
        } else if (node.values >= 2 && node.value2 == value) {
            node.value2 = node.value3;
            node.value3 = 0;
        } else if (node.values >= 3 && node.value3 == value) {
            node.value3 = 0;
        }
    }
    
    private boolean handleUnderflow(TwoFourTreeItem node, int value) {
    TwoFourTreeItem parent = node.parent;
    
    // Try to borrow from left sibling
    TwoFourTreeItem leftSibling = findLeftSibling(node);
    if (leftSibling != null && leftSibling.values > 1) {
        borrowFromLeftSibling(node, leftSibling, value);
        return true;
    }
    
    // Try to borrow from right sibling
    TwoFourTreeItem rightSibling = findRightSibling(node);
    if (rightSibling != null && rightSibling.values > 1) {
        borrowFromRightSibling(node, rightSibling, value);
        return true;
    }
    
    // Can't borrow - must merge
    if (leftSibling != null) {
        return mergeWithLeftSibling(node, leftSibling, value);
    } else if (rightSibling != null) {
        return mergeWithRightSibling(node, rightSibling, value);
    }
    
    // Should never reach here in a valid tree
    return false;
}

    private TwoFourTreeItem findLeftSibling(TwoFourTreeItem node) {
        TwoFourTreeItem parent = node.parent;
        if (parent == null) return null;
        
        if (parent.rightChild == node) {
            return parent.leftChild;
        } else if (parent.centerChild == node) {
            return parent.leftChild;
        } else if (parent.centerRightChild == node) {
            return parent.centerLeftChild;
        }
        
        return null; // node is leftmost child
    }

    private TwoFourTreeItem findRightSibling(TwoFourTreeItem node) {
        TwoFourTreeItem parent = node.parent;
        if (parent == null) return null;
        
        if (parent.leftChild == node) {
            return parent.centerChild != null ? parent.centerChild : parent.rightChild;
        } else if (parent.centerChild == node) {
            return parent.rightChild;
        } else if (parent.centerLeftChild == node) {
            return parent.centerRightChild;
        }
        
        return null; // node is rightmost child
    }

    private void borrowFromLeftSibling(TwoFourTreeItem node, TwoFourTreeItem leftSibling, int value) {
        // Remove value from current node first
        removeValueFromNode(node, value);
        
        // Move largest value from left sibling through parent to current node
        TwoFourTreeItem parent = node.parent;
        int borrowedValue;
        
        if (leftSibling.isFourNode()) {
            borrowedValue = leftSibling.value3;
            leftSibling.value3 = 0;
            leftSibling.values = 2;
        } else if (leftSibling.isThreeNode()) {
            borrowedValue = leftSibling.value2;
            leftSibling.value2 = 0;
            leftSibling.values = 1;
        } else {
            return; // Can't borrow from 2-node
        }
        
        // Find which parent value to rotate
        if (parent.leftChild == leftSibling && parent.rightChild == node) {
            // Simple 2-node parent case
            node.value1 = parent.value1;
            parent.value1 = borrowedValue;
        }
        // Add more complex cases as needed...
    }

    private void borrowFromRightSibling(TwoFourTreeItem node, TwoFourTreeItem rightSibling, int value) {
        // Remove value from current node first
        removeValueFromNode(node, value);
        
        // Move smallest value from right sibling through parent to current node
        TwoFourTreeItem parent = node.parent;
        int borrowedValue = rightSibling.value1;
        
        // Remove borrowed value from right sibling
        rightSibling.value1 = rightSibling.value2;
        rightSibling.value2 = rightSibling.value3;
        rightSibling.value3 = 0;
        rightSibling.values--;
        
        // Rotate through parent (simplified for 2-node parent)
        if (parent.leftChild == node && parent.rightChild == rightSibling) {
            node.value1 = parent.value1;
            parent.value1 = borrowedValue;
        }
    }

    private boolean mergeWithLeftSibling(TwoFourTreeItem node, TwoFourTreeItem leftSibling, int value) {
        // Remove value from node first
        removeValueFromNode(node, value);
        
        // Simple merge for 2-node case
        TwoFourTreeItem parent = node.parent;
        
        if (parent.isTwoNode() && parent.leftChild == leftSibling && parent.rightChild == node) {
            // Merge: leftSibling + parent.value1 becomes a 2-node
            leftSibling.value2 = parent.value1;
            leftSibling.values = 2;
            
            // Update parent to point only to merged node
            parent.leftChild = leftSibling;
            parent.rightChild = null;
            
            // If parent becomes empty, make leftSibling the new root
            if (parent.isRoot()) {
                root = leftSibling;
                leftSibling.parent = null;
            }
            
            return true;
        }
        
        return false; // More complex cases not implemented
    }

    private boolean mergeWithRightSibling(TwoFourTreeItem node, TwoFourTreeItem rightSibling, int value) {
        // Similar to mergeWithLeftSibling but in reverse
        removeValueFromNode(node, value);
        
        TwoFourTreeItem parent = node.parent;
        
        if (parent.isTwoNode() && parent.leftChild == node && parent.rightChild == rightSibling) {
            // Merge: node.value + parent.value1 + rightSibling becomes a 2-node
            rightSibling.value2 = rightSibling.value1;
            rightSibling.value1 = parent.value1;
            rightSibling.values = 2;
            
            parent.leftChild = rightSibling;
            parent.rightChild = null;
            
            if (parent.isRoot()) {
                root = rightSibling;
                rightSibling.parent = null;
            }
            
            return true;
        }
        
        return false;
    }
}