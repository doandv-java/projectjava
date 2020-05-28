package application.controller.web;

import application.constant.StatusRegisterUserEnum;
import application.data.model.*;
import application.data.service.*;
import application.extension.MyFunction;
import application.model.dto.ProductDTO;
import application.model.dto.UserDTO;
import application.model.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class UserController extends  BaseController{
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CartProductService cartProductService;

   @Autowired
    CartService cartService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SizeService sizeService;


    @Autowired
    private ColorService colorService;

    @Autowired
    private ProductEntityService productEntityService;

    @GetMapping(value = {"/sign-in"})
    public String signIn(Model model,
                         @Valid @ModelAttribute("productname") ProductDTO productName)
    {
        HomeVM vm = new HomeVM();
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        model.addAttribute("vm",vm);
        return "/sign-in";
    }


    @GetMapping(value = {"/sign-up"})
    public String signUp(Model model)
    {
        model.addAttribute("user", new User());
        return "/sign-up";
    }

    @RequestMapping(path="/register", method = RequestMethod.POST)
    public String registerNewUser(@Valid @ModelAttribute("user")User user){
        StatusRegisterUserEnum status =userService.registerNewUser(user);
        if(status==StatusRegisterUserEnum.Existed_Username)
            return "redirect:/user/sign-up?exit_username";

        return "redirect:/user/sign-in";
    }

    @GetMapping(value = {"/detail"})
    public String detail(Model model,
                         HttpServletResponse response,
                         HttpServletRequest request,
                         @Valid @ModelAttribute("productname") ProductDTO productName,
                         final Principal principal)
    {
        UserDetailVM vm = new UserDetailVM();
        UserVM userVM = new UserVM();

        String  username = SecurityContextHolder.getContext().getAuthentication().getName();

        User userEntity = userService.findUserByUsername(username);

        userVM.setAddress(userEntity.getAddress());
        userVM.setAvatar(userEntity.getAvatar());
        userVM.setEmail(userEntity.getEmail());
        userVM.setGender(userEntity.getGender());
        userVM.setName(userEntity.getName());
        userVM.setPhoneNumber(userEntity.getPhoneNumber());
        Date sn = userEntity.getDateOfBirth();
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        if(sn!=null){
            String date = simpleDateFormat.format(sn);
            userVM.setDateOfBirth(date);
        }

        Pageable pageable = new PageRequest(0, 50);

        Page<Product> productPage = null;

//        if (productName.getName() != null && !productName.getName().isEmpty()) {
//            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,productName.getName().trim());
//            vm.setKeyWord("Find with key: " + productName.getName());
//        } else {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,null);
//        }


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
            productVM.setName(product.getName());
            productVM.setMainImage(product.getMainImage());
            productVM.setPrice(product.getPrice());
            productVM.setShortDesc(product.getShortDesc());
           // productVM.setCreatedDate(product.getCreatedDate());
            productVM.setCategoryId(product.getCategoryId());
            productVM.setSizeVMList(MyFunction.toSizeVMList(sizeService.getListSizeByProductId(product.getId())));
            productVM.setColorVMList(MyFunction.toColorVMList(colorService.getListColorByProductId(product.getId())));
            productVM.setProductImageVMList(MyFunction.toProductImageVMList(product.getProductImageList()));
            productVMList.add(productVM);
        }

        int productAmount = 0;
        double totalPrice = 0;
        List<CartProductVM> cartProductVMS = new ArrayList<>();

        String  username2 = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity2 = userService.findUserByUsername(username2);
        String guid = getGuid(request);

        DecimalFormat df = new DecimalFormat("####0.00");

        try {
            if(guid != null) {
                Cart cartEntity;
                if(userEntity2==null)
                    cartEntity= cartService.findFirstCartByGuid(guid);
                else
                    cartEntity= cartService.findByUserName(userEntity2.getUserName());

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

        vm.setProductEntityVMList(productEntityVMList);

        vm.setCartProductVMList(cartProductVMS);
        vm.setProductAmount(productAmount);
        vm.setTotalPrice(totalPrice);
        vm.setProductVMList(productVMList);
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());

        model.addAttribute("user", userVM);
        model.addAttribute("vm", vm);
        return "/user-detail";
    }


    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") UserDTO user) {
        try {
            String  username = SecurityContextHolder.getContext().getAuthentication().getName();

            User userEntity = userService.findUserByUsername(username);

            userEntity.setAddress(user.getAddress());
            userEntity.setAvatar(user.getAvatar());
            userEntity.setEmail(user.getEmail());
            userEntity.setGender(user.getGender());
            userEntity.setName(user.getName());
            userEntity.setPhoneNumber(user.getPhoneNumber());
            userEntity.setDateOfBirth(user.getDateOfBirth());
            userService.updateUser(userEntity);

            return "redirect:/user/detail?updateSuccess";
        } catch (Exception e) {
//            logger.error(e.getMessage());
        }
        return "redirect:/user/detail?updateFail";
    }


    @GetMapping("/change-password")
    public String changePassWord(@Valid @ModelAttribute("productname") ProductVM productName,
                                 Model model,
                                 HttpServletResponse response,
                                 HttpServletRequest request,
                                 final Principal principal) {

        ChangePasswordVM changePasswordVM = new ChangePasswordVM();
        ChangePasswordVM2 vm = new ChangePasswordVM2();
        Pageable pageable = new PageRequest(0, 50);

        Page<Product> productPage = null;

//        if (productName.getName() != null && !productName.getName().isEmpty()) {
//            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,productName.getName().trim());
//            vm.setKeyWord("Find with key: " + productName.getName());
//        } else {
        productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,null);
//        }


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
            productVM.setName(product.getName());
            productVM.setMainImage(product.getMainImage());
            productVM.setPrice(product.getPrice());
            productVM.setShortDesc(product.getShortDesc());
          //  productVM.setCreatedDate(product.getCreatedDate());
            productVM.setCategoryId(product.getCategoryId());
            productVMList.add(productVM);
        }


        int productAmount = 0;
        double totalPrice = 0;
        List<CartProductVM> cartProductVMS = new ArrayList<>();

        String  username2 = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity2 = userService.findUserByUsername(username2);
        String guid = getGuid(request);

        DecimalFormat df = new DecimalFormat("####0.00");

        try {
            if(guid != null) {
                Cart cartEntity;
                if(userEntity2==null)
                    cartEntity= cartService.findFirstCartByGuid(guid);
                else
                    cartEntity= cartService.findByUserName(userEntity2.getUserName());

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

        vm.setProductVMList(productVMList);
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());

        model.addAttribute("changePassword", changePasswordVM);
        model.addAttribute("vm", vm);
        return "/change-password";
    }


    @PostMapping("change-password")
    public String changePassWordPost(@Valid @ModelAttribute("changePassword") ChangePasswordVM password) {

        try {
            if(password.getCurrentPassword() != null && password.getNewPassword() != null && password.getConfirmPassword() != null) {
                String  username = SecurityContextHolder.getContext().getAuthentication().getName();

                User userEntity = userService.findUserByUsername(username);

                if(passwordEncoder.matches(password.getCurrentPassword(),userEntity.getPasswordHash()) == true) {
                    if(password.getNewPassword().equals(password.getConfirmPassword())) {
                        userEntity.setPasswordHash(passwordEncoder.encode(password.getNewPassword()));
                        userService.updateUser(userEntity);
                        return "redirect:/user/change-password?success";
                    }
                }
            }
        } catch (Exception e) {
           // logger.error(e.getMessage());
        }
        return "redirect:/user/change-password?fail";

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
