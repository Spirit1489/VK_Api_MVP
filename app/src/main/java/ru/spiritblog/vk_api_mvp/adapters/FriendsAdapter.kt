package ru.spiritblog.vk_api_mvp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import ru.spiritblog.vk_api_mvp.R
import ru.spiritblog.vk_api_mvp.models.FriendModel

class FriendsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var sourceList: ArrayList<FriendModel> = ArrayList()
    var friendsList: ArrayList<FriendModel> = ArrayList()

    fun setupFriends(setupList: ArrayList<FriendModel>) {
        sourceList.clear()
        sourceList.addAll(setupList)
        filter(query = "")


    }

    fun filter(query: String) {
        friendsList.clear()
        sourceList.forEach {
            if (it.name.contains(query, ignoreCase = true)
                || it.surname.contains(query, ignoreCase = true)
                || it.city.contains(query, ignoreCase = true)
            ) {
                friendsList.add(it)
            }

        }

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.cell_friend, parent, false)
        return FriendsViewHolder(itemView = itemView)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FriendsViewHolder) {
            holder.bind(friendModel = friendsList[position])
        }

    }

    override fun getItemCount(): Int {
        return friendsList.count()
    }


    class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var friendCity: TextView = itemView.findViewById(R.id.friend_txt_city)
        private var friendName: TextView = itemView.findViewById(R.id.friend_txt_userName)
        private var avatar: CircleImageView = itemView.findViewById(R.id.friend_avatar)
        private var friendOnline: View = itemView.findViewById(R.id.friend_view_online)

        fun bind(friendModel: FriendModel) {
            Picasso.get().load(friendModel.avatar).into(avatar)
            friendName.text = "${friendModel.name} ${friendModel.surname} "
            friendCity.text = "${friendModel.city}"
            if (friendModel.isOnline) {
                friendOnline.visibility = View.VISIBLE
            } else {
                friendOnline.visibility = View.GONE
            }


        }


    }


}