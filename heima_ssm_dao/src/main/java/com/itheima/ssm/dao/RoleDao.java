package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleDao {

    /**
     * 根据用户id查询出所有对应的角色
     * @param userId
     * @return
     * @Select ("select * from role where id in(select roleId from users_role where userId=#{userId})")
     */
    @Select ("select * from ROLE r inner join USERS_ROLE UR ON r.ID = UR.ROLEID WHERE USERID=#{USERID}")
    public List<Role> findRoleByUserId(String userId);
}
