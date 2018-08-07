package com.coldsnap.lostnhound;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private Context mCtx; //needed to inflate list_layout layout
    private List<Pet> petList;

    public PetAdapter(Context mCtx, List<Pet> petList) {
        this.mCtx = mCtx;
        this.petList = petList;
    }

    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.entry_layout, null);
        PetViewHolder holder = new PetViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PetViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.textViewName.setText(pet.getName());
        holder.textViewType.setText(pet.getType());
        holder.textViewPostcode.setText(pet.getPostcode());
        holder.textViewColour.setText(pet.getColour());

    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    class PetViewHolder extends RecyclerView.ViewHolder{ //returns instance of first implemented method above

        ImageView imageView;
        TextView textViewName, textViewType, textViewPostcode, textViewColour;

        public PetViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.petImg); //doesn't do anything with it, no image in Pet model yet
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewPostcode = itemView.findViewById(R.id.textViewPostcode);
            textViewColour = itemView.findViewById(R.id.textViewColour);
        }
    }

}
