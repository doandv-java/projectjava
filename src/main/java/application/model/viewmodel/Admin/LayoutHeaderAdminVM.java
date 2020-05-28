package application.model.viewmodel.Admin;

public class LayoutHeaderAdminVM {

    private String userName;
    private String avatar;
    private boolean isAdmin;
    private boolean isUser;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LayoutHeaderAdminVM(String userName, String avatar, boolean isAdmin, boolean isUser) {
        this.userName = userName;
        this.avatar = avatar;
        this.isAdmin = isAdmin;
        this.isUser = isUser;
    }

    public LayoutHeaderAdminVM() {
        this.userName = "Anonymous";
        this.avatar = "/link/avatar.jpg";
        this.isAdmin = false;
        this.isUser = false;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
