package qtx.dubbo.java.exception;


import org.apache.dubbo.rpc.RpcException;
import qtx.dubbo.java.enums.DataEnums;

/**
 * @author qtx
 * @since 2022/10/29 0:00
 */
public class DataException {

    private final String msg;

    private final int code;

    private final DataEnums dataEnums;

    public DataException(DataEnums dataEnums) {
        this.code = dataEnums.getCode();
        this.dataEnums = dataEnums;
        throw new RpcException(dataEnums.getCode(), dataEnums.toString());
    }

    public DataException(String msg, int code) {
        this.code = code;
        this.dataEnums = null;
        throw new RpcException(code, msg);
    }

    public int getCode() {
        return code;
    }

    public DataEnums getDataEnums() {
        return dataEnums;
    }

    @Override
    public String toString() {
        return "DataException{" + "code=" + code + ", msg='" + this.msg + '}';
    }
}