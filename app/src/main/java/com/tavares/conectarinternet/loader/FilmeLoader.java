package com.tavares.conectarinternet.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;


import com.tavares.conectarinternet.data.Filme;
import com.tavares.conectarinternet.util.QueryUtils;

import java.util.List;

public class FilmeLoader extends AsyncTaskLoader<List<Filme>> {
    String mUrl;

    public FilmeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Filme> loadInBackground() {
        return QueryUtils.fetchMovieData(mUrl);
    }
}
