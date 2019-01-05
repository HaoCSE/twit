package code.hao.twit.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import code.hao.twit.data.TweetRepository;
import code.hao.twit.ui.tweets.TweetsViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory{
    private final TweetRepository repository;

    public static ViewModelFactory getInstance(TweetRepository repository) {
        return new ViewModelFactory(repository);
    }

    private ViewModelFactory(TweetRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TweetsViewModel.class)) {
            //noinspection unchecked
            return (T) new TweetsViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
