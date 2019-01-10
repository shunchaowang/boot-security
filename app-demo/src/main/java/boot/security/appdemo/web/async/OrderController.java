package boot.security.appdemo.web.async;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/order")
public class OrderController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @GetMapping("/sync")
  public String order() throws InterruptedException {
    logger.info("Main Thread Starts");
    String id = RandomStringUtils.randomNumeric(8);
    Thread.sleep(1000); // Processing order for 1 sec
    logger.info("Main Thread Returns");
    return "Order Processed " + id;
  }

  @GetMapping("/async-callable")
  public Callable<String> orderCallable() {
    logger.info("Main Thread Starts");

    String id = RandomStringUtils.randomNumeric(8);

    //    Callable<String> result =
    //        new Callable<String>() {
    //          @Override
    //          public String call() throws Exception {
    //            logger.info("Second Thread Starts");
    //            Thread.sleep(1000); // Processing order for 1 sec
    //            logger.info("Second Thread Ends");
    //            return "Order Processed";
    //          }
    //        };

    Callable<String> result =
        () -> {
          logger.info("Second Thread Starts");
          Thread.sleep(1000); // Processing order for 1 sec
          logger.info("Second Thread Ends");
          return "Order Processed " + id;
        };

    // String result = new CallableOrderProcessor(id).call();
    logger.info("Main Thread Returns");
    return result;
  }

  @GetMapping("/async-deferredresult")
  public DeferredResult<ResponseEntity<?>> orderDeferredResult() {
    logger.info("Main Thread Starts");

    String id = RandomStringUtils.randomNumeric(8);

    DeferredResult<ResponseEntity<?>> result = new DeferredResult<>(5000l); // timeout of 5s
    result.onCompletion(() -> logger.info("DeferredResult Completion Callback"));
    result.onTimeout(
        () ->
            result.setErrorResult(
                ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                    .body("Request timeout occurred.")));
    result.onError(
        (Throwable t) -> {
          result.setErrorResult(
              ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred."));
        });

    ForkJoinPool.commonPool()
        .submit(
            () -> {
              logger.info("Second Thread Starts");
              try {
                Thread.sleep(1000); // Processing order for 1 sec
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              logger.info("Second Thread Ends");
              result.setResult(ResponseEntity.ok("Order Processed " + id));
            });

    logger.info("Main Thread Returns");
    return result;
  }
}
