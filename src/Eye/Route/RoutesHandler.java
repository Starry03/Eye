package Eye.Route;

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
	 * @param routesArr The routes to be added.
	 */
	public void addRoutes(Route[] routesArr) {
		for (Route route : routesArr)
			routes.put(route.getPath(), route);
	}

	public void addRoutes(ArrayList<Route> routesList) {
		for (Route route : routesList)
			routes.put(route.getPath(), route);
	}

	public void addRoutes(LinkedList<Route> routesLinkedList) {
		for (Route route : routesLinkedList)
			routes.put(route.getPath(), route);
	}

	/**
	 * Removes a route from the hashmap containing all routes.
	 * @param route The route to be removed.
	 */
	public void removeRoute(Route route) {
		routes.remove(route.getPath());
	}

	/**
	 * The getRoutes method is used to retrieve the routes HashMap.
	 * It returns the routes HashMap, which contains all the routes that have been added to the RoutesHandler.
	 *
	 * @return The routes HashMap.
	 */
	public HashMap<String, Route> getRoutes() {
		return routes;
	}
}
