package application.controller.api;

import application.data.model.Product;
import application.data.model.Role;
import application.data.model.User;
import application.data.model.UserRole;
import application.data.service.RoleService;
import application.data.service.UserRoleService;
import application.data.service.UserService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.ProductDTO;
import application.model.viewmodel.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(path = "/api/user")
public class UserApiController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;

    @PostMapping(value = "/check")
    public BaseApiResult createProduct(@RequestBody String userName){
        DataApiResult result = new DataApiResult();

        try {
            User user= userService.findUserByUsername(userName);
            if(user==null){
                result.setMessage("OK");
                result.setSuccess(true);
            } else {
                result.setMessage("NOT OK");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/changeRole")
    public BaseApiResult changeRole(@RequestBody UserVM userVM){
        DataApiResult result = new DataApiResult();

        try {
            User user= userService.findOne(userVM.getId());
            Role role = roleService.findOne(userVM.getRoleId());

            if(user!=null&&role!=null){
                UserRole userRole = userRoleService.getUserRoleByUser(user.getId());
                userRole.setRoleId(userVM.getRoleId());
                userRoleService.update(userRole);
                result.setMessage("Changed Successfully");
                result.setSuccess(true);
            } else {
                result.setMessage("Fail to save");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @GetMapping("/delete/{userId}")
    public @ResponseBody  BaseApiResult deleteUser(@PathVariable int userId) {
        BaseApiResult result= new BaseApiResult();
        try {
            User user = userService.findOne(userId);
            user.setStatus(0);
            userService.updateUser(user);
            result.setSuccess(true);
            result.setMessage("Deleted Successfully");
            return result;

        }catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

}
