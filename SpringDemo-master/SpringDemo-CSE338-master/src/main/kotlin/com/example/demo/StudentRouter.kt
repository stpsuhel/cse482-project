package com.example.demo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class Users(@Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null, var username: String? = null, var fullname: String? = null, var phonenumber: Long? = null, var password: String? = null, var notification: Int = 0, var state: String? = null)

@Entity
class Products(@Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null, var productname: String? = null, var producttype: String? = null, var productdetail: String? = null, var productprice: String? = null, var productseller: Long = 0, var productbuyer: Long = 0, var rating:String? =null)

class LogingClass(val username: String? = null, val password: String? = null)

interface UsersRepository : JpaRepository<Users, Long> /*{
    @Query("SELECT * FROM Users t WHERE LOWER(t.username) = LOWER(:user.username) and LOWER(t.password) = LOWER(:user.password) ")
    fun findinLog(@Param("user") user: LogingClass): Users
}*/

interface ProductRepository : JpaRepository<Products, Long>


@RestController
@RequestMapping("/users")
class UserController(val usersRepository: UsersRepository, val productsRepository: ProductRepository) {


    @GetMapping("/getAllusers")
    fun getAll(): MutableList<Users> {
        return usersRepository.findAll()
    }

    @GetMapping("/Allproducts")
    fun getAllProduct(): MutableList<Products> {
        return productsRepository.findAll()
    }



    class cRewuest(var productid: Long? = null, var userid: Long? = null)

    @PostMapping("/UpdateProduct")
    fun updateProduct(@RequestBody dataa: cRewuest): Products {
        var myP: Optional<Products> = productsRepository.findById(dataa.productid!!)
        var data = Products()
        if (myP.isPresent) {
            data = myP.get()
            data.productbuyer = dataa.userid!!
            return productsRepository.save(data)
        }
        return data
    }



    class crew(var prodid: Long? = null)

    @PostMapping("/removeProduct")
    fun removeProduct(@RequestBody dataa: crew): Products {
        var myP: Optional<Products> = productsRepository.findById(dataa.prodid!!)
        var data = Products()
        if (myP.isPresent) {
            data = myP.get()
            data.productbuyer = 0
            data.rating = null
            return productsRepository.save(data)
        }
        return data
    }


    class crews(var proid: Long? = null)
    @PostMapping("/deleteProduct")
    fun deleteProduct(@RequestBody datas: crews): Products {
        var myP: Optional<Products> = productsRepository.findById(datas.proid!!)
        var data = Products()
        if (myP.isPresent) {
            data = myP.get()
            data.productbuyer = 0
            data.productdetail = null
            data.productname = null
            data.productprice = null
            data.productseller = 0
            data.producttype = null
            data.rating = null
            return productsRepository.save(data)
        }
        return data
    }



    class rat(var proid:Long? = null, var ratings:String? = null)

    @PostMapping("/rateProduct")
    fun productRate(@RequestBody info:rat):Products
    {
        var myP: Optional<Products> = productsRepository.findById(info.proid!!)
        var data = Products()
        if (myP.isPresent)
        {
            data = myP.get()
            data.rating = info.ratings!!
            return productsRepository.save(data)
        }
        return data
    }


    class note(var flags:Long? = null)
    @PostMapping("/notification")
    fun notify(@RequestBody info:note):MutableList<Users>
    {
        var myU:List<Users> = usersRepository.findAll()
        var data = Users()
        if(info.flags!!.equals(1))
        {
            myU.forEach {
                it.notification = 1
            }
            return usersRepository.saveAll(myU)
        }
        return usersRepository.saveAll(myU)
    }


    @PostMapping("/saveuser")
    fun saveUsers(@RequestBody users: Users): String {
        print(users.fullname)
        usersRepository.save(users)
        return " Hoise "
    }

    @PostMapping("/sellproduct")
    fun saveUsers(@RequestBody products: Products): String {
        productsRepository.save(products)
        return " Hoise "
    }

    /*@PostMapping("/Lgin")
    fun lohin(@RequestBody products: LogingClass): Users {
        return usersRepository.findinLog(products)

    }*/

    @PostMapping("/updateuser")
    fun updateUsers(@RequestBody users: Users): String {
        print(users.fullname)
        usersRepository.save(users)
        return " Hoise "
    }

}