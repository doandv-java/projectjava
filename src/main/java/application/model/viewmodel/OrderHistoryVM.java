package application.model.viewmodel;

import application.model.viewmodel.Admin.LayoutHeaderAdminVM;

import java.util.List;

public class OrderHistoryVM {

    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<OrderVM> orderVMList;
    private List<CategoryVM> categoryVMList;
    private  int productAmount;
    private List<CartProductVM> cartProductVMList;
    private String totalPrice;
    private boolean found;


    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        String temp="";
        temp=Double.toString(totalPrice);
        temp=temp.substring(0,temp.indexOf('.'));
        int n=temp.length()/3;
        int length=temp.length();
        String result="";
        for (int i=0; i<=n;i++){
            if(i==0){
                result=result.concat(temp.substring(0,length-3*n));
            } else{
                if(!result.equals(""))
                    result=result.concat(".");
                result=result.concat(temp.substring(length-3*n+(i-1)*3,length-3*n+i*3));
            }
        }
        this.totalPrice = result;
    }

    public List<CartProductVM> getCartProductVMList() {
        return cartProductVMList;
    }

    public void setCartProductVMList(List<CartProductVM> cartProductVMList) {
        this.cartProductVMList = cartProductVMList;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public List<CategoryVM> getCategoryVMList() {
        return categoryVMList;
    }

    public void setCategoryVMList(List<CategoryVM> categoryVMList) {
        this.categoryVMList = categoryVMList;
    }

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public List<OrderVM> getOrderVMList() {
        return orderVMList;
    }

    public void setOrderVMList(List<OrderVM> orderVMList) {
        this.orderVMList = orderVMList;
    }
}
