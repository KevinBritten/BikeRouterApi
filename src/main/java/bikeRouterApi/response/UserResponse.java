package bikeRouterApi.response;

import bikeRouterApi.model.User;

public class UserResponse {
	private String message;
	private User user;

	public UserResponse(String message, User user) {
		this.message = message;
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public User getUser() {
		return user;
	}

}