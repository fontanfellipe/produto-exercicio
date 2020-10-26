package br.senai.sc.produto_exercicio.database.contract;

import br.senai.sc.produto_exercicio.database.entity.ProductEntity;

public final class ProductContract {

    private ProductContract() {}

    public static final String criarTabela() {
        return "CREATE TABLE" + ProductEntity.TABLE_NAME + " (" +
                ProductEntity._ID + " INTEGER PRIMARY KEY," +
                ProductEntity.COLUMN_NAME_NOME + " TEXT," +
                ProductEntity.COLUMN_NAME_VALOR + " REAL)";
    }

    public static final String removerTabela() {
        return "DROP TABLE IF EXISTS " + ProductEntity.TABLE_NAME;
    }

}
