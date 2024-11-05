package bikeRouterApi.model;

public class AddRouteBody {
	private String userId;
	private Route route;

	public AddRouteBody() {

	}

	public AddRouteBody(String userId, Route route) {
		this.userId = userId;
		this.route = route;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

}
