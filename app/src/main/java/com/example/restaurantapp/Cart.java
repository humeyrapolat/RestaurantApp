package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class Cart extends AppCompatActivity {


    private String cartTitle, cartQuantity, cartPrice,cartImage;


    public Cart() {
    }

    public Cart(String cartTitle, String cartQuantity, String cartPrice, String cartImage) {
        this.cartTitle = cartTitle;
        this.cartQuantity = cartQuantity;
        this.cartPrice = cartPrice;
        this.cartImage = cartImage;
    }

    public String getCartImage() {
        return cartImage;
    }

    public void setCartImage(String cartImage) {
        this.cartImage = cartImage;
    }

    public String getCartTitle() {
        return cartTitle;
    }

    public void setCartTitle(String cartTitle) {
        this.cartTitle = cartTitle;
    }

    public String getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(String cartPrice) {
        this.cartPrice = cartPrice;
    }
}