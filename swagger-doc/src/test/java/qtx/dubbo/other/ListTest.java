package qtx.dubbo.other;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author qtx
 * @since 2023/7/24
 */
public class ListTest {

    private static final String TEST = "2";

    @Test
    public void test1(){
        List<String> strings = initList();
        strings.removeIf(TEST::equals);
        System.out.println(strings);
    }

    /**
     * 错误示范
     */
    @Test
    public void test2(){
        List<String> strings = initList();
        for (String s : strings) {
            if (TEST.equals(s)){
                strings.remove(s);
            }
        }
        System.out.println(strings);
    }

    private List<String> initList(){
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        return list;
    }
}
