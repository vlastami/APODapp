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

/**
 * Třída ApodAdapter je adaptér pro RecyclerView, který zobrazuje obrázky NASA APOD.
 */
public class ApodAdapter extends RecyclerView.Adapter<ApodAdapter.ViewHolder> {
    private List<ApodImage> apodImages;  // Seznam všech obrázků APOD
    private final Context context;  // Kontext aplikace
    private final OnItemClickListener listener;  // Listener pro kliknutí na položku
    private List<ApodImage> filteredImages;  // Filtrovaný seznam obrázků APOD

    /**
     * Rozhraní pro detekci kliknutí na položku.
     */
    public interface OnItemClickListener {
        void onItemClick(ApodImage image);
    }

    /**
     * Konstruktor třídy ApodAdapter.
     * @param context Kontext aplikace.
     * @param apodImages Seznam obrázků APOD.
     * @param listener Listener pro kliknutí na položku.
     */
    public ApodAdapter(Context context, List<ApodImage> apodImages, OnItemClickListener listener) {
        this.context = context;
        this.apodImages = apodImages;
        this.filteredImages = new ArrayList<>(apodImages);
        this.listener = listener;
    }

    /**
     * Filtruje seznam obrázků podle zadaného textu.
     * @param text Text pro filtrování obrázků podle popisu.
     */
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

    /**
     * Nastaví nový seznam obrázků APOD a filtruje je podle typu média.
     * @param images Nový seznam obrázků APOD.
     */
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

    /**
     * Třída ViewHolder poskytuje referenci na zobrazení pro každou položku v RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewThumbnail;

        /**
         * Konstruktor ViewHolder.
         * @param itemView Pohled položky v RecyclerView.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
        }
    }
}
