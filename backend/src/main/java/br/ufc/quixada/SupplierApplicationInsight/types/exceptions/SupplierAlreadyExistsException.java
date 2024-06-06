package br.ufc.quixada.SupplierApplicationInsight.types.exceptions;

public class SupplierAlreadyExistsException extends IllegalArgumentException {
    public SupplierAlreadyExistsException(final String message) {
        super(message);
    }
}
