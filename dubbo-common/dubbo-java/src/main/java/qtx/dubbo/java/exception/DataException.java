package qtx.dubbo.java.exception;


import org.apache.dubbo.rpc.RpcException;
import qtx.dubbo.java.enums.DataEnums;

/**
 * @author qtx
 * @since 2022/10/29 0:00
 */
public class DataException {

    public DataException(DataEnums dataEnums) {
        throw new RpcException(dataEnums.getCode(), dataEnums.toString());
    }

    public DataException(String msg, int code) {
        throw new RpcException(code, msg);
    }
}