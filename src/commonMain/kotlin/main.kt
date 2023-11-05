import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

suspend fun main() {

}

suspend fun testFlows() {
    val flow = flow<Int> {
        var counter = 2
        emit(1)
        emit(11)
        emit(111)
        while (true) {
            delay(1000)
            emit(counter)
            counter++
        }
    }
    withContext(Dispatchers.Default) {
        launch {
            flow.collect { value ->
                println("collect 1: $value")
            }
        }

        launch {
            flow.collect { value ->
                println("collect 2: $value")
            }
        }
        launch {
            flow.collectLatest { value ->
                println("collect 3: $value")
            }
        }

        delay(5000)
        launch {
            flow.collect { value ->
                println("collect 4: $value")
            }
        }
    }
}