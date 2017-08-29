package com.linkhand.baixingguanjia.entity;

import android.util.Log;

import java.io.Serializable;

import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;

/**
 * Created by JCY on 2017/7/8.
 * 说明：
 */

public class Xiaoqu implements Serializable {

    /**
     * id : 1
     * name : 小区
     * fu_id : 0
     */

    private String id;
    private String name;
    private String fu_id;
    private char letter;

    public Xiaoqu() {
    }

    public Xiaoqu(String name) {
        this.name = name;
        this.letter = '#';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.letter = letterChar(name);
    }

    public String getFu_id() {
        return fu_id;
    }

    public void setFu_id(String fu_id) {
        this.fu_id = fu_id;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    private char letterChar(String s) {
        char a = s.charAt(0);
        int v = (int) a;
        if (v >= 19968 && v <= 171941) {
            Log.d("isChinese", "onResult: " + "yes");
            String name = s.toUpperCase();
            String sep = "";
            String py = PinyinHelper.convertToPinyinString(name, sep, PinyinFormat.WITHOUT_TONE);
            return py.toUpperCase().charAt(0);
        } else {
            Log.d("isChinese", "onResult: " + "No");
            return s.toUpperCase().charAt(0);
        }
    }
}
