package application.model.viewmodel;

import application.model.api.DataApiResult;

import javax.xml.crypto.Data;
import java.util.Date;

public class DeliveryStatusVM {
    private  int id;
    private  String name;
private String createdDateShow;
private Date createdDate;

    public DeliveryStatusVM() {
    }

    public DeliveryStatusVM(int id, String name, Date createdDate) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
    }

    public String getCreatedDateShow() {
        return createdDateShow;
    }

    public void setCreatedDateShow(String createdDateShow) {
        this.createdDateShow = createdDateShow;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
