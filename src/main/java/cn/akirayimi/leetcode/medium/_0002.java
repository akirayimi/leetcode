package cn.akirayimi.leetcode.medium;

/*You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Example

Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8
Explanation: 342 + 465 = 807.
*/
public class _0002 {
	static Solution2 s = new Solution2();
	public static void main(String[] args) {
		int[] arr1 = {1};
		int[] arr2 = {9, 9};
		ListNode l1 = Solution2.transfer(arr1);
		ListNode l2 = Solution2.transfer(arr2);
		Solution2.print(l1);
		Solution2.print(l2);
		Solution2.print(s.addTwoNumbers(l1, l2));
	}
}

class Solution2 {
	public static void print(ListNode list){
		ListNode node = list;
		while (node != null){
			System.out.print(node.val + ", ");
			node = node.next;
		}
		System.out.println();
	}
	
	public static ListNode transfer(int[] arr){
		if (arr == null)
			return null;
		
		ListNode head = new ListNode(0);
		ListNode pointer = head;
		ListNode next;
		for (int i : arr){
			next = new ListNode(i);
			head.next = next;
			head = next;
		}
		return pointer.next;
	}
	
	public ListNode addEach(ListNode l1, ListNode l2, int carry) {
		if (l1 == null && l2 == null && carry == 0){
			return null;
		}
		int v1 = l1 == null ? 0 : l1.val, 
	        v2 = l2 == null ? 0 : l2.val;
		int thisTurnCarry = (v1 + v2 + carry) / 10;
		ListNode node = new ListNode((v1 + v2 + carry) % 10);
		node.next = (addEach(l1 == null ? null : l1.next, l2 == null ? null : l2.next, thisTurnCarry));
		return node;
    } 
	
	
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    	return addEach(l1, l2, 0);
    }
}

class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
	}
}
