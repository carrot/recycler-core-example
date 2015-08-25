package com.carrotcreative.recyclercore_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerViewLayout = (ProgressRecyclerViewLayout) findViewById(R.id.recycler_view_layout);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
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
