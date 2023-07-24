package trails;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * 空指针测试
 * @author qiuyiliang
 */
public class NpeTest {
    @Test
    public void testOne(){
        Student student = new Student();

        student.setAddress(Arrays.asList(new Address("望京"),null));
        List<Address> addresses = Optional.of(student)
                .map(Student::getAddress)
                .orElse(Collections.emptyList());
        for (Address address : addresses) {
            if (address == null){
                continue;
            }
            System.out.println(address.getStreet());
        }
    }
    @Data
    class Student{
        private List<Address> address;
    }

    @AllArgsConstructor
    @Data
    class Address{
        private String street;
    }
}
