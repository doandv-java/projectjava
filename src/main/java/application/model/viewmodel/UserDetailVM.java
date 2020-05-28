package application.model.viewmodel;

import application.model.viewmodel.Admin.LayoutHeaderAdminVM;

import java.util.List;

public class UserDetailVM {
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<ProductVM> productVMList;
    private UserVM userVM;
    private List<CartProductVM> cartProductVMList;
    private double totalPrice;
    private int productAmount;
    private List<ProductEntityVM> productEntityVMList;

    public List<ProductEntityVM> getProductEntityVMList() {
        return productEntityVMList;
    }

    public void setProductEntityVMList(List<ProductEntityVM> productEntityVMList) {
        this.productEntityVMList = productEntityVMList;
    }

    public List<CartProductVM> getCartProductVMList() {
        return cartProductVMList;
    }

    public void setCartProductVMList(List<CartProductVM> cartProductVMList) {
        this.cartProductVMList = cartProductVMList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public List<ProductVM> getProductVMList() {
        return productVMList;
    }

    public void setProductVMList(List<ProductVM> productVMList) {
        this.productVMList = productVMList;
    }

    public UserVM getUserVM() {
        return userVM;
    }

    public void setUserVM(UserVM userVM) {
        this.userVM = userVM;
    }
}
