package com.itheima.ssm.service.impl;

import com.itheima.ssm.dao.UserDao;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * getAuthority()需要传一个权限对象
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo = userDao.findByUsername (username);
        //处理自己的用户对象封装成UserDetails
//        User user = new User (userInfo.getUsername (),"{noop}"+userInfo.getPassword (),getAuthority());
        User user = new User (userInfo.getUsername (),userInfo.getPassword (),userInfo.getStatus ()==0?false:true,true,true,true,getAuthority (userInfo.getRoles ()));
        return user;
    }

    /**
     * 作用就是返回一个List集合，集合中装入的是角色描述
     * @return
     * @param roles
     */
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles){

        List<SimpleGrantedAuthority> list = new ArrayList<> ();
        for (Role role : roles) {
            list.add (new SimpleGrantedAuthority ("ROLE_"+role.getRoleName ()));
        }
        return list;

    }

    @Override
    public List<UserInfo> findAll() {
        return userDao.findAll();
    }

    @Override
    public void save(UserInfo userInfo) {
        //对密码进行加密处理
        userInfo.setPassword (bCryptPasswordEncoder.encode (userInfo.getPassword ()));
        userDao.save(userInfo);

    }

    @Override
    public UserInfo findById(String id) {
        return userDao.findById(id);
    }
}
