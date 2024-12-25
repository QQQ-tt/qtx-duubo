package qtx.dubbo.service.provider;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import qtx.dubbo.model.dto.provider.SysUserDTO;
import qtx.dubbo.model.entity.provider.SysUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author qtx
 * @since 2024-12-25
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 分页查询用户
     *
     * @param dto 查询条件
     * @return 分页集合
     */
    Page<SysUser> pageSysUser(SysUserDTO dto);

    /**
     * 跟新或添加用户
     *
     * @param dto 用户信息
     * @return true 成功  false 失败
     */
    boolean saveOrUpdateSysUser(SysUser dto);

    /**
     * 获取用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    SysUser getSysUserById(Integer id);

    /**
     * 创建用户
     *
     * @param dto 用户信息
     * @return true 成功  false 失败
     */
    long createSysUser(SysUser dto);

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return true 成功  false 失败
     */
    boolean removeSysUser(int id);

}
