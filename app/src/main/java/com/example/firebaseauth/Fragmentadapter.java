package com.example.firebaseauth;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Fragmentadapter extends FragmentStateAdapter {
    public Fragmentadapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();

        }
        return new firstFragment();
    }

    @Override
    public int getItemCount() {

        return 3;
    }
}
