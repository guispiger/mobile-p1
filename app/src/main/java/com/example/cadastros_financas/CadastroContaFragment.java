package com.example.cadastros_financas;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroContaFragment extends Fragment {
    EditText edDescricao;
    EditText edVencimento;
    EditText edValor;
    TextView edDescricaoCategoria;
    //-----------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //-----------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cadastro_conta, container, false);
        edDescricao = (EditText) v.findViewById(R.id.edDescricao);
        edVencimento = (EditText) v.findViewById(R.id.edVencimento);
        edValor = (EditText) v.findViewById(R.id.edValor);
        edDescricaoCategoria = (TextView) v.findViewById(R.id.edDescricaoCategoria);
        return v;
    }

    //-----------------------------------------------------------------
    public Conta validarDados(Categoria categoria){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String descricao = edDescricao.getText().toString().trim();
        Date vencimento = null;
        try {
            vencimento = sdf.parse(edVencimento.getText().toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Double valor = Double.parseDouble(edValor.getText().toString());

        if(descricao == null || descricao.isEmpty() || vencimento == null || valor == null){
            Toast.makeText(getActivity(), "É necessário informar todos os dados!", Toast.LENGTH_SHORT).show();
            return null;
        }
        edDescricao.setText("");
        edVencimento.setText("");
        edValor.setText("");

        return new Conta(descricao, vencimento, valor, categoria);
    }

    //-----------------------------------------------------------------
    public void ajustarEdicao(Conta c){
        edDescricao.setText(c.getDescricao());
        edVencimento.setText(c.getVencimento().toString());
        edValor.setText(Double.toString(c.getValor()));
    }

    //-----------------------------------------------------------------
    public void setEdDescricaoCategoria(String descricaoCategoria){
        edDescricaoCategoria.setText(descricaoCategoria);
    }
}