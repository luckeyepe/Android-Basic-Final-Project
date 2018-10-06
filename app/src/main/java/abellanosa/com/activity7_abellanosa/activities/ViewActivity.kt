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

        studentList = ArrayList<Student>()
        layoutManager = LinearLayoutManager(this)
        adapter = StudentListAdapter(this.studentList!!, this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        mDatabaseReference!!.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (shot in snapshot.getChildren()) {
                    var student = shot.getValue(Student::class.java)
                    studentList!!.add(student!!)
                    adapter = StudentListAdapter(studentList!!, applicationContext)
                    recyclerView.adapter = adapter
                }
            }

        })

        adapter!!.notifyDataSetChanged()

    }
}
