/*
 * Copyright 2015 Jeff Simpson.
 *
 * Licensed under the MIT License, (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ws.argo.plugin.transport.responder;

/**
 * TransportConfigException is an exception that can occur if the Transport is
 * provided bad configuration parameters.
 * 
 * @author jmsimpson
 *
 */
public class TransportConfigException extends Exception {

  private static final long serialVersionUID = 4913095738616220364L;

  public TransportConfigException() {
    super();
  }

  public TransportConfigException(String message, Throwable cause) {
    super(message, cause);
  }

  public TransportConfigException(String message) {
    super(message);
  }

}
