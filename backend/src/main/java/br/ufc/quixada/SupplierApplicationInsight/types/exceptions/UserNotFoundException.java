package br.ufc.quixada.SupplierApplicationInsight.types.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(final String message) {
        super(message);
    }
}
