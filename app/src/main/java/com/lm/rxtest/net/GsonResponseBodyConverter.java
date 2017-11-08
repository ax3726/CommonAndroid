package com.lm.rxtest.net;

import com.google.gson.Gson;
import com.lm.rxtest.net.ex.ApiException;
import com.lm.rxtest.net.ex.ResultException;
import com.lm.rxtest.model.BaseBean;
import com.lm.rxtest.model.ResponseCodeEnum;
import com.lm.rxtest.model.ResultResponse;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by zk on 2017/6/1.
 * Description：自定义gson转换器
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            //ResultResponse 只解析code字段进行约定异常处理
            ResultResponse resultResponse = gson.fromJson(response, ResultResponse.class);
            if (resultResponse.getStatus()==200) {
                return gson.fromJson(response, type);
            } else if (resultResponse.getStatus()==404) {
                throw new ApiException(ResponseCodeEnum.NODATA);
            } else {
                BaseBean baseBean = gson.fromJson(response, BaseBean.class);
                throw new ResultException(baseBean.getStatus(), baseBean.getInfo());
            }
        } finally {
            value.close();
        }
    }
}
