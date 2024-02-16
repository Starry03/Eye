package Eye;

import java.util.HashMap;

public class RoutesHandler {
    private final HashMap<String, Route> routers;
    public RoutesHandler() {
        routers = new HashMap<>();
    }

    public void addRouter(Route router) {
        routers.put(router.getPath(), router);
    }
    
    public void removeRouter(Route router) {
        routers.remove(router.getPath());
    }
    public HashMap<String, Route> getRouters() {
        return routers;
    }
}
