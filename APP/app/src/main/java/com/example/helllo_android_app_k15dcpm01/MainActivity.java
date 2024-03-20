package com.example.helllo_android_app_k15dcpm01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.helllo_android_app_k15dcpm01.entities.Product;
import com.example.helllo_android_app_k15dcpm01.services.ProductService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // Khai báo các biến
    private ListView listView;
    private Button btnAddProduct, btnCancelAddProduct, btnConfirmAddProduct,
            btnEditProduct, btnCancelEditProduct, btnConfirmEditProduct,
            btnDeleteProduct, btnCancelDeleteProduct, btnConfirmDeleteProduct;
    private View groupAddProduct, groupEditProduct, groupDeleteProduct;
    private EditText etProductName, etProductPrice, etProductImage,
            etEditId, etEditName, etEditPrice, etEditImage,
            etDeleteId;
    private Retrofit retrofit;
    private ProductService productService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các phần tử từ layout
        listView = findViewById(R.id.productListView);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnCancelAddProduct = findViewById(R.id.btnCancelAddProduct);
        btnConfirmAddProduct = findViewById(R.id.btnConfirmAddProduct);
        btnEditProduct = findViewById(R.id.btnEditProduct);
        btnCancelEditProduct = findViewById(R.id.btnCancelEditProduct);
        btnConfirmEditProduct = findViewById(R.id.btnConfirmEditProduct);
        btnDeleteProduct = findViewById(R.id.btnDeleteProduct);
        btnCancelDeleteProduct = findViewById(R.id.btnCancelDeleteProduct);
        btnConfirmDeleteProduct = findViewById(R.id.btnConfirmDeleteProduct);
        groupAddProduct = findViewById(R.id.groupAddProduct);
        groupEditProduct = findViewById(R.id.groupEditProduct);
        groupDeleteProduct = findViewById(R.id.groupDeleteProduct);
        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductImage = findViewById(R.id.etProductImage);
        etEditId = findViewById(R.id.etEditId);
        etEditName = findViewById(R.id.etEditName);
        etEditPrice = findViewById(R.id.etEditPrice);
        etEditImage = findViewById(R.id.etEditImage);
        etDeleteId = findViewById(R.id.etDeleteId);

        // Khởi tạo Retrofit và ProductService
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/hello-web-app/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productService = retrofit.create(ProductService.class);

        // Cài đặt sự kiện cho nút Thêm sản phẩm
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupAddProduct.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                btnCancelAddProduct.setVisibility(View.VISIBLE);
            }
        });

        // Cài đặt sự kiện cho nút Huỷ thêm sản phẩm
        btnCancelAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupAddProduct.setVisibility(View.GONE);
                etProductName.setText("");
                etProductPrice.setText("");
                etProductImage.setText("");
                listView.setVisibility(View.VISIBLE);
            }
        });

        // Cài đặt sự kiện cho nút Xác nhận thêm sản phẩm
        btnConfirmAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etProductName.getText().toString().trim();
                String priceStr = etProductPrice.getText().toString().trim();
                String image = etProductImage.getText().toString().trim();

                if (!name.isEmpty() && !priceStr.isEmpty() && !image.isEmpty()) {
                    double price = Double.parseDouble(priceStr);
                    addProduct(name, price, image);
                }
            }
        });

        // Cài đặt sự kiện cho nút Sửa sản phẩm
        btnEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupEditProduct.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                btnCancelEditProduct.setVisibility(View.VISIBLE);
            }
        });

        // Cài đặt sự kiện cho nút Huỷ sửa sản phẩm
        btnCancelEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupEditProduct.setVisibility(View.GONE);
                etEditId.setText("");
                etEditName.setText("");
                etEditPrice.setText("");
                etEditImage.setText("");
                listView.setVisibility(View.VISIBLE);
            }
        });

        // Cài đặt sự kiện cho nút Xác nhận sửa sản phẩm
        btnConfirmEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStr = etEditId.getText().toString().trim();
                String name = etEditName.getText().toString().trim();
                String priceStr = etEditPrice.getText().toString().trim();
                String image = etEditImage.getText().toString().trim();

                if (!idStr.isEmpty() && !name.isEmpty() && !priceStr.isEmpty() && !image.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    double price = Double.parseDouble(priceStr);
                    editProduct(id, name, price, image);
                }
            }
        });

        // Cài đặt sự kiện cho nút Xoá sản phẩm
        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupDeleteProduct.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                btnCancelDeleteProduct.setVisibility(View.VISIBLE);
            }
        });

        // Cài đặt sự kiện cho nút Huỷ xoá sản phẩm
        btnCancelDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupDeleteProduct.setVisibility(View.GONE);
                etDeleteId.setText("");
                listView.setVisibility(View.VISIBLE);
            }
        });

        // Cài đặt sự kiện cho nút Xác nhận xoá sản phẩm
        btnConfirmDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStr = etDeleteId.getText().toString().trim();
                if (!idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    deleteProduct(id);
                }
            }

        });
        loadProductList();
    }

    // Hàm thêm sản phẩm mới
    private void addProduct(String name, double price, String image) {
        Product product = new Product(0, name, price, image);
        Call<Product> call = productService.addProduct(product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    groupAddProduct.setVisibility(View.GONE);
                    etProductName.setText("");
                    etProductPrice.setText("");
                    etProductImage.setText("");
                    loadProductList();

                } else {
                    loadProductList();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                loadProductList();
            }
        });
    }

    // Hàm sửa thông tin sản phẩm
    private void editProduct(int id, String name, double price, String image) {
        Product product = new Product(id, name, price, image);
        Call<Product> call = productService.editProduct(id, product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    groupEditProduct.setVisibility(View.GONE);
                    etEditId.setText("");
                    etEditName.setText("");
                    etEditPrice.setText("");
                    etEditImage.setText("");
                    loadProductList();
                    Toast.makeText(MainActivity.this, "Sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Sửa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Sửa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm xoá sản phẩm
    private void deleteProduct(int id) {
        Call<Void> call = productService.deleteProduct(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Ẩn một nhóm hiển thị sản phẩm đã xóa
                    groupDeleteProduct.setVisibility(View.GONE);
                    // Xóa văn bản trong trường nhập liệu id
                    etDeleteId.setText("");
                    // Tải lại danh sách sản phẩm
                    loadProductList();
                    // Hiển thị thông báo thành công
                    Toast.makeText(MainActivity.this, "Xoá sản phẩm thành công", Toast.LENGTH_SHORT).show();

                } else {
                    // Nếu cuộc gọi không thành công, hiển thị thông báo lỗi
                    Toast.makeText(MainActivity.this, "Xoá sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Phương thức này được gọi khi xảy ra lỗi trong quá trình cuộc gọi
                // Hiển thị thông báo lỗi
                Toast.makeText(MainActivity.this, "Xoá sản phẩm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Hàm load danh sách sản phẩm
    private void loadProductList() {
        Call<List<Product>> call = productService.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();
                    ArrayAdapter<Product> adapter = new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_list_item_1, productList);
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "Không thể tải danh sách sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Không thể tải danh sách sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
