package com.itheima.ssm.service;

import com.itheima.ssm.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-service.xml")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findAll() {

        List<Product> products = productService.findAll ();

        System.out.println (products);
    }
}