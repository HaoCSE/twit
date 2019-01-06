package code.hao.twit.ui.tweets;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import code.hao.twit.R;
import code.hao.twit.data.local.model.Tweet;
import code.hao.twit.databinding.FragmentTweetsBinding;
import code.hao.twit.utils.Injection;
import code.hao.twit.utils.MessageUtils;
import code.hao.twit.utils.ViewModelFactory;
import java.util.List;

import static code.hao.twit.utils.MessageUtils.splitMessage;

public class TweetsFragment extends Fragment implements View.OnClickListener{

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

    private final TextWatcher mInputMessageWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//            super.onTextChanged(charSequence, start, before, count);
            int sendBtnResId = TextUtils.isEmpty(charSequence) ? R.mipmap.ic_send_message_normal : R.mipmap.ic_send_message_activated;
            binding.sendMessageBtn.setImageResource(sendBtnResId);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

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

        binding.inputMessageEdt.addTextChangedListener(mInputMessageWatcher);
        binding.sendMessageBtn.setOnClickListener(this);
        binding.backBtn.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                getActivity().onBackPressed();
                break;
            case R.id.send_message_btn:
                String s = binding.inputMessageEdt.getText().toString();

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

                binding.inputMessageEdt.setText("");
                break;
            default:
        }
    }
}
