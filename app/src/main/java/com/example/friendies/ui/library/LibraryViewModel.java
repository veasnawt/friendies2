package com.example.friendies.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LibraryViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public LibraryViewModel() {
        this.mText = new MutableLiveData<>();
        mText.setValue("This is bookmark fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }

}
