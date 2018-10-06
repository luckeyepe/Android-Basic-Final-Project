package abellanosa.com.activity7_abellanosa.activities

import abellanosa.com.activity7_abellanosa.R
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    var mDatabaseReference: DatabaseReference ?= null
    var alertDialog: AlertDialog.Builder ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar!!.title = "Search for Student"
        alertDialog = AlertDialog.Builder(this)
        val progress = ProgressDialog(this)

        btn_searchSearch.setOnClickListener {
            if (edt_seachIDNum.text.toString().trim().isEmpty()){
                alertDialog!!.setTitle("Error")
                alertDialog!!.setMessage("Fill in all fields")
                alertDialog!!.show()
            }else{
                progress.setTitle("Loading")
                progress.setMessage("Wait while loading...")
                progress.setCancelable(false) // disable dismiss by tapping outside of the dialog
                progress.show()

                var idNum = edt_seachIDNum.text.toString().trim()
                mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Students")

                mDatabaseReference!!.addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChild(idNum)){
                            var idNumber = snapshot.child(idNum).child("idNum").value.toString()
                            var fullName = "${snapshot.child(idNum).child("firstName").value.toString()} ${snapshot.child(idNum).child("middleName").value.toString()[0].toUpperCase()}. " +
                                    "${snapshot.child(idNum).child("lastName").value.toString()}"
                            var courseYear = "${snapshot.child(idNum).child("course").value.toString()} - ${snapshot.child(idNum).child("yearLevel").value.toString()}"

                            txt_searchIDNum.text = idNumber
                            txt_searchStudFullName.text = fullName
                            txt_searchCourseYear.text = courseYear
                            progress.dismiss()
                        }else{
                            progress.dismiss()
                            alertDialog!!.setTitle("ERROR")
                            alertDialog!!.setMessage("Student is not in the database")
                            alertDialog!!.show()
                        }
                    }

                })
            }
        }

        btn_searchDelete.setOnClickListener {
            if (edt_seachIDNum.text.toString().trim().isEmpty()){
                alertDialog!!.setTitle("Error")
                alertDialog!!.setMessage("Fill in all fields")
                alertDialog!!.show()
            }else{
                progress.setTitle("Loading")
                progress.setMessage("Wait while loading...")
                progress.setCancelable(false) // disable dismiss by tapping outside of the dialog
                progress.show()

                var idNum = edt_seachIDNum.text.toString().trim()
                mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Students")

                mDatabaseReference!!.addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChild(idNum)){
                            mDatabaseReference!!.child(idNum).setValue(null)
                            txt_searchIDNum.text = getString(R.string.id_number_text)
                            txt_searchStudFullName.text = getString(R.string.student_name_text)
                            txt_searchCourseYear.text = getString(R.string.course_year_text)
                            progress.dismiss()
                        }else{
                            progress.dismiss()
                            alertDialog!!.setTitle("ERROR")
                            alertDialog!!.setMessage("Student is not in the database")
                            alertDialog!!.show()
                        }
                    }

                })
            }
        }
    }
}
