package qtx.dubbo.config.exception;


import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import qtx.dubbo.java.Result;

import java.util.LinkedHashMap;

/**
 * 全局异常处理
 * <p>方法顺序及捕获顺序</p>
 *
 * @author qtx
 * @since 2022/10/29 0:05
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Result<String>> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("异常信息:", e);
        return new ResponseEntity<>(Result.failed(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Result<LinkedHashMap<String, String>>> bindException(BindException e) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        e.getFieldErrors()
                .forEach(a -> map.put(a.getField(), a.getDefaultMessage()));
        log.error("异常信息:", e);
        return new ResponseEntity<>(Result.failed(map, "Parameter validation failed"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Result<String>> nullException(NullPointerException e) {
        log.error("空指针异常", e);
        return new ResponseEntity<>(Result.failed("空指针异常"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RpcException.class)
    public ResponseEntity<Result<String>> dateException(RpcException e) {
        log.info("RpcException:{}", e.getMessage());
        return new ResponseEntity<>(Result.failed(e.getMessage(), e.getCode()), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<String>> exception(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(Result.failed(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}