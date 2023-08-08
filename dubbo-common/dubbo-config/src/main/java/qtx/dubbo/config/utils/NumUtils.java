package qtx.dubbo.config.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author qtx
 * @since 2022/8/31
 */
public class NumUtils {
    /**
     * 生成size个start到end之间的整数
     *
     * @param size  长度
     * @param start 区间开始数字
     * @param end   区间结束数字
     * @return 数字字符串
     */
    public static long numRandom(int size, int start, int end) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(random.nextInt(end - start) + start);
        }
        return Long.parseLong(stringBuilder.toString());
    }

    /**
     * 随机生成用户card
     *
     * @return 数字字符串
     */
    public static long numUserCard() {
        return numRandom(1, 1, 9) + numRandom(4, 0, 9);
    }

    public static String uuid() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .toLowerCase();
    }
}
