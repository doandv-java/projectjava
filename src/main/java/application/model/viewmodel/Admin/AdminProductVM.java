package application.model.viewmodel.Admin;

import application.model.viewmodel.*;

import java.util.List;

public class AdminProductVM {

    private List<SupplyVM> supplyVMList;
    private List<PromotionVM> promotionVMList;
    private List<ProductVM> productVMList;

    private List<CategoryVM> categoryVMList;
    private String keyWord;
    private LayoutHeaderAdminVM layoutHeaderAdminVM;

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

    public List<SupplyVM> getSupplyVMList() {
        return supplyVMList;
    }

    public void setSupplyVMList(List<SupplyVM> supplyVMList) {
        this.supplyVMList = supplyVMList;
    }

    public List<PromotionVM> getPromotionVMList() {
        return promotionVMList;
    }

    public void setPromotionVMList(List<PromotionVM> promotionVMList) {
        this.promotionVMList = promotionVMList;
    }
}
