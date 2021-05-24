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
import com.kakao.message.template.SocialObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.network.storage.ImageUploadResponse;
import com.kakao.util.helper.log.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;

public class KakaoTalk {
    public void kakaolink(Context context) {
        Date day = new Date();
        File imageFile = new File(Environment.getExternalStorageDirectory() + "/Test/image0.png");
        KakaoLinkService.getInstance()
                .uploadImage(context, true, imageFile, new ResponseCallback<ImageUploadResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "이미지 업로드 실패: " + errorResult);
                    }
                    @Override
                    public void onSuccess(ImageUploadResponse result) { //카카오 서버에 이미지 업로드하고, 업로드하면, url을 받아온다.
                        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
                        String toDay = date.format(day);
                        FeedTemplate params = FeedTemplate
                                .newBuilder(ContentObject.newBuilder("근무표",
                                        result.getOriginal().getUrl(), //이미지 링크 url
                                        LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                                                .setMobileWebUrl("https://developers.kakao.com").build())
                                        .setDescrption(toDay) //당일 날짜를 알려준다
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
                        serverCallbackArgs.put("user_id", "${current_user_id}"); //현재 아이디와 친구들 아이디를 가져온다.
                        serverCallbackArgs.put("product_id", "${shared_product_id}");
                        KakaoLinkService.getInstance().sendDefault(context, params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
                            @Override
                            public void onFailure(ErrorResult errorResult) {
                                Logger.e(errorResult.toString());
                            }
                            @Override
                            public void onSuccess(KakaoLinkResponse result) {
                                // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
                            }
                        });
                    }


                });

    }
    }