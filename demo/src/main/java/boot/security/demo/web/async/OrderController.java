package boot.security.demo.web.async;

import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @GetMapping("/{id:\\d+}")
  public String order(@PathVariable String id) throws InterruptedException {
    logger.info("Main Thread Starts");
    Thread.sleep(1000); // Processing order for 1 sec
    logger.info("Main Thread Returns");
    return "Order Processed " + id;
  }

  @GetMapping("/callable/{id:\\d+}")
  public Callable<String> orderCallable(@PathVariable String id) throws Exception {
    logger.info("Main Thread Starts");

    Callable<String> result =
        new Callable<String>() {
          @Override
          public String call() throws Exception {
            logger.info("Second Thread Starts");
            Thread.sleep(1000); // Processing order for 1 sec
            logger.info("Second Thread Ends");
            return "Order Processed";
          }
        };

    //    Callable<String> processor = new CallableOrderProcessor(id);
    //    String result = processor.call();
    logger.info("Main Thread Returns");
    return result;
  }

    public class CallableOrderProcessor implements Callable<String> {

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
}
