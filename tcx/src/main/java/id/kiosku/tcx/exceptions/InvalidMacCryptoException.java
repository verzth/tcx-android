package id.kiosku.tcx.exceptions;

public class InvalidMacCryptoException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid Mac";
    }
}
