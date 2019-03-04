package br.com.kanu.bugssmasher.domain

interface Contract {

    interface GameView{
        fun showBug(bug: Bug)
        fun hideBug(bug: Bug)
        fun showScore(score: Int)
        fun setIntroTextVisibility(visibility: Boolean)
        fun setGameOverTextVisibility(visibility: Boolean)
        fun setPlayButtonVisibility(visibility: Boolean)
        fun clearView()
    }

    interface GameEngine{
        fun onGameViewReady(view: GameView)
        fun onViewDestroyed()
        fun onPlayButtonClicked()
        fun onBugCliked(bug: Bug)
    }
}