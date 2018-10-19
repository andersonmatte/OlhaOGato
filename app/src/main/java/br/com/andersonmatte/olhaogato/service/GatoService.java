package br.com.andersonmatte.olhaogato.service;

import br.com.andersonmatte.olhaogato.entidade.ImagensGato;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.SimpleXmlConverterFactory;
import retrofit2.http.GET;

public interface GatoService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://thecatapi.com/api/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    @GET("images/get?format=xml&api_key=MzQ4NDcz&type=jpg&results_per_page=10")
    Call<ImagensGato> getGatos();

}
