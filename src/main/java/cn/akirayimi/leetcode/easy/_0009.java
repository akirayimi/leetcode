package cn.akirayimi.leetcode.easy;

import java.util.ArrayList;
import java.util.List;

/**
 * Determine whether an integer is a palindrome. An integer is a palindrome when
 * it reads the same backward as forward.
 * 
 * Example 1:
 * 
 * Input: 121 Output: true
 * 
 * Example 2:
 * 
 * Input: -121 Output: false Explanation: From left to right, it reads -121.
 * From right to left, it becomes 121-. Therefore it is not a palindrome.
 * 
 * Example 3:
 * 
 * Input: 10 Output: false Explanation: Reads 01 from right to left. Therefore
 * it is not a palindrome.
 * 
 * Follow up:
 * 
 * Coud you solve it without converting the integer to a string?
 * 
 * @author akira
 *
 */
public class _0009 {
	public static void main(String[] args) {
		Solution9 s = new Solution9();
		System.out.println(s.isPalindrome(10));
	}
}

class Solution9 {
	public boolean isPalindrome(int x) {
		return solution2(x);
	}
	
	public boolean solution2(int x){
		if (x % 10 == 0 && x != 0)return false;
		int halfRev = 0;
		while (x > halfRev){
			halfRev *= 10;
			halfRev += (x % 10);
			if (x == halfRev)return true;
			x /= 10;
		}
		return x == halfRev;
	}

	public boolean solution1(int x) {
		if (x < 0)
			return false;
		if (x == 0)
			return true;
		List<Integer> list = new ArrayList<Integer>();

		while (x != 0) {
			int n = x % 10;
			list.add(n);
			x = x / 10;
		}
		
		for (int i = 0, j = list.size() - 1; i < list.size() / 2; i++, j--) {
			if (!list.get(i).equals(list.get(j)))
				return false;
		}
		return true;
	}
}
