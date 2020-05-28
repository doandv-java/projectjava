package application.controller.api;

import application.data.model.Category;
import application.data.model.Size;
import application.data.model.Supply;
import application.data.service.CategoryService;
import application.data.service.SizeService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.SizeDTO;
import application.model.dto.SupplyDTO;
import application.model.viewmodel.CategoryVM;
import application.model.viewmodel.SizeVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/size")
public class SizeApiController {
    @Autowired
    private SizeService sizeService;

    @GetMapping(value="/detail", params = {"sizeId"}) //params y/n
    public @ResponseBody SizeVM getSizeInfor (@RequestParam(value = "sizeId") int sizeId){
        Size size = sizeService.findOne(sizeId);
        if(size==null)
            return null;
        SizeVM sizeVM= new SizeVM();
        sizeVM.setId(size.getId());
        sizeVM.setName(size.getName());
        sizeVM.setShortDesc(size.getShortDesc());
        sizeVM.setCreatedDate(size.getCreatedDate());

        return sizeVM;
    }

    @PostMapping(value="/delete/{sizeId}") //params y/n
    public @ResponseBody BaseApiResult delete (@PathVariable int sizeId){
        BaseApiResult result= new BaseApiResult();
        try {
            result.setSuccess(true);
            result.setMessage("Deleted category successfully");
            sizeService.delSize(sizeId);

        }catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }


    @PostMapping("/update/{sizeId}")
    public BaseApiResult update (@PathVariable int sizeId,
                                 @RequestBody SizeDTO dto){
        BaseApiResult result = new BaseApiResult();
        try{
            Size size = sizeService.findOne(sizeId);
            size.setId(dto.getId());
            size.setName(dto.getName());
            size.setShortDesc(dto.getShortDesc());
            size.setShortDesc(dto.getShortDesc());
            sizeService.addNewSize(size);
            result.setSuccess(true);
            result.setMessage("Updated size successfully");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/create")
    public BaseApiResult createSize(@RequestBody Size dto){
        DataApiResult result = new DataApiResult();

        try {
            Size size = new Size();
            size.setName(dto.getName());
            size.setShortDesc(dto.getShortDesc());
            size.setCreatedDate(new Date());
            sizeService.addNewSize(size);
            result.setData(size.getId());
            result.setMessage("Saved size successfully: " + size.getId());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
