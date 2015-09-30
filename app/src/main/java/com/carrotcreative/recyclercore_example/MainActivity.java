package com.carrotcreative.recyclercore_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.carrotcreative.recyclercore.adapter.RecyclerCoreAdapter;
import com.carrotcreative.recyclercore.adapter.RecyclerCoreModel;
import com.carrotcreative.recyclercore.widget.ProgressRecyclerViewLayout;
import com.carrotcreative.recyclercore_example.net.github.Github;
import com.carrotcreative.recyclercore_example.net.github.models.GithubUser;
import com.carrotcreative.recyclercore_example.recyclerviews.models.UserListRecyclerModel;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
{
    private ProgressRecyclerViewLayout mRecyclerViewLayout;
    private Button mTryAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerViewLayout = (ProgressRecyclerViewLayout) findViewById(R.id.recycler_view_layout);

        View errorView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.element_error_state, null, false);
        mRecyclerViewLayout.setErrorView(errorView);

        mTryAgainButton = (Button) errorView.findViewById(R.id.try_again);
        mTryAgainButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mRecyclerViewLayout.setErrorStateEnabled( ! mRecyclerViewLayout.isErrorStateEnabled());
                loadData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_error:
                mRecyclerViewLayout.setErrorStateEnabled(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        loadData();
    }

    private void loadData()
    {
        loadUsers("carrot");
    }

    private void loadUsers(String organization)
    {
        Github.api().getUsers(
                organization,
                new Callback<GithubUser[]>()
                {
                    @Override
                    public void success(GithubUser[] githubUsers, Response response)
                    {
                        prepareUsers(githubUsers);
                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                        // TODO, handle
                        mRecyclerViewLayout.setErrorStateEnabled(true);
                    }
                }
        );
    }

    private void prepareUsers(GithubUser[] githubUsers)
    {
        ArrayList<RecyclerCoreModel> models = new ArrayList<>();

        // Converting all GithubUser objects
        for(GithubUser githubUser : githubUsers)
        {
            models.add(
                    new UserListRecyclerModel()
                            .setGithubUser(githubUser)
            );
        }

        RecyclerCoreAdapter adapter = new RecyclerCoreAdapter(models);
        mRecyclerViewLayout.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewLayout.setAdapter(adapter);
    }
}
