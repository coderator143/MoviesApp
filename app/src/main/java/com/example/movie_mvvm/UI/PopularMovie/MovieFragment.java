package com.example.movie_mvvm.UI.PopularMovie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.movie_mvvm.Data.API.APIService;
import com.example.movie_mvvm.Data.API.TheMovieDBClient;
import com.example.movie_mvvm.Data.Repository.NetworkState;
import com.example.movie_mvvm.R;

public class MovieFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private MovieFragmentViewModel viewModel;
    private MoviePagedListRepository movieRepository;
    private TextView error_msg;
    private PopularMoviePagedListAdapter movieAdapter;
    private ProgressBar prog_bar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_movie, container, false);

        APIService apiService = new TheMovieDBClient().getClient();
        movieRepository=new MoviePagedListRepository(apiService);
        viewModel=getViewModel();

        movieAdapter=new PopularMoviePagedListAdapter(getContext());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(), 3);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = movieAdapter.getItemViewType(position);
                if(viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1;
                else return 3;
            }
        });

        create_movies_list();

        swipeRefreshLayout=v.findViewById(R.id.sr_movie_list);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            create_movies_list();
            swipeRefreshLayout.setRefreshing(false);
        });

        error_msg=v.findViewById(R.id.txt_error_popular);
        prog_bar=v.findViewById(R.id.progress_bar_popular);

        viewModel.networkState.observe(getViewLifecycleOwner(), networkState -> {
            if(viewModel.isListEmpty() && networkState==NetworkState.Companion.LOADING) prog_bar.setVisibility(View.VISIBLE);
            else prog_bar.setVisibility(View.GONE);
            if(viewModel.isListEmpty() && networkState==NetworkState.Companion.ERROR) prog_bar.setVisibility(View.VISIBLE);
            else prog_bar.setVisibility(View.GONE);

            if(!viewModel.isListEmpty()) movieAdapter.setNetworkState(networkState);
        });

        RecyclerView rv_movie_list = v.findViewById(R.id.rv_movie_list);
        rv_movie_list.setLayoutManager(gridLayoutManager);
        rv_movie_list.setHasFixedSize(true);
        rv_movie_list.setAdapter(movieAdapter);

        return v;
    }

    private MovieFragmentViewModel getViewModel() {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MovieFragmentViewModel(movieRepository);
            }
        }).get(MovieFragmentViewModel.class);
    }

    private void create_movies_list() {
        viewModel.moviePagedList.observe(getViewLifecycleOwner(), movies -> movieAdapter.submitList(movies));
    }
}
