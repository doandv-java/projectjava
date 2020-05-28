package application.controller.api;

import application.data.model.Promotion;
import application.data.model.Supply;
import application.data.service.PromotionService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.PromotionDTO;
import application.model.dto.SupplyDTO;
import application.model.viewmodel.PromotionVM;
import application.model.viewmodel.SupplyVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/promotion")
public class PromotionApiController {
    @Autowired
    private PromotionService promotionService;

    @RequestMapping(value="/detail", params = {"promotionId"}) //params y/n
    public @ResponseBody PromotionVM getPromotionInfor (@RequestParam(value = "promotionId") int promotionId){
        Promotion promotion = promotionService.findOne(promotionId);
        if(promotion==null)
            return null;
        PromotionVM promotionVM= new PromotionVM();
        promotionVM.setId(promotion.getId());
        promotionVM.setName(promotion.getName());
        promotionVM.setShortDesc(promotion.getShortDesc());
        promotionVM.setCreatedDate(promotion.getCreatedDate());
        promotionVM.setDiscount(promotion.getDiscount());
        promotionVM.setBeginDate(promotion.getBeginDate());
        promotionVM.setEndDate(promotion.getEndDate());
        return promotionVM;
    }

    @PostMapping(value="/delete/{promotionId}") //params y/n
    public @ResponseBody BaseApiResult delete (@PathVariable int promotionId){
        BaseApiResult result= new BaseApiResult();
        try {
            result.setSuccess(true);
            result.setMessage("Deleted promotion successfully");
            promotionService.delPromotion(promotionId);

        }catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @PostMapping(value = "/create")
    public DataApiResult createPromotion(@RequestBody PromotionDTO dto){
        DataApiResult result = new DataApiResult();

        try {
            Promotion promotion = new Promotion();
            promotion.setName(dto.getName());
            promotion.setShortDesc(dto.getShortDesc());
            promotion.setCreatedDate(new Date());
            promotion.setDiscount(dto.getDiscount());
            promotion.setBeginDate(dto.getBeginDate());
            promotion.setEndDate(dto.getEndDate());
            promotionService.addNewPromotion(promotion);
            result.setData(promotion.getId());
            result.setMessage("Saved promotion successfully: " + promotion.getId());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @PostMapping("/update/{promotionId}")
    public BaseApiResult update (@PathVariable int promotionId,
                                 @RequestBody PromotionDTO dto){
        BaseApiResult result = new BaseApiResult();
        try{
            Promotion promotion = promotionService.findOne(promotionId);
            promotion.setId(dto.getId());
            promotion.setName(dto.getName());
            promotion.setShortDesc(dto.getShortDesc());
            promotion.setShortDesc(dto.getShortDesc());
            promotion.setDiscount(dto.getDiscount());
            promotion.setBeginDate(dto.getBeginDate());
            promotion.setEndDate(dto.getEndDate());
            promotionService.addNewPromotion(promotion);
            result.setSuccess(true);
            result.setMessage("Updated promotion successfully");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
