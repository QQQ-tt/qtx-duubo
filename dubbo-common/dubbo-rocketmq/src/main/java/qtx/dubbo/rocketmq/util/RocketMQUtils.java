package qtx.dubbo.rocketmq.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.MessageListener;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.apache.rocketmq.client.apis.producer.Transaction;
import org.apache.rocketmq.client.apis.producer.TransactionChecker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import qtx.dubbo.java.info.StaticConstant;
import qtx.dubbo.rocketmq.config.ProducerFactory;
import qtx.dubbo.rocketmq.enums.RocketMQConsumerEnums;
import qtx.dubbo.rocketmq.enums.RocketMQTopicEnums;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qtx
 * @since 2023/7/17
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "rocketmq.enable", havingValue = "true")
public class RocketMQUtils {

    private final ProducerFactory producerFactory;

    private final ClientConfiguration configuration;

    public RocketMQUtils(ProducerFactory producerFactory, ClientConfiguration configuration) {
        this.producerFactory = producerFactory;
        this.configuration = configuration;
    }

    /**
     * 发送普通消息(同步)
     *
     * @param rocketMQTopicEnums 消息类型
     * @param messageBody        消息内容
     * @throws ClientException 连接异常
     */
    public void sendMsg(RocketMQTopicEnums rocketMQTopicEnums, String key, String messageBody) throws ClientException {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        // 初始化Producer时需要设置通信配置以及预绑定的Topic。
        Producer producer = producerFactory.getInstance(rocketMQTopicEnums.getTopic());
        //普通消息发送。
        Message message = provider.newMessageBuilder()
                .setTopic(rocketMQTopicEnums.getTopic())
                //设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys(key)
                //设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag(rocketMQTopicEnums.getTag())
                //消息体。
                .setBody(messageBody.getBytes())
                .build();
        try {
            //发送消息，需要关注发送结果，并捕获失败等异常。
            SendReceipt sendReceipt = producer.send(message);
            log.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
        } catch (ClientException e) {
            log.error("Failed to send message", e);
        }
    }

    /**
     * 发送普通消息(异步)
     *
     * @param rocketMQTopicEnums 消息类型
     * @param messageBody        消息内容
     * @throws ClientException 连接异常
     */
    public void sendAsyncMsg(RocketMQTopicEnums rocketMQTopicEnums, String key, String messageBody) throws ClientException {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        // 初始化Producer时需要设置通信配置以及预绑定的Topic。
        Producer producer = producerFactory.getInstance(rocketMQTopicEnums.getTopic());
        //普通消息发送。
        Message message = provider.newMessageBuilder()
                .setTopic(rocketMQTopicEnums.getTopic())
                //设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys(key)
                //设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag(rocketMQTopicEnums.getTag())
                //消息体。
                .setBody(messageBody.getBytes())
                .build();
        //发送消息，需要关注发送结果，并捕获失败等异常。
        // Set individual thread pool for send callback.
        final CompletableFuture<SendReceipt> future = producer.sendAsync(message);
        ExecutorService sendCallbackExecutor = Executors.newCachedThreadPool();
        future.whenCompleteAsync((sendReceipt, throwable) -> {
            if (null != throwable) {
                log.error("Failed to send message", throwable);
                // Return early.
                return;
            }
            log.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
        }, sendCallbackExecutor);

    }

    /**
     * 发送定时/延时消息
     *
     * @param rocketMQTopicEnums 消息类型
     * @param messageBody        消息内容
     * @param deliverTimeStamp   时间戳
     * @throws ClientException 连接异常
     */
    public void sendMsg(RocketMQTopicEnums rocketMQTopicEnums, String key, String messageBody, Long deliverTimeStamp) throws ClientException {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        // 初始化Producer时需要设置通信配置以及预绑定的Topic。
        Producer producer = producerFactory.getInstance(rocketMQTopicEnums.getTopic());
        //普通消息发送。
        Message message = provider.newMessageBuilder()
                .setTopic(rocketMQTopicEnums.getTopic())
                //设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys(key)
                //设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag(rocketMQTopicEnums.getTag())
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
        }
    }

    /**
     * 发送顺序消息
     *
     * @param rocketMQTopicEnums 消息类型
     * @param messageBody        消息内容
     * @param msgGroup           排序分组编号,推荐设置
     * @throws ClientException 连接异常
     */
    public void sendMsg(RocketMQTopicEnums rocketMQTopicEnums, String key, String messageBody, String msgGroup) throws ClientException {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        // 初始化Producer时需要设置通信配置以及预绑定的Topic。
        Producer producer = producerFactory.getInstance(rocketMQTopicEnums.getTopic());
        //普通消息发送。
        Message message = provider.newMessageBuilder()
                .setTopic(rocketMQTopicEnums.getTopic())
                //设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys(key)
                //设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag(rocketMQTopicEnums.getTag())
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
        }
    }

    /**
     * 发送事务消息(同步)
     *
     * @param rocketMQTopicEnums 消息类型
     * @param messageBody        消息内容
     * @throws ClientException 连接异常
     */
    public void sendMsg(RocketMQTopicEnums rocketMQTopicEnums, String key, String messageBody, TransactionChecker checker, String info, Boolean flag) throws ClientException {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        // 初始化Producer时需要设置通信配置以及预绑定的Topic。
        Producer producer = producerFactory.getTransactionalInstance(checker, rocketMQTopicEnums.getTopic());
        Transaction transaction = producer.beginTransaction();
        //普通消息发送。
        Message message = provider.newMessageBuilder()
                .setTopic(rocketMQTopicEnums.getTopic())
                //设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys(key)
                //设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag(rocketMQTopicEnums.getTag())
                //设置自定义标记
                .addProperty(StaticConstant.ROCKETMQ_KEY, info)
                //消息体。
                .setBody(messageBody.getBytes())
                .build();
        SendReceipt sendReceipt = null;
        try {
            //发送消息，需要关注发送结果，并捕获失败等异常。
            sendReceipt = producer.send(message, transaction);
        } catch (ClientException e) {
            log.error("Failed to send message", e);
        }
        if (flag) {
            assert sendReceipt != null;
            log.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
            transaction.commit();
        } else {
            transaction.rollback();
        }
    }


    /**
     * 消费者
     *
     * @param rocketMQTopicEnums    消息类型
     * @param rocketMQConsumerEnums 消费者群组
     * @param messageListener       监听器处理消费结果
     * @return 消费者对象
     * @throws ClientException 连接异常
     */
    public PushConsumer pushConsumer(RocketMQTopicEnums rocketMQTopicEnums, RocketMQConsumerEnums rocketMQConsumerEnums, MessageListener messageListener) throws ClientException {
        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        FilterExpression filterExpression = new FilterExpression(rocketMQTopicEnums.getTag(), FilterExpressionType.TAG);
        return provider.newPushConsumerBuilder()
                .setClientConfiguration(configuration)
                .setConsumerGroup(rocketMQConsumerEnums.getName())
                .setSubscriptionExpressions(Collections.singletonMap(rocketMQTopicEnums.getTopic(), filterExpression))
                .setMessageListener(messageListener)
                .build();
    }

    /**
     * 转换成对象
     *
     * @param messageView 消息
     * @param tClass      目标对象类型
     * @return 转换结果
     */
    public static <T> T getEntity(MessageView messageView, Class<T> tClass) {
        ByteBuffer body = messageView.getBody();
        byte[] bytes = new byte[body.remaining()];
        body.get(bytes);
        String s = new String(bytes, StandardCharsets.UTF_8);
        if (tClass.isPrimitive() || tClass == String.class) {
            return (T) s;
        }
        return JSONObject.parseObject(s, tClass);
    }
}
