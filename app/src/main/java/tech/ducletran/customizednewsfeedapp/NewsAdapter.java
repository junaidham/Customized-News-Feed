package tech.ducletran.customizednewsfeedapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<New> {

    public NewsAdapter(Activity context, ArrayList<New> newsList) {
        super(context,0,newsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_news,parent,false);
        }
        New newArticle = getItem(position);

//        ImageView newImageView = (ImageView) listViewItem.findViewById(R.id.new_image_view);
        TextView newTitleTextView = (TextView) listViewItem.findViewById(R.id.new_title_text_view);
        TextView newDateTextView = (TextView) listViewItem.findViewById(R.id.new_date_text_view);
        TextView newDescriptionTextView = (TextView) listViewItem.findViewById(R.id.new_description_text_view);

//        newImageView.setImageURI(newArticle.getImageURL());
        newTitleTextView.setText(newArticle.getTitle());
        newDateTextView.setText("Date published: " + newArticle.getTimePublished());
        newDescriptionTextView.setText(newArticle.getDescription());
        return listViewItem;
    }
}
