package br.ufc.quixada.SupplierApplicationInsight.types.exceptions;

public class AdminAlreadyExistsException extends UserAlreadyExistsException{
    public AdminAlreadyExistsException(String message) {
        super(message);
    }
}
