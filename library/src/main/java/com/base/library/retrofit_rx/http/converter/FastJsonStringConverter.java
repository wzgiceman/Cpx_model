package com.base.library.retrofit_rx.http.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * RequestBody转换器，转换为String
 * @author xuechao
 * @date 2018/10/17 上午11:00
 * @copyright cpx
 */

public class FastJsonStringConverter implements Converter<ResponseBody,String> {


    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }
}
