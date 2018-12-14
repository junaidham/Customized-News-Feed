package tech.ducletran.customizednewsfeedapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class TravelNewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_news);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new TravelNewsFracment()).commit();
    }
}
