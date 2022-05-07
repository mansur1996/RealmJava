package com.example.realm.helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.realm.R;

public class NewTaskDialog extends DialogFragment {

    NewTaskListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (NewTaskListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + "must implement NewTaskListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.new_task_dialog, null);
        EditText myTaskTxt = dialogLayout.findViewById(R.id.txt_dialog_task);

        builder.setView(dialogLayout)
                .setPositiveButton("Add task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = myTaskTxt.getText().toString();
                        mListener.onAddTask(task);
                    }
                })
                .setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onCancel(NewTaskDialog.this);
                        NewTaskDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public interface NewTaskListener{
        void onAddTask(String task);
        void onCancel(DialogFragment dialogFragment);
    }
}
