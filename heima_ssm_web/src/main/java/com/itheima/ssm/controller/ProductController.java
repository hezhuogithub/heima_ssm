package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Product;
import com.itheima.ssm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 查询全部产品
     * @return
     */
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView ();
        List<Product> ps = productService.findAll ();
        mv.addObject ("productList",ps);
        mv.setViewName ("product-list1");
        return mv;
    }

    /**
     * 产品添加
     * @param product
     * @return
     */
    @RequestMapping("/save.do")
    public String sava(Product product){
        productService.save(product);
        return "redirect:findAll.do";
    }



}
