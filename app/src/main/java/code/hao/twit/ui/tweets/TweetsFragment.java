package code.hao.twit.ui.tweets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import code.hao.twit.data.local.model.Tweet;
import code.hao.twit.databinding.FragmentTweetsBinding;
import code.hao.twit.utils.Injection;
import code.hao.twit.utils.ViewModelFactory;

import java.util.List;

public class TweetsFragment extends Fragment {

    private TweetsViewModel viewModel;
    private FragmentTweetsBinding binding;

    public static TweetsFragment newInstance() {
        return new TweetsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTweetsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = obtainViewModel(getActivity());
        setupListAdapter();



    }

    private TweetsViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = Injection.provideViewModelFactory(activity);
        return ViewModelProviders.of(activity, factory).get(TweetsViewModel.class);
    }

    private void setupListAdapter() {
        RecyclerView recyclerView = binding.rvTweetList;
        final TweetsAdapter favoritesAdapter = new TweetsAdapter();
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
//
//        // setup recyclerView
        recyclerView.setAdapter(favoritesAdapter);
        recyclerView.setLayoutManager(layoutManager);
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
//        recyclerView.addItemDecoration(itemDecoration);

        // observe favorites list


        viewModel.getTweetListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> movieList) {
                if (movieList.isEmpty()) {
                    // TODO: optimize this
                    // display empty state since there is no tweets in database
                    binding.rvTweetList.setVisibility(View.GONE);
                    binding.emptyState.setVisibility(View.VISIBLE);
                } else {
                    binding.rvTweetList.setVisibility(View.VISIBLE);
                    binding.emptyState.setVisibility(View.GONE);
                    favoritesAdapter.submitList(movieList);
                }
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tweet tweet = new Tweet();
                tweet.setTweet("this is a tweet!");
                viewModel.insertTweet(tweet);
            }
        });
    }
}
