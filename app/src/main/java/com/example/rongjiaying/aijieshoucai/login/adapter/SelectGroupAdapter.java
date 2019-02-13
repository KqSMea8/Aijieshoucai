package com.example.rongjiaying.aijieshoucai.login.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.AppCompatTextView;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.widget.CustomShapeImageView;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupBasicInfo;
import cn.jpush.im.android.api.model.GroupInfo;
import jiguang.chat.pickerimage.utils.FileUtil;

public class SelectGroupAdapter extends SimpleAdapter<GroupBasicInfo> {
    public SelectGroupAdapter(Context context) {
        super(context, R.layout.item_selectgrouplayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, GroupBasicInfo item) {
        final CustomShapeImageView iv_icon= (CustomShapeImageView) viewHoder.getView(R.id.iv_icon);
        final AppCompatTextView tvTitle=viewHoder.getAppCompatTextView(R.id.tv_title);
        final AppCompatTextView tvNum=viewHoder.getAppCompatTextView(R.id.tv_num);

    if (item!=null)
    {
        JMessageClient.getGroupInfo(item.getGroupID(), new GetGroupInfoCallback() {
            @Override
            public void gotResult(int i, String s, GroupInfo groupInfo) {
                if (i==0)
                {
                    tvNum.setText(groupInfo.getGroupMemberInfos().size()+"人");

                    tvTitle.setText(groupInfo.getGroupName());


                    File avatarFile = groupInfo.getAvatarFile();
                    if (avatarFile != null && avatarFile.exists()) {
                        iv_icon.setImageBitmap(BitmapFactory.decodeFile(avatarFile.getAbsolutePath()));
                    } else {
                        groupInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                            @Override
                            public void gotResult(int i, String s, Bitmap bitmap) {
                                if (i == 0) {
                                    iv_icon.setImageBitmap(bitmap);
                                } else {
                                    iv_icon.setImageResource(R.drawable.jmui_head_icon);
                                }
                            }
                        });
                    }
                }
            }
        });
    }


    }
}
