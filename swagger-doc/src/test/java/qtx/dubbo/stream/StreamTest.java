package qtx.dubbo.stream;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qtx
 * @since 2024/11/11 13:17
 */
public class StreamTest {

    private List<EntityTest> getList() {
        List<EntityTest> list = new ArrayList<>();
        list.add(EntityTest.builder().id(1).name("name1").build());
        list.add(EntityTest.builder().id(2).name("name2").build());
        list.add(EntityTest.builder().id(3).name("name3").build());
        list.add(EntityTest.builder().id(4).name("name4.1").build());
        list.add(EntityTest.builder().id(4).name("name4.2").build());
        list.add(EntityTest.builder().id(4).name("name4.3").build());
        return list;
    }

    @Test
    public void test() {
        List<EntityTest> list = getList();
        boolean b = list.stream()
                .allMatch(a -> a.getId() > 4);
        System.out.println(b);
    }
}

@Getter
@Builder
class EntityTest {

    private int id;
    private String name;
}
