package application.controller.api;

import application.data.model.Supply;
import application.data.service.SupplyService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.SupplyDTO;
import application.model.viewmodel.SupplyVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/supply")
public class SupplyApiController {
    @Autowired
    private SupplyService supplyService;

    @RequestMapping(value="/detail", params = {"supplyId"}) //params y/n
    public @ResponseBody SupplyVM getSupplyInfor (@RequestParam(value = "supplyId") int supplyId){
        Supply supply = supplyService.findOne(supplyId);
        if(supply==null)
            return null;
        SupplyVM supplyVM= new SupplyVM();
        supplyVM.setId(supply.getId());
        supplyVM.setName(supply.getName());
        supplyVM.setShortDesc(supply.getShortDesc());
        supplyVM.setCreatedDate(supply.getCreatedDate());

        return supplyVM;
    }

    @PostMapping(value="/delete/{supplyId}") //params y/n
    public @ResponseBody BaseApiResult delete (@PathVariable int supplyId){
        BaseApiResult result= new BaseApiResult();
        try {
            result.setSuccess(true);
            result.setMessage("Delete supply successfully");
            supplyService.delSupply(supplyId);

        }catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

//    @RequestMapping(value="/update", method = RequestMethod.POST)
//    public @ResponseBody DataApiResult update (@RequestBody SupplyVM supplyVM){
//        DataApiResult result= new DataApiResult();
//        try{
//            Supply supply = supplyService.findOne(supplyVM.getId());
//            supply.setId(supplyVM.getId());
//            supply.setName(supplyVM.getName());
//            supply.setShortDesc(supplyVM.getShortDesc());
//            supply.setShortDesc(supplyVM.getShortDesc());
//            supplyService.addNewSupply(supply);
//            result.setSuccess(true);
//            result.setMessage("Updated product successfully");
//        } catch (Exception e) {
//            result.setSuccess(false);
//            result.setMessage(e.getMessage());
//        }
//        return result;
//    }

    @PostMapping("/update/{supplyId}")
    public BaseApiResult updateSupply(@PathVariable int supplyId,
                                 @RequestBody SupplyDTO dto){
        BaseApiResult result = new BaseApiResult();
        try{
            Supply supply = supplyService.findOne(supplyId);
            supply.setId(dto.getId());
            supply.setName(dto.getName());
            supply.setShortDesc(dto.getShortDesc());
            supply.setShortDesc(dto.getShortDesc());
            supplyService.addNewSupply(supply);
            result.setSuccess(true);
            result.setMessage("Updated supply successfully");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/create")
    public BaseApiResult createSupply(@RequestBody SupplyDTO dto){
        DataApiResult result = new DataApiResult();

        try {
            Supply supply = new Supply();
            supply.setName(dto.getName());
            supply.setShortDesc(dto.getShortDesc());
            supply.setCreatedDate(new Date());
            supplyService.addNewSupply(supply);
            result.setData(supply.getId());
            result.setMessage("Saved supply successfully: " + supply.getId());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
