package qtx.dubbo.java;

import lombok.Data;
import qtx.dubbo.java.enums.DataEnums;

/**
 * @author qtx
 * @since 2023/7/11
 */
@Data
public class Result<T> {
    private String msg;
    private int code;
    private T data;

    private Result(int code) {
        this.code = code;
    }

    private Result(String msg) {
        this.msg = msg;
    }

    private Result(DataEnums enums) {
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(T data, DataEnums enums) {
        this.data = data;
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    private Result(String msg, DataEnums enums) {
        this.msg = msg;
        this.code = enums.getCode();
    }

    private Result(String msg, int code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public static <T> Result<T> success(T date) {
        return new Result<>(date, DataEnums.SUCCESS);
    }

    public static <T> Result<T> success() {
        return new Result<>(DataEnums.SUCCESS.getMsg(), DataEnums.SUCCESS);
    }

    public static <T> Result<T> failed() {
        return new Result<>(DataEnums.FAILED.getMsg(), DataEnums.FAILED);
    }

    public static <T> Result<T> failed(int code) {
        return new Result<>(code);
    }

    public static <T> Result<T> failed(String msg) {
        return new Result<>(msg, DataEnums.FAILED);
    }

    public static <T> Result<T> failed(String msg, int code) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> failed(String msg, int code, T data) {
        return new Result<>(msg, code, data);
    }

    public static <T> Result<T> failed(DataEnums enums) {
        return new Result<>(enums);
    }

    public static <T> Result<T> failed(DataEnums enums, T data) {
        return new Result<>(data, enums);
    }
}
