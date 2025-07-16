// Brendan Thomas Rodriguez
// COP 3503 - Professor Gerber
// Project 1: Two-Four Tree Implementation
// Date: 2025-06-23

public class TwoFourTree {
    private class TwoFourTreeItem {
        int values = 1;
        int value1 = 0;                             // always exists.
        int value2 = 0;                             // exists iff the node is a 3-node or 4-node.
        int value3 = 0;                             // exists iff the node is a 4-node.
        boolean isLeaf = true;
        
        TwoFourTreeItem parent = null;              // parent exists iff the node is not root.
        TwoFourTreeItem leftChild = null;           // left and right child exist iff the note is a non-leaf.
        TwoFourTreeItem rightChild = null;          
        TwoFourTreeItem centerChild = null;         // center child exists iff the node is a non-leaf 3-node.
        TwoFourTreeItem centerLeftChild = null;     // center-left and center-right children exist iff the node is a non-leaf 4-node.
        TwoFourTreeItem centerRightChild = null;

        // Methods to determine the type of node.
        public boolean isTwoNode() {
            if(values == 1){
                return true;
            }
            return false;
        }

        public boolean isThreeNode() {
            if(values == 2){
                return true;
            }
            return false;
        }

        public boolean isFourNode() {
            if(values == 3){
                return true;
            }
            return false;
        }

        public boolean isRoot() {
            if(parent == null){
                return true;
            }
            return false;
        }

        // Constructors for the different types of nodes.
        public TwoFourTreeItem(int value1) {
            this.value1 = value1;
        }

        public TwoFourTreeItem(int value1, int value2) {
            values = 2;
            if(value1 < value2){        // 2 < 1
                this.value1 = value1;
                this.value2 = value2;
            } else{                     // 1 < 2  
                this.value1 = value2;
                this.value2 = value1;
            }
        }

        public TwoFourTreeItem(int value1, int value2, int value3) {
            values = 3;
            if(value1 < value2 && value2 < value3){         // 1 < 2 < 3
                this.value1 = value1;
                this.value2 = value2;
                this.value3 = value3;
            } else if(value1 < value3 && value3 < value2){  // 1 < 3 < 2
                this.value1 = value1;
                this.value2 = value3;
                this.value3 = value2;
            } else if(value2 < value1 && value1 < value3){  // 2 < 1 < 3
                this.value1 = value2;
                this.value2 = value1;
                this.value3 = value3;
            } else if(value2 < value3 && value3 < value1){  // 2 < 3 < 1
                this.value1 = value2;
                this.value2 = value3;
                this.value3 = value1;
            } else if(value3 < value1 && value1 < value2){  // 3 < 1 < 2
                this.value1 = value3;
                this.value2 = value1;
                this.value3 = value2;
            } else{     // 3 < 2 < 1
                this.value1 = value3;
                this.value2 = value2;
                this.value3 = value1;

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

    // 2-3-4 tree utility functions
    public boolean addValue(int value) {
        
        // There is nothing in the tree, so create a new root.
        if (root == null) {
            root = new TwoFourTreeItem(value);
            return true;
        }

        // Check if the root is a four-node
        if(root.isFourNode()){
            handleFourNodeRoot();
        }

        // Start traversal and insertion from root
        return insertIntoTree(value);

        // return false;
 
    }

    // Helper functions
    public void handleFourNodeRoot(){

        // Middle value becomes the new root. Left and Right children split from the old root.
        TwoFourTreeItem newRoot = new TwoFourTreeItem(root.value2);
        TwoFourTreeItem left = new TwoFourTreeItem(root.value1);
        TwoFourTreeItem right = new TwoFourTreeItem(root.value3);

        // If fourNode is not a leaf, set the children
        if(!root.isLeaf) {
            // these nodes are not leafs
            left.isLeaf = false;
            right.isLeaf = false;

            // Set the left and right children of the new root.
            left.leftChild = root.leftChild;
            if(root.leftChild != null){
                root.leftChild.parent = left;
            }
            left.rightChild = root.centerLeftChild;
            if(root.centerLeftChild != null){
                root.centerLeftChild.parent = left;
            }
            right.leftChild = root.centerRightChild;
            if(root.centerRightChild != null){
                root.centerRightChild.parent = right;
            }
            right.rightChild = root.rightChild;
            if(root.rightChild != null){
                root.rightChild.parent = right;
            }

            newRoot.isLeaf = false; // new root is not a leaf
            newRoot.leftChild = left;
            newRoot.rightChild = right;
            left.parent = newRoot;
            right.parent = newRoot; 
            root = newRoot; // set the new root
        }
    }

    // Iterative insertion into the 2-3-4 tree
    private boolean insertIntoTree(int value) {
        TwoFourTreeItem current = root;
        TwoFourTreeItem parent = null;

        // Traverse down to the appropriate leaf node iteratively
        while (!current.isLeaf) {
            parent = current;
            if (value < current.value1) {
                current = current.leftChild;
            } else if (current.values == 1 || value < current.value2) {
                current = current.centerLeftChild;
            } else if (current.values == 2 || value < current.value3) {
                current = current.centerRightChild;
            } else {
                current = current.rightChild;
            }

            // If we encounter a null child, this should not happen in a valid tree
            if (current == null) {
                return false; // Error case, should not occur in a well-formed tree
            }
        }

        // At this point, 'current' is a leaf node where we can insert the value
        // Add the value to the leaf node
        if (current.values == 1) {
            if (value < current.value1) {
                current.value2 = current.value1;
                current.value1 = value;
            } else {
                current.value2 = value;
            }
            current.values = 2;
        } else if (current.values == 2) {
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
        } else {
            // Current is a 4-node (values == 3), need to split
            splitNode(current, parent, value);
            return true;
        }

        // Check if splitting is needed up the tree (post-insertion)
        while (parent != null && parent.isFourNode()) {
            TwoFourTreeItem grandparent = parent.parent;
            splitNode(parent, grandparent, -1); // -1 indicates no new value to insert during split
            parent = grandparent;
        }

        return true;
    }


    // Helper method to split a 4-node
    private void splitNode(TwoFourTreeItem node, TwoFourTreeItem parent, int newValue) {
        // If node is a 4-node, split it
        if (!node.isFourNode() && newValue == -1) {
            return; // No split needed
        }

        // If inserting a new value into a 4-node leaf, handle the insertion first
        if (newValue != -1) {
            // Temporarily store values and new value, then sort
            int[] values = {node.value1, node.value2, node.value3, newValue};
            java.util.Arrays.sort(values);
            node.value1 = values[0];
            node.value2 = values[1];
            node.value3 = values[2];
            // The middle value (values[1]) will be promoted during split
        }

        // Create new nodes for split
        TwoFourTreeItem left = new TwoFourTreeItem(node.value1);
        TwoFourTreeItem right = new TwoFourTreeItem(node.value3);
        int middleValue = node.value2;

        // Handle children if not a leaf
        if (!node.isLeaf) {
            left.isLeaf = false;
            right.isLeaf = false;
            left.leftChild = node.leftChild;
            if (node.leftChild != null) node.leftChild.parent = left;
            left.rightChild = node.centerLeftChild;
            if (node.centerLeftChild != null) node.centerLeftChild.parent = left;
            right.leftChild = node.centerRightChild;
            if (node.centerRightChild != null) node.centerRightChild.parent = right;
            right.rightChild = node.rightChild;
            if (node.rightChild != null) node.rightChild.parent = right;
        }

        // If node is root, create a new root
        if (parent == null) {
            TwoFourTreeItem newRoot = new TwoFourTreeItem(middleValue);
            newRoot.isLeaf = false;
            newRoot.leftChild = left;
            newRoot.rightChild = right;
            left.parent = newRoot;
            right.parent = newRoot;
            root = newRoot;
        } else {
            // Insert middle value into parent
            if (parent.values == 1) {
                if (middleValue < parent.value1) {
                    parent.value2 = parent.value1;
                    parent.value1 = middleValue;
                    parent.centerLeftChild = parent.rightChild;
                    parent.leftChild = left;
                    parent.rightChild = right;
                } else {
                    parent.value2 = middleValue;
                    parent.centerLeftChild = parent.rightChild;
                    parent.rightChild = right;
                }
                parent.values = 2;
            } else if (parent.values == 2) {
                if (middleValue < parent.value1) {
                    parent.value3 = parent.value2;
                    parent.value2 = parent.value1;
                    parent.value1 = middleValue;
                    parent.centerRightChild = parent.centerLeftChild;
                    parent.centerLeftChild = parent.rightChild;
                    parent.leftChild = left;
                    parent.rightChild = right;
                } else if (middleValue < parent.value2) {
                    parent.value3 = parent.value2;
                    parent.value2 = middleValue;
                    parent.centerRightChild = parent.centerLeftChild;
                    parent.centerLeftChild = parent.rightChild;
                    parent.rightChild = right;
                } else {
                    parent.value3 = middleValue;
                    parent.centerRightChild = parent.rightChild;
                    parent.rightChild = right;
                }
                parent.values = 3;
            }
            left.parent = parent;
            right.parent = parent;
        }
    }



    // // Helper method to traverse and insert into the tree
    // private boolean insertIntoTree(TwoFourTreeItem node, int value) {
    //     // If we've reached a leaf, handle insertion based on node type
    //     if (node.isLeaf) {
    //         return insertIntoLeaf(node, value);
    //     } else {
    //         // Determine which child to traverse based on node type and value
    //         TwoFourTreeItem child = getChildToTraverse(node, value);
    //         int childIndex = getChildIndex(node, value);

    //         // If the child is a 4-node, split it before descending
    //         if (child.isFourNode()) {
    //             splitChildNode(node, childIndex);
    //             // Recalculate child after split
    //             child = getChildToTraverse(node, value);
    //         }
    //         // Recurse into the appropriate child
    //         return insertIntoTree(child, value);
    //     }
    // }

    // // Helper method to insert into a leaf node based on its type
    // private boolean insertIntoLeaf(TwoFourTreeItem node, int value) {
    //     // Case 2: Leaf is a 2-Node
    //     if (node.isTwoNode()) {
    //         if (value < node.value1) {
    //             node.value2 = node.value1;
    //             node.value1 = value;
    //         } else {
    //             node.value2 = value;
    //         }
    //         node.values = 2;
    //         return true;
    //     }
    //     // Case 3: Leaf is a 3-Node
    //     else if (node.isThreeNode()) {
    //         if (value < node.value1) {
    //             node.value3 = node.value2;
    //             node.value2 = node.value1;
    //             node.value1 = value;
    //         } else if (value < node.value2) {
    //             node.value3 = node.value2;
    //             node.value2 = value;
    //         } else {
    //             node.value3 = value;
    //         }
    //         node.values = 3;
    //         return true;
    //     }
    //     // Case 4: Leaf is a 4-Node (should not happen if splits are handled, but included for completeness)
    //     else {
    //         // This case should be handled before reaching here, but can be split if needed
    //         return false; // Temporary placeholder; ideally, split logic would be here if not handled earlier
    //     }
    // }

    // // Helper method to determine the child index for traversal based on value comparison
    // private int getChildIndex(TwoFourTreeItem node, int value) {
    //     if (node.isTwoNode()) {
    //         return value < node.value1 ? 0 : 1;
    //     } else if (node.isThreeNode()) {
    //         if (value < node.value1) return 0;
    //         else if (value < node.value2) return 1;
    //         else return 2;
    //     }
    //     // Should not reach here since 4-nodes are split beforehand
    //     return -1;
    // }

    // // Helper method to get the child node for traversal based on value comparison
    // private TwoFourTreeItem getChildToTraverse(TwoFourTreeItem node, int value) {
    //     if (node.isTwoNode()) {
    //         return value < node.value1 ? node.leftChild : node.rightChild;
    //     } else if (node.isThreeNode()) {
    //         if (value < node.value1) return node.leftChild;
    //         else if (value < node.value2) return node.centerChild;
    //         else return node.rightChild;
    //     }
    //     // Should not reach here since 4-nodes are split beforehand
    //     return null;
    // }

    // // Helper method to split a 4-node child under a parent node during traversal
    // private void splitChildNode(TwoFourTreeItem parent, int childIndex) {
    //     TwoFourTreeItem child;
    //     if (childIndex == 0) {
    //         child = parent.leftChild;
    //     } else if (childIndex == 1 && parent.isThreeNode()) {
    //         child = parent.centerChild;
    //     } else {
    //         child = parent.rightChild;
    //     }

    //     // Create new node for right half after split
    //     TwoFourTreeItem newNode = new TwoFourTreeItem(child.value3);
    //     newNode.isLeaf = child.isLeaf;

    //     // Adjust child's values (keep only value1 as a 2-node)
    //     child.values = 1;

    //     // Move middle value (value2) up to parent
    //     if (parent.isTwoNode()) {
    //         if (childIndex == 0) {
    //             parent.value2 = parent.value1;
    //             parent.value1 = child.value2;
    //         } else {
    //             parent.value2 = child.value2;
    //         }
    //         parent.values = 2;
    //         parent.centerChild = parent.rightChild;
    //         parent.rightChild = newNode;
    //     } else if (parent.isThreeNode()) {
    //         if (childIndex == 0) {
    //             parent.value3 = parent.value2;
    //             parent.value2 = parent.value1;
    //             parent.value1 = child.value2;
    //         } else if (childIndex == 1) {
    //             parent.value3 = parent.value2;
    //             parent.value2 = child.value2;
    //         } else {
    //             parent.value3 = child.value2;
    //         }
    //         parent.values = 3;
    //         parent.centerRightChild = parent.rightChild;
    //         parent.centerLeftChild = parent.centerChild;
    //         if (childIndex == 0) {
    //             parent.centerChild = parent.centerLeftChild;
    //         } else if (childIndex == 1) {
    //             parent.centerChild = child;
    //         }
    //         parent.rightChild = newNode;
    //     }

    //     // Set parent references for the split nodes
    //     newNode.parent = parent;
    //     child.parent = parent;

    //     // If child is not a leaf, assign children to new node
    //     if (!child.isLeaf) {
    //         newNode.leftChild = child.centerRightChild;
    //         newNode.rightChild = child.rightChild;
    //         if (newNode.leftChild != null) newNode.leftChild.parent = newNode;
    //         if (newNode.rightChild != null) newNode.rightChild.parent = newNode;
    //         child.centerRightChild = null;
    //         child.rightChild = null;
    //     }
    // }

    public boolean hasValue(int value) {

        if (root == null) {
            return false; // Tree is empty
        }
        TwoFourTreeItem current = root;

        while( current != null){
            // Check based on the number of values in the node
            if (root.values == 1) {
                // 2-node: Only value1 exists, so compare against it
                return value == root.value1;
            } else if (root.values == 2) {
                // 3-node: value1 and value2 exist, so check both
                return value == root.value1 || value == root.value2;
            } else if (root.values == 3) {
                // 4-node: value1, value2, and value3 exist, so check all three
                return value == root.value1 || value == root.value2 || value == root.value3;
            } else {
                // Invalid number of values: Throw an exception for error handling
                throw new ArithmeticException("Invalid number of values in node: " + root.values);
            }
        }
        
        return false;
    }

    
    public boolean deleteValue(int value) {
        return false;
    }

    // print the tree in order
    public void printInOrder() {
        if(root != null) root.printInOrder(0);
    }

    public TwoFourTree() {

    }
}
