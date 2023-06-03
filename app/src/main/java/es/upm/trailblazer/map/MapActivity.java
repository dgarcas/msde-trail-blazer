package es.upm.trailblazer.map;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import es.upm.trailblazer.R;

public class MapActivity extends AppCompatActivity {

    Fragment trackFragment, searchRouteFragment, historyFragment;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        trackFragment = new TrackFragment();
        searchRouteFragment = new SeachRouteFragment();
        historyFragment = new HistoryFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, trackFragment).commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            item.setChecked(true);
            int track = bottomNavigationView.getMenu().getItem(0).getItemId();
            int seach = bottomNavigationView.getMenu().getItem(1).getItemId();
            int history = bottomNavigationView.getMenu().getItem(2).getItemId();

            if(itemId == track){
                fragmentTransaction(R.id.map_fragment, trackFragment);
            } else if (itemId == seach) {
                fragmentTransaction(R.id.map_fragment, searchRouteFragment);
            } else if (itemId == history) {
                fragmentTransaction(R.id.map_fragment, historyFragment);
            }
            return false;
        });
    }

    private void fragmentTransaction(@IdRes int containerViewId, @NonNull Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment).commit();
    }

}