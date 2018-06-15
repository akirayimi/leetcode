package cn.akirayimi.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of non-negative integers, you are initially positioned at the
 * first index of the array.
 * 
 * Each element in the array represents your maximum jump length at that
 * position.
 * 
 * Determine if you are able to reach the last index.
 * 
 * Example 1:
 * 
 * Input: [2,3,1,1,4] Output: true Explanation: Jump 1 step from index 0 to 1,
 * then 3 steps to the last index.
 * 
 * Example 2:
 * 
 * Input: [3,2,1,0,4] Output: false Explanation: You will always arrive at index
 * 3 no matter what. Its maximum jump length is 0, which makes it impossible to
 * reach the last index.
 * 
 * 
 * @author akira
 *
 */
public class _0055 {
	public static void main(String[] args) {
		Solution55 s = new Solution55();
		s.canJump(new int[]{2,1,1,1,4,8,5,5,1,4,4,5,1,1,1,7,1,1,0,5});
	}
}

class Solution55 {
	public boolean canJump(int[] nums) {
		return solution1(nums);
	}
	
	public boolean solution1(int[] nums){
		if (nums.length == 1)
			return true;
		return jump(nums, nums.length - 1);
	}
	
	public boolean jump(int[] nums, int position){
		System.out.println("目标: " + (position + 1));
		List<Integer> list = thosePositionCanReachSpecifyIndex(nums, position);
		if (list.size() == 0){ // 前面没有可以跳到指定点的跳板
			System.out.println("没有可以到达目标的点");
			return false;
		}
		System.out.println("在这些位置的点可以跳到位置" + (position + 1 ) + ": " + printList(list));
		for (Integer prePosition: list){
			if (prePosition == 0){
				System.out.println("从起始点可以跳到" + (position + 1) + ", 做到了。");
				return true;
			}
			return jump(nums, prePosition);
		}
		return false;
	}
	
	public List<Integer> thosePositionCanReachSpecifyIndex(int[] nums, int specifyIndex){
		List<Integer> ret = new ArrayList<Integer>();
		for (int i = 0; i < specifyIndex; i++){
			int steps = nums[i];
			if (steps >= specifyIndex - i){
				ret.add(i);
			}
		}
		
		return ret;
	}
	
	public String printList(List<Integer> list){
		StringBuilder sb = new StringBuilder("[");
		for (Integer i : list){
			sb.append(i + 1).append(" ");
		}
		sb.append("]");
		return sb.toString();
	}
	
}
