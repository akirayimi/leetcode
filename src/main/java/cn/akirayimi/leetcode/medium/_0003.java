package cn.akirayimi.leetcode.medium;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*Given a string, find the length of the longest substring without repeating characters.
Examples:

Given "abcabcbb", the answer is "abc", which the length is 3.

Given "bbbbb", the answer is "b", with the length of 1.

Given "pwwkew", the answer is "wke", with the length of 3. 
Note that the answer must be a substring, "pwke" is a subsequence and not a substring*/
public class _0003 {
	public static void main(String[] args) {
		Solution3 s3 = new Solution3();
		System.out.println(s3.lengthOfLongestSubstring("dvdf"));
	}
}

class Solution3 {
	/**
	 * Time Limit Exceeded 
	 * @param s
	 * @return
	 */
	public int solution1(String s){
		long start = System.currentTimeMillis();
		 Map<Character, Integer> map = new HashMap<Character, Integer>();
	        int back = 0;
	    	int tempMax = 0;
	        for (int i = 0; i < s.length(); i++){
	        	char cur = s.charAt(i);
	        	//如果当前字符已经存在，则记录当前map的keyset长度，清空，index置为已存在的那个字符的下标，下次循环从那个字符后开始。
	        	if (map.containsKey(cur)){
	        		back = map.get(cur);
	        		tempMax = Math.max(tempMax, map.keySet().size());
	        		map.clear();
	        		i = back;
	        	} else { //不存在的话就放进map，并且记录这个字符的下标。
	        		map.put(cur, i);
	        	}
	        	
	        }
	        System.out.println("cost time(ms): " + (System.currentTimeMillis() - start));
	        return Math.max(tempMax, map.keySet().size());
	}
	
	/**
	 * sliding window
	 * @param s
	 * @return
	 */
	public int solution2(String s){
		int i = 0, j = 0, max = 0;
		Set<Character> set = new HashSet<Character>();
		for (; i < s.length(); i++){
			for (j = i; j < s.length(); j++){
				char cur = s.charAt(j);
				
				if (set.contains(cur)){
					max = Math.max(max, set.size());
					set.clear();
					break;
				}
				set.add(cur);
			}
		}
		return Math.max(max, set.size());
	}
	
    public int lengthOfLongestSubstring(String s) {
       return solution2(s);
    }

}