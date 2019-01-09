package boot.security.web.demo.web.async;

import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallableOrderProcessor implements Callable<String> {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private String id;

  public CallableOrderProcessor(String id) {
    this.id = id;
  }

  /**
   * Computes a result, or throws an exception if unable to do so.
   *
   * @return computed result
   * @throws Exception if unable to compute a result
   */
  @Override
  public String call() throws Exception {
    logger.info("Second Thread Starts");
    Thread.sleep(1000); // Processing order for 1 sec
    logger.info("Second Thread Ends");
    return "Order Processed " + id;
  }
}
