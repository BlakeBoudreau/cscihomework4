// Names: Blake Boudreau
// x500s: boudr055

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class MyMaze
{
    Cell[][] maze;
    int rows;
    int cols;

    public MyMaze(int rows, int cols)
    {
        maze = new Cell[rows][cols];
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                maze[i][j] = new Cell();
            }
        }

    }

    private cellAtIndex at(int i, int j){
        if (i < 0 || i >= rows) return null;
        if (j < 0 || j >= cols) return null;
        if (maze[i][j].getVisited()) return null;
        return new cellAtIndex(i, j);
    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */
    public static MyMaze makeMaze(int rows, int cols)
    {

        MyMaze newMaze = new MyMaze(rows, cols); // initialize MyMaze object
        Stack<cellAtIndex> myStack = new Stack<>(); // initialize stack
        cellAtIndex start = newMaze.at(0,0); // start index 0,0
        start.visit(); // set as visited
        myStack.push(start); // push it to the stack



        while (!myStack.empty()){ // while the stack is not empty
            start = myStack.peek(); // get top element off stack but do not remove it
            //choose random neighbor
            cellAtIndex next = null;


            if (start.i == 0){
                next = newMaze.at(newMaze.getRandomNumber(0,2), newMaze.getRandomNumber(-1,2));

            }
            if (start.j == 0){
                next = newMaze.at(newMaze.getRandomNumber(-1,2), newMaze.getRandomNumber(0,2));

            }
            if (start.i == rows - 1){
                next = newMaze.at(newMaze.getRandomNumber(-1,1), newMaze.getRandomNumber(-1,2));

            }
            if (start.j == cols - 1){
                next = newMaze.at(newMaze.getRandomNumber(-1,2), newMaze.getRandomNumber(-1,1));

            }

            if (next == null){
                myStack.pop();
            }

            else{
                next.visit();
                myStack.push(next);
                if (start.j > next.j)
                    next.openRight();
                if (next.j > start.j)
                    start.openRight();
                if (start.j > next.i)
                    next.openBottom();
                if (next.i > start.i)
                    start.openBottom();
            }
        }
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                newMaze.maze[i][j].setVisited(false);
            }
        }
        newMaze.maze[rows -1][cols -1].setRight(false);
        return newMaze;

    }

    private int getRandomNumber (int min, int max){
            Random randomInt = new Random();
            return randomInt.nextInt(max - min) + min;
    }


    /* TODO: Print a representation of the maze to the terminal */
    public void printMaze(boolean path) {

        for (int j = 0; j < cols; j++) {
            System.out.print("|---");
        }
        System.out.println("|");


        for (int i = 0; i < rows; i++) {

            System.out.print(i != 0 ? "|" : " ");

            for (int j = 0; j < cols; j++) {
                Cell cell = maze[i][j];

                if (cell.getVisited() & path) {
                    System.out.print(" * ");
                } else {
                    System.out.print("   ");
                }

                if (cell.getRight()) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();

            System.out.print("|");

            for (int j = 0; j < cols; j++) {
                Cell cell = maze[i][j];
                if (cell.getBottom()) {
                    System.out.print("---|");
                } else {
                    System.out.print("   |");
                }
            }
            System.out.println();
        }
    }


        /* TODO: Solve the maze using the algorithm found in the writeup. */
        public void solveMaze () {
            Queue<cellAtIndex> queue = new LinkedList<cellAtIndex>();
            cellAtIndex current = at(0,0);
            current.visit();
            queue.add(current);
            cellAtIndex other = null;

            while (queue.size()!= 0){
                current = queue.remove();
                

                if (current.i == rows -1 && current.j == cols -1){
                    break;
                }


                other = at(current.i, current.j - 1);
                if (other != null && other.cell.getRight()){
                    other.openRight();
                    other.visit();
                    queue.add(other);
                }
                other = at(current.i, current.j + 1);
                if (other != null && current.cell.getRight()){
                    current.openRight();
                    other.visit();
                    queue.add(other);
                }
                other = at(current.i -1, current.j);
                if (other != null && other.cell.getBottom()){
                    other.openBottom();
                    other.visit();
                    queue.add(other);
                }
                other = at(current.i + 1, current.j);
                if (other != null && current.cell.getBottom()){
                    current.openBottom();
                    other.visit();
                    queue.add(other);
                }
            }
    }


    private class cellAtIndex
    {
        public int i, j;
        public Cell cell;

        public cellAtIndex(int i, int j){
            cell = maze[i][j];
            this.i = i;
            this.j = j;
        }

        public void openRight(){
            cell.setRight(false);
        }
        public void openBottom(){
            cell.setBottom(false);
        }
        public void visit(){
            cell.setVisited(true);
        }
    }
}
