package application.model.viewmodel;

import java.util.List;

public class ChartVM {
    private List<ChartLabelDataVM> labelDataVMList;

    public List<ChartLabelDataVM> getLabelDataVMList() {
        return labelDataVMList;
    }

    public void setLabelDataVMList(List<ChartLabelDataVM> labelDataVMList) {
        this.labelDataVMList = labelDataVMList;
    }

    public ChartVM(List<ChartLabelDataVM> labelDataVMList) {
        this.labelDataVMList = labelDataVMList;
    }

    public ChartVM() {
    }
}
