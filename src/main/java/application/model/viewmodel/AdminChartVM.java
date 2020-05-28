package application.model.viewmodel;

import application.model.viewmodel.Admin.LayoutHeaderAdminVM;

public class AdminChartVM {
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private ChartVM countProductByCategory;
    private  String totalPriceOfWeek;
    private int totalOrderOfWeek;
    private  int unread;
    private ChartVM profitInWeek;

    public ChartVM getProfitInWeek() {
        return profitInWeek;
    }

    public void setProfitInWeek(ChartVM profitInWeek) {
        this.profitInWeek = profitInWeek;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public int getTotalOrderOfWeek() {
        return totalOrderOfWeek;
    }

    public void setTotalOrderOfWeek(int totalOrderOfWeek) {
        this.totalOrderOfWeek = totalOrderOfWeek;
    }

    public void setTotalPriceOfWeek(String totalPriceOfWeek) {
        this.totalPriceOfWeek = totalPriceOfWeek;
    }

    public String getTotalPriceOfWeek() {
        return totalPriceOfWeek;
    }

    public void setTotalPriceOfWeek(double totalPriceOfWeek) {
        this.totalPriceOfWeek = String.format("%,.0f", totalPriceOfWeek);
    }

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public ChartVM getCountProductByCategory() {
        return countProductByCategory;
    }

    public void setCountProductByCategory(ChartVM countProductByCategory) {
        this.countProductByCategory = countProductByCategory;
    }
}
