package application.data.repository;

import application.data.model.Category;
import application.data.model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import java.util.List;

public interface SupplyRepository extends JpaRepository<Supply,Integer> {
    @Query("SELECT p FROM dbo_supply p " +
            "WHERE (:supplyName IS NULL OR UPPER(p.name) LIKE CONCAT('%',UPPER(:supplyName),'%'))")
    List<Supply> getListSupplyByName(@Param("supplyName") String supplyName);

    @Query("SELECT p FROM dbo_supply p " +
            "WHERE (:supplyId IS NULL OR UPPER(p.id) LIKE CONCAT('%',UPPER(:supplyId),'%'))")
    Supply findOne(@Param("supplyId") int supplyId);

    @Query(value = "select * from dbo_supply ", nativeQuery = true)
    List<Supply> findAll();
}
