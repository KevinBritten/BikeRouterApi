package bikeRouterApi;

import bikeRouterApi.MockDatabase.User;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class Admin {

	private MockDatabase md;

	public Admin() {
		md = MockDatabase.getInstance();
	}

	private Response addCorsHeaders(Response.ResponseBuilder responseBuilder) {
		return responseBuilder.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
				.header("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept")
				.build();
	}

	@OPTIONS
	@Path("{path:.*}")
	public Response options() {
		return addCorsHeaders(Response.ok());
	}

	@Path("signup/")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response signup(@FormParam("username") String username, @FormParam("password") String password) {

		md.addUser(new User(username, password));
		Response.ResponseBuilder responseBuilder = Response.ok("Signup successful");
		return addCorsHeaders(responseBuilder);
	}

	@Path("login/")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {

		User user = md.getUserByName(username);
		if (user != null && password.equals(user.getPassword())) {
			String jsonResponse = "{\"message\":\"Login successful\",\"userId\":" + user.getId() + "}";
			Response.ResponseBuilder responseBuilder = Response.ok(jsonResponse);
			return addCorsHeaders(responseBuilder);
		} else {
			Response.ResponseBuilder responseBuilder = Response.status(Response.Status.UNAUTHORIZED)
					.entity("Invalid credentials");
			return addCorsHeaders(responseBuilder);

		}
	}

}
