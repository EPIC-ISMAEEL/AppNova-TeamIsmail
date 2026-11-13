package com.campus.lostfound;
import android.content.*; import android.view.View; import android.widget.TextView; import org.json.*;
import com.google.android.material.chip.*; import android.view.LayoutInflater; import java.text.SimpleDateFormat; import java.util.*;
public class Db {
    static SharedPreferences p;
    static void init(Context c){ if(p==null) p=c.getApplicationContext().getSharedPreferences("db",0); }
    static JSONArray arr(String k){ try{ return new JSONArray(p.getString(k,"[]")); }catch(Exception e){ return new JSONArray(); } }
    static JSONObject user(String e){ JSONArray a=arr("users"); for(int i=0;i<a.length();i++){ JSONObject u=a.optJSONObject(i); if(u.optString("email").equals(e)) return u; } return null; }
    static boolean signup(String n,String e,String ph,String pw){
        if(user(e)!=null) return false;
        try{ p.edit().putString("users",arr("users").put(new JSONObject().put("name",n).put("email",e).put("phone",ph).put("pw",pw)).toString()).apply(); }catch(JSONException x){}
        return true;
    }
    static boolean login(String e,String pw){ JSONObject u=user(e); if(u!=null&&u.optString("pw").equals(pw)){ p.edit().putString("me",e).apply(); return true; } return false; }
    static JSONObject me(){ return user(p.getString("me","")); }
    static void logout(){ p.edit().remove("me").apply(); }
    static void addItem(JSONObject o){ p.edit().putString("items",arr("items").put(o).toString()).apply(); }
    static String fmt(long t){ return new SimpleDateFormat("dd MMM, hh:mm a",Locale.getDefault()).format(new Date(t)); }
    static void s(View r,int id,String t){ ((TextView)r.findViewById(id)).setText(t); }
    static String t(View r,int id){ return ((TextView)r.findViewById(id)).getText().toString().trim(); }
    static final String[] CATS={"Calculator","Keys","ID Card","Phone","Other"};
    static String emoji(String c){ switch(c){ case "Calculator":return "🧮"; case "Keys":return "🔑"; case "ID Card":return "🪪"; case "Phone":return "📱"; default:return "📦"; } }
    static void chips(LayoutInflater in, ChipGroup g, boolean all){
        List<String> l=new ArrayList<>(); if(all) l.add("All"); l.addAll(Arrays.asList(CATS));
        for(String s:l){ Chip c=(Chip)in.inflate(R.layout.chip,g,false); c.setText(s); g.addView(c); if(s.equals(l.get(0))) c.setChecked(true); }
    }
    static String sel(ChipGroup g){ Chip c=g.findViewById(g.getCheckedChipId()); return c==null?"Other":c.getText().toString(); }
}
