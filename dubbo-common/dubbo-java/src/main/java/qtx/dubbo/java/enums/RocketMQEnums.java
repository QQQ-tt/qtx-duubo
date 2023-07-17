package qtx.dubbo.java.enums;

/**
 * @author qtx
 * @since 2023/7/17
 */
public enum RocketMQEnums {
    TOPIC_TAG_KEY("主题","标签","索引键");

    /**
     * 主题
     */
    private final String topic;

    /**
     * 标签
     */
    private final String tag;

    /**
     * 索引键
     */
    private final String key;


    RocketMQEnums(String topic, String tag, String key) {
        this.topic = topic;
        this.tag = tag;
        this.key = key;
    }

    public String getTopic() {
        return topic;
    }

    public String getTag() {
        return tag;
    }

    public String getKey() {
        return key;
    }
}
