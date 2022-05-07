package com.example.realm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.realm.adapter.RecyclerViewAdapter;
import com.example.realm.manager.DataHelper;
import com.example.realm.helper.NewTaskDialog;
import com.example.realm.R;
import com.example.realm.model.TaskDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements NewTaskDialog.NewTaskListener {

    private Realm realm;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews(){


        realm = Realm.getDefaultInstance();
        recyclerView = findViewById(R.id.my_recyclerView);
        floatingActionButton = findViewById(R.id.my_fab);
        DialogFragment addTaskDialog = new NewTaskDialog();

        setUpRecyclerView();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog.show(getSupportFragmentManager(), "New Task");
            }
        });
    }

    @Override
    public void onAddTask(String task) {
        SecureRandom secureRandom = new SecureRandom();
        int taskId = secureRandom.nextInt(10000);

        DataHelper.newTask(realm,taskId,task);
    }

    @Override
    public void onCancel(DialogFragment dialogFragment) {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
    }

    private void setUpRecyclerView(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(realm.where(TaskDB.class).findAll());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private class TouchHelperCallback extends ItemTouchHelper.SimpleCallback{

        TouchHelperCallback(){
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped( RecyclerView.ViewHolder viewHolder, int direction) {
            DataHelper.deleteTask(realm, viewHolder.getItemId());
        }
    }
}