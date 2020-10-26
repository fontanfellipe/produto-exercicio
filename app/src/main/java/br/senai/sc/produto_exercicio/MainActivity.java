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

import br.senai.sc.produto_exercicio.database.ProdutoDAO;
import br.senai.sc.produto_exercicio.modelo.Produto;

public class MainActivity extends AppCompatActivity {

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

        definirOnClickListenerListView();
        definirLongClickListenerListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());
        adapterProdutos = new ArrayAdapter<Produto>(MainActivity.this, android.R.layout.simple_list_item_1, produtoDAO.listar());
        listViewProdutos.setAdapter(adapterProdutos);
    }

    private void definirOnClickListenerListView() {
        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Produto produtoClicado = adapterProdutos.getItem(position);
               Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
               intent.putExtra("produtoEdicao", produtoClicado);
               startActivity(intent);
            }
        });
    }

    public void onClickNovoProduto(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
        startActivity(intent);
    }

    private void definirLongClickListenerListView() {
        listViewProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Produto produtoLongClick = adapterProdutos.getItem(position);
                final ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());
                
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapterProdutos.remove(produtoLongClick);
                                adapterProdutos.notifyDataSetChanged();
                                produtoDAO.deleteItem(produtoLongClick);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }





}