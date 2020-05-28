package application.controller.web;


import application.data.model.*;
import application.data.service.*;
import application.model.dto.ProductDTO;
import application.model.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/cart")
public class CartController extends  BaseController{

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


    @GetMapping(value = {"/shoping-cart"})
    public String cart(Model model,
                       @Valid @ModelAttribute("productname") ProductDTO productName,
                       HttpServletResponse response,
                       HttpServletRequest request,
                       final Principal principal)
    {
        CartVM vm = new CartVM();
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



        List<Size> sizeList = sizeService.getAll();
        List<SizeVM> sizeVMList = new ArrayList<>();

        for(Size size2 : sizeList) {
            SizeVM sizeVM = new SizeVM();
            sizeVM.setId(size2.getId());
            sizeVM.setName(size2.getName());
            sizeVMList.add(sizeVM);
        }

//        List<Color> colorList = productService.getListColorProduct(productId);
        List<Color> colorList = colorService.getAll();
        List<ColorVM> colorVMList = new ArrayList<>();

        for(Color color : colorList) {
            ColorVM colorVM = new ColorVM();
            colorVM.setId(color.getId());
            colorVM.setName(color.getName());
            colorVMList.add(colorVM);
        }



//        Pageable pageable = new PageRequest(0, 50);
//
//        Page<Product> productPage = null;
//
//        if (productName.getName() != null && !productName.getName().isEmpty()) {
//            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,product2.getCategory().getId(),productName.getName().trim());
//            vm.setKeyWord("Find with key: " + productName.getName());
//        } else {
//            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,product2.getCategory().getId(),null);
//        }
//
//
//        List<ProductVM> productVMList = new ArrayList<>();
//
//        for(Product product : productPage.getContent()) {
//            ProductVM productVM = new ProductVM();
//            if(product.getCategory() == null) {
//                productVM.setCategoryName("");
//            } else {
//                productVM.setCategoryName(product.getCategory().getName());
//            }
//
//            if(product.getSupply() == null) {
//                productVM.setSupplyName("");
//            } else {
//                productVM.setSupplyName(product.getSupply().getName());
//            }
//
//            if(product.getPromotion() == null) {
//                productVM.setPromotionName("");
//            } else {
//                productVM.setPromotionName(product.getPromotion().getName());
//            }
//            productVM.setId(product.getId());
//            productVM.setName(product.getName());
//            productVM.setMainImage(product.getMainImage());
//            productVM.setPrice(product.getPrice());
//            productVM.setShortDesc(product.getShortDesc());
//            productVM.setCreatedDate(product.getCreatedDate());
//            productVM.setCategoryId(product.getCategoryId());
//            productVMList.add(productVM);
//        }


        int productAmount = 0;
        double totalPrice = 0;
        List<CartProductVM> cartProductVMS = new ArrayList<>();

        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        String guid = getGuid(request);
        Order order = new Order();
        if(principal!= null) {

            if (userEntity != null) {
                order.setAddress(userEntity.getAddress());
                order.setCustomerName(userEntity.getName());
                order.setPhoneNumber(userEntity.getPhoneNumber());
                order.setEmail(userEntity.getEmail());
            }
        }

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
                        cartProductVM.setTotalPrice(price);
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
        if(totalPrice>1000000){
            vm.setShipPrice(0);
            vm.setTotal(totalPrice);
        } else {
            vm.setShipPrice(49500);
            vm.setTotal(totalPrice+49500);
        }

        vm.setCategoryVMList(categoryVMList);
        vm.setColorVMList(colorVMList);
        vm.setSizeVMList(sizeVMList);
        vm.setSupplyVMList(supplyVMList);
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
//        vm.setOrderVM(order);
        model.addAttribute("order",order);
        model.addAttribute("vm",vm);
        return "/cart";
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
