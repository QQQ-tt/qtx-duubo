package qtx.dubbo.provider.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import qtx.dubbo.model.entity.provider.AcBusiness;
import qtx.dubbo.provider.mapper.AcBusinessMapper;
import qtx.dubbo.service.provider.AcBusinessService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 流程节点业务表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2023-03-30
 */
@DubboService(version = "1.0.0")
public class AcBusinessServiceImpl extends ServiceImpl<AcBusinessMapper, AcBusiness>
        implements AcBusinessService {


    @Override
    public Map<String, Object> test(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        Map<String, Object> ret = new HashMap<>();
        ret.put("body", "dubbo success"); // http response body
        ret.put("status", "200"); // http response status
        ret.put("test", "123"); // http response header
        return ret;
    }
}
