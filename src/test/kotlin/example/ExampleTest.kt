package example

import org.junit.jupiter.api.Test

internal class ExampleTest {
    companion object {
        val nestedNestedDataWithNull = NestedNestedData(null)
        val nestedNestedData = NestedNestedData("")
        val nestedData = NestedData(listOf(nestedNestedData))
        val nestedDataWithNull = NestedData(listOf(nestedNestedData, nestedNestedDataWithNull))

        val testData = Data(
            listOf(nestedData, nestedDataWithNull)
        )
    }

    @Test
    internal fun `should pass`() {
        Example().doIt(testData)
    }
}