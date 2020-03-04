package com.example.yelpit

class LikeBusiness {
    var key : String?
    val userId : String?
    val businessID : String?

    constructor() {
        key = null
        userId = null
        businessID = null
    }

    constructor(key: String?, userId: String?, businessID: String?) {
        this.key = key
        this.userId = userId
        this.businessID = businessID
    }
}