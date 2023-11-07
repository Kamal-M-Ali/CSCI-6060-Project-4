package edu.uga.cs.geographyquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.uga.cs.geographyquiz.pojo.Question;
import edu.uga.cs.geographyquiz.pojo.QuestionSet;
import edu.uga.cs.geographyquiz.pojo.Quiz;

public class QuizQuestionFragment extends Fragment {
    private static final String TAG = "QuizQuestionFragment";

    private static final String ARG_QUESTION = "question";
    public static QuizQuestionFragment newInstance(Question question) {
        QuizQuestionFragment fragment = new QuizQuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, (Serializable) question);
        fragment.setArguments(args);
        return fragment;
    }

    private static final String DEBUG_TAG = "QuizQuestionFragment";
    private TextView textView;
    private TextView textView2;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioGroup radioGroup2;
    private RadioButton radioButtonT;
    private RadioButton radioButtonF;
    private GeographyQuizData geographyQuizData;
    private Quiz quiz;
    private QuestionSet questionSet;
    private List<Question> questionList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, DEBUG_TAG + ": QuizQuestionFragment Created: ");
        // Inflate the layout for this fragment (quiz_question_fragment.xml)
        View view =  inflater.inflate(R.layout.fragment_quiz_question, container, false);
        textView = view.findViewById(R.id.textView);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioButton1 = view.findViewById(R.id.radioButton1);
        radioButton2 = view.findViewById(R.id.radioButton2);
        radioButton3 = view.findViewById(R.id.radioButton3);
        radioButtonT = view.findViewById(R.id.radioButtonT);
        radioButtonF = view.findViewById(R.id.radioButtonF);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup2 = view.findViewById(R.id.radioGroup2);
        return view;
    }

        // Initialize and set up the question and radio buttons in the fragment
        //geographyQuizData = new GeographyQuizData(getApplicationContext());
    @Override
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState ) {
        Log.d(DEBUG_TAG, DEBUG_TAG + ": QuizQuestionFragment onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        quiz = new Quiz();
        //questionSet = new QuestionSet();
        //questionList = new ArrayList<>();

        //Need to implement storing the answers to the database
        //listener to take in which answer the user chose
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId contains the ID of the selected RadioButton
                RadioButton selectedRadioButton = view.findViewById(checkedId);
                if (selectedRadioButton != null) {
                    String selectedCity = selectedRadioButton.getText().toString();
                    if(selectedCity.equalsIgnoreCase(questionList.get(quiz.getProgress()).getCapitalCity())) {
                        Toast.makeText(getActivity(), "Correct! It is the capital", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Incorrect! It is not the capital", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //listener to take in which answer the user chose
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId contains the ID of the selected RadioButton
                RadioButton selectedRadioButton = view.findViewById(checkedId);
                if (selectedRadioButton != null) {
                    String selectedTorF = selectedRadioButton.getText().toString();
                    if(selectedTorF.equalsIgnoreCase("True") &&
                            questionList.get(quiz.getProgress()).getSizeRank() == 1 ) {
                        Toast.makeText(getActivity(), "Correct! It is also the largest city.", Toast.LENGTH_SHORT).show();
                    }
                    else if(selectedTorF.equalsIgnoreCase("False") &&
                            questionList.get(quiz.getProgress()).getSizeRank() == 1 ) {
                        Toast.makeText(getActivity(), "Incorrect! It is also the largest city.", Toast.LENGTH_SHORT).show();
                    }
                    else if(selectedTorF.equalsIgnoreCase("True") &&
                            questionList.get(quiz.getProgress()).getSizeRank() != 1 ) {
                        Toast.makeText(getActivity(), "Incorrect! It is NOT also the largest city.", Toast.LENGTH_SHORT).show();
                    }
                    else if(selectedTorF.equalsIgnoreCase("False") &&
                            questionList.get(quiz.getProgress()).getSizeRank() != 1 ) {
                        Toast.makeText(getActivity(), "Correct! It is NOT also the largest city.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "You should not ever reach here", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        geographyQuizData.open();
        //new QuizQuestionFragment.QuizDBReader().execute();
    }
    public void updateQuestion(Question question) {
        if (question != null) {
            textView.setText("Which of the following is the capital city of " + question.getState() + "?");
            //get the three cities for the question in an array
            String [] shuffle = { question.getCapitalCity(), question.getSecondCity(),
                    question.getThirdCity()};
            //shuffle the answer order
            List<String> answers = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                answers.add(shuffle[i]);
            }//end for
            //shuffle the answer order
            Collections.shuffle(answers, new Random());
            //hold the 3 shuffled answers here
            int count = 3;
            String[] rand = new String[count];
            for (int i = 0; i < count; i++) {
                String randomAnswer = answers.get(i);
                rand[i] = randomAnswer;
            }//end for

            // set the buttons to be the city names in a shuffled order so its not the same each time
            radioButton1.setText(rand[0]);
            radioButton2.setText(rand[1]);
            radioButton3.setText(rand[2]);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        // open the database in onResume
        if( geographyQuizData != null )
            geographyQuizData.open();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "QuizActivity.onPause()" );
        super.onPause();
        // close the database in onPause
        if( geographyQuizData != null )
            geographyQuizData.close();
    }
}
