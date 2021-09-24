package com.wez.study.single.lock.ctrl;

import com.wez.study.single.lock.svc.ProductService;
import com.wez.study.single.lock.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ProductController
 *
 * @Author wez
 * @Time 2021/9/24 12:05
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/find_all")
    public String findAll() {
        List<ProductVO> all = productService.findAll();
        System.out.println("Controller: " + all);
        return "findAll";
    }

}
