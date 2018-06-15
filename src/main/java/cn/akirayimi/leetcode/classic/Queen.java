package cn.akirayimi.leetcode.classic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Queen {
	int row;
	int solve;
	int[][] board;
	public Queen(int row) throws FileNotFoundException{
		this.row = row;
		board = new int[row][row];
		for (int i = 0; i < row; i++){
			for (int j = 0; j < row; j++){
				board[i][j] = 0;
			}
		}
		PrintStream pw = new PrintStream(new File("test.txt"));
		System.setOut(pw);
		put(0);
	}
	
	/**
	 * rowNum begin with 0.
	 * @param rowNum
	 */
	private void put(int rowNum){
		if (rowNum == row){
			solve++;
			System.out.println("找到第" + solve + "种解法：");
			printBoard();
			clearRow(rowNum - 1);
			return;
		}
   		int firstQueenColIndex = getQueenCol(rowNum);
		
		clearRow(rowNum);
		//本行可以放置
		for (int i = firstQueenColIndex + 1; i < row; i++){
			if (valid(board, rowNum, i)){
				board[rowNum][i] = 1; //放置
				put(rowNum+1);
			}
		}
		if (rowNum == 0){
			System.out.println("一共找到" + solve + "种解法。");
			System.exit(0);
		} else {
			put(rowNum-1);
		}
	}
	
	private void clearRow(int rowNum) {
		for (int i = 0; i < this.row; i++){
			board[rowNum][i] = 0;
		}
	}

	/**
	 * 根据行得到本行皇后位置
	 * @param rowNum
	 * @return
	 */
	private int getQueenCol(int rowNum) {
		for (int i = 0; i < this.row; i++){
			if (board[rowNum][i] == 1)
				return i;
		}
		return -1;
	}

	private void printBoard() {
		System.out.println("#################");
		for (int i = 0; i < this.row; i++){
			for (int j = 0; j < this.row; j++){
				System.out.print(board[i][j] == 0 ? "○" + " " : "●" + " ");
			}
			System.out.println();
		}
		System.out.println("#################");
	}

	private boolean valid(int[][] cheess, int rowNum, int colNum) {
		// 由于规则是每行每行放置，所以不用检查行冲突了。
		// 列冲突
		for (int i = 0; i < this.row; i++){
			if (cheess[i][colNum] == 1)
				return false;
		}
		// 斜冲突
		for (int i = 0; i < rowNum; i++){
			for (int j = 0; j < this.row; j++){
				if (cheess[i][j] == 1){
					if (Math.abs((rowNum - i)) == Math.abs((colNum - j)) ){
						return false;
					}
					break;
				}
			}
		}
		return true;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Queen q = new Queen(8);
		System.out.println(q.solve);
	}
}
