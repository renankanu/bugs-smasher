package br.com.kanu.bugssmasher

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import br.com.kanu.bugssmasher.domain.Bug
import br.com.kanu.bugssmasher.domain.Contract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Contract.GameView {

    private val engine: GameEngine

    init {
        this.engine = GameEngine()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        playButton.setOnClickListener {
            engine.onPlayButtonClicked()
        }

        this.engine.onGameViewReady(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.engine.onViewDestroyed()
    }

    override fun showBug(bug: Bug) {
        val bugView = ImageView(this)
        bugView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.mite))
        bugView.scaleType = ImageView.ScaleType.FIT_CENTER
        bugView.tag = bug
        bugView.setOnClickListener {
            it?.let {
                engine.onBugCliked(it.tag as Bug)
            }
        }
        val bugSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56f, resources.displayMetrics)
        val layoutParams = FrameLayout.LayoutParams(bugSize.toInt(), bugSize.toInt())
        val screenWidth = gameLayout!!.width
        val screenHeight = gameLayout!!.height
        layoutParams.leftMargin = (bug.x * screenWidth).toInt()
        layoutParams.topMargin = (bug.y * screenHeight).toInt()
        gameLayout?.addView(bugView, layoutParams)
    }

    override fun hideBug(bug: Bug) {
        gameLayout?.let {
            for (i: Int in 0..gameLayout!!.childCount) {
                val view = gameLayout!!.getChildAt(i)
                val bugg = view.tag
                if (bug == bugg) {
                    gameLayout!!.removeView(view)
                    break
                }
            }
        }
    }

    override fun showScore(score: Int) {
        scoreText?.text = "Pontos: $score"
    }

    override fun setIntroTextVisibility(visibility: Boolean) {
        introText?.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }

    override fun setGameOverTextVisibility(visibility: Boolean) {
        gameOver?.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }

    @SuppressLint("RestrictedApi")
    override fun setPlayButtonVisibility(visibility: Boolean) {
        playButton?.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }

    override fun clearView() {
        gameLayout?.removeAllViews()
        introText?.visibility = View.INVISIBLE
        gameOver?.visibility = View.INVISIBLE
    }
}
