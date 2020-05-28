package application.controller.api;

import application.data.model.Category;
import application.data.model.Supply;
import application.data.service.CategoryService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.CategoryDTO;
import application.model.dto.SupplyDTO;
import application.model.viewmodel.CategoryVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryApiController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value="/detail", params = {"categoryId"})
    public @ResponseBody CategoryVM getSupplyInfor (@RequestParam(value = "categoryId") int categoryId){
        Category category = categoryService.findOne(categoryId);
        if(category==null)
            return null;
        CategoryVM CategoryVM= new CategoryVM();
        CategoryVM.setId(category.getId());
        CategoryVM.setName(category.getName());
        CategoryVM.setShortDesc(category.getShortDesc());
        CategoryVM.setCreatedDate(category.getCreatedDate());

        return CategoryVM;
    }

    @PostMapping(value="/delete/{categoryId}")
    public @ResponseBody BaseApiResult delete (@PathVariable int categoryId){
        BaseApiResult result= new BaseApiResult();
        try {
            result.setSuccess(true);
            result.setMessage("Deleted category successfully");
            categoryService.delCategory(categoryId);

        }catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }


    @PostMapping("/update/{categoryId}")
    public BaseApiResult update (@PathVariable int categoryId,
                                 @RequestBody CategoryDTO dto){
        BaseApiResult result = new BaseApiResult();
        try{
            Category category = categoryService.findOne(categoryId);
            category.setId(dto.getId());
            category.setName(dto.getName());
            category.setShortDesc(dto.getShortDesc());
            categoryService.addNewCategory(category);
            result.setSuccess(true);
            result.setMessage("Updated category successfully");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/create")
    public BaseApiResult createProduct(@RequestBody CategoryDTO dto){
        DataApiResult result = new DataApiResult();

        try {
            Category category = new Category();
            category.setName(dto.getName());
            category.setShortDesc(dto.getShortDesc());
            category.setCreatedDate(new Date());
            categoryService.addNewCategory(category);
            result.setData(category.getId());
            result.setMessage("Saved category successfully: " + category.getId());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
