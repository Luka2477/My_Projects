package model;

public class Board {

    private final int[][] board;

    public Board(int[][] board) {
        this.board = board;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public void print () {
        for (int i = 0; i < this.board.length; i++) {
            if (i != 0 && i % 3 == 0) {
                System.out.println("----------------------");
            }

            for (int j = 0; j < this.board.length; j++) {
                if (j != 0 && j % 3 == 0) {
                    System.out.print("| ");
                }

                System.out.print(this.board[i][j] + " ");
            }
            System.out.println();
        }
    }

}
