package qtx.dubbo.java.enums;

/**
 * @author qtx
 * @since 2023/7/17
 */
public enum RocketMQTopicEnums {
    TOPIC_TAG("主题","标签");

    /**
     * 主题
     */
    private final String topic;

    /**
     * 标签
     */
    private final String tag;

    RocketMQTopicEnums(String topic, String tag) {
        this.topic = topic;
        this.tag = tag;
    }

    public String getTopic() {
        return topic;
    }

    public String getTag() {
        return tag;
    }
}
