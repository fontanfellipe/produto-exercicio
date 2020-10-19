package br.senai.sc.produto_exercicio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.security.ProtectionDomain;
import java.util.ArrayList;

import br.senai.sc.produto_exercicio.modelo.Produto;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_NOVOPRODUTO = 1;
    private final int RESULT_CODE_NOVOPRODUTO = 10;
    private final int REQUEST_CODE_EDITARPRODUTO = 2;
    private final int REQUEST_CODE_PRODUTOEDITADO = 11;

    private ListView listViewProdutos;
    private ArrayAdapter<Produto> adapterProdutos;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Produtos");

        listViewProdutos = findViewById(R.id.listView_produtos);
        ArrayList<Produto> produtos = new ArrayList<Produto>();

        adapterProdutos = new ArrayAdapter<Produto>(MainActivity.this, android.R.layout.simple_list_item_1, produtos);

        listViewProdutos.setAdapter(adapterProdutos);

        definirOnClickListenerListView();
        definirLongClickListenerListView();
    }

    private void definirOnClickListenerListView() {
        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Produto produtoClicado = adapterProdutos.getItem(position);
               Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
               intent.putExtra("produtoEdicao", produtoClicado);
               startActivityForResult(intent, REQUEST_CODE_EDITARPRODUTO );
            }
        });
    }


    public void onClickNovoProduto(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_NOVOPRODUTO);
    }

    private void definirLongClickListenerListView() {
        listViewProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Produto produtoLongClick = adapterProdutos.getItem(position);
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapterProdutos.remove(produtoLongClick);
                                adapterProdutos.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_NOVOPRODUTO && resultCode == RESULT_CODE_NOVOPRODUTO) {
            Produto produto = (Produto) data.getExtras().getSerializable("novoProduto");
            produto.setId(++id);
            this.adapterProdutos.add(produto);
        } else if (requestCode == REQUEST_CODE_EDITARPRODUTO && resultCode == REQUEST_CODE_PRODUTOEDITADO) {
            Produto produtoEditado = (Produto) data.getExtras().getSerializable("produtoEditado");
            for(int i =0; i <adapterProdutos.getCount(); i++){
                Produto produto = adapterProdutos.getItem(i);
                if(produto.getId() == produtoEditado.getId()){
                    adapterProdutos.remove(produto);
                    adapterProdutos.insert(produtoEditado, i);
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}