package exceptions;
//(Omar 3/4)
public class InvalidTargetException extends GameActionException{

	public InvalidTargetException() {
		super();
	}

	public InvalidTargetException(String s) {
		super(s);
	}
	
}
