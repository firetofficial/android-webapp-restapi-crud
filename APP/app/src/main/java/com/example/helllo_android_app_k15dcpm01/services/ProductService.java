package com.example.helllo_android_app_k15dcpm01.services;

import com.example.helllo_android_app_k15dcpm01.entities.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductService {
    @POST("products")
    Call<Product> addProduct(@Body Product product);

    @GET("products")
    Call<List<Product>> getAllProducts();

    @PUT("products/{id}")
    Call<Product> editProduct(@Path("id") int id, @Body Product product);

    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Path("id") int id);
}
