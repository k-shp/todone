package todone

import munit.{FunSuite, ScalaCheckSuite}
import org.scalacheck.Gen
import org.scalacheck.Prop.{forAll, propBoolean}
import todone.data.{Id, State, Task}

class ControllerSuite extends FunSuite with ScalaCheckSuite {
  test("Controller should get all the tasks") {
    assertEquals(Controller.tasks.tasks.size, 2)
  }
  test("Controller should close the task when given a valid open task Id") {
    val taskId = Id(1)
    val before: Option[(Id, Task)] = Controller.tasks.tasks.find(_._1 == taskId)
    val after: Option[Task] = Controller.close(taskId)
    assertEquals(before.map(_._2.state), Some(State.open))
    assertEquals(after.map(_.state), Some(State.closed))
  }

  // Here we're relying on state from previous spec
  // This could lead to issues with ordering, debugging, mental load of keeping state in mind during implementation
  // We could address that by using suite-local and test-local fixtures
  test("Controller should do nothing when given a closed task Id") {
    val taskId = Id(1)
    val result: Option[Task] = Controller.close(taskId)
    assertEquals(result.map(_.state), Some(State.closed))
  }
  test("Close method should return None when called with a nonexistent task Id") {
    val taskId = Id(12345)
    val result: Option[Task] = Controller.close(taskId)
    assertEquals(result, Option.empty[Task])
  }

  // Same as above but trying lots of different possible ids with the help of scalacheck
  property("Close method should return None when called with any nonexistent task Id") {
    val taskIds: List[Int] = Controller.tasks.tasks.map(_._1.id)
    val nonexistentTaskIdGenerator = Gen.choose(-10000,10000) suchThat(n => !taskIds.contains(n))
    forAll(nonexistentTaskIdGenerator) {n => Controller.close(Id(n)).isEmpty}
  }
}
