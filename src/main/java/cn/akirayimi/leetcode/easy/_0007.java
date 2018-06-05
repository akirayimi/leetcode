package cn.akirayimi.leetcode.easy;

/**
 Given a 32-bit signed integer, reverse digits of an integer.
Example 1:

Input: 123
Output: 321

Example 2:

Input: -123
Output: -321

Example 3:

Input: 120
Output: 21

Note:
Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. 
For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.

 * @author akira
 *
 */
public class _0007 {
	public static void main(String[] args) {
		Solution7 s = new Solution7();
		System.out.println(Integer.MAX_VALUE);
		System.out.println(s.reverse(248723));
	}
}
class Solution7 {
    public int reverse(int x) {
        return solution1(x);
    }
    
    public int solution1(int x){
    	boolean negetive = x < 0;
    	if (negetive){
    		String str = String.valueOf(x);
    		try{
    			int ret = 0 - Integer.valueOf(reverseStr(str.substring(1, str.length())));
    			return ret;
    		} catch (Exception e){
    			return 0;
    		}
    	} else {
    		try {
    			int ret = Integer.valueOf(reverseStr(String.valueOf(x)));
    			return ret;
    		} catch(Exception e ){
    			return 0;
    		}
    	}
    }

	private String reverseStr(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = str.length() - 1; i >= 0; i--){
			sb.append(str.charAt(i));
		}
		return sb.toString();
	}
}