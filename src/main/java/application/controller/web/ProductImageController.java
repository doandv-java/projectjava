package application.controller.web;

import application.data.model.Product;
import application.data.model.ProductImage;
import application.data.repository.ProductImageRepository;
import application.data.service.ProductImageService;
import application.data.service.ProductService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.ProductImageDTO;
import application.model.dto.PromotionDTO;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ProductImage/")
public class ProductImageController {
    @Autowired
    ProductImageService productImageService;

    @Autowired
    ProductService productService;

    @PostMapping("/ListImage/{productId}")
    public @ResponseBody DataApiResult listImage(@PathVariable int productId){
        DataApiResult result= new DataApiResult();
        List<ProductImage> list = new ArrayList<>();

        try{
            Product product = productService.findOne(productId);
            list=product.getProductImageList();
            result.setMessage("Success");
            result.setSuccess(true);
            result.setData(list);
        } catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
           // return ResponseEntity.badRequest().body(result);
        }
//        return ResponseEntity.ok(result);
        return  result;
    }

    @PostMapping("/Detail/{productImageId}")
    public @ResponseBody DataApiResult detailImage(@PathVariable int productImageId){
        DataApiResult result= new DataApiResult();
//        List<ProductImage> list = new ArrayList<>();

        try{
            ProductImage productImage = productImageService.findOne(productImageId);
            result.setMessage("Success");
            result.setSuccess(true);
            result.setData(productImage);
        } catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            // return ResponseEntity.badRequest().body(result);
        }
//        return ResponseEntity.ok(result);
        return  result;
    }
}
