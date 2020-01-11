package com.debugger.downloader.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.debugger.downloader.R;
import com.debugger.downloader.constants.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

public abstract class BaseActivity extends AppCompatActivity {

    protected Stack<Fragment> fragmentsStack;
    protected CoordinatorLayout parentLayout;

    protected BottomNavigationView bnvMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    void initViews() {

        fragmentsStack = new Stack<>();
//        bnvMain = findViewById(R.id.navigation);
    }

    void setValues() {

    }

    void setListeners() {
        bnvMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return true;
            }
        });
    }

    public void switchFragment(Fragment fragment, Constants.FragmentAnimation fragmentAnimation) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        try {
            if (fragmentAnimation == Constants.FragmentAnimation.SLIDE_RIGHT) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_right);
            } else if (fragmentAnimation == Constants.FragmentAnimation.SLIDE_LEFT) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left);
            }
            fragmentTransaction.replace(R.id.frame_container, fragment).commit();
        } catch (IllegalStateException e) {
            fragmentTransaction.replace(R.id.frame_container, fragment).commitAllowingStateLoss();
        }
    }


    public void switchSelectedMenuFragment(Fragment fragment) {
        fragmentsStack.clear();
        fragmentsStack.push(fragment);
        switchFragment(fragment, Constants.FragmentAnimation.NONE);
    }


    public void switchFragmentWithBackStack(Fragment fragment, Bundle bundle) {


        fragmentsStack.push(fragment);
        switchFragment(fragment, Constants.FragmentAnimation.SLIDE_LEFT);
        fragment.setArguments(bundle);


    }


    /**
     * if the size is 5 then get the index of second last fragment i.e, 5-2 = 3 (Array indices are 0-4)
     */
    protected void popFragment() {
        int fragmentIndex = fragmentsStack.size() - 2;
        switchFragment(fragmentsStack.elementAt(fragmentIndex), Constants.FragmentAnimation.SLIDE_RIGHT);
        fragmentsStack.pop();

//        bnvMain = findViewById(R.id.navigation);
        if (getCurrentFragment() != null) {

        }
    }

    public Fragment getCurrentFragment() {
        return fragmentsStack.lastElement();
    }


    protected <T> void startNewActivity(Class<T> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    protected <T> void startNewActivity(Class<T> activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * Check if there is any fragment on stack to move back to that fragment
     */
    @Override
    public void onBackPressed() {
        if (fragmentsStack != null && fragmentsStack.size() > 1) {
            popFragment();
        } else {
            showExitDialog();
        }
    }


    public void showExitDialog() {
        AlertDialog alertbox = new AlertDialog.Builder(this).setMessage("Are you sure you want to exit")
                .setPositiveButton(("Exit"), new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        setLastSeen();
                        finish();
                    }
                }).setNegativeButton("Logout", (arg0, arg1) -> {

                  /*  Intent i = new Intent(BaseActivity.this, LoginActivity.class);
                    PrefManager.getInstance(getBaseContext()).resetPrefManager();
                    startActivity(i);
                    finish();*/
                   /* AccessToken accessToken = AccessToken.getCurrentAccessToken();
                    LoginManager.getInstance().logOut();
                    GoogleSignInUtils googleSignInUtils = new GoogleSignInUtils();
                    googleSignInUtils.googleLoginManager(BaseActivity.this);
                    if(googleSignInUtils.checkSession()!=null)
                        googleSignInUtils.signOut();
                    setLastSeen();
                    PrefManager.getInstance(getBaseContext()).resetPrefManager();
                    Intent i = new Intent(BaseActivity.this,SigninActivity.class);
                    finish();
                    startActivity(i);*/
                }).show();
    }



    private void setLastSeen() {/*
        String date = getTime();
        new LoginUtils(getBaseContext(),date);*/
    }

    private String getTime() {
        try {
            DateFormat sdf = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
            String currentDate = sdf.format(new Date());
            return currentDate;

        } catch (Exception e) {

            Log.d("TimeException", e.getMessage());
            return "2:46 pm";
        }

    }


}
