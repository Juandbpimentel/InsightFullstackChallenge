package br.ufc.quixada.SupplierApplicationInsight.types.enums;

import lombok.Getter;

@Getter
public enum SupplyType {
    PRODUCTS("PRODUCTS"),
    SERVICES("SERVICES"),
    RAW_MATERIALS("RAW_MATERIALS"),
    OTHERS("OTHERS");

    private final String supplyType;

    SupplyType(String supplyType) {
        this.supplyType = supplyType;
    }
}
