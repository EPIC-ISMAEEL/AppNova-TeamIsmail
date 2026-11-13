package com.campus.lostfound;
import android.content.Intent; import android.net.Uri; import android.os.Bundle; import android.text.*; import android.view.*; import android.widget.*;
import androidx.fragment.app.Fragment; import com.google.android.material.chip.ChipGroup; import com.google.android.material.dialog.MaterialAlertDialogBuilder; import org.json.*;
public class FeedFragment extends Fragment {
    LayoutInflater in; View v; LinearLayout l; ChipGroup g; EditText q; JSONArray a; String me;
    public View onCreateView(LayoutInflater inf, ViewGroup c, Bundle b){
        in=inf; v=in.inflate(R.layout.fragment_feed,c,false); l=v.findViewById(R.id.list); g=v.findViewById(R.id.cats); q=v.findViewById(R.id.q);
        a=Db.arr("items"); JSONObject u=Db.me(); me=u.optString("email");
        Db.s(v,R.id.hi,"Hi, "+u.optString("name").split(" ")[0]+" 👋");
        Db.chips(in,g,true); g.setOnCheckedStateChangeListener((x,ids)->render());
        q.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s,int i,int j,int k){ render(); }
            public void beforeTextChanged(CharSequence s,int i,int j,int k){}
            public void afterTextChanged(Editable e){}
        });
        render(); return v;
    }
    void render(){
        l.removeAllViews(); String cat=Db.sel(g), s=q.getText().toString().toLowerCase().trim(); int n=0;
        for(int k=a.length()-1;k>=0;k--){
            JSONObject o=a.optJSONObject(k); String ct=o.optString("cat","Other");
            String hay=(o.optString("title")+o.optString("desc")+o.optString("place")).toLowerCase();
            if(!(cat.equals("All")||cat.equals(ct))||!hay.contains(s)) continue; n++;
            View r=in.inflate(R.layout.item,l,false);
            String ph=o.optString("phone"), by=o.optString("by"), pl=o.optString("place"), d=o.optString("desc");
            double la=o.optDouble("lat"), ln=o.optDouble("lng");
            Db.s(r,R.id.ic,Db.emoji(ct)); Db.s(r,R.id.t,o.optString("title")); Db.s(r,R.id.sub,ct+" · 🕒 "+Db.fmt(o.optLong("time")));
            Db.s(r,R.id.d,d); if(d.isEmpty()) r.findViewById(R.id.d).setVisibility(View.GONE);
            Db.s(r,R.id.loc,"📍 "+(pl.isEmpty()?"":pl+" · ")+String.format("%.4f, %.4f",la,ln));
            Db.s(r,R.id.by,"👤 "+by);
            View cb=r.findViewById(R.id.c);
            if(o.optString("email").equals(me)) cb.setVisibility(View.GONE);
            cb.setOnClickListener(x->new MaterialAlertDialogBuilder(requireContext()).setTitle("Contact "+by)
                .setItems(new String[]{"Call","Message"},(dl,w)->startActivity(new Intent(w==0?Intent.ACTION_DIAL:Intent.ACTION_SENDTO,Uri.parse((w==0?"tel:":"smsto:")+ph)))).show());
            r.findViewById(R.id.m).setOnClickListener(x->startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("geo:"+la+","+ln+"?q="+la+","+ln+"(Found+here)"))));
            l.addView(r);
        }
        v.findViewById(R.id.empty).setVisibility(n==0?View.VISIBLE:View.GONE);
    }
}
