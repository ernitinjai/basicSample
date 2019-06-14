package com.exercise.threadexample;

import android.content.*;
import android.os.*;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.util.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.appcompat.app.*;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage,mthreadoutput;
    private Button mExecuteTheradsButton, mStopThreadButton, mlifecycleEventsButton;

    //Thread runnable class
    ThreadExampleClass threadExampleClass[];

    //Just to display the thread identifier name
    String[] threadName;

    final int THREAD_COUNT = 7;

    //It being asked how to disable double click.
    final int DISABLE_CLICK_TIME = 2000; //2 sec



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_activity_a:
                    Intent intent = new Intent(MainActivity.this,ActivityB.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_activity_b:
                    intent = new Intent(MainActivity.this,ActivityC.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        mthreadoutput = findViewById(R.id.threadoutput);
        createThreadNamePool();
        enableLifeCycleEvent();
        mExecuteTheradsButton = findViewById(R.id.execute);
        mStopThreadButton = findViewById(R.id.stop);
        enableThreadEvent();


        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //For Mockito test, thread count must not be less then zero value
    public int getThreadCount(){
        return THREAD_COUNT;
    }

    private void createThreadNamePool() {
        threadName = new String[THREAD_COUNT];
        threadExampleClass = new ThreadExampleClass[THREAD_COUNT];
        for(int i=0;i<THREAD_COUNT;i++) {
            threadName[i] = getResources().getString(R.string.threadName) + i;
            threadExampleClass[i] = new ThreadExampleClass(threadName[i]);
        }
    }

    public class enableButtonClick implements Runnable{

        @Override
        public void run() {
            //enable the clicks after 2 sec
            enableButton();
        }
    }

    private void enableThreadEvent() {

        enableButton();
        mExecuteTheradsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //disable clicks for next 2 sec
                disableButton();

                Handler handler = new Handler();
                handler.postDelayed(new enableButtonClick(),DISABLE_CLICK_TIME);

                for(int i=0;i<THREAD_COUNT;i++) {
                    new Thread(threadExampleClass[i]).start();
                }
            }
        });

        mStopThreadButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                for(int i=0;i<THREAD_COUNT;i++) {
                    if(threadExampleClass[i] !=null)
                        threadExampleClass[i].stop();
                }
            }
        });
    }

    private void enableLifeCycleEvent() {
        mlifecycleEventsButton = findViewById(R.id.lifecycle_events);
        mlifecycleEventsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mTextMessage.setText(((MainApplication)getApplication()).getRecordLifeCycle().toString());
            }
        });
    }


    public class ThreadExampleClass implements Runnable {
        private String CLASSTAG = this.toString();

        private String threadName; // Thread identifier
        boolean exit; //To discontinue the thread's run method.
        Object lock = new Object();
        int statusFlag = 0;
        private final int READY   = 0;
        private final int RUNNING = 1;
        private final int WAITING = 2;
        private final int SLEEPING= 3;


        public ThreadExampleClass(String threadName){
            exit = true;
            this.threadName = threadName;
            statusFlag =0;
        }
        @Override
        public void run() {

            while(exit){ //Each thread acquired while loop and hence it may raise for RACE CONDITION.

                    synchronized (lock) { // we locked it , and doesnt allow another thread to enter into it.

                        while(statusFlag != READY) { //Extra effort to make thread wait till another one is executing the shared resource.
                            try {
                                lock.wait();  // making sure only one thread can execute shared resources

                            } catch (InterruptedException e) {

                            }
                        }

                        //SHARED RESOURCE
                        statusFlag = RUNNING;
                        Log.d(CLASSTAG, threadName);
                        updateTextView(threadName); //update Android Text view

                        //------------------------
                        //notify(); //we do not require here, as it should dispatch for condition .

                        try{
                            //Since Android need some time to refresh/update UI.
                            Thread.sleep(600);
                         } catch (InterruptedException e) {

                         }
                        statusFlag = READY;
                }

            }
        }

        public void stop(){
            exit = false;
        }
    }

    private void updateTextView(final String threadName) {
        runOnUiThread(new Runnable() { //simple way to attach worker thread to main UI thread
            @Override
            public void run() {
                mthreadoutput.setText(threadName);
            }
        });
    }

    private void disableButton(){
        mExecuteTheradsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        mExecuteTheradsButton.setEnabled(false);

    }

    private void enableButton(){
        mExecuteTheradsButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        mExecuteTheradsButton.setEnabled(true);
    }


}
