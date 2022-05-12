package com.example.GroceryStore.entity;

import org.hibernate.criterion.Example;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.Constraint;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Embeddable
public class ProductDetails {


    @Column(name = "product_weight", table = "product_details", nullable = false)
    @NotNull(message = "weight cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false,message = "weight cannot be 0.0")
    BigDecimal productWeight;


    @Column(name = "product_price", table = "product_details", nullable = false)
    @NotNull(message = "price cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false,message = "price cannot be 0.0")
//    @Digits(integer=8, fraction=3, message = "{javax.validation.constraints.Digits.message}")
    BigDecimal productPrice;


    public BigDecimal getProductQRCode() {
        return productQRCode;
    }

    public void setProductQRCode(BigDecimal productQRCode) {
        this.productQRCode = productQRCode;
    }

    @Column(name = "product_QRCode", table = "product_details", nullable = false)
    @NotNull(message = "QR code cannot be empty")
    BigDecimal productQRCode;


    public BigDecimal getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(BigDecimal productWeight) {
        this.productWeight = productWeight;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }


}
