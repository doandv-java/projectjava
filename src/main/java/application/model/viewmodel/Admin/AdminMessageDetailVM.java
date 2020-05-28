package application.model.viewmodel.Admin;

import application.model.viewmodel.MessageVM;

public class AdminMessageDetailVM {
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private MessageVM messageVM;
    private String keyWord;

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public MessageVM getMessageVM() {
        return messageVM;
    }

    public void setMessageVM(MessageVM messageVM) {
        this.messageVM = messageVM;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}

