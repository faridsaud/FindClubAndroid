package ec.edu.epn.findclub;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;

import ec.edu.epn.findclub.Application.MyApp;
import ec.edu.epn.findclub.VO.Discoteca;

public class DiscotecaIngresar extends AppCompatActivity {
    ImageView discoImageImgView;
    Uri imageUri=Uri.parse("android:drawable/ic_menu_camera");
    String nombreImagen;
    File file;
    Discoteca disco = new Discoteca();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discoteca_ingresar);
        discoImageImgView = (ImageView) findViewById(R.id.imageView2);

        discoImageImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/png");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccione la imagen de la discoteca"), 1);
            }

        });
    }
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageUri = data.getData();
                discoImageImgView.setImageURI(data.getData());
            }
        }
        ContentResolver cr=this.getContentResolver();
        Bitmap bit = null;
        try{
            bit = android.provider.MediaStore.Images.Media.getBitmap(cr,imageUri);
            ExifInterface exif = new ExifInterface(file.getAbsolutePath());
            Matrix matrix = new Matrix();
            bit = Bitmap.createBitmap(bit,0,0,bit.getWidth(), bit.getHeight(), matrix, true);
        }catch (Exception e){
            e.printStackTrace();
        }
        discoImageImgView.setImageBitmap(bit);
    }

    public void registrarDisco(View view) {
        new InvocaServicioREST().execute();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class InvocaServicioREST extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                EditText nombre = (EditText)findViewById(R.id.editText14);
                EditText descripcion = (EditText)findViewById(R.id.editText15);
                MyApp userURL=((MyApp) getApplication());
                String email = userURL.getUsuarioLogeado().getEmail();
                final String url = userURL.getUrlServicio() +"/ServiceDiscoteca/Ingresar/"+nombre.getText().toString() +"/"+ email +"/"+descripcion.getText().toString()+"/Image";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getForObject(url, Void.class);
                Toast.makeText( getApplicationContext(), "Se ha registrado la discoteca " + nombre.getText().toString(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Cliente Rest", e.getMessage(), e);
            }
            return null;
        }
        protected void onPostExecute() {
            Log.i("Discoteca", "Discoteca ingresada con exito ");
            }

    }
}
