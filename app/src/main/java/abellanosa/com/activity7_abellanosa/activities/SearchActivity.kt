package abellanosa.com.activity7_abellanosa.activities

import abellanosa.com.activity7_abellanosa.R
import abellanosa.com.activity7_abellanosa.models.Student
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.popup_edit_student.view.*

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
            if (edt_seachIDNum.text.toString().trim().isEmpty() && txt_searchIDNum.text == getString(R.string.id_number_text)){
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

        btn_searchEdit.setOnClickListener {
            Toast.makeText(applicationContext, "Edit button clicked", Toast.LENGTH_LONG).show()
            updateStudent()
        }
    }

    private fun updateStudent() {
        var dialogBuilder: AlertDialog.Builder?
        var alert: AlertDialog.Builder?
        var dialog: AlertDialog?
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Students")

        if (edt_seachIDNum.text.toString().trim().isEmpty() && txt_searchIDNum.text == getString(R.string.id_number_text)){
            alertDialog!!.setTitle("Error")
            alertDialog!!.setMessage("Fill in all fields")
            alertDialog!!.show()
        }else{
            //start making the popup after checking the value of the id Number
            var idNum = txt_searchIDNum.text.toString().trim()
            var view = LayoutInflater.from(this).inflate(R.layout.popup_edit_student, null)

            var popupIdNum = view.edt_editPopupStudentIDNum
            var popupFirstName = view.edt_editPopupStudentFirstName
            var popupMiddelName = view.edt_editPopupStudentMiddelName
            var popupLastName = view.edt_editPopupStudentLastName
            var popupCourse = view.edt_editPopupStudentCourse
            var popupYearLevel = view.edt_editPopupStudentYearLevel
            var popupUpdateButton = view.btn_editPopupStudentUpdate

            //fill in the edtitext with hints about current student info
            mDatabaseReference!!.child(idNum).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    popupIdNum.hint = snapshot.child("idNum").value.toString()
                    popupFirstName.hint = snapshot.child("firstName").value.toString()
                    popupMiddelName.hint = snapshot.child("middleName").value.toString()
                    popupLastName.hint = snapshot.child("lastName").value.toString()
                    popupCourse.hint = snapshot.child("course").value.toString()
                    popupYearLevel.hint = snapshot.child("yearLevel").value.toString()
                }

            })

            popupIdNum.isEnabled = false

            dialogBuilder = AlertDialog.Builder(this).setView(view)
            dialog = dialogBuilder!!.create()
            dialog?.show()

            popupUpdateButton.setOnClickListener {
                var s = Student()

                //copy values in to student s
                if (!popupFirstName.text.isNullOrEmpty()){
                    s.firstName = popupFirstName.text.toString().trim()
                }else{
                    s.firstName = popupFirstName.hint.toString().trim()
                }

                if (!popupMiddelName.text.isNullOrEmpty()){
                    s.middleName = popupMiddelName.text.toString().trim()
                }else{
                    s.middleName = popupMiddelName.hint.toString().trim()
                }

                if (!popupLastName.text.isNullOrEmpty()){
                    s.lastName = popupLastName.text.toString().trim()
                }else{
                    s.lastName = popupLastName.hint.toString().trim()
                }

                if (!popupCourse.text.isNullOrEmpty()){
                    s.course = popupCourse.text.toString().trim()
                }else{
                    s.course = popupCourse.hint.toString().trim()
                }

                if (!popupYearLevel.text.isNullOrEmpty()){
                    s.yearLevel = popupYearLevel.text.toString().trim()
                }else{
                    s.yearLevel = popupYearLevel.hint.toString().trim()
                }

                s.idNum = idNum

                //save to realtime database
                mDatabaseReference!!.child(idNum).setValue(s)

                //popup info
                alertDialog!!.setTitle("SUCCESS")
                alertDialog!!.setMessage("Student info has been updated")
                alertDialog!!.show()

                dialog.dismiss()
            }
        }
    }
}
