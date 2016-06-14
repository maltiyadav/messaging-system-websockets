package models.domain

import java.util.Date

object Users{

  case class UserForm(name:String,email:String,password:(String,String),joinDate: Date)

  case class LoginForm(email:String,password: String)
}