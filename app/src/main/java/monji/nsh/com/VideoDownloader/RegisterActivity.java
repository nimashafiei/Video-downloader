package monji.nsh.com.VideoDownloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    String baseURL;
    Button btnRegister;
    TextView teTitle, teTitle2, teFn, teLn, teNic, teOrg, teMobile, teEmail, teAccountNumber, tePath, teAddress, teCityID, teBank, tePelak;
    EditText edFn, edLn, edNic, edOrg, edMobile, edEmail, edAccountNumber, edPath, edAddress, edBank, edPelak;
    Spinner edCityID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        baseURL = getString(R.string.server_address);

        init();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void register(){

//        String cityID = ((CityResult)edCityID.getSelectedItem()).getCityID();

        Map<String, String> data = new HashMap<>();
        data.put("name", edFn.getText().toString());
        data.put("lname", edLn.getText().toString());
        data.put("nic", edNic.getText().toString());
        data.put("orgId", edOrg.getText().toString());
        data.put("mobile", edMobile.getText().toString());
        data.put("email", edEmail.getText().toString());
        data.put("accountNumber", edAccountNumber.getText().toString());
        data.put("path", edPath.getText().toString());
        data.put("address", edAddress.getText().toString());
        data.put("city_id", "317");
        data.put("plateNumber", edPelak.getText().toString());
        data.put("bank", edBank.getText().toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Rest.api_Items service = retrofit.create(Rest.api_Items.class);

        Call<RegisterItems> itemsCall = service.register(data);

        itemsCall.enqueue(new Callback<RegisterItems>() {
            @Override
            public void onResponse(Call<RegisterItems> call, Response<RegisterItems> response) {
                Log.e("Message", response.body().toString());
                String str = response.body().getResult();
                String date = response.body().getResult();
                if (str.equals("1")) {
                    Toast.makeText(RegisterActivity.this, "ثبت با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    Log.e("Message", str);
                    Log.e("Message", date);

                } else if (str.equals("0")) {
                    Toast.makeText(RegisterActivity.this, "خطا در ثبت اطلاعات", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RegisterItems> call, Throwable throwable) {
                Log.e("Message", "Failed");
            }
        });
    }


    private void init(){
        teTitle = (TextView) findViewById(R.id.teTitle);
        teTitle2 = (TextView) findViewById(R.id.teTitle2);
        teFn = (TextView) findViewById(R.id.teFn);
        teLn = (TextView) findViewById(R.id.teLn);
        teNic = (TextView) findViewById(R.id.teNic);
        teOrg = (TextView) findViewById(R.id.teOrg);
        teMobile = (TextView) findViewById(R.id.teMobile);
        teEmail = (TextView) findViewById(R.id.teEmail);
        teAccountNumber = (TextView) findViewById(R.id.teAccountNumber);
        tePath = (TextView) findViewById(R.id.tePath);
        teAddress = (TextView) findViewById(R.id.teAddress);
        teCityID = (TextView) findViewById(R.id.teCity);
        tePelak = (TextView) findViewById(R.id.tePelak);
        teBank = (TextView) findViewById(R.id.teBank);

        edFn = (EditText) findViewById(R.id.edFname);
        edLn = (EditText) findViewById(R.id.edLname);
        edNic = (EditText) findViewById(R.id.edNic);
        edOrg = (EditText) findViewById(R.id.edOrg);
        edMobile = (EditText) findViewById(R.id.edMobile);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edAccountNumber = (EditText) findViewById(R.id.edAccountNumber);
        edPath = (EditText) findViewById(R.id.edPath);
        edAddress = (EditText) findViewById(R.id.edAddress);
        edPelak = (EditText) findViewById(R.id.edPelak);
        edBank = (EditText) findViewById(R.id.edBank);

        edCityID = (Spinner) findViewById(R.id.edCity);

        teTitle.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        teTitle2.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        teFn.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        teLn.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        teNic.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        teOrg.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        teMobile.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        teEmail.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        teAccountNumber.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        tePath.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        teAddress.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        teCityID.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        teBank.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        tePelak.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));teTitle.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));

        edFn.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        edLn.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        edNic.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        edOrg.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        edMobile.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        edEmail.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        edAccountNumber.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        edPath.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        edAddress.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        edBank.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));
        edPelak.setTypeface(ProjectTools.getRoyaFont(RegisterActivity.this));

        btnRegister = (Button) findViewById(R.id.btnRegister);

        fillCitySpinner();
    }

    private void fillCitySpinner(){
        ArrayList<CityResult> cities = new ArrayList<>();

        CityResult city = new CityResult();
        city.add("316", "لاهیجان");
        cities.add(city);

        city = new CityResult();
        city.add("317", "صومعه سرا");
        cities.add(city);

        city = new CityResult();
        city.add("318", "بندر انزلی");
        cities.add(city);

        city = new CityResult();
        city.add("319", "رشت");
        cities.add(city);

        city = new CityResult();
        city.add("320", "لنگرود");
        cities.add(city);

        city = new CityResult();
        city.add("321", "رودسر");
        cities.add(city);

        city = new CityResult();
        city.add("322", "املش");
        cities.add(city);

        city = new CityResult();
        city.add("323", "ماسال");
        cities.add(city);

        city = new CityResult();
        city.add("324", "شفت");
        cities.add(city);

        city = new CityResult();
        city.add("325", "رودبار");
        cities.add(city);

        city = new CityResult();
        city.add("326", "فومن");
        cities.add(city);

        city = new CityResult();
        city.add("327", "آستارا");
        cities.add(city);

        city = new CityResult();
        city.add("328", "سیاهکل");
        cities.add(city);

        city = new CityResult();
        city.add("329", "آستانه اشرفیه");
        cities.add(city);

        city = new CityResult();
        city.add("330", "رضوانشهر");
        cities.add(city);

        city = new CityResult();
        city.add("331", "طوالش");
        cities.add(city);

        CityAdapter adp = new CityAdapter(cities,
                RegisterActivity.this);
        edCityID.setAdapter(adp);

    }
}
