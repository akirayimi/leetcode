package cn.akirayimi.leetcode.hard;

import java.util.Stack;

/**
 * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.

'.' Matches any single character.
'*' Matches zero or more of the preceding element.

The matching should cover the entire input string (not partial).

Note:

    s could be empty and contains only lowercase letters a-z.
    p could be empty and contains only lowercase letters a-z, and characters like . or *.

Example 1:

Input:
s = "aa"
p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".

Example 2:

Input:
s = "aa"
p = "a*"
Output: true
Explanation: '*' means zero or more of the precedeng element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".

Example 3:

Input:
s = "ab"
p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".

Example 4:

Input:
s = "aab"
p = "c*a*b"
Output: true
Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore it matches "aab".

Example 5:

Input:
s = "mississippi"
p = "mis*is*p*."
Output: false


 * @author akira
 *
 */
public class _0010 {

}

class Solution {
	public boolean isMatch(String s, String p) {
		return solution1(s, p);
	}
	
	public boolean solution1(String s, String p){
		
		Stack<Character> ss = new Stack<Character>();
		Stack<Character> ps = new Stack<Character>();
		for (char c : s.toCharArray()){
			ss.push(c);
		}
		
		for (char c : p.toCharArray()){
			ps.push(c);
		}
		
		while (!ps.isEmpty()){
			char cur = ps.pop();
			if (cur == '.'){ //匹配任意单字符
				ss.pop();
				continue;
			} else if (cur == '*'){
				
			} else {
				
			}
		}
		
		
		
		return false;
	}
}