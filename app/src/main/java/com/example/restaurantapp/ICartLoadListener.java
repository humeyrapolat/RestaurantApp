package com.example.restaurantapp;

import java.util.List;

public interface ICartLoadListener {
    void onCartLoadSuccess(List<Cart> cartList);
    void onCartLoadFailed(String message);

}
