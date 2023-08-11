package qtx.dubbo.algorithm;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Math.random()*(n-m)+m，生成大于等于m小于n的随机数
 *
 * @author qtx
 * @since 2023/7/21
 */
public class SortTest {


    @Test
    public void test1() {
        int[] ints = new int[100];
        for (int i = 0; i < ints.length; i++) {
            int random = (int) (Math.random() * (100));
            ints[i] = random;
        }
        System.out.println(Arrays.toString(ints));
        long x = System.currentTimeMillis();
        quick_sort(ints, 0, ints.length - 1);
        long l = System.currentTimeMillis();
        System.out.println(l - x);
        System.out.println(Arrays.toString(ints));
    }

    /**
     * 快速排序
     */
    void quick_sort(int[] q, int l, int r) {
        if (l >= r) {
            return;
        }
        int x = q[l + r >> 1], i = l - 1, j = r + 1;
        while (i < j) {
            do {
                i++;
            } while (q[i] < x);
            do {
                j--;
            } while (q[j] > x);
            if (i < j) {
                swap(q, i, j);
            }
        }

        // 排左面
        quick_sort(q, l, j);
        // 排右面
        quick_sort(q, j + 1, r);
    }

    @Test
    public void test2() {
        int[] ints = new int[3];
        for (int i = 0; i < ints.length; i++) {
            int random = (int) (Math.random() * (100));
            ints[i] = random;
        }
        System.out.println(Arrays.toString(ints));
        long x = System.currentTimeMillis();
        int i = quick_find(ints, 0, ints.length - 1, 1);
        long l = System.currentTimeMillis();
        System.out.println(l - x);
        System.out.println(i);
    }

    /**
     * 快速选择
     *
     * @param q 原始数组
     * @param l 左
     * @param r 右
     * @param k 选择的位置
     * @return 结果
     */
    int quick_find(int[] q, int l, int r, int k) {
        if (l >= r) {
            return q[l];
        }
        int x = q[l + r >> 1], i = l - 1, j = r + 1;
        while (i < j) {
            while (q[++i] < x) ;
            while (q[--j] > x) ;
            if (i < j) {
                swap(q, i, j);
            }
        }
        if (j - l + 1 >= k) {
            // 排左面
            return quick_find(q, l, j, k);
        } else {
            // 排右面
            return quick_find(q, j + 1, r, k - (j - l + 1));
        }
    }

    @Test
    public void test3() {
        int[] arr = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        System.out.println(findKthLargest(arr, 4));
    }

    /**
     * @param nums 数组
     * @param k    第k大的数
     * @return 结果
     */
    int findKthLargest(int[] nums, int k) {
        int[] array = Arrays.stream(nums)
                .distinct()
                .toArray();
        return quick_find(nums, 0, nums.length - 1, nums.length - k + 1);
    }

    int[] tmp;

    void merge_sort(int[] q, int l, int r) {
        if (l >= r) {
            return;
        }

        int mid = (l + r) >> 1;

        merge_sort(q, l, mid);
        merge_sort(q, mid + 1, r);

        int k = 0, i = l, j = mid + 1;

        while (i <= mid && j <= r) {
            if (q[i] <= q[j]) tmp[k++] = q[i++];
            else tmp[k++] = q[j++];
        }
        while (i <= mid) tmp[k++] = q[i++];
        while (j <= r) tmp[k++] = q[j++];

        for (int m = l, n = 0; m < tmp.length; m++, n++) {
            q[m] = tmp[n];
        }
    }


    private void swap(int[] q, int i, int j) {
        int temp = q[i];
        q[i] = q[j];
        q[j] = temp;
    }
}
