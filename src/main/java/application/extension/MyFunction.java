package application.extension;

import application.data.model.Color;
import application.data.model.Product;
import application.data.model.ProductImage;
import application.data.model.Size;
import application.model.viewmodel.ColorVM;
import application.model.viewmodel.ProductImageVM;
import application.model.viewmodel.SizeVM;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class MyFunction {
    public static List<SizeVM> toSizeVMList(List<Size> sizeList){
        List<SizeVM> sizeVMList = new ArrayList<>();
        for(Size size2 : sizeList) {
            SizeVM sizeVM = new SizeVM();
            sizeVM.setId(size2.getId());
            sizeVM.setName(size2.getName());
            sizeVMList.add(sizeVM);
        }
        return sizeVMList;
    }

    public static List<ColorVM> toColorVMList(List<Color> colorList){
        List<ColorVM> colorVMList = new ArrayList<>();
        for(Color color : colorList) {
            ColorVM colorVM = new ColorVM();
            colorVM.setId(color.getId());
            colorVM.setName(color.getName());
            colorVMList.add(colorVM);
        }
        return colorVMList;
    }

    public static List<ProductImageVM> toProductImageVMList(List<ProductImage> productImageList){
        List<ProductImageVM> productImageVMList = new ArrayList<>();
        for(ProductImage img : productImageList){
            ProductImageVM productImageVM= new ProductImageVM();
            productImageVM.setId(img.getId());
            productImageVM.setLink(img.getLink());
            productImageVM.setTitle(img.getTitle());
            productImageVMList.add(productImageVM);
        }
        return productImageVMList;
    }

    public static String getGuid(HttpServletRequest request) {
        Cookie cookie[] = request.getCookies();

        if(cookie!=null) {
            for(Cookie c :cookie) {
                if(c.getName().equals("guid"))  return c.getValue();
            }
        }
        return null;
    }
    public static boolean isContainer(List<Product> productList, int id){
        for(Product product: productList){
            if (product.getId()==id)
                return true;
        }
        return false;
    }
}
