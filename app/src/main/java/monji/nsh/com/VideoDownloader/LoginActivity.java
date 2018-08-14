package monji.nsh.com.VideoDownloader;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {


    String baseURL;

    Button submit;
    TextView txt, reg;
    EditText edtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        baseURL = getString(R.string.server_address);

        submit = (Button) findViewById(R.id.btnLogin);
        txt = (TextView) findViewById(R.id.teLogin);
        reg = (TextView) findViewById(R.id.teLoginReg);
        edtxt = (EditText) findViewById(R.id.edLogin);

        submit.setTypeface(ProjectTools.getRoyaFont(LoginActivity.this));
        txt.setTypeface(ProjectTools.getRoyaFont(LoginActivity.this));
        reg.setTypeface(ProjectTools.getRoyaFont(LoginActivity.this));
        edtxt.setTypeface(ProjectTools.getRoyaFont(LoginActivity.this));

        SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.SharePreferences), 0);
        final SharedPreferences.Editor editor = pref.edit();

        if(pref.getString("Token", null) != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, String> data = new HashMap<>();
                data.put("mobile", edtxt.getText().toString());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Rest.api_Items service = retrofit.create(Rest.api_Items.class);

                Call<RegisterItems> itemsCall = service.login(data);

                itemsCall.enqueue(new Callback<RegisterItems>() {
                    @Override
                    public void onResponse(Call<RegisterItems> call, Response<RegisterItems> response) {
                        String str = response.body().getResult();
                        if (str != null) {
                            if(str.equals("1")){

                                editor.putString("mobile", edtxt.getText().toString());
                                editor.commit();


                                Intent intent = new Intent(LoginActivity.this, AfterLoginActivity.class);
                                intent.putExtra("mobile", edtxt.getText().toString());
                                startActivity(intent);

                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "شماره وارد شده نادرست است", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterItems> call, Throwable throwable) {
                        Toast.makeText(LoginActivity.this, "خطا در ارتباط با شبکه", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
