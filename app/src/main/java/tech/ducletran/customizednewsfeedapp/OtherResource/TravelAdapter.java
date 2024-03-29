package tech.ducletran.customizednewsfeedapp.OtherResource;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tech.ducletran.customizednewsfeedapp.R;

public class TravelAdapter extends ArrayAdapter<City> {


    public TravelAdapter(Activity context, ArrayList<City> newsList) {
        super(context,0,newsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_city_items,parent,false);
        }
        final City newCity = getItem(position);

        ImageView cityImageView= listViewItem.findViewById(R.id.city_image_view);
        TextView cityTitleTextView = listViewItem.findViewById(R.id.city_name_text_view);
        TextView cityDescriptionView = listViewItem.findViewById(R.id.city_description_text_view);
        LinearLayout detailedView = listViewItem.findViewById(R.id.city_detail_view);

        // Load content to view
        Picasso.get().load(newCity.getImageURL()).resize(360,250).into(cityImageView);
        cityTitleTextView.setText(newCity.getName() + " - " + newCity.getCountry());
        cityDescriptionView.setText(newCity.getDescription());

        if (newCity.getExpanding()) {
            detailedView.setVisibility(View.VISIBLE);
        } else {
            detailedView.setVisibility(View.GONE);
        }


        return listViewItem;
    }
}
