package com.azhar.restfulwebservices;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView) findViewById(R.id.output);

    }

    public void runClickHandler(View view) {
//        output.append("Button clicked\n");
        getSupportLoaderManager().restartLoader(0, null, this).forceLoad();

    }

    public void clearClickHandler(View view) {
        output.setText("");
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        output.append("creating loader\n");
        return new MyTaskLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        output.append("loader finished,returned: " + data + "\n");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    private static class MyTaskLoader extends AsyncTaskLoader<String> {


        public MyTaskLoader(@NonNull Context context) {
            super(context);
        }

        @Nullable
        @Override
        public String loadInBackground() {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "from the loader";
        }

        @Override
        public void deliverResult(@Nullable String data) {
            data+=", delivered";
            super.deliverResult(data);

        }
    }

}
