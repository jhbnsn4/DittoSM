package dittoSM.model;

import java.util.Objects;

/**
 * MK: added MyCustomMessage class to customize http request messages
 * @author mtkee
 *
 */
public class MyCustomMessage {

	private String message;
	private String otherPossibleInformation;
	
	public MyCustomMessage() {
	}

	public MyCustomMessage(String message, String otherPossibleInformation) {
		super();
		this.message = message;
		this.otherPossibleInformation = otherPossibleInformation;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOtherPossibleInformation() {
		return otherPossibleInformation;
	}

	public void setOtherPossibleInformation(String otherPossibleInformation) {
		this.otherPossibleInformation = otherPossibleInformation;
	}

	@Override
	public String toString() {
		return "MessageCustomizedForThisExample [message=" + message + ", otherPossibleInformation="
				+ otherPossibleInformation + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(message, otherPossibleInformation);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyCustomMessage other = (MyCustomMessage) obj;
		return Objects.equals(message, other.message)
				&& Objects.equals(otherPossibleInformation, other.otherPossibleInformation);
	}
	
	
}
