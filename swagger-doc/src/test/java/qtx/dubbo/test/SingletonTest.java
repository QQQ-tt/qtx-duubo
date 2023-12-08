package qtx.dubbo.test;

import lombok.Data;
import org.junit.jupiter.api.Test;
import qtx.dubbo.test.entity.UserTest;

/**
 * @author qtx
 * @since 2023/11/13
 */
public class SingletonTest {

     private static volatile UserTest instance;

     public UserTest getInstance(){
         if (instance == null) {
             synchronized (SingletonTest.class) {
                 if (instance == null) {
                     instance = new UserTest();
                 }
             }
         }
         return instance;
     }

     @Test
     public void test(){
         UserTest instance1 = getInstance();
         instance1.setName("name");
         System.out.println(instance1);
         UserTest instance2 = getInstance();
         instance2.setAddress("address");
         System.out.println(instance2);
     }
}

