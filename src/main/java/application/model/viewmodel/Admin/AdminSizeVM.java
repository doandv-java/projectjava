package application.model.viewmodel.Admin;

import application.model.viewmodel.CategoryVM;
import application.model.viewmodel.SizeVM;

import java.util.List;

public class AdminSizeVM {
    private String keyWord;
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<SizeVM> sizeVMList;

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

    public List<SizeVM> getSizeVMList() {
        return sizeVMList;
    }

    public void setSizeVMList(List<SizeVM> sizeVMList) {
        this.sizeVMList = sizeVMList;
    }
}
