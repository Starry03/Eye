package Eye.Route;

import Eye.Route.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class RoutesHandler {
	/**
	 * The RoutesHandler class is responsible for managing a collection of Route objects.
	 * It uses a HashMap to store these routes, where the key is a string representing the path of the route,
	 * and the value is the Route object itself.
	 */
	private final HashMap<String, Route> routes = new HashMap<>();

	/**
	 * The addRoute method is used to add a new route to the routes HashMap.
	 * It takes a Route object as a parameter, retrieves the path of the route using route.getPath(),
	 * and adds the route to the HashMap with the path as the key.
	 *
	 * @param route The route to be added.
	 */
	public void addRoute(Route route) {
		routes.put(route.getPath(), route);
	}

	/**
	 * The addRoutes method is used to add a collection of routes to the routes HashMap.
	 * It takes an array of Route objects as a parameter, and for each route in the array,
	 * it calls the addRoute method to add the route to the HashMap.
	 *
	 * @param routes The routes to be added.
	 */
	public void addRoutes(Route[] routes) {
		for (Route route : routes)
			addRoute(route);
	}

	public void addRoutes(ArrayList<Route> routes) {
		for (Route route : routes)
			addRoute(route);
	}

	public void addRoutes(LinkedList<Route> routes) {
		for (Route route : routes)
			addRoute(route);
	}

	/**
	 * The removeRoute method is used to remove a route from the routes HashMap.
	 * Similar to the addRoute method, it takes a Route object as a parameter, retrieves the path of the route,
	 * and removes the route from the HashMap using the path as the key.
	 *
	 * @param route The route to be removed.
	 */
	public void removeRoute(Route route) {
		routes.remove(route.getPath());
	}

	public HashMap<String, Route> getRoutes() {
		return routes;
	}
}
