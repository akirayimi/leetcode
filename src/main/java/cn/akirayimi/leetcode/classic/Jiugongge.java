package cn.akirayimi.leetcode.classic;

public class Jiugongge {
	public static void main(String[] args) {
		print(1,2,3,4,5,6,7,8,9);
	}
	
	public static void start(){
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
