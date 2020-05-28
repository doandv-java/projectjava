package application.model.viewmodel;

import application.model.viewmodel.Admin.LayoutHeaderAdminVM;
import application.model.viewmodel.Admin.LayoutHeaderVM;

import java.util.List;

public  class HotProductVM {
    private List<ProductVM> productVMList;
    private List<CategoryVM> categoryVMList;
    private List<ColorVM> colorVMList;
    private List<SizeVM> sizeVMList;
    private List<SupplyVM> supplyVMList;
    private LayoutHeaderVM layoutHeaderVM;
    private String keyWord;
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<CartProductVM> cartProductVMList;
    private double totalPrice;
    private int productAmount;

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
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

    public List<ProductVM> getProductVMList() {
        return productVMList;
    }

    public void setProductVMList(List<ProductVM> productVMList) {
        this.productVMList = productVMList;
    }

    public List<CategoryVM> getCategoryVMList() {
        return categoryVMList;
    }

    public void setCategoryVMList(List<CategoryVM> categoryVMList) {
        this.categoryVMList = categoryVMList;
    }

    public List<ColorVM> getColorVMList() {
        return colorVMList;
    }

    public void setColorVMList(List<ColorVM> colorVMList) {
        this.colorVMList = colorVMList;
    }

    public List<SizeVM> getSizeVMList() {
        return sizeVMList;
    }

    public void setSizeVMList(List<SizeVM> sizeVMList) {
        this.sizeVMList = sizeVMList;
    }

    public List<SupplyVM> getSupplyVMList() {
        return supplyVMList;
    }

    public void setSupplyVMList(List<SupplyVM> supplyVMList) {
        this.supplyVMList = supplyVMList;
    }

    public LayoutHeaderVM getLayoutHeaderVM() {
        return layoutHeaderVM;
    }

    public void setLayoutHeaderVM(LayoutHeaderVM layoutHeaderVM) {
        this.layoutHeaderVM = layoutHeaderVM;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
