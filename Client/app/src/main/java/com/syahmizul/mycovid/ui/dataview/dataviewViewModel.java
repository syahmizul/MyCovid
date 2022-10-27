package com.syahmizul.mycovid.ui.dataview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class dataviewViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public dataviewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dataview fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}