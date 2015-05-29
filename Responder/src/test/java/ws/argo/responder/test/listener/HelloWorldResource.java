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

package ws.argo.responder.test.listener;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/helloworld")
public class HelloWorldResource {

  public HelloWorldResource() {
    System.out.println("Instantiating HelloWorld");
  }

  // The Java method will process HTTP GET requests
  @GET
  // The Java method will produce content identified by the MIME Media
  // type "text/plain"
  @Produces("text/plain")
  public String getIt() {
    return "Hello World";
  }
}
