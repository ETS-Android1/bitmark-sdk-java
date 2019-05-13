package com.bitmark.androidsdksample;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.bitmark.androidsdksample.constants.AppConstants;
import com.bitmark.androidsdksample.fragments.AccountFragment;
import com.bitmark.androidsdksample.fragments.QueryFragment;
import com.bitmark.androidsdksample.fragments.RegistrationIssuanceFragment;
import com.bitmark.androidsdksample.fragments.TransferFragment;
import com.bitmark.androidsdksample.samples.SDKSample;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigation();

        if (savedInstanceState == null) {
            loadAccountFragment();
        }

        SDKSample.initialize(AppConstants.API_TOKEN, AppConstants.NETWORK_MODE);
    }

    private void setupBottomNavigation() {
        navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_account:
                    loadAccountFragment();
                    return true;
                case R.id.navigation_registration_issuance:
                    replaceByRegistrationIssuanceFragment();
                    return true;
                case R.id.navigation_query:
                    replaceByQueryFragment();
                    return true;
                case R.id.navigation_transfer:
                    replaceByTransferFragment();
                    return true;
                default:
                    return false;
            }
        });
    }

    private void loadAccountFragment() {
        AccountFragment fragment = AccountFragment.newInstance();
        replaceFragment(fragment);
    }

    private void replaceByRegistrationIssuanceFragment() {
        RegistrationIssuanceFragment fragment = RegistrationIssuanceFragment.newInstance();
        replaceFragment(fragment);
    }

    private void replaceByQueryFragment() {
        QueryFragment fragment = QueryFragment.newInstance();
        replaceFragment(fragment);
    }

    private void replaceByTransferFragment() {
        TransferFragment fragment = TransferFragment.newInstance();
        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_content, fragment);
        ft.commit();
    }
}
