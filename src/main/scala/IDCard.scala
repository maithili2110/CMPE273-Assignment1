
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
class IDCard {
  
@BeanProperty
var cardId: String = _

@BeanProperty @(NotNull @beanGetter)
var card_name: String = _

@BeanProperty @(NotNull @beanGetter)
var card_number : String = _

val dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
val date = new Date()


@BeanProperty
var expiration_date: String = dateFormat.format(date)
  
}