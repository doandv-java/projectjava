package application.data.repository;

import application.data.model.Favourite;
import application.data.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer>{
    @Query("select p from dbo_favourite f, dbo_product p " +
            "where f.productId=p.id and f.userName=:userName")
    List<Product> getProductFavouriteByUserName(@Param("userName") String userName);


    @Query("select p from dbo_favourite f, dbo_product p " +
            "where f.productId=p.id and f.guid=:guid")

    List<Product> getProductFavouriteByGuid(@Param("guid") String guid);




}