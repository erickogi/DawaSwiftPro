package com.dev.dawaswift.adapters.pharmacy


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.dawaswift.R
import com.dev.dawaswift.models.pharmacy.Pharmacy
import java.util.*

class PharmacyItemsAdapter(
    internal var main: Context,
    private var modelList: List<Pharmacy>?,
    private val recyclerListener: OnRecyclerViewItemClick
) : RecyclerView.Adapter<PharmacyItemViewHolder>(), Filterable {
    private var orig: List<Pharmacy>? = null
    override fun getFilter(): Filter {


        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var oReturn: FilterResults = FilterResults()
                var results: MutableList<Pharmacy>? = LinkedList()

                if (orig == null)
                    orig = modelList
                if (constraint != null) {
                    if ((orig != null) and (orig!!.isNotEmpty())) {
                        for (g in orig!!) {
                            if (g.name?.toLowerCase()!!.contains(constraint.toString())) results?.add(g)
                        }
                    }
                    oReturn.values = results
                }
                return oReturn

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                modelList = results?.values as List<Pharmacy>
                notifyDataSetChanged()
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyItemViewHolder {
        var itemView: View? = null

        itemView = LayoutInflater.from(parent.context).inflate(R.layout.pharmacy_item_linear, parent, false)



        return PharmacyItemViewHolder(itemView!!, recyclerListener)
    }


    override fun onBindViewHolder(holder: PharmacyItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.name.text = model.name
        holder.phone.text = model.tel
        holder.email.text = model.email

        if (model.isOpen == "0") {
            holder.open.visibility = View.VISIBLE
            holder.closed.visibility = View.GONE
        } else {
            holder.open.visibility = View.GONE
            holder.closed.visibility = View.VISIBLE

        }

        CommonUtils().loadImage(main, model.image, holder.image)

    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }


    fun refresh(modelList: List<Pharmacy>?) {
        this.modelList = modelList
        notifyDataSetChanged()

    }
}
