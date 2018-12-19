package tech.ducletran.customizednewsfeedapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

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
