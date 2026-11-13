package com.campus.lostfound;
import android.os.Bundle; import androidx.appcompat.app.AppCompatActivity; import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle b){
        super.onCreate(b); Db.init(this); setContentView(R.layout.activity_main);
        BottomNavigationView n=findViewById(R.id.nav);
        n.setOnItemSelectedListener(i->{
            int id=i.getItemId();
            Fragment f=id==R.id.feed?new FeedFragment():id==R.id.post?new PostFragment():new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,f).commit(); return true;
        });
        if(b==null) n.setSelectedItemId(R.id.feed);
    }
}
