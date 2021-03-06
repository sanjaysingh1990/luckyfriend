package com.init.luckyfriend.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.init.luckyfriend.R;


import com.init.luckyfriend.activity.AppTour.AppTourSlides;
import com.init.luckyfriend.activity.DATA.WallDataBean;
import com.init.luckyfriend.activity.EditProfile.MainUserProfile;
import com.init.luckyfriend.activity.ExtendedProfile.FullExtendedProfile;
import com.init.luckyfriend.activity.ExtendedProfile.FullProfile;
import com.init.luckyfriend.activity.FriendSearch.FriendsSearchFragment;
import com.init.luckyfriend.activity.HomeFragment.HomeFragment;
import com.init.luckyfriend.activity.LikesFragment.LikesTabFragment;
import com.init.luckyfriend.activity.Messages.MessageTabFragment;
import com.init.luckyfriend.activity.MyPost.PostDataBean;
import com.init.luckyfriend.activity.MyPost.PostFragment;
import com.init.luckyfriend.activity.NearByTinder.Nearby;
import com.init.luckyfriend.activity.Notification.NotificationFragment;
import com.init.luckyfriend.activity.PhotosFragment.PhotosFragment;
import com.init.luckyfriend.activity.Profile.Profile;
import com.init.luckyfriend.activity.Visitors.VisitorFragment;
import com.init.luckyfriend.activity.WallSearch.FavouritesFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    RecyclerView recyclerView;
    TextView toolbartitle;
    TextView activities,favourites,photos,likes,friends,nearby,help,visitors,messages,settings,matches,myposts,logout;
    TextView notification,search,bottommessages,bottomneraby,bottommatches;
    public static LinearLayout bottomLayout;
    CircleImageView profileimage;
    public static TextView username;
    Typeface customfont,customfont1;
    ImageButton toolbaricon,toolbarplus;
    SharedPreferences pref;
    String name,gender;
    Integer type;
    FrameLayout menu_layout;
    ImageButton status;
    ProgressDialog prog;
     Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbartitle = (TextView) findViewById(R.id.toolbar_title);
        toolbaricon = (ImageButton) findViewById(R.id.toolbar_icon);
        toolbarplus = (ImageButton) findViewById(R.id.toolbar_plus);

        status = (ImageButton) findViewById(R.id.update_status);

//        prog=new ProgressDialog(this);
        //      prog.setMessage("Updating status...");

        toolbaricon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(MainActivity.this, toolbaricon);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(MainActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent filter=new Intent(getApplicationContext(),Filter.class);
                        startActivityForResult(filter, 10);

                        return true;
                    }
                });

                popup.show();//showing popup menu


                //closing the setOnClickListener method

               /* Context wrapper = new ContextThemeWrapper(getApplicationContext(), R.style.PopupMenu);
                PopupMenu popupMenu = new PopupMenu(wrapper, view);
                popupMenu.setOnMenuItemClickListener(new MenuClick());
                popupMenu.inflate(R.menu.action_menu);
                popupMenu.show();
*/

    /*             Intent filter=new Intent(getApplicationContext(),Filter.class);
                startActivityForResult(filter, 10);
      */
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_update_status);
                dialog.setTitle("New status....");

                final EditText status=(EditText)dialog.findViewById(R.id.writestatus);

                Button dialogButton = (Button) dialog.findViewById(R.id.ok);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(status.getText().toString().length()==0){
                            Toast.makeText(getApplicationContext(),"Please write the status",Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            UpdateStatus(status.getText().toString());

                        }
                        dialog.dismiss();


                    }
                });


                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                // if button is clicked, close the custom dialog
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        customfont=Typeface.createFromAsset(getAssets(),"NeoSansPro-Regular.ttf");

        customfont1=Typeface.createFromAsset(getAssets(), "SF-UI-Display-Regular.ttf");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbartitle.setText("Lucky Friend");
        toolbartitle.setTypeface(customfont);
        toolbartitle.setTextColor(Color.parseColor("#2f6fff"));

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container_body,new HomeFragment()).commit();
        mNavigationView = (NavigationView) findViewById(R.id.stuff) ;
        mNavigationView.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        bottomLayout=(LinearLayout)findViewById(R.id.bottomlayout);
        activities=(TextView)findViewById(R.id.activities);
        favourites=(TextView)findViewById(R.id.favourites);
        photos=(TextView)findViewById(R.id.photos);
        likes=(TextView)findViewById(R.id.likes);
        friends=(TextView)findViewById(R.id.friends);
        nearby=(TextView)findViewById(R.id.peoplenearby);
        help=(TextView)findViewById(R.id.help);
        visitors=(TextView)findViewById(R.id.visitors);
        notification=(TextView)findViewById(R.id.notification);
        search=(TextView)findViewById(R.id.search);
        messages=(TextView)findViewById(R.id.messages);
        bottommessages=(TextView)findViewById(R.id.messagesbottom);
        bottomneraby=(TextView)findViewById(R.id.nearby);
        bottommatches=(TextView)findViewById(R.id.matchesbottom);
        settings=(TextView)findViewById(R.id.settings);
        matches=(TextView)findViewById(R.id.matches);
        username=(TextView)findViewById(R.id.username);
        profileimage=(CircleImageView)findViewById(R.id.ivMenuUserProfilePhoto);
        myposts=(TextView)findViewById(R.id.myposts);
        username.setTypeface(customfont1);
        menu_layout=(FrameLayout)findViewById(R.id.menu_layout);
        logout=(TextView)findViewById(R.id.logout);


      type=getIntent().getIntExtra("typeLogin",1);
//        Toast.makeText(MainActivity.this, "type " + type, Toast.LENGTH_SHORT).show();

       if(type==1){
           name=Singleton.pref.getString("uname","");
           username.setText(name);
           String profilepic=Singleton.pref.getString("uimage","");
            gender=Singleton.pref.getString("ugender","");

           if (profilepic == "") {
               profileimage.setImageResource(R.drawable.iiiii);
           } else
               Singleton.imageLoader.displayImage(profilepic, profileimage, Singleton.defaultOptions);

           if(gender.compareToIgnoreCase("Male")==0)
           {
               menu_layout.setBackgroundColor(Color.parseColor("#2f6fff"));
               if (Build.VERSION.SDK_INT >= 21) {
                   Window window = getWindow();
                   window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                   window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                   window.setStatusBarColor(getResources().getColor(R.color.textColorPrimary));
               }


           }

           else {
               menu_layout.setBackgroundColor(Color.parseColor("#f63e65"));
               if (Build.VERSION.SDK_INT >= 21) {
                   Window window = getWindow();
                   window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                   window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                   window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
               }
           }

       }
        if(type==2){
            name=Singleton.pref.getString("uname","");
            username.setText(name);
            String profilepic=Singleton.pref.getString("uimage","");
            // Singleton.imageLoader.displayImage(profilepic, profileimage, Singleton.defaultOptions);
            if (profilepic == "") {
                profileimage.setImageResource(R.drawable.iiiii);
            } else
                Singleton.imageLoader.displayImage(profilepic, profileimage, Singleton.defaultOptions);


        }


        if(type==3){
            name=Singleton.pref.getString("uname","");
            username.setText(name);
            String profilepic=Singleton.pref.getString("uimage","");
          // Singleton.imageLoader.displayImage(profilepic, profileimage, Singleton.defaultOptions);
            if (profilepic == "") {
                profileimage.setImageResource(R.drawable.iiiii);
            } else
                Singleton.imageLoader.displayImage(profilepic, profileimage, Singleton.defaultOptions);


        }


        if(type == 4) {
              name=Singleton.pref.getString("uname","");
              username.setText(name);
                gender=Singleton.pref.getString("ugender","");
          String profilepic=Singleton.pref.getString("uimage","");
          Singleton.imageLoader.displayImage(profilepic,profileimage,Singleton.defaultOptions);

          if(gender.compareToIgnoreCase("Male")==0)
          {
              menu_layout.setBackgroundColor(Color.parseColor("#2f6fff"));
              if (Build.VERSION.SDK_INT >= 21) {
                  Window window = getWindow();
                  window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                  window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                  window.setStatusBarColor(getResources().getColor(R.color.textColorPrimary));
              }


          }

          else {
              menu_layout.setBackgroundColor(Color.parseColor("#f63e65"));
              if (Build.VERSION.SDK_INT >= 21) {
                  Window window = getWindow();
                  window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                  window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                  window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
              }
          }
      }
          if(type == 5) {
              Singleton.pref.getString("uname", "");
              username.setText(Singleton.pref.getString("uname", ""));

              gender = Singleton.pref.getString("ugender","");
              String profilepic=Singleton.pref.getString("uimage","");

              if(profilepic==""){
                  profileimage.setImageResource(R.drawable.iiiii);
              }
              else
              Singleton.imageLoader.displayImage(profilepic,profileimage,Singleton.defaultOptions);

              if(gender.compareToIgnoreCase("Male")==0)
              {
                  menu_layout.setBackgroundColor(Color.parseColor("#2f6fff"));
                  if (Build.VERSION.SDK_INT >= 21) {
                      Window window = getWindow();
                      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                      window.setStatusBarColor(getResources().getColor(R.color.textColorPrimary));
                  }


              }

              else {
                  menu_layout.setBackgroundColor(Color.parseColor("#f63e65"));
                  if (Build.VERSION.SDK_INT >= 21) {
                      Window window = getWindow();
                      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                      window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                  }
              }
          }

        if(type == 6) {
            Singleton.pref.getString("uname", "");
            username.setText(Singleton.pref.getString("uname", ""));

            gender = Singleton.pref.getString("ugender","");
            String profilepic=Singleton.pref.getString("uimage","");

            if(profilepic==""){
                profileimage.setImageResource(R.drawable.iiiii);
            }
            else
                Singleton.imageLoader.displayImage(profilepic,profileimage,Singleton.defaultOptions);

            if(gender.compareToIgnoreCase("Male")==0)
            {
                menu_layout.setBackgroundColor(Color.parseColor("#2f6fff"));
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(getResources().getColor(R.color.textColorPrimary));
                }


            }

            else {
                menu_layout.setBackgroundColor(Color.parseColor("#f63e65"));
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


          SharedPreferences.Editor editor= Singleton.pref.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getApplicationContext(),"Logout successful..",Toast.LENGTH_LONG).show();
                Intent main=new Intent(MainActivity.this, AppTourSlides.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(main);
                finish();


            }
        });

        myposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //bottomLayout.setVisibility(View.VISIBLE);

                //toolbaricon.setImageResource(R.drawable.toolbar_plus);
                toolbarplus.setVisibility(View.VISIBLE);
                toolbaricon.setVisibility(View.GONE);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, new PostFragment()).commit();

                toolbarplus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent newpost=new Intent(getApplicationContext(),NewPost.class);
                        startActivityForResult(newpost, 11);

                    }
                });
            }
        });




        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                //bottomLayout.setVisibility(View.INVISIBLE);
                //toolbartitle.setText("name");
                //toolbaricon.setImageResource(R.drawable.profile_comment_icon);
               // mFragmentManager = getSupportFragmentManager();
                //mFragmentTransaction = mFragmentManager.beginTransaction();
                //mFragmentTransaction.replace(R.id.container_body, new Mai).commit();
                Intent mainuserprofile=new Intent(getApplicationContext(), MainUserProfile.class);
                startActivityForResult(mainuserprofile,13);


            }
        });

        bottommatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottommatches.setBackgroundColor(Color.parseColor("#2f6fff"));
                bottommatches.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_matches_white_icon, 0, 0);
                bottommatches.setTextColor(Color.parseColor("#ffffff"));

                bottomneraby.setBackgroundColor(Color.parseColor("#ffffff"));
                bottomneraby.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_nearby_grey_icon, 0, 0);
                bottomneraby.setTextColor(Color.parseColor("#555e71"));


                search.setBackgroundColor(Color.parseColor("#ffffff"));
                search.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_serach_icon, 0, 0);
                search.setTextColor(Color.parseColor("#555e71"));


                bottommessages.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommessages.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_message_grey_icon, 0, 0);
                bottommessages.setTextColor(Color.parseColor("#555e71"));


                notification.setBackgroundColor(Color.parseColor("#ffffff"));
                notification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_notification_grey_icon, 0, 0);
                notification.setTextColor(Color.parseColor("#555e71"));


                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, new MatchesFragment()).commit();
                toolbarplus.setVisibility(View.GONE);
                toolbaricon.setVisibility(View.VISIBLE);



            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notification.setBackgroundColor(Color.parseColor("#2f6fff"));
                notification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_notification_white_icon, 0, 0);
                notification.setTextColor(Color.parseColor("#ffffff"));

                bottomneraby.setBackgroundColor(Color.parseColor("#ffffff"));
                bottomneraby.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_nearby_grey_icon, 0, 0);
                bottomneraby.setTextColor(Color.parseColor("#555e71"));


                search.setBackgroundColor(Color.parseColor("#ffffff"));
                search.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_serach_icon, 0, 0);
                search.setTextColor(Color.parseColor("#555e71"));


                bottommessages.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommessages.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_message_grey_icon, 0, 0);
                bottommessages.setTextColor(Color.parseColor("#555e71"));


                bottommatches.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommatches.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_matches_grey_icon, 0, 0);
                bottommatches.setTextColor(Color.parseColor("#555e71"));


                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, new NotificationFragment()).commit();

                toolbarplus.setVisibility(View.GONE);
                toolbaricon.setVisibility(View.VISIBLE);

            }
        });

        bottomneraby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomneraby.setBackgroundColor(Color.parseColor("#2f6fff"));
                bottomneraby.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_nearby_white_icon, 0, 0);
                bottomneraby.setTextColor(Color.parseColor("#ffffff"));

                bottommessages.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommessages.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_message_grey_icon, 0, 0);
                bottommessages.setTextColor(Color.parseColor("#555e71"));


                search.setBackgroundColor(Color.parseColor("#ffffff"));
                search.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_serach_icon, 0, 0);
                search.setTextColor(Color.parseColor("#555e71"));


                notification.setBackgroundColor(Color.parseColor("#ffffff"));
                notification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_notification_grey_icon, 0, 0);
                notification.setTextColor(Color.parseColor("#555e71"));


                bottommatches.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommatches.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_matches_grey_icon, 0, 0);
                bottommatches.setTextColor(Color.parseColor("#555e71"));

                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, new Nearby()).commit();
                search.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_serach_icon, 0, 0);

                toolbarplus.setVisibility(View.GONE);
                toolbaricon.setVisibility(View.VISIBLE);

            }
        });


        bottommessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottommessages.setBackgroundColor(Color.parseColor("#2f6fff"));
                bottommessages.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_message_white_icon, 0, 0);
                bottommessages.setTextColor(Color.parseColor("#ffffff"));

                search.setBackgroundColor(Color.parseColor("#ffffff"));
                search.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_serach_icon, 0, 0);
                search.setTextColor(Color.parseColor("#555e71"));


                bottomneraby.setBackgroundColor(Color.parseColor("#ffffff"));
                bottomneraby.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_nearby_grey_icon, 0, 0);
                bottomneraby.setTextColor(Color.parseColor("#555e71"));


                notification.setBackgroundColor(Color.parseColor("#ffffff"));
                notification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_notification_grey_icon, 0, 0);
                notification.setTextColor(Color.parseColor("#555e71"));


                bottommatches.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommatches.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_matches_grey_icon, 0, 0);
                bottommatches.setTextColor(Color.parseColor("#555e71"));


                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, new MessageTabFragment()).commit();
                search.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_serach_icon, 0, 0);
                toolbarplus.setVisibility(View.GONE);
                toolbaricon.setVisibility(View.VISIBLE);


            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setBackgroundColor(Color.parseColor("#2f6fff"));
                search.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_search_icon_white, 0, 0);
                search.setTextColor(Color.parseColor("#ffffff"));

                notification.setBackgroundColor(Color.parseColor("#ffffff"));
                notification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_notification_grey_icon, 0, 0);
                notification.setTextColor(Color.parseColor("#555e71"));

                bottommessages.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommessages.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_message_grey_icon, 0, 0);
                bottommatches.setTextColor(Color.parseColor("#555e71"));

                bottomneraby.setBackgroundColor(Color.parseColor("#ffffff"));
                bottomneraby.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_nearby_grey_icon, 0, 0);
                bottomneraby.setTextColor(Color.parseColor("#555e71"));

                bottommatches.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommatches.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_matches_grey_icon, 0, 0);
                bottommatches.setTextColor(Color.parseColor("#555e71"));

                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, new HomeFragment()).commit();

                toolbarplus.setVisibility(View.GONE);
                toolbaricon.setVisibility(View.VISIBLE);

            }
        });

        activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                MainActivity.bottomLayout.setVisibility(View.VISIBLE);

                search.setBackgroundColor(Color.parseColor("#2f6fff"));
                search.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_search_icon_white, 0, 0);
                search.setTextColor(Color.parseColor("#ffffff"));

                bottommessages.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommessages.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_message_icon, 0, 0);
                bottommessages.setTextColor(Color.parseColor("#555e71"));

                notification.setBackgroundColor(Color.parseColor("#ffffff"));
                notification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_notification_grey_icon, 0, 0);
                notification.setTextColor(Color.parseColor("#555e71"));


                bottomneraby.setBackgroundColor(Color.parseColor("#ffffff"));
                bottomneraby.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_nearby_grey_icon, 0, 0);
                bottomneraby.setTextColor(Color.parseColor("#555e71"));


                bottommatches.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommatches.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_matches_grey_icon, 0, 0);
                bottommatches.setTextColor(Color.parseColor("#555e71"));





                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, new HomeFragment()).commit();

                toolbarplus.setVisibility(View.GONE);
                toolbaricon.setVisibility(View.VISIBLE);

            }
        });
        favourites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    MainActivity.bottomLayout.setVisibility(View.VISIBLE);
                    mFragmentManager = getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.container_body, new FavouritesFragment()).commit();

                    toolbarplus.setVisibility(View.GONE);
                    toolbaricon.setVisibility(View.VISIBLE);

                }
            });

       photos.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
//        toolbartitle.setText("Photos");
      //  bottomLayout.setVisibility(View.GONE);
        MainActivity.bottomLayout.setVisibility(View.VISIBLE);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container_body, new PhotosFragment()).commit();

        toolbarplus.setVisibility(View.GONE);
        toolbaricon.setVisibility(View.VISIBLE);

    }
});

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                MainActivity.bottomLayout.setVisibility(View.VISIBLE);
                bottommessages.setBackgroundColor(Color.parseColor("#2f6fff"));
                bottommessages.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_message_white_icon, 0, 0);
                bottommessages.setTextColor(Color.parseColor("#ffffff"));

                search.setBackgroundColor(Color.parseColor("#ffffff"));
                search.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_serach_icon, 0, 0);
                search.setTextColor(Color.parseColor("#555e71"));

                notification.setBackgroundColor(Color.parseColor("#ffffff"));
                notification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_notification_grey_icon, 0, 0);
                notification.setTextColor(Color.parseColor("#555e71"));


                bottomneraby.setBackgroundColor(Color.parseColor("#ffffff"));
                bottomneraby.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_nearby_grey_icon, 0, 0);
                bottomneraby.setTextColor(Color.parseColor("#555e71"));


                bottommatches.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommatches.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_matches_grey_icon, 0, 0);
                bottommatches.setTextColor(Color.parseColor("#555e71"));



                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, new MessageTabFragment()).commit();
                toolbarplus.setVisibility(View.GONE);
                toolbaricon.setVisibility(View.VISIBLE);

            }
        });


        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                MainActivity.bottomLayout.setVisibility(View.VISIBLE);
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, new LikesTabFragment()).commit();
                toolbarplus.setVisibility(View.GONE);
                toolbaricon.setVisibility(View.VISIBLE);

            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                MainActivity.bottomLayout.setVisibility(View.VISIBLE);
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, new FriendsSearchFragment()).commit();
                toolbarplus.setVisibility(View.GONE);
                toolbaricon.setVisibility(View.VISIBLE);

            }
        });

        nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                MainActivity.bottomLayout.setVisibility(View.VISIBLE);

                bottomneraby.setBackgroundColor(Color.parseColor("#2f6fff"));
                bottomneraby.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_nearby_white_icon, 0, 0);
                bottomneraby.setTextColor(Color.parseColor("#ffffff"));

                search.setBackgroundColor(Color.parseColor("#ffffff"));
                search.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_serach_icon, 0, 0);
                search.setTextColor(Color.parseColor("#555e71"));

                notification.setBackgroundColor(Color.parseColor("#ffffff"));
                notification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_notification_grey_icon, 0, 0);
                notification.setTextColor(Color.parseColor("#555e71"));


                bottommessages.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommessages.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_message_grey_icon, 0, 0);
                bottommessages.setTextColor(Color.parseColor("#555e71"));


                bottommatches.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommatches.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_matches_grey_icon, 0, 0);
                bottommatches.setTextColor(Color.parseColor("#555e71"));






                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, new Nearby()).commit();
                toolbarplus.setVisibility(View.GONE);
                toolbaricon.setVisibility(View.VISIBLE);

            }
        });

        visitors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                MainActivity.bottomLayout.setVisibility(View.VISIBLE);
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, new VisitorFragment()).commit();
                toolbarplus.setVisibility(View.GONE);
                toolbaricon.setVisibility(View.VISIBLE);

            }
        });


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent help=new Intent(getApplicationContext(),Help.class);
                startActivity(help);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent help=new Intent(getApplicationContext(),Settings.class);
                startActivity(help);
            }
        });

        matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                MainActivity.bottomLayout.setVisibility(View.VISIBLE);

                bottommatches.setBackgroundColor(Color.parseColor("#2f6fff"));
                bottommatches.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_matches_white_icon, 0, 0);
                bottommatches.setTextColor(Color.parseColor("#ffffff"));

                search.setBackgroundColor(Color.parseColor("#ffffff"));
                search.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_serach_icon, 0, 0);
                search.setTextColor(Color.parseColor("#555e71"));

                notification.setBackgroundColor(Color.parseColor("#ffffff"));
                notification.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_notification_grey_icon, 0, 0);
                notification.setTextColor(Color.parseColor("#555e71"));


                bottomneraby.setBackgroundColor(Color.parseColor("#ffffff"));
                bottomneraby.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_nearby_grey_icon, 0, 0);
                bottomneraby.setTextColor(Color.parseColor("#555e71"));


                bottommessages.setBackgroundColor(Color.parseColor("#ffffff"));
                bottommessages.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_message_grey_icon, 0, 0);
                bottommessages.setTextColor(Color.parseColor("#555e71"));






                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, new MatchesFragment()).commit();
                toolbarplus.setVisibility(View.GONE);
                toolbaricon.setVisibility(View.VISIBLE);


            }
        });



        final android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle=new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close);
        mNavigationView.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
    //    displayView(position);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView mImage) {
            this.bmImage = mImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mBitmap = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mBitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mBitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


    public void UpdateStatus(final String statusText){
//        prog.show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,getResources().getString(R.string.url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // prog.dismiss();
//            Log.e("error",error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rqid", 23+"");
                params.put("person_id",Singleton.pref.getString("person_id",""));
                params.put("status",statusText);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);


    }

    @Override
    public void onBackPressed() {
       this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10)
        {

                String items=  data.getStringExtra("data");

                if(items!=null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("data", items);
                    SearchFragment hme = new SearchFragment();
                    hme.setArguments(bundle);
                    mFragmentManager = getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.container_body, hme).commit();
                }
            else{

                }
                //Toast.makeText(this,requestCode+""+items,Toast.LENGTH_LONG).show();
          }

      else  if(requestCode==11)
        {
            String items=  data.getStringExtra("data");

            if(items!=null) {
                Bundle bundle = new Bundle();
                bundle.putString("data", items);
                PostFragment hme = new PostFragment();
                hme.setArguments(bundle);
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, hme).commit();
            }
           else
            {

            }
            }

        else  if(requestCode==13)
        {
            String items=  data.getStringExtra("image");

            if(items!=null) {
//                Toast.makeText(getApplicationContext(),items,Toast.LENGTH_LONG).show();
               Singleton.imageLoader.displayImage(items,profileimage,Singleton.defaultOptions);

                          }
            else
            {

            }
        }
        if(requestCode==14)
        {

            String items=  data.getStringExtra("data");

            if(items!=null) {
                Bundle bundle = new Bundle();
                bundle.putString("data", items);
                AdvancedSearchFragment hme = new AdvancedSearchFragment();
                hme.setArguments(bundle);
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.container_body, hme).commit();
            }
            else{

            }
            //Toast.makeText(this,requestCode+""+items,Toast.LENGTH_LONG).show();
        }


    }

    private class MenuClick implements PopupMenu.OnMenuItemClickListener {
        int pos;

        public MenuClick(int pos) {
            this.pos = pos;
        }

        public MenuClick(){

        }

        @Override
        public boolean onMenuItemClick(final MenuItem item) {
            switch (item.getItemId()) {
                case R.id.user_edit:
                    //Toast.makeText(context, "Edit Clicked" + pos, Toast.LENGTH_SHORT).show();
                    break;



                case R.id.user_delete:
//                    Toast.makeText(context, "Delete Clicked" + pos, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
                    alertDialogBuilder.setMessage("Do you really want to delete the post ?");

                    alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // Toast.makeText(context, "You clicked yes button", Toast.LENGTH_LONG).show();


                        }
                    });

                                        break;
            }
            return false;
        }

    }

}
