package exceptions;

/**
 * [EXCEPTIONS] Insufficient Funds Exception.
 * Purpose: Thrown when an account balance cannot cover an escrow or transaction amount.
 * Syllabus: Custom Checked Exception, business rule enforcement.
 */
public class InsufficientFundsException extends PrizzAppException {
    public InsufficientFundsException(String message) {
        super("PRIZZ_ERR_INSUFFICIENT_FUNDS", message, null);
    }
}

