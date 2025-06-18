# Project Title: Two-Four Tree Implementation

## Overview
This project implements a Two-Four Tree data structure in Java, which is a type of self-balancing tree that maintains sorted data and allows for efficient insertion, deletion, and search operations. The project includes an application that tests the functionality of the Two-Four Tree with various integer inputs.

## Files
- **App.java**: Contains the main application logic, including methods for generating integer lists, executing find operations on a TwoFourTree, and testing the tree with various cases. It also includes a `main` method that initializes a TwoFourTree with prime numbers and performs operations on it.

- **TwoFourTree.java**: Defines the `TwoFourTree` class, which represents the two-three-four tree data structure. It includes a nested class `TwoFourTreeItem` that represents the nodes of the tree. The class currently has methods for adding, finding, and deleting values, but these methods are not yet implemented.

## Building and Running the Application
1. Ensure you have Java Development Kit (JDK) installed on your machine.
2. Clone the repository or download the project files to your local machine.
3. Open a terminal and navigate to the project directory.
4. Compile the Java files using the following command:
   ```
   javac App.java TwoFourTree.java
   ```
5. Run the application with the following command:
   ```
   java App
   ```

## Testing
The application includes a series of test cases that demonstrate the functionality of the Two-Four Tree. The tests generate random integers, perform find operations, and measure the performance of the tree against a standard Java TreeSet.

## Future Improvements
- Implement the methods for adding, finding, and deleting values in the `TwoFourTree` class.
- Enhance the testing framework to include more comprehensive test cases.
- Consider adding a graphical representation of the tree structure for better visualization.

## License
This project is licensed under the MIT License - see the LICENSE file for details.