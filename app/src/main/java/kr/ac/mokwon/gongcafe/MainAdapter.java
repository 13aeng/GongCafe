package kr.ac.mokwon.gongcafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {
    private ArrayList<CafeDTO> arrayList;
    private Context context;

    public MainAdapter(ArrayList<CafeDTO> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.CustomViewHolder holder, int position) {

        Glide.with(holder.imageView_main).load(arrayList.get(position).getImageUrl()).into(holder.imageView_main);
        holder.textView1.setText(arrayList.get(position).getCafeName());
        holder.textView2.setText(arrayList.get(position).getInfo());
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList !=null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView textView1;
        TextView textView2;
        ImageView imageView_main;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.textView1 = itemView.findViewById(R.id.textview1);
            this.textView2 = itemView.findViewById(R.id.textview2);
            this.imageView_main = itemView.findViewById(R.id.imageView_main);

        }
    }
}


