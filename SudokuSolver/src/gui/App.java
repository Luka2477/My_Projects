package gui;

import model.Board;
import model.Solver;

public class App {

    public static void main(String[] args) {
        Board board = new Board(new int[][] {
                {1,0,6,0,0,0,4,0,0},
                {0,0,0,4,0,0,0,0,5},
                {0,0,0,0,2,0,3,0,8},
                {0,0,4,0,9,8,0,0,1},
                {0,0,0,0,7,0,0,0,0},
                {9,0,0,5,4,0,2,0,0},
                {6,0,1,0,8,0,0,0,0},
                {4,0,0,0,0,9,0,0,0},
                {0,0,5,0,0,0,8,0,3}
        });

        board.print();

        if (Solver.solveBoard(board.getBoard())) {
            System.out.println("Board is solved!");
        } else {
            System.out.println("Board is not solvable");
        }

        board.print();
    }

}
