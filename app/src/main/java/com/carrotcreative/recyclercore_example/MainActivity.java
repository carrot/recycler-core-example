package com.carrotcreative.recyclercore_example;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
    private RecyclerCoreAdapter mRecyclerCoreAdapter;
    private ArrayList<RecyclerCoreModel> mModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerViewLayout = (ProgressRecyclerViewLayout) findViewById(R.id.recycler_view_layout);

        View emptyState = LayoutInflater.from(getApplicationContext()).inflate(R.layout.empty_state, null);
        mRecyclerViewLayout.setEmptyStateView(emptyState);

        /**
         * set click listener on empty state.
         */
        emptyState.findViewById(R.id.empty_state_button)
                .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), R.string.empty_state_toast_message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        loadUsers("carrot");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.action_empty_state)
        {
            if(! mModelList.isEmpty())
            {
                mModelList.clear();
                mRecyclerCoreAdapter.notifyDataSetChanged();
            }
            else
            {
                loadUsers("carrot");
            }
        }
        return super.onOptionsItemSelected(item);
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
        mModelList = new ArrayList<>();

        // Converting all GithubUser objects
        for(GithubUser githubUser : githubUsers)
        {
            mModelList.add(
                    new UserListRecyclerModel()
                            .setGithubUser(githubUser)
            );
        }
        mRecyclerCoreAdapter = new RecyclerCoreAdapter(mModelList);
        mRecyclerViewLayout.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewLayout.setAdapter(mRecyclerCoreAdapter);
    }
}
