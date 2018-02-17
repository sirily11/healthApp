package com.example.qiweili.healthapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_main_friend.view.*

/**
 * Created by qiweili on 2018/2/15.
 */
class MyAdapterForFriendScreen(context: Context, data: List<DataAPI>) :RecyclerView.Adapter<FriendScreen.ViewHolder>() {
    /**
     * Private variables
     */
    private var data = data
    private val myContext = context

    /**
     * This method will determine the length of the recycleview's length
     */
    override fun getItemCount(): Int {
        return data.size
    }

    /**
     * Some knowledge about the Layout Inflater:
     * Layout Inflater actually is a converter which will find the
     * layout file from your res/layout folder(That is why we need to
     * have a parent.context. And convert the XML layout file into
     * the Kotlin(or Java) object.
     * ----------------------------------------------------------------
     * What does the view holder do in android?
     * View holder is actually a holder which contains all the subviews
     * of the parent view. The reason why we use holder instead of calling
     * the subviews each time is that calling a subview is very inefficient
     * way. Each time we run out code, the Android itself will call the
     * FindViewByID, so the best way to improve the speed is save all the views
     * into the view holder. And I believe in the list view, creating a view holder
     * is optional, but in the recycle view that Android will require you
     * to create a view holder.
     * ----------------------------------------------------------------
     * And the second line is about connect the current
     * parent layout(recycle layout) to the child layout(row_main_friendscreen)
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FriendScreen.ViewHolder{

        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.row_main_friend, parent, false)
        return FriendScreen.ViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: FriendScreen.ViewHolder, position: Int) {
        holder?.view?.first_name?.text = data[position].first_name
        holder?.view?.last_name?.text = data[position].last_name
        val imageView = holder?.view?.imageView_profile
        Picasso.with(myContext)
                .load(data.get(position).img_url)
                .resize(300,300)
                .into(imageView);
    }


}



