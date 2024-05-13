package qtx.dubbo.hello;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author qtx
 * @since 2024/2/21
 */
public class ArrayTest {

    /**
     * 访问数组
     */
    @Test
    void visitArray() {
        int[] arr = new int[]{1, 2, 3};
        int randomIndex = ThreadLocalRandom.current()
                .nextInt(0, arr.length);
        int randomValue = arr[randomIndex];
        System.out.println(randomValue);
    }

    @Test
    void insertArray() {
        int x = 1;
        int num = 10;
        int[] arr = new int[]{1, 2, 3};
        for (int i = arr.length - 1; i > x; i--){
            arr[i] = arr[i - 1];
        }
        arr[x] = num;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    void remove(){
        int[] arr = new int[]{1, 2, 3, 4, 5};
        int x = 3;
        for (int i = x; i < arr.length - 1; i++){
            arr[i] = arr[i + 1];
        }
        System.out.println(Arrays.toString(arr));

        ArrayList<Object> list = new ArrayList<>();
    }


}
