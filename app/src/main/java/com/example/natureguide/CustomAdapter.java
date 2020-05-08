package com.example.natureguide;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class CustomAdapter extends BaseAdapter {
    //CUSTOM ADAPTER (LIST VIEW)מובנה משמש לחיבור בין ה רשימה לכל פריט ברשימה
    NatureLocation[] locationArr;
    static LayoutInflater inflater;  //for inflate layout items
    private GoogleMap mMap;


    public CustomAdapter(Context context, NatureLocation arr[]) {
        this.locationArr = arr;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return locationArr.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.favorites_list_view_item, viewGroup, false);
        //favOnClick
        Button btnAddToFavorites = (Button) view.findViewById(R.id.btnAddToFavorites);
        btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(inflater.getContext(),  "btnAddToFavorites\nlocationArr["+position+"]", Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(inflater.getContext(), LocationInfo.class);
//                intent.putExtra("Description", locationArr[position].getDescription());
//                intent.putExtra("Name", locationArr[position].getName());
//                intent.putExtra("Title", locationArr[position].getTitle());
//                intent.putExtra("Image", locationArr[position].getImage());
//                inflater.getContext().startActivity(intent);
            }
        });
        //btnOpenInMapsOnClick
        Button btnOpenInMaps = (Button) view.findViewById(R.id.btnOpenInMaps);
        btnOpenInMaps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Toast.makeText(inflater.getContext(),  "btnOpenInMaps\nlocationArr["+position+"]", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(inflater.getContext(), MapsActivity.class);
                LatLng Caesrea = new LatLng(32.501173, 34.892103);

                intent.putExtra("LatLngv", locationArr[position].getLatLangv());
                intent.putExtra("LatLngv1", locationArr[position].getLatLangv1());
                intent.putExtra("Description", locationArr[position].getDescription());
                intent.putExtra("Name", locationArr[position].getName());
                intent.putExtra("Title", locationArr[position].getTitle());
                intent.putExtra("Image", locationArr[position].getImage());
                inflater.getContext().startActivity(intent);
            }
        });
        ImageView imgCountry = view.findViewById(R.id.img_location);
        TextView txtName = view.findViewById(R.id.txt_title);
        TextView txtDescription = view.findViewById(R.id.txt_description);

        imgCountry.setImageResource(locationArr[position].getImage());
        txtName.setText(locationArr[position].getTitle());
        txtDescription.setText(locationArr[position].getDescription());
        return view;
    }


}
