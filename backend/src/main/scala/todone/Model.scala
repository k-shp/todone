package todone

import todone.data._

import scala.collection.mutable

object Model {
  var tasks = mutable.LinkedHashMap(
      Id(1) -> Task(
        State.open,
        "Play with the ToDone interface",
        "",
        None,
        Tags.empty),
      Id(2) -> Task(
        State.open,
        "Implement functionality to close a completed task",
        "",
        None,
        Tags.empty)
  )
}
