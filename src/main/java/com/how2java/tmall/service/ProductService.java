package com.how2java.tmall.service;

import com.how2java.tmall.dao.ProductDAO;
import com.how2java.tmall.dao.PropertyDAO;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductDAO productDAO;
    @Autowired CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;
    @Autowired OrderIteamService orderItemService;
    @Autowired ReviewService reviewService;

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
    public void fill(List<Category> categorys) {
        for (Category category : categorys) {
            fill(category);
        }
    }
    public void fill(Category category) {
        List<Product> products = listByCategory(category);
        productImageService.setFirstProdutImages(products);
        category.setProducts(products);
    }

    public void fillByRow(List<Category> categorys) {
        int productNumberEachRow = 8;
        for (Category category : categorys) {
            List<Product> products =  category.getProducts();
            List<List<Product>> productsByRow =  new ArrayList<>();
            for (int i = 0; i < products.size(); i+=productNumberEachRow) {
                int size = i+productNumberEachRow;
                size= size>products.size()?products.size():size;
                List<Product> productsOfEachRow =products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    public List<Product> listByCategory(Category category){
        return productDAO.findByCategoryOrderById(category);
    }
    public void setSaleAndReviewNumber(Product product) {
        int saleCount = orderItemService.getSaleCount(product);
        product.setSaleCount(saleCount);

        int reviewCount = reviewService.getCount(product);
        product.setReviewCount(reviewCount);

    }

    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products)
            setSaleAndReviewNumber(product);
    }
}