package application.model.viewmodel.Admin;

import application.model.viewmodel.PromotionVM;

import java.util.List;

public class AdminPromotionVM {
    private String keyWord;
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<PromotionVM> promotionVMList;

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

    public List<PromotionVM> getPromotionVMList() {
        return promotionVMList;
    }

    public void setPromotionVMList(List<PromotionVM> promotionVMList) {
        this.promotionVMList = promotionVMList;
    }
}
