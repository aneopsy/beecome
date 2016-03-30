package io.aneopsy.theis_p.beecome.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.BindString;
import io.aneopsy.theis_p.beecome.R;
import jp.wasabeef.picasso.transformations.MaskTransformation;

public class BaseDrawerActivity extends BaseActivity {
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.vNavigation)
    NavigationView vNavigation;
    @BindDimen(R.dimen.global_menu_avatar_size)
    int avatarSize;
    @BindString(R.string.user_profile_photo)
    String profilePhoto;
    private ImageView ivMenuUserProfilePhoto;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.activity_drawer);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        bindViews();
        setupHeader();
    }
    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
            vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    if (menuItem.isChecked()) menuItem.setChecked(false);
                    else menuItem.setChecked(true);
                    drawerLayout.closeDrawers();
                        int[] startingLocation = new int[2];
                        switch (menuItem.getItemId()) {
                            case R.id.menu_suivi:
                                getWindow().findViewById(android.R.id.content).getLocationOnScreen(startingLocation);
                                startingLocation[0] += getWindow().findViewById(android.R.id.content).getWidth() / 2;
                                UserProfileActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.menu_calendar:
                                getWindow().findViewById(android.R.id.content).getLocationOnScreen(startingLocation);
                                startingLocation[0] += getWindow().findViewById(android.R.id.content).getWidth() / 2;
                                CalendarActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.menu_defis:
                                getWindow().findViewById(android.R.id.content).getLocationOnScreen(startingLocation);
                                startingLocation[0] += getWindow().findViewById(android.R.id.content).getWidth() / 2;
                                DefisActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.menu_forum:
                                getWindow().findViewById(android.R.id.content).getLocationOnScreen(startingLocation);
                                startingLocation[0] += getWindow().findViewById(android.R.id.content).getWidth() / 2;
                                ConseilActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.menu_frigo:
                                getWindow().findViewById(android.R.id.content).getLocationOnScreen(startingLocation);
                                startingLocation[0] += getWindow().findViewById(android.R.id.content).getWidth() / 2;
                                FrigoActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.menu_points:
                                getWindow().findViewById(android.R.id.content).getLocationOnScreen(startingLocation);
                                startingLocation[0] += getWindow().findViewById(android.R.id.content).getWidth() / 2;
                                PointsActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.menu_about:
                                final Intent intent = new Intent(BaseDrawerActivity.this, AboutActivity.class);
                                getWindow().findViewById(android.R.id.content).getLocationOnScreen(startingLocation);
                                intent.putExtra(AboutActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.menu_web:
                                goToUrl("http://beecome.xyz/");
                                return true;
                            case R.id.menu_contact:
                                goToUrl("http://beecome.xyz/index.php/contact");
                                return true;
                        default:
                            Toast.makeText(getApplicationContext(),"En cours de construction",Toast.LENGTH_SHORT).show();
                            return true;
                    }
                }
            });
        }
    }
    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
    private void setupHeader() {
        View headerView = vNavigation.getHeaderView(0);
        ivMenuUserProfilePhoto = (ImageView) headerView.findViewById(R.id.ivMenuUserProfilePhoto);
        headerView.findViewById(R.id.vGlobalMenuHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGlobalMenuHeaderClick(v);
            }
        });

        Picasso.with(this)
                .load(R.drawable.karl_profil)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform((new MaskTransformation(this, R.drawable.hexamask)))
                .into(ivMenuUserProfilePhoto);
    }
    public void onGlobalMenuHeaderClick(final View v) {
        drawerLayout.closeDrawer(Gravity.LEFT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                startingLocation[0] += v.getWidth() / 2;
                UserProfileActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                overridePendingTransition(0, 0);
            }
        }, 200);
    }
}
