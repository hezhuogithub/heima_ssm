package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoleDao {

    /**
     * 根据用户id查询出所有对应的角色
     * @param userId
     * @return
     * @Select ("select * from role where id in(select roleId from users_role where userId=#{userId})")
     */
    @Select ("select * from role r inner join users_role ur on r.id = ur.roleId where userId=#{userId}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many = @Many(select = "com.itheima.ssm.dao.PermissionDao.findPermissionByRoleId"))
    })
    public List<Role> findRoleByUserId(String userId);

    @Select ("select * from role")
    List<Role> findAll();

    @Insert ("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    void save(Role role);
}
