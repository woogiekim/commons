package io.github.woogiekim.commons.core.data

import io.github.woogiekim.commons.core.data.CursorImpl
import io.github.woogiekim.commons.core.data.CursorRequest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test

internal class CursorImplTest {
    @Test
    fun `성공 - 첫번째인 경우`() {
        val request = CursorRequest(null, 2)
        val content = listOf("1", "2", "3")

        val sut = CursorImpl(content, request, 3) { it.toString() }

        assertThat(sut.cursor).isNull()
        assertThat(sut.getNextCursor()).isEqualTo("3")
        assertThat(sut.size).isEqualTo(2)
        assertThat(sut.numberOfElements).isEqualTo(2)
        assertThat(sut.content).containsExactly("1", "2")
        assertThat(sut.hasContent()).isTrue
        assertThat(sut.totalElements).isEqualTo(3)
        assertThat(sut.hasNext()).isTrue
        assertThat(sut.isFirst).isTrue
        assertThat(sut.isLast).isFalse
        assertThat(sut.hasPrevious()).isFalse
        assertThat(sut.map { it.toLong() }).containsExactly(1, 2)

        assertThatExceptionOfType(UnsupportedOperationException::class.java).isThrownBy { sut.sort }
        assertThatExceptionOfType(UnsupportedOperationException::class.java).isThrownBy { sut.number }
        assertThatExceptionOfType(UnsupportedOperationException::class.java).isThrownBy { sut.nextPageable() }
        assertThatExceptionOfType(UnsupportedOperationException::class.java).isThrownBy { sut.previousPageable() }
    }

    @Test
    fun `성공 - 중간인 경우`() {
        val request = CursorRequest("3", 2)
        val content = listOf("3", "4", "5")

        val sut = CursorImpl(content, request, 5) { it.toString() }

        assertThat(sut.cursor).isEqualTo("3")
        assertThat(sut.getNextCursor()).isEqualTo("5")
        assertThat(sut.size).isEqualTo(2)
        assertThat(sut.numberOfElements).isEqualTo(2)
        assertThat(sut.content).containsExactly("3", "4")
        assertThat(sut.hasContent()).isTrue
        assertThat(sut.hasNext()).isTrue
        assertThat(sut.isFirst).isFalse
        assertThat(sut.isLast).isFalse
        assertThat(sut.hasPrevious()).isTrue
    }

    @Test
    fun `성공 - 마지막인 경우`() {
        val request = CursorRequest("5", 2)
        val content = listOf("5")

        val sut = CursorImpl(content, request, 5) { it.toString() }

        assertThat(sut.cursor).isEqualTo("5")
        assertThat(sut.getNextCursor()).isNull()
        assertThat(sut.size).isEqualTo(2)
        assertThat(sut.numberOfElements).isEqualTo(1)
        assertThat(sut.content).containsExactly("5")
        assertThat(sut.hasContent()).isTrue
        assertThat(sut.hasNext()).isFalse
        assertThat(sut.isFirst).isFalse
        assertThat(sut.isLast).isTrue
        assertThat(sut.hasPrevious()).isTrue
    }

    @Test
    fun `성공 - 빈 경우`() {
        val request = CursorRequest("6", 2)
        val content = emptyList<String>()

        val sut = CursorImpl(content, request, 5) { it.toString() }

        assertThat(sut.cursor).isEqualTo("6")
        assertThat(sut.getNextCursor()).isNull()
        assertThat(sut.numberOfElements).isEqualTo(0)
        assertThat(sut.content).isEmpty()
        assertThat(sut.hasContent()).isFalse
        assertThat(sut.hasNext()).isFalse
        assertThat(sut.isFirst).isFalse
        assertThat(sut.isLast).isTrue
        assertThat(sut.hasPrevious()).isTrue
    }
}
