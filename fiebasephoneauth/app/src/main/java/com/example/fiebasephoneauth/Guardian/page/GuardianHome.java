package com.example.fiebasephoneauth.Guardian.page;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fiebasephoneauth.R;
import com.example.fiebasephoneauth.databinding.ActivityGuardianHomeBinding;
import com.example.fiebasephoneauth.login.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * <h3> Guardian Home Activity </h3>
 *
 * GuardianMenuHomeFragment, GuardianMenuEventFragment, GuardianMenuProfileFragment를
 * BottomNavigationView를 통해 전환할 수 있음
 */
public class GuardianHome extends AppCompatActivity {
    ActivityGuardianHomeBinding binding;

    FragmentManager fragmentManager = getSupportFragmentManager();
    GuardianMenuHomeFragment guardianMenuHomeFragment = new GuardianMenuHomeFragment();
    GuardianMenuEventFragment guardianMenuEventFragment = new GuardianMenuEventFragment();
    GuardianMenuProfileFragment guardianMenuProfileFragment = new GuardianMenuProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String idTxt = intent.getStringExtra("id");

        binding = ActivityGuardianHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, guardianMenuHomeFragment).commitAllowingStateLoss();

        Bundle bundle = new Bundle();
        bundle.putString("id",idTxt);
        guardianMenuHomeFragment.setArguments(bundle);
        guardianMenuEventFragment.setArguments(bundle);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        int selectedItemId = getIntent().getIntExtra("SELECTED_ITEM", 0);
        if (selectedItemId != 0) {
            // 선택된 아이템이 있다면 해당 아이템을 선택하도록 함
            bottomNavigationView.setSelectedItemId(selectedItemId);
        }

    }



    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            if (item.getItemId() == R.id.menu_home) {
                transaction.replace(R.id.frameLayout, guardianMenuHomeFragment).commit();
            } else if (item.getItemId() == R.id.menu_event) {
                transaction.replace(R.id.frameLayout, guardianMenuEventFragment).commit();
            } else if (item.getItemId() == R.id.menu_logout) {
                //자동 로그인 정보 삭제
                SharedPreferences AutoLoginsharedPreferences = getSharedPreferences("autoLogin", MODE_PRIVATE);
                SharedPreferences AppTokenSharedPreferences = getSharedPreferences("AppToken", MODE_PRIVATE);
                SharedPreferences.Editor AutoLoginEditor = AutoLoginsharedPreferences.edit();
                SharedPreferences.Editor AppTokenEditor = AppTokenSharedPreferences.edit();
                AutoLoginEditor.clear();
                AutoLoginEditor.commit();
                AppTokenEditor.clear();
                AppTokenEditor.commit();
                Intent intent = new Intent(GuardianHome.this, MainActivity.class);
                startActivity(intent);
            }

            return true;
        }
    }
}

