package model;

public abstract class Solver {

    public static boolean isNumInRow (int[][] board, int num, int row) {
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == num) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumInColumn (int[][] board, int num, int col) {
        for (int[] ints : board) {
            if (ints[col] == num) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumInBox (int[][] board, int num, int row, int col) {
        int rowBound = row - row % (board.length / 3);
        int colBound = col - col % (board.length / 3);

        for (int i = rowBound; i < rowBound + board.length / 3; i++) {
            for (int j = colBound; j < colBound + board.length / 3; j++) {
                if (board[i][j] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNumValid (int[][] board, int num, int row, int col) {
        return !isNumInRow(board, num, row) &&
                !isNumInColumn(board, num, col) &&
                !isNumInBox(board, num, row, col);
    }

    public static boolean solveBoard (int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    for (int num = 1; num <= board.length; num++) {
                        if (isNumValid(board, num, i, j)) {
                            board[i][j] = num;

                            if (solveBoard(board)) {
                                return true;
                            } else {
                                board[i][j] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

}
