package bikeRouterApi;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
	private MongoDatabase db;

	public RoutesResource() {
		md = MockDatabase.getInstance();
		db = MongoDBConnection.getDatabase();
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
		MongoCollection<Document> usersCollection = db.getCollection("users");
		MongoDBService usersService = new MongoDBService(usersCollection);
		User user = null;
		ObjectId userId = new ObjectId(body.getUserId());
		try {
			userId = new ObjectId(body.getUserId()); // Convert the string to ObjectId
			user = usersService.getUserById(userId); // Use ObjectId for querying
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.BAD_REQUEST)
					.entity("Invalid User Id format.");
			return addCorsHeaders(responseBuilder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user != null) {
			MongoCollection<Document> routesCollection = db.getCollection("routes");
			MongoDBService routesService = new MongoDBService(routesCollection);
			Route route = body.getRoute();
			try {
				route.setId(routesService.addRoute(route).toString());
			} catch (Exception e) {
				Response.ResponseBuilder responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Unable to add route.");
				return addCorsHeaders(responseBuilder);
			}
			user.addRoute(route.getId(), route.getName());
			try {
				usersService.updateUser(userId, user);
			} catch (Exception e) {
				Response.ResponseBuilder responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Unable to update user.");
				return addCorsHeaders(responseBuilder);
			}
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
