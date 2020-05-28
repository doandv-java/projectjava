package application.data.repository;

import application.data.model.CartProduct;
import application.data.model.Category;
import application.data.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Integer> {
    @Query(value = "select * from dbo_product_image where product_id=:productId", nativeQuery = true)
    ProductImage findOne(@Param("productId") Integer productId);

    @Query(value = "select * from dbo_product_image", nativeQuery = true)
    List<ProductImage> findAll();
}
