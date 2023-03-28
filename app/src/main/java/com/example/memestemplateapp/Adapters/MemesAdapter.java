package com.example.memestemplateapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memestemplateapp.MemeActivity;
import com.example.memestemplateapp.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MemesAdapter extends RecyclerView.Adapter<MemesAdapter.ViewHolder> {

    Context context;
    List<String> urlList;

    public MemesAdapter(Context context, List<String> urlList) {
        this.context = context;
        this.urlList = urlList;
    }

    @Override
    public MemesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemesAdapter.ViewHolder holder, int position) {
        Picasso.get().load(urlList.get(position)).placeholder(R.drawable.error).into(holder.meme_img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MemeActivity.class);
                intent.putStringArrayListExtra("memes", (ArrayList<String>) urlList);
                intent.putExtra("pos", holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView meme_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meme_img = itemView.findViewById(R.id.meme_img);
        }
    }
}
