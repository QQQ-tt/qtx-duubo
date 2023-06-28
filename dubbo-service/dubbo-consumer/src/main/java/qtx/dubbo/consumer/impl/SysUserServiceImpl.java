package qtx.dubbo.consumer.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import qtx.dubbo.consumer.mapper.SysUserMapper;
import qtx.dubbo.model.entity.consumer.SysUser;
import qtx.dubbo.model.entity.provider.AcBusiness;
import qtx.dubbo.service.consumer.SysUserService;
import qtx.dubbo.service.provider.AcBusinessService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2023-04-05
 */
@DubboService
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @DubboReference(version = "1.0.0")
    private AcBusinessService acBusinessService;

    @Override
    public List<AcBusiness> listAc() {
        return acBusinessService.list();
    }

    @Override
    public String test() {
//    return acBusinessService.test();
        HashMap<String, Object> map = new HashMap<>();
        map.put("hhh", "哈哈哈");
        acBusinessService.test(map);
        return null;
    }
}
