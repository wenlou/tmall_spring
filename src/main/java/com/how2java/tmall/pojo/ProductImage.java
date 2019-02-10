package com.how2java.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "productimage")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })

public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name="pid")
    @JsonBackReference
    private Product product;

    private String type;

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", product=" + product +
                ", type='" + type + '\'' +
                '}';
    }
}