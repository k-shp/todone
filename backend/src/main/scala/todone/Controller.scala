package todone

import todone.data._

object Controller {
  def tasks = Tasks(Model.tasks.toList)

  def close(id: Id): Option[Task] = {
    Model.tasks.updateWith(id)(t => t.map(_.close))
  }

}
