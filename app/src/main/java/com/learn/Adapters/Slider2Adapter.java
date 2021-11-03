package com.learn.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.learn.booklistapp.BooksDetails;
import com.learn.booklistapp.BooksInfo;
import com.learn.booklistapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Slider2Adapter extends RecyclerView.Adapter<Slider2Adapter.Slider2ViewHolder> {

    private List<BooksInfo> list;
    private RecyclerView recyclerView;
    private Context context;
    private int layoutId;
    private int uniqueL;

    public Slider2Adapter(List<BooksInfo> list, RecyclerView recyclerView, Context context,int layoutId,int uniqueL) {
        this.list = list;
        this.recyclerView = recyclerView;
        this.context = context;
        this.layoutId = layoutId;
        this.uniqueL = uniqueL;

    }

    @NonNull
    @Override
    public Slider2Adapter.Slider2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Slider2ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Slider2ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        BooksInfo currBook = list.get(pos);
        holder.setInfo(list.get(pos));
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

    class Slider2ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView tittle;
        private TextView author;

        private ImageView magazineImage;
        private TextView magazineText;

        public Slider2ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.book_image_slider2);
            tittle = itemView.findViewById(R.id.bookName_slider2);

            magazineImage = itemView.findViewById(R.id.magazine_image);
            magazineText = itemView.findViewById(R.id.magazine_name);
           // author = itemView.findViewById(R.id.author_slider2);
        }

        void setInfo(BooksInfo sliderItem) {

            if (uniqueL == 1) {
                try {
                    Glide.with(imageView.getContext())
                            .load(new URL(sliderItem.getThumbnailLink()))
                            .into(imageView);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                tittle.setText(sliderItem.getBookTitle());

                //  author.setText("By:- " + sliderItem.getAuthors().get(0));
            }

            else if(uniqueL == 2) {
                try {
                    Glide.with(magazineImage.getContext())
                            .load(new URL(sliderItem.getThumbnailLink()))
                            .into(magazineImage);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                magazineText.setText(sliderItem.getBookTitle());
            }


        }

    }
}
