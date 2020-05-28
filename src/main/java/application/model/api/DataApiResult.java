package application.model.api;

import groovy.transform.ToString;

@ToString
public class DataApiResult extends BaseApiResult {

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
