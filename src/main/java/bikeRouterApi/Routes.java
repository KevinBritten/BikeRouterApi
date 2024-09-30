package bikeRouterApi;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("routes")
public class Routes {
	private MockDatabase md;

	public Routes() {
		md = MockDatabase.getInstance();
	}

	private Response addCorsHeaders(Response.ResponseBuilder responseBuilder) {
		return responseBuilder.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
				.header("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept")
				.build();
	}

	@Path("addRoute/")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRoute(@QueryParam("userId") int userId, @QueryParam("routeName") String routeName) {
		User user = md.getUserById(userId);
		if (user != null) {
			Route route = new Route(routeName);
			user.addRoute(route.getId(), route.getName());
			Response.ResponseBuilder responseBuilder = Response.ok("Route added.");
			return addCorsHeaders(responseBuilder);
		} else {
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.NOT_FOUND)
					.entity("User not found");
			return addCorsHeaders(responseBuilder);
		}
	}

	@Path("saveRoute/")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveRoute(SaveRouteBody body) {
		User user = md.getUserById(body.getUserId());
		if (user != null) {
			Route route = body.getRoute();
			user.addRoute(route.getId(), route.getName());
			Response.ResponseBuilder responseBuilder = Response.ok("Route added.");
			return addCorsHeaders(responseBuilder);
		} else {
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.NOT_FOUND)
					.entity("User not found");
			return addCorsHeaders(responseBuilder);
		}
	}

}
