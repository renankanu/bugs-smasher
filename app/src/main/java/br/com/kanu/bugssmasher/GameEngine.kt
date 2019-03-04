package br.com.kanu.bugssmasher

import android.os.Handler
import br.com.kanu.bugssmasher.domain.Bug
import br.com.kanu.bugssmasher.domain.Contract
import java.util.*

class GameEngine : Contract.GameEngine {

    private var gameView: Contract.GameView? = null

    private val random: Random = Random()

    private var bugs: MutableList<Bug> = ArrayList<Bug>()

    private var score: Int = 0

    private val handler: Handler = Handler()

    private val showNewBugRunnable = object : Runnable {
        override fun run() {
            if (bugs.isNotEmpty()) {
                handler.removeCallbacks(this)
                gameView?.clearView()
                gameView?.setGameOverTextVisibility(true)
                gameView?.setPlayButtonVisibility(true)
            } else {
                val bug = Bug((bugs.size + 1).toLong(), random.nextFloat(), random.nextFloat())
                bugs.add(bug)
                gameView?.showBug(bug)
                handler.postDelayed(this, 1500)
            }
        }

    }

    override fun onGameViewReady(view: Contract.GameView) {
        gameView = view
    }

    override fun onViewDestroyed() {
        gameView = null
    }

    override fun onPlayButtonClicked() {
        gameView?.setPlayButtonVisibility(false)
        gameView?.clearView()
        bugs.clear()
        showNewBugRunnable.run()
    }

    override fun onBugCliked(bug: Bug) {
        bugs.remove(bug)
        gameView?.hideBug(bug)
        score++
        gameView?.showScore(score)
    }
}