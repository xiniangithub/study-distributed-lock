package com.wez.study.single.lock.svc;

import com.wez.study.single.lock.dal.ProductMapper;
import com.wez.study.single.lock.po.Product;
import com.wez.study.single.lock.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProductServiceImpl
 *
 * @Author wez
 * @Time 2021/9/24 12:01
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductVO> findAll() {
        List<Product> all = productMapper.findAll();
        System.out.println("Service: " + all);
        return null;
    }
}
