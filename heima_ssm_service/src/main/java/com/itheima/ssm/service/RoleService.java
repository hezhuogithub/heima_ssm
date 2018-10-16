package com.itheima.ssm.service;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RoleService {


    List<Role> findAll();

    void save(Role role);

    Role findById(String roleId);

    void deleteById(String id);

    List<Permission> findOtherPermissions(String roleId);

    void addPermissionToRole(String roleId, String[] permissionIds);
}
