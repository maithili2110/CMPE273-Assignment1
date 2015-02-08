
import javax.persistence.Id
import javax.persistence.GeneratedValue
import java.lang.Long
import javax.persistence.Entity
import scala.beans.BeanProperty
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.boot._
import org.springframework.boot.autoconfigure._
import org.springframework.stereotype._
import org.springframework.web.bind.annotation._
import java.util._
import scala.collection.JavaConversions._
import java.sql.Timestamp
import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import scala.annotation.meta.beanGetter
import javax.validation.constraints.NotNull


@Entity
class BankAccount {
  
@BeanProperty
var ba_id: String = _

@BeanProperty
var account_name: String = _


//@NotEmpty(message ="Routing number must be entered!")
@BeanProperty @(NotNull @beanGetter)
var routing_number : String = _

@BeanProperty
@NotEmpty(message ="Account number must be entered!")
var account_number : String = _


  
}