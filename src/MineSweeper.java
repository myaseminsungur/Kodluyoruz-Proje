import java.util.Scanner;
import java.util.Random;

public class MineSweeper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to MineSweeper!");

        int rows, cols, mines;
        char[][] board;
        boolean[][] revealed;
        boolean gameOver = false;

        // Get the number of rows and columns from the user
        System.out.print("Enter the number of rows: ");
        rows = scanner.nextInt();
        System.out.print("Enter the number of columns: ");
        cols = scanner.nextInt();

        // Create the game board, initialize it, and place mines
        board = new char[rows][cols];
        revealed = new boolean[rows][cols];
        initializeBoard(board);
        mines = rows * cols / 4; // Set mine count
        placeMines(board, random, mines);

        while (!gameOver) {
            printBoard(board, revealed);

            int row, col;
            do {
                System.out.print("Enter row and column (e.g., 1 2): ");
                row = scanner.nextInt();
                col = scanner.nextInt();

                if (row < 0 || row >= rows || col < 0 || col >= cols) {
                    System.out.println("Invalid input. Please enter valid row and column numbers.");
                }
            } while (row < 0 || row >= rows || col < 0 || col >= cols);

            if (board[row][col] == '*') {
                gameOver = true;
            } else {
                revealed[row][col] = true;
                if (board[row][col] == ' ') {
                    revealEmptyCells(board, revealed, row, col);
                }
            }

            if (isGameWon(board, revealed, mines)) {
                printBoard(board, revealed);
                System.out.println("You won!");
                break;
            }
        }

        if (gameOver) {
            printBoard(board, revealed);
            System.out.println("Game Over!");
        }

        scanner.close();
    }

    private static void initializeBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private static void placeMines(char[][] board, Random random, int mines) {
        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < mines; i++) {
            int row, col;
            do {
                row = random.nextInt(rows);
                col = random.nextInt(cols);
            } while (board[row][col] == '*');
            board[row][col] = '*';
        }
    }

    private static void printBoard(char[][] board, boolean[][] revealed) {
        // Print the game board, showing revealed cells
        System.out.print("  ");
        for (int col = 0; col < board[0].length; col++) {
            System.out.print(" " + col);
        }
        System.out.println();

        for (int row = 0; row < board.length; row++) {
            System.out.print(row + " |");
            for (int col = 0; col < board[row].length; col++) {
                if (revealed[row][col]) {
                    System.out.print(" " + board[row][col]);
                } else {
                    System.out.print(" -");
                }
            }
            System.out.println();
        }
    }

    private static void revealEmptyCells(char[][] board, boolean[][] revealed, int row, int col) {
        // Recursively reveal neighboring empty cells
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];
            if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length && !revealed[newRow][newCol]) {
                revealed[newRow][newCol] = true;
                if (board[newRow][newCol] == ' ') {
                    revealEmptyCells(board, revealed, newRow, newCol);
                }
            }
        }
    }

    private static boolean isGameWon(char[][] board, boolean[][] revealed, int mines) {
        // Check if all non-mine cells are revealed
        int emptyCells = board.length * board[0].length - mines;
        int revealedCells = 0;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (revealed[row][col]) {
                    revealedCells++;
                }
            }
        }

        return revealedCells == emptyCells;
    }
}
