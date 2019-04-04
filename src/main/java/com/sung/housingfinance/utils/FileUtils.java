package com.sung.housingfinance.utils;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class FileUtils {

    public static List<List<String>> readCsv(File file) throws Exception{
        List<List<String>> result = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), "EUC-KR"));
        String line = "";

        while((line = bufferedReader.readLine()) != null){

            String[] row = line.split( ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            List<String> rowList = new ArrayList<>();
            for(String col : row) {
                rowList.add(col.replace("\"", "").replace(",",""));
            }

            result.add(rowList);
        }

        if(result.size() < 1) {
            log.info("빈 파일입니다.");
        }

        return result;
    }




}
