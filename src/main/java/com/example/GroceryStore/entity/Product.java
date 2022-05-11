package com.example.GroceryStore.entity;


import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_weight")
    private long productWeight;

    @Column(name = "product_price")
    private float productPrice;

    @Column(name = "product_QR")
    private long productQRCode;


    public Product() {
    }

    public Product(String productName, long productWeight, float productPrice, long productQR) {
        super();
        this.productName = productName;
        this.productWeight = productWeight;
        this.productPrice = productPrice;
        this.productQRCode = productQR;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(long productWeight) {
        this.productWeight = productWeight;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public long getProductQRCode() {
        return productQRCode;
    }

    public void setProductQRCode(long productQRCode) {
        this.productQRCode = productQRCode;
    }

}
