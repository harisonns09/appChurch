package com.church.appChurch.enums;

public enum TipoValorPagamento {
    INTEGRAL("Integral"),
    PROMOCIONAL("Promocional");

    private String tipoValorPagamento;

    TipoValorPagamento(String tipoValorPagamento) {
        this.tipoValorPagamento = tipoValorPagamento;
    }

    public String getTipoValorPagamento() {
        return tipoValorPagamento;
    }
}
