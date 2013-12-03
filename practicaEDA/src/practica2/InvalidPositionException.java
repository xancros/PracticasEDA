package practica2;

/**
 * Thrown when a position is determined to be invalid.
 */
// A run-time exception for invalid positions
public class InvalidPositionException extends RuntimeException {  
  public InvalidPositionException(String err) {
    super(err);
  }
  public InvalidPositionException() {
    /* default constructor */
  }
}
