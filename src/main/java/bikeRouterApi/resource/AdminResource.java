package bikeRouterApi.resource;

import java.security.SecureRandom;
import java.util.Base64;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bikeRouterApi.database.MongoDBConnection;
import bikeRouterApi.model.User;
import bikeRouterApi.repository.AuthService;
import bikeRouterApi.repository.MongoDBService;
import bikeRouterApi.response.UserResponse;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class AdminResource {

	private MongoDatabase db;

	public AdminResource() {
		db = MongoDBConnection.getDatabase();
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
			return Response.ok("Signup successful").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
			return Response.status(Response.Status.UNAUTHORIZED).entity("Username not found.").build();
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
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unable to set token").build();
			}

			String jsonResponse = "{\"message\":\"Login successful\",\"userId\":\"" + user.getId() + "\"}";
			return Response.ok(jsonResponse)
					.header("Set-Cookie",
							"authToken=" + authToken + "; Max-Age=3600; Path=/; HttpOnly; Secure; SameSite=Strict")
					.build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
		}
	}

	@Path("user/")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response user(@FormParam("userId") String userId) {
		MongoCollection<Document> collection = db.getCollection("users");
		MongoDBService service = new MongoDBService(collection);
		User user;

		try {
			ObjectId objectId = new ObjectId(userId); // Convert the string to ObjectId
			user = service.getUserById(objectId); // Use ObjectId for querying
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid User Id format.").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.UNAUTHORIZED).entity("User Id not found.").build();
		}

		if (user != null) {
			UserResponse jsonResponse = new UserResponse("User found.", user);
			return Response.ok(jsonResponse).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("User not found with id " + userId).build();
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
			return Response.ok(userId).build();

		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Invalid token").build();
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
			return Response.status(Response.Status.UNAUTHORIZED).entity("User ID not found.").build();
		}

		if (user != null) {
			user.setAuthToken(null);
		}

		try {
			service.updateUser(new ObjectId(user.getId()), user);
			return Response.ok("Logout successful.")
					.header("Set-Cookie", "authToken=; Max-Age=0; Path=/; HttpOnly; Secure; SameSite=Strict").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unable to logout.").build();
		}
	}

}
