package qtx.dubbo.log.interfaces;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;
import qtx.dubbo.log.interfaces.apisix.ApiEntity;

/**
 * @author qtx
 * @since 2023/8/2
 */
@HttpExchange(value = "/apisix/admin", contentType = MimeTypeUtils.APPLICATION_JSON_VALUE, accept = MimeTypeUtils.APPLICATION_JSON_VALUE)
public interface ApiSixClient {

    @PutExchange("/routes/{id}")
    String addRoute(@RequestHeader(name = "X-API-KEY") String key, @PathVariable long id, @RequestBody ApiEntity entity);
}
