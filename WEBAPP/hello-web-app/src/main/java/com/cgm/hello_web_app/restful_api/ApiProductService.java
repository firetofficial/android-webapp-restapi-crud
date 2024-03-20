package com.cgm.hello_web_app.restful_api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cgm.hello_web_app.model.Product;
import com.cgm.hello_web_app.model.ProductDAO;

@Path("/products")
public class ApiProductService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts_JSON() {
        ProductDAO productDAO = new ProductDAO();
        return Response.ok(productDAO.getAllProducts()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) {
        ProductDAO productDAO = new ProductDAO();
        
        // Thêm sản phẩm vào cơ sở dữ liệu
        productDAO.addProduct(product);
        
        // Trả về mã HTTP 200 OK và thông báo "test thành công"
        return Response.ok("Thành công").build();
    }
    
    @PUT
    @Path("/{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("productId") int productId, Product product) {
        ProductDAO productDAO = new ProductDAO();
        Product existingProduct = productDAO.getProductById(productId);

        if (existingProduct != null) {
            product.setId(productId);
            productDAO.updateProduct(product);
            return Response.ok(product).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }
    }


    @DELETE
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("productId") int productId) {
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductById(productId);

        if (product != null) {
            productDAO.deleteProduct(productId);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }
    }
}
