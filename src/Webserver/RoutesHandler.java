package Webserver;

import java.util.LinkedList;

public class RoutesHandler {
    private final LinkedList<Route> routers;
    public RoutesHandler() {
        routers = new LinkedList<>();
    }

    public void addRouter(Route router) {
        routers.addFirst(router);
    }
    
    public void removeRouter(Route router) {
        routers.remove(router);
    }
    public LinkedList<Route> getRouters() {
        return routers;
    }
}
