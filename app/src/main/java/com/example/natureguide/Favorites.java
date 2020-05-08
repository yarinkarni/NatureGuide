package com.example.natureguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private NatureLocation[] listToShow;
    private ListView favoritesListView;
    //data base ref
    FirebaseDatabase database;
    DatabaseReference favLocationRef, userRef;
    //firebase auth user
    private FirebaseAuth mAuth;
    //nl list
    ArrayList<NatureLocation> nlList;
    NatureLocation[] listOfPlaces;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolber);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //connect to view
        favoritesListView = (ListView) findViewById(R.id.favoritesListView);
        //details to show
        nlList = new ArrayList<NatureLocation>();
        // Read from the database
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        favLocationRef = database.getReference("Users").child(mAuth.getUid()).child("favLocation");
        userRef = database.getReference("Users").child(mAuth.getUid());

        listOfPlaces = new NatureLocation[]{new NatureLocation("אין עדיין דברים במועדפים", "אין עדיין דברים במועדפים", "אין עדיין דברים במועדפים", R.drawable.border, 30.627331, 34.887882)};

        //CustomAdapter instance and connection to list view
        adapter = new CustomAdapter(Favorites.this, listOfPlaces);
        favoritesListView.setAdapter(adapter);


        //Read from the database
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    NatureLocation temp = ds.getValue(NatureLocation.class);
                    nlList.add(temp);
                }
                listOfPlaces = new NatureLocation[nlList.size()];
                for (int i = 0; i < listOfPlaces.length; i++) {
                    listOfPlaces[i] = new NatureLocation(nlList.get(i).getName(), nlList.get(i).getTitle(), nlList.get(i).getDescription(), nlList.get(i).getImage(), nlList.get(i).getLatLangv(), nlList.get(i).getLatLangv1());
                }
                adapter = new CustomAdapter(Favorites.this, listOfPlaces);
                favoritesListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Toast.makeText(Favorites.this, "Failed to read value", Toast.LENGTH_SHORT).show();
            }

        };
        favLocationRef.addListenerForSingleValueEvent(valueEventListener);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firstName = dataSnapshot.child("firstName").getValue(String.class);
                String lastName = dataSnapshot.child("lastName").getValue(String.class);
                String userEmail = dataSnapshot.child("email").getValue(String.class);
                ((TextView) findViewById(R.id.nav_header_userName)).setText(firstName + " " + lastName);
                ((TextView) findViewById(R.id.nav_header_userEmail)).setText(userEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_Favorites:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MessageFragment()).commit();
                startActivity(new Intent(this, Favorites.class));
                break;
            case R.id.nav_logOut:
                mAuth.signOut();
                startActivity(new Intent(this, LoginPage.class));
                break;
            case R.id.nav_mainActivity:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MessageFragment()).commit();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}