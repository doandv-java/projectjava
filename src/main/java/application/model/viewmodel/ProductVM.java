package application.model.viewmodel;

import java.util.Date;
import java.util.List;

public class    ProductVM {
    private int id;
    private String categoryName;
    private String name;
    private String shortDesc;
    private String mainImage;
    private String price;
    private String createdDate;
    private List<ProductImageVM> productImageVMS;
    private String supplyName;
    private String promotionName;
    private int categoryId;
    private int rateAvg;
    private int favourite;
    private List<ColorVM> colorVMList;
    private  List <SizeVM> sizeVMList;
    private List<ProductEntityVM> productEntityVMList;
    private List<ProductImageVM> productImageVMList;

    public List<ProductImageVM> getProductImageVMList() {
        return productImageVMList;
    }

    public void setProductImageVMList(List<ProductImageVM> productImageVMList) {
        this.productImageVMList = productImageVMList;
    }

    public List<ProductEntityVM> getProductEntityVMList() {
        return productEntityVMList;
    }

    public void setProductEntityVMList(List<ProductEntityVM> productEntityVMList) {
        this.productEntityVMList = productEntityVMList;
    }

    public List<ColorVM> getColorVMList() {
        return colorVMList;
    }

    public void setColorVMList(List<ColorVM> colorVMList) {
        this.colorVMList = colorVMList;
    }

    public List<SizeVM> getSizeVMList() {
        return sizeVMList;
    }

    public void setSizeVMList(List<SizeVM> sizeVMList) {
        this.sizeVMList = sizeVMList;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public int getRateAvg() {
        return rateAvg;
    }

    public void setRateAvg(int rateAvg) {
        this.rateAvg = rateAvg;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        String temp="";
        temp=Double.toString(price);
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
        this.price = result;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public List<ProductImageVM> getProductImageVMS() {
        return productImageVMS;
    }

    public void setProductImageVMS(List<ProductImageVM> productImageVMS) {
        this.productImageVMS = productImageVMS;
    }
}
