package edu.uga.cs.geographyquiz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uga.cs.geographyquiz.pojo.Quiz;

/**
 * An adapter class for the RecyclerView to show all past quizzes.
 */
public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizHolder> {
    private static final String DEBUG_TAG = "QuizRecyclerAdapter";
    private final List<Quiz> values;


    /**
     * The class's constructor. Call to create a QuizRecyclerAdapater object.
     * @param quizList a list of Quiz objects
     */
    public QuizRecyclerAdapter(List<Quiz> quizList) {
        this.values = quizList;
    }

    /**
     * A ViewHolder class for the adapter to "hold" an item.
     */
    public static class QuizHolder extends RecyclerView.ViewHolder {
        TextView quizNumber;
        TextView score;
        TextView datetime;

        /**
         * The QuizHolder's constructor.
         * @param itemView
         */
        public QuizHolder(View itemView) {
            super(itemView);
            quizNumber = itemView.findViewById(R.id.quizNumber);
            score = itemView.findViewById(R.id.score);
            datetime = itemView.findViewById(R.id.datetime);
        }
    }

    /**
     * Call to create the ViewHolder.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return a QuizHolder object
     */
    @NonNull
    @Override
    public QuizHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz, parent, false);
        return new QuizHolder(view);
    }

    /**
     * This method fills in the values of a holder to show a Quiz result.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(QuizHolder holder, int position) {
        Quiz quiz = values.get(position);
        Log.d(DEBUG_TAG, "onBindViewHolder: " + quiz);

        holder.quizNumber.setText("Quiz #" + quiz.getQuizId());
        holder.score.setText("Score: " + quiz.getResult() + " / 12");
        holder.datetime.setText("Date: " + quiz.getDate());
    }

    /**
     * @return how many items are currently being held
     */
    @Override
    public int getItemCount() { return (values != null) ? values.size() : 0; }
}
