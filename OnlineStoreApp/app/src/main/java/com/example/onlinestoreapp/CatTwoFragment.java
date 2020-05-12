package com.example.onlinestoreapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlinestoreapp.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatTwoFragment extends Fragment {


    private RecyclerView recyclerView;

    //Firebase...
    private DatabaseReference mCatTwoDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview=inflater.inflate(R.layout.fragment_cat_two, container, false);
        recyclerView=myview.findViewById(R.id.recycler_cat_two_data);
        mCatTwoDatabase= FirebaseDatabase.getInstance().getReference().child("CatTwoDatabase");
        mCatTwoDatabase.keepSynced(true);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        return myview;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Data> moptions =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mCatTwoDatabase, Data.class)
                        .build();
        FirebaseRecyclerAdapter<Data, CatTwoViewHolder> adapter = new FirebaseRecyclerAdapter<Data, CatTwoViewHolder>
                (moptions) {
            @Override
            public CatTwoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_item_data,parent,false);
                return new CatTwoViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull CatTwoViewHolder holder, int position, @NonNull final Data model) {
                holder.setTitle(model.getTitle());
                holder.setDescription(model.getDescription());
                holder.setImage(model.getImage());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),CatOneDetailsActivity.class);
                        intent.putExtra("title",model.getTitle());
                        intent.putExtra("description",model.getDescription());
                        intent.putExtra("image",model.getImage());
                        startActivity(intent);

                    }
                });

            };
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public static class CatTwoViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public CatTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setTitle(String title)
        {
            TextView mTitle=mView.findViewById(R.id.post_title);
            mTitle.setText(title);
        }
        public void setDescription(String description)
        {
            TextView mDescription=mView.findViewById(R.id.post_details);
            mDescription.setText(description);
        }
        public void setImage(final String image)
        {
            final ImageView mImage=mView.findViewById(R.id.post_image);
            Picasso.get()
                    .load(image)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(mImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(image).into(mImage);

                        }
                    });

        }
    }

}
