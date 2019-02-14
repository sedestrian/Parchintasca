package com.agaboardi.parchintasca.list

import android.location.Location
import android.support.design.card.MaterialCardView
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.common.GlideApp
import com.agaboardi.parchintasca.data.source.local.db.models.Park
import com.agaboardi.parchintasca.common.extensions.getResourceId
import com.agaboardi.parchintasca.common.views.RecyclerViewEmpty
import com.agaboardi.parchintasca.data.source.local.db.models.ModelFavorite
import com.agaboardi.parchintasca.map.domain.model.ParkCategory
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.item_park.view.*
import mindtek.it.bikeapp.kotlin.extensions.db

/**
 * Created by bipol on 3/13/2018.
 */
class HolderParkList(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val icon: AppCompatImageView = itemView.typeIconHeader
    private val name: AppCompatTextView = itemView.name
    private val favorite: LikeButton = itemView.favorite
    private val location: AppCompatTextView = itemView.address
    private val emptyTag: MaterialCardView = itemView.emptyView
    private val type: TextView = itemView.type
    private val emptyTagName: TextView = itemView.emptyTagName
    private val tags: RecyclerViewEmpty = itemView.tags
    private val card: MaterialCardView = itemView.sheet
    private val item = itemView

    fun bind(position: Int, park: Park, onItemClick: ((park: Park, position: Int) -> Unit)?){
        name.text = park.name
        type.text = type.context.getString(ParkCategory.named(park.category_id))
        GlideApp.with(name.context)
                .load(ParkCategory.drawableFor(park.category_id))
                .error(R.color.grey_35)
                .into(icon)
        tags.layoutManager = LinearLayoutManager(icon.context, LinearLayoutManager.HORIZONTAL, false)
        emptyTagName.text = name.context.getString(R.string.empty_tag_list)
        tags.setEmptyView(emptyTag)
        val adapter = AdapterTags(park)
        adapter.setOnClick { onItemClick?.invoke(park, position) }
        tags.adapter = adapter
        location.text = park.address?.getAddressLine(0)
        favorite.isLiked = db.parkDao().isFavorite(park.id)
        favorite.setOnLikeListener(object : OnLikeListener {
            override fun liked(p0: LikeButton?) {
                db.parkDao().setFavorite(ModelFavorite(park.id, true))
                park.favorite = true
                db.parkDao().savePark(park)
            }

            override fun unLiked(p0: LikeButton?) {
                db.parkDao().removeFavorite(park.id)
                park.favorite = false
                db.parkDao().savePark(park)
            }
        })
        card.setOnClickListener {
            onItemClick?.invoke(park, position)
        }
    }
}