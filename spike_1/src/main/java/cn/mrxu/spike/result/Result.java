package cn.mrxu.spike.result;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;


    /**
     * 成功时调用
     * @param data 返回数据
     * @param <T> 类型
     * @return Result结果
     */
    public static <T> Result<T> success(T data){
        return new Result<>(data);
    }

    /**
     * 失败时调用
     * @param cm CodeMsg对象，存储错误信息和错误代码
     * @param <T> 类型
     * @return Result对象
     */
    public static <T> Result<T> error(CodeMsg cm) {
        return new Result<T>(cm);
    }



    /********************************constructor******************************************/

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm){
        if(cm == null) return;

        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }



    /********************************get&set******************************************/

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

}
