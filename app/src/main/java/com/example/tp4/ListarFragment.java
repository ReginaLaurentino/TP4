package com.example.tp4;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarFragment extends Fragment {

    public static final String TITLE = "Listar";

    public static ListarFragment newInstance() {

        return new ListarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listar, container, false);
    }
}