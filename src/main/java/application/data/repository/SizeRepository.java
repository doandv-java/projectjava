package application.data.repository;

import application.data.model.Category;
import application.data.model.Color;
import application.data.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SizeRepository extends JpaRepository<Size,Integer> {
    @Query("SELECT p FROM dbo_size p " +
            "WHERE (:sizeName IS NULL OR UPPER(p.name) LIKE CONCAT('%',UPPER(:sizeName),'%'))")
    List<Size> getListSizeByName(@Param("sizeName") String sizeName);

    @Query(value = "SELECT DISTINCT s.* FROM dbo_product_entity pe " +
            "INNER JOIN  dbo_size s on(pe.size_id=s.size_id) " +
            "WHERE pe.product_id=:productId " +
            "ORDER BY s.name", nativeQuery = true)
    List<Size> getListSizeByProductId (@Param("productId") Integer productId);

    @Query(value = "SELECT * FROM dbo_size WHERE size_id=:sizeId", nativeQuery = true)
    Size findOne(@Param("sizeId") int sizeId);

    @Query(value = "SELECT * FROM dbo_size ", nativeQuery = true)
    List<Size> findAll();
}
