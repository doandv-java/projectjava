package application.data.service;

import application.data.model.Promotion;
import application.data.model.Supply;
import application.data.repository.PromotionRepository;
import application.data.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    public Promotion findOne(int promotionId){
        return promotionRepository.findOne(promotionId);
    }

    public List<Promotion> getAll(){
        return promotionRepository.findAll();
    }

    public void addNewPromotion(Promotion promotion){
        promotionRepository.save(promotion);
    }

    @Transactional
    public void delPromotion(int promotionId){
        promotionRepository.delete(promotionId);
    }

    public List<Promotion> getListSupplyByName(String promotionName){
        return promotionRepository.getListPromotionByName(promotionName);
    }

}
