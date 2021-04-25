package ru.spiritblog.vk_api_mvp.presenters

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.spiritblog.vk_api_mvp.R
import ru.spiritblog.vk_api_mvp.models.FriendModel
import ru.spiritblog.vk_api_mvp.providers.FriendsProvider
import ru.spiritblog.vk_api_mvp.views.FriendsView


@InjectViewState
class FriendsPresenter : MvpPresenter<FriendsView>() {
    fun loadFriends() {
        viewState.startLoading()
        FriendsProvider(presenter = this).loadFriends()
    }


    fun friendsLoaded(friendsList: ArrayList<FriendModel>) {
        viewState.endLoading()
        if (friendsList.size == 0) {
            viewState.setupEmptyList()
            viewState.showError(textResource = R.string.friends_no_items)
        } else {
            viewState.setupFriendsList(friendsList = friendsList)
        }


    }

    fun showError(){
        viewState.showError(textResource = R.string.login_error_credentials)

    }



}