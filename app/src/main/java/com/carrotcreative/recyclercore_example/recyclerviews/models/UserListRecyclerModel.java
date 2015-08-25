package com.carrotcreative.recyclercore_example.recyclerviews.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carrotcreative.recyclercore.adapter.RecyclerCoreController;
import com.carrotcreative.recyclercore.adapter.RecyclerCoreModel;
import com.carrotcreative.recyclercore_example.R;
import com.carrotcreative.recyclercore_example.net.github.models.GithubUser;
import com.carrotcreative.recyclercore_example.recyclerviews.controllers.UserListRecyclerController;

public class UserListRecyclerModel extends RecyclerCoreModel
{
    GithubUser mGithubUser;

    @Override
    public RecyclerCoreController buildController(LayoutInflater inflater, ViewGroup parent)
    {
        View rootView = inflater.inflate(R.layout.element_user_list, parent, false);
        return new UserListRecyclerController(rootView);
    }

    public GithubUser getGithubUser()
    {
        return mGithubUser;
    }

    public UserListRecyclerModel setGithubUser(GithubUser githubUser)
    {
        mGithubUser = githubUser;
        return this;
    }
}
