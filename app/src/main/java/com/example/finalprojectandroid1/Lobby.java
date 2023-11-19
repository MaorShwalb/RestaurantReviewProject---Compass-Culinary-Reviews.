package com.example.finalprojectandroid1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectandroid1.databinding.LobbyBinding;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Lobby extends Fragment {

    private FirebaseAuth mAuth;  // need to add later only to rec when admin is login
    private FirebaseFirestore db;

    private LobbyBinding binding;

    private static String JSON_URL = "https://run.mocky.io/v3/1fefe2a6-cb69-4f12-a7b8-338ad140b00e";

    List<RestaurantModelClass> RestaurantList;
    RecyclerView recyclerView;

    private Adapter.RecyclerViewClickListener listener;

    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = LobbyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        RestaurantList = new ArrayList<>();
        recyclerView = binding.recyclerViewRestaurant;

        GetData getData = new GetData();
        getData.execute();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //DocumentReference docRef = db.collection("collectionName").document();


        //--------------------------------------------------------------------------------


        //CollectionReference collectionRef = db.collection("RestaurantList");



        //String docRef = db.collection("RestaurantList").getClass().toString();
        //String name = db.getClass().getName();
        //Log.d(String.valueOf(collectionRef), "check");
        //if (id == "")
            //write = false;
        //--------------------------------------------------------------------------------


        binding.btnEditRegister.setVisibility(View.GONE);

        String string = null;
        if (mAuth.getCurrentUser() != null)
        {
            binding.btnEditLogout.setVisibility(View.VISIBLE);
            binding.btnEditLogin.setVisibility(View.GONE);
            string = mAuth.getCurrentUser().getEmail();
           //username : admin@gmail.com pass: Maor123456
           //username : guess@gmail.com pass: Guess123456
            if (string.equals("admin@gmail.com")) {
                binding.btnEditRegister.setVisibility(View.VISIBLE);
            }
        }else{
            binding.btnEditLogout.setVisibility(View.GONE);
            binding.btnEditLogin.setVisibility(View.VISIBLE);
        }
        //--------------------------------------------------------------------------------


        binding.btnEditLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_lobby_to_loginFragment);
        //        Log.d(user.email, "email in login fragment");
            }
        });

        binding.btnEditLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
                //FirebaseAuth.getInstance().signOut();
                //binding.btnEditLogout.setVisibility(View.GONE);
                //binding.btnEditLogin.setVisibility(View.VISIBLE);
                //        Log.d(user.email, "email in login fragment");
            }
        });


        binding.btnEditRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(view).navigate(R.id.action_lobby_to_registerFragment);
            }
        });

        return binding.getRoot();
    }

    public void onBackPressed() {

        // add vibrate

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to logout?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            FirebaseAuth.getInstance().signOut();
            binding.btnEditLogout.setVisibility(View.GONE);
            binding.btnEditLogin.setVisibility(View.VISIBLE);
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            progressBar = binding.progressBar;
            progressBar.setVisibility(View.VISIBLE);

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();


                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    int data = inputStreamReader.read();
                    while (data != -1) {
                        current += (char) data;
                        data = inputStreamReader.read();
                    }

                    return current;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            progressBar.setVisibility(View.GONE);

            return current;
        }


        @Override
        protected void onPostExecute(String s) {

            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("restaurant");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    RestaurantModelClass model = new RestaurantModelClass();
                    model.setAddress(jsonObject1.getString("id"));
                    model.setFoodType(jsonObject1.getString("name"));
                    //need to add for img


                    write(model.getAddress(), model.getFoodType());

                    RestaurantList.add(model);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressBar.setVisibility(View.GONE);

            PutDataIntoRecyclerView(RestaurantList);

        }

        private void write(String address, String foodType) {

            Map<String, Object> Restaurant = new HashMap<>();
            Restaurant.put("address", address);
            Restaurant.put("foodType", foodType);

            db.collection("RestaurantList")
                    .whereEqualTo("address", address)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                String documentID = documentSnapshot.getId();
                                Restaurant.put("Id", documentID);
                                db.collection("RestaurantList")
                                        .document(documentID)
                                        .update(Restaurant)
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
                                db.collection("RestaurantList")
                                        .add(Restaurant)
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
                                write(address, foodType); //not must only for the id
                            }
                        }
                    });

        }
    }


//-----------------------------------------------------------------
/*            db.collection("RestaurantList")
                    .add(Restaurant)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(documentReference.getId(), "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w( "Error adding document", e);
                        }
                    });*/



    private void PutDataIntoRecyclerView(List<RestaurantModelClass> RestaurantList){

        setOnClickListener();

        Adapter adapter = new Adapter(getContext(), RestaurantList, listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapter);

    }

    private void setOnClickListener() {
        listener = new Adapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(view.getContext(), RestaurantList.get(position).address, Toast.LENGTH_SHORT).show();

                RestaurantList.get(position).setCurrAddress(RestaurantList.get(position).address);
                //Log.d(null, RestaurantList.get(position).getCurrAddress() + " check ");

                Bundle bundle = new Bundle();
                bundle.putString("currAddress", RestaurantList.get(position).getCurrAddress());

                Navigation.findNavController(view).navigate(R.id.action_lobby_to_reviews, bundle);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
