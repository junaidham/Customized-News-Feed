package tech.ducletran.customizednewsfeedapp;

import android.app.backup.FullBackupDataOutput;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DisplayImageActivity extends AppCompatActivity {
    private float scale = 1f;
    private Matrix matrix = new Matrix();
    private TouchImageView fullImageView;
    ScaleGestureDetector SGD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_image_view);

        fullImageView = findViewById(R.id.image_display_view);


        Intent intent = getIntent();
        String image_url = intent.getStringExtra(ArtNewsFracment.EXTRA_MESSAGE);

        Picasso.get().load(image_url).into(fullImageView);

    }

}
