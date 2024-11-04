package bikeRouterApi;

import java.security.SecureRandom;
import java.util.Base64;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class AdminResource {

	private MongoDatabase db;

	public AdminResource() {
		db = MongoDBConnection.getDatabase();
	}

	private Response addCorsHeaders(Response.ResponseBuilder responseBuilder) {
		return responseBuilder.header("Access-Control-Allow-Origin", "http://localhost:3000")
				.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
				.header("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept")
				.header("Access-Control-Allow-Credentials", "true").build();
	}

	@Path("signup/")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response signup(@FormParam("username") String username, @FormParam("password") String password) {
		User user = new User(username, password);
		MongoCollection<Document> collection = db.getCollection("users");
		MongoDBService service = new MongoDBService(collection);
		try {
			service.insertUser(user);
			Response.ResponseBuilder responseBuilder = Response.ok("Signup successful");
			return addCorsHeaders(responseBuilder);
		} catch (Exception e) {
			e.printStackTrace();
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			return addCorsHeaders(responseBuilder);
		}

	}

	@Path("login/")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
		MongoCollection<Document> collection = db.getCollection("users");
		MongoDBService service = new MongoDBService(collection);
		User user;
		try {
			user = service.getUserByName(username);
		} catch (Exception e) {
			e.printStackTrace();
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.UNAUTHORIZED)
					.entity("Username not found.");
			return addCorsHeaders(responseBuilder);
		}
		if (user != null && password.equals(user.getPassword())) {
			SecureRandom random = new SecureRandom();
			byte[] tokenBytes = new byte[32];
			random.nextBytes(tokenBytes);
			String authToken = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);

			user.setAuthToken(authToken);
			try {
				service.updateUser(new ObjectId(user.getId()), user);
			} catch (Exception e) {
				Response.ResponseBuilder responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("Unable to set token");
				return addCorsHeaders(responseBuilder);
			}

			String jsonResponse = "{\"message\":\"Login successful\",\"userId\":\"" + user.getId() + "\"}";
			Response.ResponseBuilder responseBuilder = Response.ok(jsonResponse).header("Set-Cookie",
					"authToken=" + authToken + "; Max-Age=3600; Path=/; HttpOnly; Secure; SameSite=Strict");

			return addCorsHeaders(responseBuilder);

		} else {
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.UNAUTHORIZED)
					.entity("Invalid credentials");
			return addCorsHeaders(responseBuilder);

		}
	}

	@Path("user/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response user(@QueryParam("userId") String userId) {

		MongoCollection<Document> collection = db.getCollection("users");
		MongoDBService service = new MongoDBService(collection);
		User user;
		try {
			ObjectId objectId = new ObjectId(userId); // Convert the string to ObjectId
			user = service.getUserById(objectId); // Use ObjectId for querying
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.BAD_REQUEST)
					.entity("Invalid User Id format.");
			return addCorsHeaders(responseBuilder);
		} catch (Exception e) {
			e.printStackTrace();
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.UNAUTHORIZED)
					.entity("User Id not found.");
			return addCorsHeaders(responseBuilder);
		}
		if (user != null) {
			UserResponse jsonResponse = new UserResponse("User found.", user);
			Response.ResponseBuilder responseBuilder = Response.ok(jsonResponse);
			return addCorsHeaders(responseBuilder);
		} else {
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.NOT_FOUND)
					.entity("User not found with id " + userId);
			return addCorsHeaders(responseBuilder);
		}
	}

	@Path("getUserIdByToken")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getUserIdByToken(@CookieParam("authToken") String token) {
		MongoCollection<Document> collection = db.getCollection("users");
		AuthService service = new AuthService(collection);
		Document user = service.getUserByToken(token);

		if (user != null) {
			String userId = user.getObjectId("_id").toHexString(); // Convert ObjectId to String
			Response.ResponseBuilder responseBuilder = Response.ok(userId);
			return addCorsHeaders(responseBuilder);
		} else {
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.NOT_FOUND)
					.entity("Invalid token");
			return addCorsHeaders(responseBuilder);
		}
	}

	@Path("logout")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response logout(@FormParam("userId") String userId) {
		MongoCollection<Document> collection = db.getCollection("users");
		MongoDBService service = new MongoDBService(collection);
		User user;
		try {
			user = service.getUserById(new ObjectId(userId));
		} catch (Exception e) {
			e.printStackTrace();
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.UNAUTHORIZED)
					.entity("User ID not found.");
			return addCorsHeaders(responseBuilder);
		}

		if (user != null) {
			user.setAuthToken(null);
		}
		try {
			service.updateUser(new ObjectId(user.getId()), user);
			Response.ResponseBuilder responseBuilder = Response.ok("Logout successful.").header("Set-Cookie",
					"authToken=; Max-Age=0; Path=/; HttpOnly; Secure; SameSite=Strict");
			return addCorsHeaders(responseBuilder);
		} catch (Exception e) {
			e.printStackTrace();
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Unable to logout.");
			return addCorsHeaders(responseBuilder);
		}
	}

}
