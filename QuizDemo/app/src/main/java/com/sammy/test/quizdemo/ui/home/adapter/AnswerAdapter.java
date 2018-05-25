package com.sammy.test.quizdemo.ui.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sammy.test.quizdemo.R;
import com.sammy.test.quizdemo.model.Question;
import com.sammy.test.quizdemo.ui.home.HomeFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by sng
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private List<Question.Answer> mAnswerList;
    private HomeFragment.HomeItemListener mListener;
    private Context mContext;

    public AnswerAdapter(@NonNull Context context, @NonNull List<Question.Answer> answerList, @Nullable HomeFragment.HomeItemListener listener) {
        this.mAnswerList = answerList;
        mListener = listener;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mAnswerList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mAnswerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        ImageView mImage;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(Question.Answer answer, final HomeFragment.HomeItemListener listener) {

            Glide.with(mContext)
                    .load(answer.getImageUrl())
                    .into(mImage);

            if(listener != null) {
                itemView.setOnClickListener((View v) -> {
                    listener.onRowClick(answer);
                });
            }
        }
    }
}