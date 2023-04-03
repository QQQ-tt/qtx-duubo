package qtx.dubbo.provider.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import qtx.dubbo.service.provider.UserService;
/**
 * @author qtx
 * @since 2023/4/3 22:01
 */
@Service
@DubboService
public class UserServiceImpl implements UserService {
  @Override
  public String getUser() {
    return "provider";
  }
}
