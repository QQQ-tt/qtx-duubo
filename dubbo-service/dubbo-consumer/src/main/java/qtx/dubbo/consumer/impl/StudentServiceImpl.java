package qtx.dubbo.consumer.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import qtx.dubbo.service.consumer.StudentService;
import qtx.dubbo.service.provider.UserService;

/**
 * @author qtx
 * @since 2023/4/3 22:01
 */
@Service
public class StudentServiceImpl implements StudentService {

  @DubboReference private UserService userService;

  @Override
  public String getStudent() {
    return null;
  }

  @Override
  public String getUserConsumer() {
    return userService.getUser();
  }
}
