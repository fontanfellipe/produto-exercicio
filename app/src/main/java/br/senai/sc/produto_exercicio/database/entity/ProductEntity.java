package br.senai.sc.produto_exercicio.database.entity;

import android.provider.BaseColumns;

public final class ProductEntity implements BaseColumns {

    private ProductEntity() {}

    public static final String TABLE_NAME = "produto";
    public static final String COLUMN_NAME_NOME = "nome";
    public static final String COLUMN_NAME_VALOR = "valor";

}
