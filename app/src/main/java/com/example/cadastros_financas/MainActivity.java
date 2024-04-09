package com.example.cadastros_financas;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Categoria> categorias;
    CadastroCategoriaFragment fragCadastroCategoria;
    ListaCategoriaFragment fragListaCategoria;
    Categoria categoriaSelecionada = null;


    //-----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragCadastroCategoria = (CadastroCategoriaFragment) getFragmentManager().findFragmentByTag("fragCadastroCategoria");
        fragListaCategoria = (ListaCategoriaFragment) getFragmentManager().findFragmentByTag("fragListaCategorias");
        if (savedInstanceState != null) {
            categorias = (ArrayList<Categoria>) savedInstanceState.getSerializable("listaCategorias");
        }
        if (categorias == null) {
            categorias = new ArrayList<Categoria>();
        }
        fragListaCategoria.setCategorias(categorias);
    }

    //-----------------------------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle bld) {
        super.onSaveInstanceState(bld);
        bld.putSerializable("listaCategorias", categorias);
        bld.putSerializable("categoriaSelecionada", categoriaSelecionada);
    }

    //-----------------------------------------------------------------
    public void adicionar(View v) {
        if (fragCadastroCategoria != null) {
            Categoria categoria = fragCadastroCategoria.validarDados();
            if (categoria != null) {
                if (categoriaSelecionada != null) {
                    this.confirmaEdit(categoriaSelecionada, categoria);
                    Log.d("CATEGORIA", "Alterada");
                } else {
                    this.confirmaAdd(categoria);
                    Log.d("CATEGORIA", "Adicionada");
                }
                categoriaSelecionada = null;
            }
        }
    }

    //----------------------------------------------------------------
    public void removerCategoria(View v) {
        if (fragListaCategoria != null) {
            Categoria categoria = fragListaCategoria.getCategoriaSelecionada();
            if (categoria == null) {
                Toast.makeText(this, "Selecione a categoria que deseja remover", Toast.LENGTH_SHORT).show();
            } else {
                if (!fragListaCategoria.remover(categoria)) {
                    Toast.makeText(this, "Erro inesperado ao remover categoria, verificar Logs!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //----------------------------------------------------------------
    public void editarCategoria(View v) {
        if (fragListaCategoria != null) {
            Categoria categoria = fragListaCategoria.getCategoriaSelecionada();
            if (categoria == null) {
                Toast.makeText(this, "Selecione a categoria a editar", Toast.LENGTH_SHORT).show();
            } else {
                fragCadastroCategoria.ajustarEdicao(categoria);
                categoriaSelecionada = categoria;
            }
        }
    }

    //----------------------------------------------------------------
    public void confirmaAdd(Categoria categoria) {
        AlertDialog.Builder bld = new AlertDialog.Builder(MainActivity.this);
        bld.setTitle("Confirmar");
        bld.setMessage("Confirma a adição da categoria: " + categoria.getDescricao() + " ?");

        bld.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fragListaCategoria.adicionar(categoria);
            }
        });

        bld.setNeutralButton("Cancelar", null);

        bld.show();
    }

    //----------------------------------------------------------------
    public void confirmaEdit(Categoria categoriaEditando, Categoria categoria) {
        AlertDialog.Builder bld = new AlertDialog.Builder(MainActivity.this);
        bld.setTitle("Confirmar");
        bld.setMessage("Confirma a alteração da categoria: " + categoria.getDescricao() + " ?");

        bld.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fragListaCategoria.substituir(categoriaEditando, categoria);
            }
        });

        bld.setNeutralButton("Cancelar", null);

        bld.show();
    }

    //----------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //----------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.item_addConta){
            categoriaSelecionada = fragListaCategoria.getCategoriaSelecionada();
            addConta(categoriaSelecionada);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    //----------------------------------------------------------------
    public void addConta(Categoria categoria){
        if(categoria == null){
            Toast.makeText(this, "Selecione uma categoria para adicionar uma conta", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent it = new Intent(this, ContasActivity.class);
        it.putExtra("categoria", categoria);
        startActivityForResult(it, 123);
    }

    @Override
    public void onActivityResult(int requisicao, int resposta, Intent dados){
        super.onActivityResult(requisicao,resposta,dados);
        if(requisicao == 123 && resposta == RESULT_OK){
            ArrayList<Conta> contas = (ArrayList<Conta>) dados.getSerializableExtra("contas");

            Categoria categoriaContas = new Categoria(categoriaSelecionada.getDescricao());
            categoriaContas.setContas(contas);
            fragListaCategoria.substituir(categoriaSelecionada, categoriaContas);

            categoriaSelecionada = null;
        }
    }
}