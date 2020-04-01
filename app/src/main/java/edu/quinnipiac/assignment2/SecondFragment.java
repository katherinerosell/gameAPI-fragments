package edu.quinnipiac.assignment2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 *
 * SecondActivity
 * Author: Katherine Rosell and Jenna Saleh
 * 3/31/2020
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {
    static interface SecondListener{
        void displayAttr(ImageView imgView, TextView txtView);
    }

    private SecondListener secondListner;

    private static ImageView genreIMG;
    private static TextView listOfGames;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_second, container, false);

        genreIMG = layout.findViewById(R.id.genre_img);
        listOfGames = layout.findViewById(R.id.listofgames);
        secondListner.displayAttr(genreIMG, listOfGames);

        return inflater.inflate(R.layout.fragment_second,container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if(view != null){
            genreIMG = view.findViewById(R.id.genre_img);
            listOfGames = view.findViewById(R.id.listofgames);
            secondListner.displayAttr(genreIMG, listOfGames);
        }

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
        this.secondListner = (SecondListener)context;
    }

}


