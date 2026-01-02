package io.github.woogiekim.commons.core.data

import io.github.woogiekim.commons.core.data.CursorRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CursorRequestTest {
    @Test
    fun `성공 - 첫번째인 경우`() {
        val sut = CursorRequest(null, 10)

        assertThat(sut.cursor).isNull()
        assertThat(sut.cursorSize).isEqualTo(10)
        assertThat(sut.first).isSameAs(sut)
        assertThat(sut.hasPrevious()).isFalse
        assertThat(sut.convert { it.toLong() }).isNull()
    }

    @Test
    fun `성공 - 중간인 경우`() {
        val sut = CursorRequest(1, 10)

        assertThat(sut.cursor).isEqualTo(1)
        assertThat(sut.first).isNotSameAs(sut)
        assertThat(sut.hasPrevious()).isTrue
        assertThat(sut.convert { it.toLong() }).isEqualTo(1)
    }
}
