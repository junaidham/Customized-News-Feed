package tech.ducletran.customizednewsfeedapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import tech.ducletran.customizednewsfeedapp.Fragments.ArtNewsFracment;
import tech.ducletran.customizednewsfeedapp.R;
import tech.ducletran.customizednewsfeedapp.OtherResource.TouchImageView;

public class DisplayImageActivity extends AppCompatActivity {
    private TouchImageView fullImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.display_image_view);
        super.onCreate(savedInstanceState);

        fullImageView = findViewById(R.id.image_display_view);


        Intent intent = getIntent();
        String image_url = intent.getStringExtra(ArtNewsFracment.EXTRA_MESSAGE);

        Picasso.get().load(image_url).into(fullImageView);
        setTitle("Artwork");
    }

}
