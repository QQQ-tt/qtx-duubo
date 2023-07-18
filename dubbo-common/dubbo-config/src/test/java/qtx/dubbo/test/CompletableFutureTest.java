package qtx.dubbo.test;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @author qtx
 * @since 2023/7/18
 */
public class CompletableFutureTest {

    /**
     * 异步方法对异常和结果的操作
     */
    @Test
    public void whenCompleteFutureTest() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // 模拟不稳定异常
            if (Math.random() < 0.5) {
                throw new RuntimeException("Oops! Something went wrong.");
            }
            return 666;
        });

        // 使用 whenCompleteAsync 处理结果和异常
        CompletableFuture<Integer> whenCompleteFuture = future.whenCompleteAsync((result, ex) -> {
            if (ex != null) {
                System.out.println("Exception occurred: " + ex.getMessage());
            } else {
                System.out.println("Result: " + result);
            }
        });

        System.out.println("--------------------------------");

        // 等待异步操作完成(阻塞操作非必要执行)
        whenCompleteFuture.join();

        System.out.println("--------------------------------");

    }
}
