package monji.nsh.com.VideoDownloader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CityAdapter extends BaseAdapter {

    private ArrayList<CityResult> mCities;
    private Context mContext;

    LayoutInflater inflter;

    public CityAdapter(ArrayList<CityResult> cities, Context context){
        this.mCities = cities;
        this.mContext = context;

        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return mCities.size();
    }

    @Override
    public Object getItem(int position) {
        return mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(mCities.get(position).getCityID());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        view = inflter.inflate(R.layout.city_list_layout, null);
        TextView names = (TextView) view.findViewById(R.id.cityName);
        names.setText(mCities.get(i).getCityName());
        return view;
    }
}