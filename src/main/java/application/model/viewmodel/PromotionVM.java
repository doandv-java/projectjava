package application.model.viewmodel;

import application.extension.CustomDateSerializer2;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class PromotionVM
{
    private int id;
    private  String name;
    private Date createdDate;
    private String shortDesc;
    private double discount;
    private Date beginDate;
    private Date endDate;

    public PromotionVM() {
    }

    public PromotionVM(int id, String name, Date createdDate, String shortDesc, double discount, Date beginDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.shortDesc = shortDesc;
        this.discount = discount;
        this.beginDate = beginDate;
        this.endDate = endDate;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date cratedDate) {
        this.createdDate = cratedDate;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
