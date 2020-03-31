package edu.quinnipiac.assignment2;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class TempActivity extends AppCompatActivity implements GenreSelectFragment.Listner {
    ShareActionProvider provider;
    //urls of api here!
    private final String urlHost = "https://rawg-video-games-database.p.rapidapi.com/genres";
    private final String key = "cf3ce5d779mshb68e7898cff4e2ep1b8712jsn1cd80cdd8b5b"; //katie's specific key, different from Jenna's for some reason
    private static GenreHandler genreHandler = new GenreHandler();
    private static String genrePicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //just a precaution for version errors
        if (Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //Spinner object
        final Spinner genreSpinner = findViewById(R.id.genre_spinner);
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genreHandler.GENRES);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);
        //the Find Games button is what really allows the user to go to the next activity using the spinner
        //we only want to go to the next activity when the user clicks the find games button
        Button searchButton = findViewById(R.id.searchB);//search Button
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String spinnerText = genreSpinner.getSelectedItem().toString();
                Log.d("SPINNER", spinnerText);
                genrePicked = spinnerText;
                TempActivity.RetrieveGenreGames myFetchRequest = (TempActivity.RetrieveGenreGames) new TempActivity.RetrieveGenreGames().execute(genrePicked);

            }
        });

    }

    @Override
    public void itemClicked(long ID) {
        Log.d("MAIN ACTIVITY - itemClicked", " ID from fragment  " + ID);
    }


    /**
     * RetrieveGenreGames
     * AsyncTask handles the internet connection to the api information we are using.
     */

    private class RetrieveGenreGames extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection mainConnection = null;
            ArrayList<String> gamesOfGenre;

            Log.d("RetrieveGenreGames", "RetrieveGenreGames");
            try {
                Log.d("RetrieveGenreGames", "---------in try block--------");
                URL url = new URL(urlHost); //+ "/" + genrePicked //no need for ?mashape-key=....
                mainConnection = (HttpsURLConnection) url.openConnection();
                mainConnection.setRequestMethod("GET");
                mainConnection.setRequestProperty("x-rapidapi-key", key);
                mainConnection.connect();

                if (mainConnection.getInputStream() == null) {
                    Log.d("NO INTERNET", "NO INTERNET");
                    return null;
                }
                gamesOfGenre = getStringRead(
                        new BufferedReader(new InputStreamReader((mainConnection.getInputStream()))), strings[0]);
                //Log.d("games in genre", gamesOfGenre.toString());
            } catch (Exception ex){
                Log.e("ERR in RETREIVE", ex.toString());
                return null;
            } finally{
                if (mainConnection != null) mainConnection.disconnect();
            }
            return String.valueOf(gamesOfGenre);
        }


        private ArrayList<String> getStringRead(BufferedReader bufferedReader, String currGenere) throws Exception {
            StringBuffer buffer = new StringBuffer();
            String ln; //the line we are reading from
            while((ln=bufferedReader.readLine()) != null){  buffer.append(ln + '\n');  }
            if (bufferedReader != null){
                try{ bufferedReader.close(); }
                catch(IOException err){Log.d("oops no mainAct", "Error" + err.getMessage());}
            }
            return genreHandler.getGamesofGenre(buffer.toString(), currGenere);
        }

        /**
         * OnPOstExecute
         * Using the "res" String we creates, which is the list of games we were able to retrieve,
         * send that information into the second activity to be displayed.
         * As well as the image url of the certain genre
         * @param res
         */
        @Override
        protected void onPostExecute(String res){
            if (res != null){
                Log.d("*************   MAIN ACTIVITY  *************", " on post execute : res   " + res + " ------------ TYPE OF res:  "  + res.getClass());
                Intent intent = new Intent(TempActivity.this, SecondActivity.class);
                intent.putExtra("List of Games", res);
                intent.putExtra("Genre", genrePicked);
                intent.putExtra("Img", genreHandler.getImageBackground());
                startActivity(intent);
            }
        }

    }



}
