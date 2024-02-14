package Webserver;

import java.util.LinkedList;

public class RoutesHandler {
    public LinkedList<Route> routers = new LinkedList<>();
    public RoutesHandler() {}

    public void addRouter(Route router) {
        routers.add(router);
    }
    
    public void removeRouter(Route router) {
        routers.remove(router);
    }
}
