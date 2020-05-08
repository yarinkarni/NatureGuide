package com.example.natureguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private ListView mainActivityListView;

    private FirebaseAuth mAuth;
    private DatabaseReference favLocationRef, userRef;

    boolean doubleBackToExitPressedOnce = false;//click back twice to exit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolber);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mAuth = FirebaseAuth.getInstance();

        //connect to list view
        mainActivityListView = (ListView) findViewById(R.id.mainListView);
        //details to show
        final NatureLocation[] listToShow = new NatureLocation[]{
                new NatureLocation("נחל אלכסנדר", "נחל אלכסנדר", "נחל גדול באיזור חדרה עמק חפר", R.drawable.alexander, 32.386652, 34.892112),
                new NatureLocation("עכו", "עכו", "עַכּוֹ היא עיר במחוז הצפון בישראל, הגובלת מדרום בחופיו הצפוניים של מפרץ עכו וממערב בים התיכון. בשנת 2016 התגוררו בעיר כ-48,000 תושבים, כשני שלישים מהם יהודים. עכו היא אחת מערי הנמל העתיקות בעולם, ודברי ימיה המתועדים מתחילים בתקופת הברונזה הקדומה.", R.drawable.akko,32.922746, 35.068234),
                new NatureLocation("ג'ילבון", "ג'ילבון", "מסלול הג'ילבון יתאים תמיד, ובמיוחד רגע אחרי ששוקם אחרי שריפה: הוא גם יבש וגם רטוב, ויוכל לספק כל טיילן חובב צפון. ", R.drawable.jilbon, 33.046639, 35.667540),
                new NatureLocation("מכתש רמון", "מכתש רמון", "מכתש רמון הוא המכתש האירוזי הגדול בעולם. הוא מצוי בישראל ומהווה אחד מחמשת המכתשים שבנגב. ואחד מחמישים \"שמורות אור בינלאומית\" בעולם, נופו של מכתש רמון הוא ייחודי. על קצה המכתש מצויה העיירה מצפה רמון. אורכו של המכתש כ-40 ק\"מ, רוחבו המרבי כ-9 ק\"מ ועומקו המרבי כ-350 מטר. במזרח, מחולק המכתש על ידי הר ארדון לשתי בקעות: בקעת ארדון ובקעת מחמל.", R.drawable.alexander, 30.627331, 34.887882)
        };

        //CustomAdapter instance and connection to list view
        final CustomAdapter adapter = new CustomAdapter(MainActivity.this, listToShow);
        mainActivityListView.setAdapter(adapter);

        //read from data base
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());
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
            case R.id.nav_maps:
                startActivity(new Intent(this, MapsActivity.class));
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //close app in two back button presses
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        }
        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //in 2 sec it will turn back doubleBackToExitPressedOnce flag off
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
