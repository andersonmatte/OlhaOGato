package br.com.andersonmatte.olhaogato.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.andersonmatte.olhaogato.R;
import br.com.andersonmatte.olhaogato.entidade.Gato;

public class GatoAdapter extends ArrayAdapter<Gato> {

    private List<Gato> listaGatos;
    private Context context;
    private Boolean favorito;

    public GatoAdapter(Context context, List<Gato> listaGatosRecebida, Boolean favorito) {
        super(context, 0, listaGatosRecebida);
        this.listaGatos = listaGatosRecebida;
        this.context = context;
        this.favorito = favorito;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.lista_gatos, null);
        //Aqui ocorre a mágica no setTag onde é passado a posição da ListView!
        view.setTag(position);
        Button button = view.findViewById(R.id.botao_coracao);
        button.setTag(position);
        //Aqui ocorre a carga das imagens com o Picasso,
        //referência http://square.github.io/picasso/
        Gato gato = getItem(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.imagemGato);
        Picasso.get().load(gato.getUrl()).into(imageView);
        return view;
    }

}
