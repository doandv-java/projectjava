package application.model.viewmodel;

import application.model.viewmodel.Admin.LayoutHeaderAdminVM;

import java.util.List;

public class TrackingVM {
    LayoutHeaderAdminVM layoutHeaderAdminVM;
    List<OrderTrackingVM> orderTrackingVMList;
    private  int productAmount;
    private List<CartProductVM> cartProductVMList;
    private String totalPrice;
    private  String keyWord;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartProductVM> getCartProductVMList() {
        return cartProductVMList;
    }

    public void setCartProductVMList(List<CartProductVM> cartProductVMList) {
        this.cartProductVMList = cartProductVMList;
    }

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public List<OrderTrackingVM> getOrderTrackingVMList() {
        return orderTrackingVMList;
    }

    public void setOrderTrackingVMList(List<OrderTrackingVM> orderTrackingVMList) {
        this.orderTrackingVMList = orderTrackingVMList;
    }
}
