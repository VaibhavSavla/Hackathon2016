package com.example.vaibhav.hackathon;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.vaibhav.hackathon.adapter.OffersViewPagerAdapter;

public class CustomerHomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager_offers);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        OffersViewPagerAdapter adapter = new OffersViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CustomerHomeActivityFragment(), "Nearby");
        adapter.addFragment(new RequestOfferFragment(), "Request Offer");
        adapter.addFragment(new PopularOffersFragment(), "Popular");
        viewPager.setAdapter(adapter);
    }

}
