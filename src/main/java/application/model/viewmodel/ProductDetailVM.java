package application.model.viewmodel;

import application.data.model.ProductEntity;
import application.model.viewmodel.Admin.LayoutHeaderAdminVM;
import application.model.viewmodel.Admin.LayoutHeaderVM;

import java.util.List;

public class ProductDetailVM {
    private List<ProductVM> productVMList;
    private List<CategoryVM> categoryVMList;
    private List<ColorVM> colorVMList;
    private List<SizeVM> sizeVMList;
    private List<SupplyVM> supplyVMList;
    private LayoutHeaderVM layoutHeaderVM;
    private String keyWord;
    private ProductVM productVM;
    private List<ProductImageVM> productImageVMList;
    private List<ProductEntityVM> productEntityVMList;
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<CartProductVM> cartProductVMList;
    private String totalPrice;
    private int productAmount;
    private List<RateVM> rateVMList;
    private int totalRate;
    private int rateAvg;

    public int getRateAvg() {
        return rateAvg;
    }

    public void setRateAvg(int rateAvg) {
        this.rateAvg = rateAvg;
    }

    public int getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(int totalRate) {
        this.totalRate = totalRate;
    }

    public List<RateVM> getRateVMList() {
        return rateVMList;
    }

    public void setRateVMList(List<RateVM> rateVMList) {
        this.rateVMList = rateVMList;
    }

    public List<CartProductVM> getCartProductVMList() {
        return cartProductVMList;
    }

    public void setCartProductVMList(List<CartProductVM> cartProductVMList) {
        this.cartProductVMList = cartProductVMList;
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

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public ProductDetailVM() {
    }

    public ProductDetailVM(List<ProductVM> productVMList, List<CategoryVM> categoryVMList, List<ColorVM> colorVMList, List<SizeVM> sizeVMList, List<SupplyVM> supplyVMList, LayoutHeaderVM layoutHeaderVM, String keyWord, ProductVM productVM, List<ProductImageVM> productImageVMList) {
        this.productVMList = productVMList;
        this.categoryVMList = categoryVMList;
        this.colorVMList = colorVMList;
        this.sizeVMList = sizeVMList;
        this.supplyVMList = supplyVMList;
        this.layoutHeaderVM = layoutHeaderVM;
        this.keyWord = keyWord;
        this.productVM = productVM;
        this.productImageVMList = productImageVMList;
    }


    public List<ProductEntityVM> getProductEntityVMList() {
        return productEntityVMList;
    }

    public void setProductEntityVMList(List<ProductEntityVM> productEntityVMList) {
        this.productEntityVMList = productEntityVMList;
    }

    public List<ProductVM> getProductVMList() {
        return productVMList;
    }

    public void setProductVMList(List<ProductVM> productVMList) {
        this.productVMList = productVMList;
    }

    public List<CategoryVM> getCategoryVMList() {
        return categoryVMList;
    }

    public void setCategoryVMList(List<CategoryVM> categoryVMList) {
        this.categoryVMList = categoryVMList;
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

    public List<SupplyVM> getSupplyVMList() {
        return supplyVMList;
    }

    public void setSupplyVMList(List<SupplyVM> supplyVMList) {
        this.supplyVMList = supplyVMList;
    }

    public LayoutHeaderVM getLayoutHeaderVM() {
        return layoutHeaderVM;
    }

    public void setLayoutHeaderVM(LayoutHeaderVM layoutHeaderVM) {
        this.layoutHeaderVM = layoutHeaderVM;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public ProductVM getProductVM() {
        return productVM;
    }

    public void setProductVM(ProductVM productVM) {
        this.productVM = productVM;
    }

    public List<ProductImageVM> getProductImageVMList() {
        return productImageVMList;
    }

    public void setProductImageVMList(List<ProductImageVM> productImageVMList) {
        this.productImageVMList = productImageVMList;
    }
}
