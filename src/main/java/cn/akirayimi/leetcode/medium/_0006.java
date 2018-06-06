package cn.akirayimi.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

/**
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number
 * of rows like this: (you may want to display this pattern in a fixed font for
 * better legibility)
 * 
 * P A H N A P L S I I G Y I R
 * 
 * And then read line by line: "PAHNAPLSIIGYIR"
 * 
 * Write the code that will take a string and make this conversion given a
 * number of rows:
 * 
 * string convert(string s, int numRows);
 * 
 * Example 1:
 * 
 * Input: s = "PAYPALISHIRING", numRows = 3 Output: "PAHNAPLSIIGYIR"
 * 
 * Example 2:
 * 
 * Input: s = "PAYPALISHIRING", numRows = 4 Output: "PINALSIGYAHRPI"
 * Explanation:
 * 
 * P     I    N
   A   L S  I G
   Y A   H R
   P     I
 * 
 * 
 * @author akira
 *
 */
public class _0006 {

}

class Solution {
	public String convert(String s, int numRows) {
		return solution1(s, numRows);
	}
	
	public String solution1(String s, int row){
		if (s == null) return null;
		StringBuilder[] list = new StringBuilder[row];
		
		for (int i = 0; i < s.length(); i++){
			char cur = s.charAt(i);
			list[i].append(cur);//modify
		}
			
		StringBuilder ret = new StringBuilder();
		for (StringBuilder sb : list){
			ret.append(sb);
		}
		return ret.toString();
	}
}