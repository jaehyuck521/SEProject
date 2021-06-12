package gachon.mpclass.setermtest;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.network.storage.ImageUploadResponse;
import com.kakao.util.helper.log.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//class for sending the png file to kakao link
public class KakaoTalk {
    public void kakaolink(Context context) {
        Date day = new Date();
        File imageFile = new File(Environment.getExternalStorageDirectory() + "/Test/image0.png");
        // get the png file path
        KakaoLinkService.getInstance() //png upload to kakao server failure
                .uploadImage(context, true, imageFile, new ResponseCallback<ImageUploadResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "이미지 업로드 실패: " + errorResult);
                    }
                    @Override
                    //upload success
                    public void onSuccess(ImageUploadResponse result) { //카카오 서버에 이미지 업로드하고, 업로드하면, url을 받아온다.
                        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
                        String toDay = date.format(day); //get the now date and time
                        FeedTemplate params = FeedTemplate //make the kakao link file
                                .newBuilder(ContentObject.newBuilder("근무표",
                                        result.getOriginal().getUrl(), //image link url
                                        LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                                                .setMobileWebUrl("https://developers.kakao.com").build())
                                        .setDescrption(toDay) //set the now date and time
                                        .build())
                                //.addButton(new ButtonObject("웹에서 보기", LinkObject.newBuilder().setWebUrl("'https://developers.kakao.com").setMobileWebUrl("'https://developers.kakao.com").build()))
                                .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
                                        .setWebUrl("'https://developers.kakao.com")
                                        .setMobileWebUrl("'https://developers.kakao.com")
                                        .setAndroidExecutionParams("key1=value1")
                                        .setIosExecutionParams("key1=value1")
                                        .build()))
                                .build();
                        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
                        serverCallbackArgs.put("user_id", "${current_user_id}"); //get the current user kakao id
                        serverCallbackArgs.put("product_id", "${shared_product_id}");
                        //use the kakao link service
                        KakaoLinkService.getInstance().sendDefault(context, params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
                            @Override
                            public void onFailure(ErrorResult errorResult) {
                                Logger.e(errorResult.toString());
                            }
                            @Override
                            public void onSuccess(KakaoLinkResponse result) {

                            }
                        });
                    }


                });

    }
    }