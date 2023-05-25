package com.example.appskasir.Database

import androidx.room.*

@Dao
interface CafeDao {

//    User
    @Insert
    fun addUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM User")
    fun getUser(): List<User>

    @Query("SELECT * FROM User WHERE id=:User_id")
    fun getUser(User_id: Int): List<User>

    @Query("SELECT * FROM User WHERE email =:mEmail AND password =:mPass")
    fun login(mEmail: String, mPass: String): List<User>

//    Menu
    @Insert
    fun addMenu(menu: Menu)

    @Update
    fun updateMenu(menu: Menu)

    @Delete
    fun deleteMenu(menu: Menu)

    @Query("SELECT * FROM Menu")
    fun getMenu(): List<Menu>

    @Query("SELECT * FROM Menu WHERE id=:Menu_id")
    fun getMenu(Menu_id: Int): List<Menu>

//    Meja
    @Insert
    fun addMeja(meja: Meja)

    @Update
    fun updateMeja(meja: Meja)

    @Delete
    fun deleteMeja(meja: Meja)

    @Query("SELECT * FROM Meja")
    fun getMeja(): List<Meja>

    @Query("SELECT * FROM Meja WHERE id=:mejaId")
    fun getMeja(mejaId: Int): List<Meja>

}