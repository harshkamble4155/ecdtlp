package org.icspl.ecdtlp.Adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.nfc.Tag
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.util.Log.i
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.alert_preview_image.view.*
import kotlinx.android.synthetic.main.fragment_create_report.view.*
import kotlinx.android.synthetic.main.image_view_alert.view.*
import kotlinx.android.synthetic.main.item_view_single_fragment.view.*
import org.icspl.ecdtlp.R
import org.icspl.ecdtlp.models.PreviewModel
import android.provider.MediaStore
import android.graphics.Bitmap



class PreviewAdapter(var mList: ArrayList<PreviewModel>, val context: Context) :
        RecyclerView.Adapter<PreviewAdapter.PreviewViewHolder>() {
    companion object {
        private val TAG = "PreviewAdapter"
    }


    class PreviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_ts_no = view.tv_ts_no
        val type = view.type
        val tS_Ch = view.tS_Ch
        val ts_Location = view.ts_Location
        val carrrier_Pro_MIN = view.carrrier_Pro_MIN
        val carrrier_Pro_MAX = view.carrrier_Pro_MAX
        val casing_Un_Pro_Min_On = view.casing_Un_Pro_Min_On
        val casing_Un_Pro_Max_Off = view.casing_Un_Pro_Max_Off
        val valve = view.valve
        val potential_1 = view.potential_1
        val potential_2 = view.potential_2
        val ac_PSP = view.ac_PSP
        val zinc_Value = view.zinc_Value
        val date = view.date
        val time = view.time
        val manual_Remarks = view.manual_Remarks
        val btn_tlp_Photo = view.btn_tlp_Photo
        val btn_reading_Photo = view.btn_reading_Photo
        val btn_selfie = view.btn_selfie
        val btn_startkm = view.btn_startkm
        val btn_endkm = view.btn_endkm
        val btn_vehicle_no = view.btn_vehicle_no
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder =
            PreviewViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_view_single_fragment, parent, false))

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        i(TAG, "OnBindHolder")
        val model = mList.get(position)
        holder.tv_ts_no.text = model.tsNo

        holder.type.text = model.type
        holder.tS_Ch.text = model.tsCh
        holder.ts_Location.text = model.tsLoc
        holder.carrrier_Pro_MIN.text = model.cpMin
        holder.carrrier_Pro_MAX.text = model.cpMax
        holder.casing_Un_Pro_Min_On.text = model.casMin
        holder.casing_Un_Pro_Max_Off.text = model.casMax
        holder.valve.text = model.valve
        holder.potential_1.text = model.p1
        holder.potential_2.text = model.p2
        holder.ac_PSP.text = model.acPSP
        holder.zinc_Value.text = model.zinc
        holder.date.text = model.date
        holder.time.text = model.time
        holder.manual_Remarks.text = model.manRemark
        holder.btn_tlp_Photo.setOnClickListener {
            showPhoto(model.tlpFile)
        }
        holder.btn_reading_Photo.setOnClickListener {
            i(TAG, "tag ${model.tlpFile}")
            showPhoto(model.readingFile)
        }
        holder.btn_startkm.setOnClickListener {
            showPhoto(model.startKmFile)
        }
        holder.btn_endkm.setOnClickListener {
            showPhoto(model.endKmFile)
        }
        holder.btn_selfie.setOnClickListener {
            showPhoto(model.selfieFile)
        }
        holder.btn_vehicle_no.setOnClickListener {
            showPhoto(model.vehicalNoFile)
        }

    }

    private fun showPhoto(fileTag: String) {
        i(TAG, "tag $fileTag")

        if (fileTag!=null){
            val dialogView = LayoutInflater.from(context).inflate(R.layout.alert_preview_image, null)

            val img = dialogView.img_files
            img.setImageURI(Uri.parse(fileTag))
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("View Image")
            alertDialog.setView(dialogView)
            alertDialog.setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.show()
        }



    }


}