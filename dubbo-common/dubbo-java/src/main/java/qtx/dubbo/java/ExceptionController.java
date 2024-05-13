package qtx.dubbo.java;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.exception.DataException;

/**
 * @author qtx
 * @since 2024/5/13 10:10
 */
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @RequestMapping("/filterException")
    public void test(HttpServletRequest request) {
        if (request.getAttribute("filterException") instanceof DataEnums) {
            new DataException((DataEnums) request.getAttribute("filterException"));
        } else {
            new DataException(DataEnums.FAILED);
        }
    }
}
