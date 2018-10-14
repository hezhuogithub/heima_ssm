package com.itheima.ssm.controller;


import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView ();
        List<UserInfo> userList = userService.findAll();
        mv.addObject ("userList",userList);
        mv.setViewName ("user-list");
        return mv;
    }

    /**
     * 用户添加
     * @param userInfo
     * @return
     */
    @RequestMapping("/save.do")
    public String save(UserInfo userInfo){

        userService.save(userInfo);

        return "redirect:findAll.do";

    }


    @RequestMapping("/findById.do")
    public ModelAndView findById(String id){
        ModelAndView mv = new ModelAndView ();
        UserInfo userInfo = userService.findById(id);
        mv.addObject ("user",userInfo);
        System.out.println (userInfo);
        mv.setViewName ("user-show");
        return mv;

    }
}
