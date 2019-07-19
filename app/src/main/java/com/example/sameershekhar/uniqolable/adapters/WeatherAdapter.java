package com.example.sameershekhar.uniqolable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sameershekhar.uniqolable.R;
import com.example.sameershekhar.uniqolable.util.Utils;
import com.example.sameershekhar.uniqolable.models.Weather;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {

    private Weather weather;
    private Context context;
    private static int limit=8;

    public WeatherAdapter(Context context) {
        this.context = context;
    }

    public void setData(Weather weather){
        this.weather=weather;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(weather.getList().get(position*limit));
    }

    @Override
    public int getItemCount() {
        if(weather==null)
            return 0;
        else
            return weather.getCnt()/limit;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.day)
        TextView day;
        @BindView(R.id.dayTemp)
        TextView daytemp;
        @BindView(R.id.imageView)
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindData(com.example.sameershekhar.uniqolable.models.List list){
            day.setText(Utils.getFormatedTime(list.getDt()));
            daytemp.setText(Utils.getTemp(list.getMain().getTemp().toString()));
            imageView.setImageResource(Utils.getImageAdapter(list.getWeather().get(0).getMain()));


        }
    }




}
