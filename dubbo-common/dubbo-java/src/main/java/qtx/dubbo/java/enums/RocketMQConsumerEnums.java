package qtx.dubbo.java.enums;

import lombok.Getter;

/**
 * @author qtx
 * @since 2023/7/19
 */
@Getter
public enum RocketMQConsumerEnums {

    CONSUMER_GROUP_NAME("消费者组名称"),
    Log_consumer_group("Log_consumer_group"),
    Url_consumer_group("Url_consumer_group");

    /**
     * 消费者组
     */
    private final String name;


    RocketMQConsumerEnums(String name) {
        this.name = name;
    }

}
