package com.debugger.downloader.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import com.debugger.downloader.Interface.OnBackPressedGeneric;
import com.debugger.downloader.activity.MainActivity;

import java.util.Stack;


public class BaseFragment extends Fragment implements OnBackPressedGeneric {

    protected Stack<Fragment> fragmentsStack;
    private Activity activity;
    private MainActivity parent;
    private final String TAG = "";
    protected CoordinatorLayout parentLayout;
    Toolbar toolbar;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        setParent(getHomeActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public MainActivity getHomeActivity() {
        try {
            return (MainActivity) activity;
        } catch (Exception ex) {
            return null;
        }
    }

    public MainActivity getParent() {
        return parent;
    }


    public void setParent(MainActivity parent) {
        this.parent = parent;
    }

    @Override
    public void onBackPressed() {
        getParent().onBackPressed();
    }


    public void initViews(View view) {

//        parentLayout = view.findViewById(R.id.parentLayout);


    }

    void setValues() {
    }

    void setListeners() {
    }


    protected <T> void startNewActivity(Class<T> activity) {
        Intent intent = new Intent(getActivity(), activity);
        startActivity(intent);
    }

    protected <T> void startNewActivity(Class<T> activity, Bundle bundle) {
        Intent intent = new Intent(getActivity(), activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
