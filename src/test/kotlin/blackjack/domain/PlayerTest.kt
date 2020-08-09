package blackjack.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal class PlayerTest {

    @Test
    fun `초기 카드 2장 받고 해당 카드가 있는지 확인`() {
        // given
        val expectedCard = Card.denominationOf("A")
        val deck = object : DrawStrategy {
            override fun fetchCard(): Card {
                return expectedCard
            }
        }
        val player = Player("Malibin")

        // when
        player.deal(deck)

        // then
        assertThat(player.getCards()).isEqualTo(listOf(expectedCard, expectedCard))
    }

    @Test
    fun `21점 초과후 hit시 exception`() {
        // given
        val expectedCard = Card.denominationOf("K")
        val deck = object : DrawStrategy {
            override fun fetchCard(): Card {
                return expectedCard
            }
        }
        val player = Player("Malibin", Cards("8", "9", "10"))

        // then
        assertThatThrownBy { player.hit(deck) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("21점을 초과해 카드를 더 가져올 수 없습니다.")
    }

    @Test
    fun `플레이어의 현재 점수 계산`() {
        // given
        val player = Player("Malibin", Cards("A", "J"))

        // then
        assertThat(player.getScore()).isEqualTo(21)
    }
}