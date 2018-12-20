package tech.ducletran.customizednewsfeedapp.OtherResource;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tech.ducletran.customizednewsfeedapp.R;

public class TravelAdapter extends ArrayAdapter<New> {

    public TravelAdapter(Activity context, ArrayList<New> newsList) {
        super(context,0,newsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_city_items,parent,false);
        }
        New newCity = getItem(position);

        ImageView cityImageView= listViewItem.findViewById(R.id.city_image_view);
        TextView cityTitleTextView = listViewItem.findViewById(R.id.city_name_text_view);
//        TextView cityDescriptionView = listViewItem.findViewById(R.id.city_description_text_view);

        Picasso.get().load(newCity.getImageURL()).resize(360,250).into(cityImageView);
        cityTitleTextView.setText(newCity.getTitle() + " - " + newCity.getWriter());
//        cityDescriptionView.setText(newCity.getDescription());


        return listViewItem;
    }
}
