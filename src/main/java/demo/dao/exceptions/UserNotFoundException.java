package demo.dao.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String email) {
    super("Could not find User with the email of " + email);
  }

	public UserNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
