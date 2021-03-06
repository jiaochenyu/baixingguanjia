package com.linkhand.baixingguanjia.utils;

import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.linkhand.baixingguanjia.entity.CommonType;
import com.linkhand.baixingguanjia.entity.Qu;
import com.linkhand.baixingguanjia.entity.Sheng;
import com.linkhand.baixingguanjia.entity.Shi;
import com.linkhand.baixingguanjia.entity.Xiaoqu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

/**
 * Created by JCY on 2017/7/8.
 * 说明： 解析数据 省 市 区 小区
 */

public class JSONUtils {

    /**
     * 获取省 市 区 小区 四级联动数据  以Json字符串传递
     *
     * @param jsonArray
     * @return
     * @throws JSONException
     */
    public static List<Sheng> getLocationDataAll(JSONArray jsonArray) throws JSONException {

        List<Sheng> mlist = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Sheng sheng = new Sheng();
            JSONObject shengJson = jsonArray.getJSONObject(i);
            sheng.setId(shengJson.getString("id"));
            sheng.setName(shengJson.getString("name"));
            List<Shi> shiList = new ArrayList<>();
            JSONArray shiArray = shengJson.getJSONArray("shi");
            for (int j = 0; j < shiArray.length(); j++) {
                Shi shi = new Shi();
                JSONObject shiJson = shiArray.getJSONObject(j);
                shi.setId(shiJson.getString("id"));
                shi.setName(shiJson.getString("name"));
                List<Qu> quList = new ArrayList<>();
                JSONArray quArray = shiJson.getJSONArray("qu");
                for (int k = 0; k < quArray.length(); k++) {
                    Qu qu = new Qu();
                    JSONObject quJson = quArray.getJSONObject(k);
                    qu.setName(quJson.getString("name"));
                    qu.setId(quJson.getString("id"));
                    List<Xiaoqu> xiaoquList = new ArrayList<>();
                    JSONArray xiaoquArray = quJson.getJSONArray("xiaoqu");
                    for (int l = 0; l < xiaoquArray.length(); l++) {
                        Xiaoqu xiaoqu = new Xiaoqu();
                        JSONObject xiaoquJson = xiaoquArray.getJSONObject(l);
                        xiaoqu.setName(xiaoquJson.getString("name"));
                        xiaoqu.setId(xiaoquJson.getString("id"));
                        xiaoquList.add(xiaoqu);
                    }
                    qu.setXiaoquList(xiaoquList);
                    quList.add(qu);
                }
                shi.setQuList(quList);
                shiList.add(shi);
            }
            sheng.setShiList(shiList);
            mlist.add(sheng);
        }
        Gson gson = new Gson();
        String json = gson.toJson(mlist).toString();

        return mlist;
    }

    /**
     * 地区二级联动 区 小区
     *
     * @param
     * @return
     * @throws JSONException
     */
    public static Sheng getLocationData(JSONObject jsonObject) throws JSONException {
        Gson gson = new Gson();
        Sheng sheng = gson.fromJson(jsonObject.get("sheng").toString(), Sheng.class);
        Shi shi = gson.fromJson(jsonObject.get("shi").toString(), Shi.class);
        sheng.setShi(shi);
        List<Shi> shiList = new ArrayList<>();
        List<Qu> quList = new ArrayList<>();
        JSONArray quArray = jsonObject.getJSONArray("qu");
        for (int k = 0; k < quArray.length(); k++) {
            Qu qu = new Qu();
            JSONObject quJson = quArray.getJSONObject(k);
            qu.setName(quJson.getString("name"));
            qu.setId(quJson.getString("id"));
            List<Xiaoqu> xiaoquList = new ArrayList<>();
            JSONArray xiaoquArray = quJson.getJSONArray("xiaoqu");
            for (int l = 0; l < xiaoquArray.length(); l++) {
                Xiaoqu xiaoqu = new Xiaoqu();
                JSONObject xiaoquJson = xiaoquArray.getJSONObject(l);
                xiaoqu.setName(xiaoquJson.getString("name"));
                xiaoqu.setId(xiaoquJson.getString("id"));
                xiaoquList.add(xiaoqu);
            }
            qu.setXiaoquList(xiaoquList);
            quList.add(qu);
        }
        Log.e("地区接口参数", "小区数量: " +  quList.get(0).getXiaoquList().size());
        shi.setQuList(quList);
        shiList.add(shi);

        sheng.setShiList(shiList);
        return sheng;
    }

    /**
     * 获取的数据格式化
     * 四级级联动
     */
    public static Map<String, Object> getDiquAll(List<Sheng> shengList) {
        ArrayList<Sheng> shengs = new ArrayList<Sheng>();
        SparseArray<LinkedList<Shi>> shis = new SparseArray<LinkedList<Shi>>();
        SparseArray<SparseArray<LinkedList<Qu>>> qus = new SparseArray<>();
        SparseArray<SparseArray<SparseArray<LinkedList<Xiaoqu>>>> xiaoqus = new SparseArray<>();
        Map<String, Object> map = new HashMap<>();


        for (int i = 0; i < shengList.size(); i++) {
            shengs.add(shengList.get(i));
            LinkedList<Shi> shiItem = new LinkedList<Shi>();
            List<Shi> shiList = shengList.get(i).getShiList();
            SparseArray<LinkedList<Qu>> quSparse= new SparseArray<>();
            SparseArray<SparseArray<LinkedList<Xiaoqu>>> xiaoquSparse= new SparseArray<>();
            for (int j = 0; j < shiList.size(); j++) {
                shiItem.add(shiList.get(j));
                List<Qu> qulist =  shiList.get(j).getQuList();
                LinkedList<Qu> quItem = new LinkedList<Qu>();
                SparseArray<LinkedList<Xiaoqu>> xiaoquSparseS= new SparseArray<>();
                for (int k = 0; k < qulist.size(); k++) {
                    quItem.add(qulist.get(k));
                    List<Xiaoqu> xiaoqulist =  qulist.get(k).getXiaoquList();
                    LinkedList<Xiaoqu> xiaoquItem = new LinkedList<Xiaoqu>();
                    for (int l = 0; l < xiaoqulist.size(); l++) {
                        xiaoquItem.add(xiaoqulist.get(l));
                    }
                    xiaoquItem.add(0,new Xiaoqu("全区"));
                    xiaoquSparseS.put(k+1,xiaoquItem);
                }
                quItem.add(0,new Qu("全城"));
                quSparse.put(j,quItem);
                xiaoquSparseS.put(0,new LinkedList<Xiaoqu>());
                xiaoquSparse.put(j,xiaoquSparseS);
            }
            shis.put(i, shiItem);
//            quSparse.put(0,new LinkedList<Qu>());
            qus.put(i,quSparse);
            xiaoqus.put(i,xiaoquSparse);
        }



        map.put("shengs", shengs);
        map.put("shis", shis);
        map.put("qus", qus);
        map.put("xiaoqus", xiaoqus);
        return map;
    }


    /**
     * 获取的数据格式化  没有全城 全区
     * 四级级联动
     */
    public static Map<String, Object> getDiquAllOrigin(List<Sheng> shengList) {
        ArrayList<Sheng> shengs = new ArrayList<Sheng>();
        SparseArray<LinkedList<Shi>> shis = new SparseArray<LinkedList<Shi>>();
        SparseArray<SparseArray<LinkedList<Qu>>> qus = new SparseArray<>();
        SparseArray<SparseArray<SparseArray<LinkedList<Xiaoqu>>>> xiaoqus = new SparseArray<>();
        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < shengList.size(); i++) {
            shengs.add(shengList.get(i));
            LinkedList<Shi> shiItem = new LinkedList<Shi>();
            List<Shi> shiList = shengList.get(i).getShiList();
            SparseArray<LinkedList<Qu>> quSparse= new SparseArray<>();
            SparseArray<SparseArray<LinkedList<Xiaoqu>>> xiaoquSparse= new SparseArray<>();
            for (int j = 0; j < shiList.size(); j++) {
                shiItem.add(shiList.get(j));
                List<Qu> qulist =  shiList.get(j).getQuList();
                LinkedList<Qu> quItem = new LinkedList<Qu>();
                SparseArray<LinkedList<Xiaoqu>> xiaoquSparseS= new SparseArray<>();
                for (int k = 0; k < qulist.size(); k++) {
                    quItem.add(qulist.get(k));
                    List<Xiaoqu> xiaoqulist =  qulist.get(k).getXiaoquList();
                    LinkedList<Xiaoqu> xiaoquItem = new LinkedList<Xiaoqu>();
                    for (int l = 0; l < xiaoqulist.size(); l++) {
                        xiaoquItem.add(xiaoqulist.get(l));
                    }
                    xiaoquItem.add(0,new Xiaoqu("全区"));
                    xiaoquSparseS.put(k+1,xiaoquItem);
                }
                quItem.add(0,new Qu("全城"));
                quSparse.put(j,quItem);
                xiaoquSparseS.put(0,new LinkedList<Xiaoqu>());
                xiaoquSparse.put(j,xiaoquSparseS);
            }
            shis.put(i, shiItem);
//            quSparse.put(0,new LinkedList<Qu>());
            qus.put(i,quSparse);
            xiaoqus.put(i,xiaoquSparse);
        }



        map.put("shengs", shengs);
        map.put("shis", shis);
        map.put("qus", qus);
        map.put("xiaoqus", xiaoqus);
        return map;
    }


    /**
     * 获取的数据格式化
     * 二级联动
     */
    public static Map<String, Object> getDiqu(List<Qu> quList) {
        ArrayList<Qu> groups = new ArrayList<Qu>();
        SparseArray<LinkedList<Xiaoqu>> children = new SparseArray<LinkedList<Xiaoqu>>();
        Map<String, Object> map = new HashMap<>();
        groups.add(new Qu("全城"));
        //测试数据
        for (int i = 0; i < quList.size(); i++) {
            groups.add(quList.get(i));
            LinkedList<Xiaoqu> tItem = new LinkedList<Xiaoqu>();
            for (int j = 0; j < quList.get(i).getXiaoquList().size(); j++) {
                tItem.add(quList.get(i).getXiaoquList().get(j));
            }
            tItem.add(0, new Xiaoqu("全区"));
            children.put(i + 1, tItem);
        }
        children.put(0, new LinkedList<Xiaoqu>());
        map.put("groups", groups);
        map.put("children", children);
        return map;
    }

    /**
     *
     * @param quList
     * @return
     *  二级联动 没有全城 全区
     */
    public static Map<String, Object> getDiqu2(List<Qu> quList) {
        ArrayList<Qu> groups = new ArrayList<Qu>();
        SparseArray<LinkedList<Xiaoqu>> children = new SparseArray<LinkedList<Xiaoqu>>();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < quList.size(); i++) {
            groups.add(quList.get(i));
            LinkedList<Xiaoqu> tItem = new LinkedList<Xiaoqu>();
            for (int j = 0; j < quList.get(i).getXiaoquList().size(); j++) {
                tItem.add(quList.get(i).getXiaoquList().get(j));
            }
            children.put(i, tItem);
        }
        map.put("groups", groups);
        map.put("children", children);
        return map;
    }

    /**
     * 获取cartype id 和name转换成  两个String数组
     */
    public static Map<String, List<String>> getStrArray(List<?> mList) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> items = new ArrayList<>();
        List<String> itemsValue = new ArrayList<>();
        if (mList.get(0) instanceof CommonType) {
            for (int i = 0; i < mList.size(); i++) {
                items.add(((CommonType) mList.get(i)).getName());
                itemsValue.add(((CommonType) mList.get(i)).getId());
            }
        }
        items.add(0, "类型不限");
        itemsValue.add(0, "n");
        map.put("items", items);
        map.put("itemsValue", itemsValue);
        return map;
    }

    /**
     * 地区二级联动选择器AndroidPicker 二手车发布
     * 市 区 小区 存到 自带的Province 中
     */
    public static ArrayList<Province> getAddress2PickerData(Sheng sheng) {
        ArrayList<Province> mList = new ArrayList<>();
        Shi shi = sheng.getShiList().get(0);
        Province province = new Province(shi.getId(), shi.getName());
        ArrayList<City> cityList = new ArrayList<>();
        for (int i = 0; i < shi.getQuList().size(); i++) {
            Qu qu = shi.getQuList().get(i);
            List<Xiaoqu> xiaoquList = qu.getXiaoquList();
            ArrayList<County> countyList = new ArrayList<>();
            for (int j = 0; j < xiaoquList.size(); j++) {
                Xiaoqu xiaoqu = xiaoquList.get(j);
                County county = new County(xiaoqu.getId(), xiaoqu.getName());
                countyList.add(county);
            }
            City city = new City(qu.getId(), qu.getName());
            city.setCounties(countyList);
            cityList.add(city);
        }
        province.setCities(cityList);
        mList.add(province);
        return mList;
    }
}
