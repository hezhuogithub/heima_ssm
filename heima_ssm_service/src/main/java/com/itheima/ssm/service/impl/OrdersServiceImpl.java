package com.itheima.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.ssm.dao.OrdersDao;
import com.itheima.ssm.domain.Orders;
import com.itheima.ssm.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersDao ordersDao;


    @Override
    public List<Orders> findAll(int page,int size) {

        //参数pageNum 是页码值  参数pageSize  代表是每页显示条数
        PageHelper.startPage (page,size);
        return ordersDao.findAll ();
    }
}