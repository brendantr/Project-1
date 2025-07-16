// Brendan T. Rodriguez
// COP 3503 - Professor Gerber
// Project 1: Two-Four Tree Implementation
// Date: 2025-06-30

public class TwoFourTree {
    private class TwoFourTreeItem {
        int values = 1;
        int value1 = 0;                              // always exists.
        int value2 = 0;                             // exists iff the node is a 3-node or 4-node.
        int value3 = 0;                             // exists iff the node is a 4-node.
        boolean isLeaf = true;

        TwoFourTreeItem parent = null;              // parent exists iff the node is not root.
        TwoFourTreeItem leftChild = null;           // left and right child exist iff the node is a non-leaf.
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

            // Three values must be sorted in ascending order
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
            } else{                                         // 3 < 2 < 1

                this.value1 = value3;
                this.value2 = value2;
                this.value3 = value1;
            }
        }

        private void printIndents(int indent) {
            for (int i = 0; i < indent; i++) System.out.print("  ");
        }

        // Helper - used to check value of current node
        public boolean valueOfCurrNode(int value){

            // local variable to hold itemKey value of current node
            int contains = values;

            // check if the current node contains the value
            if(contains == 1){
                return (value1 == value);
            } else if(contains == 2){
                return (value1 == value || value2 == value);
            } else if(contains == 3){
                return (value1 == value || value2 == value || value3 == value);
            } else {
                return false; // should never happen
            }
        }

        // Helper - used to attach a child to the current node
        public boolean attachChild(TwoFourTreeItem child) {
            // If the child is null, just return true
            if (child == null) return true;
            child.parent = this;
            isLeaf = false;

            // Assign child to the correct pointer based on value and node type
            if (isTwoNode()) {
                // Two-node can only have two children
                if (child.value1 < value1) {
                    leftChild = child;
                } else {
                    rightChild = child; 
                }
            // A three-node can have three children
            } else if (isThreeNode()) {
                // A three-node can have a left and right child, and a center child
                if (child.value1 < value1) {
                    leftChild = child;
                } else if (child.value1 < value2) {
                    centerChild = child;
                } else {
                    rightChild = child;
                }
            // A four-node can have five children
            } else if (isFourNode()) {
                // A four-node can have a left, center-left, center, center-right, and right child
                if (child.value1 < value1) {
                    leftChild = child;
                } else if (child.value1 < value2) {
                    centerLeftChild = child;
                } else if (child.value1 < value3) {
                    centerRightChild = child;
                } else {
                    rightChild = child;
                }
            } else {
                // Should not happen
                System.err.println("Invalid node state in attachChild");
                return false;
            }
            return true;
        }

        // Helper - used to remove the current node from its parent
        public boolean removeFromParent() {
            // If the node is a root node, we cannot remove it
            if (values != 0) return false;
            // If the node is already a root, we can just return true
            if (isRoot()) return true;

            // Array of all possible child pointers in the parent
            TwoFourTreeItem[] parentChildren = {
                parent.leftChild,
                parent.centerLeftChild,
                parent.centerChild,
                parent.centerRightChild,
                parent.rightChild
            };

            // Remove this node from its parent's children
            boolean removed = false;
            // Iterate through the parent's children to find this node
            for (int i = 0; i < parentChildren.length; i++) {
                if (parentChildren[i] == this) {
                    switch (i) {
                        case 0: parent.leftChild = null; break;
                        case 1: parent.centerLeftChild = null; break;
                        case 2: parent.centerChild = null; break;
                        case 3: parent.centerRightChild = null; break;
                        case 4: parent.rightChild = null; break;
                    }
                    // Mark as removed
                    removed = true;
                    break;
                }
            }
            // This node was not found in its parent's children
            if (!removed) return false;

            parent = null;  // update leaf status here if needed
            return true;
        }

        public void printInOrder(int indent) {
            if (!isLeaf) leftChild.printInOrder(indent + 1);
            printIndents(indent);
            System.out.printf("%d\n", value1);
            if (isThreeNode()) {
                if (!isLeaf) centerChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
            } else if (isFourNode()) {
                if (!isLeaf) centerLeftChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
                if (!isLeaf) centerRightChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value3);
            }
            if (!isLeaf) rightChild.printInOrder(indent + 1);
        }

        // Override toString for better debugging
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            int[] vals = {value1, value2, value3};
            for (int i = 0; i < values; i++) {
                if (i > 0) sb.append(", ");
                sb.append(vals[i]);
            }
            sb.append("]");
            return sb.toString();
        }

        // update target value with successor for deletion
        public boolean update(int value, int newValue) {
            // Assume value is one of the values in the node
            int[] vals = {value1, value2, value3};
            // Count how many values are in the node
            for (int i = 0; i < values; i++) {
                // Check if the value matches
                if (vals[i] == value) {
                    // Update the value
                    vals[i] = newValue;
                    // Reassign the values back to the node
                    value1 = vals[0];
                    // Reassign value2 and value3 if they exist
                    if (values > 1) value2 = vals[1];
                    if (values > 2) value3 = vals[2];
                    // Update the count of values
                    return true;
                }
            }
            // If the value was not found, return false
            return false;
        }
    }

    TwoFourTreeItem root = null;

    // Returns true on successful operation
    public boolean addValue(int value) {
        // If the tree is empty, create a new root node
        if(root == null) {
            root = new TwoFourTreeItem(value);
            return true;
        }
        else {
            TwoFourTreeItem curNode = root;
            // Traverse down the tree to find the correct position for the new value
            while(curNode != null) {
                // If the current node contains the value, return true
                if(curNode.valueOfCurrNode(value)) return true;
                if(curNode.isFourNode()) {
                    curNode = splitNode(curNode);
                }
                if(curNode.isLeaf) {
                    if(hasValue(value)) System.out.println("Value exists in tree");
                    if(!attachValue(curNode, value)) {
                        throw new IllegalStateException("Cannot attach value to node: " + curNode);
                    }
                    return true;
                }
                // If the current node is not a leaf, get the next child based on the value
                curNode = getNextChild(curNode, value);
            }
        }
        return false;
    }
    
    // Returns the next child based on the value
    private TwoFourTreeItem getNextChild(TwoFourTreeItem node, int value) {
        // If node is two-node, three-node, or four-node, return the next child based on value
        if(node.isTwoNode()) {
            if(value < node.value1) return node.leftChild;
            else if(value > node.value1) return node.rightChild;
            else throw new IllegalStateException("Should not be checking if value is in node");
        }
        // now check if node is three-node or four-node
        else if(node.isThreeNode()) {
            if(value < node.value1) return node.leftChild;
            else if(value < node.value2) return node.centerChild;
            else if(value > node.value3) return node.rightChild;
            else throw new IllegalStateException("Should not be checking if value is in node");
        }
        // check if node is four-node
        else if(node.isFourNode()) {
            if(value < node.value1) return node.leftChild;
            else if(value < node.value2) return node.centerLeftChild;
            else if(value < node.value3) return node.centerRightChild;
            else if(value > node.value3) return node.rightChild;
            else throw new IllegalStateException("Should not be checking if value is in node");
        }
        // ERROR - NOT A 2, 3, OR 4 NODE
        else throw new IllegalStateException("Not a 2,3, or 4 node");
    }

    // splitNodes a 4-node into two 2-nodes and returns the new parent node
    private TwoFourTreeItem splitNode(TwoFourTreeItem node) {
        int value = node.value2;
        TwoFourTreeItem left = new TwoFourTreeItem(node.value1);
        TwoFourTreeItem right = new TwoFourTreeItem(node.value3);

        // Assign children using a helper
        assignsplitNodeChildren(node, left, right);

        if (node.isRoot()) {
            root = new TwoFourTreeItem(value);
            root.attachChild(left);
            root.attachChild(right);
            return root;
        } else {
            // Remove node from parent's children
            detachFromParent(node);
            node = node.parent;
            attachValue(node, value);
            node.attachChild(left);
            node.attachChild(right);
            return node;
        }
    }

    // Helper to assign children to left and right nodes after splitNode
    private void assignsplitNodeChildren(TwoFourTreeItem original, TwoFourTreeItem left, TwoFourTreeItem right) {
        left.attachChild(original.leftChild);
        left.attachChild(original.centerLeftChild);
        right.attachChild(original.centerRightChild);
        right.attachChild(original.rightChild);
    }

    // Helper to detach a node from its parent
    private void detachFromParent(TwoFourTreeItem node) {
        if(node.parent.leftChild == node) node.parent.leftChild = null;
        else if(node.parent.centerLeftChild == node) node.parent.centerLeftChild = null;
        else if(node.parent.centerChild == node) node.parent.centerChild = null;
        else if(node.parent.centerRightChild == node) node.parent.centerRightChild = null;
        else if(node.parent.rightChild == node) node.parent.rightChild = null;
        else throw new IllegalStateException("Node is not a child of node.parent");
    }
    
    // Attaches a value to a node
    private boolean attachValue(TwoFourTreeItem node, int value) {
        // If the node is null, return false
        if(node.isFourNode()) return false;
        // If the node is a leaf, we can just add the value
        else if(node.isThreeNode()) {
            if(value < node.value1) {
                node.value3 = node.value2;
                node.value2 = node.value1;
                node.value1 = value;
            }
            else if(value < node.value2) {
                node.value3 = node.value2;
                node.value2 = value;
            }
            else node.value3 = value;
        }
        else {
            // 2-node
            if(value < node.value1) {
                node.value2 = node.value1;
                node.value1 = value;
            }
            else node.value2 = value;
        }
        node.values++;
        if(!node.isLeaf) reassignChildren(node);
        return true;
    }

    // Takes a value from a node, shifting values down if necessary
    private boolean takeValue(TwoFourTreeItem node, int value) {
        // If the node is null, return false
        if(value == node.value1) {
            node.value1 = node.value2;
            node.value2 = node.value3;
        }
        // If the node is a 3-node, we can just remove the value
        else if(value == node.value2) {
            node.value2 = node.value3;
        }
        node.value3 = 0;
        node.values--;

        // If the node is now empty, we need to remove it from its parent
        return true;
    }

    // Reassigns children of a node, ensuring they are properly attached
    private boolean reassignChildren(TwoFourTreeItem node) {
        if(node.isLeaf) return true; // no children to organize
        // if(node.isNodeValid()) return true;

        // Reassign children to the node, ensuring they are properly attached
        TwoFourTreeItem[] children = new TwoFourTreeItem[] {
                node.leftChild,
                node.centerLeftChild,
                node.centerChild,
                node.centerRightChild,
                node.rightChild
        };

        // Reset the children pointers
        node.leftChild = null;
        node.centerLeftChild = null;
        node.centerChild = null;
        node.centerRightChild = null;
        node.rightChild = null;

        // Attach children to the node
        for(TwoFourTreeItem child : children) {
            // If the child is null, skip it
            if(!node.attachChild(child)) return false;
        }

        // If we reach here, all children have been successfully reassigned
        return true;
    }

    // Returns true if value exists in tree
    public boolean hasValue(int value) {
        // If the tree is empty, return false
        if(root == null) return false;

        // Start at the root and traverse down the tree
        TwoFourTreeItem curNode = root;
        while(curNode != null) {
            // The current node is a leaf, check if it contains the value
            if(curNode.valueOfCurrNode(value)) return true;
            else curNode = getNextChild(curNode, value);
        }

        // If we reach here, the value was not found
        return false;
    }

    // Deletes a value from the tree, returning true if successful
    public boolean deleteValue(int value) {
        TwoFourTreeItem target = root;
        // If the tree is empty, return false
        while(target != null && !target.valueOfCurrNode(value)) {
            // Traverse down the tree to find the target node
            target = getNextChild(target, value);
            // If the target is a two-node, merge with its sibling
            if(target != null && target.isTwoNode()) target = mergeWithSibling(target);
        }

        if(target == null) return false; // Value to be deleted not found

        // Value to be deleted found:
        if(target.isLeaf) {
            // If the target is a leaf node, we can just remove the value
            takeValue(target, value);
            // If the target is now empty, we need to remove it from its parent
            if(target.values == 0) target.removeFromParent();
            return true;
        }

        // If the target is not a leaf node, we need to find the successor
        TwoFourTreeItem candidate = getReplacementSubTree(target, value);
        // If the successor is null, we cannot delete the value
        if(candidate == null) throw new IllegalStateException("Failed to find a proper condidate for deletion");
        else {
            int newValue;
            if(candidate.value1 > value) newValue = candidate.value1;
            else {
                switch(candidate.values) {
                    case 1: {
                        throw new IllegalStateException("Successor should not be a 2-node");
                    }
                    case 2: {
                        newValue = candidate.value2;
                        break;
                    }
                    case 3: {
                        newValue = candidate.value3;
                        break;
                    }
                    default: {
                        throw new ArithmeticException("Fix the successor");
                    }
                }
            }
            // Now we have the new value to replace the target value
            while(!target.valueOfCurrNode(value)) {
                // Update the target node with the new value
                target = getNextChild(target, value);
            }
            // If the target is a leaf node, we can just update the value
            if(target.isLeaf) { 
                // If the target is a leaf node, we can just update the value
                if(target != candidate) throw new IllegalStateException("check here for bug");
                // If the target is a leaf node, we can just update the value
                takeValue(candidate, value);
                // Update the target node with the new value
                if(candidate.values == 0) candidate.removeFromParent();
                return true;
            }

            // If the target is not a leaf node, we need to update the value
            target.update(value, newValue);
            takeValue(candidate, newValue);
            // Reassign children of the target node
            if(target.values == 0) target.removeFromParent();
            return true;
        }
    }

    // Merges a node with its sibling, returning the new parent node if necessary
    private TwoFourTreeItem mergeWithSibling(TwoFourTreeItem node) {
        TwoFourTreeItem[] siblings = getSibling(node);

        if(siblings == null || siblings[0] == null && siblings[1] == null) {
            return node;
        }

        boolean isLeftSibling = true;

        // This loop handles Case 1
        for(TwoFourTreeItem sibling : siblings) {
            if(sibling == null) {
                isLeftSibling = false;
                continue;
            }

            if(!sibling.isTwoNode()) { 
                // If the sibling is not a 2-node, we can rotate
                int parentValue; 
                int siblingValue;

                // Determine which sibling we are dealing with
                if(isLeftSibling) {
                    
                    // use rightmost value of the sibling node
                    switch(sibling.values) {
                        case 1: throw new IllegalStateException("No 2-node when rotating");
                        case 2: {
                            siblingValue = sibling.value2;
                            break;
                        }
                        case 3: {
                            siblingValue = sibling.value3;
                            break;
                        }
                        default: throw new ArithmeticException("Count better");
                    }
                }
                else {
                    // use leftmost value of the sibling node
                    siblingValue = sibling.value1;
                }

                // The parent value is the value that oversees the node and its sibling
                if(isLeftSibling) {
                   parentValue = getBridgeValue(node.parent, siblingValue, node.value1);
                }
                else {
                    parentValue = getBridgeValue(node.parent, node.value1, siblingValue);
                }

                // Now that we have parentValue and siblingValue, we rotate!

                node.parent.update(parentValue, siblingValue);
                if(!attachValue(node, parentValue)) throw new IllegalStateException("Should be 2-node");
                // Node is now a 3-node

                // Inheriting sibling's child
                if(isLeftSibling) {
                    node.attachChild(sibling.rightChild);
                    sibling.rightChild = null;
                }
                else {
                    node.attachChild(sibling.leftChild);
                    sibling.leftChild = null;
                }

                // Removing siblingValue
                takeValue(sibling, siblingValue);
                reassignChildren(sibling);

                return node;

            }

            // If we reach here, the sibling is a 2-node
            isLeftSibling = false;
        }

        // Should only ever exit the loop if one or more siblings are 2-node
        TwoFourTreeItem sibling;

        // Determine which sibling to merge with
        if(siblings[0] == null) {
            sibling = siblings[1];
        }
        else if(siblings[1] == null) {
            sibling = siblings[0];
        }
        else {
            // Do I prefer left or right sibling? I'm going to choose right.
            sibling = siblings[1];
        }

        // Case 2
        if(node.parent.isTwoNode()) {

            // Removing node and sibling from being a child of node.parent
            node.parent.leftChild = null;
            node.parent.rightChild = null;

            attachValue(node.parent, node.value1);
            attachValue(node.parent, sibling.value1);

            if(!node.parent.attachChild(node.leftChild))
                throw new IllegalStateException("Error attaching node left child");
            if(!node.parent.attachChild(node.rightChild))
                throw new IllegalStateException("Error attaching node right child");
            if(!node.parent.attachChild(sibling.leftChild))
                throw new IllegalStateException("Error attaching sibling left child");
            if(!node.parent.attachChild(sibling.rightChild))
                throw new IllegalStateException("Error attaching sibling right child");

            return node.parent;
        }
        // Case 3
        else {
            // Finding parent value for merge
            isLeftSibling = sibling == siblings[0];
            int parentValue;
            if(isLeftSibling) {
                parentValue = getBridgeValue(node.parent, sibling.value1, node.value1);
            }
            else {
                parentValue = getBridgeValue(node.parent, node.value1, sibling.value1);
            }

            // Now that we have parentValue, merge!
            attachValue(node, parentValue);
            attachValue(node, sibling.value1);

            // Removing sibling from being a child of node.parent
            if(node.parent.leftChild == sibling) node.parent.leftChild = null;
            else if(node.parent.centerLeftChild == sibling) node.parent.centerLeftChild = null;
            else if(node.parent.centerChild == sibling) node.parent.centerChild = null;
            else if(node.parent.centerRightChild == sibling) node.parent.centerRightChild = null;
            else if(node.parent.rightChild == sibling) node.parent.rightChild = null;
            else throw new IllegalStateException("Sibling is not a child of node.parent");

            // Removing sibling from being a child of node
            takeValue(node.parent, parentValue);
            reassignChildren(node.parent);

            // Now we need to attach the children of node and sibling to the new node
            if(!node.attachChild(sibling.leftChild))
                throw new IllegalStateException("Not enough space for child.");
            if(!node.attachChild(sibling.rightChild))
                throw new IllegalStateException("Not enough space for child.");

            return node;
        }
    }

    // Returns an array containing the siblings of a node
    private TwoFourTreeItem[] getSibling(TwoFourTreeItem node) {
        // If the node is root, it has no siblings
        if(node.parent == null) return null;

        // Determine if the node is a left, center-left, center, center-right, or right child
        boolean isLeftChild = node == node.parent.leftChild;
        boolean isCenterLeftChild = node == node.parent.centerLeftChild;
        boolean isCenterChild = node == node.parent.centerChild;
        boolean isCenterRightChild = node == node.parent.centerRightChild;
        boolean isRightChild = node == node.parent.rightChild;

        // siblings[0] is sibling to left, and siblings[1] is siblings to right
        TwoFourTreeItem[] siblings = new TwoFourTreeItem[2];

        // Determine the siblings based on the type of child
        if(isLeftChild)  {
            siblings[0] = null;
            switch(node.parent.values) {
                case 1: {
                    siblings[1] = node.parent.rightChild;
                    break;
                }
                case 2: {
                    siblings[1] = node.parent.centerChild;
                    break;
                }
                case 3: {
                    siblings[1] = node.parent.centerLeftChild;
                    break;
                }
                default:  throw new ArithmeticException("There are too many values in parent");
            }
        }
        else if(isCenterLeftChild) {
            siblings[0] = node.parent.leftChild;
            siblings[1] = node.parent.centerRightChild;
        }
        else if(isCenterChild) {
            siblings[0] = node.parent.leftChild;
            siblings[1] = node.parent.rightChild;
        }
        else if(isCenterRightChild) {
            siblings[0] = node.parent.centerLeftChild;
            siblings[1] = node.parent.rightChild;
        }
        else if(isRightChild) {
            siblings[1] = null;
            // Determine which sibling to assign based on the parent's values
            switch(node.parent.values) {
                case 1: {
                    siblings[0] = node.parent.leftChild;
                    break;
                }
                case 2: {
                    siblings[0] = node.parent.centerChild;
                    break;
                }
                case 3: {
                    siblings[0] = node.parent.centerRightChild;
                    break;
                }
                default:  throw new ArithmeticException("There are too many values in parent");
            }
        }
        else throw new IllegalStateException("Here is a bug in getSibling");

        return siblings;
    }

    // Returns the value that is overseeing the node and its sibling
    private int getBridgeValue(TwoFourTreeItem parent, int lower, int upper) {
        if (parent == null) throw new IllegalStateException("getBridgeValue");
        if (parent.values >= 1 && parent.value1 > lower && parent.value1 < upper) return parent.value1;
        if (parent.values >= 2 && parent.value2 > lower && parent.value2 < upper) return parent.value2;
        if (parent.values == 3 && parent.value3 > lower && parent.value3 < upper) return parent.value3;
        // If not found, print debug info
        throw new IllegalStateException("No parent value overseeing node and sibling");
    }

    // Advances to a leaf node, going left or right based on the goLeft parameter
    private TwoFourTreeItem advancedToLeaf(TwoFourTreeItem node, boolean goLeft) {
        // Traverse to the left or right child until reaching a leaf node
        while (!node.isLeaf) {
            // Check if the node is a two-node, three-node, or four-node
            node = goLeft ? node.leftChild : node.rightChild;
            // If the node is a three-node, we need to check the center child
            if (node.isTwoNode()) node = mergeWithSibling(node);
        }
        // If we reach here, we have found a leaf node
        return node;
    }

    // Returns the subtree that contains the replacement value for the target
    private TwoFourTreeItem getReplacementSubTree(TwoFourTreeItem node, int target) {
        // If the node is a leaf, we cannot find a replacement subtree
        if(node.isLeaf) return node;

        // Check if the target is one of the values in the node
        boolean[] isValue = {
            node.value1 == target,
            node.value2 == target,
            node.value3 == target
        };

        TwoFourTreeItem candidate = null;
        if(isValue[0] || isValue[1] || isValue[2]) {
            candidate = getNextSubTree(node, target);
            if(candidate == null) throw new IllegalStateException("No successor");
        } else {
            throw new IllegalStateException("Should be a value inside node");
        }

        // Moved down to one of node's children
        int candidateVal = candidate.value1;
        if(candidate.isTwoNode()) candidate = mergeWithSibling(candidate);
        if(candidate.valueOfCurrNode(target)) candidate = getReplacementSubTree(candidate, target);
        if(candidateVal > target) {
            // Find minimum
            candidate = advancedToLeaf(candidate, true);
        }
        else if(candidateVal < target) {
            // Find maximum
            candidate = advancedToLeaf(candidate, false);
        }
        else if(!candidate.isLeaf && candidate.valueOfCurrNode(target)) throw new IllegalStateException("Target not candidate");

        return candidate;
    }

    // Returns the next subtree based on the target value
    private TwoFourTreeItem getNextSubTree(TwoFourTreeItem node, int target) {
        if (node.isTwoNode()) {
            // If the node is a two-node, return the appropriate child
            return node.rightChild != null ? node.rightChild : node.leftChild;
        } else if (node.isThreeNode()) {
            if (target == node.value1) {
                return node.centerChild != null ? node.centerChild : node.leftChild;
            } else if (target == node.value2) {
                return node.rightChild != null ? node.rightChild : node.centerChild;
            }
        // If the node is a four-node, return the appropriate child based on the target value
        } else if (node.isFourNode()) {
            if (target == node.value1) {
                // If the target is the first value, return the left child
                return node.centerLeftChild != null ? node.centerLeftChild : node.leftChild;
            } else if (target == node.value2) {
                // If the target is the second value, return the center left child
                return node.centerRightChild != null ? node.centerRightChild : node.centerLeftChild;
            } else if (target == node.value3) {
                // If the target is the third value, return the right child
                return node.rightChild != null ? node.rightChild : node.centerRightChild;
            }
        }
        throw new IllegalStateException("No valid successor child for given node and target");
    }

    public void printInOrder() {
        if (root != null) root.printInOrder(0);
    }

    public TwoFourTree() {
        root = null;
    }

}
