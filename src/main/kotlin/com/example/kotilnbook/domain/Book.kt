package com.example.kotilnbook.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "book")
class Book (
        title: String,
        @Column(name = "price", nullable = false)
        var price:Int= 0

) : AuditingEntity(){
        @Column(name = "title", nullable = false)
        var title:String = title
                protected set


        fun toDto():BookResponse {

             return BookResponse(title=this.title, price=this.price, createAt=this.createdAt)

        }


}
