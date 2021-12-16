import java.util.concurrent.Flow;

/**
 * @author deer
 * @date 2021-12-09
 */
public class Main {
    public static void main(String[] args) {
        getTemperatures("北京").subscribe(new TempSubscriber());
    }

    private static Flow.Publisher<TempInfo> getTemperatures(String town) {
        return  subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
    }
}
