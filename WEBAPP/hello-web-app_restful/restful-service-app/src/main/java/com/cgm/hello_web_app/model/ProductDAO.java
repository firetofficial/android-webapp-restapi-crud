package com.cgm.hello_web_app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
	
	public List<Product> getAllProducts() {
	    List<Product> list = null;
	    Connection conn = null;
	    String url, user, password;
	    String query;
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    try {
	        ///load driver
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        //1. Connect to MySQL Database
	        url = "jdbc:mysql://localhost:3306/product-k15";
	        user = "root";
	        password = "thanhBi1@";
	        conn = DriverManager.getConnection(url, user, password);
	        //System.out.println(conn);
	        //2. execute query statement: SQL

	        query = "select * from product";
	        pst = conn.prepareStatement(query);
	        rs = pst.executeQuery();
	        list = new ArrayList<Product>();
	        while (rs.next()) {
	            int id = rs.getInt("id"); // Lấy ID của sản phẩm
	            String name = rs.getString("name");
	            String image = rs.getString("image");
	            double price = rs.getDouble("price");

	            Product product = new Product(id, name, price, image); // Thêm ID vào sản phẩm
	            list.add(product);
	        }

	    } catch (ClassNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (SQLException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } finally {
	        try {
	            conn.close();
	            rs.close();
	            pst.close();
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

	    //3. get result

	    return list;
	}

	public void addProduct(Product product) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/product-k15";
        String user = "root";
        String password = "thanhBi1@";

        String insertQuery = "INSERT INTO product (name, price, image) VALUES (?, ?, ?)";
        String idQuery = "SELECT MAX(id) AS max_id FROM product";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            
            // Lấy ID cao nhất từ bảng product
            pst = conn.prepareStatement(idQuery);
            rs = pst.executeQuery();
            int maxId = 0;
            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }
            
            // Tạo ID mới
            int newId = maxId + 1;
            
            // Thêm sản phẩm vào cơ sở dữ liệu với ID mới
            pst = conn.prepareStatement(insertQuery);
            pst.setString(1, product.getName());
            pst.setDouble(2, product.getPrice());
            pst.setString(3, product.getImage());
            
            pst.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	public void updateProduct(Product product) {
	    Connection conn = null;
	    PreparedStatement pst = null;

	    String url = "jdbc:mysql://localhost:3306/product-k15";
	    String user = "root";
	    String password = "thanhBi1@";

	    String updateQuery = "UPDATE product SET name = ?, price = ?, image = ? WHERE id = ?";

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection(url, user, password);

	        pst = conn.prepareStatement(updateQuery);
	        pst.setString(1, product.getName());
	        pst.setDouble(2, product.getPrice());
	        pst.setString(3, product.getImage());
	        pst.setInt(4, product.getId());

	        pst.executeUpdate();
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pst != null) {
	                pst.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}


	public static void main(String[] args) {
		ProductDAO productDAO = new ProductDAO();
		productDAO.getAllProducts();
	}
	public void deleteProduct(int productId) {
	    Connection conn = null;
	    PreparedStatement pst = null;

	    String url = "jdbc:mysql://localhost:3306/product-k15";
	    String user = "root";
	    String password = "thanhBi1@";

	    String query = "DELETE FROM product WHERE id = ?";

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection(url, user, password);

	        pst = conn.prepareStatement(query);
	        pst.setInt(1, productId);

	        pst.executeUpdate();
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pst != null) {
	                pst.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	public Product getProductById(int productId) {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Product product = null;

    String url = "jdbc:mysql://localhost:3306/product-k15";
    String user = "root";
    String password = "thanhBi1@";

    String query = "SELECT * FROM product WHERE id = ?";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, password);

        pst = conn.prepareStatement(query);
        pst.setInt(1, productId);

        rs = pst.executeQuery();
        if (rs.next()) {
        	int id = rs.getInt("id"); // Lấy ID của sản phẩm
            String name = rs.getString("name");
            String image = rs.getString("image");
            double price = rs.getDouble("price");

            product = new Product(id, name, price, image);
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return product;
}



}
