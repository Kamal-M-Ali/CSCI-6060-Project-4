package edu.uga.cs.geographyquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class QuizFragment extends Fragment {

    public QuizFragment()
    {
        // required default constructor
    }

    /**
     * Creates a new QuizFragment based on the chosen question
     *
     * @param  questionIndex integer representing the index of the question
     *                       in the quiz database
     */
    public static QuizFragment newInstance( int questionIndex ) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt( "question", questionIndex );
        fragment.setArguments( args );
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false );
    }




}
