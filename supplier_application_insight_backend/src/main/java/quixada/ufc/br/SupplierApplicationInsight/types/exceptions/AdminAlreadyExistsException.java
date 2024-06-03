package quixada.ufc.br.SupplierApplicationInsight.types.exceptions;

public class AdminAlreadyExistsException extends UserAlreadyExistsException{
    public AdminAlreadyExistsException(String message) {
        super(message);
    }
}
