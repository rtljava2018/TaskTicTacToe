package com.rtllabs.tasktictactoe

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rtllabs.tasktictactoe.ui.TicTacToeScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TicTacToeAcceptanceTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()


    val moves = listOf(
        0 to 0, 0 to 1, 0 to 2,
        1 to 1, 1 to 0, 1 to 2,
        2 to 1, 2 to 0, 2 to 2
    )

    //a game is over when all fields are taken means draw

    @Test
    fun givenAllFieldsTakenNoWinnerThenGameIsDraw() {
        launchGame()

        moves.forEach { (r, c) ->
            composeRule.onNodeWithTag("cell_${r}_$c").performClick()
        }

        composeRule.onNodeWithText("Draw").assertIsDisplayed()
    }

    //A game is over when all fields in a column are taken by a player

    @Test
    fun givenPlayerCompletesColumnWhenGameEndsThenPlayerWins() {
        launchGame()

        composeRule.onNodeWithTag("cell_0_0").performClick() // X
        composeRule.onNodeWithTag("cell_0_1").performClick() // O
        composeRule.onNodeWithTag("cell_1_0").performClick() // X
        composeRule.onNodeWithTag("cell_1_1").performClick() // O
        composeRule.onNodeWithTag("cell_2_0").performClick() // X

        composeRule.onNodeWithText("Winner: X").assertIsDisplayed()
    }

    //A game is over when all fields in a row are taken by a player

    @Test
    fun givenPlayerCompletesRowWhenGameEndsThenPlayerWins() {
        launchGame()

        composeRule.onNodeWithTag("cell_0_0").performClick() // X
        composeRule.onNodeWithTag("cell_1_0").performClick() // O
        composeRule.onNodeWithTag("cell_0_1").performClick() // X
        composeRule.onNodeWithTag("cell_1_1").performClick() // O
        composeRule.onNodeWithTag("cell_0_2").performClick() // X

        composeRule.onNodeWithText("Winner: X").assertIsDisplayed()
    }

    //A game is over when all fields in a diagonal are taken by a player

    @Test
    fun givenPlayerCompletesDiagonalWhenGameEndsThenPlayerWins() {
        launchGame()

        composeRule.onNodeWithTag("cell_0_0").performClick() // X
        composeRule.onNodeWithTag("cell_0_1").performClick() // O
        composeRule.onNodeWithTag("cell_1_1").performClick() // X
        composeRule.onNodeWithTag("cell_0_2").performClick() // O
        composeRule.onNodeWithTag("cell_2_2").performClick() // X

        composeRule.onNodeWithText("Winner: X").assertIsDisplayed()
    }


    //A player can take a field if not already taken
    @Test
    fun givenPlayersAlternateTurnsWhenGameInProgressThenTurnsSwitchCorrectly() {
        launchGame()

        composeRule.onNodeWithTag("cell_0_0").performClick() // X
        composeRule.onNodeWithTag("cell_0_1").performClick() // O

        composeRule.onNodeWithTag("cell_0_0").assertTextEquals("X")
        composeRule.onNodeWithTag("cell_0_1").assertTextEquals("O")
    }

    //No moves allowed after game is over

    @Test
    fun givenGameIsOverWhenPlayerClicksCellThenBoardDoesNotChange() {
        launchGame()


        composeRule.onNodeWithTag("cell_0_0").performClick()
        composeRule.onNodeWithTag("cell_1_0").performClick()
        composeRule.onNodeWithTag("cell_0_1").performClick()
        composeRule.onNodeWithTag("cell_1_1").performClick()
        composeRule.onNodeWithTag("cell_0_2").performClick()

        composeRule.onNodeWithTag("cell_2_2").performClick()//Added extra click after game over

        composeRule.onNodeWithText("Winner: X").assertIsDisplayed()
        composeRule
            .onNodeWithTag("cell_2_2")
            .assertTextEquals("")
    }



    private fun launchGame() {
        composeRule.setContent {
            TicTacToeScreen()
        }
    }
}