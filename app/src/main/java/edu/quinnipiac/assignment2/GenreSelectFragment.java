package edu.quinnipiac.assignment2;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.ShareActionProvider;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * MainActivity
 * @Authors: Katherine Rosell, Jenna Saleh
 * Similar to the NumbersApp, the user selects a genre of a game and this code establishes a network connection!
 *
 */


public class GenreSelectFragment extends Fragment implements View.OnClickListener {
    static interface Listner{
        void genreSelected(String genreString);
    }
    
    private Listner listner;

    ShareActionProvider provider;
    //urls of api here!

    private static String genrePicked;
    private static Spinner genreSpinner;
    private static Button searchButton;
    private static final String[] MYGENRES = GenreHandler.GENRES;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View layout = inflater.inflate(R.layout.fragment_main, container, false);

        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(layout.getContext(), android.R.layout.simple_spinner_item, MYGENRES);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner = layout.findViewById(R.id.genre_spinner);
        genreSpinner.setAdapter(genreAdapter);

        return layout;
    }


    @Override
    public void onStart(){
        super.onStart();
        View view = getView();
        if(view != null){
            genreSpinner = view.findViewById(R.id.genre_spinner);
            searchButton = (Button) view.findViewById(R.id.searchB);
            //searchButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        String spinnerText = genreSpinner.getSelectedItem().toString();
        Log.d("----- GenreSelectFragment----- SPINNER  ", spinnerText);
        genrePicked = spinnerText;
        listner.genreSelected(genrePicked);
    }


    /**
     * onAttach
     * Called when the fragment gets attached to the activity.
     * Because Activity class is a subclass of Context we may do this.
     * @param context
     */
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.listner = (Listner)context;
    }

    

}
