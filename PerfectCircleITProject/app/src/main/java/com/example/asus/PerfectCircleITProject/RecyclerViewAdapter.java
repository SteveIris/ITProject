package com.example.asus.PerfectCircleITProject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<OneGameCard> arrayList;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageView firstImage;
        public ImageView secondImage;
        public TextView difficultyText;
        public TextView markText;
        public TextView dateText;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            firstImage=itemView.findViewById(R.id.firstImage);
            secondImage=itemView.findViewById(R.id.secondImage);
            difficultyText=itemView.findViewById(R.id.difficultyText);
            markText=itemView.findViewById(R.id.markText);
            dateText=itemView.findViewById(R.id.dateText);
        }
    }

    public RecyclerViewAdapter (ArrayList<OneGameCard> arrayList){
       this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.one_game_card, viewGroup, false);
         RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
         return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
         OneGameCard oneGameCard = arrayList.get(i);
        recyclerViewHolder.difficultyText.setText(oneGameCard.getDifficultyText());
        recyclerViewHolder.markText.setText(oneGameCard.getMarkText());
        recyclerViewHolder.dateText.setText(oneGameCard.getDateText());
        Glide.with(recyclerViewHolder.firstImage.getContext()).load(oneGameCard.getImage1Url()).into(recyclerViewHolder.firstImage);
        Glide.with(recyclerViewHolder.secondImage.getContext()).load(oneGameCard.getImage2Url()).into(recyclerViewHolder.secondImage);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
