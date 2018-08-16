package com.tavares.conectarinternet.activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.squareup.picasso.Picasso;
import com.tavares.conectarinternet.R;
import com.tavares.conectarinternet.data.Filme;
import com.tavares.conectarinternet.util.QueryUtils;

public class DetalhesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView posterImageView = (ImageView) findViewById(R.id.movie_detail_poster_image);
        TextView titleTextView = (TextView) findViewById(R.id.movie_detail_title);
        TextView releaseDateTextView = (TextView) findViewById(R.id.movie_detail_release_date);
        TextView voteAverageTextView = (TextView) findViewById(R.id.movie_detail_vote_average);
        TextView overViewTextView = (TextView) findViewById(R.id.movie_detail_overview);


        Filme filme = getIntent().getExtras().getParcelable(Filme.class.getSimpleName());
        String requestUrlForPoster = QueryUtils.makeRequestUrlForPoster(filme.getCartaz());

        Picasso.with(this)
                .load(requestUrlForPoster)
                .into(posterImageView);

        titleTextView.setText(filme.getTitulo());
        releaseDateTextView.setText(filme.getDataLancamento());
        voteAverageTextView.setText(filme.getVotacaoMedia());
        overViewTextView.setText(filme.getSinopse());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
