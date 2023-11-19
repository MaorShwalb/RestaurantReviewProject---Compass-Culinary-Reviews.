package com.example.finalprojectandroid1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectandroid1.databinding.AllCardsReviewsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Reviews extends Fragment {

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;

    private AllCardsReviewsBinding binding;

    private String address = "";

    private Boolean flag = true;


    RecyclerView recyclerView;
    List<ReviewModelClass>ReviewList;
    ReviewsAdapter reviewsAdapter;
    LinearLayoutManager layoutManager;

    String string = "";

    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = AllCardsReviewsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();


        String string = null;
        if (mAuth.getCurrentUser() != null)
        {
            binding.btnFloating.setVisibility(View.VISIBLE);
            Log.d(string, "connect!!!!!!!!!!!!!!!");

            Log.d(mAuth.getCurrentUser().getEmail(), "user name");   //must be in if != null. cuz if = null its crush
        }else{
            binding.btnFloating.setVisibility(View.GONE);
            Log.d(string, "not connect!!!!!!!!!!!!!!!");
        }


        binding.btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                Log.d(address, "first1");
                address = bundle.getString("currAddress");
                Log.d(address, "first2");

                Navigation.findNavController(view).navigate(R.id.action_reviews_to_addCardView, bundle);
                //        Log.d(user.email, "email in login fragment");
            }
        });


        readFromFirestore();

        //creatRecyclerView();

        return binding.getRoot();
    }


    private final String[] currId = {""};
    private void readFromFirestore() {

        progressBar = binding.progressBar;

        Log.d(address, "second1");

        Bundle bundle = getArguments();
        Log.d(address, "second 2");

        ReviewList = new ArrayList<>();

        if (bundle != null) {
            address = bundle.getString("currAddress");


            //final String[] currId = {""};
            String finalAddress = address;
            Log.d(null, address + " currAddress ");


            CollectionReference collectionReference = db.collection("RestaurantList");
            collectionReference.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            progressBar.setVisibility(View.VISIBLE);

                            if(flag){
                                //first time
                                flag = false;
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    Log.d(null, documentSnapshot.getId() + " => " + documentSnapshot.getData());

                                    Log.d(null, " => " + documentSnapshot.get("address"));
                                    if (Objects.equals(documentSnapshot.get("address"), finalAddress)) {
                                        currId[0] = documentSnapshot.getId();
                                    }

                                }
                                readFromFirestore();
                            } else {
                                //second time

    /*           if we use this piece of code   "final String[] currId = {""};"  need to be in "readFromFirestore()"
          for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    Log.d(null, documentSnapshot.getId() + " => " + documentSnapshot.getData());

                                    Log.d(null, " => " + documentSnapshot.get("address"));
                                    if (Objects.equals(documentSnapshot.get("address"), finalAddress)) {
                                        currId[0] = documentSnapshot.getId();
                                    }

                                }*/
                                Log.d(null, address + " currAddress out");
                                Log.d(null, currId[0] + " currAddress out");

                                CollectionReference collectionReference = db.collection("RestaurantList").document(currId[0]).collection("ReviewsList");
                                collectionReference.get()
                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                                    Log.d(null, documentSnapshot.getId() + " =>>>> " + documentSnapshot.getData());
                                                    Log.d(address, "second 3");

/*                                                    string = documentSnapshot.getId();
                                                    ReviewList.add(new ReviewModelClass(documentSnapshot.getId()));*/

                                                    Map<String, Object> data = documentSnapshot.getData();
                                                    String Review = (String) data.get("Review");
                                                    String Rating = (String) data.get("Rating");
                                                    Rating = "Rating: " + Rating +"/5 Stars";
                                                    //Log.d(string, "string test2");
                                                    String DateAndTime = (String) data.get("DateAndTime");
                                                    ReviewList.add(new ReviewModelClass(Review, Rating, DateAndTime));


                                                }
                                                Log.d(string, "string test1");

                                                recyclerView = binding.recyclerViewReview;
                                                layoutManager = new LinearLayoutManager(getContext());  //this
                                                layoutManager.setOrientation(RecyclerView.VERTICAL);
                                                recyclerView.setLayoutManager(layoutManager);
                                                reviewsAdapter=new ReviewsAdapter(ReviewList);
                                                recyclerView.setAdapter(reviewsAdapter);
                                                reviewsAdapter.notifyDataSetChanged();

                                            }
                                        });
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });


/*        CollectionReference collectionReference = db.collection("RestaurantList").document(currId[0]).collection(finalAddress);
        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Log.d(null, documentSnapshot.getId() + " =>>>> " + documentSnapshot.getData());

                        }

                    }
                });*/

        }

    }


    //@SuppressLint("NotifyDataSetChanged")
/*    private void creatRecyclerView() {
        //Log.d(string, "string test2");


        recyclerView = binding.recyclerViewReview;
        //layoutManager = new LinearLayoutManager();  //this
        //layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        reviewsAdapter=new ReviewsAdapter(ReviewList);
        recyclerView.setAdapter(reviewsAdapter);
        reviewsAdapter.notifyDataSetChanged();
    }*/



    private void PutDataIntoRecyclerView(List<ReviewModelClass> ReviewList){

        ReviewsAdapter adapter = new ReviewsAdapter(ReviewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);

    }




}
