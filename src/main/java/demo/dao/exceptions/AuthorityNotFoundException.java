package demo.dao.exceptions;

public class AuthorityNotFoundException extends RuntimeException {
  public AuthorityNotFoundException(String email) {
    super("Could not find Authority with the email of " + email);
  }

	public AuthorityNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
