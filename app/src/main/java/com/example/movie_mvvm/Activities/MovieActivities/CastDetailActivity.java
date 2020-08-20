package com.example.movie_mvvm.Activities.MovieActivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.example.movie_mvvm.Entities.Movies.CastDetails;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.NetworkServices.TheMovieDBClient;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Repositories.DetailsRepository;
import com.example.movie_mvvm.Utilities.Constants;
import com.example.movie_mvvm.ViewModels.MoviesViewModel.CastDetailsViewModel;

import java.util.Objects;

public class CastDetailActivity extends AppCompatActivity {

    private DetailsRepository repository;
    private TextView cast_name, cast_department, cast_bio;
    private ImageView cast_poster;
    private Toolbar toolbar;

    @Override
    public void onBackPressed() { finish(); }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_details);

        String from = getIntent().getStringExtra("from");

        cast_name=findViewById(R.id.tv_cast_detail_title);
        cast_department=findViewById(R.id.tv_cast_detail_department);
        cast_bio=findViewById(R.id.tv_cast_detail_biography);
        cast_poster=findViewById(R.id.iv_cast_detail_poster);
        toolbar=findViewById(R.id.toolbar_cast_details);

        int castID=getIntent().getIntExtra("castID", 1);
        int movieID=getIntent().getIntExtra("id", 1);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent=new Intent(CastDetailActivity.this, SingleMovieActivity.class);
            intent.putExtra("id", movieID);
            intent.putExtra("from", from);
            startActivity(intent);
            finish();
        });

        APIService apiService=new TheMovieDBClient().getClient();
        repository=new DetailsRepository(apiService);
        CastDetailsViewModel castDetailsViewModel=getCastDetailsViewModel(castID);

        castDetailsViewModel.castDetails.observe(this, this::bindUI);
    }

    private CastDetailsViewModel getCastDetailsViewModel(int castID) {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new CastDetailsViewModel(repository, castID);
            }
        }).get(CastDetailsViewModel.class);
    }

    @SuppressLint("SetTextI18n")
    private void bindUI(CastDetails castDetails) {
        cast_name.setText(castDetails.getCastDetailsName());
        if(!castDetails.getCastDetailDepartment().equals("")) cast_department.setText(castDetails.getCastDetailDepartment());
        else cast_department.setText("Dunno");
        if(!castDetails.getCastDetailBiography().equals("")) cast_bio.setText(castDetails.getCastDetailBiography());
        else cast_bio.setText("Search wikipedia. There you will find more than just a name");

        String castPosterURL = Constants.POSTER_BASE_URL + castDetails.getCastDetailPoster();
        if(castDetails.getCastDetailPoster()!=null) Glide.with(this)
                .load(castPosterURL)
                .into(cast_poster);
        else cast_poster.setImageResource(R.drawable.character);
    }
}
