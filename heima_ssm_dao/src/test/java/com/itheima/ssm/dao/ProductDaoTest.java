package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ="classpath:spring/applicationContext-dao.xml" )
public class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void test(){
        Product byId = productDao.findById ("676C5BD1D35E429A8C2E114939C5685A");
        System.out.println (byId);
    }

}
