package com.campus.lostfound;
import android.content.Intent; import android.os.Bundle; import android.view.View; import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
public class AuthActivity extends AppCompatActivity {
    boolean signup;
    protected void onCreate(Bundle b){
        super.onCreate(b); Db.init(this);
        if(Db.me()!=null){ open(); return; }
        setContentView(R.layout.activity_auth);
        View root=findViewById(android.R.id.content), nl=findViewById(R.id.nameL), pl=findViewById(R.id.phoneL);
        Button go=findViewById(R.id.go), sw=findViewById(R.id.sw);
        sw.setOnClickListener(v->{
            signup=!signup; int vis=signup?View.VISIBLE:View.GONE; nl.setVisibility(vis); pl.setVisibility(vis);
            go.setText(signup?"Sign up":"Log in"); Db.s(root,R.id.ftitle,signup?"Create account":"Welcome back"); sw.setText(signup?"Have an account? Log in":"New here? Sign up");
        });
        go.setOnClickListener(v->{
            String n=Db.t(root,R.id.name), ph=Db.t(root,R.id.phone), e=Db.t(root,R.id.email).toLowerCase(), pw=Db.t(root,R.id.pw);
            if(e.isEmpty()||pw.length()<6||signup&&(n.isEmpty()||ph.isEmpty())){ toast("Fill all fields (password 6+ chars)"); return; }
            boolean ok=signup?Db.signup(n,e,ph,pw)&&Db.login(e,pw):Db.login(e,pw);
            if(ok) open(); else toast(signup?"Email already registered":"Wrong email or password");
        });
    }
    void toast(String s){ Toast.makeText(this,s,Toast.LENGTH_SHORT).show(); }
    void open(){ startActivity(new Intent(this,MainActivity.class)); finish(); }
}
