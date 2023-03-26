package com.technopie.eyewise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technopie.eyewise.R;
import com.technopie.eyewise.model.Answer;
import com.technopie.eyewise.model.Question;
import com.technopie.eyewise.model.QuestionAnswer;

import java.util.List;

public class QuestionAnswerListAdapter extends RecyclerView.Adapter<QuestionAnswerListAdapter.ListViewHolder> {

    Context mContext;
    List<QuestionAnswer> questionsAnswer;

    public QuestionAnswerListAdapter(Context mContext, List<QuestionAnswer> questionsAnswer) {
        this.mContext = mContext;
        this.questionsAnswer = questionsAnswer;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.question_answer, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        QuestionAnswer questionAnswer = questionsAnswer.get(position);
        holder.question.setText(questionAnswer.getQuestion());
        holder.answer.setText(questionAnswer.getAns());
        holder.edit.setOnClickListener(v -> {
            Toast.makeText(mContext, "To Be Implemented Soon!!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return questionsAnswer.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        TextView question, answer;
        Button edit;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}
