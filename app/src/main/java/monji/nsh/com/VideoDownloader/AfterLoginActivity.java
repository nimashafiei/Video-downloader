package monji.nsh.com.VideoDownloader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class AfterLoginActivity extends AppCompatActivity {

    String baseURL, mobileNum;

    Button submit;
    TextView txt;
    EditText edtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        baseURL = getString(R.string.server_address);

        Bundle bl = getIntent().getExtras();
        if(bl != null){
            mobileNum = bl.getString("mobile");
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.SharePreferences), 0);
        final SharedPreferences.Editor editor = pref.edit();

        submit = (Button) findViewById(R.id.btnAfterLogin);
        txt = (TextView) findViewById(R.id.teAfterLogin);
        edtxt = (EditText) findViewById(R.id.edAfterLogin);

        submit.setTypeface(ProjectTools.getRoyaFont(AfterLoginActivity.this));
        txt.setTypeface(ProjectTools.getRoyaFont(AfterLoginActivity.this));
        edtxt.setTypeface(ProjectTools.getRoyaFont(AfterLoginActivity.this));



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> data = new HashMap<>();
                data.put("mobile", mobileNum);
                data.put("password", edtxt.getText().toString());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Rest.api_Items service = retrofit.create(Rest.api_Items.class);

                Call<RegisterItems> itemsCall = service.afterLogin(data);

                itemsCall.enqueue(new Callback<RegisterItems>() {
                    @Override
                    public void onResponse(Call<RegisterItems> call, Response<RegisterItems> response) {

                        try {
                            String str = response.body().getResult();
                            String name = response.body().getName();
                            Log.e("Msg", str);
                            if (str != null) {
                                if (str.equals("invalid_credentials")) {
                                    Toast.makeText(AfterLoginActivity.this, "کد وارد شده نامعتبر می باشد.", Toast.LENGTH_SHORT).show();
                                } else if (str.equals("could_not_create_token")) {
                                    Toast.makeText(AfterLoginActivity.this, "خطا در ارتباط با شبکه", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("Token", str);
                                    editor.putString("Token", str);
                                    editor.putString("Name", name);
                                    editor.commit();

                                    Intent intent = new Intent(AfterLoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                Log.e("Message", "null");
                            }
                        }
                        catch (Exception ex){
                            Log.e("Exeptipn" , ex.getMessage());
                            Toast.makeText(AfterLoginActivity.this, "خطا در ارتباط با شبکه", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterItems> call, Throwable throwable) {
                        Log.e("Message", "Failed  " + throwable.getMessage());
                    }
                });
            }
        });

    }
}
