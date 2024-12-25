package qtx.dubbo.provider.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import qtx.dubbo.config.utils.NumUtils;
import qtx.dubbo.java.enums.DataEnums;
import qtx.dubbo.java.exception.DataException;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.model.dto.provider.SysUserDTO;
import qtx.dubbo.model.entity.provider.SysUser;
import qtx.dubbo.provider.mapper.SysUserMapper;
import qtx.dubbo.redis.RedisUtils;
import qtx.dubbo.service.provider.SysUserService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author qtx
 * @since 2024-12-25
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final PasswordEncoder passwordEncoder;

    public SysUserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<SysUser> pageSysUser(SysUserDTO dto) {
        return page(dto.getPage(),
                Wrappers.lambdaQuery(SysUser.class)
                        .eq(StringUtils.isNotBlank(dto.getUserName()), SysUser::getUserName, dto.getUserName())
                        .eq(StringUtils.isNotBlank(dto.getUserCard()), SysUser::getUserCard, dto.getUserCard()));
    }

    @Override
    public boolean saveOrUpdateSysUser(SysUser dto) {
        long count = count(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserCard, dto.getUserCard())
                .ne(dto.getId() != null, SysUser::getId, dto.getId()));
        if (count > 0) {
            new DataException(DataEnums.DATA_IS_ABNORMAL);
        }
        if (StringUtils.isNotBlank(dto.getPassword())) {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return saveOrUpdate(dto);
    }

    @Override
    public SysUser getSysUserById(Integer id) {
        return getById(id);
    }

    @Override
    public long createSysUser(SysUser dto) {
        if (StringUtils.isBlank(dto.getPassword())) {
            new DataException(DataEnums.DATA_IS_ABNORMAL);
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        long l;
        do {
            l = NumUtils.numUserCard();
        } while (count(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserCard, l)) > 0);
        dto.setUserCard(String.valueOf(l));
        dto.setStatus(Boolean.TRUE);
        Map<String, String> map = Map.of("userCode", dto.getUserCard(), "password", dto.getPassword());
        RedisUtils.hPutAll(StaticConstant.SYS_USER + dto.getUserCard() + StaticConstant.REDIS_INFO, map);
        save(dto);
        return l;
    }

    @Override
    public boolean removeSysUser(int id) {
        return removeById(id);
    }

    @PostConstruct
    private void initUser() {
        List<SysUser> list = list();
        list.forEach(item -> {
            Map<String, String> map = Map.of("userCode", item.getUserCard(), "password", item.getPassword());
            RedisUtils.hPutAll(StaticConstant.SYS_USER + item.getUserCard() + StaticConstant.REDIS_INFO, map);
        });
    }
}
