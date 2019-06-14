package com.exercise.threadexample;

import android.os.*;

import androidx.annotation.*;
import androidx.appcompat.app.*;

public class ActivityB extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_b);
    }
}
