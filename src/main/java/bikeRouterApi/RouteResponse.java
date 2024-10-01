package bikeRouterApi;

public class RouteResponse {
	private String message;
	private Route route;

	public RouteResponse(String message, Route route) {
		this.message = message;
		this.route = route;
	}

	public String getMessage() {
		return message;
	}

	public Route getRoute() {
		return route;
	}

}