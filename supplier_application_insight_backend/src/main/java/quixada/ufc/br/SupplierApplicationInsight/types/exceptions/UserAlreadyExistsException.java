package quixada.ufc.br.SupplierApplicationInsight.types.exceptions;

import javax.naming.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException {
    public UserAlreadyExistsException(final String message) {
        super(message);
    }
}
