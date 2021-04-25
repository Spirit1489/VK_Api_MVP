package ru.spiritblog.vk_api_mvp.providers

import android.os.Handler
import android.util.Log
import com.google.gson.JsonParser
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject
import ru.spiritblog.vk_api_mvp.models.FriendModel
import ru.spiritblog.vk_api_mvp.presenters.FriendsPresenter

private const val TAG = "FriendsProvider"

class FriendsProvider(var presenter: FriendsPresenter) {

    fun testLoadFriends(hasFriends: Boolean) {
        Handler().postDelayed({
            val friendsList: ArrayList<FriendModel> = ArrayList()
            if (hasFriends) {
                val friend1 = FriendModel(
                    "Ivan",
                    "Petrov",
                    "Moscow",
                    "https://upload.wikimedia.org/wikipedia/ru/8/86/%D0%98%D0%B2%D0%B0%D0%BD_%D0%98%D0%B2%D0%B0%D0%BD%D0%BE%D0%B2%D0%B8%D1%87_%D0%9F%D0%B5%D1%82%D1%80%D0%BE%D0%B2_%28%D0%BF%D0%B5%D0%B2%D0%B5%D1%86%29.jpg",
                    true
                )
                val friend2 = FriendModel(
                    "asdf",
                    "asdfsdf",
                    "asdfsadf",
                    "https://lh3.googleusercontent.com/proxy/X8fntXvnV7RdT_zJ0ReH1nkN2T64EZKrJYLf_D3VN02_f_JyZ9eFcbJryTAQQOwsQgnsSn6bVA6JWIySluAe12GkVg27z_8ONA",
                    false
                )
                val friend3 = FriendModel(
                    "vasya",
                    "petuch",
                    "pososec",
                    "https://memohrc.org/sites/default/files/styles/new_teaser_big/public/halturin_maksim6162.jpg?itok=y7Bk8Eeu",
                    true
                )

                friendsList.add(friend1)
                friendsList.add(friend2)
                friendsList.add(friend3)

            }

            presenter.friendsLoaded(friendsList = friendsList)

        }, 2000)

    }


    fun loadFriends() {

        VK.execute(VKUsersRequest(), object : VKApiCallback<JSONObject> {
            override fun success(result: JSONObject) {
                val friendsList: ArrayList<FriendModel> = ArrayList()

                val parsedJson = JsonParser.parseString(result.toString()).asJsonObject
                parsedJson.get("response").asJsonObject.getAsJsonArray("items").forEach {

                    val city = if (it.asJsonObject.get("city") == null) {
                        ""
                    } else {
                        it.asJsonObject.get("city").asJsonObject.get("title").asString
                    }


                    val friend = FriendModel(
                        name = it.asJsonObject.get("first_name").asString,
                        surname = it.asJsonObject.get("last_name").asString,
                        city = city,
                        avatar = it.asJsonObject.get("photo_100").asString,
                        isOnline = it.asJsonObject.get("online").asInt == 1
                    )
                    friendsList.add(friend)

                }

                presenter.friendsLoaded(friendsList = friendsList)


            }

            override fun fail(error: Exception) {
                presenter.showError()
            }
        })

    }

    class VKUsersRequest : VKRequest<JSONObject> {
        constructor(uids: IntArray = intArrayOf()) : super("friends.get") {
            if (uids.isNotEmpty()) {
                addParam("user_ids", uids.joinToString(","))
            }
            addParam("fields", "sex, bdate, city, photo_100, online")
            addParam("count", "100")
        }

    }


}













