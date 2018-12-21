package tech.ducletran.customizednewsfeedapp.OtherResource;

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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tech.ducletran.customizednewsfeedapp.R;

public class MusicAdapter extends ArrayAdapter<MusicTrack> {

    public MusicAdapter(Activity context, ArrayList<MusicTrack> newsList) {
        super(context,0,newsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;

        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_music_items,parent,false);
        }
        MusicTrack newTrack = getItem(position);

        ImageView songImageView = listViewItem.findViewById(R.id.song_image_view);
        TextView artistTextView = listViewItem.findViewById(R.id.song_artist_text_view);
        TextView nameTextView = listViewItem.findViewById(R.id.song_name_text_view);

        nameTextView.setText(newTrack.getTrackName());
        artistTextView.setText(newTrack.getArtist());
        Picasso.get().load(newTrack.getThumbnail()).fit().into(songImageView);

        return listViewItem;
    }
}
