package qtx.dubbo.provider.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.exception.DataException;
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
@Service
@DubboService(version = "1.0.0")
public class AcBusinessServiceImpl extends ServiceImpl<AcBusinessMapper, AcBusiness>
        implements AcBusinessService {


    @Override
    public Map<String, Object> test(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        AcBusiness acBusiness = new AcBusiness();
        acBusiness.setId(1);
        HashMap<String, Object> ret = new HashMap<>();
        HashMap<String, Object> body = new HashMap<>();
        body.put("msg","success");
        body.put("code","200");
        body.put("data","123");
        ret.put("body", body); // http response body
        ret.put("status", "200"); // http response status
        ret.put("test", "123"); // http response header
        //return ResultMap.success(acBusiness);
        System.out.println(ret);
        return ret;
    }

    @Override
    public AcBusiness test1(boolean flag) {
        if (flag){
            new DataException(DataEnums.FAILED);
        }
        return AcBusiness.builder().businessInfo("哈哈哈").id(1).acNameId(2).build();
    }
}
