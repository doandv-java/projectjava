package application.controller.web;

import application.data.model.*;
import application.data.service.*;
import application.extension.MyFunction;
import application.model.dto.ProductDTO;
import application.model.viewmodel.*;
import application.model.viewmodel.Admin.LayoutHeaderAdminVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/product")
public class ProductController extends  BaseController{

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    SizeService sizeService;

    @Autowired
    ColorService colorService;

    @Autowired
    SupplyService supplyService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ProductEntityService productEntityService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    RateService rateService;
    @GetMapping(value = {"/{productId}"})
    public String home(Model model,
                       @Valid @ModelAttribute("productname") ProductDTO productName,
                       @PathVariable("productId") Integer productId,
                       HttpServletResponse response,
                       HttpServletRequest request,
                       final Principal principal)
    {
        ProductDetailVM vm = new ProductDetailVM();
        List<Category> categoryList = categoryService.getAll();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        for(Category category : categoryList) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(category.getId());
            categoryVM.setName(category.getName());
            categoryVMList.add(categoryVM);
        }

        List<Supply> supplyList = supplyService.getAll();
        List<SupplyVM> supplyVMList = new ArrayList<>();

        for(Supply supply : supplyList) {
            SupplyVM supplyVM = new SupplyVM();
            supplyVM.setId(supply.getId());
            supplyVM.setName(supply.getName());
            supplyVMList.add(supplyVM);
        }


        List<ProductEntity> productEntityList = productEntityService.getAll();
        List<ProductEntityVM> productEntityVMList = new ArrayList<>();
        for(ProductEntity item : productEntityList){
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





        Product product2= productService.findOne(productId);
        ProductVM productVM2 = new ProductVM();
        productVM2.setId(product2.getId());
        productVM2.setName(product2.getName());
        productVM2.setPrice(product2.getPrice());
        productVM2.setShortDesc(product2.getShortDesc());
        productVM2.setMainImage(product2.getMainImage());
        productVM2.setCategoryName(product2.getCategory().getName());
        productVM2.setCategoryId(product2.getCategoryId());
        List<Rate> rateList = product2.getRateList();
        int totalRate = rateList.size();

        List<ProductImage> productImageList= product2.getProductImageList();
        List<ProductImageVM> productImageVMList = new ArrayList<>();
        for(ProductImage img : productImageList){
            ProductImageVM productImageVM= new ProductImageVM();
            productImageVM.setId(img.getId());
            productImageVM.setLink(img.getLink());
            productImageVM.setTitle(img.getTitle());
            productImageVMList.add(productImageVM);
        }


        List<Size> sizeList = sizeService.getListSizeByProductId(productId);
        List<SizeVM> sizeVMList = new ArrayList<>();

        for(Size size2 : sizeList) {
            SizeVM sizeVM = new SizeVM();
            sizeVM.setId(size2.getId());
            sizeVM.setName(size2.getName());
            sizeVMList.add(sizeVM);
        }

//        List<Color> colorList = productService.getListColorProduct(productId);
        List<Color> colorList = colorService.getListColorByProductId(productId);
        List<ColorVM> colorVMList = new ArrayList<>();

        for(Color color : colorList) {
            ColorVM colorVM = new ColorVM();
            colorVM.setId(color.getId());
            colorVM.setName(color.getName());
            colorVMList.add(colorVM);
        }
        int totalStar=0, amountStar=0;
        List<RateVM> rateVMList = new ArrayList<>();
        for(Rate rate : rateList){
            RateVM rateVM= new RateVM();
            rateVM.setAvatar(rate.getUser().getAvatar());
            rateVM.setComment(rate.getComment());
            rateVM.setCreatedDate(rate.getCreatedDate());
            rateVM.setStar(rate.getStar());
            rateVM.setId(rate.getId());
            totalStar+=rate.getStar();
            amountStar++;
            if(rate.getUser().getName()==null || rate.getUser().getName().equals("unname"))
                rateVM.setUserName(rate.getUser().getUserName());
            else
                rateVM.setUserName(rate.getUser().getName());
            List<Rate> chilRate = rate.getRateList();
            if(chilRate!=null){
                List<RateVM> rateVMListChil = new ArrayList<>();
                for(Rate item : chilRate){
                    RateVM rateVM2= new RateVM();
                    rateVM2.setAvatar(item.getUser().getAvatar());
                    rateVM2.setComment(item.getComment());
                    rateVM2.setCreatedDate(item.getCreatedDate());
                    rateVM2.setId(item.getId());
                    rateVM2.setParentId(item.getRate().getId());
                    if(item.getUser().getName()==null || item.getUser().getName().equals("unname"))
                        rateVM2.setUserName(item.getUser().getUserName());
                    else
                        rateVM2.setUserName(item.getUser().getName());
                    rateVMListChil.add(rateVM2);
                }
                rateVM.setRateVMList(rateVMListChil);
            }
            rateVMList.add(rateVM);
        }
        if(amountStar==0)
            vm.setTotalRate(0);
        else
            vm.setRateAvg(Math.round(totalStar/amountStar));
        Pageable pageable = new PageRequest(0, 50);

        Page<Product> productPage = null;

        if (productName.getName() != null && !productName.getName().isEmpty()) {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,product2.getCategory().getId(),productName.getName().trim());
            vm.setKeyWord("Find with key: " + productName.getName());
        } else {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,product2.getCategory().getId(),null);
        }


        List<ProductVM> productVMList = new ArrayList<>();

        for(Product product : productPage.getContent()) {
            ProductVM productVM = new ProductVM();
            if(product.getCategory() == null) {
                productVM.setCategoryName("");
            } else {
                productVM.setCategoryName(product.getCategory().getName());
            }

            if(product.getSupply() == null) {
                productVM.setSupplyName("");
            } else {
                productVM.setSupplyName(product.getSupply().getName());
            }

            if(product.getPromotion() == null) {
                productVM.setPromotionName("");
            } else {
                productVM.setPromotionName(product.getPromotion().getName());
            }
            productVM.setId(product.getId());
            productVM.setRateAvg(Math.round(rateService.getRateAvg(product.getId())));
            productVM.setName(product.getName());
            productVM.setMainImage(product.getMainImage());
            productVM.setPrice(product.getPrice());
            productVM.setShortDesc(product.getShortDesc());
         //   productVM.setCreatedDate(product.getCreatedDate());
            productVM.setSizeVMList(MyFunction.toSizeVMList(sizeService.getListSizeByProductId(product.getId())));
            productVM.setColorVMList(MyFunction.toColorVMList(colorService.getListColorByProductId(product.getId())));
            productVM.setProductImageVMList(MyFunction.toProductImageVMList(product.getProductImageList()));
            productVM.setCategoryId(product.getCategoryId());
            productVMList.add(productVM);
        }


        int productAmount = 0;
        double totalPrice = 0;
        List<CartProductVM> cartProductVMS = new ArrayList<>();

        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        String guid = getGuid(request);

        DecimalFormat df = new DecimalFormat("####0.00");

        try {
            if(guid != null) {
                Cart cartEntity;
                if(userEntity==null)
                    cartEntity= cartService.findFirstCartByGuid(guid);
                else
                    cartEntity= cartService.findByUserName(userEntity.getUserName());

                if(cartEntity != null) {
                    productAmount = cartEntity.getListCartProducts().size();
                    for(CartProduct cartProduct : cartEntity.getListCartProducts()) {
                        CartProductVM cartProductVM = new CartProductVM();
                        cartProductVM.setId(cartProduct.getId());
                        cartProductVM.setName(cartProduct.getProductEntity().getProduct().getName());
                        cartProductVM.setProductId(cartProduct.getProductEntity().getProductId());
                        cartProductVM.setProductName(cartProduct.getProductEntity().getProduct().getName());
                        cartProductVM.setMainImage(cartProduct.getProductEntity().getProduct().getMainImage());
                        cartProductVM.setAmount(cartProduct.getAmount());
                        cartProductVM.setColorName(cartProduct.getProductEntity().getColor().getName());
                        cartProductVM.setSizeName(cartProduct.getProductEntity().getSize().getName());
                        cartProductVM.setProductEntityId(cartProduct.getProductEntityId());
                        double price = cartProduct.getAmount()*cartProduct.getProductEntity().getProduct().getPrice();
                        cartProductVM.setPrice(cartProduct.getProductEntity().getProduct().getPrice());
                        totalPrice += price;
                        cartProductVMS.add(cartProductVM);
                    }
                }
            }
        } catch (Exception e) {
            //logger.error(e.getMessage());
        }

        vm.setCartProductVMList(cartProductVMS);
        vm.setProductAmount(productAmount);
        vm.setTotalPrice(totalPrice);

        vm.setTotalRate(totalRate);
        vm.setRateVMList(rateVMList);
        vm.setProductEntityVMList(productEntityVMList);
        vm.setProductImageVMList(productImageVMList);
        vm.setProductVM(productVM2);
        vm.setCategoryVMList(categoryVMList);
        vm.setColorVMList(colorVMList);
        vm.setProductVMList(productVMList);
        vm.setSizeVMList(sizeVMList);
        vm.setSupplyVMList(supplyVMList);
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        model.addAttribute("vm",vm);
        return "/product-detail";
    }


    public String getGuid(HttpServletRequest request) {
        Cookie cookie[] = request.getCookies();

        if(cookie!=null) {
            for(Cookie c :cookie) {
                if(c.getName().equals("guid"))  return c.getValue();
            }
        }
        return null;
    }
}
