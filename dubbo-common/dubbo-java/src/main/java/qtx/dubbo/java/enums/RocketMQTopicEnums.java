package qtx.dubbo.java.enums;

import lombok.Getter;

/**
 * @author qtx
 * @since 2023/7/17
 */
@Getter
public enum RocketMQTopicEnums {
    TOPIC_TAG("主题","标签"),
    Log_Normal("Log_Normal","log");

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

}
