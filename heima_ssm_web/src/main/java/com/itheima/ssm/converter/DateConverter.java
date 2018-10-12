package com.itheima.ssm.converter;



import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String,Date> {


    @Override
    public Date convert(String source) {
        if (source!=null){
            SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
            try {
                Date date = sdf.parse (source);
                return date;
            } catch (ParseException e) {
               new RuntimeException ("数据类型转换失败!");
            }
        }else{
            System.out.println ("请传入数据!");
        }

        return null;
    }
}
