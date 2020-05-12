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
public class AllFragment extends Fragment {
private RecyclerView allRecycler;
private RecyclerView recyclerViewCatTwo;

//Firebase..
    private DatabaseReference mCatOneDatabase;
    private DatabaseReference mCatTwoDatabase;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview= inflater.inflate(R.layout.fragment_all, container, false);
        mCatOneDatabase= FirebaseDatabase.getInstance().getReference().child("CatOneDatabase");
        mCatOneDatabase.keepSynced(true);
        mCatTwoDatabase= FirebaseDatabase.getInstance().getReference().child("CatTwoDatabase");
        mCatTwoDatabase.keepSynced(true);

        //Cat Two Recycler..


        allRecycler=myview.findViewById(R.id.recycler_all);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        allRecycler.setHasFixedSize(true);
        allRecycler.setLayoutManager(layoutManager);

        //Cat Two Recycler..

        recyclerViewCatTwo=myview.findViewById(R.id.recycler_cattwo);
        LinearLayoutManager layoutManagerCatTwo=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        layoutManagerCatTwo.setStackFromEnd(true);
        layoutManagerCatTwo.setReverseLayout(true);
        recyclerViewCatTwo.setHasFixedSize(true);
        recyclerViewCatTwo.setLayoutManager(layoutManagerCatTwo);
        return myview;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mCatOneDatabase, Data.class)
                        .build();
        FirebaseRecyclerAdapter<Data, CatOneViewHolder> adapterOne = new FirebaseRecyclerAdapter<Data, CatOneViewHolder>
                (options) {
            @Override
            public CatOneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,parent,false);
                return new CatOneViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull CatOneViewHolder holder, int position, @NonNull final Data model) {
                holder.setTitle(model.getTitle());
                holder.setDescription(model.getDescription());
                holder.setImage(model.getImage());
                holder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),CatOneDetailsActivity.class);
                        intent.putExtra("title",model.getTitle());
                        intent.putExtra("description",model.getDescription());
                        intent.putExtra("image",model.getImage());
                        startActivity(intent);

                    }
                });
            }
        };

        /*FirebaseRecyclerAdapter<Data,CatOneViewHolder> adapterOne=new FirebaseRecyclerAdapter<Data, CatOneViewHolder>(
                Data.class,
                R.layout.item_data,
                CatOneViewHolder.class,
                mCatOneDatabase

        ) {
            @Override
            protected void populateViewHolder(CatOneViewHolder viewHolder, Data model, int i) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setImage(model.getImage());


            }
        };*/
        allRecycler.setAdapter(adapterOne);
        adapterOne.startListening();

        FirebaseRecyclerOptions<Data> moptions =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mCatTwoDatabase, Data.class)
                        .build();
        FirebaseRecyclerAdapter<Data, CatTwoViewHolder> adapterTwo = new FirebaseRecyclerAdapter<Data, CatTwoViewHolder>
                (moptions) {
            @Override
            public CatTwoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,parent,false);
                return new CatTwoViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull CatTwoViewHolder holder, int position, @NonNull final Data model) {
                holder.msetTitle(model.getTitle());
                holder.msetDescription(model.getDescription());
                holder.msetImage(model.getImage());
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
            }
        };
       /* FirebaseRecyclerAdapter<Data,CatTwoViewHolder> adapterTwo=new FirebaseRecyclerAdapter<Data, CatTwoViewHolder>(
                Data.class,
                R.layout.item_data,
                CatTwoViewHolder.class,
                mCatTwoDatabase
        ) {
            @Override
            protected void populateViewHolder(CatTwoViewHolder viewHolder, Data model, int i) {
                viewHolder.msetTitle(model.getTitle());
                viewHolder.msetDescription(model.getDescription());
                viewHolder.msetImage(model.getImage());
            }
        };*/
        recyclerViewCatTwo.setAdapter(adapterTwo);
        adapterTwo.startListening();
    }
    public static class CatOneViewHolder extends RecyclerView.ViewHolder{
        View myview;

        public CatOneViewHolder(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }
        public void setTitle(String title)
        {
            TextView mTitle=myview.findViewById(R.id.title);
            mTitle.setText(title);
        }
        public void setDescription(String description)
        {
            TextView mDescription=myview.findViewById(R.id.description);
            mDescription.setText(description);
        }
        public void setImage(final String image)
        {
            final ImageView mImage=myview.findViewById(R.id.imageView);
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
    public static class CatTwoViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public CatTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void msetTitle(String title)
        {
            TextView mTitle=mView.findViewById(R.id.title);
            mTitle.setText(title);
        }
        public void msetDescription(String description)
        {
            TextView mDescription=mView.findViewById(R.id.description);
            mDescription.setText(description);
        }
        public void msetImage(final String image)
        {
            final ImageView mImageview=mView.findViewById(R.id.imageView);
            Picasso.get()
                    .load(image)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(mImageview, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(image).into(mImageview);

                        }
                    });
        }
    }
}
