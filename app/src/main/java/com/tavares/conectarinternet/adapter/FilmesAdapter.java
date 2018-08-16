package com.tavares.conectarinternet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.rtoshiro.util.format.MaskFormatter;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.pattern.MaskPattern;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.squareup.picasso.Picasso;
import com.tavares.conectarinternet.R;
import com.tavares.conectarinternet.activities.DetalhesActivity;
import com.tavares.conectarinternet.data.Filme;
import com.tavares.conectarinternet.util.QueryUtils;

import java.util.List;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.MovieHolder>{

    private LayoutInflater mLayoutInflater;
    private List<Filme> mFilmes;
    private Context mContext;

    public FilmesAdapter(Context context, List<Filme> filmes) {
        mFilmes = filmes;
        mContext = context;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.adapter_item, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, final int position) {
        final Filme hotel = mFilmes.get(position);
        Picasso.with(mContext)
                .load(QueryUtils.makeRequestUrlForPoster(mFilmes.get(position).getCartaz()))
                .into(holder.moviePosterImageView);
        holder.moviePosterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetalhesActivity.class);
                intent.putExtra(Filme.class.getSimpleName(), mFilmes.get(position));
                mContext.startActivity(intent);
            }
        });



        holder.dataLancamento.setText(hotel.getDataLancamento());
    }

    @Override
    public int getItemCount() {
        return mFilmes.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView moviePosterImageView;
        private TextView dataLancamento;

        public MovieHolder(View itemView) {
            super(itemView);
            moviePosterImageView =  itemView.findViewById(R.id.movie_list_poster_image);
            dataLancamento =   itemView.findViewById(R.id.textData);



        }
    }

    public void updateItems(List<Filme> filmes) {
        mFilmes.clear();
        mFilmes.addAll(filmes);
        notifyDataSetChanged();
    }
}
