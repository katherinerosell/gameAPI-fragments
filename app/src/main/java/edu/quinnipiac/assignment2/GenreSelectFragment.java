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
        void itemClicked(long ID);
    }
    
    private Listner listner;

    ShareActionProvider provider;
    //urls of api here!

    private static String genrePicked;
    private Spinner genreSpinner;
    private Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View layout = inflater.inflate(R.layout.fragment_main, container, false);
        /**
        genreSpinner = layout.findViewById(R.id.genre_spinner);
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(layout.getContext(), android.R.layout.simple_spinner_item, genreHandler.GENRES);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);
        //the Find Games button is what really allows the user to go to the next activity using the spinner
        //we only want to go to the next activity when the user clicks the find games button
        searchButton = layout.findViewById(R.id.searchB);//search Button
        searchButton.setOnClickListener(this);
         **/
        /**

        //searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String spinnerText = genreSpinner.getSelectedItem().toString();
                Log.d("SPINNER", spinnerText);
                genrePicked = spinnerText;
                RetrieveGenreGames myFetchRequest = (RetrieveGenreGames) new RetrieveGenreGames().execute(genrePicked);

            }
        });
         **/
        return layout;
    }


    @Override
    public void onStart(){
        super.onStart();
        View view = getView();
        if(view != null){
            Spinner genreSpinner = (Spinner) view.findViewById(R.id.genre_spinner);
            Button searchButton = (Button) view.findViewById(R.id.searchB);
        }
    }



    @Override
    public void onClick(View v) {
        String spinnerText = genreSpinner.getSelectedItem().toString();
        Log.d("----- GenreSelectFragment----- SPINNER  ", spinnerText);
        genrePicked = spinnerText;
        
       // RetrieveGenreGames myFetchRequest = (RetrieveGenreGames) new RetrieveGenreGames().execute(genrePicked);
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
