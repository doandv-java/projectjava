package application.data.repository;

import application.data.model.Color;
import application.data.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("select sum(p.amount) from dbo_product_entity p where (p.productId = :productId) ")
    Long getTotalProducts(@Param("productId") Integer productId);

    @Query("SELECT p FROM dbo_product p " +
            "WHERE (:categoryId IS NULL OR (p.categoryId = :categoryId))" +
            "AND (:productName IS NULL OR UPPER(p.name) LIKE CONCAT('%',UPPER(:productName),'%'))")
    Page<Product> getListProductByCategoryOrProductNameContaining(Pageable pageable, @Param("categoryId") Integer categoryId, @Param("productName") String productName);

    @Query("SELECT p FROM dbo_product p " +
            "WHERE  ( UPPER(p.name) LIKE CONCAT('%',UPPER(:productName),'%'))")
    List<Product> getListProductByName(@Param("productName") String productName);

	
	 @Query( value = "select p.* " +
            "from dbo_product p " +
            "inner join dbo_product_entity pe on(p.product_id=pe.product_id) " +
            "inner join dbo_order_product op on(op.product_entity_id=pe.product_entity_id) " +
            "group by p.product_id " +
            "order by sum(pe.amount) desc " +
            "limit 20;", nativeQuery = true)
    List<Product> getHotProduct();

    @Query("SELECT distinct p FROM dbo_product p " +
            "LEFT  JOIN p.productEntityList pe " +
            "WHERE (:categoryId IS NULL OR (p.categoryId = :categoryId)) " +
            "AND (:colorId IS NULL OR (pe.colorId = :colorId)) " +
            "AND (:sizeId IS NULL OR (pe.sizeId = :sizeId)) " +
            "AND (:supplyId IS NULL OR (p.supplyId = :supplyId)) " +
            "AND (:productName IS NULL OR UPPER(p.name) LIKE CONCAT('%',UPPER(:productName),'%'))")
    Page<Product> getListProduct(Pageable pageable, @Param("categoryId") Integer categoryId
                                , @Param("productName") String productName
            , @Param("colorId") Integer colorId
            , @Param("sizeId") Integer sizeId
            , @Param("supplyId") Integer supplyId );
}
