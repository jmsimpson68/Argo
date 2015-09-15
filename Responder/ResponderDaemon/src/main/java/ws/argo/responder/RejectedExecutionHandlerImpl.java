package ws.argo.responder;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * The RejectedExecutionHandlerImpl will deal with probes that cannot be
 * processed by the responder given its threading conditions.
 * 
 * <p>
 * In short, if the processor is being flooded with probe messages, it can only
 * handle so many at a time. If, for some reason, the configuration of the
 * processor can't handle all of the incoming probes, this handler will be
 * called deal with the probed dumped on the floor.
 * 
 * @author jmsimpson
 *
 */
public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {
  public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    System.out.println(r.toString() + " is rejected"); // TODO: better logging
  }
}
