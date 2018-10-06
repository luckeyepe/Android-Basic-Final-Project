package abellanosa.com.activity7_abellanosa.adapters

import abellanosa.com.activity7_abellanosa.R
import abellanosa.com.activity7_abellanosa.models.Student
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


///this class is used to bind a list of items into a layout, in this instance it is a list of student classes
//The studentviewholder class is used to inflate the layout you selected
class StudentListAdapter(private val list: ArrayList<Student>, private val context: Context): RecyclerView.Adapter<StudentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.students_row, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bindItem(list[position])
    }
}