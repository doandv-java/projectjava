package application.controller.web;

import application.data.model.*;
import application.data.service.*;
import application.extension.DELIVERYSTATUS;
import application.extension.MyFunction;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.OrderDTO;
import application.model.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.crypto.Data;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/order")
public class OrderController extends BaseController {
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

    @Autowired
    OrderService orderService;

    @Autowired
    DeliveryStatusService deliveryStatusService;

    @Autowired
    OrderDeliveryStatusService orderDeliveryStatusService;

    @Autowired
    MessageService messageService;
    @GetMapping("/checkout")
    public String checkoutA(Model model,
                           @Valid @ModelAttribute("productname") ProductVM productName,

                           HttpServletResponse response,
                           HttpServletRequest request,
                           final Principal principal) {
        ProductDetailVM vm = new ProductDetailVM();
        List<Category> categoryList = categoryService.getAll();
        List<CategoryVM> categoryVMList = new ArrayList<>();
        OrderVM order= new OrderVM();
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
        int totalStar=0, amountStar=0;
        List<RateVM> rateVMList = new ArrayList<>();

        if(amountStar==0)
            vm.setTotalRate(0);
        else
            vm.setRateAvg(Math.round(totalStar/amountStar));
        Pageable pageable = new PageRequest(0, 50);

        Page<Product> productPage = null;

        if (productName.getName() != null && !productName.getName().isEmpty()) {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,productName.getName().trim());
            vm.setKeyWord("Find with key: " + productName.getName());
        } else {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,null);
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

        vm.setRateVMList(rateVMList);

        vm.setCategoryVMList(categoryVMList);
        vm.setColorVMList(colorVMList);
        vm.setProductVMList(productVMList);
        vm.setSizeVMList(sizeVMList);
        vm.setSupplyVMList(supplyVMList);
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        model.addAttribute("order",order);
        model.addAttribute("vm",vm);
//        CheckoutVM vm = new CheckoutVM();
//        int productAmount = 0;
//
//        List<CartProductVM> cartProductVMS = new ArrayList<>();
//
//        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User userEntity = userService.findUserByUsername(username);
//        String guid = getGuid(request);
//
//        DecimalFormat df = new DecimalFormat("####0.00");
//
//        try {
//            if(guid != null) {
//                Cart cartEntity;
//                if(userEntity==null)
//                    cartEntity= cartService.findFirstCartByGuid(guid);
//                else
//                    cartEntity= cartService.findByUserName(userEntity.getUserName());
//
//                if(cartEntity != null) {
//                    productAmount = cartEntity.getListCartProducts().size();
//                    for(CartProduct cartProduct : cartEntity.getListCartProducts()) {
//                        CartProductVM cartProductVM = new CartProductVM();
//                        cartProductVM.setId(cartProduct.getId());
//                        cartProductVM.setName(cartProduct.getProductEntity().getProduct().getName());
//                        cartProductVM.setProductId(cartProduct.getProductEntity().getProductId());
//                        cartProductVM.setProductName(cartProduct.getProductEntity().getProduct().getName());
//                        cartProductVM.setMainImage(cartProduct.getProductEntity().getProduct().getMainImage());
//                        cartProductVM.setAmount(cartProduct.getAmount());
//                        cartProductVM.setColorName(cartProduct.getProductEntity().getColor().getName());
//                        cartProductVM.setSizeName(cartProduct.getProductEntity().getSize().getName());
//                        cartProductVM.setProductEntityId(cartProduct.getProductEntityId());
//                        cartProductVM.setPrice(cartProduct.getProductEntity().getProduct().getPrice());
//                        cartProductVMS.add(cartProductVM);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            //logger.error(e.getMessage());
//        }
//        vm.setProductAmount(productAmount);
//        vm.setCartProductVMList(cartProductVMS);
//        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        return "/checkout";
    }
    @PostMapping("/checkout")
    public String checkout(@Valid @ModelAttribute("order") OrderVM orderVM,
                                  @Valid @ModelAttribute("productname") ProductVM productName,
                                  HttpServletResponse response,
                                  HttpServletRequest request,
                                  final Principal principal) {
        Order order = new Order();
        DataApiResult data = new DataApiResult();

        boolean flag = false;

        Cookie cookie[] = request.getCookies();

        String guid = null;

        if(cookie!=null) {
            for(Cookie c : cookie) {
                if(c.getName().equals("guid")) {
                    flag = true;
                    guid = c.getValue();
                }
            }
        }

        if(flag == true) {

            double totalPrice = 0;

            if(principal != null) {
                String  username = SecurityContextHolder.getContext().getAuthentication().getName();
                order.setUserName(username);
            }

            order.setGuid(guid);
            order.setCustomerName(orderVM.getCustomerName());
            order.setEmail(orderVM.getEmail());
            order.setAddress(orderVM.getAddress());
            order.setPhoneNumber(orderVM.getPhoneNumber());
            order.setCreatedDate(new Date());
            order.setDeliveryStatusId(1);
            order.setCreatedDateShow(new Date());

            Cart cartEntity = cartService.findFirstCartByGuid(guid);
            if(cartEntity != null) {
                List<OrderProduct> orderProducts = new ArrayList<>();
                for (CartProduct cartProduct : cartEntity.getListCartProducts()) {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrder(order);
                    orderProduct.setProductEntity(cartProduct.getProductEntity());
                    orderProduct.setAmount(cartProduct.getAmount());
                    ProductEntity current = productEntityService.findOne(cartProduct.getProductEntity().getId());
                    if(cartProduct.getAmount()>current.getAmount()){
                        Message message = new Message();
                        message.setEmail("system");
                        message.setStatus(1);
                        message.setCreatedDate(new Date());
                        message.setTitle("Not Enough Amount");
                        message.setContent(order.toString());
                        messageService.update(message);
                    } else{
                        current.setAmount(current.getAmount()-cartProduct.getAmount());
                    }
                    productEntityService.update(current);
                    double price = cartProduct.getAmount() * cartProduct.getProductEntity().getProduct().getPrice();
                    totalPrice += price;

                    orderProduct.setPrice(price);

                    orderProducts.add(orderProduct);
                }

                order.setListProductOrders(orderProducts);
                order.setPrice(totalPrice);
                if(totalPrice>1000000){
                    order.setShipPrice(0);
                } else{
                    order.setShipPrice(49500);
                }
                orderService.addNewOrder(order);
                OrderDeliveryStatus orderDeliveryStatus = new OrderDeliveryStatus();
                orderDeliveryStatus.setCreatedDate(new Date());
                orderDeliveryStatus.setOrder(order);
                orderDeliveryStatus.setDeliveryStatus(deliveryStatusService.findOne(DELIVERYSTATUS.PROCESSING));
                orderDeliveryStatusService.add(orderDeliveryStatus);

                cartService.deleteCart(cartEntity.getId());
            }
            data.setMessage("OK");
            data.setSuccess(true);
            return "redirect:/order/history";

        }
        data.setMessage("Fail");
        data.setSuccess(false);
//        return data;
        return "redirect:/order/history";
    }


    @GetMapping("/history")
    public String orderHistory(Model model,
                               @Valid @ModelAttribute("productname") ProductVM productName,
                               HttpServletResponse response,
                               HttpServletRequest request,
                               final Principal principal) {

        OrderHistoryVM vm = new OrderHistoryVM();


        List<Category> categoryList = categoryService.getAll();
        List<CategoryVM> categoryVMList = new ArrayList<>();
        for(Category category : categoryList) {
            CategoryVM categoryVM = new CategoryVM();
            categoryVM.setId(category.getId());
            categoryVM.setName(category.getName());
            categoryVMList.add(categoryVM);
        }
        List<OrderVM> orderVMS = new ArrayList<>();

        Cookie[] cookie = request.getCookies();

        String guid = null;
        boolean flag = false;
        DecimalFormat df = new DecimalFormat("####0.00");

        List<Order> orderEntityList = null;

        if(principal != null) {
            String  username = SecurityContextHolder.getContext().getAuthentication().getName();
            orderEntityList = orderService.findOrderByGuidOrUserName(null,username);
        } else {
            if(cookie != null) {
                for(Cookie c : cookie) {
                    if(c.getName().equals("guid")) {
                        flag = true;
                        guid = c.getValue();
                    }
                }
                if(flag == true) {
                    orderEntityList = orderService.findOrderByGuidOrUserName(guid,null);
                }
            }
        }

        if(orderEntityList != null) {
            for(Order order : orderEntityList) {
                OrderVM orderVM = new OrderVM();
                orderVM.setId(order.getId());
                orderVM.setCustomerName(order.getCustomerName());
                orderVM.setEmail(order.getEmail());
                orderVM.setAddress(order.getAddress());
                orderVM.setPhoneNumber(order.getPhoneNumber());
                orderVM.setPrice((order.getPrice()));
                orderVM.setCreatedDate(order.getCreatedDate());

                orderVMS.add(orderVM);
            }
        }
        if(orderEntityList.size()==0){
            vm.setFound(false);
        } else{
            vm.setFound(true);
        }
        int productAmount = 0;
        double totalPrice = 0;
        List<CartProductVM> cartProductVMS = new ArrayList<>();

        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);


        try {
            if(guid != null ||userEntity!=null ) {
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
        vm.setTotalPrice(totalPrice);
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setProductAmount(productAmount);
        vm.setCartProductVMList(cartProductVMS);
        vm.setOrderVMList(orderVMS);
        vm.setCategoryVMList(categoryVMList);
        model.addAttribute("vm",vm);
        return "/order-history";
    }


    @GetMapping("/history/{orderId}")
    public String getOrderDetail(Model model,
                                 @Valid @ModelAttribute("productname") ProductVM productName,
                                 @PathVariable("orderId") int orderId,
                                 HttpServletResponse response,
                                 HttpServletRequest request,
                                 final Principal principal) {

        OrderDetailVM vm = new OrderDetailVM();

        DecimalFormat df = new DecimalFormat("####0.00");

        double totalPriceOrder = 0;

        List<OrderProductVM> orderProductVMS = new ArrayList<>();

        Order orderEntity = orderService.findOne(orderId);

        if(orderEntity != null) {
            vm.setDeliveryStatusId(orderEntity.getDeliveryStatusId());
            for(OrderProduct orderProduct : orderEntity.getListProductOrders()) {
                OrderProductVM orderProductVM = new OrderProductVM();

                orderProductVM.setProductId(orderProduct.getProductEntity().getProduct().getId());
                orderProductVM.setMainImage(orderProduct.getProductEntity().getProduct().getMainImage());
                orderProductVM.setAmount(orderProduct.getAmount());
                orderProductVM.setName(orderProduct.getProductEntity().getProduct().getName());
                orderProductVM.setColorName(orderProduct.getProductEntity().getColor().getName());
                orderProductVM.setSizeName(orderProduct.getProductEntity().getSize().getName());
                orderProductVM.setPrice((orderProduct.getProductEntity().getProduct().getPrice()));
                orderProductVM.setTotalPrice(orderProduct.getProductEntity().getProduct().getPrice()*orderProduct.getAmount());
                totalPriceOrder += orderProduct.getPrice();

                orderProductVMS.add(orderProductVM);
            }
        }
        int productAmount = 0;
        double totalPrice = 0;
        List<CartProductVM> cartProductVMS = new ArrayList<>();

        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        String guid = getGuid(request);
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

        List<Product> productList = productService.getHotProduct();
        List<ProductVM> productVMList = new ArrayList<>();

        for(Product product : productList) {
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
        vm.setProductVMList(productVMList);
        vm.setProductAmount(productAmount);
        vm.setTotalPrice(totalPrice);
        vm.setCartProductVMList(cartProductVMS);
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setOrderProductVMS(orderProductVMS);
        vm.setTotalPriceOrder((totalPriceOrder));
        vm.setTotalProduct(orderProductVMS.size());

        model.addAttribute("vm",vm);

        return "/order-detail";
    }

    @PostMapping("/cancel/{orderId}")
    public  @ResponseBody  BaseApiResult cancelOrder(Model model,
                                     @Valid @ModelAttribute("productname") ProductVM productName,
                                     @PathVariable("orderId") int orderId,
                                     HttpServletResponse response,
                                     HttpServletRequest request,
                                     final Principal principal) {

        BaseApiResult result = new BaseApiResult();
        Order order = orderService.findOne(orderId);
        if(order!=null){
            try{
                order.setDeliveryStatusId(DELIVERYSTATUS.CANCEL);
                orderService.update(order);

                OrderDeliveryStatus orderDeliveryStatus = new OrderDeliveryStatus();
                orderDeliveryStatus.setCreatedDate(new Date());
                orderDeliveryStatus.setDeliveryStatus(deliveryStatusService.findOne(DELIVERYSTATUS.CANCEL));
                orderDeliveryStatus.setOrder(order);
                orderDeliveryStatusService.add(orderDeliveryStatus);

                Message message = new Message();
                message.setTitle("Cancel Order");
                message.setEmail("system");
                message.setCreatedDate(new Date());
                message.setContent(order.toString());
                messageService.update(message);
                result.setMessage("Canceled Successfully");
                result.setSuccess(true);
                return result;
            } catch (Exception e){
                result.setMessage(e.getMessage());
                result.setSuccess(false);
                return result;
            }
        }
        result.setMessage("Order Not Found");
        result.setSuccess(false);
        return result;
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

    @PostMapping(value="/changeStatus")
    public @ResponseBody DataApiResult changeStatus(@Valid @RequestBody OrderDTO dto
                                                    ){
        DataApiResult result = new DataApiResult();
        Order order = orderService.findOne(dto.getId());
        if(order!=null){
           // if(order.getDeliveryStatusId()== DELIVERYSTATUS.SHIPPED ||
           //         order.getDeliveryStatusId()== DELIVERYSTATUS.CANCEL||
             //       order.getDeliveryStatusId()>dto.getStatus()){
             //   result.setMessage("Update Failed");
             //   result.setSuccess(false);
             //   return result;
           // }
            order.setDeliveryStatusId(dto.getStatus());
            orderService.update(order);

            OrderDeliveryStatus orderDeliveryStatus = new OrderDeliveryStatus();
            orderDeliveryStatus.setOrder(order);
            orderDeliveryStatus.setDeliveryStatus(deliveryStatusService.findOne(dto.getStatus()));
            orderDeliveryStatus.setCreatedDate(new Date());

            orderDeliveryStatusService.add(orderDeliveryStatus);
            result.setMessage("Updated Successfully");
            result.setSuccess(true);
            return result;
        }
        result.setMessage("Order Not Found");
        result.setSuccess(false);
        return result;
    }

}
