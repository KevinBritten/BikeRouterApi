package bikeRouterApi;

public class SaveRouteBody {
	private int userId;
	private Route route;

	public SaveRouteBody() {

	}

	public SaveRouteBody(int userId, Route route) {
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
