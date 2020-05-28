package application.model.viewmodel.Admin;

import application.model.viewmodel.*;

import java.util.List;

public class AdminInvoiceVM {

    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<OrderProductVM> orderProductVMList;
    private String totalPrice;
    private String shipPrice;
    private OrderVM orderVM;
    private String productTotalPrice;

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public List<OrderProductVM> getOrderProductVMList() {
        return orderProductVMList;
    }

    public void setOrderProductVMList(List<OrderProductVM> orderProductVMList) {
        this.orderProductVMList = orderProductVMList;
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

    public String getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(double totalPrice) {
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
        this.shipPrice = result;
    }

    public OrderVM getOrderVM() {
        return orderVM;
    }

    public void setOrderVM(OrderVM orderVM) {
        this.orderVM = orderVM;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(double totalPrice) {
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
        this.productTotalPrice = result;
    }
}
