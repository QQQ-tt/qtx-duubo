package qtx.dubbo.java.enums;

/**
 * @author qtx
 * @since 2023/7/19
 */
public enum RocketMQConsumerEnums {

    CONSUMER_GROUP_NAME("消费者组名称");

    /**
     * 消费者组
     */
    private final String name;


    RocketMQConsumerEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
