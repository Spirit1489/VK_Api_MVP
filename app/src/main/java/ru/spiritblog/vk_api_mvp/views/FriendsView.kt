package ru.spiritblog.vk_api_mvp.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.spiritblog.vk_api_mvp.models.FriendModel


@StateStrategyType(value = AddToEndSingleStrategy::class)
interface FriendsView : MvpView {

    fun showError(textResource: Int)

    fun setupEmptyList()

    fun setupFriendsList(friendsList: ArrayList<FriendModel>)

    fun startLoading()

    fun endLoading()


}