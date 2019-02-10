package com.how2java.tmall.service;

import com.how2java.tmall.dao.ProductDAO;
import com.how2java.tmall.dao.ProductImageDAO;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
    @Autowired
    ProductImageDAO productImageDAO;
    public static final String type_single = "single";
    public static final String type_detail = "detail";
    @Autowired ProductService productService;

    public void add(ProductImage bean) {
        productImageDAO.save(bean);
    }

    public void delete(int id) {
        productImageDAO.delete(id);
    }

    public ProductImage get(int id) {
        ProductImage p= productImageDAO.findOne(id);
        return p;
    }
    public  List<ProductImage> listSingleProductImages(Product product){
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product,type_single);
    }
    public  List<ProductImage> listDetailProductImages(Product product){
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product,type_detail);
    }
    public void setFirstProdutImage(Product product) {
        List<ProductImage> singleImages = listSingleProductImages(product);
        if(!singleImages.isEmpty()) {
            product.setFirstProductImage(singleImages.get(0));
        }
        else {
            product.setFirstProductImage(new ProductImage());
        }//这样做是考虑到产品还没有来得及设置图片，但是在订单后台管理里查看订单项的对应产品图片。

    }
    public void setFirstProdutImages(List<Product> products) {
        for (Product product : products) {
            setFirstProdutImage(product);
        }
    }
}