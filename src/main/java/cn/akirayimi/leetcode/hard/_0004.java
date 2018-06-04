package cn.akirayimi.leetcode.hard;

/*There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

Example 1:

nums1 = [1, 3]
nums2 = [2]

The median is 2.0

Example 2:

nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5
*/
public class _0004 {
	public static void main(String[] args) {
		int[] arr1 = {1, 2};
		int[] arr2 = {3, 4};
		Solution4 s4 = new Solution4();
		System.out.println(s4.findMedianSortedArrays(arr1, arr2));
		;
	}
}

class Solution4 {
	
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		Number
		return solution1(nums1, nums2);
	}
	
	/**
	 * 
	 * @param nums1
	 * @param nums2
	 * @return
	 */
	public double solution2(int[] nums1, int[] nums2){
		return 0;
	}
	
	
	
	/**
	 * merge and find, O(m+n)
	 * @param nums1
	 * @param nums2
	 * @return
	 */
	public double solution1(int[] nums1, int[] nums2) {
		int merge[] = merge(nums1, nums2);
		int size = merge.length;
		if (size % 2 == 0)
			return (merge[size / 2 - 1] + merge[size / 2]) / 2.0;
		return 1.0 * merge[size / 2];
	}

	public void print(int[] arr) {
		for (int i : arr)
			System.out.print(i + ", ");
	}

	public int[] merge(int[] sortArr1, int[] sortArr2) {
		if (sortArr2 == null && sortArr1 == null)
			return null;
		if (sortArr1 == null)
			return sortArr2;
		if (sortArr2 == null)
			return sortArr1;
		int totalCount = sortArr1.length + sortArr2.length;
		int index1 = 0, index2 = 0;
		int[] ret = new int[totalCount];
		for (int i = 0; i < totalCount; i++) {
			if (index1 == sortArr1.length) {
				ret[i] = sortArr2[index2++];
				continue;
			} else if (index2 == sortArr2.length) {
				ret[i] = sortArr1[index1++];
				continue;
			}

			if (sortArr1[index1] < sortArr2[index2]) {
				ret[i] = sortArr1[index1];
				index1++;
			} else {
				ret[i] = sortArr2[index2];
				index2++;
			}

		}
		return ret;
	}
}