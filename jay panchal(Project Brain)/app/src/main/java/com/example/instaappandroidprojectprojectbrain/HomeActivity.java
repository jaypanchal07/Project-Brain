package com.example.instaappandroidprojectprojectbrain;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.instaappandroidprojectprojectbrain.Fragment.HomeFragment;
import com.example.instaappandroidprojectprojectbrain.Fragment.FollowingFragment;
import com.example.instaappandroidprojectprojectbrain.Fragment.ProfileFragment;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();

    }
    public void initialize() {
        bundle = new Bundle();
        bundle.putString("username", getIntent().getStringExtra("username"));

        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home).setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_idea).setText("Following"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile).setText("Profile"));

        tabLayout.selectTab(tabLayout.getTabAt(0));

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();

        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        Fragment currentFrag = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        getSupportFragmentManager().beginTransaction().remove(currentFrag).commit();

        if (tab.getPosition() == 0) {
            HomeFragment fragment = new HomeFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                    fragment, HomeFragment.class.getSimpleName())
                    .addToBackStack(null)
                    .commit();
        }
        else if (tab.getPosition() == 1) {
            FollowingFragment fragment = new FollowingFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                    fragment, FollowingFragment.class.getSimpleName())
                    .addToBackStack(null)
                    .commit();
        }
        else {
            ProfileFragment fragment = new ProfileFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                    fragment, ProfileFragment.class.getSimpleName())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

    @Override
    public void onBackPressed() {
    }
}