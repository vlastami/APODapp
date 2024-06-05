//java/com/example/apodapp/ui/ApodAdapter.java
package com.example.apodapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apodapp.R;
import com.example.apodapp.data.ApodImage;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ApodAdapter extends RecyclerView.Adapter<ApodAdapter.ViewHolder> {
    private List<ApodImage> apodImages;
    private Context context;
    private OnItemClickListener listener;
    private List<ApodImage> filteredImages;

    public interface OnItemClickListener {
        void onItemClick(ApodImage image);
    }

    public ApodAdapter(Context context, List<ApodImage> apodImages, OnItemClickListener listener) {
        this.context = context;
        this.apodImages = apodImages;
        this.filteredImages = new ArrayList<>(apodImages);
        this.listener = listener;
    }

    public void filter(String text) {
        filteredImages.clear();
        if (text.isEmpty()) {
            filteredImages.addAll(apodImages);
        } else {
            text = text.toLowerCase();
            for (ApodImage image : apodImages) {
                if (image.getExplanation().toLowerCase().contains(text)) {
                    filteredImages.add(image);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apod_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApodImage image = filteredImages.get(position);
        Glide.with(context).load(image.getUrl()).into(holder.imageViewThumbnail);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(image));
    }

    @Override
    public int getItemCount() {
        return filteredImages.size();  // getItemCount() vrací velikost filtrovaného seznamu
    }


    public void setApodImages(List<ApodImage> images) {
        List<ApodImage> filteredList = new ArrayList<>();
        for (ApodImage image : images) {
            if (!image.getUrl().contains("youtube.com") && !image.getUrl().contains("vimeo.com")) {
                filteredList.add(image);
            }
        }
        this.apodImages = filteredList;
        this.filteredImages = new ArrayList<>(filteredList);
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
        }
    }
}
