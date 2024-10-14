# Utilizzo

### Main example

All you need is:

- create routes
- add them into a RoutesHandler
- create and start the server passing a port number and the RoutesHandler instance

```java
package Test;

import Eye.Route.RoutesHandler;
import Eye.Server;

public class Main {
	public static void main(String[] args) {
		int PORT = 7777;

		RoutesHandler routesHandler = new RoutesHandler();
		routesHandler.addRoute(new Index());
		routesHandler.addRoute(new Datas());
		Server server = new Server(PORT, routesHandler);
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
}
```

### Route example

Response are standardized, just need bytes as parameter

```java
package Test;

import Eye.Response.HTML;
import Eye.Local.FileManager;
import Eye.Route.Route;

import java.io.IOException;

public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	public String response() throws IOException {
		HTML response = new HTML(FileManager.GetFileContent("src/Test/index.html"));
		return response.getResponse();
	}
}

```

## Classes

### Server

- run()

### EndpointThread

- thread separated request handling, uses RequestHandler

### RequestHandler

- parses reqeuest data

### Route handler

- linked list of routes

### Route

- path
- response()

### Response

- HTML()
- JSON()
