package application.controller.web;

import application.constant.RoleIdConstant;
import application.data.model.*;
import application.data.service.*;
import application.model.dto.ProductDTO;
import application.model.viewmodel.*;
import application.model.viewmodel.Admin.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping(path = "/admin")
public class AdminController extends  BaseController {

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private  OrderService orderService;

    @Autowired
    private  DeliveryStatusService deliveryStatusService;


    @Autowired
    private OrderDeliveryStatusService orderDeliveryStatusService;

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public String admin(Model model) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }

        model.addAttribute("vm", "Group FIT");
        return "/admin/report";
    }

    @GetMapping("/supply")
    public String supply(Model model,
                         @Valid @ModelAttribute("supplyname") SupplyVM supplyName) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminSupplyVM vm= new AdminSupplyVM();
        List<Supply> supplyList;
        if (supplyName.getName() != null) {
            supplyList = supplyService.getListSupplyByName(supplyName.getName());
            vm.setKeyWord("Search: "+supplyName.getName());
        } else {
            supplyList = supplyService.getAll();
            vm.setKeyWord("");
        }
        List<SupplyVM> supplyVMList = new ArrayList<>();
        if(supplyList==null)
            vm.setKeyWord("Not found");
        else{
            for (Supply supply : supplyList) {
                SupplyVM supplyVM = new SupplyVM();
                supplyVM.setId(supply.getId());
                supplyVM.setName(supply.getName());
                supplyVM.setShortDesc(supply.getShortDesc());
                supplyVM.setCreatedDate(supply.getCreatedDate());
                supplyVMList.add(supplyVM);
            }
        }

        LayoutHeaderAdminVM layoutHeaderAdminVM=this.getLayoutHeaderAdminVM();
        vm.setLayoutHeaderAdminVM( layoutHeaderAdminVM);
        vm.setSupplyVMList(supplyVMList);
        model.addAttribute("vm", vm);
        return "/admin/admin-supply";
    }

    @GetMapping("/category")
    public String category(Model model,
                           @Valid @ModelAttribute("categoryname") CategoryVM categoryName) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminCategoryVM vm = new AdminCategoryVM();
        List<Category> categoryList;
        if (categoryName.getName() != null) {
            categoryList = categoryService.getListCategoryByName(categoryName.getName());
            vm.setKeyWord("Search: "+categoryName.getName());
        } else {
            categoryList = categoryService.getAll();
            vm.setKeyWord("");

        }
        List<CategoryVM> categoryVMList = new ArrayList<>();
        if(categoryList==null){
            vm.setKeyWord("Not found");
        } else{
            for (Category category: categoryList) {
                CategoryVM categoryVM = new CategoryVM();
                categoryVM.setId(category.getId());
                categoryVM.setName(category.getName());
                categoryVM.setShortDesc(category.getShortDesc());
                categoryVM.setCreatedDate(category.getCreatedDate());
                categoryVMList.add(categoryVM);
            }
        }
        LayoutHeaderAdminVM layoutHeaderAdminVM=this.getLayoutHeaderAdminVM();
        vm.setLayoutHeaderAdminVM( layoutHeaderAdminVM);
        vm.setCategoryVMList(categoryVMList);
        model.addAttribute("vm", vm);
        return "/admin/admin-category";
    }




    @GetMapping("/promotion")
    public String promotion(Model model,
                           @Valid @ModelAttribute("promotionname") PromotionVM promotionName) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminPromotionVM vm = new AdminPromotionVM();
        List<Promotion> promotionList;
        if (promotionName.getName() != null) {
            promotionList = promotionService.getListSupplyByName(promotionName.getName());
            vm.setKeyWord("Search: "+promotionName.getName());
        } else {
            promotionList = promotionService.getAll();
            vm.setKeyWord("");

        }
        List<PromotionVM> promotionVMList = new ArrayList<>();
        if(promotionList==null){
            vm.setKeyWord("Not found");
        } else{
            for (Promotion promotion: promotionList) {
                PromotionVM promotionVM = new PromotionVM();
                promotionVM.setId(promotion.getId());
                promotionVM.setName(promotion.getName());
                promotionVM.setShortDesc(promotion.getShortDesc());
                promotionVM.setCreatedDate(promotion.getCreatedDate());
                promotionVM.setBeginDate(promotion.getBeginDate());
                promotionVM.setEndDate(promotion.getEndDate());
                promotionVM.setDiscount(promotion.getDiscount());
                promotionVMList.add(promotionVM);
            }
        }
        LayoutHeaderAdminVM layoutHeaderAdminVM=this.getLayoutHeaderAdminVM();
        vm.setLayoutHeaderAdminVM( layoutHeaderAdminVM);
        vm.setPromotionVMList(promotionVMList);
        model.addAttribute("vm", vm);
        return "/admin/admin-promotion";
    }



    @GetMapping("/size")
    public String size(Model model,
                         @Valid @ModelAttribute("sizename") SizeVM sizeName) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminSizeVM vm= new AdminSizeVM();
        List<Size> sizeList;
        if (sizeName.getName() != null) {
            sizeList = sizeService.getListSizeByName(sizeName.getName());
            vm.setKeyWord("Search: "+sizeName.getName());
        } else {
            sizeList = sizeService.getAll();
            vm.setKeyWord("");
        }
        List<SizeVM> sizeVMList = new ArrayList<>();
        if(sizeList==null)
            vm.setKeyWord("Not found");
        else{
            for (Size size : sizeList) {
                SizeVM sizeVM = new SizeVM();
                sizeVM.setId(size.getId());
                sizeVM.setName(size.getName());
                sizeVM.setShortDesc(size.getShortDesc());
                sizeVM.setCreatedDate(size.getCreatedDate());
                sizeVMList.add(sizeVM);
            }
        }

        LayoutHeaderAdminVM layoutHeaderAdminVM=this.getLayoutHeaderAdminVM();
        vm.setLayoutHeaderAdminVM( layoutHeaderAdminVM);
        vm.setSizeVMList(sizeVMList);
        model.addAttribute("vm", vm);
        return "/admin/admin-size";
    }


    @GetMapping("/color")
    public String color(Model model,
                       @Valid @ModelAttribute("colorname") ColorVM colorName) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminColorVM vm= new AdminColorVM();
        List<Color> colorList;
        if (colorName.getName() != null) {
            colorList = colorService.getListColorByName(colorName.getName());
            vm.setKeyWord("Search: "+colorName.getName());
        } else {
            colorList = colorService.getAll();
            vm.setKeyWord("");
        }
        List<ColorVM> colorVMList = new ArrayList<>();
        if(colorList==null)
            vm.setKeyWord("Not found");
        else{
            for (Color color : colorList) {
                ColorVM colorVM = new ColorVM();
                colorVM.setId(color.getId());
                colorVM.setName(color.getName());
                colorVM.setShortDesc(color.getShortDesc());
                colorVM.setCreatedDate(color.getCreatedDate());
                colorVMList.add(colorVM);
            }
        }

        LayoutHeaderAdminVM layoutHeaderAdminVM=this.getLayoutHeaderAdminVM();
        vm.setLayoutHeaderAdminVM( layoutHeaderAdminVM);
        vm.setColorVMList(colorVMList);
        model.addAttribute("vm", vm);
        return "/admin/admin-color";
    }


    @GetMapping("/product")
    public String product(Model model,
                          @Valid @ModelAttribute("productname") ProductDTO productName,
                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "8") Integer size
                     //     @RequestParam(name = "categoryId", required = false, defaultValue = "0") Integer categoryId
                     //     @RequestParam(name = "supplyId", required = false, defaultValue = "0") Integer supplyId
    ) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminProductVM vm = new AdminProductVM();


        /**
         * set list categoryVM
         */
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

        List<Promotion> promotionList = promotionService.getAll();
        List<PromotionVM> promotionVMList = new ArrayList<>();

        for(Promotion promotion : promotionList) {
            PromotionVM promotionVM = new PromotionVM();
            promotionVM.setId(promotion.getId());
            promotionVM.setName(promotion.getName());
            promotionVMList.add(promotionVM);
        }


        Pageable pageable = new PageRequest(page, size);

        Page<Product> productPage = null;

        if (productName.getName() != null && !productName.getName().isEmpty()) {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,productName.getName().trim());
            vm.setKeyWord("Search: " + productName.getName());
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
            productVM.setName(product.getName());
            productVM.setMainImage(product.getMainImage());
            productVM.setPrice(product.getPrice());
            productVM.setShortDesc(product.getShortDesc());

            String pattern = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date sn = product.getCreatedDate();
            if(sn!=null){
                String date = simpleDateFormat.format(sn);
                productVM.setCreatedDate(date);
            }

            //productVM.setCreatedDate(product.getCreatedDate());

            productVMList.add(productVM);
        }
        LayoutHeaderAdminVM layoutHeaderAdminVM= this.getLayoutHeaderAdminVM();
        vm.setLayoutHeaderAdminVM(layoutHeaderAdminVM);
        vm.setCategoryVMList(categoryVMList);
        vm.setProductVMList(productVMList);
        vm.setSupplyVMList(supplyVMList);
        vm.setPromotionVMList(promotionVMList);
        if(productVMList.size() == 0) {
            vm.setKeyWord("Not found any product");
        }


        model.addAttribute("vm",vm);
        model.addAttribute("page",productPage);

        return "/admin/admin-product";
    }



    @GetMapping("/ware-house")
    public String warehouse(Model model,
                          @Valid @ModelAttribute("productname") ProductDTO productName,
                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "8") Integer size) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminWareHouseVM vm = new AdminWareHouseVM();


        /**
         * set list categoryVM
         */
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

        List<Color> colorList = colorService.getAll();
        List<ColorVM> colorVMList = new ArrayList<>();

        for(Color color : colorList) {
            ColorVM colorVM = new ColorVM();
            colorVM.setId(color.getId());
            colorVM.setName(color.getName());
            colorVMList.add(colorVM);
        }

        Pageable pageable = new PageRequest(page, size);

        Page<Product> productPage = null;

        if (productName.getName() != null && !productName.getName().isEmpty()) {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,productName.getName().trim());
            vm.setKeyWord("Search: " + productName.getName());
        } else {
            productPage = productService.getListProductByCategoryOrProductNameContaining(pageable,null,null);
        }


        List<WareHouseVM> wareHouseVMSList = new ArrayList<>();

        for(Product product : productPage.getContent()) {
            WareHouseVM wareHouseVM = new WareHouseVM();
            wareHouseVM.setProductId(product.getId());
            wareHouseVM.setName(product.getName());

            Long x=productService.getTotalProducts(product.getId());
//            wareHouseVM.setAmount(productService.getTotalProducts(product.getId()));
            if(x==null)
                wareHouseVM.setAmount(0);
            else
                wareHouseVM.setAmount(x);
            wareHouseVMSList.add(wareHouseVM);
        }
        LayoutHeaderAdminVM layoutHeaderAdminVM= this.getLayoutHeaderAdminVM();
        vm.setLayoutHeaderAdminVM(layoutHeaderAdminVM);
        vm.setCategoryVMList(categoryVMList);
        //vm.setProductVMList(productVMList);
        vm.setSupplyVMList(supplyVMList);
        vm.setColorVMList(colorVMList);
        vm.setSizeVMList(sizeVMList);
       // vm.setPromotionVMList(promotionVMList);
        if(wareHouseVMSList.size() == 0) {
            vm.setKeyWord("Not found any product");
        }

        vm.setWareHouseVMList(wareHouseVMSList);
        model.addAttribute("vm",vm);
        model.addAttribute("page",productPage);

        return "/admin/admin-warehouse";
    }

    @GetMapping("/order")
    public String order(Model model,
                         @Valid @ModelAttribute("customerName") OrderVM customerName,
                        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(name = "size", required = false, defaultValue = "8") Integer size) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminOrderVM vm= new AdminOrderVM();
        Pageable pageable = new PageRequest(page, size);

        Page<Order> orderPage = null;

        if (customerName.getCustomerName() != null && !customerName.getCustomerName().isEmpty()) {
            if(customerName.getDeliveryStatusId()==0)
                orderPage = orderService.getListOrderByCustomerName(pageable,customerName.getCustomerName().trim());
            else
                orderPage = orderService.getListOrderByCustomerName2(pageable,customerName.getCustomerName().trim(),customerName.getDeliveryStatusId());

            vm.setKeyWord("Search: " + customerName.getCustomerName());
        } else {
            if(customerName.getDeliveryStatusId()==0)
                orderPage = orderService.getListOrderByCustomerName(pageable,null);
            else
                orderPage = orderService.getListOrderByCustomerName2(pageable,null,customerName.getDeliveryStatusId());

        }


        List<OrderVM> orderVMList = new ArrayList<>();

        for(Order order : orderPage.getContent()) {
            OrderVM orderVM = new OrderVM();
            DeliveryStatus deliveryStatus = deliveryStatusService.findOne(order.getDeliveryStatusId());
            if(deliveryStatus!=null){
                orderVM.setDeliveryStatus(deliveryStatus.getName());
            }
            orderVM.setCreatedDate(order.getCreatedDate());
            orderVM.setCustomerName(order.getCustomerName());
            orderVM.setPhoneNumber(order.getPhoneNumber());
            orderVM.setPrice(order.getPrice());
            orderVM.setId(order.getId());
            orderVM.setDeliveryStatusId(order.getDeliveryStatusId());
            orderVMList.add(orderVM);
        }

        List<DeliveryStatusVM> deliveryStatusVMList= new ArrayList<>();
        for (DeliveryStatus deliveryStatus: deliveryStatusService.getAll()) {
            DeliveryStatusVM deliveryStatusVM = new DeliveryStatusVM();
            deliveryStatusVM.setId(deliveryStatus.getId());
            deliveryStatusVM.setName(deliveryStatus.getName());
            deliveryStatusVMList.add(deliveryStatusVM);
        }

        LayoutHeaderAdminVM layoutHeaderAdminVM=this.getLayoutHeaderAdminVM();
        vm.setLayoutHeaderAdminVM( layoutHeaderAdminVM);
        vm.setOrderVMList(orderVMList);
        vm.setDeliveryStatusVMS(deliveryStatusVMList);
        model.addAttribute("vm", vm);
        model.addAttribute("page",orderPage);
        return "/admin/admin-order";
    }

    @GetMapping("/order/{orderId}")
    public String orderDetail(Model model,
                              @PathVariable("orderId") Integer orderId) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminInvoiceVM vm= new AdminInvoiceVM();
        Order order = orderService.findOne(orderId);
        double totalPriceOrder=0;
        if(order!=null){

            List<OrderProductVM> orderProductVMS = new ArrayList<>();
            for(OrderProduct orderProduct : order.getListProductOrders()) {
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
                vm.setOrderProductVMList(orderProductVMS);

            }

            OrderVM orderVM = new OrderVM();
            orderVM.setId(order.getId());
            orderVM.setCustomerName(order.getCustomerName());
            orderVM.setEmail(order.getEmail());
            orderVM.setAddress(order.getAddress());
            orderVM.setPrice(order.getPrice());
            orderVM.setPhoneNumber(order.getPhoneNumber());
            orderVM.setShipPrice(order.getShipPrice());
            orderVM.setCreatedDate(order.getCreatedDate());
            vm.setOrderVM(orderVM);
        }
        vm.setTotalPrice(order.getPrice()+order.getShipPrice());
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        model.addAttribute("vm", vm);
        return "/admin/admin-invoice";
    }


    @GetMapping("/invoice-print/{orderId}")
    public String invoicePrint(Model model,
                              @PathVariable("orderId") Integer orderId) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminInvoiceVM vm= new AdminInvoiceVM();
        Order order = orderService.findOne(orderId);
        double totalPriceOrder=0;
        if(order!=null){

            List<OrderProductVM> orderProductVMS = new ArrayList<>();
            for(OrderProduct orderProduct : order.getListProductOrders()) {
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
                vm.setOrderProductVMList(orderProductVMS);

            }

            OrderVM orderVM = new OrderVM();
            orderVM.setId(order.getId());
            orderVM.setCustomerName(order.getCustomerName());
            orderVM.setEmail(order.getEmail());
            orderVM.setAddress(order.getAddress());
            orderVM.setPrice(order.getPrice());
            orderVM.setPhoneNumber(order.getPhoneNumber());
            orderVM.setShipPrice(order.getShipPrice());
            orderVM.setCreatedDate(order.getCreatedDate());
            vm.setOrderVM(orderVM);
        }
        vm.setTotalPrice(order.getPrice()+order.getShipPrice());
        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        model.addAttribute("vm", vm);
        return "/admin/admin-invoice-print";
    }

    @GetMapping("/mail") ///{thetype}
    public String mail(Model model,
                        @Valid @ModelAttribute("messageVM") MessageVM messageVM,
                       //@PathVariable("thetype") String thetype,
                        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(name = "size", required = false, defaultValue = "15") Integer size) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminMessageVM vm= new AdminMessageVM();
        Pageable pageable = new PageRequest(page, size);

        Page<Message> messagePage = null;

        if (messageVM.getKeyword() != null && !messageVM.getKeyword().isEmpty()) {
            messagePage = messageService.getAllSearch(pageable,messageVM.getKeyword().trim());
            vm.setKeyWord("Search: " + messageVM.getKeyword());
        } else {
            messagePage = messageService.getAllSearch(pageable,null);
        }


        List<MessageVM> messageVMList = new ArrayList<>();

        for(Message message : messagePage.getContent()) {
            MessageVM item = new MessageVM();
            item.setCreatedDate(message.getCreatedDate());
            item.setEmail(message.getEmail());
            item.setContent(message.getContent());
            item.setTitle(message.getTitle());
            item.setId(message.getId());
            item.setDelete(message.getTrash());
            item.setImportant(message.getImportant());
            item.setSent(message.getSent());
            item.setStatus(message.getStatus());

            messageVMList.add(item);
        }

        LayoutHeaderAdminVM layoutHeaderAdminVM=this.getLayoutHeaderAdminVM();
        vm.setLayoutHeaderAdminVM( layoutHeaderAdminVM);
        vm.setMessageVMList(messageVMList);
        model.addAttribute("vm", vm);
        model.addAttribute("page",messagePage);
        return "/admin/admin-mail";
    }

    @GetMapping("/mail/detail/{messageId}") ///{thetype}
    public String detail(Model model,
                       @Valid @ModelAttribute("messageVM") MessageVM messageVM,
                       @PathVariable("messageId") Integer messageId,
                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                       @RequestParam(name = "size", required = false, defaultValue = "15") Integer size) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminMessageDetailVM vm= new AdminMessageDetailVM();
        Message message = messageService.findOne(messageId);
        MessageVM messageVM2 = new MessageVM();
        if(message!=null){
            messageVM2.setTitle(message.getTitle());
            messageVM2.setEmail(message.getEmail());
            messageVM2.setContent(message.getContent());
            messageVM2.setCreatedDate(message.getCreatedDate());
            messageVM2.setId(message.getId());
            messageVM2.setStatus(message.getStatus());
            if(message.getStatus()==1){
                message.setStatus(0);
                messageService.update(message);
            }
        }

        LayoutHeaderAdminVM layoutHeaderAdminVM=this.getLayoutHeaderAdminVM();
        vm.setLayoutHeaderAdminVM( layoutHeaderAdminVM);
        vm.setMessageVM(messageVM2);
        model.addAttribute("vm", vm);
        return "/admin/admin-mail-detail";
    }


    @GetMapping("/report") ///{thetype}
    public String report(Model model) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminChartVM vm= new AdminChartVM();

        List<ChartLabelDataVM> countProductByCategory= categoryService.countProductByCategory();

        ChartVM chartVM= new ChartVM();
        chartVM.setLabelDataVMList(countProductByCategory);
        vm.setCountProductByCategory(chartVM);
        if(orderService.totalPriceOfWeek()!=null)
            vm.setTotalPriceOfWeek(orderService.totalPriceOfWeek());
        else
            vm.setTotalPriceOfWeek(0);

        if(orderService.totalOrderOfWeek()!=null)
            vm.setTotalOrderOfWeek(orderService.totalOrderOfWeek());
        else
            vm.setTotalPriceOfWeek(0);

        if(messageService.getTotalUnread()!=null)
            vm.setUnread(messageService.getTotalUnread());
        else
            vm.setUnread(0);

        List<ChartLabelDataVM2> temp = orderService.profitInWeek();
        List<ChartLabelDataVM> listTemp = new ArrayList<>();
        ChartVM chartVM1 = new ChartVM();
        for (ChartLabelDataVM2 item : temp ) {
            ChartLabelDataVM chartLabelDataVM = new ChartLabelDataVM();
            if(item.getData()!=null){
                chartLabelDataVM.setData(item.getData().longValue());
            } else{
                chartLabelDataVM.setData(0);
            }

            LocalDateTime ldt = LocalDateTime.ofInstant(item.getLabel().toInstant(),
                    ZoneId.systemDefault());

            chartLabelDataVM.setLabel(DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH).format(ldt).toString());
            listTemp.add(chartLabelDataVM);
        }
        chartVM1.setLabelDataVMList(listTemp);

        vm.setProfitInWeek(chartVM1);

        vm.setLayoutHeaderAdminVM(this.getLayoutHeaderAdminVM());
        model.addAttribute("vm",vm);
        return "/admin/admin-report";
    }


    @GetMapping("/user")
    public String user(Model model,
                        @Valid @ModelAttribute("userName") UserVM userName,
                        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(name = "size", required = false, defaultValue = "8") Integer size) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            Role role = roleService.getRoleByUser(userEntity.getId());
            if (role.getId() != RoleIdConstant.Role_Admin) {
                return "redirect:/user/sign-in";
            }
        } else{
            return "redirect:/user/sign-in";
        }
        AdminUserVM vm= new AdminUserVM();
        Pageable pageable = new PageRequest(page, size);

        Page<User> userPage = null;

        if (!Strings.isEmpty(userName.getName())) {
            userPage = userService.getList(pageable,userName.getUserName().trim());
            vm.setKeyWord("Search: " + userName.getUserName());
        } else {
            userPage = userService.getList(pageable,null);
        }


        List<UserVM> userVMList = new ArrayList<>();

        for(User user: userPage.getContent()) {
            UserVM userVM = new UserVM();
            userVM.setId(user.getId());
            userVM.setName(user.getName());
            userVM.setGender(user.getGender());
            userVM.setUserName(user.getUserName());
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date sn = user.getDateOfBirth();
            if(sn!=null){
                String date = simpleDateFormat.format(sn);
                userVM.setDateOfBirth(date);
            }
            userVM.setEmail(user.getEmail());
            userVM.setRoleId(roleService.getRoleByUser(user.getId()).getId());
           userVMList.add(userVM);
        }

        List<RoleVM> roleVMArrayList= new ArrayList<>();
        for (Role role: roleService.getListAllRole()) {
            RoleVM roleVM = new RoleVM();
            roleVM.setId(role.getId());
            roleVM.setName(role.getName());
            roleVMArrayList.add(roleVM);
        }

        LayoutHeaderAdminVM layoutHeaderAdminVM=this.getLayoutHeaderAdminVM();
        vm.setLayoutHeaderAdminVM( layoutHeaderAdminVM);
        vm.setUserVMList(userVMList);
        vm.setRoleVMList(roleVMArrayList);
        model.addAttribute("vm", vm);
        model.addAttribute("page",userPage);
        return "/admin/admin-user";
    }
}
