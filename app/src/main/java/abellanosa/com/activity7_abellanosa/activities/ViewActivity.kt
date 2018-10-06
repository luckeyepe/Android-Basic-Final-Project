package abellanosa.com.activity7_abellanosa.activities

import abellanosa.com.activity7_abellanosa.R
import abellanosa.com.activity7_abellanosa.adapters.StudentListAdapter
import abellanosa.com.activity7_abellanosa.models.Student
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_view.*
import com.google.firebase.database.DataSnapshot



class ViewActivity : AppCompatActivity() {
    private var mDatabaseReference: DatabaseReference ?= null
    private var adapter: StudentListAdapter ?= null
    private var studentList: ArrayList<Student> ?= null
    private var layoutManager: RecyclerView.LayoutManager ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        supportActionBar!!.hide()
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child("Students")

        studentList = ArrayList<Student>()//a list of students
        layoutManager = LinearLayoutManager(this)//sets the layout to a scrolling layout that goes up and down(similar to scroll view)
        adapter = StudentListAdapter(this.studentList!!, this)//passes the list of students and the application context

        recyclerView.layoutManager = layoutManager//assign layout
        recyclerView.adapter = adapter//assign adapter

        mDatabaseReference!!.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                //this loop goes through ever branch under the "Student" node in the firebase database
                for (shot in snapshot.getChildren()) {
                    var student = shot.getValue(Student::class.java)//using the Student model, the data grabed from one node is easily placed into a student class
                    studentList!!.add(student!!)//adds a student into the list
                    adapter = StudentListAdapter(studentList!!, applicationContext)//update the list of students
                    recyclerView.adapter = adapter//updates the adapter
                }
            }

        })

        adapter!!.notifyDataSetChanged()//shows the adapter

    }
}
