package abellanosa.com.activity7_abellanosa.adapters

import abellanosa.com.activity7_abellanosa.R
import abellanosa.com.activity7_abellanosa.models.Student
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bindItem(student: Student){
        var fullName: TextView = itemView.findViewById(R.id.txt_studentRowFullName) as TextView
        var idNum: TextView = itemView.findViewById(R.id.txt_studentRowIDNum) as TextView
        var courseYear: TextView = itemView.findViewById(R.id.txt_studentRowCourseYear) as TextView
        var profilePicture: ImageView = itemView.findViewById(R.id.imgv_studentRowsProfileThumbnail) as ImageView

        fullName.text = "${student.lastName}, ${student.firstName} ${student.middleName}"
        idNum.text = student.idNum
        courseYear.text = "${student.course} - ${student.yearLevel}"
        profilePicture.setImageResource(R.drawable.default_avata)
    }
}