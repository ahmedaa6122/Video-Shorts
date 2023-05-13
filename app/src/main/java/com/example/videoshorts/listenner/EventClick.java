package com.example.videoshorts.listenner;

import com.example.videoshorts.model.SaveSearch;

public interface EventClick {
    void onClickSearch(String content);

    void onClickDelete(SaveSearch object, int position);
}