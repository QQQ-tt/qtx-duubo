package qtx.dubbo.config.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import qtx.dubbo.java.enums.RocketMQEnums;

/**
 * @author qtx
 * @since 2023/7/17
 */
@Slf4j
@Component
public class RocketMQUtils {

    @Value("${rocketmq.endpoint}")
    private String endpoint;

    private final ClientConfiguration configuration;

    public RocketMQUtils(ClientConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * 发送普通消息
     *
     * @param rocketMQEnums 消息类型
     * @param messageBody   消息内容
     * @throws ClientException 连接异常
     */
    public void sendMsg(RocketMQEnums rocketMQEnums, String messageBody) throws ClientException {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        // 初始化Producer时需要设置通信配置以及预绑定的Topic。
        Producer producer = getProducer(rocketMQEnums, provider);
        //普通消息发送。
        Message message = provider.newMessageBuilder()
                .setTopic(rocketMQEnums.getTopic())
                //设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys(rocketMQEnums.getKey())
                //设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag(rocketMQEnums.getTag())
                //消息体。
                .setBody(messageBody.getBytes())
                .build();
        try {
            //发送消息，需要关注发送结果，并捕获失败等异常。
            SendReceipt sendReceipt = producer.send(message);
            log.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
        } catch (ClientException e) {
            log.error("Failed to send message", e);
            e.printStackTrace();
        }
    }

    /**
     * 发送定时/延时消息
     *
     * @param rocketMQEnums    消息类型
     * @param messageBody      消息内容
     * @param deliverTimeStamp 时间戳
     * @throws ClientException 连接异常
     */
    public void sendMsg(RocketMQEnums rocketMQEnums, String messageBody, Long deliverTimeStamp) throws ClientException {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        // 初始化Producer时需要设置通信配置以及预绑定的Topic。
        Producer producer = getProducer(rocketMQEnums, provider);
        //普通消息发送。
        Message message = provider.newMessageBuilder()
                .setTopic(rocketMQEnums.getTopic())
                //设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys(rocketMQEnums.getKey())
                //设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag(rocketMQEnums.getTag())
                //设置发送时间
                .setDeliveryTimestamp(deliverTimeStamp)
                //消息体。
                .setBody(messageBody.getBytes())
                .build();
        try {
            //发送消息，需要关注发送结果，并捕获失败等异常。
            SendReceipt sendReceipt = producer.send(message);
            log.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
        } catch (ClientException e) {
            log.error("Failed to send message", e);
            e.printStackTrace();
        }
    }

    /**
     * 发送顺序消息
     *
     * @param rocketMQEnums 消息类型
     * @param messageBody   消息内容
     * @param msgGroup      排序分组编号,推荐设置
     * @throws ClientException 连接异常
     */
    public void sendMsg(RocketMQEnums rocketMQEnums, String messageBody, String msgGroup) throws ClientException {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        // 初始化Producer时需要设置通信配置以及预绑定的Topic。
        Producer producer = getProducer(rocketMQEnums, provider);
        //普通消息发送。
        Message message = provider.newMessageBuilder()
                .setTopic(rocketMQEnums.getTopic())
                //设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys(rocketMQEnums.getKey())
                //设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag(rocketMQEnums.getTag())
                //设置顺序消息的排序分组，该分组尽量保持离散，避免热点排序分组。
                .setMessageGroup(msgGroup)
                //消息体。
                .setBody(messageBody.getBytes())
                .build();
        try {
            //发送消息，需要关注发送结果，并捕获失败等异常。
            SendReceipt sendReceipt = producer.send(message);
            log.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
        } catch (ClientException e) {
            log.error("Failed to send message", e);
            e.printStackTrace();
        }
    }

    @Bean
    public ClientConfiguration getClientConfiguration() {
        return ClientConfiguration.newBuilder()
                .setEndpoints(endpoint)
                .build();
    }

    private Producer getProducer(RocketMQEnums rocketMQEnums, ClientServiceProvider provider) throws ClientException {
        return provider.newProducerBuilder()
                .setTopics(rocketMQEnums.getTopic())
                .setClientConfiguration(configuration)
                .build();
    }

}
