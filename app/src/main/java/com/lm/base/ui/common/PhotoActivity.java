package com.lm.base.ui.common;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.lm.base.common.MyApplication;
import com.lm.lib_common.base.BaseActivity;
import com.lm.lib_common.base.BasePresenter;
import com.lm.lib_common.utils.FileProvider7;
import com.lm.lib_common.utils.runtimepermission.PermissionsManager;
import com.lm.lib_common.utils.runtimepermission.PermissionsResultAction;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by It_young on 15/4/8.
 * 内置拍照或者选择照片的Activity
 */
public abstract class PhotoActivity<P extends BasePresenter, B extends ViewDataBinding> extends BaseActivity<P, B> {

    private int queue = -1;

    private int aspectX = 1, aspectY = 1;
    private int outputX = 800, outputY = 800;

    public int getAspectX() {
        return aspectX;
    }

    public void setAspectX(int aspectX) {
        this.aspectX = aspectX;
    }

    public int getAspectY() {
        return aspectY;
    }

    public void setAspectY(int aspectY) {
        this.aspectY = aspectY;
    }

    public int getOutputX() {
        return outputX;
    }

    public void setOutputX(int outputX) {
        this.outputX = outputX;
    }

    public int getOutputY() {
        return outputY;
    }

    public void setOutputY(int outputY) {
        this.outputY = outputY;
    }

    protected void takephoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider7.getUriForFile(aty,new File(MyApplication.getBase_Path() + temppicname)));
        startActivityForResult(intent, 1);
    }

    /**
     * 销毁图片文件
     */
    private void destoryBimap() {
        if (photo != null && !photo.isRecycled()) {
            photo.recycle();
            photo = null;
        }
    }

    private Bitmap photo = null;
    private String picPath = "";

    public void doPhoto() {

        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(aty, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        }, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
                String filename = "MT" + (t.format(new Date())) + ".jpg";
                String state = MyApplication.getBase_Path();

                File photoFile = new File(state + "/" + filename);// 图片储存路径
                if (!photoFile.getParentFile().exists()) {
                    photoFile.getParentFile().mkdirs();
                }
                picPath = state + "/" + filename;
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");


                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider7.getUriForFile(aty,photoFile));
                startActivityForResult(intent, 4);
            }

            @Override
            public void onDenied(String permission) {
                Toast.makeText(MyApplication.getInstance(), "请开启权限才能使用相机相册功能哦!", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void doPhoto(int queue) {
        this.queue = queue;
        SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
        String filename = "MT" + (t.format(new Date())) + ".jpg";
        String state = MyApplication.getBase_Path();

        File photoFile = new File(state + "/" + filename);// 图片储存路径
        if (!photoFile.getParentFile().exists()) {
            photoFile.getParentFile().mkdirs();
        }
        picPath = state + "/" + filename;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider7.getUriForFile(aty,photoFile));
        startActivityForResult(intent, 1);
    }

    private String output_image = "";

    protected void pickphoto() {

        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(aty, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        }, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                File outputImage = new File(MyApplication.getBase_Path(),
                        "output_image.jpg");
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                try {
                    outputImage.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
                output_image = outputImage.getAbsolutePath();
                Intent intent = new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_PICK);
  /*   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {//4.4及以上    intent2.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {//4.4以下
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
       intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));*/
                startActivityForResult(intent, 5);
            }

            @Override
            public void onDenied(String permission) {
                Toast.makeText(MyApplication.getInstance(), "请开启权限才能使用相机相册功能哦!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    protected void pickphoto(int queue) {
        this.queue = queue;
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(aty, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        }, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, 2);
            }

            @Override
            public void onDenied(String permission) {
                Toast.makeText(MyApplication.getInstance(), "请开启权限才能使用相机相册功能哦!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void takephoto(int queue) {
        this.queue = queue;
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(aty, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        }, new PermissionsResultAction() {
            @Override
            public void onGranted() {

                temppicname = "/takephoto_temp" + String.valueOf(System.currentTimeMillis()).substring(6) + ".jpg";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider7.getUriForFile(aty,new File(MyApplication.getBase_Path() + temppicname)));
                startActivityForResult(intent, 1);
            }

            @Override
            public void onDenied(String permission) {
                Toast.makeText(MyApplication.getInstance(), "请开启权限才能使用相机相册功能哦!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public static final int PICTYPESQUARE = 1;//正方形
    public static final int PICTYPERCE1 = 2;//16:9长方形
    public static final int PICTYPERCE2 = 3;//4:3长方形

    protected void takephoto(int queue, int type) {
        this.queue = queue;
        switch (type) {
            case PICTYPESQUARE://正方形
                outputX = 300;
                outputY = 300;
                aspectX = 1;
                aspectY = 1;
                break;
            case PICTYPERCE1://长方形16:9
                outputX = 400;
                outputY = 300;
                aspectX = 16;
                aspectY = 9;
                break;
            case PICTYPERCE2://长方形4:3
                outputX = 400;
                outputY = 400;
                aspectX = 4;
                aspectY = 3;
                break;
        }
        temppicname = "/takephoto_temp.jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider7.getUriForFile(aty,new File(MyApplication.getBase_Path() + temppicname)));
        startActivityForResult(intent, 1);
    }


    protected void pickphoto(int queue, int type) {
        this.queue = queue;
        switch (type) {
            case PICTYPESQUARE://正方形
                outputX = 300;
                outputY = 300;
                aspectX = 1;
                aspectY = 1;
                break;
            case PICTYPERCE1://长方形16:9
                outputX = 1600;
                outputY = 900;
                aspectX = 16;
                aspectY = 9;
                break;
            case PICTYPERCE2://长方形4:3
                outputX = 1600;
                outputY = 1200;
                aspectX = 4;
                aspectY = 3;
                break;
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, 2);
    }

    String choosePicPath = null;
    String returnpicpath = MyApplication.getBase_Path() + "/takephoto.jpg";
    String temppic = MyApplication.getBase_Path();
    String temppicname = "/takephoto_temp.jpg";

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {

        Log.e("startPhotoZoom.uri==>", uri.toString());
        Intent intent = new Intent("com.android.camera.action.CROP");
       // intent.setDataAndType(uri, "image/*");
        intent.setDataAndType(getImageContentUri(new File(uri.getPath())), "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", getAspectX());
        intent.putExtra("aspectY", getAspectY());
        // outputX outputY 是裁剪图片宽高
     /*   intent.putExtra("outputX", getOutputX());
        intent.putExtra("outputY", getOutputY());*/

        intent.putExtra("scale",true);
        intent.putExtra("scaleUpIfNeeded", true);
        File tempFile = new File(returnpicpath);
        intent.putExtra("output", Uri.fromFile(tempFile));// 保存到原文件
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        intent.putExtra("back_icon-data", false);
        startActivityForResult(intent, 3);
    }

    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1://拍照
                    Log.e("onActivityResult", temppic + temppicname);
                    File temp = new File(temppic + temppicname);
                    startPhotoZoom(Uri.fromFile(temp));
                    break;
                case 2://选择照片
                 outputX = 400;
                    outputY = 400;
                    startPhotoZoom(data.getData());
                    break;
                case 3://裁剪过后
                    if (data != null) {
                        Bitmap image = BitmapFactory.decodeFile(returnpicpath);
                        if (image != null) {
                            try {
                                File dir = new File(MyApplication.getBase_Path());
                                File myCaptureFile = new File(dir, "photo" + System.currentTimeMillis() + ".jpg");
                                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                                image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                                bos.flush();
                                bos.close();
                                choosePicPath = myCaptureFile.getPath();
                                photoSuccess(choosePicPath, myCaptureFile, queue);
                                delTempPic(temppic + temppicname);
                                delTempPic(returnpicpath);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            photoFaild();
                        }
                    } else {
                        photoFaild();
                    }
                    break;
                case 4:
                    File file = new File(picPath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    photoSuccess(picPath, file, queue);
                    break;
                case 5:
                    //  String realPathFromURI = getRealPathFromURI(data.getData());
                    String realPathFromURI = getPath(aty, data.getData());
                    File choose_file = new File(realPathFromURI);
                    if (!choose_file.exists()) {
                        choose_file.mkdirs();
                    }
                    photoSuccess(realPathFromURI, choose_file, queue);
                    break;
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String handleImageOnKitkat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri
                    .getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri
                    .getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果不是document类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        }
        return imagePath;

    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    public String getRealFilePath(final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        if (!TextUtils.isEmpty(contentUri.getAuthority())) {
            Cursor cursor = getContentResolver().query(contentUri,
                    new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (null == cursor) {
                Toast.makeText(this, "图片没找到", Toast.LENGTH_SHORT).show();
                return "";
            }
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        } else {
            path = contentUri.getPath();
        }


        return path;
    }

    /**
     * <br>功能简述:4.4及以上获取图片的方法
     * <br>功能详细描述:
     * <br>注意:
     *
     * @param context
     * @param uri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {


        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;


        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];


                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {


                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));


                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];


                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }


                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};


                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {


            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();


            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }


        return null;
    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {


        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};


        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    /**
     * The Uri to check.
     *
     * @param uri
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * 存储的临时的图片 用完进行删除
     **/
    private void delTempPic(String path) {
        File temp = new File(path);
        if (temp.isFile() && temp.exists()) {
            temp.delete();
        }
    }

    public abstract void photoSuccess(String path, File file, int... queue);

    public abstract void photoFaild();
}
