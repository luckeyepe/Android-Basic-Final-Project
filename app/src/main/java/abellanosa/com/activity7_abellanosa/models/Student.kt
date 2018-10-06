package abellanosa.com.activity7_abellanosa.models

class Student() {
    var idNum: String ?= null
    var firstName: String ?= null
    var middleName: String ?= null
    var lastName: String ?= null
    var course: String ?= null
    var yearLevel: String ?= null
    var profilePicuter: String ?= null
    var thumbnailPicture: String ?= null

    constructor(idNum: String, firstName: String, middleName: String, lastName:String, course: String, yearLevel: String, profilePicture: String, thumbnailPicture: String): this() {
        this.idNum = idNum
        this.firstName = firstName
        this.middleName = middleName
        this.lastName = lastName
        this.course = course
        this.yearLevel = yearLevel
        this.profilePicuter = profilePicture
        this.thumbnailPicture = thumbnailPicture
    }
}