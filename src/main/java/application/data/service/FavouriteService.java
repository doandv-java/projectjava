package application.data.service;

import application.data.model.Favourite;
import application.data.model.Product;
import application.data.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteService {
    @Autowired
    FavouriteRepository favouriteRepository;

    public List<Product> getProductFavouriteByUserName(String userName){
        return favouriteRepository.getProductFavouriteByUserName(userName);
    }

    public List<Product> getProductFavouriteByGuid(String guid){
        return favouriteRepository.getProductFavouriteByGuid(guid);
    }

    public void addNewFavourite(Favourite favourite){
        favouriteRepository.save(favourite);
    }
}
