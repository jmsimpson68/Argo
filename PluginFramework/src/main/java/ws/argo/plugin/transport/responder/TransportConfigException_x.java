package ws.argo.plugin.transport.responder;

/**
 * This exception gets thrown when some kind of error occured during the
 * configuration of a Transport.
 * 
 * @author jmsimpson
 *
 */
public class TransportConfigException_x extends Exception {

  private static final long serialVersionUID = 753487924802419379L;

  public TransportConfigException_x() {
    super();
  }

  public TransportConfigException_x(String message, Throwable cause) {
    super(message, cause);
  }

  public TransportConfigException_x(String message) {
    super(message);
  }
}
