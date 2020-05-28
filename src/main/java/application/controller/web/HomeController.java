package application.controller.web;

import application.data.model.*;
import application.data.service.*;
import application.extension.MyFunction;
import application.model.dto.ProductDTO;
import application.model.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController extends BaseController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @Autowired
    SizeService sizeService;

    @Autowired
    ColorService colorService;

    @Autowired
    SupplyService supplyService;

    @Autowired
    CartProductService cartProductService;

    @Autowired
    UserService userService;

    @Autowired
    RateService rateService;

    @Autowired
    FavouriteService favouriteService;

    @Autowired
    ProductEntityService productEntityService;

    @GetMapping(value = {"/", "home"})
    public String home(Model model,
                       @Valid @ModelAttribute("productname") ProductDTO productName,
                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                       @RequestParam(name = "size", required = false, defaultValue = "8") Integer size,
                       HttpServletResponse response,
                       HttpServletRequest request,
                       final Principal principal) {
        this.checkCookie(response, request, principal);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        String guid = MyFunction.getGuid(request);

        HomeVM vm = new HomeVM();
        List<Category> categoryList = categoryService.getAll();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(category.getId());
            categoryVM.setName(category.getName());
            categoryVMList.add(categoryVM);
        }

        List<Supply> supplyList = supplyService.getAll();
        List<SupplyVM> supplyVMList = new ArrayList<>();

        for (Supply supply : supplyList) {
            SupplyVM supplyVM = new SupplyVM();
            supplyVM.setId(supply.getId());
            supplyVM.setName(supply.getName());
            supplyVMList.add(supplyVM);
        }


        List<Size> sizeList = sizeService.getAll();
        List<SizeVM> sizeVMList = MyFunction.toSizeVMList(sizeList);

        List<Color> colorList = colorService.getAll();
        List<ColorVM> colorVMList = MyFunction.toColorVMList(colorList);

        List<Product> favouriteProductList = new ArrayList<>();
        if (userEntity != null) {
            favouriteProductList = favouriteService.getProductFavouriteByUserName(userEntity.getUserName());
        } else {
            favouriteProductList = favouriteService.getProductFavouriteByGuid(guid);
        }

        Pageable pageable = new PageRequest(page, 12);

        Page<Product> productPage = null;

        if (productName.getName() != null && !productName.getName().isEmpty()) {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable, null, productName.getName().trim());
            vm.setKeyWord("Find with key: " + productName.getName());
        } else {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable, null, null);
        }


        List<ProductVM> productVMList = new ArrayList<>();

        for (Product product : productPage.getContent()) {
            ProductVM productVM = new ProductVM();
            if (product.getCategory() == null) {
                productVM.setCategoryName("");
            } else {
                productVM.setCategoryName(product.getCategory().getName());
            }

            if (product.getSupply() == null) {
                productVM.setSupplyName("");
            } else {
                productVM.setSupplyName(product.getSupply().getName());
            }

            if (product.getPromotion() == null) {
                productVM.setPromotionName("");
            } else {
                productVM.setPromotionName(product.getPromotion().getName());
            }
            productVM.setId(product.getId());
            productVM.setName(product.getName());
            productVM.setMainImage(product.getMainImage());
            productVM.setPrice(product.getPrice());
            productVM.setShortDesc(product.getShortDesc());
            productVM.setRateAvg(Math.round(rateService.getRateAvg(product.getId())));
            productVM.setCategoryId(product.getCategoryId());
            productVM.setSizeVMList(MyFunction.toSizeVMList(sizeService.getListSizeByProductId(product.getId())));
            productVM.setColorVMList(MyFunction.toColorVMList(colorService.getListColorByProductId(product.getId())));
            productVM.setProductImageVMList(MyFunction.toProductImageVMList(product.getProductImageList()));
            if (MyFunction.isContainer(favouriteProductList, product.getId()))
                productVM.setFavourite(1);
            else
                productVM.setFavourite(0);
            productVMList.add(productVM);
        }

        List<ProductEntity> productEntityList = productEntityService.getAll();
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

        int productAmount = 0;
        double totalPrice = 0;
        List<CartProductVM> cartProductVMS = new ArrayList<>();

        try {
            if (guid != null) {
                Cart cartEntity;
                if (userEntity == null)
                    cartEntity = cartService.findFirstCartByGuid(guid);
                else
                    cartEntity = cartService.findByUserName(userEntity.getUserName());

                if (cartEntity != null) {
                    productAmount = cartEntity.getListCartProducts().size();
                    for (CartProduct cartProduct : cartEntity.getListCartProducts()) {
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
                        double price = cartProduct.getAmount() * cartProduct.getProductEntity().getProduct().getPrice();
                        cartProductVM.setPrice(cartProduct.getProductEntity().getProduct().getPrice());
                        totalPrice += price;
                        cartProductVMS.add(cartProductVM);
                    }
                }
            }
        } catch (Exception e) {
            //logger.error(e.getMessage());
        }
        vm.setProductEntityVMList(productEntityVMList);
        vm.setCartProductVMList(cartProductVMS);
        vm.setProductAmount(productAmount);
        vm.setTotalPrice(totalPrice);
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setCategoryVMList(categoryVMList);
        vm.setColorVMList(colorVMList);
        vm.setProductVMList(productVMList);
        vm.setSizeVMList(sizeVMList);
        vm.setSupplyVMList(supplyVMList);
        model.addAttribute("vm", vm);
        return "/home";
    }


    @GetMapping(value = {"/store"})
    public String store(Model model,
                        @Valid @ModelAttribute("productname") ProductDTO productName,
                        @RequestParam(name = "categoryId", required = false) Integer categoryId,
                        @RequestParam(name = "colorId", required = false) Integer colorId,
                        @RequestParam(name = "sizeId", required = false) Integer sizeId,
                        @RequestParam(name = "supplyId", required = false) Integer supplyId,
                        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(name = "size", required = false, defaultValue = "8") Integer size,
                        @RequestParam(name = "sortByPrice", required = false) String sort,
                        HttpServletResponse response,
                        HttpServletRequest request,
                        final Principal principal) {
        HomeVM vm = new HomeVM();
        List<Category> categoryList = categoryService.getAll();
        List<CategoryVM> categoryVMList = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(category.getId());
            categoryVM.setName(category.getName());
            categoryVMList.add(categoryVM);
        }

        List<Supply> supplyList = supplyService.getAll();
        List<SupplyVM> supplyVMList = new ArrayList<>();

        for (Supply supply : supplyList) {
            SupplyVM supplyVM = new SupplyVM();
            supplyVM.setId(supply.getId());
            supplyVM.setName(supply.getName());
            supplyVMList.add(supplyVM);
        }

        List<ProductEntity> productEntityList = productEntityService.getAll();
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

        List<Size> sizeList = sizeService.getAll();
        List<SizeVM> sizeVMList = MyFunction.toSizeVMList(sizeList);

        List<Color> colorList = colorService.getAll();
        List<ColorVM> colorVMList = MyFunction.toColorVMList(colorList);

        Sort sortable = new Sort(Sort.Direction.ASC, "id");
        if (sort != null) {
            if (sort.equals("ASC")) {
                sortable = new Sort(Sort.Direction.ASC, "price");
            } else {
                sortable = new Sort(Sort.Direction.DESC, "price");
            }
        }

        Pageable pageable = new PageRequest(page, size, sortable);

        Page<Product> productPage = null;

        if (productName.getName() != null && !productName.getName().isEmpty()) {
            productPage = productService.getListProduct(pageable, categoryId, productName.getName().trim(), colorId, sizeId, supplyId);
            vm.setKeyWord("Search: " + productName.getName());
        } else {
            productPage = productService.getListProduct(pageable, categoryId, null, colorId, sizeId, supplyId);
        }


        List<ProductVM> productVMList = new ArrayList<>();

        for (Product product : productPage.getContent()) {
            ProductVM productVM = new ProductVM();
            if (product.getCategory() == null) {
                productVM.setCategoryName("");
            } else {
                productVM.setCategoryName(product.getCategory().getName());
            }

            if (product.getSupply() == null) {
                productVM.setSupplyName("");
            } else {
                productVM.setSupplyName(product.getSupply().getName());
            }

            if (product.getPromotion() == null) {
                productVM.setPromotionName("");
            } else {
                productVM.setPromotionName(product.getPromotion().getName());
            }
            productVM.setId(product.getId());
            productVM.setName(product.getName());
            productVM.setMainImage(product.getMainImage());
            productVM.setPrice(product.getPrice());
            productVM.setRateAvg(Math.round(rateService.getRateAvg(product.getId())));
            productVM.setShortDesc(product.getShortDesc());
            //   productVM.setCreatedDate(product.getCreatedDate());
            productVM.setCategoryId(product.getCategoryId());
            productVM.setSizeVMList(MyFunction.toSizeVMList(sizeService.getListSizeByProductId(product.getId())));
            productVM.setColorVMList(MyFunction.toColorVMList(colorService.getListColorByProductId(product.getId())));
            productVM.setProductImageVMList(MyFunction.toProductImageVMList(product.getProductImageList()));
            productVMList.add(productVM);
        }

        int productAmount = 0;
        double totalPrice = 0;
        List<CartProductVM> cartProductVMS = new ArrayList<>();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        String guid = MyFunction.getGuid(request);

        DecimalFormat df = new DecimalFormat("####0.00");

        try {
            if (guid != null) {
                Cart cartEntity;
                if (userEntity == null)
                    cartEntity = cartService.findFirstCartByGuid(guid);
                else
                    cartEntity = cartService.findByUserName(userEntity.getUserName());

                if (cartEntity != null) {
                    productAmount = cartEntity.getListCartProducts().size();
                    for (CartProduct cartProduct : cartEntity.getListCartProducts()) {
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
                        double price = cartProduct.getAmount() * cartProduct.getProductEntity().getProduct().getPrice();
                        cartProductVM.setPrice(cartProduct.getProductEntity().getProduct().getPrice());
                        totalPrice += price;
                        cartProductVMS.add(cartProductVM);
                    }
                }
            }
        } catch (Exception e) {
            //logger.error(e.getMessage());
        }

        if (productVMList.size() == 0) {
            vm.setFound(false);
        } else {
            vm.setFound(true);
        }
        vm.setCartProductVMList(cartProductVMS);
        vm.setProductAmount(productAmount);
        vm.setTotalPrice(totalPrice);

        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setCategoryVMList(categoryVMList);
        vm.setColorVMList(colorVMList);
        vm.setProductVMList(productVMList);
        vm.setSizeVMList(sizeVMList);
        vm.setSupplyVMList(supplyVMList);
        vm.setProductEntityVMList(productEntityVMList);
        model.addAttribute("vm", vm);
        model.addAttribute("page", productPage);

        return "/store";
    }


}
