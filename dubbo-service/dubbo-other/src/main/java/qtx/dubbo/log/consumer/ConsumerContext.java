package qtx.dubbo.log.consumer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qtx
 * @since 2023/8/3 19:42
 */
public class ConsumerContext {

    private static final List<ConsumerInfo> CONSUMER_INFO_LIST = new ArrayList<>();

    public static void registerConsumer(ConsumerInfo consumerInfo) {
        CONSUMER_INFO_LIST.add(consumerInfo);
    }

    public static List<ConsumerInfo> getConsumerInfoList() {
        return CONSUMER_INFO_LIST;
    }
}
