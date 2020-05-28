package application.model.viewmodel;

import java.util.List;

public class ChartVM2 {
    private List<ChartLabelDataVM3> labelDataVMList;

    public List<ChartLabelDataVM3> getLabelDataVMList() {
        return labelDataVMList;
    }

    public void setLabelDataVMList(List<ChartLabelDataVM3> labelDataVMList) {
        this.labelDataVMList = labelDataVMList;
    }

    public ChartVM2(List<ChartLabelDataVM3> labelDataVMList) {
        this.labelDataVMList = labelDataVMList;
    }

    public ChartVM2() {
    }
}
