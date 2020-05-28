package application.model.viewmodel;

import java.util.Date;
import java.util.List;

public class OrderTrackingVM {
    private Integer id;
    private String customerName;
    private String phoneNumber;
    private String address;
    private String email;
    private Date createdDate;
    private String price;
    private String shipPrice;
    private String totalProce;
    private String deliveryStatus;
    private int deliveryStatusId;
    private List<OrderProductVM> orderProductVMList;
    private List<DeliveryStatusVM> deliveryStatusVMList;


    public String getTotalProce() {
        return totalProce;
    }

    public void setTotalProce(String totalProce) {
        this.totalProce = totalProce;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(String shipPrice) {
        this.shipPrice = shipPrice;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getDeliveryStatusId() {
        return deliveryStatusId;
    }

    public void setDeliveryStatusId(int deliveryStatusId) {
        this.deliveryStatusId = deliveryStatusId;
    }

    public List<OrderProductVM> getOrderProductVMList() {
        return orderProductVMList;
    }

    public void setOrderProductVMList(List<OrderProductVM> orderProductVMList) {
        this.orderProductVMList = orderProductVMList;
    }

    public List<DeliveryStatusVM> getDeliveryStatusVMList() {
        return deliveryStatusVMList;
    }

    public void setDeliveryStatusVMList(List<DeliveryStatusVM> deliveryStatusVMList) {
        this.deliveryStatusVMList = deliveryStatusVMList;
    }
}
