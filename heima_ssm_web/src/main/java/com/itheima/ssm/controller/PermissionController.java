package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/permission")
@Controller
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView ();
        List<Permission> permissionList = permissionService.findAll();
        System.out.println (permissionList);
        mv.addObject ("permissionList",permissionList);
        mv.setViewName ("permission-list");
        return mv;
    }


    @RequestMapping("/save.do")
    public String save(Permission permission){
        permissionService.save(permission);
        return "redirect:findAll.do";
    }

    /**
     * 资源权限详情
     * @param permissionId
     * @param model
     * @return
     */
   @RequestMapping("/findById.do")
    public String findById(@RequestParam(name = "id",required = true) String permissionId, Model model){
        Permission permission =  permissionService.findById(permissionId);
        model.addAttribute ("permission",permission);
        return "permission-show";
   }

    /**
     * 根据id删除资源权限 包含表permission 和 role_permission
     * @param id
     * @return
     */
   @RequestMapping("/deletePermission.do")
    public String deletePermission(String id){
       permissionService.deleteById(id);
       return "redirect:findAll.do";
   }
}
