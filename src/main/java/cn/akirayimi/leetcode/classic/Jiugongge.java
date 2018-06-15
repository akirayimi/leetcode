package cn.akirayimi.leetcode.classic;

import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class Jiugongge {
	public static void main(String[] args) {
		
	}
	
	public void put(int[][] board, int num){
		//查找board第一个不为0的项，此位置就是需要放置的地方。
	}
	
	public int[][] findPutPosition(int[][] board){
		int row = board.length;
		int col = board[0].length;
		return null;
	}
	
	
	public static void init(Deque<Integer> set){
		set.clear();
		for (int i = 1; i <= 9; i++){
			set.push(i);
		}
	}
	
	public static void start(){
		Set<Integer> set = new HashSet<Integer>();
		
		
	}
	
	public static void print(int... arr){
		if (arr.length != 9)
			return;
		System.out.println("-------");
		System.out.println("|" + arr[0] + "|" + arr[1] + "|" + arr[2] +"|");
//		System.out.println("--------");
		System.out.println("|" + arr[3] + "|" + arr[4] + "|" + arr[5] +"|");
//		System.out.println("--------");
		System.out.println("|" + arr[6] + "|" + arr[7] + "|" + arr[8] +"|");
		System.out.println("-------");
	}
}
