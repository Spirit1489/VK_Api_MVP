package ru.spiritblog.vk_api_mvp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import ru.spiritblog.vk_api_mvp.R
import ru.spiritblog.vk_api_mvp.adapters.FriendsAdapter
import ru.spiritblog.vk_api_mvp.models.FriendModel
import ru.spiritblog.vk_api_mvp.presenters.FriendsPresenter
import ru.spiritblog.vk_api_mvp.views.FriendsView

class FriendsActivity : MvpAppCompatActivity(), FriendsView {

    @InjectPresenter
    lateinit var friendsPresenter: FriendsPresenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var errorView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var friendsAdapter: FriendsAdapter
    private lateinit var searchView: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        recyclerView = findViewById(R.id.recyclerView)
        errorView = findViewById(R.id.txt_friends_no_items)
        progressBar = findViewById(R.id.progress_bar_friends)
        searchView = findViewById(R.id.txt_friends_search)

        searchView.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                friendsAdapter.filter(s.toString())

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


        friendsPresenter.loadFriends()
        friendsAdapter = FriendsAdapter()
        recyclerView.adapter = friendsAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(
                applicationContext,
                RecyclerView.VERTICAL, false
            )
        recyclerView.setHasFixedSize(true)


    }


    // Friends View Implementation


    override fun showError(textResource: Int) {
        errorView.text = getString(textResource)

    }

    override fun setupEmptyList() {
        recyclerView.visibility = View.GONE
        errorView.visibility = View.VISIBLE

    }

    override fun setupFriendsList(friendsList: ArrayList<FriendModel>) {
        recyclerView.visibility = View.VISIBLE
        errorView.visibility = View.GONE

        friendsAdapter.setupFriends(setupList = friendsList)
    }


    override fun startLoading() {
        errorView.visibility = View.GONE
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun endLoading() {
        progressBar.visibility = View.GONE
    }
}