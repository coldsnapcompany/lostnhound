package com.coldsnap.lostnhound;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(PetViewHolder holder, int position) { //getting the text and then image values for each list entry
        Pet pet = petList.get(position);
        holder.textViewName.setText(pet.getName());
        holder.textViewType.setText(pet.getType());
        holder.textViewPostcode.setText(pet.getPostcode());
        holder.textViewColour.setText(pet.getColour());
        holder.textViewStatus.setText(pet.getStatus());

        // aim is to check is text is Found or Lost and change the font colour accordingly
        // not working, doesn't recognise Found or Lost strings, all strings made RED, does work without "if" check though, maybe try getValue()
//        if(holder.textViewStatus.toString().trim().equalsIgnoreCase("Found")) {
//            holder.textViewStatus.setTextColor(Color.GREEN);
//        }
//        else if(holder.textViewStatus.toString().trim().equalsIgnoreCase("Lost")){
//            holder.textViewStatus.setTextColor(Color.RED);
//        }

        Picasso.get()
                .load(pet.getImage())
                .placeholder(R.drawable.whiskers)
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public class PetViewHolder extends RecyclerView.ViewHolder{ //returns instance of first implemented method above - sets the values to the layout

        ImageView imageView;
        TextView textViewName, textViewType, textViewPostcode, textViewColour, textViewStatus;

        public PetViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.petImg);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewPostcode = itemView.findViewById(R.id.textViewPostcode);
            textViewColour = itemView.findViewById(R.id.textViewColour);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);



        }
    }

}
