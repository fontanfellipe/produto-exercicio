package br.senai.sc.produto_exercicio.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.senai.sc.produto_exercicio.database.entity.ProductEntity;
import br.senai.sc.produto_exercicio.modelo.Produto;

public class ProdutoDAO {

    private final String SQL_LISTAR_TODOS = "SELECT * FROM " + ProductEntity.TABLE_NAME;
    private DBGateway dbGateway;

    public ProdutoDAO(Context context){
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Produto produto) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductEntity.COLUMN_NAME_NOME, produto.getNome());
        contentValues.put(ProductEntity.COLUMN_NAME_VALOR, produto.getValor());

        if(produto.getId() > 0){
            return dbGateway.getDatabase().update(ProductEntity.TABLE_NAME,
                    contentValues,
                    ProductEntity._ID + "=?",
                    new String[]{String.valueOf(produto.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(ProductEntity.TABLE_NAME, null, contentValues) > 0;
    }

    public List<Produto> listar() {
        List<Produto> produtos = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ProductEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_NOME));
            Float valor = cursor.getFloat(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_VALOR));

            produtos.add(new Produto(id, nome, valor));
        }
        cursor.close();
        return produtos;
    }

    public int deleteItem(Produto produto){
       return dbGateway.getDatabase().delete(ProductEntity.TABLE_NAME, ProductEntity._ID + "=?", new String[]{String.valueOf(produto.getId())});

    }

}
