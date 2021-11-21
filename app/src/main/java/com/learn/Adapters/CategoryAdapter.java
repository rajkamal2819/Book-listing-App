package com.learn.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learn.booklistapp.CategoriesResultActivity;
import com.learn.booklistapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.Models.Categories;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.categoryViewHolder> {

    private ArrayList<Categories> list;
    private RecyclerView recyclerView;
    private Context context;

    public CategoryAdapter(ArrayList<Categories> list, RecyclerView recyclerView, Context context) {
        this.list = list;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryAdapter.categoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.categoryViewHolder holder, int position) {
        holder.setImageView(list.get(position));
        Log.i("Slider item: ", "" + list.size());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CategoriesResultActivity.class);
                String category = "";

                int pos = holder.getAdapterPosition();

                switch (holder.getAdapterPosition()) {
                    case 0:
                        category = "Action and Adventure"; break;
                    case 1:
                        category = "classic"; break;
                    case 2:
                        category = "thrillers and crimes"; break;
                    case 3:
                        category = "comics"; break;
                    case 4:
                        category = "science fiction"; break;
                    case 5:
                        category = "auto biography"; break;
                    case 6:
                        category = "historic"; break;
                    case 7:
                        category = "horror"; break;
                    case 8:
                        category = "sports"; break;
                    case 9:
                        category = "Others"; break;
                }

                String httpLink = "https://www.googleapis.com/books/v1/volumes?q=";
                httpLink += category;

                i.putExtra("Category", httpLink);
                i.putExtra("CategoryType", category);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class categoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.categoryImage);
            textView = itemView.findViewById(R.id.categoryName);

        }

        void setImageView(Categories sliderItem) {

            imageView.setImageResource(sliderItem.getCategoriesImage());
            textView.setText(sliderItem.getCategoriesName());


        }


    }


}