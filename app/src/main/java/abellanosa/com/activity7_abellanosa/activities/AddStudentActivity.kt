package abellanosa.com.activity7_abellanosa.activities

import abellanosa.com.activity7_abellanosa.R
import abellanosa.com.activity7_abellanosa.models.Student
import android.app.AlertDialog
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_student.*

class AddStudentActivity : AppCompatActivity() {
    var mDatabaseReference: DatabaseReference? = null
    var alertDialog: AlertDialog.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        supportActionBar!!.title = "Add Student"
        alertDialog = AlertDialog.Builder(this)
        val progress = ProgressDialog(this)

        btn_addStudentAdd.setOnClickListener {
            //adds a loading popup
            progress.setTitle("Loading")
            progress.setMessage("Wait while loading...")
            progress.setCancelable(false) // disable dismiss by tapping outside of the dialog
            progress.show()

            if (isCompleteInfo()) {
                var idNum = edt_addStudentIDNum.text.toString().trim()
                var fname = edt_addStudentFirstName.text.toString().trim()
                var mname = edt_adStudentMiddelName.text.toString().trim()
                var lname = edt_addStudentLastName.text.toString().trim()
                var course = edt_addStudentCourse.text.toString().trim()
                var yearLevel = edt_addStudentYearLevel.text.toString().trim()

                mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Students")

                mDatabaseReference!!.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        //checks the db if id number is already in the database
                        if (p0.hasChild(idNum)) {
                            progress.dismiss()
//                            Toast.makeText(applicationContext, "Error there", Toast.LENGTH_LONG).show()
                            alertDialog!!.setTitle("ERROR")
                            alertDialog!!.setMessage("Student is already added to the database")
                            alertDialog!!.show()
                        }
                        else{
                            mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Students").child(idNum)

                            var s = Student()
                            s.idNum = idNum
                            s.firstName = fname
                            s.middleName = mname
                            s.lastName = lname
                            s.course = course
                            s.yearLevel = yearLevel

                            mDatabaseReference!!.setValue(s).addOnCompleteListener {
                                task: Task<Void> ->
                                if(task.isSuccessful){
                                    // To dismiss the dialog
                                    progress.dismiss()
                                    alertDialog!!.setTitle("INFO")
                                    alertDialog!!.setMessage("Student is now added to the database")
                                    alertDialog!!.show()
                                }
                                else{
                                    // To dismiss the dialog
                                    progress.dismiss()
                                    alertDialog!!.setTitle("ERROR")
                                    alertDialog!!.setMessage("Student is not added to the database")
                                    alertDialog!!.show()
                                }
                            }
                        }
                    }
                })
            }
            else{
                alertDialog!!.setTitle("ERROR")
                alertDialog!!.setMessage("Please Fill up all fields")
                alertDialog!!.show()
            }
        }
    }
        private fun isCompleteInfo(): Boolean {
            return !(edt_addStudentIDNum.text.isNullOrEmpty() && edt_addStudentFirstName.text.isNullOrEmpty() && edt_adStudentMiddelName.text.isNullOrEmpty() && edt_addStudentLastName.text.isNullOrEmpty()
                    && edt_addStudentCourse.text.isNullOrEmpty() && edt_addStudentYearLevel.text.isNullOrEmpty())
        }
}
