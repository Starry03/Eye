# Utilizzo

### Main example

All you need is:

- create routes
- add them into a RoutesHandler
- create and start the server passing a port number and the RoutesHandler instance

```java
import Test.Root;

public class Main {
	public static void main(String[] args) {
		RoutesHandler routesHandler = new RoutesHandler();
		routesHandler.addRouter(new Root()); // add route to LinkedList
		Server server = new Server(7777, routesHandler);
		server.run();
	}
}
```

### Route example

Response are standardized, just need bytes as parameter

```java
public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	public String response() {
		return Response.HTML(GetFileContent("src/index.html"));
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
