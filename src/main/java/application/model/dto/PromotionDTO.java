package application.model.dto;

import application.extension.CustomDateSerializer;
import application.extension.CustomDateSerializer2;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class PromotionDTO {
    private int id;
    private String name;
    private String shortDesc;
    private double discount;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdDate;

    @JsonSerialize(using = CustomDateSerializer2.class)
    private Date beginDate;

    @JsonSerialize(using = CustomDateSerializer2.class)
    private Date endDate;

    public PromotionDTO(int id, String name, String shortDesc, double discount, Date createdDate, Date beginDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.shortDesc = shortDesc;
        this.discount = discount;
        this.createdDate = createdDate;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public PromotionDTO() {
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
