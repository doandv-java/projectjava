package application.controller.api;


import application.data.model.*;
import application.data.repository.FavouriteRepository;
import application.data.service.FavouriteService;
import application.data.service.ProductService;
import application.data.service.UserService;
import application.model.api.BaseApiResult;
import application.model.dto.FavouriteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/favourite")
public class FavouriteApiController {
    @Autowired
    FavouriteService favouriteService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public BaseApiResult add(@RequestBody FavouriteDTO dto){
        BaseApiResult result = new BaseApiResult();
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        Product product = productService.findOne(dto.getProductId());
        if(product==null){
            result.setMessage("Product not found!");
            result.setSuccess(false);
            return result;
        }
        try {
            Favourite favourite = new Favourite();
            favourite.setProductId(dto.getProductId());
            if(userEntity!=null)
                favourite.setUserName(userEntity.getUserName());
            favourite.setGuid(dto.getGuid());
            favourite.setCreatedDate(new Date());
            favouriteService.addNewFavourite(favourite);
            result.setMessage(product.getName()+ " is added to wishlist!");
            result.setSuccess(true);
            return result;

        } catch (Exception e) {
            // logger.error(e.getMessage());
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            return result;
        }
      //  result.setMessage("Can not add!");
       // result.setSuccess(false);
       // return result;

    }
}
