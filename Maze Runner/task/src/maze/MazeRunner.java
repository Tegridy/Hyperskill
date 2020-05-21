package maze;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.List;

public class MazeRunner {

    // size of the maze
    private int rows;
    private int columns;

    // size of the graph
    private int graphRows;
    private int graphColumns;

    private int exit;
    private int entrance;

    private int[][] maze;


    // list of edges
    ArrayList<Edge> listOfEdges = new ArrayList<>();

    // minimum spanning tree
    ArrayList<Edge> minimumSpanningTree = new ArrayList<>();

    /* minimum spanning tree contains edges containing two vertices
    / There are two methods to check, if a edge will cause a circle in my tree
    / 1st: go through all edges and check, if the edge is the tree
    / 2nd: (and in my opinion simpler method) search if both vertices of this edge are already covered
    /       in this
    / so i create parallel to the spanningTree a Vertex list and use it :) */
    ArrayList<Vertex> verticesInMinimumSpanningTree = new ArrayList<>();

    void createMaze(){

        // crate the base maze
        // set all numbers on the border to 1

        // I don't have to catch mazes with size < 3, because it will never
        // get to the part where it starts to look for edges

        // create maze
        maze = new int[rows][columns];

        // now fill the maze
        // go through all rows
        for(int i = 0; i < rows; i++){

            // go through all columns
            for(int j = 0; j < columns; j++){

                // set the starting Vertex always to 0
                if((i == 1) && (j == 1)){

                    maze[i][j] = 0;

                }
                else{

                    // if you are on the border, just set 1
                    if((i == 0) || (j == 0) || (i == (rows-1) || (j == (columns-1)))){

                        maze[i][j] = 1;

                    } else {

                        // if both coordinates are uneven, it's always a free field
                        // == vertices of our graph
                        if(((i%2)==1)&& ((j%2)==1)){

                            maze[i][j] = 0;

                        }
                        else {

                            // if both coordinates are even, it's always a wall
                            if(((i%2)== 0) && ((j%2)==0)){

                                maze[i][j] = 1;

                            }
                            else {

                                // now check, if our spanning tree has a edge here
                                // for this we create the supposed edge and check if it is in our list
                                // because we checked it. i and j have to be:
                                // one (and only one) even and one (and only one) uneven

                                Edge thisEdge = new Edge();
                                Vertex firstVertex = new Vertex();
                                Vertex secondVertex = new Vertex();

                                // if i is uneven, we search for a horizontal edge
                                // this means row stays the same
                                if((i%2==1)){

                                    firstVertex.setPlaceInRow((i-1)/2);
                                    firstVertex.setPlaceInColumn((j/2)-1);
                                    secondVertex.setPlaceInRow((i-1)/2);
                                    secondVertex.setPlaceInColumn((j/2));


                                }
                                else {

                                    // if i is even, we search for a vertical edge
                                    // this means the column is the same for both

                                    firstVertex.setPlaceInRow((i/2)-1);
                                    firstVertex.setPlaceInColumn((j-1)/2);
                                    secondVertex.setPlaceInRow((i/2));
                                    secondVertex.setPlaceInColumn((j-1)/2);

                                }

                                thisEdge.setFirstVertex(firstVertex);
                                thisEdge.setSecondVertex(secondVertex);
                                //weight is not relevant

                                // if we can find this edge in our tree, its a walking path (means 0)
                                // otherwise its a wall
                                boolean found = false;

                                for(Edge minimalSpanningTreeEdge : minimumSpanningTree){

                                    if(minimalSpanningTreeEdge.equals(thisEdge)){

                                        found = true;
                                        break;

                                    }

                                }

                                if(found){

                                    maze[i][j] = 0;

                                }
                                else{

                                    maze[i][j] = 1;

                                }

                            }


                        }



                    }

                }

            }


        }

        // now create a random entrance
        Random random = new Random();

        boolean createdEntrance = false;
        while(!createdEntrance){

            entrance = random.nextInt();

            entrance = Math.abs(entrance % rows);

            if(!((entrance == 0) || (entrance == (rows-1)))){

                if(maze[entrance][1] == 0){

                    maze[entrance][0] = 0;
                    createdEntrance = true;


                }


            }

        }

        boolean createdExit = false;
        while(!createdExit){

            exit = random.nextInt();

            exit = Math.abs(exit % rows);

            if(!((exit == 0) || (exit == (rows-1)))){

                int lastColumnOfMaze = columns - 2;

                if((columns%2)== 0){

                    lastColumnOfMaze = columns - 3;

                }

                if(maze[exit][lastColumnOfMaze] == 0){

                    maze[exit][columns-1] = 0;

                    if((columns%2) == 0){

                        maze[exit][columns -2] = 0;

                    }

                    createdExit = true;

                }


            }

        }

    }

    void createMinimumSpanningTree(){

        // you can only create a spanning tree, starting a size >= 5 on at least one side
        // this means our graph has to have at least one size >= 2
        if((graphRows > 1) || (graphColumns > 1)){

            // our first Vertex is the 0 0- Vertex
            Vertex startingVertex = new Vertex();
            startingVertex.setPlaceInRow(0);
            startingVertex.setPlaceInColumn(0);

            Edge nextMinimumWeight = null;

            // ad the first edge
            // go through all edges
            for(Edge edge : listOfEdges){

                // is this a edge of the starting Vertex?
                if(edge.containsVertex(startingVertex)){

                    // is this the first edge with the starting Vertex?
                    if(nextMinimumWeight == null){

                        // take the first, so i can compare it later
                        nextMinimumWeight = edge;

                    }
                    else {

                        // if another edge is smaller, take the smaller one
                        if(nextMinimumWeight.getWeight() > edge.getWeight()){

                            nextMinimumWeight = edge;

                        }

                    }

                }

            }

            // we have found our first edge! YAY \^.^/
            minimumSpanningTree.add(nextMinimumWeight);
            verticesInMinimumSpanningTree.add(nextMinimumWeight.getFirstVertex());
            verticesInMinimumSpanningTree.add(nextMinimumWeight.getSecondVertex());

            // reset our minimum weight edge
            nextMinimumWeight = null;

            // number of edges in a minimum spanning tree is always vertices - 1
            int numberOfEdgesInOurMinimumSpanningTree = (graphRows * graphColumns) - 1;

            // now get a list of all edges
            while(minimumSpanningTree.size() < numberOfEdgesInOurMinimumSpanningTree){

                // look after every edge in the graph
                for(Edge graphEdge : listOfEdges){

                    // the edge is only interesting, if it would not:
                    // generate a circle or is already part
                    // of our spanning tree
                    if(!createsCircleInSpanningTree(graphEdge)){

                        // is this edge connected with our current spanning tree?
                        for(Vertex VertexInMinimumSpanningTree : verticesInMinimumSpanningTree){

                            if(graphEdge.containsVertex(VertexInMinimumSpanningTree)){

                                // if this is the first edge, it's our current, minimal weight
                                if(nextMinimumWeight == null){

                                    nextMinimumWeight = graphEdge;

                                }
                                else {

                                    // is the weight of this edge smaller, than the others?
                                    if(graphEdge.getWeight() < nextMinimumWeight.getWeight()){

                                        nextMinimumWeight = graphEdge;

                                    }

                                }


                            }

                        }


                    }
                }

                // we found our next edge! :)
                minimumSpanningTree.add(nextMinimumWeight);

                // and for convenience the vertices to our vertices list
                verticesInMinimumSpanningTree.add(nextMinimumWeight.getFirstVertex());
                verticesInMinimumSpanningTree.add(nextMinimumWeight.getSecondVertex());

                // reset the minimum weight
                nextMinimumWeight = null;

            }

        }

    }

    boolean createsCircleInSpanningTree(Edge edge){

        boolean foundFirstVertex = false;
        boolean foundSecondVertex = false;

        for(Vertex spanningTreeVertex : verticesInMinimumSpanningTree){

            if(spanningTreeVertex.equals(edge.getFirstVertex())){

                foundFirstVertex = true;

            }

            if(spanningTreeVertex.equals(edge.getSecondVertex())){

                foundSecondVertex = true;

            }

        }

        return foundFirstVertex && foundSecondVertex;

    }

    void createGraphWithRandomEdges(){

        // my vertices are the uneven fields of the maze
        this.graphRows = ((rows%2) == 0) ? ((rows-1)/2) : (rows/2);
        this.graphColumns = ((columns%2) == 0) ? ((columns-1)/2) : (columns/2);

        // random generator (for debugging i would recommend to use a seed)
        Random random = new Random();

        // fill the List with all possible edges
        for(int i = 0; i < graphRows; i++){

            for(int j = 0; j < graphColumns; j++){

                // Vertex of the current place in the Graph
                Vertex thisVertex = new Vertex();
                thisVertex.setPlaceInRow(i);
                thisVertex.setPlaceInColumn(j);

                // if you are not in the last row, there is a Vertex under this one
                if(i < (graphRows-1)){

                    Vertex VertexUnderThisVertex = new Vertex();
                    VertexUnderThisVertex.setPlaceInRow(i + 1);
                    VertexUnderThisVertex.setPlaceInColumn(j);

                    Edge edgeFromThisVertexToTheVertexUnderIt = new Edge();
                    edgeFromThisVertexToTheVertexUnderIt.setFirstVertex(thisVertex);
                    edgeFromThisVertexToTheVertexUnderIt.setSecondVertex(VertexUnderThisVertex);
                    edgeFromThisVertexToTheVertexUnderIt.setWeight(random.nextInt());

                    listOfEdges.add(edgeFromThisVertexToTheVertexUnderIt);

                }

                // if you are not in the last column, there is a Vertex next to this one
                if(j < (graphColumns-1)){

                    Vertex VertexNextThisVertex = new Vertex();
                    VertexNextThisVertex.setPlaceInRow(i);
                    VertexNextThisVertex.setPlaceInColumn(j + 1);

                    Edge edgeFromThisVertexToTheNextVertex = new Edge();
                    edgeFromThisVertexToTheNextVertex.setFirstVertex(thisVertex);
                    edgeFromThisVertexToTheNextVertex.setSecondVertex(VertexNextThisVertex);
                    edgeFromThisVertexToTheNextVertex.setWeight(random.nextInt());

                    listOfEdges.add(edgeFromThisVertexToTheNextVertex);

                }

            }

        }

    }

    void getSizeOfMaze(){

        Scanner scanner = new Scanner(System.in);
        boolean avalible = false;

        boolean q = false;
        while (!q){

            if (!avalible){
                System.out.println("1. Generate a new maze.\n" +
                        "2. Load a maze.\n" +
                        "0. Exit.");
            } else {
                System.out.println("1. Generate a new maze.\n" +
                        "2. Load a maze.\n" +
                        "3. Save the maze.\n" +
                        "4. Display the maze.\n" +
                        "5. Find the escape.\n" +
                        "0. Exit.");
            }

            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Please, enter the size of a maze");

                    int inputRows = scanner.nextInt();
                    //int inputColumns = scanner.nextInt();
                    int inputColumns = inputRows;

                    while ((inputRows < 3) || (inputColumns < 3)) {

                        if (inputRows < 3) {

                            System.out.println("To few rows for a maze.");
                            System.out.println("Please insert a correct number (> 3)");

                            inputRows = scanner.nextInt();


                        } else {

                            System.out.println("To few columns for a maze.");
                            System.out.println("Please insert a correct number (> 3)");

                            inputColumns = scanner.nextInt();

                        }
                    }

                    this.rows = inputRows;
                    this.columns = inputColumns;

                    createGraphWithRandomEdges();
                    createMinimumSpanningTree();
                    createMaze();

                    printMaze();
                    avalible = true;

                    break;
                case 2:
                    scanner.nextLine();
                    String path = scanner.nextLine();
                    readMaze(path);
                    avalible = true;
                    break;
                case 3:
                    scanner.nextLine();
                    String path1 = scanner.nextLine();
                    saveMaze(path1);
                    break;
                case 4:
                    printMaze();
                    break;
                case 0:
                    q = true;
                    break;
                case 5:
                    printSolution();
                    break;
                default:
                    System.out.println("Incorrect option. Try again.");
            }

        }
    }


     static class Cell
    {
        int x;
        int y;

        public Cell(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "{" + x + "," + y + '}';
        }
    };

    public void shortestPath(int[][] map, Stack<Cell> path) {
        Cell start = new Cell(this.entrance, 0);
        Cell end = new Cell(this.exit, this.columns - 1);


        // initialize distances array filled with infinity
        int[][] distances = new int[map.length][];
        for (int i = 0; i < map.length; i++) {
            distances[i] = new int[map[i].length];
            Arrays.fill(distances[i], Integer.MAX_VALUE);
        }

        // the start node should get distance 0
        int distance = 0;
        List<Cell> currentCells = Collections.singletonList(start);

        while (distances[end.x][end.y] == Integer.MAX_VALUE
                && !currentCells.isEmpty()) {
            List<Cell> nextCells = new ArrayList<>();

            // loop over all cells added in previous round
            // set their distance
            //    and add their neighbors to the list for next round
            for (Cell cell : currentCells) {
                if (distances[cell.x][cell.y] == Integer.MAX_VALUE
                        && map[cell.x][cell.y] != 1) {
                    distances[cell.x][cell.y] = distance;
                    addNeighbors(cell, nextCells, map.length, map[0].length);
                }
            }

            // prepare for next round
            currentCells = nextCells;
            distance++;
        }

        // find the path
        if (distances[end.x][end.y] < Integer.MAX_VALUE) {
            Cell cell = end;
            path.push(end);
            for (int d = distances[end.x][end.y]-1; d >= 0; d--) {
                cell = getNeighbor(cell, d, distances);
                path.push(cell);
            }
        }
    }

    // add all valid neighbors of a cell to the list
    private void addNeighbors(Cell cell, List<Cell> list,
                                     int maxRow, int maxCol) {
        int[][] ds = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : ds) {
            int row = cell.x + d[0];
            int col = cell.y + d[1];
            if (isValid(row, col, maxRow, maxCol))
                list.add(new Cell(row, col));
        }
    }

    // find the neighbor of a cell having a certain distance from the start
    private Cell getNeighbor(Cell cell, int distance, int[][] distances) {
        int[][] ds = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : ds) {
            int row = cell.x + d[0];
            int col = cell.y + d[1];
            if (isValid(row, col, distances.length, distances[0].length)
                    && distances[row][col] == distance)
                return new Cell(row, col);
        }
        return null;
    }

    // check if coordinates are inside the maze
    private static boolean isValid(int row, int col, int maxRow, int maxCol) {
        return row >= 0 && row < maxRow && col >= 0 && col < maxCol;
    }

    void printMaze(){

        for(int[] row : this.maze) {
            for (int tile : row) {

                switch (tile) {
                    case 1:
                        System.out.print("\u2588\u2588");
                        break;

                    case 0:
                        System.out.print("  ");
                        break;

                    case 2:
                        System.out.print("//");
                        break;

                    default:
                        System.out.println("Wow, this shouldn't happen!");
                        break;
                }
            }
            System.out.println();
        }
    }

    void printSolution(){
        Stack<Cell> path = new Stack<>();
        shortestPath(maze, path);

        while (!path.isEmpty()) {
            Cell cell = path.pop();
            int x = cell.x;
            int y = cell.y;

            maze[x][y] = 2;
        }
        printMaze();
    }

    void saveMaze(String path){
        File file = new File(path);
        try(FileOutputStream cell = new FileOutputStream(file)) {

            // First 2 bytes will be size of the maze
            cell.write(rows);
            cell.write(columns);

            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    cell.write(maze[i][j]);
                }
            }
        } catch (Exception e){
            System.out.println("Can't save to a file.");
        }
    }

    void readMaze(String path){
        File file = new File(path);

        try(FileInputStream fileInputStream = new FileInputStream(file)){

            int rows = fileInputStream.read();
            int columns = fileInputStream.read();

            this.maze = new int[rows][columns];

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {

                        this.maze[i][j] = fileInputStream.read();

                    }
            }

        } catch (Exception e){
            System.out.println("The file probably does not exist.");
        }
    }

}
