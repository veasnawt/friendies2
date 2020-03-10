package com.example.friendies.ui.download;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DownloadViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public DownloadViewModel() {
        this.mText = new MutableLiveData<>();
        mText.setValue("This is Download fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}
