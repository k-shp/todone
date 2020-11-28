package todone

import todone.data._

object Controller {
  def tasks = Tasks(Model.tasks.toList)

  def close(id: Id): Option[Task] = {
    println(s"DEBUG: tasks before $tasks")
    val res = Model.tasks.updateWith(id)(t => t.map(_.close))
    println(s"DEBUG: tasks after $tasks")
    res
  }

}
