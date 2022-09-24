package org.icspl.ecdtlp.Adapter

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log.i
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_create_report.view.*
import kotlinx.android.synthetic.main.item_sync_adapter_row.view.*
import org.icspl.ecdtlp.R
import org.icspl.ecdtlp.models.SynNowMode
import android.view.animation.AlphaAnimation
import android.widget.Button


class SyncAdapter(private val mlist: ArrayList<SynNowMode>, val context: Context, private var iServerData: IServerData) :
        RecyclerView.Adapter<SyncAdapter.SyncAdapterHolder?>() {

    interface IServerData {
        fun sendingDataCallback(date: String, section: String, quarter: String)
    }

    inner class SyncAdapterHolder(view: View, viewType: Int) : RecyclerView.ViewHolder(view) {
        var view_type = 0
        lateinit var tv_date_row: TextView
        lateinit var tv_section_row: TextView
        lateinit var tv_quater_row: TextView
        lateinit var btn_syncr_row: Button

        init {

            if (viewType == TYPE_ITEM) {
                tv_date_row = itemView.findViewById(R.id.tv_date_row)
                tv_section_row = itemView.findViewById(R.id.tv_section_row)
                tv_quater_row = itemView.findViewById(R.id.tv_quater_row)
                btn_syncr_row = itemView.findViewById(R.id.btn_syncr_row)

                view_type = 1
            } else if (viewType == TYPE_HEAD) {

                view_type = 0
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyncAdapterHolder {


        val itemView: View
        var syncAdapterHolder: SyncAdapterHolder? = null
        if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_sync_adapter_row, parent, false)
            syncAdapterHolder = SyncAdapterHolder(itemView, viewType)
        } else if (viewType == TYPE_HEAD) {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_header_sync, parent, false)
            syncAdapterHolder = SyncAdapterHolder(itemView, viewType)
        }
        return syncAdapterHolder!!
    }


    override fun onBindViewHolder(holder: SyncAdapterHolder, position: Int) {
        i(TAG, "OnBindHolder")

        if (holder.view_type == TYPE_ITEM) {
            val model = mlist.get(position - 1)

            holder.tv_date_row.text = model.date
            holder.tv_section_row.text = model.section
            holder.tv_quater_row.text = model.quaters
            setFadeAnimation(holder.itemView)
            holder.btn_syncr_row.setOnClickListener {

                showAlertDialog(holder.tv_date_row, holder.tv_section_row, holder.tv_quater_row)
            }

        } else if (holder.view_type == TYPE_HEAD) {
        }

    }

    private fun showAlertDialog(tv_date_row: TextView, tv_section_row: TextView, tv_quaters_row1: TextView) {
        AlertDialog.Builder(context)
                .setTitle("Are you sure want to Sync?")
                .setMessage("Please check again before syncing your data of the day ${tv_date_row.text.toString()}")
                .setCancelable(false)
                .setPositiveButton("Send Data", { dialogInterface, i ->
                    dialogInterface.dismiss()
                    iServerData.sendingDataCallback(
                            tv_date_row.text.toString(),
                            tv_section_row.text.toString(),
                            tv_quaters_row1.text.toString())
                }).setNegativeButton("Cancel", { dialogInterface, i ->
                    dialogInterface.dismiss()
                }).show()
    }


    override fun getItemCount(): Int {
        return mlist.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEAD else TYPE_ITEM
    }

    companion object {
        val TAG = "SyncAdapter"
        private val TYPE_HEAD = 0
        private val TYPE_ITEM = 1
    }


    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 2000
        view.startAnimation(anim)
    }
}