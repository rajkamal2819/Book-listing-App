package com.learn.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.learn.booklistapp.BooksDetails;
import com.learn.booklistapp.BooksInfo;
import com.learn.booklistapp.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{

    private List<BooksInfo> list;
    private ViewPager2 viewPager2;
    private Context context;

    public SliderAdapter(List<BooksInfo> list, ViewPager2 viewPager2, Context context) {
        this.list = list;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        BooksInfo currBook = list.get(pos);
        holder.setImageView(list.get(pos));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, BooksDetails.class);
                i.putExtra("tittle", currBook.getBookTitle());
                i.putExtra("publisher", currBook.getPublisher());
                i.putExtra("publishingDate", currBook.getPublishingDate());
                i.putExtra("description", currBook.getDescription());
                i.putExtra("pageCount", currBook.getPageCount());
                i.putExtra("authors", currBook.getAuthors());
                i.putExtra("thumbnailLink", currBook.getThumbnailLink());
                i.putExtra("language", currBook.getLanguage());
                i.putExtra("previewLink", currBook.getPreviewLink());
                i.putExtra("buyingLink", currBook.getBuyingLink());
                i.putExtra("rating", currBook.getRating());
                i.putExtra("ratingCount", currBook.getRatingCount());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.roundedImageView);
        }

        void setImageView(BooksInfo sliderItem){

            try {
                Glide.with(imageView.getContext())
                        .load(new URL(sliderItem.getThumbnailLink()))
                        .into(imageView);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }


    }



}