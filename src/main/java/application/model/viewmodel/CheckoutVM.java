package application.model.viewmodel;

import application.model.viewmodel.Admin.LayoutHeaderAdminVM;
import application.model.viewmodel.Admin.LayoutHeaderVM;

import java.util.List;

public class CheckoutVM {

    private String keyWord;
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<CartProductVM> cartProductVMList;
    private int productAmount;

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public List<CartProductVM> getCartProductVMList() {
        return cartProductVMList;
    }

    public void setCartProductVMList(List<CartProductVM> cartProductVMList) {
        this.cartProductVMList = cartProductVMList;
    }
}
