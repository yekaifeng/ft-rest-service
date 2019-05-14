package hello;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 基本返回体
 * @author Freeman
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {

    /**
     * 应答数据
     */
    private Object data;

    public CommonResponse() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
