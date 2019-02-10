package com.how2java.tmall.service;

import com.how2java.tmall.dao.ProductDAO;
import com.how2java.tmall.dao.PropertyDAO;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductDAO productDAO;
    @Autowired CategoryService categoryService;

    public Page4Navigator<Product> list(int cid, int start, int size, int navigatePages) {
        Category category=categoryService.get(cid);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =productDAO.findByCategory(category,pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }
    public List<Product> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return productDAO.findAll(sort);
    }

    public void add(Product bean) {
        productDAO.save(bean);
    }

    public void delete(int id) {
        productDAO.delete(id);
    }

    public Product get(int id) {
        Product p= productDAO.findOne(id);
        return p;
    }
    public void update(Product product) {
        productDAO.save(product);
    }
}