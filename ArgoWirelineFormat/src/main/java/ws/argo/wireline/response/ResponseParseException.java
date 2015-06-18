package ws.argo.wireline.response;

public class ResponseParseException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 5368454292780751828L;

  public ResponseParseException() {
  }

  public ResponseParseException(String message) {
    super(message);
  }

  public ResponseParseException(Throwable cause) {
    super(cause);
  }

  public ResponseParseException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResponseParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
