package bikeRouterApi;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("routes")
public class RoutesResource {
	private MockDatabase md;

	public RoutesResource() {
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
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRoute(AddRouteBody body) {
		User user = md.getUserById(body.getUserId());
		if (user != null) {
			Route route = body.getRoute();
			md.addRoute(route);
			user.addRoute(route.getId(), route.getName());
			Response.ResponseBuilder responseBuilder = Response.ok("Route added.");
			return addCorsHeaders(responseBuilder);
		} else {
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.NOT_FOUND)
					.entity("User not found");
			return addCorsHeaders(responseBuilder);
		}
	}

	@Path("getRoute")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoute(@QueryParam("routeId") int routeId) {
		Route route = md.getRouteById(routeId);
		if (route != null) {
			Response.ResponseBuilder responseBuilder = Response.ok(new RouteResponse("Route found.", route));
			return addCorsHeaders(responseBuilder);
		} else {
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.NOT_FOUND)
					.entity("Route not found");
			return addCorsHeaders(responseBuilder);
		}
	}

}
