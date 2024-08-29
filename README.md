
# Java GUI for BFS Traversal

This is a Java-based graphical user interface (GUI) application that visualizes Breadth-First Search (BFS) traversal on a graph. The program allows you to interactively add vertices and edges, and then perform BFS traversal, displaying each step visually.

## Features

- **Add Vertices:** Click on the canvas to add vertices.
- **Add Directed Edges:** Click on two vertices to create a directed edge between them.
- **BFS Traversal:** Visually displays BFS traversal starting from vertex A.
- **Undo Functionality:** Clear the entire graph and start over by pressing the Undo button.

## Prerequisites

- Java Development Kit (JDK) installed on your machine.

## How to Run

1. **Compile the program:**

    ```bash
    javac *.java
    ```

2. **Run the program:**

    ```bash
    java JavaBFS_GUI
    ```

3. **Using the Application:**

    - **Adding Vertices:** Click on the canvas to place vertices.
    - **Adding Edges:** Click on two vertices to create a directed edge between them.
    - **BFS Traversal:** Press the BFS button to visualize the traversal.
    - **Undo:** Press the Undo button to clear the graph and start fresh.

## Code Structure

- **Main.java:** The entry point of the application. It sets up the GUI and initializes the graph.
- **GraphicalPicturePanel.java:** Handles the drawing of the graph, including vertices and edges, and manages the BFS visualization.
- **ButtonListener.java:** Manages the button actions like adding vertices, edges, and performing BFS traversal.
- **Graph.java:** Contains the logic for managing vertices, edges, and performing BFS traversal.

## Author 
- Mohammad Athar