package com.example.friendies.ui.bookmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookmarkViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public BookmarkViewModel() {
        this.mText = new MutableLiveData<>();
        mText.setValue("This is bookmark fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}
