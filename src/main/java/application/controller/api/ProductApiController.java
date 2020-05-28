package application.controller.api;

import application.data.model.*;
import application.data.service.*;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.ProductDTO;
import application.model.viewmodel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(path = "/api/product")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private ProductEntityService productEntityService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SizeService sizeService;

    @PostMapping(value = "/create")
    public BaseApiResult createProduct(@RequestBody ProductDTO dto) {
        DataApiResult result = new DataApiResult();

        try {
            Product product = new Product();
            product.setName(dto.getName());
            product.setShortDesc(dto.getShortDesc());
            if (dto.getPrice() > Math.pow(10, 7)) {
                result.setMessage("The price can not be over 10 million VND: " + product.getId());
                throw new IOException("The price can not be over 10 million VND");
            }
            product.setPrice(dto.getPrice());
            product.setMainImage(dto.getMainImage());
            product.setCategory(categoryService.findOne(dto.getCategoryId()));
            product.setCreatedDate(new Date());
            product.setSupplyId(dto.getSupplyId());
            product.setSupply(supplyService.findOne(dto.getSupplyId()));
            product.setCategoryId(dto.getCategoryId());
            product.setPromotion(promotionService.findOne(dto.getPromotionId()));

            productService.addNewProduct(product);
            result.setData(product.getId());
            result.setMessage("Saved product successfully: " + product.getId());
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/quick-view")
    public DataApiResult quickView(@RequestBody int productId) {
        DataApiResult result = new DataApiResult();
        ProductQuickViewVM data = new ProductQuickViewVM();
        try {
            Product product = productService.findOne(productId);
            if (product == null) {
                result.setSuccess(false);
                result.setMessage("Can not find this product");
                return result;
            }

            data.setId(product.getId());
            data.setName(product.getName());
            data.setMainImage(product.getMainImage());
            data.setPrice(product.getPrice());
            data.setShortDesc(product.getShortDesc());


            List<Size> sizeList = sizeService.getAll();
            List<SizeVM> sizeVMList = new ArrayList<>();
            for (Size size2 : sizeList) {
                SizeVM sizeVM = new SizeVM();
                sizeVM.setId(size2.getId());
                sizeVM.setName(size2.getName());
                sizeVMList.add(sizeVM);
            }
            data.setSizeVMList(sizeVMList);

            List<Color> colorList = colorService.getAll();
            List<ColorVM> colorVMList = new ArrayList<>();
            for (Color color : colorList) {
                ColorVM colorVM = new ColorVM();
                colorVM.setId(color.getId());
                colorVM.setName(color.getName());
                colorVMList.add(colorVM);
            }
            data.setColorVMList(colorVMList);

            List<ProductEntity> productEntityList = productEntityService.findByProductId(productId);
            List<ProductEntityVM> productEntityVMList = new ArrayList<>();
            for (ProductEntity item : productEntityList) {
                ProductEntityVM entityVM = new ProductEntityVM();
                entityVM.setColorName(item.getColor().getName());
                entityVM.setSizeName(item.getSize().getName());
                entityVM.setAmount(item.getAmount());
                entityVM.setProductId(item.getProductId());
                entityVM.setColorId(item.getColorId());
                entityVM.setSizeId(item.getSizeId());
                entityVM.setProductEntityId(item.getId());
                productEntityVMList.add(entityVM);
            }
            data.setProductEntityVMList(productEntityVMList);

            List<ProductImage> productImageList = product.getProductImageList();
            List<ProductImageVM> productImageVMList = new ArrayList<>();
            for (ProductImage img : productImageList) {
                ProductImageVM productImageVM = new ProductImageVM();
                productImageVM.setId(img.getId());
                productImageVM.setLink(img.getLink());
                productImageVM.setTitle(img.getTitle());
                productImageVMList.add(productImageVM);
            }
            data.setProductImageVMList(productImageVMList);

            result.setData(data);
            result.setMessage("OK");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @PostMapping("/update/{productId}")
    public BaseApiResult updateProduct(@PathVariable int productId,
                                       @RequestBody ProductDTO dto) {
        BaseApiResult result = new BaseApiResult();

        try {
            Product product = productService.findOne(productId);
            product.setName(dto.getName());
            product.setShortDesc(dto.getShortDesc());
            if (dto.getPrice() > Math.pow(10, 7)) {
                result.setMessage("The price can not be over 10 million VND: " + product.getId());
                throw new IOException("The price can not be over 10 million VND");
            }
            product.setPrice(dto.getPrice());
            product.setMainImage(dto.getMainImage());
            product.setCreatedDate(new Date());
            product.setSupplyId(dto.getSupplyId());
            product.setSupply(supplyService.findOne(dto.getSupplyId()));
            product.setPromotion(promotionService.findOne(dto.getPromotionId()));
            product.setCategoryId(dto.getCategoryId());
            product.setCategory(categoryService.findOne(dto.getCategoryId()));
            productService.addNewProduct(product);
            result.setSuccess(true);
            result.setMessage("Updated product successfully");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }


    @GetMapping("/detail/{productId}")
    public BaseApiResult detailMaterial(@PathVariable int productId) {
        DataApiResult result = new DataApiResult();

        try {
            Product productEntity = productService.findOne(productId);
            if (productEntity == null) {
                result.setSuccess(false);
                result.setMessage("Can not find this product");
            } else {
                ProductDTO dto = new ProductDTO();
                dto.setId(productEntity.getId());
                if (productEntity.getCategory() != null) {
                    dto.setCategoryId(productEntity.getCategory().getId());
                }

                if (productEntity.getSupply() != null) {
                    dto.setSupplyId(productEntity.getSupply().getId());
                }

                if (productEntity.getPromotion() != null) {
                    dto.setPromotionId(productEntity.getPromotion().getId());
                }
                dto.setMainImage(productEntity.getMainImage());
                dto.setName(productEntity.getName());
                dto.setPrice(productEntity.getPrice());
                dto.setShortDesc(productEntity.getShortDesc());
                dto.setCreatedDate(new Date());
                result.setSuccess(true);
                result.setData(dto);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }


    @PostMapping(value = "/delete/{productId}")
    public @ResponseBody
    BaseApiResult delete(@PathVariable int productId) {
        BaseApiResult result = new BaseApiResult();
        try {
            productService.deleteProduct(productId);
            result.setSuccess(true);
            result.setMessage("Deleted product successfully");

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @GetMapping(value = "/getAllSearch")
    public String getAllSearch(Model model,
                               @Valid @ModelAttribute("productname") ProductDTO productName,
                               @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                               @RequestParam(name = "size", required = false, defaultValue = "8") Integer size,
                               @RequestParam(name = "categoryId", required = false, defaultValue = "") Integer categoryId,
                               @RequestParam(name = "supplyId", required = false, defaultValue = "") Integer supplyId) {
        DataApiResult result = new DataApiResult();


        return "/admin/admin-product";
    }


    @PostMapping("/listSearch")
    public BaseApiResult getList(@RequestBody String productName) {
        DataApiResult result = new DataApiResult();

        try {
            List<Product> productList = productService.getListProductByName(productName);
            if (productList == null) {
                result.setSuccess(false);
                result.setMessage("Can not find this product");
            } else {
                List<ProductVM> productVMList = new ArrayList<>();
                for (Product product : productList) {
                    ProductVM dto = new ProductVM();
                    dto.setId(product.getId());
                    dto.setName(product.getName());
                    dto.setPrice(product.getPrice());
                    productVMList.add(dto);
                }

                result.setMessage("Success");
                result.setSuccess(true);
                result.setData(productVMList);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
//        List<ProductVM> productVMList = new ArrayList<>();
//                for (int i=0; i<10; i++) {
//                    ProductVM dto = new ProductVM();
//                    dto.setId(i);
//                    dto.setName("name "+i);
//                    dto.setPrice(1000D);
//                    productVMList.add(dto);
//                }
//                result.setData(productVMList);
        return result;
    }

    @PostMapping("/AmountProductEntity/{productId}")
    public DataApiResult getAmountProductEntity(@PathVariable int productId) {
        DataApiResult result = new DataApiResult();

        try {
            List<ProductEntity> productList = productEntityService.findByProductId(productId);
            List<ProductEntityVM> productEntityVMList = new ArrayList<>();

            for (ProductEntity entity : productList) {
                ProductEntityVM obj = new ProductEntityVM();
                obj.setProductEntityId(entity.getId());
                obj.setAmount(entity.getAmount());
                obj.setColorName(entity.getColor().getName());
                obj.setSizeName(entity.getSize().getName());
                obj.setProductId(entity.getProductId());
                productEntityVMList.add(obj);
            }
            result.setSuccess(true);
            result.setMessage("Success");
            result.setData(productEntityVMList);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }
}
