package bikeRouterApi.resource;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bikeRouterApi.database.MongoDBConnection;
import bikeRouterApi.model.AddRouteBody;
import bikeRouterApi.model.Route;
import bikeRouterApi.model.User;
import bikeRouterApi.repository.MongoDBService;
import bikeRouterApi.response.RouteResponse;
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
	private MongoDatabase db;

	public RoutesResource() {
		db = MongoDBConnection.getDatabase();
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
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid User Id format.").build();
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
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unable to add route.").build();
			}

			user.addRoute(route.getId(), route.getName());
			try {
				usersService.updateUser(userId, user);
			} catch (Exception e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unable to update user.").build();
			}

			return Response.ok("Route added.").build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();

		}
	}

	@Path("getRoute")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoute(@QueryParam("routeId") String routeId) {
		MongoCollection<Document> routesCollection = db.getCollection("routes");
		MongoDBService routesService = new MongoDBService(routesCollection);
		Route route;
		try {
			route = routesService.getRouteById(new ObjectId(routeId));
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unable to find route.").build();
		}

		if (route != null) {
			return Response.ok(new RouteResponse("Route found.", route)).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Route not found").build();
		}
	}

}
