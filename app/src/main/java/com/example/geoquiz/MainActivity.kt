package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.example.geoquiz.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    //9/26
    private lateinit var binding: ActivityMainBinding

    //9/26
    //private lateinit var trueButton: Button;
    //private lateinit var falseButton: Button;
    //9/21 model tier for data
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    // Define the question bank with question text resource IDs and correct answers



    private var currentIndex = 0 //tracking the current index

    //Initialize an array to keep track of answered questions
    private var answeredQuestions = Array<Pair<Boolean, Boolean>>(questionBank.size) {
        Pair(false, false)
    }



    //9/21 view binding makes the coding and widget connected together easier
    //instead of using findviewbyid
    // This function is called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //9/26
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //bind the local button variable with the widget in the
        //layout view
        //9/26
        //trueButton =  findViewById(R.id.true_button)
        //falseButton = findViewById(R.id.false_button)

        binding.trueButton.setOnClickListener { view: View ->
            //display message by calling toast
            //9/26
            // Toast.makeText(this, R.string.correct_toast,
            //Toast.LENGTH_LONG).show()
            checkAnswer(true)
            disableAnswerButtons()
        }

        binding.falseButton.setOnClickListener { view: View ->
            //display message by calling toast
            //9/26
            // Toast.makeText(this, R.string.Incorrect_toast,
            //Toast.LENGTH_LONG).show()
            checkAnswer(false)
            disableAnswerButtons()
        }
        //9/26
        val questionTextResId =
            questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            //binding.questionTextView.setText(questionBank[currentIndex].textResId)
            updateQuestion()

        }

        binding.questionTextView.setOnClickListener { view: View ->
            //This is for the challenge add a listener to the textview so now you can click the text view to go the the next question
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()


        }

        binding.previousButton.setOnClickListener{
            currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
            updateQuestion()

        }

        updateQuestion()
    }



    // Update the question text and button states
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
        // Disable/enable answer buttons based on whether the question has been answered
        if (answeredQuestions[currentIndex].first) {
            disableAnswerButtons()
        } else {
            enableAnswerButtons()
        }

        if (answeredQuestions.all { it.first }) {
            calculateAndDisplayScore()
        }
    }

    // Check the user's answer and update the answeredQuestions array
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val answeredCorrectly = userAnswer == correctAnswer
        answeredQuestions[currentIndex] = Pair(true, answeredCorrectly)

        val messageResId = if (answeredCorrectly) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show()

        // If all questions have been answered, calculate and display the score
        if (answeredQuestions.all { it.first }) {
            calculateAndDisplayScore()
        }
    }


    // Disable answer buttons
    private fun disableAnswerButtons() {
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }


    // Disable answer buttons
    private fun enableAnswerButtons() {
        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true
    }


    // Calculate and display the quiz score
    private fun calculateAndDisplayScore() {
        val totalQuestions = questionBank.size
        val correctAnswers = answeredQuestions.count { it.second }
        val percentageScore = (correctAnswers.toFloat() / totalQuestions.toFloat()) * 100
        val scoreMessage = "Your score: ${percentageScore.toInt()}%"
        Toast.makeText(this, scoreMessage, Toast.LENGTH_LONG).show()
    }
}




