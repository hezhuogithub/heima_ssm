package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/role")
@Controller
public class RoleController {


    @Autowired
    private RoleService roleService;

    @RequestMapping("/findAll")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView ();
       List<Role> roleList =  roleService.findAll();
       mv.addObject ("roleList",roleList);
       mv.setViewName ("role-list");
       return mv;
    }

    @RequestMapping("/save.do")
    public String save(Role role){
        roleService.save(role);
        return "redirect:findAll.do";
    }

    /**
     * 角色详情查询
     * @return
     */
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id",required = true)String roleId){
        ModelAndView mv = new ModelAndView ();
        Role role = roleService.findById(roleId);
        mv.addObject ("role",role);
        mv.setViewName ("role-show");
        return mv;
    }

    /**
     * 通过id删除角色信息 包含role表 users_role表 和 role_permission表中的信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteRole.do")
    public String deleteRole(String id){
        roleService.deleteById(id);
        return "redirect:findAll.do";
    }

    /**
     * 根据id查询角色信息和可添加的权限信息
     * 就是查询所有未被添加的权限信息
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping("/findRoleByIdAndAllPermission.do")
    public String findRoleByIdAndAllPermission(@RequestParam(name = "id",required = true) String roleId, Model model){
        //1.根据id查询所有角色信息
        Role role = roleService.findById (roleId);
        //2.根据id查询所有角色未被添加的权限信息
        List<Permission> otherPermissions = roleService.findOtherPermissions(roleId);
        model.addAttribute ("role",role);
        model.addAttribute ("permissionList",otherPermissions);
        return "role-permission-add";
    }

    /**
     * 给角色添加权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(name = "roleId",required = true)String roleId,@RequestParam(name = "ids",required = true) String[] permissionIds){
        roleService.addPermissionToRole(roleId,permissionIds);
        return "redirect:findAll.do";
    }

}
