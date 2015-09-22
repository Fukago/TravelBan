package com.example.apple.travelban.module.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.apple.travelban.R;
import com.example.apple.travelban.model.AccountModel;
import com.example.apple.travelban.model.RemoteFileModel;
import com.example.apple.travelban.model.bean.User;
import com.example.apple.travelban.module.ad.AdFragment;
import com.example.apple.travelban.module.train.TrainListFragment;
import com.example.apple.travelban.module.travel.CityClassFragment;
import com.example.apple.travelban.module.travel.HotelFragment;
import com.example.apple.travelban.module.user.LogInActivity;
import com.example.apple.travelban.module.user.MyCollectionActivity;
import com.example.apple.travelban.module.user.UserDataActivity;
import com.example.apple.travelban.utils.ActivityCollector;
import com.example.apple.travelban.utils.ImageProvider;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.kermit.exutils.utils.ExUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;

/**
 * Created by apple on 15/8/18.
 */
public class TravelBanActivity extends AppCompatActivity {
    private MaterialViewPager mViewPager;
    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private SimpleDraweeView mImage;
    private ActionBarDrawerToggle mDrawerToggle;
    private String userName;
    private String[] data = {"我的资料","我的收藏","我的设置", "关于我们","切换账号"};

    private List<Drawer> drawerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelban);

        BmobUpdateAgent.initAppVersion(this);
        ActivityCollector.addActivity(this);
        BmobUpdateAgent.update(this);
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {

            }
        });
        setData();
        User userInfo = BmobUser.getCurrentUser(TravelBanActivity.this, User.class);
        if (userInfo.getAccount()!=null) {
            userName=userInfo.getAccount();
        }

        mImageProvider = new ImageProvider(this);
        initView();
        Toast.makeText(TravelBanActivity.this, "欢迎，" + userName + "！", Toast.LENGTH_SHORT).show();
    }

    private void setData() {
        Intent intent = getIntent();
        userName = intent.getStringExtra("username");
    }

    private void initView() {
        setTitle("");
        mImage = (SimpleDraweeView) findViewById(R.id.userImage);
        if (!TextUtils.isEmpty(AccountModel.getInstance().getCurrentUser().getFace())){
            mImage.setImageURI(Uri.parse(AccountModel.getInstance().getCurrentUser().getFace()));
        }
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFace();
            }
        });
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        toolbar = mViewPager.getToolbar();

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        TextView username = (TextView) findViewById(R.id.username);
        username.setText(userName);
        Drawer data = new Drawer("我的资料", R.drawable.drawer_data);
        Drawer collection = new Drawer("我的收藏", R.drawable.drawer_collection);
        Drawer setting = new Drawer("我的设置", R.drawable.drawer_setting);
        Drawer about = new Drawer("关于我们", R.drawable.drawer_about);
        Drawer logout = new Drawer("切换账号", R.drawable.drawer_logout);
        drawerList.add(data);
        drawerList.add(collection);
        drawerList.add(setting);
        drawerList.add(about);
        drawerList.add(logout);

        DrawerAdapter adapter = new DrawerAdapter(TravelBanActivity.this,
                R.layout.drawer_listview_child, drawerList);
        ListView listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Drawer drawer = drawerList.get(position);
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(TravelBanActivity.this, UserDataActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(TravelBanActivity.this, MyCollectionActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Toast.makeText(TravelBanActivity.this, "亲～程序员君太懒了，Ban还在开发中，敬请期待！", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 3: {
                        Intent intent = new Intent(TravelBanActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                    }

                    default: {
                        Intent intent = new Intent(TravelBanActivity.this, LogInActivity.class);
                        startActivity(intent);
                        break;
                    }
                }

            }
        });

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        return AdFragment.newInstance();
                    case 1:
                        return CityClassFragment.newInstance();
                    case 2:
                        return HotelFragment.newInstance();

                    default:
                        return TrainListFragment.newInstance();
                }
            }


            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "发现";
                    case 1:
                        return "分类";
                    case 2:
                        return "酒店服务";
                    case 3:
                        return "订票";
                }
                return "";
            }
        });
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");

                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://sc.jb51.net/uploads/allimg/140207/8-14020H10215V9.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://tupian.aladd.net/2014/7/1/45.jpg");

                }

                return null;
            }
        });
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.logo_white);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "亲，想关注我们了吗？", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "亲～再按一次退出程序哦！", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityCollector.finishAll();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageProvider.onActivityResult(requestCode, resultCode, data);
    }

    class DrawerAdapter extends ArrayAdapter<Drawer> {

        private int resourceId;

        public DrawerAdapter(Context context, int textViewResourceId, List<Drawer> objects) {

            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View converView, ViewGroup parent) {
            Drawer drawer = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            TextView drawerItemName = (TextView) view.findViewById(R.id.drawer_text);
            ImageView drawerItemImage = (ImageView) view.findViewById(R.id.drawer_image);
            drawerItemName.setText(drawer.drawerItemName);
            drawerItemImage.setImageResource(drawer.getImage());
            return view;
        }
    }

    class Drawer {

        String drawerItemName;

        int image;

        public Drawer(String drawerItemName, int image) {

            this.drawerItemName = drawerItemName;
            this.image = image;
        }

        public String getDrawerItemName() {
            return drawerItemName;
        }

        public int getImage() {
            return image;
        }

    }

    private MaterialDialog dialog;
    public void showProgress(String title){
        dialog = new MaterialDialog.Builder(this)
                .title(title)
                .content("请稍候")
                .progress(true, 100)
                .cancelable(false)
                .show();
    }

    public void dismissProgress(){
        if (dialog != null){
            dialog.dismiss();
        }
    }

    public void editFace(){
        new MaterialDialog.Builder(this)
                .title("请选择图片来源")
                .items(new String[]{"拍照", "相册"})
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        preFace(i);
                    }
                })
                .show();
    }

    private String face = "";
    private ImageProvider mImageProvider;
    private Bitmap bitmap;
    public void preFace(int style){
        ImageProvider.OnImageSelectListener onImageSelectListener = new ImageProvider.OnImageSelectListener() {
            @Override
            public void onImageSelect() {
                showProgress("请稍后");
            }

            @Override
            public void onImageLoaded(Uri uri) {
                dismissProgress();

                mImageProvider.cropImage(uri, 300, 300, new ImageProvider.OnImageSelectListener() {
                    @Override
                    public void onImageSelect() {
                        showProgress("修改中");
                    }

                    @Override
                    public void onImageLoaded(Uri uri) {
                        dismissProgress();
                        try {
                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (bitmap != null) {
                            final BmobFile bmobFile = new BmobFile(new File(uri.getPath()));
                            RemoteFileModel.getInstance().upload(bmobFile, new UploadFileListener() {
                                @Override
                                public void onSuccess() {
                                    face = bmobFile.getFileUrl(TravelBanActivity.this);
                                    mImage.setImageURI(Uri.parse(face));
                                    AccountModel.getInstance().updateFace(face);
                                    ExUtils.Toast("上传文件成功");
                                }
                                @Override
                                public void onFailure(int i, String s) {
                                    ExUtils.Toast("上传文件失败");
                                }
                            });
                        }
                    }

                    @Override
                    public void onError() {
                        ExUtils.Toast("上传文件失败");
                    }
                });
            }

            @Override
            public void onError() {
                ExUtils.Toast("上传文件失败");
            }
        };
        switch (style){
            case 0:
                mImageProvider.getImageFromCamera(onImageSelectListener);
                break;
            case 1:
                mImageProvider.getImageFromAlbum(onImageSelectListener);
                break;
        }
    }

}


