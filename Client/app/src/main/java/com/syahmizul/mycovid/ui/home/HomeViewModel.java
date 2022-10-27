package com.syahmizul.mycovid.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> riskStatus;



    public HomeViewModel() {
        riskStatus = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return riskStatus;
    }
}