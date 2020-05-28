package application.model.viewmodel.Admin;

import application.model.viewmodel.MessageVM;

import java.util.List;

public class AdminMessageVM {
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<MessageVM> messageVMList;
    private String keyWord;

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

    public List<MessageVM> getMessageVMList() {
        return messageVMList;
    }

    public void setMessageVMList(List<MessageVM> messageVMList) {
        this.messageVMList = messageVMList;
    }
}
