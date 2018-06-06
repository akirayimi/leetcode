package cn.akirayimi.leetcode.medium;

import sun.applet.Main;

/**
Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

Example 1:

Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.

Example 2:

Input: "cbbd"
Output: "bb"

 * @author akira
 *
 */
public class _0005 {
	public static void main(String[] args) {
		String s = "abcded";
		Solution5 s5 = new Solution5();
		System.out.println(s5.longestPalindrome(s));
	}
}
class Solution5 {
    public String longestPalindrome(String s) {
        return solution1(s);
    }
    
    /**
     * manacher's algorithm
     * @param s
     * @return
     */
    public String solution2(String s){
    	return null;
    }
    
    public String solution1(String s){
    	String ret = null;
    	int max = 0;
    	for (int i = 0; i < s.length(); i++){
    		for (int j = i + 1; j <= s.length(); j++){
    			if (j - i <= max){
    				continue;
    			} 
    			if (isPalindromic(s, i, j)){
    				max = j - i;
    				ret = s.substring(i, j);
    			}
    		}
    	}
    	return ret;
    }
    
    public boolean isPalindromic(String s, int start, int end){
    	while (end > start){
    		char a = s.charAt(start);
    		char b = s.charAt(end - 1);
    		if (a != b)
    			return false;
    		start++;
    		end--;
    	}
    	return true;
    }
}