package com.puce.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    ImageButton btnReBuscar;
    Button      btnEliminar,
                btnTodosBuscar,
                btnAgregar,
                btnEditar;
    EditText    etReBuscar,
                etNombre,
                etProducto,
                etCant,
                etPrize;

    RecyclerView    rvUsuarios;
    List<User>   listaUsuarios = new ArrayList<>();
    AdaptadorUsuarios adaptadorUsuarios;
    Retrofit retrofit;
    APIRest api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etReBuscar = findViewById(R.id.etReBuscar);
        etNombre    = findViewById(R.id.etNombre);
        etProducto  = findViewById(R.id.etProducto);
        etCant  = findViewById(R.id.etCant);
        etPrize  = findViewById(R.id.etPrize);

        btnReBuscar     = findViewById(R.id.btnReBuscar);
        btnEliminar     = findViewById(R.id.btnEliminar);
        btnTodosBuscar  = findViewById(R.id.btnTodosBuscar);
        btnAgregar      = findViewById(R.id.btnAgregar);
        btnEditar       = findViewById(R.id.btnEditar);
        rvUsuarios      = findViewById(R.id.rvUsuarios);
        rvUsuarios.setLayoutManager(new GridLayoutManager(this, 1));
        retrofit = new AdaptadorRetrofit().getAdaptador();
        api      = retrofit.create(APIRest.class);
        getUsuarios(api);

        btnReBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etReBuscar.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Ingrese Record a Buscar", Toast.LENGTH_LONG).show();
                } else {
                    getUsuario(api, etReBuscar.getText().toString());
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etReBuscar.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Ingrese Record a Eliminar", Toast.LENGTH_LONG).show();
                } else {
                    eliminarUsuario(api, etReBuscar.getText().toString());
                }
            }
        });

        btnTodosBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUsuarios(api);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((etProducto.getText().toString().equals(""))) {
                    Toast.makeText(MainActivity.this, "Complete la información", Toast.LENGTH_LONG).show();
                } else {
                    agregarUsuario(api, etNombre.getText().toString(),
                            etProducto.getText().toString(),
                            etPrize.getText().toString(),
                            etCant.getText().toString());
                    Toast.makeText(MainActivity.this, "Se ingreso el dato", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((etReBuscar.getText().toString().equals("")) || (etNombre.getText().toString().equals("")) || (etProducto.getText().toString().equals(""))) {
                    Toast.makeText(MainActivity.this, "Complete la información", Toast.LENGTH_LONG).show();
                } else {
                    editarUsuario(api, etReBuscar.getText().toString(), etNombre.getText().toString(), etProducto.getText().toString());
                }
            }
        });
    }


    public void getUsuario(final APIRest api, String record) {
        Log.d("TAG", "ENTRA METODO");
        listaUsuarios.clear();
        Call<User> call = api.obtenerUsuario(record);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG", "Error Body "+response.errorBody());
                Log.d("TAG", "Mensaje "+response.message());
                Log.d("TAG", "RAW "+response.raw());
                Log.d("TAG", "Headers "+response.headers());
                switch (response.code()) {
                    case 200:
                        listaUsuarios.add(response.body());
                        etReBuscar.setText("");
                        adaptadorUsuarios = new AdaptadorUsuarios(MainActivity.this, listaUsuarios);
                        rvUsuarios.setAdapter(adaptadorUsuarios);
                        break;
                    case 204:
                        Toast.makeText(MainActivity.this, "No existe ese registro", Toast.LENGTH_SHORT).show();
                        etReBuscar.setText("");
                        getUsuarios(api);
                        break;
                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                System.out.println("Error "+t);
            }
        });
    }

    public void getUsuarios(APIRest api) {
        listaUsuarios.clear();
        Call<List<User>> call = api.obtenerUsuarios();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("TAG", "Error Body "+response.errorBody());
                Log.d("TAG", "Mensaje "+response.message());
                Log.d("TAG", "RAW "+response.raw());
                Log.d("TAG", "Headers "+response.headers());
                listaUsuarios = new ArrayList<User>(response.body());
                adaptadorUsuarios = new AdaptadorUsuarios(MainActivity.this, listaUsuarios);
                rvUsuarios.setAdapter(adaptadorUsuarios);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("TAG","Error "+t);
            }
        });
    }

    public void eliminarUsuario(final APIRest api, String record) {
        listaUsuarios.clear();
        Call<Void> call = api.eliminarUsuario(record);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(MainActivity.this, "Se elimino correctamente", Toast.LENGTH_SHORT);
                        etReBuscar.setText("");
                        getUsuarios(api);
                        break;
                    case 204:
                        Toast.makeText(MainActivity.this, "No se elimino el registro", Toast.LENGTH_SHORT).show();
                        etReBuscar.setText("");
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void agregarUsuario(final APIRest api, String usuario, String producto, String precio, String cantidad) {
        listaUsuarios.clear();
        User user = new User();
        user.setName(usuario);
        user.setProduct(producto);
        user.setPrize(precio);
        user.setCant(cantidad);

        Call<Void> call = api.agregarUsuario(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("TAG", "Codigo "+response.code());
                Log.d("TAG", "Body "+response.body());
                Log.d("TAG", "Error Body "+response.errorBody());
                Log.d("TAG", "Mensaje "+response.message());
                Log.d("TAG", "RAW "+response.raw());
                Log.d("TAG", "Headers "+response.headers());

                switch (response.code()) {

                    case 400:
                        Toast.makeText(MainActivity.this, "Faltaron campos.", Toast.LENGTH_SHORT);
                        etNombre.setText("");
                        etProducto.setText("");
                        etCant.setText("");
                        etPrize.setText("");
                        break;
                    case 200:
                        Toast.makeText(MainActivity.this, "Se inserto correctamente", Toast.LENGTH_SHORT);
                        etNombre.setText("");
                        etProducto.setText("");
                        etCant.setText("");
                        etPrize.setText("");
                        getUsuarios(api);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Error agregar "+t);
            }
        });
    }

    public void editarUsuario(final APIRest api, String record, String nombre, String producto) {
        listaUsuarios.clear();
        User usuario = new User();
        usuario.setName(nombre);
        usuario.setRecord(record);
        usuario.setProduct(producto);

        Call<Void> call = api.editarUsuario(usuario);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 400:
                        Toast.makeText(MainActivity.this, "No se puede editar.", Toast.LENGTH_SHORT).show();
                        etReBuscar.setText("");
                        etNombre.setText("");
                        etProducto.setText("");
                        break;
                    case 200:
                        Toast.makeText(MainActivity.this, "Se edito correctamente", Toast.LENGTH_SHORT).show();
                        etReBuscar.setText("");
                        etNombre.setText("");
                        etProducto.setText("");
                        getUsuarios(api);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}