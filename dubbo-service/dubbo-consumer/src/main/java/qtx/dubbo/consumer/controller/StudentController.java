package qtx.dubbo.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qtx.dubbo.service.consumer.StudentService;

/**
 * @author qtx
 * @since 2023/4/3 21:57
 */
@RestController
@RequestMapping("student")
public class StudentController {

  private final StudentService service;

  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/getUser")
  public String getUser() {
    return service.getUserConsumer();
  }
}
