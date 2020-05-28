package application.model.viewmodel.Admin;

import application.data.model.DeliveryStatus;
import application.model.viewmodel.Admin.LayoutHeaderAdminVM;
import application.model.viewmodel.DeliveryStatusVM;
import application.model.viewmodel.OrderVM;

import java.util.List;

public class AdminOrderVM {
    private String keyWord;
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<OrderVM> orderVMList;
    private  List<DeliveryStatusVM> deliveryStatusVMS;

    public List<DeliveryStatusVM> getDeliveryStatusVMS() {
        return deliveryStatusVMS;
    }

    public void setDeliveryStatusVMS(List<DeliveryStatusVM> deliveryStatusVMS) {
        this.deliveryStatusVMS = deliveryStatusVMS;
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

    public List<OrderVM> getOrderVMList() {
        return orderVMList;
    }

    public void setOrderVMList(List<OrderVM> orderVMList) {
        this.orderVMList = orderVMList;
    }
}
