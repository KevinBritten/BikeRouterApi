package bikeRouterApi.response;

import java.util.List;

import bikeRouterApi.model.Route;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RouteListWrapper {
	private List<Route> routes;

	@XmlElement
	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
}
