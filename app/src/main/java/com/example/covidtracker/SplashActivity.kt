package com.example.covidtracker

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    val interpolator = OvershootInterpolator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashLottie.playAnimation()
        splashLottie.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {
                target.visibility = View.VISIBLE
            }
        })

        target.visibility = View.VISIBLE

        Handler().postDelayed({

            target.animate().translationX(300f).translationY(100f).setInterpolator(interpolator).setDuration(500).start()

        },1000)

        Handler().postDelayed({

            target.animate().translationX(80f).translationY(180f).setInterpolator(interpolator).setDuration(500).start()

        },1800)

        Handler().postDelayed({

            target.animate().translationX(250f).translationY(250f).setInterpolator(interpolator).setDuration(500).start()

        },2600)

        Handler().postDelayed({

            target.animate().rotation(90f).scaleX(1.3f).scaleY(1.3f).setInterpolator(interpolator).setDuration(500).start()

        },3200)

        Handler().postDelayed({
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            splashLottie.cancelAnimation()
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }, 5000 )

    }
}