package application.data.repository;

import application.data.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartProductRepository extends JpaRepository<CartProduct,Integer> {

    @Query(value = "SELECT * FROM dbo_cart_product cp " +
            "WHERE cp.cart_id = :cartId  " +
            "AND cp.product_entity_id = :productEntityId " +
            "ORDER BY cp.cart_product_id DESC LIMIT 1",nativeQuery = true)
    CartProduct findFirstCartProductByCartIdAndProductEntityId(@Param("cartId") Integer cartId, @Param("productEntityId") Integer productEntityId);

    @Query("SELECT p FROM dbo_cart_product p " +
            "WHERE (:cartProductId IS NULL OR UPPER(p.id) LIKE CONCAT('%',UPPER(:cartProductId),'%'))")
    CartProduct findOne(@Param("cartProductId") Integer cartProductId);
}
