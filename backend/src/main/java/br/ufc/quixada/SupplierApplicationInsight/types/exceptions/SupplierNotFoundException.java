package br.ufc.quixada.SupplierApplicationInsight.types.exceptions;

public class SupplierNotFoundException extends IllegalArgumentException {
    public SupplierNotFoundException(final String message) {
        super(message);
    }
}
