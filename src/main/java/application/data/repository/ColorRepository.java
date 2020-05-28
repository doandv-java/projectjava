package application.data.repository;

import application.data.model.Color;
import application.data.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color,Integer> {
    @Query("SELECT p FROM dbo_color p " +
            "WHERE (:colorName IS NULL OR UPPER(p.name) LIKE CONCAT('%',UPPER(:colorName),'%'))")
    List<Color> getListColorByName(@Param("colorName") String colorName);

    @Query(value = "SELECT DISTINCT c.* FROM dbo_product_entity pe " +
            "INNER JOIN  dbo_color c on(pe.color_id=c.color_id) " +
            "WHERE pe.product_id=:productId " +
            "ORDER BY c.name", nativeQuery = true)
    List<Color> getListColorByProductId (@Param("productId") Integer productId);

    @Query("SELECT c FROM dbo_color c " +
            "WHERE (:colorId IS NULL OR UPPER(c.id) LIKE CONCAT('%',UPPER(:colorId),'%'))")
    Color findOne(@Param("colorId") int colorId);

    @Query(value = "SELECT * FROM dbo_color ", nativeQuery = true)
    List<Color> findAll();
}
