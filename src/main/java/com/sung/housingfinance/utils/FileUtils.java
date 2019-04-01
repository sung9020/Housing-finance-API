package com.sung.housingfinance.utils;/*
 *
 * @author 123msn
 * @since 2019-04-01
 */

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    public List<List<String>> readCsv(File file) throws Exception{
        List<List<String>> result = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), "UTF8"));
        String line = "";

        while((line = bufferedReader.readLine()) != null){
            List<String> rowList = Arrays.asList(line.split(","));
            result.add(rowList);
        }

        return result;
    }




}
