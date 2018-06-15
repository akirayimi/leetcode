package cn.akirayimi.leetcode.hard;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of non-negative integers, you are initially positioned at the
 * first index of the array.
 * 
 * Each element in the array represents your maximum jump length at that
 * position.
 * 
 * Your goal is to reach the last index in the minimum number of jumps.
 * 
 * Example:
 * 
 * Input: [2,3,1,1,4] Output: 2 Explanation: The minimum number of jumps to
 * reach the last index is 2. Jump 1 step from index 0 to 1, then 3 steps to the
 * last index.
 * 
 * Note:
 * 
 * You can assume that you can always reach the last index.
 * 
 * @author akira
 *
 */
public class _0045 {

}

class Solution45 {
	int jump(int A[], int n) {
		if (n < 2) return 0;
		int level = 0, currentMax = 0, i = 0, nextMax = 0;

		while (currentMax - i + 1 > 0) { // nodes count of current level>0
			level++;
			//traverse current level , and update the max reach of next level
			for (; i <= currentMax; i++) { 
				nextMax = Math.max(nextMax, A[i] + i);
				if (nextMax >= n - 1)
					// if last element is in level+1, then the min jump=level
					return level; 
			}
			currentMax = nextMax;
		}
		return 0;
	}
}
