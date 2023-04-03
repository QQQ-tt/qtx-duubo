package qtx.dubbo.service.consumer;
/**
 * @author qtx
 * @since 2023/4/3 22:06
 */
public interface StudentService {

  /**
   * 获取学生
   * @return 字符串
   */
  String getStudent();

  /**
   * 消费用户
   * @return 字符串
   */
  String getUserConsumer();
}
