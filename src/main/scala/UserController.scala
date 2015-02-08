
import java.lang.Long
import scala.beans.BeanProperty
import org.springframework.boot._
import org.springframework.boot.autoconfigure._
import org.springframework.stereotype._
import org.springframework.web.bind.annotation._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.immutable._
import java.sql.Timestamp
import net.liftweb.json.DefaultFormats
import net.liftweb.json._
import java.util.Enumeration
import java.util.HashMap
import java.util.Map
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.ModelAndView
import java.text.SimpleDateFormat
import scala.collection.script.Remove
import org.hibernate.validator.constraints.NotEmpty
import javax.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import javax.servlet.http.HttpServletResponse
import main.scala.ErrorMessage
import org.springframework.context.annotation.ComponentScan
import scala.util.control.Breaks._



object UserController {
def main(args:Array[String]) {
SpringApplication.run(classOf[UserController])
}
}


@Controller
@EnableAutoConfiguration
@RequestMapping(Array("api/v1/users"))
	class UserController {

  
  var userList = new Array[Map[String, String]](100)
  var userid : Int = 1
  var user_id : String = "u-"+userid
  
  
  @RequestMapping(value=Array("/"),method =Array(RequestMethod.POST))
  @ResponseBody
	 private def addUser(@RequestBody @Valid user :User,bindingResult: BindingResult,httpResponse :HttpServletResponse ) : Any = {
    
   if( bindingResult.hasErrors())
    {
     httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST)
      new ErrorMessage("Error")
      
    }
   else {
    user.uid = user_id
    var userDetails = scala.collection.mutable.Map("user_id" -> user_id,
    												"email" -> user.getEmail,
    												"password" -> user.getPassword,
    												"name" -> user.getName,
    												"created_at" -> user.getCreated_at,
    												"updated_at" -> user.getUpdated_at)												
    userList(userid-1) = userDetails
      userid = userid + 1
      user_id = "u-"+userid
      httpResponse.setStatus(HttpServletResponse.SC_CREATED)
      user
   }
  }
  
   
@RequestMapping(value=Array("{uid}"),method =Array(RequestMethod.GET))
@ResponseBody
	def viewUser(@PathVariable uid: String) = {
	println("inside GET")
	var res = uid.split("-")
         var index = res(1).toInt
         //      UsersArray(index-1)
	  userList(index-1)
	
	}


@RequestMapping(value=Array("/{uid}"),method =Array(RequestMethod.PUT))
@ResponseBody
def updateUser(@PathVariable uid: String,@Valid @RequestBody user :User ,bindingResult: BindingResult,httpResponse :HttpServletResponse ) : Any = {
    
   if( bindingResult.hasErrors())
    {
     httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST)
      new ErrorMessage("Error")
      
    }
   else {
 
     var res = uid.split("-")
         var index = res(1).toInt
  
         var userString = userList(index-1)
         //var userString = userList(uid.toInt-1)
  
  
  userString.keys.foreach{ i =>  
    					
    						if(i.equalsIgnoreCase("name")) user.setName(userString(i))
    						if(i.equalsIgnoreCase("created_at")) user.setCreated_at(userString(i))
    						if(i.equalsIgnoreCase("uid")) user.setUid(userString(i))
    						val dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    						user.setUpdated_at((new Date()).toString())
                           print( "Key = " + i )
                           println(" Value = " + userString(i) )
                           }
	  
	
  userString("email") = user.getEmail
  userString("password") = user.getPassword
  userList(index-1) = userString
  //userList (uid.toInt -1) = userString
  httpResponse.setStatus(HttpServletResponse.SC_CREATED)
  user  
   }
}

//IDCard 

 
  var CardList =  new ArrayList[Map[String, String]](100)
  var cardid : Int = 1	
  var card_id : String = ""
	
  @RequestMapping(value=Array("/{userid}/idcards"),method =Array(RequestMethod.POST))
  @ResponseBody
	 private def addidcard(@PathVariable userid :String ,@RequestBody @Valid card :IDCard ,bindingResult: BindingResult,httpResponse :HttpServletResponse ) : Any = {
    if( bindingResult.hasErrors())
    {
     httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST)
     new ErrorMessage("Error!")
      
    }
   else {
     var res = userid.split("-")
         var index = res(1).toInt
         //card_id = "u-" + index + "c-" + cardid
         card_id = "c-" +cardid + "u-" +index
    //card_id = "U-" + userid + "IDCard-"+cardid
    card.cardId  = card_id
    
    var idcarddetails = scala.collection.mutable.Map("card_id" -> card_id ,								
    												"card Name" -> card.getCard_name,
    												"card Number" -> card.getCard_number,
    												"expiration date" -> card.getExpiration_date)
    																				
    CardList.add(idcarddetails)
      cardid = cardid + 1
      //card_id = "IDcard-"+cardid
      httpResponse.setStatus(HttpServletResponse.SC_CREATED)
      card
   }
  }
  
  
@RequestMapping(value=Array("{uid}/idcards"),method =Array(RequestMethod.GET))
@ResponseBody
	def listidcards(@PathVariable uid:String) = {
	println("inside GET")
	var temp : String=""
	var res = uid.split("-")
    var index = res(1).toInt
	
	for (i <- 0 until CardList.length )
	  if (CardList(i).get("card_id").contains("u-"+index))
	//if (CardList(i).get("card_id").startsWith("u-" +index))
	  {
	    
	    temp +=CardList(i).toString()
	    println(temp)
	  }
	     	
	temp
  }
	 
	   
@ResponseStatus(HttpStatus.NO_CONTENT)
@RequestMapping(value=Array("{uid}/idcards/{cid}"),method =Array(RequestMethod.DELETE))
@ResponseBody
	def removeidcard(@PathVariable uid:String, @PathVariable cid :String) = {
	println("inside DELETE")
	var temp : String=""
	  var res = uid.split("-")
    var index = res(1).toInt
	  
	var flag: Int = 0 

	  for (i <- 0 until (CardList.length))
	  {
	    if ( flag == 0) 
	    {
	    
	    if (CardList(i).get("card_id").equals(cid))
	    {   	   
	    println("inside delete if loop")
	    CardList.remove(i)
	    flag = 1 
	    }   
	    }
	  }
	}
	
 
//WebLogin
var WebLoginList =  new ArrayList[Map[String, String]](100)
  var loginid : Int = 1
  var login_id : String = ""
	 
 
  @RequestMapping(value=Array("/{userid}/weblogins"),method =Array(RequestMethod.POST))
  @ResponseBody
	 private def addweblogin(@PathVariable userid :String ,@Valid @RequestBody weblogin :WebLogin ,bindingResult: BindingResult,httpResponse :HttpServletResponse ) : Any = {
    
   if( bindingResult.hasErrors())
    {
     httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST)
      new ErrorMessage("Error")
      
    }
   else {
     
   var res = userid.split("-")
    var index = res(1).toInt
     login_id = "l-" + loginid + "u-" +index
  //login_id = "U-" + userid + "L-" + loginid  
  println(login_id)
  weblogin.login_id  = login_id
    
    var logindetail = scala.collection.mutable.Map("login_id" -> login_id,								
    												"url" -> weblogin.getUrl,
    												"login" -> weblogin.getLogin,
    												"password" -> weblogin.getPassword)
    																				
  WebLoginList.add(logindetail)
  loginid = loginid + 1
  httpResponse.setStatus(HttpServletResponse.SC_CREATED)
  weblogin
   }
	 } 


@RequestMapping(value=Array("{uid}/weblogins"),method =Array(RequestMethod.GET))
@ResponseBody
	def listweblogin(@PathVariable uid:String) = {
	var temp : String=""
	
	   var res = uid.split("-")
    var index = res(1).toInt
	for (i <- 0 until WebLoginList.length )
	{
	  if (WebLoginList(i).get("login_id").contains("u-"+index))
	//if (WebLoginList(i).get("login_id").startsWith("U-" +uid))
	  {  
	    temp +=WebLoginList(i).toString()
	    println(temp)
	  }	     	
	//temp
	}
	temp
  }

@ResponseStatus(HttpStatus.NO_CONTENT)
@RequestMapping(value=Array("{uid}/weblogins/{cid}"),method =Array(RequestMethod.DELETE))
@ResponseBody
	def removeweblogin(@PathVariable uid:String, @PathVariable cid :String) = {
	var temp : String=""
	  var flag: Int = 0 

	  
	  for (i <- 0 until (WebLoginList.length))
	  {
	    if (flag == 0)
	  {
	    
	  if (WebLoginList(i).get("login_id").equals(cid))
	  {   	   
	    WebLoginList.remove(i)
	    flag =1
	  }
	  }
	  }
	}

//Bank Account


var BankAccList =  new ArrayList[Map[String, String]](100)
  var baid : Int = 1
  var ba_id : String = ""
	 
   
  @RequestMapping(value=Array("/{userid}/bankaccounts"),method =Array(RequestMethod.POST))
  @ResponseBody
	 private def addbankacc(@PathVariable userid :String ,@Valid @RequestBody bankacc :BankAccount ,bindingResult: BindingResult,httpResponse :HttpServletResponse ) : Any = {
    
   if( bindingResult.hasErrors())
    {
     httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST)
      new ErrorMessage("Error")
      
    }
   else {
     var res = userid.split("-")
    var index = res(1).toInt
    ba_id ="b-"+baid +"u-" +index
  //ba_id = "U-" + userid + "B-" + baid  
  println(ba_id)
  bankacc.ba_id  = ba_id
    
    var bankdetail = scala.collection.mutable.Map("ba_id" -> ba_id,								
    												"account_name" -> bankacc.getAccount_name,
    												"routing_number" -> bankacc.getRouting_number,
    												"account_number" -> bankacc.getAccount_number)
    																				
  BankAccList.add(bankdetail)
      baid = baid + 1
 httpResponse.setStatus(HttpServletResponse.SC_CREATED)
      bankacc
   }
	 } 


@RequestMapping(value=Array("{uid}/bankaccounts"),method =Array(RequestMethod.GET))
@ResponseBody
	def listbankacc(@PathVariable uid:String) = {
	var temp : String=""
	var res = uid.split("-")
    var index = res(1).toInt
    
	for (i <- 0 until BankAccList.length ){
	  if (BankAccList(i).get("ba_id").contains("u-" +index))
	//if (BankAccList(i).get("ba_id").startsWith("U-" +uid))
	  {  
	    temp +=BankAccList(i).toString()
	    println(temp)
	  }	     	
	}
	temp
  }

@ResponseStatus(HttpStatus.NO_CONTENT)
@RequestMapping(value=Array("{uid}/bankaccounts/{cid}"),method =Array(RequestMethod.DELETE))
@ResponseBody
	def removebankacc(@PathVariable uid:String, @PathVariable cid :String) = {
	var temp : String=""
var flag: Int = 0 
	  for (i <- 0 until (BankAccList.length))
	  {
	    if(flag ==0)
	    {
	   
	    	if (BankAccList(i).get("ba_id").equals(cid))
	    	{   	   
	    		BankAccList.remove(i)
	    		flag=1
	    	}
	    }
	        
	  }
	}

  /*@ExceptionHandler()
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  def handleException(exception: Exception): ErrorMessage = {
    println("hey...")
    
    new ErrorMessage("Error")
  }
  */

}