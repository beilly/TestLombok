
package com.iwgame.testlombok;

import java.io.File;
import java.util.List;

import org.xutils.DbManager;
import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ViewInject;

import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
    DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
    .setDbName("test")
    .setDbDir(new File("/sdcard"))
    .setDbVersion(1)
    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
        @Override
        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
            // TODO: ...
            // db.addColumn(...);
            // db.dropTable(...);
            // ...
        }
    });
    
    @ViewInject(R.id.txt)
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        DbManager db = x.getDb(daoConfig);
        LogUtil.e("onCreate hello");
        try {
            Student student = new Student(1, "hello");
            db.save(student);
            List<Student> students = db.selector(Student.class).findAll();
            String str = new Gson().toJson(students);
            LogUtil.e(str);
            tv.setText(str);
        } catch (DbException e) {
            e.printStackTrace();
            tv.setText(e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            DbManager db = x.getDb(daoConfig);
            try {
                List<Student> students = db.selector(Student.class).findAll();
                String str = new Gson().toJson(students);
                LogUtil.e(str);
                tv.setText(str);
            } catch (DbException e) {
                e.printStackTrace();
                tv.setText(e.getMessage());
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
