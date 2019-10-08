package com.example.myscheduler

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

//ここの引数　data: OrderedRealmCollection<Schedule>はデータの一覧（データセット）
//autoUpdate: true -> アダプター表示を自動更新する false -> 自動更新しない

class ScheduleAdapter(data: OrderedRealmCollection<Schedule>) :
    RealmRecyclerViewAdapter<Schedule, ScheduleAdapter.ViewHolder>(data, true) {

    private var listener: ((Long?) -> Unit)? = null
    fun setOnItemClickListener(listener: (Long?) -> Unit) {
        this.listener = listener
    }

    init {
        setHasStableIds(true)
    }
    //セルに使用するビューの設定を保持する（表示のセットを意味する）
    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val title: TextView = cell.findViewById(android.R.id.text1)
        val detail: TextView = cell.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
        val infrater = LayoutInflater.from(parent.context)
        val view = infrater.inflate(
            android.R.layout.simple_list_item_2,
            parent, false
        )
        return ViewHolder(view)
    }
    //RecycleViewによって呼び出され、指定位置にデータを表示する
    override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder, position: Int) {
        val schedule: Schedule? = getItem(position)
//        holder.date.text = DateFormat.format("yyyy/MM/dd", schedule?.date)
        holder.title.text = schedule?.title
        holder.detail.text = schedule?.detail
        holder.itemView.setOnClickListener {
            listener?.invoke(schedule?.id)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: 0
    }
}