package qtx.dubbo.test;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author qtx
 * @since 2024/5/20 16:50
 */
public class ForCycles {

    public List<Integer> test1() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        return list;
    }


    @Test
    public void test2() {
        List<Integer> list = test1();
 /*       for(int i=0; i < list.size(); i++){
            if(list.get(i) == 2)
                list.remove(i);
        }*/

        Iterator<Integer> it = list.iterator();
        while(it.hasNext()){
            Integer value = it.next();
            if(value == 2){
                list.remove(value);
            }
        }

/*        for(Integer i:list){
            if(i==3) list.remove(i);
        }*/

        System.out.println(list);
    }
}
