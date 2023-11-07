package edu.uga.cs.geographyquiz;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.geographyquiz.pojo.Question;


public class QuizPagerAdapter extends FragmentStateAdapter {

    private static final String DEBUG_TAG = "QuizPagerAdapter";
    private List<Question> questionList; // You need to pass the list of questions to the adapter

    public QuizPagerAdapter(FragmentActivity fragmentActivity, List<Question> questionList) {
        super(fragmentActivity);
        this.questionList = questionList;
        Log.d(DEBUG_TAG, DEBUG_TAG + ": QuizPagerAdapter questionList.size(): " + questionList.size());
    }

    public void updateQuestion(int position, Question question) {
        questionList.set(position, question);
        // Notify the adapter that the data has changed
        notifyDataSetChanged();
        Log.d(DEBUG_TAG, DEBUG_TAG + ": updateQuestion: " + question.toString());
    }


    @Override
    public Fragment createFragment(int position) {
        // Create and return a new instance of QuizQuestionFragment with the question data
        return QuizQuestionFragment.newInstance(questionList.get(position));
    }

    @Override
    public int getItemCount() {
        // Return the total number of questions in the quiz
        return questionList.size();
    }
}
