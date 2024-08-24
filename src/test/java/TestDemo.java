import java.util.Arrays;

public class TestDemo {
    public static void main(String[] args) {
        int[] nums = {2, 2, 4, 5, 1, 2};

        int minOperations = minOperationsToMakeGood(nums);
        System.out.println("最少操作次数：" + minOperations);

        int[] nums1 = bubbleSort(nums);
        System.out.println(Arrays.toString(nums1));
    }

    public static int minOperationsToMakeGood(int[] nums) {
        Arrays.sort(nums); // 对数组进行排序

        int n = nums.length;
        int minNum = nums[0];
        int maxNum = nums[n - 1];

        int minOperations = Integer.MAX_VALUE;

        for (int i = minNum; i <= maxNum; i++) {
            int operations = 0;
            for (int j = 0; j < n; j++) {
                operations += Math.abs(nums[j] - i); // 计算每个元素与目标值的差的绝对值之和
            }

            minOperations = Math.min(minOperations, operations);
        }

        return minOperations;
    }

    public static int[] bubbleSort(int[] arr) {
        // int[] nums = {2, 2, 4, 5, 1, 2};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j+1]) {
                    // System.out.println(arr[j]);
                    int temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }
}