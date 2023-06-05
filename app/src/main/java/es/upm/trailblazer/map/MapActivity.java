package es.upm.trailblazer.map;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import es.upm.trailblazer.R;

public class MapActivity extends AppCompatActivity {

    Fragment trackFragment, searchRouteFragment, historyFragment;
    BottomNavigationView bottomNavigationView;
    int itemTrackId, itemSearchId, itemHistoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        trackFragment = new TrackFragment();
        searchRouteFragment = new SeachRouteFragment();
        historyFragment = new HistoryFragment();

        commitFragmentTransaction(R.id.map_fragment, trackFragment);
        setBottomNavigationViewBehavior();
    }

    private void setBottomNavigationViewBehavior(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> makeFragmentTransaction(item));
    }

    private boolean makeFragmentTransaction(MenuItem item) {
        int itemId = item.getItemId();
        int track = bottomNavigationView.getMenu().getItem(0).getItemId();
        int search = bottomNavigationView.getMenu().getItem(1).getItemId();
        int history = bottomNavigationView.getMenu().getItem(2).getItemId();
        Fragment fragmentToTransit = null;
        Boolean result = false;

        item.setChecked(true);

        if (itemId == track) {
            fragmentToTransit = trackFragment;
        } else if (itemId == search) {
            fragmentToTransit = searchRouteFragment;
        } else if (itemId == history) {
            fragmentToTransit = historyFragment;
        }
       if (fragmentToTransit != null) {
            commitFragmentTransaction(R.id.map_fragment, fragmentToTransit);
            result = true;
        }
        return result;
    }

    private void commitFragmentTransaction(@IdRes int containerViewId, @NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment).commit();
    }

}