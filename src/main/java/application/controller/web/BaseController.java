package application.controller.web;

import application.constant.CompanyConstant;
import application.constant.RoleIdConstant;
import application.data.model.Cart;
import application.data.model.Role;
import application.data.model.User;
import application.data.model.UserRole;
import application.data.service.CartService;
import application.data.service.RoleService;
import application.data.service.UserRoleService;
import application.data.service.UserService;
import application.model.viewmodel.Admin.LayoutHeaderAdminVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.UUID;

public class BaseController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    public void checkCookie(HttpServletResponse response,
                            HttpServletRequest request,
                            final Principal principal) {
        Cookie cookie[] = request.getCookies();


        if(principal!= null) {
            String  username = SecurityContextHolder.getContext().getAuthentication().getName();
            Cart cartEntity = cartService.findByUserName(username);
            if(cartEntity != null) {
                Cookie cookie1 = new Cookie("guid",cartEntity.getGuid());
                cookie1.setPath("/");
                response.addCookie(cookie1);
            } else {
                UUID uuid = UUID.randomUUID();
                String guid = uuid.toString();
                Cart cart = new Cart();
                cart.setGuid(guid);
                cart.setUserName(username);
                cartService.addNewCart(cart);

                Cookie cookie2 = new Cookie("guid",guid);
                cookie2.setPath("/");
                response.addCookie(cookie2);
            }
        } else {
            boolean flag2 = true;

            String guid = null;

            if(cookie!=null) {
                for(Cookie c : cookie) {
                    if(c.getName().equals("guid")) {
                        flag2 = false;
                        guid = c.getValue();
                    }
                }
            }

            if(flag2) {
                UUID uuid = UUID.randomUUID();
                String guid2 = uuid.toString();
                Cart cart2 = new Cart();
                cart2.setGuid(guid2);
                cartService.addNewCart(cart2);

                Cookie cookie3 = new Cookie("guid",guid2);
                cookie3.setPath("/");
                cookie3.setMaxAge(60*60*24);
                response.addCookie(cookie3);

            } else {
                Cart cartEntity = cartService.findFirstCartByGuid(guid);
                if(cartEntity == null) {
                    Cart cart3 = new Cart();
                    cart3.setGuid(guid);
                    cartService.addNewCart(cart3);
                }
            }

        }

    }


    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {

        LayoutHeaderAdminVM vm = new LayoutHeaderAdminVM();

        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        if(userEntity!=null) {
            vm.setUser(true);
            Role role = roleService.getRoleByUser(userEntity.getId());
            if(role.getId()== RoleIdConstant.Role_Admin){
                vm.setAdmin(true);
            } else{
                vm.setAdmin(false);
            }
            vm.setUserName(username);
            vm.setId(userEntity.getId());
            if(userEntity.getAvatar() != null) {
                vm.setAvatar(userEntity.getAvatar());
            } else {
                vm.setAvatar("https://aets.org.es/wp-content/uploads/2014/12/omita-el-icono-del-perfil-avatar-placeholder-gris-de-la-foto-99724602.jpg");
            }
        }

        return vm;

    }

}
