package br.com.andersonmatte.olhaogato.activities;

import android.os.Bundle;

import br.com.andersonmatte.olhaogato.R;
import br.com.andersonmatte.olhaogato.base.ActivityMenuBase;

public class HomeActivity extends ActivityMenuBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home);
    }

}
