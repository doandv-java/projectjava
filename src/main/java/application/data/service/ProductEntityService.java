package application.data.service;

import application.data.model.Product;
import application.data.model.ProductEntity;
import application.data.repository.ProductEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductEntityService {
    @Autowired
    ProductEntityRepository productEntityRepository;

   public void update(ProductEntity productEntityList)
    {
        productEntityRepository.save(productEntityList);
    }

    public List<ProductEntity> findByProductId(Integer productId){
        return productEntityRepository.findByProductId(productId);
    }

    public ProductEntity findOne(Integer productEntityId){
        return productEntityRepository.findOne(productEntityId);
    }

    public ProductEntity getByProductSizeColor(Integer productId,Integer colorId,Integer sizeId){
        return productEntityRepository.getByProductSizeColor(productId,colorId,sizeId);
    }

    public  List<ProductEntity> getAll(){
        return productEntityRepository.getAll();
    }

}
