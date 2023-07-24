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
        int i = quick_find(ints, 0, ints.length - 1, 2);
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
        if (j - i + 1 >= k) {
            // 排左面
            return quick_find(q, l, j, k);
        } else {
            // 排右面
            return quick_find(q, j + 1, r, k);
        }
    }


    private void swap(int[] q, int i, int j) {
        int temp = q[i];
        q[i] = q[j];
        q[j] = temp;
    }
}
