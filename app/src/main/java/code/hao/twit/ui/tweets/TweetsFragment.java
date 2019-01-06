package code.hao.twit.ui.tweets;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import code.hao.twit.data.local.model.Tweet;
import code.hao.twit.databinding.FragmentTweetsBinding;
import code.hao.twit.utils.Injection;
import code.hao.twit.utils.MessageUtils;
import code.hao.twit.utils.ViewModelFactory;
import java.util.List;

import static code.hao.twit.utils.MessageUtils.splitMessage;

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Enter your tweet");

                // Set an EditText view to get user input
                final EditText input = new EditText(getActivity());
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String s = input.getText().toString();

                        List<String> splits;
                        try {
                            splits = splitMessage(s,MessageUtils.MAX_LENGTH);
                        } catch (MessageUtils.SplitMessageException e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            return;
                        }
                        // if splits is an empty array => ...
                        for (int i = 0; i < splits.size(); i++) {
                            Tweet tweet = new Tweet();
                            tweet.setTweet(splits.get(i));
                            viewModel.insertTweet(tweet);

                        }

//
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                alert.show();
            }
        });
    }
}
