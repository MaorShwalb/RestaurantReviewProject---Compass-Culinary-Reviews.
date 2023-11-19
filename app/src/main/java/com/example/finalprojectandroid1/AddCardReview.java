package com.example.finalprojectandroid1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.finalprojectandroid1.databinding.AddCardViewBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddCardReview extends Fragment {

    private AddCardViewBinding binding;

    private FirebaseFirestore db;

    public ReviewModelClass model;

    String currIdGlobal = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = AddCardViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        db = FirebaseFirestore.getInstance();

        TextView textView = binding.reviewField;

        RatingBar ratingBar = binding.ratingStar;
        //ratingBar.getRating();
        //Toast.makeText(getContext(), "Rating:" + ratingBar.getRating(), Toast.LENGTH_SHORT).show();

        binding.btnFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(String.valueOf(textView.getText()), "check");
                model = new ReviewModelClass();
                model.setReview(textView.toString());


                Toast.makeText(getContext(), "Rating:" + ratingBar.getRating(), Toast.LENGTH_SHORT).show();


                write(textView.getText().toString(), String.valueOf(ratingBar.getRating()));



                Bundle bundle = getArguments();
                String address = bundle.getString("currAddress");

                Navigation.findNavController(view).navigate(R.id.action_addCardView_to_reviews, bundle);   // -the nav with the bag (last add don't shown)
                //Navigation.findNavController(view).navigate(R.id.action_addCardView_to_lobby, bundle);
            }
        });


        return binding.getRoot();
    }


    private void write(String review, String rating) {
        //Log.d(null, rating + " rating ");

        SimpleDateFormat DAT = new SimpleDateFormat("'Date: 'dd-MM-yyyy '\nTime: 'HH:mm:ss z");
        String dateAndTime = DAT.format(new Date());
        //Log.d(null, dateAndTime + " dateAndTime ");

        Map<String, Object> Reviews = new HashMap<>();
        Reviews.put("Review", review);
        Reviews.put("Rating", rating);
        Reviews.put("DateAndTime", dateAndTime);

        Bundle bundle = getArguments();
        String address = "";
        if (bundle != null) {
            address = bundle.getString("currAddress");
            Log.d(null, address + " currAddress ");
        }

        //-------------------------------good one for read-------------------------------
        CollectionReference usersRef = db.collection("RestaurantList");
        String finalAddress = address;
        final String[] currId = {""};
        usersRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            //Log.d(null, documentSnapshot.getId() + " => " + documentSnapshot.getData());

                            //Log.d(null, " => " + documentSnapshot.get("address"));

                            if (Objects.equals(documentSnapshot.get("address"), finalAddress)) {
                                currId[0] = documentSnapshot.getId();
                                currIdGlobal = currId[0];
                            }
                        }

                        if (!currIdGlobal.equals("")) {
                            Log.d(null, " 3 " + currIdGlobal);
                            db.collection("RestaurantList").document(currIdGlobal).collection("ReviewsList")
                                    .whereEqualTo("Review", review)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                                String documentID = documentSnapshot.getId();
                                                Reviews.put("Id", documentID);
                                                db.collection("RestaurantList").document(currIdGlobal).collection("ReviewsList")
                                                        .document(documentID)
                                                        .update(Reviews)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Log.d(documentID, "DocumentSnapshot added with ID: " + documentID);
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("Error adding document", e);
                                                            }
                                                        });
                                            } else {
                                                //if not exist create
                                                db.collection("RestaurantList").document(currIdGlobal).collection("ReviewsList")
                                                        .add(Reviews)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                Log.d(documentReference.getId(), "DocumentSnapshot added with ID: " + documentReference.getId());
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("Error adding document", e);
                                                            }
                                                        });
                                                write(review, rating); //not must only for the id
                                            }
                                        }
                                    });
                        }

                    }
                });
        //-------------------------------good one for read-------------------------------

//-------------------------------------get all the ids-------------------------------------------
        /*CollectionReference usersRef = db.collection("RestaurantList");
        usersRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String documentId = documentSnapshot.getId();
                            Log.d( documentId, "id");
                        }
                    }
                });*/



    }


}




/*        Bundle bundle = getArguments();
        String address = "";
        if (bundle != null) {
            address = bundle.getString("currAddress");
            Log.d(null, address + " currAddress ");
        }

        //-------------------------------good one for read-------------------------------
        CollectionReference usersRef = db.collection("RestaurantList");
        String finalAddress = address;
        final String[] currId = {""};
        usersRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            //Log.d(null, documentSnapshot.getId() + " => " + documentSnapshot.getData());

                            //Log.d(null, " => " + documentSnapshot.get("address"));

                            if (Objects.equals(documentSnapshot.get("address"), finalAddress)) {
                                currId[0] = documentSnapshot.getId();
                                currIdGlobal = currId[0];
                            }
                        }
                        Log.d(null, " 1 " + currIdGlobal);
                    }
                });
        //-------------------------------good one for read-------------------------------*/
