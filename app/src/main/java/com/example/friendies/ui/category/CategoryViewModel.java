package com.example.friendies.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoryViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public CategoryViewModel() {
        this.mText = new MutableLiveData<>();
        mText.setValue("This is Category fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}
