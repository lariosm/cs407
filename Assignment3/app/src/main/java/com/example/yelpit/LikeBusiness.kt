package com.example.yelpit

class LikeBusiness {
    val userId : String?
    val businessID : String?

    constructor() {
        userId = null
        businessID = null
    }

    constructor(userId: String?, businessID: String?) {
        this.userId = userId
        this.businessID = businessID
    }
}