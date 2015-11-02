package com.carrotcreative.recyclercore_example.recyclerviews.models;

import com.carrotcreative.recyclercore_example.R;
import com.carrotcreative.recyclercore_example.net.github.models.GithubUser;
import com.carrotcreative.recyclercore_example.recyclerviews.controllers.UserListRecyclerController;
import com.carrrotcreative.recyclercore.annotations.RecyclerCoreModel;

@RecyclerCoreModel(controller = UserListRecyclerController.class, layout = R.layout.element_user_list)
public class UserListRecyclerModel
{
    GithubUser mGithubUser;

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
