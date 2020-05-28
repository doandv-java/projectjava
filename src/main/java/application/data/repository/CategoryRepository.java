package application.data.repository;

import application.data.model.Category;
import application.model.viewmodel.ChartLabelDataVM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    @Query("SELECT p FROM dbo_category p " +
            "WHERE (:categoryName IS NULL OR UPPER(p.name) LIKE CONCAT('%',UPPER(:categoryName),'%'))")
    List<Category> getListCategoryByName(@Param("categoryName") String categoryName);

    @Query("select distinct  new application.model.viewmodel.ChartLabelDataVM(c.name,sum(pe.amount)) " +
            "from dbo_category c inner  join c.listProducts p " +
            "inner join p.productEntityList pe " +
            "group by c.id " +
            "order by c.name asc")
    List<ChartLabelDataVM> countProductByName();

    @Query("SELECT p FROM dbo_category p " +
            "WHERE (:categoryId IS NULL OR UPPER(p.id) LIKE CONCAT('%',UPPER(:categoryId),'%'))")
    Category findOne(@Param("categoryId") int categoryId);

    @Query(value = "SELECT * FROM dbo_category ", nativeQuery = true)
    List<Category> findAll();
}
