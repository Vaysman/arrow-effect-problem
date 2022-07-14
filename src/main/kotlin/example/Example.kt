package example

import arrow.core.continuations.effect
import arrow.core.continuations.ensureNotNull
import arrow.fx.coroutines.parTraverse
import kotlinx.coroutines.runBlocking

class Example {
    fun doIt(data: Data) = runBlocking {
        effect<ExampleError, Unit> {
            val result = doIt1(data).bind()
            println(result.size)
        }.fold(
            {
                when (it) {
                    ExampleError.NullError -> println("str is null")
                }
            },
            {}
        )
    }

    private fun doIt1(data: Data) =
        effect<ExampleError, List<List<String>>> {
            data.nested.parTraverse {nested ->
                doIt2(nested).bind()
            }
        }

    private fun doIt2(nested: NestedData) =
        effect<ExampleError, List<String>> {
            nested.nestedNested.parTraverse { nestedNested ->
                val str = ensureNotNull(nestedNested.str) { ExampleError.NullError }
                str
            }
        }
}


data class Data(val nested: List<NestedData>)

data class NestedData(val nestedNested: List<NestedNestedData>)

data class NestedNestedData(val str: String?)

sealed interface ExampleError {
    object NullError : ExampleError
}
