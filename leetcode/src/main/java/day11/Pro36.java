package day11;

/**
 * @description
 * @date:2022/9/28 12:54
 * @author: qyl
 */
public class Pro36 {
    public static void main(String[] args) {
        char[][] board = {{'8','3','.','.','7','.','.','.','.'}
                ,{'6','.','.','1','9','5','.','.','.'}
                ,{'.','9','8','.','.','.','.','6','.'}
                ,{'8','.','.','.','6','.','.','.','3'}
                ,{'4','.','.','8','.','3','.','.','1'}
                ,{'7','.','.','.','2','.','.','.','6'}
                ,{'.','6','.','.','.','.','2','8','.'}
                ,{'.','.','.','4','1','9','.','.','5'}
                ,{'.','.','.','.','8','.','.','7','9'}};
        System.out.println(new Solution36().isValidSudoku(board));
    }
}

class Solution36 {
    boolean[][] rowUse = new boolean[10][10];
    boolean[][] colUse = new boolean[10][10];
    boolean[][] gridUse = new boolean[10][10];

    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                int num = board[i][j]-'0';
                if (rowUse[i][num] || colUse[j][num] || gridUse[(i/3)*3+j/3][num]){
                    return false;
                }
                rowUse[i][num] = colUse[j][num] = gridUse[(i/3)*3+j/3][num] = true;
            }
        }
        return true;
    }
}
