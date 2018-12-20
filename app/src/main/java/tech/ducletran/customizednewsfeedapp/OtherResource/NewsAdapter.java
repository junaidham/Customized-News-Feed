package tech.ducletran.customizednewsfeedapp.OtherResource;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import tech.ducletran.customizednewsfeedapp.R;


public class NewsAdapter extends ArrayAdapter<New> {

    public NewsAdapter(Activity context, ArrayList<New> newsList) {
        super(context,0,newsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_items,parent,false);
        }
        New newArticle = getItem(position);

        ImageView newImageView = (ImageView) listViewItem.findViewById(R.id.new_image_view);
        TextView newTitleTextView = (TextView) listViewItem.findViewById(R.id.new_title_text_view);
        TextView newDateTextView = (TextView) listViewItem.findViewById(R.id.new_date_text_view);
        TextView newDescriptionTextView = (TextView) listViewItem.findViewById(R.id.new_description_text_view);
        TextView newSourceTextView = (TextView) listViewItem.findViewById(R.id.new_source_text_view);

        Picasso.get().load(newArticle.getImageURL()).resize(360,150).into(newImageView);
        newTitleTextView.setText(newArticle.getTitle());
        if (!newArticle.getDescription().equals("null")) {
            newDescriptionTextView.setText(newArticle.getDescription());
        }
        newSourceTextView.setText(newArticle.getSource());
        newDateTextView.setText("Date published: " + newArticle.getTimePublished());

        return listViewItem;
    }
}
