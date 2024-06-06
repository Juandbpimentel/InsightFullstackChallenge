package br.ufc.quixada.SupplierApplicationInsight.types.exceptions;

public class SupplyNotFoundException extends IllegalArgumentException {
    public SupplyNotFoundException(final String message) {
        super(message);
    }
}
