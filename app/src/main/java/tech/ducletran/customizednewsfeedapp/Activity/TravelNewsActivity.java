package tech.ducletran.customizednewsfeedapp.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import tech.ducletran.customizednewsfeedapp.Fragments.TravelNewsFracment;
import tech.ducletran.customizednewsfeedapp.R;

public class TravelNewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_news);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new TravelNewsFracment()).commit();
    }
}
