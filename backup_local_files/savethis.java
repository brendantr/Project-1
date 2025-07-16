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

        // Helper function - used to check value of current node
        public boolean valueOfCurrNode(int value){

            // local variable to hold itemKey value of current node
            int contains = values;

            // logical conditions to determine if value is in current node
            if(contains == 1){
                return (value1 == value);
            } else if(contains == 2){
                return (value1 == value || value2 == value);
            } else if(contains == 3){
                return (value1 == value || value2 == value || value3 == value);
            } else {
                return false; // should never reach here
            }
        }

        private void assignSplitChildren(TwoFourTreeItem left, TwoFourTreeItem right) {
            
            // DEBUG: show what children are being assigned
            // System.out.printf("assignSplitChildren: this=[%d,%d,%d] left=[%d] right=[%d]\n", 
                // this.value1, this.value2, this.value3, left.value1, right.value1);


            // Assign children to the left node
            left.leftChild = this.leftChild;
            left.rightChild = this.centerLeftChild;
                // System.out.printf("  left.leftChild=%s, left.rightChild=%s\n",
                   //     (left.leftChild != null ? left.leftChild.value1 : "null"),
                   //     (left.rightChild != null ? left.rightChild.value1 : "null"));
 
            // Conditionally assign center child if it exists
            if (left.leftChild != null) left.leftChild.parent = left;
            if (left.rightChild != null) left.rightChild.parent = left;
            // If the node is a leaf, it should not have children
            left.isLeaf = this.isLeaf;

            // Assign children to the right node
            right.leftChild = this.centerRightChild;
            right.rightChild = this.rightChild;
                //System.out.printf("  right.leftChild=%s, right.rightChild=%s\n",
                  //  (right.leftChild != null ? right.leftChild.value1 : "null"),
                  //  (right.rightChild != null ? right.rightChild.value1 : "null"));
            // Conditionally assign center child if it exists
            if (right.leftChild != null) right.leftChild.parent = right;
            if (right.rightChild != null) right.rightChild.parent = right;
            // If the node is a leaf, it should not have children
            right.isLeaf = this.isLeaf;
        }

        
        private TwoFourTreeItem splitFourNode() {

            // Post three new nodes and set middle to new parent
            TwoFourTreeItem newRoot = new TwoFourTreeItem(value2);
            TwoFourTreeItem left = new TwoFourTreeItem(value1);
            TwoFourTreeItem right = new TwoFourTreeItem(value3);
            
            // newRoot is not a leaf 
            newRoot.isLeaf = false;
            
            // Assign children newRoot's children
            newRoot.leftChild = left; 
            newRoot.rightChild = right;
            left.parent = newRoot;
            right.parent = newRoot;

            // If 4-node to split is NOT a leaf
            if (!isLeaf) assignSplitChildren(left, right);

            // if node to be split is the root, make the new root the middle node
            if (isRoot()) root = newRoot;
            else {  // the node is not the root, so merge it into the parent
            
                newRoot.parent = parent;    // initialize parent reference 

                // Check if the node belongs to the left, right, or center of the parent
                if (parent.leftChild.value1 == this.value1) {
                    parent.leftChild = newRoot;                    
                }
                else if (parent.rightChild.value1 == this.value1) {
                    parent.rightChild = newRoot;
                }
                else if (parent.isThreeNode()) {
                    parent.centerChild = newRoot;
                }
                else if (parent.centerLeftChild.value1 == this.value1) {
                    parent.centerLeftChild = newRoot;
                }
                else {
                    parent.centerRightChild = newRoot;
                }
                
                // move up the new root to the parent
                newRoot = parent.upwardShift(newRoot);
            }
            return newRoot;
        }

        private TwoFourTreeItem getNextChild(int value) {
            if (isTwoNode()) {
                return (value > value1) ? rightChild : leftChild;
            } else if (isThreeNode()) {
                if (value > value2) return rightChild;
                else if (value > value1) return centerChild;
                else return leftChild;
            } else { // Four node
                if (value > value3) return rightChild;
                else if (value > value2) return centerRightChild;
                else if (value > value1) return centerLeftChild;
                else return leftChild;
            }
        }

        private TwoFourTreeItem fuse(TwoFourTreeItem sibling) {

            // case where one of the nodes to fuse is the left child of the parent
            if (parent.leftChild.value1 == value1 || parent.leftChild.value1 == sibling.value1) { 
                // use append to add parent & sibling value to current node,
                // then remove value from parent and reset child in case sibling was left
                append(parent.value1);
                append(sibling.value1);
                parent.remove(parent.value1);
                parent.leftChild = this;
                // handle removing old sibling based on whether the parent is now a 2 or 3 node
                // (parent was resized and is now smaller, used to be 3 or 4 node)
                if (parent.isTwoNode()) {
                    parent.centerChild = null;
                }
                else {
                    parent.centerLeftChild = null;
                    parent.centerChild = parent.centerRightChild;
                    parent.centerRightChild = null;
                }
            }
            // case where one of the nodes to fuse is the right child of the parent
            else if (parent.rightChild.value1 == value1 || parent.rightChild.value1 == sibling.value1) {
                // fuse in this case where one node is the center child
                // fuse values and then remove center child as parent becomes a two node
                if (parent.isThreeNode()) {
                    append(parent.value2);
                    append(sibling.value1);
                    parent.remove(parent.value2);
                    parent.centerChild = null;
                }
                // fuse where one node is the center right child
                // fuse values & turn 4 node into 3 node
                else {
                    append(parent.value3);
                    append(sibling.value1);
                    parent.remove(parent.value3);
                    parent.centerRightChild = null;
                    parent.centerChild = parent.centerLeftChild;
                    parent.centerLeftChild = null;
                }
                // in case sibling was right child, make sure right child points to current node
                parent.rightChild = this;
            }
            // case where the parent is a four node with nodes to fuse in the center
            else {
                // basically combine 2 center children and middle parent value into a 4 node center child of 3 node parent
                append(parent.value2);
                append(sibling.value1);
                parent.remove(parent.value2);
                parent.centerRightChild = null;
                parent.centerChild = this;
                parent.centerLeftChild = null;
            }
            // if node is not a leaf, children should be reorganized
            if (!isLeaf) {
                // sibling is larger, so its old children will be the right childs of new 4 node
                if (sibling.value1 > value1) {

                    centerLeftChild = rightChild;

                    centerRightChild = sibling.leftChild;
                    sibling.leftChild.parent = this;
                    rightChild = sibling.rightChild;
                    sibling.rightChild.parent = this;
                }
                // sibling is smaller, so its old children will be the left childs of new 4 node
                else {
                    centerRightChild = leftChild;

                    centerLeftChild = sibling.rightChild;
                    sibling.rightChild.parent = this;
                    leftChild = sibling.leftChild;
                    sibling.leftChild.parent = this;

                }
            }
            return this;
        }

        private TwoFourTreeItem rotate(TwoFourTreeItem sibling) {

            // move parent value into node and move sibling value to parent for all cases
            // case where node to rotate into is the left child of its parent
            if (parent.leftChild.value1 == value1) { 
                this.append(parent.value1);
                parent.remove(parent.value1);
                parent.append(sibling.value1);
                sibling.remove(sibling.value1);
            }
            // case where sibling node is the left child
            else if (parent.leftChild.value1 == sibling.value1) {
                this.append(parent.value1);
                parent.remove(parent.value1);
                // determine whether rightmost sibling value is value2 or value2=3
                if (sibling.isThreeNode()) {
                    parent.append(sibling.value2);
                    sibling.remove(sibling.value2);
                }
                else {
                    parent.append(sibling.value3);
                    sibling.remove(sibling.value3);
                }
            }
            // case where node to rotate into is the right child
            else if (parent.rightChild.value1 == value1) {

                // move rightmost parent value down based on if it is a 3 or 4 node
                if (parent.isThreeNode()) {
                    this.append(parent.value2);
                    parent.remove(parent.value2);
                }
                else {
                    this.append(parent.value3);
                    parent.remove(parent.value3);
                }
                // then move rightmost sibling value up
                if (sibling.isThreeNode()) {
                    parent.append(sibling.value2);
                    sibling.remove(sibling.value2);
                }
                else {
                    parent.append(sibling.value3);
                    sibling.remove(sibling.value3);
                }

            }
            // case where sibling is the right child
            else if (parent.rightChild.value1 == sibling.value1) {
                // move rightmost parent value down based on if it is a 3 or 4 node
                if (parent.isThreeNode()) {
                    this.append(parent.value2);
                    parent.remove(parent.value2);
                }
                else {
                    this.append(parent.value3);
                    parent.remove(parent.value2);
                }
                // then move leftmost sibling value up
                parent.append(sibling.value1);
                sibling.remove(sibling.value1);
            }
            // case where node to rotate is the center left child
            else if (parent.centerLeftChild.value1 == value1) {
                this.append(parent.value2);
                parent.remove(parent.value2);
                parent.append(sibling.value1);
                sibling.remove(sibling.value1);
            }
            // case where node to rotate is center right child
            else {
                this.append(parent.value2);
                parent.remove(parent.value2);
                // find rightmost sibling value and remove it
                if (sibling.isThreeNode()) {
                    parent.append(sibling.value2);
                    sibling.remove(sibling.value2);
                }
                else {
                    parent.append(sibling.value3);
                    sibling.remove(sibling.value3);
                }
            }

            // adjust children if node is not a leaf
            if (!isLeaf) {
                // if sibling is bigger than node
                if (sibling.value1 > value1) {
                    // move siblings old left child to be node's right child
                    centerChild = rightChild;
                    rightChild = sibling.leftChild;
                    rightChild.parent = this;
                    // sibling is 2 or 3 node since it has been resized
                    if (sibling.isTwoNode()) {
                        sibling.leftChild = sibling.centerChild;
                        sibling.centerChild = null;
                    }
                    else {
                        sibling.leftChild = sibling.centerLeftChild;
                        sibling.centerChild = sibling.centerRightChild;
                        sibling.centerLeftChild = null;
                        sibling.centerRightChild = null;
                    }
                }
                // if sibling is smaller than node
                else {
                    // move sibling's old right child to be node's left child
                    centerChild = leftChild;
                    leftChild = sibling.rightChild;
                    leftChild.parent = this;
                    // sibling is 2 or 3 node since it has been resized
                    if (sibling.isTwoNode()) {
                        sibling.rightChild = sibling.centerChild;
                        sibling.centerChild = null;
                    }
                    else {
                        sibling.rightChild = sibling.centerRightChild;
                        sibling.centerChild = sibling.centerLeftChild;
                        sibling.centerLeftChild = null;
                        sibling.centerRightChild = null;
                    }
                }
            }
            return this;

        }
        

        private TwoFourTreeItem upwardShift(TwoFourTreeItem node) {
            
            // null check
            if (node == null) return this;

            // Helper to set parent if child is not null
            java.util.function.Consumer<TwoFourTreeItem> setParent = (child) -> {
                if (child != null) child.parent = this;
            };

            // Case: node to move up is the left child
            if (leftChild != null && leftChild.value1 == node.value1) {
                append(node.value1);
                leftChild = node.leftChild;
                setParent.accept(leftChild);
                if (isTwoNode()) {
                    centerChild = node.rightChild;
                    setParent.accept(centerChild);
                } else {
                    centerLeftChild = node.rightChild;
                    setParent.accept(centerLeftChild);
                    centerRightChild = centerChild;
                    centerChild = null;
                }
                return this;
            }

            // Case: node to move up is the right child
            if (rightChild != null && rightChild.value1 == node.value1) {
                append(node.value1);
                rightChild = node.rightChild;
                setParent.accept(rightChild);
                if (isTwoNode()) {
                    centerChild = node.leftChild;
                    setParent.accept(centerChild);
                } else {
                    centerRightChild = node.leftChild;
                    setParent.accept(centerRightChild);
                    centerLeftChild = centerChild;
                    centerChild = null;
                }
                return this;
            }

            // Case: node to move up is the center child (for 3-node parent)
            append(node.value1);
            centerLeftChild = node.leftChild;
            setParent.accept(centerLeftChild);
            centerRightChild = node.rightChild;
            setParent.accept(centerRightChild);
            centerChild = null;
            return this;
        }


        private void append(int value) {

            // if the root got removed in edge case, this will prevent errors
            if (values == 0) value1 = value;        
            
            // case where a value is appended to a two node
            else if (isTwoNode()) {
                if (value1 > value) {
                    value2 = value1;
                    value1 = value;
                }
                else {
                    value2 = value;
                }
            }
            // case where a value is appended to a three node
            else if (isThreeNode()) {
                if (value > value2) {
                    value3 = value;
                }
                else if (value > value1 && value != value2) {
                    value3 = value2;
                    value2 = value;
                }
                else if (value < value1) {
                    value3 = value2;
                    value2 = value1;
                    value1 = value;
                }
                // this should never be reached
                else {
                    return;
                }
            }
            // this case should never be reached
            else {
                return;
            }
            // increment values to reflect added value
            values++;
        }

        private void remove(int value) {

            // if value to remove is the first value
            if (value == value1) {
                value1 = value2;
                value2 = value3;
                value3 = 0;
            }
            // if value to remove is the second value
            else if (value == value2) {
                value2 = value3;
                value3 = 0;
            }
            // if value to remove is the third value
            else if (value == value3) {
                value3 = 0;
            }
            // decrement values to reflect removed value
            values--;
        }

        private TwoFourTreeItem findSibling() {

            // create sibling, which will be initialized in the function
            TwoFourTreeItem sibling;

            // if parent is a two node root, then it is the only case where a 2 node is the parent
            if (parent.isRoot() && parent.isTwoNode()) {
                if (parent.leftChild.value1 == value1) {
                    sibling = parent.rightChild;
                }
                else {
                    sibling = parent.leftChild;
                }
            }
            // case where the parent is a 3 node
            else if (parent.isThreeNode()) {
                if (parent.centerChild.value1 == value1) {
                    sibling = parent.leftChild;
                    if (!parent.rightChild.isTwoNode()) sibling = parent.rightChild;
                }
                else {
                    sibling = parent.centerChild;
                }
            }
            // case where the parent is a 4 node
            else {
                if (parent.centerLeftChild.value1 == value1) {
                    sibling = parent.leftChild;
                    if (!parent.centerRightChild.isTwoNode()) sibling = parent.centerRightChild;
                }
                else if (parent.centerRightChild.value1 == value1) {
                    sibling = parent.rightChild;
                    if (!parent.centerLeftChild.isTwoNode()) sibling = parent.centerLeftChild;
                }
                else if (parent.leftChild.value1 == value1) {
                    sibling = parent.centerLeftChild;
                }
                else {
                    sibling = parent.centerRightChild;
                }
            }

            return sibling;

        }

        // provided print functions below
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

            } 
            else if(isFourNode()) {

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
   
    // Tree starts with a null root
    TwoFourTreeItem root = null;

    private TwoFourTreeItem mergeNode(TwoFourTreeItem node) {

        // Node to be merged is a non-leaf root - If both children are two nodes as well, otherwise just return
        if (node.isRoot() && !node.isLeaf) {
            if (node.leftChild.isTwoNode() && node.rightChild.isTwoNode()) {
                // merge the two children into a new two node
                node.isLeaf = node.leftChild.isLeaf && node.rightChild.isLeaf;

                // create new two node with left and right child values 
                node = node.upwardShift(node.leftChild);
                node = node.upwardShift(node.rightChild);

            }
            else {
                return node;
            }
        }
        // if first case is not met, find sibling
        else {
            TwoFourTreeItem sibling = node.findSibling();
            // fuse two node sibling
            if (sibling.isTwoNode()) {
                node = node.fuse(sibling);
            }
            // rotate if sibling is not two node
            else {
                node = node.rotate(sibling);
            }
        }
        return node;

    }

    private TwoFourTreeItem lookupNode(TwoFourTreeItem node, int itemKey, boolean mergeFlag, boolean splitFlag) {
        
        // Start at the current node and iterate through the tree
        while (node != null) {
            
            // Split if it is a four node and you are adding a node to the tree
            if (splitFlag && node.isFourNode()) {
                node = node.splitFourNode();
            }
           
            // Else merge if it is a two node and you are deleting a node
            else if (mergeFlag && node.isTwoNode()) {
                node = mergeNode(node);
            }
            
            // If value was found or node is a leaf, return node
            if (node.valueOfCurrNode(itemKey) || node.isLeaf) {
                return node;
            }
            
            // Otherwise, find children
            TwoFourTreeItem child = node.getNextChild(itemKey);

            // If child exists, continue with it; otherwise, return current node
            if (child != null) {
                node = child;
            } else {
                return node;
            }
        }
        // Should not occur, but return root to prevent errors
        return root;
    }

    public boolean addValue(int value) {

        // case where tree is empty -> set new root
        if (root == null) {
            root = new TwoFourTreeItem(value);
            return false;
        }
        
        // call lookup and raise the split flag
        TwoFourTreeItem seekNode = lookupNode(root, value, false, true);

        // check to make sure it is not already in tree
        if (seekNode.valueOfCurrNode(value)) return true;

        // append to leaf
        seekNode.append(value);
        return false;
    }

    public boolean hasValue(int value) {

        if (root == null) {
            return false; // Tree is empty
        }

        // call search
        TwoFourTreeItem contains = lookupNode(root, value, false, false);

        // if the value is there return true
        if (contains.valueOfCurrNode(value)) return true;

        return false;
    }

    public boolean deleteValue(int value) {

        // check if tree is empty
        if (root == null) return false; // cannot delete from an empty tree

        // Initialize node and look for value to delete
        TwoFourTreeItem contains = lookupNode(root, value, true, false);

        // check if value exists
        if (!contains.valueOfCurrNode(value)) return false; // item cannot be deleted.
        
        // Check if value is in a leaf, if so it can just be removed
        if (contains.isLeaf) {

            contains.remove(value);

            return true;

        }
        // find leftmost right node
        else {
            TwoFourTreeItem leftmostRight;
            // case where value to delete was found in root
            if (contains.isRoot()) {
                leftmostRight = lookupNode(contains.rightChild, value - 1, true, false);
            }
            // where value was found in left value of node
            else if (contains.value1 == value) {
                if (contains.isThreeNode()) {
                    leftmostRight = lookupNode(contains.centerChild, value - 1, true, false);
                }
                else {
                    leftmostRight = lookupNode(contains.centerLeftChild, value - 1, true, false);
                }
            }
            // value was found in middle / right value
            else if (contains.value2 == value) {
                if (contains.isThreeNode()) {
                    leftmostRight = lookupNode(contains.rightChild, value - 1, true, false);
                }
                else {
                    leftmostRight = lookupNode(contains.centerRightChild, value - 1, true, false);
                }
            }
            // value was found in right value of a four node
            else {
                leftmostRight = lookupNode(contains.rightChild, value - 1, true, false);
            }
            // if value to delete was merged into the leftmostright node
            if (leftmostRight.valueOfCurrNode(value)) {
                leftmostRight.remove(value);
                // if value is a two node after removal, call merge again to prevent errors
                if (leftmostRight.isTwoNode()) leftmostRight = mergeNode(leftmostRight);
                leftmostRight.remove(value);
                return true;
            }
            // in case contains was moved when moving down to find leftmost right
            contains = lookupNode(contains, value, false, false);
            // then replace value with value from leftmostRight and remove leftmostRight's value
            if (contains.value1 == value) contains.value1 = leftmostRight.value1;
            else if (contains.value2 == value) contains.value2 = leftmostRight.value1;
            else if (contains.value3 == value) contains.value3 = leftmostRight.value1;
            leftmostRight.remove(leftmostRight.value1);

            return true;
        }
    }

    public void printInOrder() {
        if(root != null) root.printInOrder(0);
    }

    public TwoFourTree() {

    }

}