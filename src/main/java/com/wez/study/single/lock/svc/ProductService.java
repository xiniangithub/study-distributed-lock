package com.wez.study.single.lock.svc;

import com.wez.study.single.lock.vo.ProductVO;

import java.util.List;

/**
 * ProductService
 *
 * @Author wez
 * @Time 2021/9/24 12:00
 */
public interface ProductService {

    List<ProductVO> findAll();

}
