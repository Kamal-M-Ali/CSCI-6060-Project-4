package edu.uga.cs.geographyquiz;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.uga.cs.geographyquiz.pojo.Question;
import edu.uga.cs.geographyquiz.pojo.Quiz;


public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.QuestionHolder> {
    private static final String DEBUG_TAG = "QuestionRecyclerAdapter";
    private final List<Question> values;
    private Quiz quiz;

    /**
     * The class's constructor. Call to create a QuestionRecyclerAdapater object.
     *
     * @param questionList a list of Question objects
     * @param quiz a Quiz objects
     */
    public QuestionRecyclerAdapter(List<Question> questionList, Quiz quiz) {
        this.values = questionList;
        this.quiz = quiz;
    }

    /**
     * A ViewHolder class for the adapter to "hold" an item.
     */
    public static class QuestionHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView textView2;
        private RadioGroup radioGroup;
        private RadioButton radioButton1;
        private RadioButton radioButton2;
        private RadioButton radioButton3;
        private RadioGroup radioGroup2;
        private RadioButton radioButtonT;
        private RadioButton radioButtonF;
        private boolean q1, q2;

        /**
         * The QuestionHolder's constructor.
         * @param itemView
         */
        public QuestionHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            radioButton1 = itemView.findViewById(R.id.radioButton1);
            radioButton2 = itemView.findViewById(R.id.radioButton2);
            radioButton3 = itemView.findViewById(R.id.radioButton3);
            radioButtonT = itemView.findViewById(R.id.radioButtonT);
            radioButtonF = itemView.findViewById(R.id.radioButtonF);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            radioGroup2 = itemView.findViewById(R.id.radioGroup2);
            q1 = false; // for capital question
            q2 = false; // for size question
        }
    }

    /**
     * Call to create the ViewHolder.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return a QuestionHolder object
     */
    @NonNull
    @Override
    public QuestionRecyclerAdapter.QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_question, parent, false);
        return new QuestionRecyclerAdapter.QuestionHolder(view);
    }

    /**
     * This method fills in the values of a holder to show a Quiz result.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(QuestionHolder holder, int position) {
        Question question = values.get(position);
        Log.d(DEBUG_TAG, "onBindViewHolder: " + question);

        holder.textView.setText("Which one of the following is the capital city of " + question.getState() + "?");
        holder.textView2.setText("Is the capital city also the largest city in the state of " + question.getState() + "?");
        shuffleQuestions(holder, question);

        //listener to take in which answer the user chose
        holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = holder.radioGroup.findViewById(checkedId);

            if (selectedRadioButton.getText().equals(question.getCapitalCity())) {
                if (!holder.q1) {
                    quiz.setResult(quiz.getResult() + 1);
                    holder.q1 = true;
                }
            } else if (holder.q1) {
                quiz.setResult(quiz.getResult() - 1);
                holder.q1 = false;
            }
            Log.d(DEBUG_TAG, "Updated capital city answer: " + holder.q1 + ", w/capital of " + question.getCapitalCity());
        });

        //listener to take in which answer the user chose
        holder.radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            if ((checkedId == holder.radioButtonT.getId() && question.getSizeRank().equals(1))
                    || (checkedId == holder.radioButtonF.getId() && !question.getSizeRank().equals(1))) {
                if (!holder.q2) {
                    quiz.setResult(quiz.getResult() + 1);
                    holder.q2 = true;
                }
            } else if (holder.q2) {
                quiz.setResult(quiz.getResult() - 1);
                holder.q2 = false;
            }
            Log.d(DEBUG_TAG, "Updated size answer: " + holder.q2 + ", w/size of " + question.getSizeRank());
        });
    }

    /**
     * Helper method to present the questions to the user in a random order.
     * @param holder
     * @param question
     */
    private void shuffleQuestions(QuestionHolder holder, Question question) {
        if (question != null) {;
            //get the three cities for the question in an array
            String [] shuffle = { question.getCapitalCity(), question.getSecondCity(), question.getThirdCity()};
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
            holder.radioButton1.setText(rand[0]);
            holder.radioButton2.setText(rand[1]);
            holder.radioButton3.setText(rand[2]);
        }
    }

    /**
     * @return how many items are currently being held
     */
    @Override
    public int getItemCount() { return (values != null) ? values.size() : 0; }

}
