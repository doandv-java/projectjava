package application.controller.web;

import application.data.model.*;
import application.data.service.*;
import application.model.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping(path = "/tracking")
public class TrackingController extends  BaseController{
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

    @GetMapping("")
    public String tracking(Model model,
                               @Valid @ModelAttribute("productname") ProductVM productname,
                               @Valid @ModelAttribute("orderId") OrderVM orderId,
                               @Valid @ModelAttribute("phone") String phone,
                               HttpServletResponse response,
                               HttpServletRequest request,
                               final Principal principal) {
        TrackingVM vm = new TrackingVM();

        List<OrderVM> orderVMS = new ArrayList<>();

        Cookie[] cookie = request.getCookies();

        String guid = null;
        boolean flag = false;
        DecimalFormat df = new DecimalFormat("####0.00");

        List<Order> orderEntityList = null;

        if(phone != null && !phone.isEmpty()){
            orderEntityList= orderService.findOrderByPhoneNumber(phone);

        } else {

            if(principal != null) {
                String  username = SecurityContextHolder.getContext().getAuthentication().getName();
                orderEntityList = orderService.findOrderByGuidOrUserName2(null,username);
            } else {
                if(cookie != null) {
                    for(Cookie c : cookie) {
                        if(c.getName().equals("guid")) {
                            flag = true;
                            guid = c.getValue();
                        }
                    }
                    if(flag) {
                        orderEntityList = orderService.findOrderByGuidOrUserName2(guid,null);
                    }
                }
            }
        }

        List<OrderTrackingVM> orderTrackingVMList = new ArrayList<>();
        if(orderEntityList != null) {
            for (Order order : orderEntityList) {
                OrderTrackingVM orderTrackingVM = new OrderTrackingVM();
                orderTrackingVM.setId(order.getId());
                orderTrackingVM.setCustomerName(order.getCustomerName());
                orderTrackingVM.setEmail(order.getEmail());
                orderTrackingVM.setAddress(order.getAddress());
                orderTrackingVM.setPhoneNumber(order.getPhoneNumber());
                orderTrackingVM.setPrice(String.format("%,.0f", order.getPrice()));
                orderTrackingVM.setCreatedDate(order.getCreatedDate());
                orderTrackingVM.setDeliveryStatusId(order.getDeliveryStatusId());
                double totalPriceOrder = 0;
                List<OrderProductVM> orderProductVMList = new ArrayList<>();
                for (OrderProduct orderProduct : order.getListProductOrders()) {
                    OrderProductVM orderProductVM = new OrderProductVM();

                    orderProductVM.setProductId(orderProduct.getProductEntity().getProduct().getId());
                    orderProductVM.setMainImage(orderProduct.getProductEntity().getProduct().getMainImage());
                    orderProductVM.setAmount(orderProduct.getAmount());
                    orderProductVM.setName(orderProduct.getProductEntity().getProduct().getName());
                    orderProductVM.setColorName(orderProduct.getProductEntity().getColor().getName());
                    orderProductVM.setSizeName(orderProduct.getProductEntity().getSize().getName());
                    orderProductVM.setPrice((orderProduct.getProductEntity().getProduct().getPrice()));
                    orderProductVM.setTotalPrice(orderProduct.getProductEntity().getProduct().getPrice() * orderProduct.getAmount());
                    totalPriceOrder += orderProduct.getPrice();

                    orderProductVMList.add(orderProductVM);
                }
//                orderProductVMList.
                orderTrackingVM.setOrderProductVMList(orderProductVMList);
                orderTrackingVMList.add(orderTrackingVM);
            }
        }

            if(orderEntityList.size()!=0){
                vm.setKeyWord("OK");
            } else{
                vm.setKeyWord("Can not find the order");
            }

        //set cart
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

        //end set cart
        vm.setTotalPrice( String.format("%,.0f", totalPrice));
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        vm.setProductAmount(productAmount);
        vm.setCartProductVMList(cartProductVMS);
        vm.setOrderTrackingVMList(orderTrackingVMList);
//        vm.setOrderVMList(orderVMS);
//        vm.setCategoryVMList(categoryVMList);
        model.addAttribute("vm",vm);
        return "/tracking";
    }
}
