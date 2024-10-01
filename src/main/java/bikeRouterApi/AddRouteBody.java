package bikeRouterApi;

public class AddRouteBody {
	private int userId;
	private Route route;

	public AddRouteBody() {

	}

	public AddRouteBody(int userId, Route route) {
		this.userId = userId;
		this.route = route;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

}
