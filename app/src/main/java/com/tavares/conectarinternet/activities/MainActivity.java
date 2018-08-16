package com.tavares.conectarinternet.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.tavares.conectarinternet.loader.FilmeLoader;
import com.tavares.conectarinternet.adapter.FilmesAdapter;
import com.tavares.conectarinternet.R;
import com.tavares.conectarinternet.data.Filme;
import com.tavares.conectarinternet.util.QueryUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Filme>> {

    private static final String PATH_POPULAR = "popular";
    private static final String PATH_TOP_RATED = "top_rated";
    private static final String PATH_TOP_CINEMAS = "now_playing";

    private static final int MOVIE_LIST_COLUMN = 3;
    private static final int LOADER_ID_POPULAR = 0;
    private static final int LOADER_ID_TOP_RATED = 1;
    private static final int LOADER_ID_EM_CINEMAS = 2;

    private ProgressBar mLoadingIndicator;
    private TextView mNoNetworkTextView;
    private TextView mNoResultTextView;
    private RecyclerView mMovieList;
    private FilmesAdapter mMoviesListAdapter;
    private Loader<List<Filme>> mMovieLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLoadingIndicator =  findViewById(R.id.movie_list_loading_indicator);
        mNoNetworkTextView =  findViewById(R.id.movie_list_no_network_text_view);
        mNoResultTextView =  findViewById(R.id.movie_list_no_result_text_view);
        mMovieList =findViewById(R.id.movie_list_recycler_view);
        mMovieList.setLayoutManager(new GridLayoutManager(this, MOVIE_LIST_COLUMN));
        mMovieList.setHasFixedSize(true);
        mMoviesListAdapter = new FilmesAdapter(this, new ArrayList<Filme>());
        mMovieList.setAdapter(mMoviesListAdapter);

        if (isConnected()) {
            //INICIA PRIMEIRAMENTE A TELA COM OS FILMES POPULARES
            getSupportLoaderManager().initLoader(LOADER_ID_POPULAR, null, this);
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
            mNoNetworkTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter_popular:
                getSupportLoaderManager().restartLoader(LOADER_ID_POPULAR, null, this);
                return true;
            case R.id.action_filter_top_rated:
                getSupportLoaderManager().restartLoader(LOADER_ID_TOP_RATED, null, this);
                return true;
            case R.id.action_filter_filmes_cinema:
                getSupportLoaderManager().restartLoader(LOADER_ID_EM_CINEMAS, null, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<List<Filme>> onCreateLoader(int loaderId, Bundle args) {
        String pathForFilter = "";
        switch (loaderId) {
            case LOADER_ID_TOP_RATED:
                pathForFilter = PATH_TOP_RATED;
                break;

            case LOADER_ID_EM_CINEMAS:
                pathForFilter = PATH_TOP_CINEMAS;
                break;

            default:
                pathForFilter = PATH_POPULAR;
        }
        String requestUrlForMovieList = QueryUtils.makeRequestUrlForMovieList(pathForFilter);
        mMovieLoader = new FilmeLoader(this, requestUrlForMovieList);
        return mMovieLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Filme>> loader, List<Filme> filmes) {
        mLoadingIndicator.setVisibility(View.GONE);
        if (filmes == null || filmes.size() == 0) {
            mNoResultTextView.setVisibility(View.VISIBLE);
        } else {
            mMoviesListAdapter.updateItems(filmes);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Filme>> loader) {
        loader = null;
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
