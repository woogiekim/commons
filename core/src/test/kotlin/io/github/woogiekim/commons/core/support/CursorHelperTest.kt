package io.github.woogiekim.commons.core.support

import io.github.woogiekim.commons.core.data.CursorRequest
import io.github.woogiekim.commons.core.support.CursorHelper
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.function.LongSupplier

internal class CursorHelperTest {
    @Test
    fun `성공`() {
        val foo1 = Foo(1)
        val foo2 = Foo(2)
        val foo3 = Foo(3)

        val sut = CursorHelper.getCursor(listOf(foo1, foo2, foo3), CursorRequest(1, 2), { 4 }) { it.id }

        assertThat(sut.totalElements).isEqualTo(4)
        assertThat(sut.nextCursor).isEqualTo(3L)
    }

    @Test
    fun `성공 - 전체 개수 가져오기 안해도 되는 경우`() {
        val totalSupplier = mockkClass(LongSupplier::class)

        every { totalSupplier.asLong } returns -1

        val foo1 = Foo(1)
        val foo2 = Foo(2)
        val foo3 = Foo(3)

        val sut = CursorHelper.getCursor(listOf(foo1, foo2, foo3), CursorRequest(null, 4), totalSupplier) { it.id }

        assertThat(sut.totalElements).isEqualTo(3)
        assertThat(sut.nextCursor).isNull()

        verify(exactly = 0) {
            totalSupplier.asLong
        }
    }

    data class Foo(
        val id: Long
    )
}
